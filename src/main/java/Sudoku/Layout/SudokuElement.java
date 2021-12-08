package Sudoku.Layout;

import Sudoku.Solver.Prototype;

import java.util.List;
import java.util.Objects;

public final class SudokuElement extends Prototype<SudokuElement> {

    private int value;
    private static final int EMPTY = -1;
    private List<Integer> possibleValues;

    public SudokuElement() {
        this.value = EMPTY;
        this.possibleValues = List.of(1,2,3,4,5,6,7,8,9);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuElement that = (SudokuElement) o;

        if (value != that.value) return false;
        return Objects.equals(possibleValues, that.possibleValues);
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (possibleValues != null ? possibleValues.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String val;
        if(value == EMPTY){
            val = " ";
        } else {
            val = String.valueOf(value);
        }
        return val;
    }

    public SudokuElement clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        this.value = val;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(List<Integer> possibleValues) {
        this.possibleValues = possibleValues;
    }
}