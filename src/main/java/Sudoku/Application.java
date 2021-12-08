package Sudoku;

public class Application {

    public static void main(String[] args) throws Exception {

        SudokuGame theGame = new SudokuGame();

        boolean gameFinished = false;
        while (!gameFinished) {
            gameFinished = theGame.startGame();
        }
        System.out.println("End of the game!");
    }
}