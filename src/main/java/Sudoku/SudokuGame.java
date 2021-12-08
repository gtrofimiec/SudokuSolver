package Sudoku;

import Sudoku.Layout.BoardGenerator;
import Sudoku.Layout.SudokuBoard;
import Sudoku.Solver.SudokuProcessor;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class SudokuGame {

    public SudokuBoard board = new SudokuBoard();
    Scanner scanner = new Scanner(System.in);
    BoardGenerator boardGenerator = new BoardGenerator();
    SudokuProcessor sudokuProcessor = new SudokuProcessor();
    public boolean sudokuSolved;
    boolean gameFinished;

    public boolean startGame() throws CloneNotSupportedException {
        gameFinished = false;
        System.out.println("Do you want to play? [y]-yes [n]-no");
        String userDecision = scanner.nextLine();
        if(userDecision.equals("y")) {
            System.out.println("Enter values for the following lines with no spaces. Enter 0 for an empty field.");
            boolean verify;
            do {
                verify = false;
                System.out.println("Enter row number [values: 1-9] or type START if board is completed.");
                String rowNumberOrEnd = scanner.nextLine();
                if(rowNumberOrEnd.equals("START")) {
                    verify = true;
                    System.out.println("Sudoku board to solve:\n" + board + "\nType [y] to start solving.");
                    userDecision = scanner.nextLine();
                    if (Objects.equals(userDecision, "y")) {
                        while(!gameFinished) {
                            gameFinished = solveSudoku(board);
                        }
                        System.out.println("Sudoku solved!");
                        return true;
                    } else if (Objects.equals(userDecision, "n")) {
                        return gameFinished = true;
                    }
                } else {
                    try {
                        if (Stream.of(Integer.parseInt(rowNumberOrEnd))
                                .allMatch(val -> List.of(1, 2, 3, 4, 5, 6, 7, 8, 9).contains(val))) {
                            System.out.println("Enter values for row " + rowNumberOrEnd + " (format: 123456789). Enter 0 for an empty field.");
                            userDecision = scanner.nextLine();
                            if (userDecision.chars().allMatch(Character::isDigit) && userDecision.chars().count()==9) {
                                int[] rowValues = new int[9];
                                for (int n = 0; n < rowValues.length; n++) {
                                    rowValues[n] = Character.getNumericValue(userDecision.charAt(n));
                                    board = boardGenerator.boardGenerate(Integer.parseInt(rowNumberOrEnd) - 1, rowValues);
                                }
                                System.out.println(board);
                            } else {
                                System.out.println("No valid values were entered");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("No valid values were entered");
                    }
                }
            } while(!verify);
        } else if(userDecision.equals("n")) {
            System.out.println("End of the game!");
            System.exit(0);
        }
        return gameFinished;
    }

    public boolean solveSudoku(SudokuBoard board) throws CloneNotSupportedException {
        sudokuSolved = sudokuProcessor.completenessVerification(board);
        while (!sudokuSolved) {
            sudokuSolved = sudokuProcessor.sudokuProcess(board);
        }
        System.out.println("Sudoku solved!");
        return startGame();
    }
}