package model;

public class Task extends AbstractTask {
    public Task(String name, String description, int id, Status status) {
        super(name, description, id, status);
    }

    public Task(String name, String description) {
        super(name, description);
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
                '}' + '\n';
    }
}