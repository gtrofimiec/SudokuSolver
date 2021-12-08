package Sudoku.Layout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardGenerator {

    SudokuBoard board = new SudokuBoard();

    public SudokuBoard boardGenerate(int rowNumber, int @NotNull [] rowValues) {
        for(int i = 0; i < rowValues.length; i++) {
            if(rowValues[i]==0) {
                rowValues[i]=-1;
            }
            board.getSudokuRow().get(rowNumber).getSudokuElementsList().get(i).setValue(rowValues[i]);
        }
        return board;
    }

    public Set<SudokuElement> generateRangeForElement(@NotNull SudokuBoard board, int rowCounter, int columnCounter) {
        Set<SudokuElement> rangeForElement = new HashSet<>();
        board.getSudokuRow().get(rowCounter).getSudokuElementsList().stream()
                .filter(el -> el.getValue() != -1)
                .forEach(rangeForElement::add);
        board.getSudokuRow().stream()
                .map(rows -> rows.getSudokuElementsList().get(columnCounter))
                .filter(el -> el.getValue() != -1)
                .forEach(rangeForElement::add);
        generateRange3x3ToCompare(board,
                rowCounter, columnCounter).stream()
                .filter(el -> el.getValue() != -1)
                .forEach(rangeForElement::add);
        return rangeForElement;
    }

    public List<SudokuElement> generateRange3x3ToCompare(SudokuBoard board, int rowOfCheckedElement, int columnOfCheckedElement) {
        List<SudokuElement> rangeOfElements = new ArrayList();
        List<SudokuRow> rangeOfRows = new ArrayList();

        if(rowOfCheckedElement<=2) {
            rangeOfRows = board.getSudokuRow().stream()
                    .filter(rows -> board.getSudokuRow().indexOf(rows) <= 2)
                    .collect(Collectors.toList());
        } else if (rowOfCheckedElement<=5) {
            rangeOfRows = board.getSudokuRow().stream()
                    .filter(rows -> board.getSudokuRow().indexOf(rows) >= 3 && board.getSudokuRow().indexOf(rows) <= 5)
                    .collect(Collectors.toList());
        }  else if (rowOfCheckedElement<=8) {
            rangeOfRows = board.getSudokuRow().stream()
                    .filter(rows -> board.getSudokuRow().indexOf(rows) >= 6 && board.getSudokuRow().indexOf(rows) <= 8)
                    .collect(Collectors.toList());
        }

        if(columnOfCheckedElement<=2) {
            for(SudokuRow row : rangeOfRows) {
                for(int i=0; i<=2; i++) {
                    if(row.getSudokuElementsList().get(i).getValue()!=-1) {
                        rangeOfElements.add(row.getSudokuElementsList().get(i));
                    }
                }
            }
        } else if(columnOfCheckedElement<=5) {
            for(SudokuRow row : rangeOfRows) {
                for(int i=3; i<=5; i++) {
                    if(row.getSudokuElementsList().get(i).getValue()!=-1) {
                        rangeOfElements.add(row.getSudokuElementsList().get(i));
                    }
                }
            }
        } else if(columnOfCheckedElement<=8) {
            for(SudokuRow row : rangeOfRows) {
                for(int i=6; i<=8; i++) {
                    if(row.getSudokuElementsList().get(i).getValue()!=-1) {
                        rangeOfElements.add(row.getSudokuElementsList().get(i));
                    }
                }
            }
        }
        return rangeOfElements;
    }
}