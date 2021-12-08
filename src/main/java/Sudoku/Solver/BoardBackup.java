package Sudoku.Solver;

import Sudoku.Layout.SudokuBoard;

public class BoardBackup {

    SudokuBoard savedBoard;
    int rowOfCheckedElement;
    int columnOfCheckedElement;
    int guessingValueOfElement;

    public BoardBackup(SudokuBoard savedBoard, int rowOfCheckedElement, int columnOfCheckedElement,
                       int guessingValueOfElement) {
        this.savedBoard = savedBoard;
        this.rowOfCheckedElement = rowOfCheckedElement;
        this.columnOfCheckedElement = columnOfCheckedElement;
        this.guessingValueOfElement = guessingValueOfElement;
    }

    public SudokuBoard getSavedBoard() {
        return savedBoard;
    }

    public int getRowOfCheckedElement() {
        return rowOfCheckedElement;
    }

    public int getColumnOfCheckedElement() {
        return columnOfCheckedElement;
    }

    public int getGuessingValueOfElement() {
        return guessingValueOfElement;
    }
}