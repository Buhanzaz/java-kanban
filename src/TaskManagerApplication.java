import web.server.HttpTaskServer;
import web.server.KVServer;

import java.io.IOException;

public class TaskManagerApplication {

    public static void main(String[] args) {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
