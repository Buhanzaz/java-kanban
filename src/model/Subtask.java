package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends AbstractTask {
    protected int epicId;

    public Subtask(int epicId, String name, String description, int id, Status status) {
        super(name, description, id, status);
        this.epicId = epicId;
    }

    public Subtask(int epicId, String name, String description) {
        super(name, description);
        this.epicId = epicId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ", duration='" + getDuration() + '\'' +
                ", startTime='" + getStartTime() + '\'' +
                ", endTime='" + getEndTime() + '\'' +
                '}' + '\n';
    }
}