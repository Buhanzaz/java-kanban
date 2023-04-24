package model;

import service.Status;

import java.util.Objects;

public class Subtask extends AbstractTask{
    protected int epicId;
    public Subtask(String name, String description, int id, Status status) {
        super(name, description, id, status);
    }

    public Subtask(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}