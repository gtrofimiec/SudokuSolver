package Sudoku.Layout;

import Sudoku.Solver.Prototype;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow extends Prototype<SudokuRow> {

    private List<SudokuElement> sudokuElementsList;

    public SudokuRow() {
        this.sudokuElementsList = List.of(new SudokuElement(), new SudokuElement(), new SudokuElement(),
                new SudokuElement(), new SudokuElement(), new SudokuElement(), new SudokuElement(),
                new SudokuElement(), new SudokuElement());
    }

    public List<SudokuElement> getSudokuElementsList() {
        return sudokuElementsList;
    }

    public SudokuRow clone() throws CloneNotSupportedException {
        SudokuRow clonedRow = super.clone();
        clonedRow.sudokuElementsList = new ArrayList<>();
        for(SudokuElement element : sudokuElementsList) {
            SudokuElement clonedElement = element.clone();
            clonedRow.sudokuElementsList.add(clonedElement);
        }
        return clonedRow;
    }

    @Override
    public String toString() {
        return sudokuElementsList.toString();
    }
}