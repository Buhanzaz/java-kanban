package model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractTask implements Comparable<AbstractTask>{
    protected String name;
    protected String description;
    protected int id;
    protected Status status;
    protected int epicId;
    protected int duration;
    protected LocalDateTime startTime;

    /*Constructor to update*/
    public AbstractTask(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;

    }

    /*Constructor to create*/
    public AbstractTask(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }


    public abstract TypeTasks getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask task = (AbstractTask) o;
        return id == task.id && epicId == task.epicId && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(AbstractTask o) {
        if (startTime != null) {
            return this.startTime.isBefore(o.startTime) ? -1 : 1;
        }

        return 1;
    }
}