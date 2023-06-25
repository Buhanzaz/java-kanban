package model;

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