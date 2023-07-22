package model;

import java.time.LocalDateTime;

public class Subtask extends AbstractTask {
    protected int epicId;

    public Subtask(int epicId, String name, String description, int id, Status status, int duration, LocalDateTime startTime) {
        super(name, description, id, status);
        this.epicId = epicId;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Subtask(int epicId, String name, String description, int duration, LocalDateTime startTime) {
        super(name, description);
        this.epicId = epicId;
        this.duration = duration;
        this.startTime = startTime;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TypeTasks getType() {
        return TypeTasks.SUBTASK;
    }

    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}' + '\n';
    }
}