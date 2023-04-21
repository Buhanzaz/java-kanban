package model;

public class Task {
    public String name;
    public String description;
    public int id;
    public String status;

    public Task(String name, String description, int id, String status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }
}
