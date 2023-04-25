package model;

import service.Status;

import java.util.HashMap;

public class Epic extends AbstractTask {
    protected HashMap<Integer, Status> subtasksIdAndStatus = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name, description);
        this.id = id;
    }

    public void setSubtasksIdAndStatus(int id, Status status) {
        subtasksIdAndStatus.put(id, status);
    }

    public HashMap<Integer, Status> getSubtasksIdAndStatus() {
        return subtasksIdAndStatus;
    }

    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}' + '\n';
    }
}