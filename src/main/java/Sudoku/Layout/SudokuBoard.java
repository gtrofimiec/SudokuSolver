package Sudoku.Layout;

import Sudoku.Solver.Prototype;

import java.util.ArrayList;
import java.util.List;

public final class SudokuBoard extends Prototype<SudokuBoard> {

    private List<SudokuRow> sudokuRow;

    public SudokuBoard() {
        this.sudokuRow = List.of(new SudokuRow(), new SudokuRow(), new SudokuRow(), new SudokuRow(), new SudokuRow()
                , new SudokuRow(), new SudokuRow(), new SudokuRow(), new SudokuRow());
    }

    public List<SudokuRow> getSudokuRow() {
        return sudokuRow;
    }

    public SudokuBoard deepCopy() throws CloneNotSupportedException {
        SudokuBoard clonedBoard = super.clone();
        clonedBoard.sudokuRow = new ArrayList<>();
        for(SudokuRow row : this.sudokuRow) {
            SudokuRow clonedRow = row.clone();
            clonedBoard.sudokuRow.add(clonedRow);
        }
        return clonedBoard;
    }

    @Override
    public String toString() {
        StringBuilder sudokuBoard = new StringBuilder("Current board");
        for(SudokuRow row : sudokuRow)
            sudokuBoard.append("\n").append(row);
        return sudokuBoard.toString().replace(",","|");
    }
}