package service;

import okhttp3.*;
import com.google.gson.*;
import java.io.IOException;

public class GeminiService {

    private static final String API_KEY = "AIzaSyD0VIC5ACDOPYc5pTymJrnBS7x64QSFkoo";
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY;

    private OkHttpClient client;
    private Gson gson;

    public GeminiService() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    // ===== APPEL API GEMINI =====
    private String appelAPI(String prompt) {
        String requestBody = "{\n" +
                "  \"contents\": [{\n" +
                "    \"parts\": [{\n" +
                "      \"text\": \"" + prompt.replace("\"", "'") + "\"\n" +
                "    }]\n" +
                "  }]\n" +
                "}";

        RequestBody body = RequestBody.create(
                requestBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String responseStr = response.body().string();
                JsonObject json = JsonParser.parseString(responseStr).getAsJsonObject();
                return json.getAsJsonArray("candidates")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("content")
                        .getAsJsonArray("parts")
                        .get(0).getAsJsonObject()
                        .get("text").getAsString();
            }
        } catch (IOException e) {
            System.out.println("Erreur API Gemini: " + e.getMessage());
        }
        return "Erreur lors de la connexion à l'IA.";
    }

    // ===== FONCTION 1 : DÉCOUPER UNE TÂCHE =====
    public String decouperTache(String titreTache) {
        String prompt = "Tu es un coach de productivité. " +
                "L'utilisateur a cette tâche : " + titreTache + ". " +
                "Découpe-la en 5 sous-tâches concrètes et courtes. " +
                "Réponds avec une liste numérotée, sans introduction.";
        return appelAPI(prompt);
    }

    // ===== FONCTION 2 : REFORMULER UN OBJECTIF =====
    public String reformulerObjectif(String objectif) {
        String prompt = "Tu es un coach de productivité. " +
                "Reformule cet objectif flou en objectif SMART clair et précis : " +
                objectif + ". Réponds en 2-3 phrases maximum.";
        return appelAPI(prompt);
    }

    // ===== FONCTION 3 : CONSEILS DE PLANIFICATION =====
    public String conseilsPlanification(String situation) {
        String prompt = "Tu es un coach de productivité. " +
                "L'utilisateur décrit sa situation : " + situation + ". " +
                "Donne 3 conseils pratiques de planification. " +
                "Réponds avec une liste numérotée.";
        return appelAPI(prompt);
    }

    // ===== FONCTION 4 : PROPOSER PRIORITÉS =====
    public String proposerPriorites(String taches) {
        String prompt = "Tu es un coach de productivité. " +
                "Voici les tâches de l'utilisateur : " + taches + ". " +
                "Propose un ordre de priorité avec une courte justification. " +
                "Réponds avec une liste numérotée.";
        return appelAPI(prompt);
    }
}