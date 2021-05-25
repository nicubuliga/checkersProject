import controllers.Controller;
import controllers.MyMouseListener;
import views.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private String opponentMsg;
    private JLabel opponent;
    private JPanel inputPanel;
    private JRadioButton bot;
    private JRadioButton realPlayer;
    private ButtonGroup oppType;
    private JButton submitBtn;
    private GridLayout gridLayout = new GridLayout(4,1);

    private void readOpponentType() {
        inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(250,250));

        inputPanel.setLayout(gridLayout);

        opponent = new JLabel("Select your opponent: ");

        opponent.setFont(new Font("Arial", Font.PLAIN, 15));

        inputPanel.add(opponent);

        realPlayer = new JRadioButton("Real Player");
        realPlayer.setFont(new Font("Arial", Font.PLAIN, 15));
        realPlayer.setSelected(true);

        inputPanel.add(realPlayer);
//
        bot = new JRadioButton("AI Player");
        bot.setFont(new Font("Arial", Font.PLAIN, 15));
        bot.setSelected(false);

        inputPanel.add(bot);

        oppType = new ButtonGroup();
        oppType.add(realPlayer);
        oppType.add(bot);
//
        submitBtn = new JButton("Play");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        submitBtn.setSize(100, 20);

        inputPanel.add(submitBtn);
        add(inputPanel, BorderLayout.CENTER);

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(realPlayer.isSelected()) {
                        opponentMsg = "Real Player";
                    } else {
                        opponentMsg = "AI";
                    }
                    connect();
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Server connection error!",
                            "Error", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        });
    }

    public ClientApp ()
    {

        readOpponentType();
//            connect();
    }

    public void connect() throws IOException {
        socket = new Socket(serverAddress, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        String type = in.readLine();
        out.println(opponentMsg);
        out.flush();

        Controller controller = new Controller(in, out);

        setup(controller);

//        Noul thread responsabil pentru desfasurarea jocului
        new Thread(controller).start();
    }

    public void setup(Controller controller)
    {
        MyMouseListener listener = new MyMouseListener();
        listener.setController(controller);

        boardPanel = new BoardPanel(listener , controller.idPlayer);
        controller.setBoardPanel(boardPanel);
        controller.setFrame(this);

        inputPanel.setVisible(false);
        setLayout(new BorderLayout());
        add(boardPanel);
        setSize(new Dimension(720, 720));
        revalidate();
        repaint();
    }

}
