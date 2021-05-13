package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ClientThread extends Thread {
    private Socket socket = null;
    boolean running = true;
    DataUtil dataUtil;

    public ClientThread(Socket socket, DataUtil dataUtil) throws SocketException {
        this.socket = socket;
        this.socket.setSoTimeout(90000);
        this.dataUtil = dataUtil;
    }

    public void run() {

        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            while (running) {

                try {
                    String request = in.readLine();
                    String raspuns = "Command not found";
                    // Send the response to the output stream: server → client
                    System.out.println(request);

                    out.println(raspuns);
                    out.flush();
                } catch (IOException e)
                {
                    running = false;
                    out.println("Disconnected");
                }

            }

//            for (server.Person p : dataUtil.getPersons()) {
//                System.out.println(p);
//            }

        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}