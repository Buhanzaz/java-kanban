package service;


public interface ManagerInterface {
    String NEW_PROGRESS = "NEW";
    String IN_PROGRESS = "IN_PROGRESS";
    String DONE_PROGRESS = "DONE";
    void deleteAll();
    void getAllObject();
    void getById(int id);
    void removeById(int id);
    int identifier();

}
