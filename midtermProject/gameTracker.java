/**
 * This class keeps track of a minesweeper game and
 * allows the user to play a traditional game
 *
 * I received help from Jordan Wade on my zeroClick() and on my clickFunction()
 *  Womack helped me debug getFlaggingStatus and getgameOver()
 */

package edu.angelo.midtermprojectbarron;

import java.util.Random;

public class gameTracker {

    /**
     * number of rows for the game board
     */
    private final int NUM_ROWS = 8;
    /**
     * number of columns for the game board
     */
    private final int NUM_COLUMNS = 8;
    /**
     * number of randomly generated mines around the game board
     */
    private final int NUM_MINES= 10;
    /**
     * Random object for random number generation
     */
    private static final Random randGen = new Random();
    /**
     * will become true when the player has clicked a mine ans lost
     */
    private boolean gameOver = false;
    /**
     * will become true when the player meets the win condition
     */
    private boolean hasWon = false;
    /**
     * becomes true or false to toggle the flag placing mode
     */
    private boolean flaggingMode = false;

    /**
     * acts as the "main" game board with numbers and mines
     */
    public int[][] mineField;
    /**
     * an array that keeps track of mines exclusively from the mineField array
     */
    public int[][] whereTheMinesAre;
    /**
     * acts as a "cover" for the mineField array and allows for spaces to be revealed
     */
    public boolean[][] hasBeenRevealed;
    /**
     * an array that keeps track of where the player has placed flags
     */
    public int[][] whereFlagsAre;
    /**
     * Default and only contructor that will initalize
     * the game board according to NUM_ROWS and NUM_COLUMNS.
     *
     * Will create a fresh mineField array.
     */
    public gameTracker(){
        this.mineField = new int[NUM_ROWS][NUM_COLUMNS];
        this.whereTheMinesAre = new int[NUM_ROWS][NUM_COLUMNS];
        this.hasBeenRevealed = new boolean[NUM_ROWS][NUM_COLUMNS];
        this.whereFlagsAre = new int[NUM_ROWS][NUM_COLUMNS];
        createNewMineField();
    }

    /**
     * Initliazes mineField and whereTheMinesAre to avoid null reference error.
     *
     * Fills mineField array with randomly generated mines
     * and corresponding numbers in the cells adjacent to mines.
     *
     */
    public void createNewMineField(){
       //DO NOT SKIP THIS, WILL CAUSE A NULL REF ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLUMNS; j++){
                this.mineField[i][j] = 0;
                this.whereFlagsAre[i][j] = 0;
                this.whereTheMinesAre[i][j] = 0;
                this.hasBeenRevealed[i][j] = false;
            }
        }
        this.gameOver = false;
        this.hasWon = false;

            //place mines in random location
            //be sure to make sure mines don't "overlap" and each has a unique location
            //MINES ARE REPRESENTED BY -1
            int uniqueMineCounter = 0;
            while (uniqueMineCounter < NUM_MINES) {
                int rowPlace = randGen.nextInt(NUM_ROWS) + 0;
                int columnPlace = randGen.nextInt(NUM_COLUMNS) + 0;
                try {
                    if (this.mineField[rowPlace][columnPlace] != -1 && this.whereTheMinesAre[rowPlace][columnPlace] == 0) {
                        this.mineField[rowPlace][columnPlace] = -1;
                        this.whereTheMinesAre[rowPlace][columnPlace] = 1;
                        uniqueMineCounter += 1;
                    }

                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }

            //give squares number based on adjacent mines
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLUMNS; j++) {
                    if (this.mineField[i][j] != -1) {
                        int adjacentMines = 0;
                        //top left corner relative to i, j(i - 1, j -1)
                        try {
                            if (this.mineField[i - 1][j - 1] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //directly above
                        try {
                            if (this.mineField[i - 1][j] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //top right corner
                        try {
                            if (this.mineField[i - 1][j + 1] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //left of
                        try {
                            if (this.mineField[i][j - 1] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //right of
                        try {
                            if (this.mineField[i][j + 1] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //bottom left corner
                        try {
                            if (this.mineField[i + 1][j - 1] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //directly below
                        try {
                            if (this.mineField[i + 1][j] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //bottom right
                        try {
                            if (this.mineField[i + 1][j + 1] == -1) {
                                adjacentMines += 1;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }

                        //assign number to current index
                        this.mineField[i][j] = adjacentMines;
                        System.out.println("" + i + " " + j + " has " + adjacentMines + " mines");
                    }

                }
            }
    }


    /**
     * Accepts a row and a column to click
     * if the player clicks a mine then the game ends.
     *
     * if the player clicks a zero, surrounding zeroes are cleared
     *
     * if the player clicks a non zero/non mine sqaure then that swaure is cleared
     * @param row index of row
     * @param column index of column
     */
    public void click(int row, int column){
        //would I need a try-catch block????? IDK
        //game over situation ----> player clicks on mine
        //reset game board
        if(this.mineField[row][column] == -1){
            System.out.println("RIP you clicked a mine lmao. Skill Issue tbh");
            this.hasBeenRevealed[row][column] = true;
            gameOver = true;
            return;
        }
        //zero space click
        if(this.mineField[row][column] == 0){
            //figure out recursive call
            //this.hasBeenRevealed[row][column] = true;
            zeroClicked(row, column);
        }
        //non zero and non mine click
        if(this.mineField[row][column] >= 1){
            //reveal in main activity
            this.hasBeenRevealed[row][column] = true;
        }
    }

    /**
     * Will clear surrounding/connecting zeros when a zero square is clicked
     * @param row index of row
     * @param column index of column
     */
    public void zeroClicked(int row, int column){
        //top left
        try {
            if(this.hasBeenRevealed[row][column] == false){
                this.hasBeenRevealed[row][column] = true;
                click(row - 1, column -1);
                click(row - 1, column);
                click(row - 1, column + 1);
                click(row , column -1);
                click(row, column + 1);
                click(row + 1, column - 1);
                click(row + 1, column);
                click(row + 1, column + 1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("" + row + "," + column + "is out of bounds");
        }

    }

    /**
     * Will determine if the board is cleared.
     * @return true if cleared, false if not
     */
    public boolean isCleared(){
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLUMNS; j++) {
                if(this.whereTheMinesAre[i][j]!= 0){
                    return false;
                }
            }
        }
        hasWon = true;
        return true;
    }

    /**
     * @return the status of the current game
     */
    public boolean getGameOverStatus(){
        return this.gameOver;
    }

    /**
     * Accepts a row and column representing the square to be clicked
     * and places a flag in that location.
     *
     * Also updates flagsCorrectlyPlaced and numPlayerFlags to help with determining win condition.
     * @param row index of row
     * @param column index of column
     */
    public void placeFlag(int row, int column) {
        this.whereFlagsAre[row][column] = 1;
    }

    /**
     * @return returns the status of flaggingMode
     */
    public boolean getFlaggingMode(){
        return this.flaggingMode;
    }

    /**
     * allows for the toggling of flagging mode
     */
    public void setFlaggingMode(){

        this.flaggingMode = !this.flaggingMode;
    }

    /**
     *
     * @return the status of hasWon
     */
    public boolean getHasWon(){
        return hasWon;
    }

    /**
     * will set hasWon to true if the player has won
     */
    public void hasThePlayerWon(){
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLUMNS; j++){
                if(this.whereFlagsAre[i][j] != this.whereTheMinesAre[i][j]) {
                    this.hasWon = false;
                    return;
                }
            }
        }
        this.hasWon = true;
    }


    /**
     * Outputs the minefield(gameBoard) as a string
     * @return string containing the mineField array in string format
     */
    public String toString() {
        String outputString = "";
        for(int[] row:this.mineField){
            outputString = outputString + "[";
            for(int column: row){

                if (column == -1){
                    outputString = outputString + "x";
                } else
                outputString = outputString + String.valueOf(column);
            }
            outputString = outputString + "]\n";
        }
        return outputString;
    }

    /**
     * builds and returns a string for testing within the main method
     * @return string of hasBeenRevealed array
     */
    public String toStringRevealed() {
        String outputString = "";
        for(boolean[] row:this.hasBeenRevealed){
            outputString = outputString + "[";
            for(boolean column: row){

                if (column == true){
                    outputString = outputString + "1";
                } else
                    outputString = outputString + "0";
            }
            outputString = outputString + "]\n";
        }
        return outputString;
    }

    /**
     * main method used for testing/debugging
     * @param args
     */
    public static void main(String[] args) {
        gameTracker mineSweeperBoard = new gameTracker();
        System.out.print(mineSweeperBoard.toStringRevealed());
        System.out.println("--------------TESTING CLICK------------");
        mineSweeperBoard.click(1,1);
        mineSweeperBoard.click(0,0);
        mineSweeperBoard.click(3,4);
        System.out.print(mineSweeperBoard.toStringRevealed());
    }


}
