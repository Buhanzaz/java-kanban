package model;

import java.time.LocalDateTime;

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

    public String toString() {
        return "Task{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ", duration='" + getDuration() + '\'' +
                ", startTime='" + getStartTime() + '\'' +
                ", endTime='" + getEndTime() + '\'' +
                '}' + '\n';
    }
}