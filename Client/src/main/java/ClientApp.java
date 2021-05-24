import controllers.Controller;
import controllers.MyMouseListener;
import views.BoardPanel;

import javax.swing.*;
import java.awt.*;
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
//    private String opponent;
    private JLabel opponent;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JButton sub;
    private GridLayout gridLayout = new GridLayout(4,1);
    public ClientApp ()
    {
        try {
//            opponent = (String) JOptionPane.showInputDialog(null, "AI or Real Player", "Type",
//                    JOptionPane.OK_CANCEL_OPTION);
            setPreferredSize(new Dimension(300,200));
            setTitle("Checkers");
//            setBounds(300, 90, 900, 600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);

//            c = getContentPane();
            getContentPane().setLayout(gridLayout);
            getContentPane().setHorizontalAlignment(JLabel.CENTER);


            opponent = new JLabel("Select your opponent: ");

            opponent.setFont(new Font("Arial", Font.PLAIN, 15));
//            opponent.setSize(300, 20);
//            opponent.setLocation(100, 200);
            getContentPane().add(opponent);

            male = new JRadioButton("Male");
            male.setFont(new Font("Arial", Font.PLAIN, 15));
            male.setSelected(true);
//            male.setSize(75, 20);
//            male.setLocation(100, 150);
            getContentPane().add(male);

            female = new JRadioButton("Female");
            female.setFont(new Font("Arial", Font.PLAIN, 15));
            female.setSelected(false);
//            female.setSize(80, 20);
//            female.setLocation(175, 150);
            getContentPane().add(female);

            gengp = new ButtonGroup();
            gengp.add(male);
            gengp.add(female);

            sub = new JButton("Submit");
            sub.setFont(new Font("Arial", Font.PLAIN, 15));
            sub.setSize(100, 20);
//            sub.setLocation(150, );
//            sub.addActionListener(this);
            getContentPane().add(sub);


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

        String type = in.readLine();
        out.println(opponent);
        out.flush();

        Controller controller = new Controller(in, out);

        setup(controller);
        new Thread(controller).start();
    }

    public void setup(Controller controller)
    {
        MyMouseListener listener = new MyMouseListener();
        listener.setController(controller);

        boardPanel = new BoardPanel(listener , controller.idPlayer);
        controller.setBoardPanel(boardPanel);
        controller.setFrame(this);
        add(boardPanel);
    }

}
