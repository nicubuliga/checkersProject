package controllers;

import views.BoardPanel;

import java.io.*;
import java.net.SocketException;
import java.util.Scanner;

public class Controller implements Runnable{


    private PrintWriter out;
    private BufferedReader in;
    public int idPlayer ;
    boolean running = true;


    private BoardPanel boardPanel;

    public Controller(BufferedReader bufferedReader, PrintWriter printWriter)
    {
        this.in = bufferedReader;
        this.out = printWriter;
    }

    public void setBoardPanel(BoardPanel panel){
        this.boardPanel = panel;
    }

    @Override
    public void run() {
        while (running) {
            try {
                String responseServer = in.readLine();
                String[] args = responseServer.split(" ");
                String response = args[0];

                if(response.equals("test"))
                {
                    out.println("ok");
                    out.flush();
                } else
                if(response.equals("turn"))
                {
                    Scanner s = new Scanner(System.in);
                    String command = s.nextLine();
                    // metoda miscare
                    out.println(command);
                    out.flush();
                }
                else
                    if(response.equals("start"))
                    {
                        this.idPlayer = Integer.parseInt(args[1]);
                    }
            } catch (SocketException e)
            {
                running = false;
                System.out.println("Server disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
