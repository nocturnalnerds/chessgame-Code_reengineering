import javax.swing.*;
import java.util.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ChessGame {
    static String board[][] = {
            { "r", "n", "b", "q", "k", "b", "n", "r" },
            { "p", "p", "p", "p", "p", "p", "p", "p" },
            { " ", " ", " ", " ", " ", " ", " ", " " },
            { " ", " ", " ", " ", " ", " ", " ", " " },
            { " ", " ", " ", " ", " ", " ", " ", " " },
            { " ", " ", " ", " ", " ", " ", " ", " " },
            { "P", "P", "P", "P", "P", "P", "P", "P" },
            { "R", "N", "B", "Q", "K", "B", "N", "R" }
    };

    static int whiteKing, blackKing, moves;

    public static void main(String[] args) {
        UserInterface gui = new UserInterface();

        JFrame f = new JFrame("James Calcagni   Chess Project   CS-111");
        f.add(gui);
        f.setSize(475, 500);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (!"K".equals(board[whiteKing / 8][whiteKing % 8])) {
            whiteKing++;
        }
        while (!"k".equals(board[blackKing / 8][blackKing % 8])) {
            blackKing++;
        }
    }

    private static void moveMade() {
        moves++;
    }

    public static void movePiece(String move) {
        if (moves % 2 != 0) {
            if (move.charAt(4) != 'p') {
                board[Character.getNumericValue(move.charAt(2))][Character
                        .getNumericValue(move.charAt(3))] = board[Character.getNumericValue(move.charAt(0))][Character
                                .getNumericValue(move.charAt(1))];
                board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
                if ("k".equals(
                        board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
                    blackKing = 8 * Character.getNumericValue(move.charAt(0))
                            + Character.getNumericValue(move.charAt(1));
                }
            } else {
                board[6][Character.getNumericValue(move.charAt(0))] = " ";
                board[7][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(3));
            }
        } else {
            if (move.charAt(4) != 'P') {
                board[Character.getNumericValue(move.charAt(2))][Character
                        .getNumericValue(move.charAt(3))] = board[Character.getNumericValue(move.charAt(0))][Character
                                .getNumericValue(move.charAt(1))];
                board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
                if ("K".equals(
                        board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
                    whiteKing = 8 * Character.getNumericValue(move.charAt(0))
                            + Character.getNumericValue(move.charAt(1));
                }
            } else {
                board[1][Character.getNumericValue(move.charAt(0))] = " ";
                board[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(3));
            }
        }
        moveMade();

    }

    public static String possMoves() {
        String out = " ";
        for (int i = 0; i < 64; i++) {
            if (moves % 2 == 0) {
                switch (board[i / 8][i % 8]) {

                    case "P":
                        out += possWhitePawn(i);
                        break;
                    case "K":
                        out += possWhiteKing(i);
                        break;
                    case "Q":
                        out += possWhiteQueen(i);
                        break;
                    case "N":
                        out += possWhiteKnight(i);
                        break;
                    case "R":
                        out += possWhiteRook(i);
                        break;
                    case "B":
                        out += possWhiteBishop(i);
                        break;
                }
            } else {
                switch (board[i / 8][i % 8]) {
                    case "p":
                        out += possBlackPawn(i);
                        break;
                    case "n":
                        out += possBlackKnight(i);
                        break;
                    case "r":
                        out += possBlackRook(i);
                        break;
                    case "k":
                        out += possBlackKing(i);
                        break;
                    case "q":
                        out += possBlackQueen(i);
                        break;
                    case "b":
                        out += possBlackBishop(i);
                        break;
                }
            }
        }
        return out;
    }

    public static String possWhitePawn(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8;
        try {
            for (int j = -1; j <= 1; j += 2) {
                if (Character.isLowerCase(board[row - 1][col + j].charAt(0)) && i >= 16) {
                    previous = board[row - 1][col + j];
                    board[row][col] = " ";
                    board[row - 1][col + j] = "P";
                    if (noWhiteKingThreat() == true) {
                        out = out + row + col + (row - 1) + (col + j) + previous;
                    }
                    board[row][col] = "P";
                    board[row - 1][col + j] = previous;
                }
            }
        } catch (Exception e) {
        }

        try {
            if (" ".equals(board[row - 1][col]) && i >= 16) {
                previous = board[row - 1][col];
                board[row][col] = " ";
                board[row - 1][col] = "P";
                if (noWhiteKingThreat() == true) {
                    out = out + row + col + (row - 1) + col + previous;
                }
                board[row][col] = "P";
                board[row - 1][col] = previous;
            }
        } catch (Exception e) {
        }
        try {
            if (" ".equals(board[row - 1][col]) && " ".equals(board[row - 2][col]) && i >= 48) {
                previous = board[row - 2][col];
                board[row][col] = " ";
                board[row - 2][col] = "P";
                if (noWhiteKingThreat() == true) {
                    out = out + row + col + (row - 2) + col + previous;
                }
                board[row][col] = "P";
                board[row - 2][col] = previous;
            }
        } catch (Exception e) {
        }
        try {
            if (" ".equals(board[row - 1][col]) && i < 16) {
                previous = board[row - 1][col];
                board[row][col] = "";
                board[row - 1][col] = "Q";
                if (noWhiteKingThreat() == true) {
                    out = out + col + col + previous + "Q" + "P";
                }
                board[row][col] = "P";
                board[row - 1][col] = previous;
            }
        } catch (Exception e) {
        }

        try {
            for (int j = -1; j <= 1; j += 2) {
                if (Character.isLowerCase(board[row - 1][col + j].charAt(0)) && i < 16) {
                    previous = board[row - 1][col + j];
                    board[row][col] = "";
                    board[row - 1][col + j] = "Q";
                    if (noWhiteKingThreat() == true) {
                        out = out + col + (col + j) + previous + "Q" + "P";
                    }
                    board[row][col] = "P";
                    board[row - 1][col + j] = previous;
                }
            }
        } catch (Exception e) {
        }

        try {
            int j = 1;
            if (Character.isLowerCase(board[row - 1][col + j].charAt(0)) && i == 8) {
                previous = board[row][col];
                board[row][col] = " ";
                board[0][col + j] = "Q";
                if (noWhiteKingThreat() == true) {
                    out = out + "0" + "1" + previous + "Q" + "P";
                }
                board[row][col] = "P";
                board[0][col + j] = previous;
            }
        } catch (Exception e) {
        }
        return out;
    }

    public static String possBlackPawn(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8;
        try {
            for (int j = -1; j <= 1; j += 2) {
                if (Character.isUpperCase(board[row + 1][col - j].charAt(0)) && i <= 47) {
                    previous = board[row + 1][col - j];
                    board[row][col] = " ";
                    board[row + 1][col - j] = "p";
                    if (noBlackKingThreat() == true) {
                        out = out + row + col + (row + 1) + (col - j) + previous;
                    }
                    board[row][col] = "p";
                    board[row + 1][col - j] = previous;
                }
            }
        } catch (Exception e) {
        }

        try {
            if (" ".equals(board[row + 1][col]) && i <= 47) {
                previous = board[row + 1][col];
                board[row][col] = " ";
                board[row + 1][col] = "p";
                if (noBlackKingThreat() == true) {
                    out = out + row + col + (row + 1) + col + previous;
                }
                board[row][col] = "p";
                board[row + 1][col] = previous;
            }
        } catch (Exception e) {
        }

        try {
            if (" ".equals(board[row + 1][col]) && " ".equals(board[row + 2][col]) && i <= 15) {
                previous = board[row + 2][col];
                board[row][col] = " ";
                board[row + 2][col] = "p";
                if (noBlackKingThreat() == true) {
                    out = out + row + col + (row + 2) + col + previous;
                }
                board[row][col] = "p";
                board[row + 2][col] = previous;
            }
        } catch (Exception e) {
        }
        try {
            if (" ".equals(board[7][col]) && i >= 47) {
                previous = board[7][col];
                board[row][col] = " ";
                board[7][col] = "q";
                if (noBlackKingThreat() == true) {
                    out = out + col + col + previous + "q" + "p";
                }
                board[row][col] = "p";
                board[7][col] = previous;
            }
        } catch (Exception e) {
        }

        try {
            for (int j = -1; j <= 1; j += 2) {
                if (Character.isUpperCase(board[7][col + j].charAt(0)) && i >= 47) {
                    previous = board[7][col + j];
                    board[row][col] = " ";
                    board[row + 1][col + j] = "q";
                    if (noBlackKingThreat() == true) {
                        out = out + col + (col + j) + previous + "q" + "p";
                    }
                    board[row][col] = "p";
                    board[row + 1][col + j] = previous;
                }
            }
        } catch (Exception e) {
        }
        try {
            int j = 1;
            if (Character.isUpperCase(board[row + 1][col + j].charAt(0)) && i == 48) {
                previous = board[row + 1][1];
                board[row][col] = " ";
                board[row + 1][col + j] = "p";
                if (noBlackKingThreat() == true) {
                    out = out + "0" + "1" + previous + "q" + "p";
                }
                board[row][col] = "p";
                board[row + 1][col + j] = previous;
            }
        } catch (Exception e) {
        }
        return out;
    }

    public static String possWhiteKnight(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8;
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                try {
                    if (Character.isLowerCase(board[row + j][col + k * 2].charAt(0))
                            || " ".equals(board[row + j][col + k * 2])) {
                        previous = board[row + j][col + k * 2];
                        board[row][col] = " ";
                        board[row + j][col + k * 2] = "N";
                        if (noWhiteKingThreat() == true) {
                            out = out + row + col + (row + j) + (col + k * 2) + previous;
                        }
                        board[row][col] = "N";
                        board[row + j][col + k * 2] = previous;
                    }
                } catch (Exception e) {
                }
                try {
                    if (Character.isLowerCase(board[row + j * 2][col + k].charAt(0))
                            || " ".equals(board[row + j * 2][col + k])) {
                        previous = board[row + j * 2][col + k];
                        board[row][col] = " ";
                        board[row + j * 2][col + k] = "N";
                        if (noWhiteKingThreat() == true) {
                            out = out + row + col + (row + j * 2) + (col + k) + previous;
                        }
                        board[row][col] = "N";
                        board[row + j * 2][col + k] = previous;
                    }
                } catch (Exception e) {
                }

            }
        }
        return out;
    }

    public static String possBlackKnight(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8;
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                try {
                    if (Character.isUpperCase(board[row + j][col + k * 2].charAt(0))
                            || " ".equals(board[row + j][col + k * 2])) {
                        previous = board[row + j][col + k * 2];
                        board[row][col] = " ";
                        board[row + j][col + k * 2] = "n";
                        if (noBlackKingThreat() == true) {
                            out = out + row + col + (row + j) + (col + k * 2) + previous;
                        }
                        board[row][col] = "n";
                        board[row + j][col + k * 2] = previous;
                    }
                } catch (Exception e) {
                }
                try {
                    if (Character.isUpperCase(board[row + j * 2][col + k].charAt(0))
                            || " ".equals(board[row + j * 2][col + k])) {
                        previous = board[row + j * 2][col + k];
                        board[row][col] = " ";
                        board[row + j * 2][col + k] = "n";
                        if (noBlackKingThreat() == true) {
                            out = out + row + col + (row + j * 2) + (col + k) + previous;
                        }
                        board[row][col] = "n";
                        board[row + j * 2][col + k] = previous;
                    }
                } catch (Exception e) {
                }
            }
        }
        return out;
    }

    public static String possWhiteRook(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8, foo = 1;
        for (int j = -1; j <= 1; j += 2) {
            try {
                while (" ".equals(board[row][col + foo * j])) {
                    previous = board[row][col + foo * j];
                    board[row][col] = " ";
                    board[row][col + foo * j] = "R";
                    if (noWhiteKingThreat() == true) {
                        out = out + row + col + row + (col + foo * j) + previous;
                    }
                    board[row][col] = "R";
                    board[row][col + foo * j] = previous;
                    foo++;
                }
                if (Character.isLowerCase(board[row][col + foo * j].charAt(0))) {
                    previous = board[row][col + foo * j];
                    board[row][col] = " ";
                    board[row][col + foo * j] = "R";
                    if (noWhiteKingThreat() == true) {
                        out = out + row + col + row + (col + foo * j) + previous;
                    }
                    board[row][col] = "R";
                    board[row][col + foo * j] = previous;
                }
            } catch (Exception e) {
            }
            foo = 1;
            try {
                while (" ".equals(board[row + foo * j][col])) {
                    previous = board[row + foo * j][col];
                    board[row][col] = " ";
                    board[row + foo * j][col] = "R";
                    if (noWhiteKingThreat() == true) {
                        out = out + row + col + (row + foo * j) + col + previous;
                    }
                    board[row][col] = "R";
                    board[row + foo * j][col] = previous;
                    foo++;
                }
                if (Character.isLowerCase(board[row + foo * j][col].charAt(0))) {
                    previous = board[row + foo * j][col];
                    board[row][col] = " ";
                    board[row + foo * j][col] = "R";
                    if (noWhiteKingThreat() == true) {
                        out = out + row + col + (row + foo * j) + col + previous;
                    }
                    board[row][col] = "R";
                    board[row + foo * j][col] = previous;
                }
            } catch (Exception e) {
            }
            foo = 1;
        }
        return out;
    }

    public static String possBlackRook(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8, foo = 1;
        for (int j = -1; j <= 1; j += 2) {
            try {
                while (" ".equals(board[row][col + foo * j])) {
                    previous = board[row][col + foo * j];
                    board[row][col] = " ";
                    board[row][col + foo * j] = "r";
                    if (noBlackKingThreat() == true) {
                        out = out + row + col + row + (col + foo * j) + previous;
                    }
                    board[row][col] = "r";
                    board[row][col + foo * j] = previous;
                    foo++;
                }
                if (Character.isUpperCase(board[row][col + foo * j].charAt(0))) {
                    previous = board[row][col + foo * j];
                    board[row][col] = " ";
                    board[row][col + foo * j] = "r";
                    if (noBlackKingThreat() == true) {
                        out = out + row + col + row + (col + foo * j) + previous;
                    }
                    board[row][col] = "r";
                    board[row][col + foo * j] = previous;
                }
            } catch (Exception e) {
            }
            foo = 1;
            try {
                while (" ".equals(board[row + foo * j][col])) {
                    previous = board[row + foo * j][col];
                    board[row][col] = " ";
                    board[row + foo * j][col] = "r";
                    if (noBlackKingThreat() == true) {
                        out = out + row + col + (row + foo * j) + col + previous;
                    }
                    board[row][col] = "r";
                    board[row + foo * j][col] = previous;
                    foo++;
                }
                if (Character.isUpperCase(board[row + foo * j][col].charAt(0))) {
                    previous = board[row + foo * j][col];
                    board[row][col] = " ";
                    board[row + foo * j][col] = "r";
                    if (noBlackKingThreat() == true) {
                        out = out + row + col + (row + foo * j) + col + previous;
                    }
                    board[row][col] = "r";
                    board[row + foo * j][col] = previous;
                }
            } catch (Exception e) {
            }
        }
        return out;
    }

    public static String possWhiteBishop(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8, foo = 1;
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                if (j != 0 && k != 0) {
                    try {
                        while (" ".equals(board[row + foo * j][col + foo * k])) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "B";
                            if (noWhiteKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "B";
                            board[row + foo * j][col + foo * k] = previous;
                            foo++;
                        }
                        if (Character.isLowerCase(board[row + foo * j][col + foo * k].charAt(0))) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "B";
                            if (noWhiteKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "B";
                            board[row + foo * j][col + foo * k] = previous;
                        }
                    } catch (Exception e) {
                    }
                    foo = 1;
                }
            }
        }
        return out;
    }

    public static String possBlackBishop(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8, foo = 1;
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                if (j != 0 && k != 0) {
                    try {
                        while (" ".equals(board[row + foo * j][col + foo * k])) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "b";
                            if (noBlackKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "b";
                            board[row + foo * j][col + foo * k] = previous;
                            foo++;
                        }
                        if (Character.isUpperCase(board[row + foo * j][col + foo * k].charAt(0))) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "b";
                            if (noBlackKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "b";
                            board[row + foo * j][col + foo * k] = previous;
                        }
                    } catch (Exception e) {
                    }
                    foo = 1;
                }
            }
        }
        return out;
    }

    public static String possWhiteQueen(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8, foo = 1;
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if (j != 0 || k != 0) {
                    try {
                        while (" ".equals(board[row + foo * j][col + foo * k])) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "Q";
                            if (noWhiteKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "Q";
                            board[row + foo * j][col + foo * k] = previous;
                            foo++;
                        }
                        if (Character.isLowerCase(board[row + foo * j][col + foo * k].charAt(0))) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "Q";
                            if (noWhiteKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "Q";
                            board[row + foo * j][col + foo * k] = previous;
                        }
                    } catch (Exception e) {
                    }
                    foo = 1;
                }
            }
        }
        return out;
    }

    public static String possBlackQueen(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8, foo = 1;
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if (j != 0 || k != 0) {
                    try {
                        while (" ".equals(board[row + foo * j][col + foo * k])) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "q";
                            if (noBlackKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "q";
                            board[row + foo * j][col + foo * k] = previous;
                            foo++;
                        }
                        if (Character.isUpperCase(board[row + foo * j][col + foo * k].charAt(0))) {
                            previous = board[row + foo * j][col + foo * k];
                            board[row][col] = " ";
                            board[row + foo * j][col + foo * k] = "q";
                            if (noBlackKingThreat() == true) {
                                out = out + row + col + (row + foo * j) + (col + foo * k) + previous;
                            }
                            board[row][col] = "q";
                            board[row + foo * j][col + foo * k] = previous;
                        }
                    } catch (Exception e) {
                    }
                    foo = 1;
                }
            }
        }
        return out;
    }

    public static String possWhiteKing(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8;
        for (int j = 0; j < 9; j++) {
            if (j != 4) {
                try {
                    if (Character.isLowerCase(board[row - 1 + j / 3][col - 1 + j % 3].charAt(0))
                            || " ".equals(board[row - 1 + j / 3][col - 1 + j % 3])) {
                        previous = board[row - 1 + j / 3][col - 1 + j % 3];
                        board[row][col] = " ";
                        board[row - 1 + j / 3][col - 1 + j % 3] = "K";
                        int kingFoo = whiteKing;
                        whiteKing = i + (j / 3) * 8 + j % 3 - 9;
                        if (noWhiteKingThreat() == true) {
                            out = out + row + col + (row - 1 + j / 3) + (col - 1 + j % 3) + previous;
                        }
                        board[row][col] = "K";
                        board[row - 1 + j / 3][col - 1 + j % 3] = previous;
                        whiteKing = kingFoo;
                    }

                } catch (Exception e) {
                }
            }

        }
        return out;
    }

    public static String possBlackKing(int i) {
        String out = " ", previous;
        int row = i / 8, col = i % 8;
        for (int j = 0; j < 9; j++) {
            if (j != 4) {
                try {
                    if (Character.isUpperCase(board[row - 1 + j / 3][col - 1 + j % 3].charAt(0))
                            || " ".equals(board[row - 1 + j / 3][col - 1 + j % 3])) {
                        previous = board[row - 1 + j / 3][col - 1 + j % 3];
                        board[row][col] = " ";
                        board[row - 1 + j / 3][col - 1 + j % 3] = "k";
                        int kingFoo = blackKing;
                        blackKing = i + (j / 3) * 8 + j % 3 - 9;
                        if (noBlackKingThreat() == true) {
                            out = out + row + col + (row - 1 + j / 3) + (col - 1 + j % 3) + previous;
                        }
                        board[row][col] = "k";
                        board[row - 1 + j / 3][col - 1 + j % 3] = previous;
                        blackKing = kingFoo;
                    }

                } catch (Exception e) {
                }
            }

        }
        return out;
    }

    public static boolean noWhiteKingThreat() {

        int foo = 1;
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                try {
                    while (" ".equals(board[whiteKing / 8 + foo * i][whiteKing % 8 + foo * j])) {
                        foo++;
                    }
                    if ("b".equals(board[whiteKing / 8 + foo * i][whiteKing % 8 + foo * j])
                            || "q".equals(board[whiteKing / 8 + foo * i][whiteKing % 8 + foo * j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                foo = 1;
            }
        }

        for (int i = -1; i <= 1; i += 2) {
            try {
                while (" ".equals(board[whiteKing / 8][whiteKing % 8 + foo * i])) {
                    foo++;
                }
                if ("r".equals(board[whiteKing / 8][whiteKing % 8 + foo * i])
                        || "q".equals(board[whiteKing / 8][whiteKing % 8 + foo * i])) {
                    return false;
                }
            } catch (Exception e) {
            }
            foo = 1;
            try {
                while (" ".equals(board[whiteKing / 8 + foo * i][whiteKing % 8])) {
                    foo++;
                }
                if ("r".equals(board[whiteKing / 8 + foo * i][whiteKing % 8])
                        || "q".equals(board[whiteKing / 8 + foo * i][whiteKing % 8])) {
                    return false;
                }
            } catch (Exception e) {
            }
            foo = 1;
        }

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                try {
                    if ("n".equals(board[whiteKing / 8 + i][whiteKing % 8 + j * 2])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                try {
                    if ("n".equals(board[whiteKing / 8 + i * 2][whiteKing % 8 + j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
            }
        }

        if (whiteKing >= 16) {
            try {
                if ("p".equals(board[whiteKing / 8 - 1][whiteKing % 8 - 1])) {
                    return false;
                }
            } catch (Exception e) {
            }
            try {
                if ("p".equals(board[whiteKing / 8 - 1][whiteKing % 8 + 1])) {
                    return false;
                }
            } catch (Exception e) {
            }
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    try {
                        if ("k".equals(board[whiteKing / 8 + i][whiteKing % 8 + j])) {
                            return false;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return true;
    }

    public static boolean noBlackKingThreat() {

        int foo = 1;
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                try {
                    while (" ".equals(board[blackKing / 8 + foo * i][blackKing % 8 + foo * j])) {
                        foo++;
                    }
                    if ("B".equals(board[blackKing / 8 + foo * i][blackKing % 8 + foo * j])
                            || "Q".equals(board[blackKing / 8 + foo * i][blackKing % 8 + foo * j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                foo = 1;
            }
        }

        for (int i = -1; i <= 1; i += 2) {
            try {
                while (" ".equals(board[blackKing / 8][blackKing % 8 + foo * i])) {
                    foo++;
                }
                if ("R".equals(board[blackKing / 8][blackKing % 8 + foo * i])
                        || "Q".equals(board[blackKing / 8][blackKing % 8 + foo * i])) {
                    return false;
                }
            } catch (Exception e) {
            }
            foo = 1;
            try {
                while (" ".equals(board[blackKing / 8 + foo * i][blackKing % 8])) {
                    foo++;
                }
                if ("R".equals(board[blackKing / 8 + foo * i][blackKing % 8])
                        || "Q".equals(board[blackKing / 8 + foo * i][blackKing % 8])) {
                    return false;
                }
            } catch (Exception e) {
            }
            foo = 1;
        }

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                try {
                    if ("N".equals(board[blackKing / 8 + i][blackKing % 8 + j * 2])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                try {
                    if ("N".equals(board[blackKing / 8 + i * 2][blackKing % 8 + j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
            }
        }

        if (blackKing < 48) {
            try {
                if ("P".equals(board[blackKing / 8 + 1][blackKing % 8 + 1])) {
                    return false;
                }
            } catch (Exception e) {
            }
            try {
                if ("P".equals(board[blackKing / 8 + 1][blackKing % 8 - 1])) {
                    return false;
                }
            } catch (Exception e) {
            }
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    try {
                        if ("K".equals(board[blackKing / 8 + i][blackKing % 8 + j])) {
                            return false;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return true;
    }
}
