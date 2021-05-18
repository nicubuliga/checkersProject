import controllers.Controller;
import controllers.MyMouseListener;
import views.BoardPanel;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApp extends JFrame {
    String serverAddress = "127.0.0.1"; // The server's IP address
    int PORT = 31401; // The server's port
    Socket socket = null;

    // View
    private BoardPanel boardPanel;

    private PrintWriter out;
    private BufferedReader in;

    public ClientApp ()
    {
        try {
            String name = (String) JOptionPane.showInputDialog(null, "Enter your name to Connect", "Connect to Server",
                    JOptionPane.OK_CANCEL_OPTION);
            connect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        socket = new Socket(serverAddress, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        Controller controller = new Controller(in, out);

//        setup(controller);
        new Thread(controller).start();
    }

    public void setup(Controller controller)
    {
        MyMouseListener listener = new MyMouseListener();
        listener.setController(controller);

        boardPanel = new BoardPanel(listener);
        controller.setBoardPanel(boardPanel);
        add(boardPanel);
    }

}