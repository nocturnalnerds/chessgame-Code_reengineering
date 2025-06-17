package after;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int tile = 58;
    static int X, Y, newX, newY;

    private final Image chessPieceArray = new ImageIcon("ChessPieces.jpg").getImage();

    public UserInterface() {
        setPreferredSize(new Dimension(8 * tile, 8 * tile));
        setBackground(Color.BLACK);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawPieces(g);
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i < 64; i += 2) {
            int x1 = (i % 8 + (i / 8) % 2) * tile;
            int y1 = (i / 8) * tile;
            int x2 = ((i + 1) % 8 - (i / 8) % 2) * tile;
            int y2 = ((i + 1) / 8) * tile;

            g.setColor(new Color(52, 235, 183));
            g.fillRect(x1, y1, tile, tile);

            g.setColor(new Color(235, 64, 52));
            g.fillRect(x2, y2, tile, tile);
        }
    }

    private void drawPieces(Graphics g) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String piece = ChessGame.board[row][col];
                if (!piece.equals(" ")) {
                    Point cordinate = getCoordinate(piece);
                    if (cordinate != null) {
                        g.drawImage(
                                chessPieceArray,
                                col * tile, row * tile, (col + 1) * tile, (row + 1) * tile,
                                cordinate.x * 64, cordinate.y * 64, (cordinate.x + 1) * 64, (cordinate.y + 1) * 64,
                                this
                        );
                    }
                }
            }
        }
    }

    private Point getCoordinate(String piece) {
        return switch (piece) {
            case "K" -> new Point(0, 0);
            case "k" -> new Point(0, 1);
            case "Q" -> new Point(1, 0);
            case "q" -> new Point(1, 1);
            case "R" -> new Point(2, 0);
            case "r" -> new Point(2, 1);
            case "B" -> new Point(3, 0);
            case "b" -> new Point(3, 1);
            case "N" -> new Point(4, 0);
            case "n" -> new Point(4, 1);
            case "P" -> new Point(5, 0);
            case "p" -> new Point(5, 1);
            default -> null;
        };
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (inBounds(e.getX(), e.getY())) {
            X = e.getX();
            Y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (inBounds(e.getX(), e.getY()) && e.getButton() == MouseEvent.BUTTON1) {
            newX = e.getX();
            newY = e.getY();
            String move = createMoveCommand();
            if (ChessGame.possMoves().contains(move)) {
                ChessGame.movePiece(move);
            }
            repaint();
        }
    }

    private String createMoveCommand() {
        String piece = ChessGame.board[Y / tile][X / tile];
        String target = ChessGame.board[newY / tile][newX / tile];

        if (piece.equals("P") && newY / tile == 0 && Y / tile == 1) {
            return "" + X / tile + newX / tile + target + "Q" + piece;
        }
        if (piece.equals("p") && newY / tile == 7 && Y / tile == 6) {
            return "" + X / tile + newX / tile + target + "q" + piece;
        }

        return "" + Y / tile + X / tile + newY / tile + newX / tile + target;
    }

    private boolean inBounds(int x, int y) {
        return x < 8 * tile && y < 8 * tile;
    }

    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
}
