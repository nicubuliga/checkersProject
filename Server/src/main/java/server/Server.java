package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Server {
    DataUtil data;
    //     Define the port on which the server is listening
    public static final int PORT = 8100;
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
                    new ClientThread(socket, dataUtil).start();
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