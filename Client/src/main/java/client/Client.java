package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    String serverAddress = "127.0.0.1"; // The server's IP address
    int PORT = 8100; // The server's port
    boolean running = true;

    public void communicate() throws IOException {
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
            while (running) {
                try {
//                    Scanner s = new Scanner(System.in);
//                    String command = s.nextLine();
//                    String[] arguments = command.split(" ");
//                    if (arguments[0].equals("exit") || arguments[0].equals("stop")) {
//                        running = false;
//                    }
//
//                    out.println(command);
////                    Waiting for response
                    String response = in.readLine();
                    if(response.equals("Your turn"))
                    {
                        Scanner s = new Scanner(System.in);
                        String command = s.nextLine();
                        // metoda miscare
                        out.println(command);
                        out.flush();
                    }
                    else
                        continue;
                    System.out.println(response);
                } catch (SocketException e)
                {
                    running = false;
                    System.out.println("Server disconnected");
                }

            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.communicate();

    }
}