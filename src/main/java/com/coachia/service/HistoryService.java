package com.coachia.service;

import com.coachia.dao.impl.ProductivityDAO;
import com.coachia.dao.impl.TaskDAOImpl;
import com.coachia.model.Task;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class HistoryService {

    private ProductivityDAO productivityDAO;
    private TaskDAOImpl taskDAO;

    public HistoryService(ProductivityDAO productivityDAO, TaskDAOImpl taskDAO) {
        this.productivityDAO = productivityDAO;
        this.taskDAO = taskDAO;
    }

    // ── ENREGISTRER AUTOMATIQUEMENT QUAND TÂCHE = TERMINÉ ─
    public void markAsCompleted(Task t, int userId, long timeSpentSeconds) throws SQLException {
        // 1. Changer le statut
        t.setStatus("Terminé");
        taskDAO.update(t);

        // 2. Enregistrer dans l'historique
        productivityDAO.logTime(t.getId(), userId, timeSpentSeconds);
    }
}