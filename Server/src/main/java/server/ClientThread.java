package server;

import server.models.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ClientThread extends Thread {
    private Socket socket1 = null;
    private Socket socket2 = null;
    private Board gameBoard;
    boolean running = true;
    boolean turn = true;
    DataUtil dataUtil;

    public ClientThread(Socket socket1, Socket socket2, DataUtil dataUtil) throws SocketException {
        this.gameBoard = new Board();
        this.socket1 = socket1;
        this.socket1.setSoTimeout(90000);
        this.socket2 = socket2;
        this.socket2.setSoTimeout(90000);
        this.dataUtil = dataUtil;
//        dataUtil.addSocket(socket);
    }

    public void run() {

        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket1.getInputStream()));
            PrintWriter out = new PrintWriter(socket1.getOutputStream());

            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(socket2.getInputStream()));
            PrintWriter out2 = new PrintWriter(socket2.getOutputStream());
            String move = "";
            out.println("start 1");
            out.flush();
            out2.println("start 2");
            out2.flush();
            while (running) {

                try {

                    String raspuns = "Command not found";
                    if (turn) {
                        out.println("turn " + move);
                        out.flush();
                        move = in.readLine();
                        gameBoard.makeMove(move,1);

                        if(gameBoard.checkWinner() != 0) {

                            if(gameBoard.checkWinner() == 1) {
                                out.println("GameOver winner");
                                out2.println("GameOver loser");
                            } else {
                                out.println("GameOver loser");
                                out2.println("GameOver winner");
                            }
                            out.flush();
                            out2.println();

                            running = false;
                        }

                        System.out.println(move);
                        turn = false;
                    } else {
                        out2.println("turn " + move);
                        out2.flush();
                        move = in2.readLine();
                        gameBoard.makeMove(move,2);

                        if(gameBoard.checkWinner() != 0) {
                            if(gameBoard.checkWinner() == 1) {
                                out.println("GameOver winner");
                                out2.println("GameOver loser");
                            }else {
                                out.println("GameOver loser");
                                out2.println("GameOver winner");
                            }
                            out.flush();
                            out2.println();

                            running = false;
                        }

                        System.out.println(move);
                        turn = true;
                    }
                    // Send the response to the output stream: server → client

                } catch (IOException e) {
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
                socket1.close(); // or use try-with-resources
                socket2.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}