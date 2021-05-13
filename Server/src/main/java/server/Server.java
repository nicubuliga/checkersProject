package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Server {
    DataUtil data;
    //     Define the port on which the server is listening
    public static final int PORT = 31401;
    public Server() throws IOException {
        DataUtil dataUtil = new DataUtil();
        data = dataUtil;

        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(5000);
            while (dataUtil.running) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    if(dataUtil.socketQueue != null && dataUtil.socketQueue.isConnected())
                    {
                        new ClientThread(data.socketQueue, socket, dataUtil).start();
                        dataUtil.socketQueue = null;
                    }
                    else
                        dataUtil.socketQueue = socket;
                } catch (SocketTimeoutException e){
                }
            }
        } catch (IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();

    }
}