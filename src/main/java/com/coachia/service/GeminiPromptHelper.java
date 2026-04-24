package com.coachia.service;

import com.coachia.model.Task;
import com.coachia.model.User;
import java.util.List;

public class GeminiPromptHelper {

    // ── PROMPT : DÉCOUPER UNE TÂCHE ───────────────────────
    public static String buildDecompositionPrompt(Task task) {
        return "Tu es un coach de productivité. " +
                "L'utilisateur a cette tâche : '" + task.getTitle() + "'. " +
                "Description : '" + task.getDescription() + "'. " +
                "Découpe-la en 5 sous-tâches concrètes et réalisables. " +
                "Réponds UNIQUEMENT en JSON : {\"subtasks\": [\"...\", \"...\", ...]}";
    }

    // ── PROMPT : PROPOSER DES PRIORITÉS ──────────────────
    public static String buildPriorityPrompt(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tu es un coach de productivité. ");
        sb.append("Voici la liste des tâches de l'utilisateur : ");
        for (Task t : tasks) {
            sb.append("- ").append(t.getTitle())
                    .append(" (échéance: ").append(t.getDueDate()).append(") ");
        }
        sb.append("Propose un ordre de priorité. ");
        sb.append("Réponds UNIQUEMENT en JSON : {\"priorities\": [\"...\", ...]}");
        return sb.toString();
    }



    // ── PROMPT : CONSEILS DE PLANIFICATION ───────────────
    public static String buildPlanningAdvicePrompt(User user, List<Task> tasks) {
        return "Tu es un coach de productivité. " +
                "L'utilisateur s'appelle " + user.getName() + ". " +
                "Il a " + tasks.size() + " tâches en cours. " +
                "Donne-lui 3 conseils concrets pour mieux planifier sa semaine. " +
                "Réponds UNIQUEMENT en JSON : {\"advice\": [\"...\", \"...\", \"...\"]}";
    }
}