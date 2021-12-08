package Sudoku.Solver;

import Sudoku.Layout.BoardGenerator;
import Sudoku.Layout.SudokuBoard;
import Sudoku.Layout.SudokuElement;
import Sudoku.Layout.SudokuRow;
import Sudoku.SudokuGame;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class SudokuProcessor {

    List<BoardBackup> backtrack = new ArrayList<>();
    BoardGenerator boardGenerator = new BoardGenerator();
    int rowCounter;
    int columnCounter;
    public boolean newValueEnteredToBoard;
    public boolean isComplete;

    public boolean sudokuProcess(@NotNull SudokuBoard board) throws CloneNotSupportedException {
        do {
            newValueEnteredToBoard = false;
            rowCounter = 0;
            for (SudokuRow row : board.getSudokuRow()) {
                rowCounter++;
                columnCounter = 0;
                for (SudokuElement element : row.getSudokuElementsList()) {
                    columnCounter++;
                    if (element.getValue() == -1) {
                        solvingProcess(board, row, element, rowCounter, columnCounter);
                    }
                }
            }
        } while (newValueEnteredToBoard);
        if(completenessVerification(board)) {
            SudokuGame sudokuGame = new SudokuGame();
            System.out.println(board);
            return sudokuGame.solveSudoku(board);
        } else {
            tryingToGuessValues(board);
        }
        return isComplete;
    }

    public boolean completenessVerification(@NotNull SudokuBoard board) {
        return board.getSudokuRow().stream()
                .flatMap(row -> row.getSudokuElementsList().stream())
                .noneMatch(el -> el.getValue() == -1);
    }

    void solvingProcess(SudokuBoard board, @NotNull SudokuRow row, SudokuElement element, int rowCounter,
                        int columnCounter) throws CloneNotSupportedException {

        List<SudokuElement> valuesInRow = row.getSudokuElementsList().stream()
                .filter(el->el.getValue() != -1)
                .collect(Collectors.toList());
        eliminationFromPossibleValuesList(board, valuesInRow, element);

        List<SudokuElement> valuesInColumn = board.getSudokuRow().stream()
                .map(rows -> rows.getSudokuElementsList().get(columnCounter - 1))
                .filter(el -> el.getValue() != -1)
                .collect(Collectors.toList());
        eliminationFromPossibleValuesList(board, valuesInColumn, element);

        List<SudokuElement> valuesInRange3x3 = boardGenerator.generateRange3x3ToCompare(board,
                rowCounter - 1, columnCounter - 1);
        eliminationFromPossibleValuesList(board, valuesInRange3x3, element);

        Set<Integer> valuesInCumulatedRange = new HashSet<>();

        valuesInRow.stream()
                .filter(el -> el.getValue() == -1)
                .flatMap(el -> el.getPossibleValues().stream())
                .forEach(valuesInCumulatedRange::add);
        valuesInColumn.stream()
                .filter(el -> el.getValue() == -1)
                .flatMap(el -> el.getPossibleValues().stream())
                .forEach(valuesInCumulatedRange::add);
        valuesInRange3x3.stream()
                .filter(el -> el.getValue() == -1)
                .flatMap(el -> el.getPossibleValues().stream())
                .forEach(valuesInCumulatedRange::add);

        List<Integer> list = element.getPossibleValues().stream()
                .filter(val -> !valuesInCumulatedRange.contains(val))
                .collect(Collectors.toList());

        if(list.size() == 1) {
            settingNewValueToBoard(board, element, list.get(0));
        }
    }

    void eliminationFromPossibleValuesList(SudokuBoard board, @NotNull List<SudokuElement> listElementsToCompare,
                                           @NotNull SudokuElement comparedElement) throws CloneNotSupportedException {

        List<Integer> valuesEnteredInRange = listElementsToCompare.stream()
                .map(SudokuElement::getValue)
                .filter(val -> val != -1)
                .collect(Collectors.toList());

        List<Integer> newListOfPossibleValues = comparedElement.getPossibleValues().stream()
                .filter(val -> !valuesEnteredInRange.contains(val))
                .collect(Collectors.toList());
        comparedElement.setPossibleValues(newListOfPossibleValues);

        if(comparedElement.getPossibleValues().size() == 1) {
            settingNewValueToBoard(board, comparedElement, comparedElement.getPossibleValues().get(0));
        }
    }

    void settingNewValueToBoard(SudokuBoard board, SudokuElement element, int possibleValue) throws CloneNotSupportedException {

        Set<SudokuElement> rangeOfElement = boardGenerator.generateRangeForElement(board, rowCounter - 1, columnCounter - 1);
        boolean notExistInRange = rangeOfElement.stream()
                .noneMatch(val -> val.getValue() == element.getPossibleValues().get(0));
        if(notExistInRange) {
            element.setValue(possibleValue);
            newValueEnteredToBoard = true;

            List<Integer> possibleValuesList = element.getPossibleValues().stream()
                    .filter(value -> value != possibleValue)
                    .collect(Collectors.toList());
            element.setPossibleValues(possibleValuesList);
        } else {
            backupRecovery();
        }
    }

    private void tryingToGuessValues(@NotNull SudokuBoard board) throws CloneNotSupportedException {
        rowCounter = 0;
        for (SudokuRow row : board.getSudokuRow()) {
            rowCounter++;
            columnCounter = 0;
            for (SudokuElement element : row.getSudokuElementsList()) {
                columnCounter++;
                if (element.getValue() == -1) {
                    if (element.getPossibleValues().size() > 0) {
                        int firstPossibleValue = element.getPossibleValues().get(0);
                        try {
                            SudokuBoard clonedBoard = board.deepCopy();
                            backtrack.add(new BoardBackup(clonedBoard, rowCounter - 1,
                                    columnCounter - 1, firstPossibleValue));
                        } catch (CloneNotSupportedException e) {
                            System.out.println("Unable to create backup" + e);
                        }
                        settingNewValueToBoard(board, element, firstPossibleValue);
                        sudokuProcess(board);
                    } else {
                        if (!backtrack.isEmpty()) {
                            backupRecovery();
                        } else {
                            System.out.println("Sudoku is not correct!");
                        }
                    }
                }
            }
        }
    }

    void backupRecovery() throws CloneNotSupportedException {
        BoardBackup wrongMove = backtrack.get(backtrack.size() - 1);
        backtrack.remove(backtrack.size()-1);
        SudokuBoard sudokuBoard = wrongMove.getSavedBoard();

        int rowOfWrongElement = wrongMove.getRowOfCheckedElement();
        int columnOfWrongElement = wrongMove.getColumnOfCheckedElement();
        SudokuElement wrongElement = sudokuBoard.getSudokuRow().get(rowOfWrongElement).getSudokuElementsList()
                .get(columnOfWrongElement);
        List<Integer> possibleValuesList = sudokuBoard.getSudokuRow().get(rowOfWrongElement).getSudokuElementsList()
                .get(columnOfWrongElement).getPossibleValues();

        List<Integer> newPossibleValuesList = possibleValuesList.stream()
                .filter(val -> val != wrongMove.getGuessingValueOfElement())
                .collect(Collectors.toList());
        wrongElement.setPossibleValues(newPossibleValuesList);
        wrongElement.setValue(-1);
        tryingToGuessValues(sudokuBoard);
    }
}