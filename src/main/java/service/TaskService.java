package service;

import dao.TaskDAO;
import model.Task;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    private TaskDAO taskDAO;

    public TaskService() {
        this.taskDAO = new TaskDAO();
    }

    public boolean creerTache(Task task) {
        if (task.getTitre() == null || task.getTitre().isEmpty()) {
            return false;
        }
        return taskDAO.create(task);
    }

    public List<Task> getTachesParUser(int userId) {
        return taskDAO.findByUser(userId);
    }

    public List<Task> filtrerParStatut(int userId, Task.Statut statut) {
        return taskDAO.findByUser(userId).stream()
                .filter(t -> t.getStatut() == statut)
                .collect(Collectors.toList());
    }

    public List<Task> filtrerParPriorite(int userId, Task.Priorite priorite) {
        return taskDAO.findByUser(userId).stream()
                .filter(t -> t.getPriorite() == priorite)
                .collect(Collectors.toList());
    }

    public boolean mettreAJour(Task task) {
        return taskDAO.update(task);
    }

    public boolean supprimer(int taskId) {
        return taskDAO.delete(taskId);
    }

    public boolean changerStatut(Task task, Task.Statut nouveauStatut) {
        task.setStatut(nouveauStatut);
        return taskDAO.update(task);
    }
}