package server;

import server.ai.AIPlayer;
import server.models.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class AIThread extends Thread{
    private Socket socket1 = null;
    private Board gameBoard;
    private AIPlayer aiPlayer;
    boolean running = true;
    boolean turn = true;

    public AIThread(Socket socket1) throws SocketException {
        this.gameBoard = new Board();
        aiPlayer = new AIPlayer();
        this.socket1 = socket1;
        this.socket1.setSoTimeout(90000);
    }

    public void run() {

        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket1.getInputStream()));
            PrintWriter out = new PrintWriter(socket1.getOutputStream());

            String move = "";
            out.println("start 1");
            out.flush();

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
                            } else {
                                out.println("GameOver loser");
                            }
                            out.flush();
                            running = false;
                        }

                        System.out.println(move);
                        turn = false;
                    } else {
//                        Miscarea botului
                        move = aiPlayer.getNextMove(3, gameBoard);
                        gameBoard.makeMove(move, 2);
                        gameBoard.printBoard();
                        System.out.println("nr white piece: " + gameBoard.getNrWhitePiece());
                        System.out.println("nr black piece: " + gameBoard.getNrBlackPiece());
                        System.out.println("nr white king: " + gameBoard.getNrWhiteKing());
                        System.out.println("nr black king: " + gameBoard.getNrBlackKing());
                        if(gameBoard.checkWinner() != 0) {
                            if(gameBoard.checkWinner() == 1) {
                                out.println("GameOver winner");
                            } else {
                                out.println("GameOver loser");
                            }
                            out.flush();
                            running = false;
                        }

                        System.out.println(move);
                        turn = true;
                    }
                    // Send the response to the output stream: server → client

                } catch (IOException e) {
                    running = false;
                    out.println("Disconnected");
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
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
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
