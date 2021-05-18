import javax.swing.*;

public class Client {

    public static void main(String[] args) {
        ClientApp client = new ClientApp();
        client.setTitle("Checkers");
        client.pack();
        client.setVisible(true);
        client.setLocation(250, 150);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
