import java.awt.*;
import java.awt.event.*;

public class TicTacToeAWT {
    int boardWidth = 600;
    int boardHeight = 650; // 50px for the text panel on top

    Frame frame = new Frame("Tic-Tac-Toe");
    Label textLabel = new Label();
    Panel textPanel = new Panel();
    Panel boardPanel = new Panel();

    Button[][] board = new Button[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    TicTacToeAWT() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setAlignment(Label.CENTER);
        textLabel.setText("Tic-Tac-Toe");

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button tile = new Button();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        Button tile = (Button) e.getSource();
                        if (tile.getLabel().equals("")) {
                            tile.setLabel(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }
    }

    void checkWinner() {
        // horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getLabel().equals("")) continue;

            if (board[r][0].getLabel().equals(board[r][1].getLabel()) &&
                board[r][1].getLabel().equals(board[r][2].getLabel())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        // vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getLabel().equals("")) continue;

            if (board[0][c].getLabel().equals(board[1][c].getLabel()) &&
                board[1][c].getLabel().equals(board[2][c].getLabel())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        // diagonally
        if (board[0][0].getLabel().equals(board[1][1].getLabel()) &&
            board[1][1].getLabel().equals(board[2][2].getLabel()) &&
            !board[0][0].getLabel().equals("")) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        // anti-diagonally
        if (board[0][2].getLabel().equals(board[1][1].getLabel()) &&
            board[1][1].getLabel().equals(board[2][0].getLabel()) &&
            !board[0][2].getLabel().equals("")) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(Button tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void setTie(Button tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new TicTacToeAWT();
        });
    }
}