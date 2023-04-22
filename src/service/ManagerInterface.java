package service;

import model.Task;

public interface ManagerInterface {
    public final String NEW_PROGRESS = "NEW";
    static final String IN_PROGRESS = "IN_PROGRESS";
    static final String DONE_PROGRESS = "DONE";

    void getAllObject();
    void getById(int id);
    void deleteAll();
    void removeById(int id);
    int identifier();

}
