import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int tile = 58, X, Y, newX, newY;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int i = 0; i < 64; i += 2) {
            setVisible(true);
            setSize(900, 900);
            g.setColor(new Color(52, 235, 183));
            g.fillRect((i % 8 + (i / 8) % 2) * tile, (i / 8) * tile, tile, tile);
            g.setColor(new Color(235, 64, 52));
            g.fillRect(((i + 1) % 8 - (i / 8) % 2) * tile, ((i + 1) / 8) * tile, tile, tile);
        }
        Image chessPieceArray = new ImageIcon("ChessPieces.jpg").getImage();

        for (int i = 0; i < 64; i++) {
            int j = -1, k = -1;
            switch (ChessGame.board[i / 8][i % 8]) {
                case "K":
                    j = 0;
                    k = 0;
                    break;
                case "k":
                    j = 0;
                    k = 1;
                    break;
                case "Q":
                    j = 1;
                    k = 0;
                    break;
                case "q":
                    j = 1;
                    k = 1;
                    break;
                case "R":
                    j = 2;
                    k = 0;
                    break;
                case "r":
                    j = 2;
                    k = 1;
                    break;
                case "B":
                    j = 3;
                    k = 0;
                    break;
                case "b":
                    j = 3;
                    k = 1;
                    break;
                case "N":
                    j = 4;
                    k = 0;
                    break;
                case "n":
                    j = 4;
                    k = 1;
                    break;
                case "P":
                    j = 5;
                    k = 0;
                    break;
                case "p":
                    j = 5;
                    k = 1;
                    break;
            }
            if (j != -1 && k != -1) {
                g.drawImage(chessPieceArray, (i % 8) * tile, (i / 8) * tile, (i % 8 + 1) * tile, (i / 8 + 1) * tile,
                        j * 64, k * 64, (j + 1) * 64, (k + 1) * 64, this);
            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 8 * tile && e.getY() < 8 * tile) {

            X = e.getX();
            Y = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() < 8 * tile && e.getY() < 8 * tile) {
            newX = e.getX();
            newY = e.getY();
            String dragpiece;
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (newY / tile == 0 && Y / tile == 1 && "P".equals(ChessGame.board[Y / tile][X / tile])) {
                    dragpiece = "" + X / tile + newX / tile + ChessGame.board[newY / tile][newX / tile] + "Q" + "P";
                } else if (newY / tile == 7 && Y / tile == 6 && "p".equals(ChessGame.board[Y / tile][X / tile])) {
                    dragpiece = "" + X / tile + newX / tile + ChessGame.board[newY / tile][newX / tile] + "q" + "p";
                } else {
                    dragpiece = "" + Y / tile + X / tile + newY / tile + newX / tile
                            + ChessGame.board[newY / tile][newX / tile];
                }

                if (ChessGame.possMoves().replaceAll(dragpiece, " ").length() < ChessGame.possMoves().length()) {
                    ChessGame.movePiece(dragpiece);
                }

            }
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
