package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class Server {
    DataUtil data;
    //     Define the port on which the server is listening
    public static final int PORT = 31401;

    public Server() throws IOException {
        DataUtil dataUtil = new DataUtil();
        data = dataUtil;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
//            serverSocket.setSoTimeout(5000);
            while (dataUtil.running) {
                Socket socket = null;
                try {
                    if (dataUtil.socketQueue != null)
                        System.out.println(dataUtil.socketQueue.isClosed());
                    socket = serverSocket.accept();

                    String type = readOpponentType(socket);

                    if(type.equals("AI")) {
                        System.out.println("Joaca cu botul");
                    } else if(type != null){

                        if (dataUtil.socketQueue != null) {
                            try {
                                PrintWriter printtest = new PrintWriter(dataUtil.socketQueue.getOutputStream(), true);
                                printtest.println("test");
                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(dataUtil.socketQueue.getInputStream()));
                                int testMess = in.read();
                                if (testMess != -1) {
                                    new ClientThread(data.socketQueue, socket, dataUtil).start();
                                }
                                dataUtil.socketQueue = null;
                            } catch (SocketException e) {
                                dataUtil.socketQueue = socket;
                            }
                        } else {
                            dataUtil.socketQueue = socket;

                        }
                    }
                } catch (SocketTimeoutException e) {
                }
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }


    private String readOpponentType(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("type");
            out.flush();
            return in.readLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;

    }


    public static void main(String[] args) throws IOException {
        Server server = new Server();

    }
}