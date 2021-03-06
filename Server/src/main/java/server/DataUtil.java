package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Un obiect de tip DataUtil va mentine o coada a clientilor si
    perechile de clienti care sunt intr-un joc curent
 */

public class DataUtil {
    List<Socket> socketList = new ArrayList<>();
    Map<Socket,Socket> socketPairs = new HashMap<>();
    Socket socketQueue = null;
    boolean running = true;

    public List<Socket> getSocketList() {
        return socketList;
    }
    public void addSocket(Socket socket)
    {
        socketList.add(socket);
    }
    public void makePair(Socket socket1, Socket socket2)
    {
        socketPairs.put(socket1,socket2);
    }
}
