package model;

import java.util.ArrayList;

public class Epic extends AbstractTask {
    protected ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name, description);
        this.id = id;
    }

    @Override
    public TypeTasks getType() {
        return TypeTasks.EPIC;
    }

    public void addSubtasksId(int id) {
        subtasksId.add(id);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void removeSubtaskId(int id) {
        Integer subId = subtasksId.indexOf(id);
        subtasksId.remove(subId);
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