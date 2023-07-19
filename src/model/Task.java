package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task extends AbstractTask {
    public Task(String name, String description, int id, Status status, int duration, LocalDateTime startTime) {
        super(name, description, id, status);
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, int duration, LocalDateTime startTime) {
        super(name, description);
        this.duration = duration;
        this.startTime = startTime;
    }


    @Override
    public TypeTasks getType() {
        return TypeTasks.TASKS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask task = (AbstractTask) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", epicId=" + epicId +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + getEndTime() +
                '}';
    }
}