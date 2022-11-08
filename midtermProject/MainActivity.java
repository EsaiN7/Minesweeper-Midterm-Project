package edu.angelo.midtermprojectbarron;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Random;

import edu.angelo.midtermprojectbarron.gameTracker;

public class MainActivity extends AppCompatActivity {

    //errors should go away when other stuff is written
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameBoard = new gameTracker();
        mineFieldButtons = new Button[][]{{
                findViewById(R.id.button00),
                findViewById(R.id.button01),
                findViewById(R.id.button02),
                findViewById(R.id.button03),
                findViewById(R.id.button04),
                findViewById(R.id.button05),
                findViewById(R.id.button06),
                findViewById(R.id.button07)
        }, {
                findViewById(R.id.button10),
                findViewById(R.id.button11),
                findViewById(R.id.button12),
                findViewById(R.id.button13),
                findViewById(R.id.button14),
                findViewById(R.id.button15),
                findViewById(R.id.button16),
                findViewById(R.id.button17)
        }, {
                findViewById(R.id.button20),
                findViewById(R.id.button21),
                findViewById(R.id.button22),
                findViewById(R.id.button23),
                findViewById(R.id.button24),
                findViewById(R.id.button25),
                findViewById(R.id.button26),
                findViewById(R.id.button27)
        }, {
                findViewById(R.id.button30),
                findViewById(R.id.button31),
                findViewById(R.id.button32),
                findViewById(R.id.button33),
                findViewById(R.id.button34),
                findViewById(R.id.button35),
                findViewById(R.id.button36),
                findViewById(R.id.button37)
        }, {
                findViewById(R.id.button40),
                findViewById(R.id.button41),
                findViewById(R.id.button42),
                findViewById(R.id.button43),
                findViewById(R.id.button44),
                findViewById(R.id.button45),
                findViewById(R.id.button46),
                findViewById(R.id.button47)
        }, {
                findViewById(R.id.button50),
                findViewById(R.id.button51),
                findViewById(R.id.button52),
                findViewById(R.id.button53),
                findViewById(R.id.button54),
                findViewById(R.id.button55),
                findViewById(R.id.button56),
                findViewById(R.id.button57)
        }, {
                findViewById(R.id.button60),
                findViewById(R.id.button61),
                findViewById(R.id.button62),
                findViewById(R.id.button63),
                findViewById(R.id.button64),
                findViewById(R.id.button65),
                findViewById(R.id.button66),
                findViewById(R.id.button67)
        }, {
                findViewById(R.id.button70),
                findViewById(R.id.button71),
                findViewById(R.id.button72),
                findViewById(R.id.button73),
                findViewById(R.id.button74),
                findViewById(R.id.button75),
                findViewById(R.id.button76),
                findViewById(R.id.button77)
        }};
        updateMineField();
    }

    /**
     * number of rows for the game board
     */
    private final int NUM_ROWS = 8;
    /**
     * number of columns for the game board
     */
    private final int NUM_COLUMNS = 8;
    /**
     * number of mines on the game board
     */
    private final int NUM_MINES= 10;

    /**
     * gameTracker object representing the minesweeper game board
     */
    private gameTracker gameBoard = new gameTracker();


    /**
     * Array of buttons that will allow for interaction with
     * mineField
     */
    private Button[][] mineFieldButtons;

    /**
     * updates the gameboard according to user interaction
     */
    private void updateMineField() {

        gameBoard.hasThePlayerWon();

        if(gameBoard.getHasWon() == true){
            TextView outcomeMessage = findViewById(R.id.textView);
            outcomeMessage.setText("CONGRATULATIONS! You are a sweeper of the mines \uD83C\uDF89!");
            outcomeMessage.setTextColor(Color.BLACK);
            outcomeMessage.setBackgroundColor(Color.GREEN);
        }

        //reveals all mines on game over
        if(gameBoard.getGameOverStatus() == true){
            for(int i = 0; i < NUM_ROWS; i++){
                for(int j = 0; j < NUM_COLUMNS; j++){
                    if(gameBoard.mineField[i][j] == -1){
                        gameBoard.hasBeenRevealed[i][j] = true;
                    }
                }
            }
        }
        //will toggle color for flagging mode button
        if(gameBoard.getFlaggingMode() == true){
            findViewById(R.id.flagToggle).setBackgroundColor(Color.RED);
        } else
        {
            findViewById(R.id.flagToggle).setBackgroundColor(Color.GRAY);
        }

        //updates text on buttons depending on user interactions
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLUMNS; j++){

                if(gameBoard.whereFlagsAre[i][j] == 1){
                    mineFieldButtons[i][j].setText("\uD83D\uDEA9"); //flag emoji
                } else {

                    if (gameBoard.hasBeenRevealed[i][j] == true) {
                        if(gameBoard.mineField[i][j] == -1){
                            mineFieldButtons[i][j].setText("\uD83D\uDCA3");//bomb emoji
                        } else {
                            mineFieldButtons[i][j].setText(String.valueOf(gameBoard.mineField[i][j]));
                        }
                    } else {
                        //set blank text for square
                        mineFieldButtons[i][j].setText("");
                        TextView outcomeMessage = findViewById(R.id.textView);
                        outcomeMessage.setText("Place flags on all the mines to win! Be careful, you can't pick up flags once placed.");
                        outcomeMessage.setTextColor(Color.WHITE);
                        outcomeMessage.setBackgroundColor(Color.BLUE);
                    }
                }
            }
        }

        if(gameBoard.isCleared() == true){
            TextView outcomeMessage = findViewById(R.id.textView);
            outcomeMessage.setText("CONGRATULATIONS! You are a sweeper of the mines! \uD83C\uDF89");
            outcomeMessage.setTextColor(Color.BLACK);
            outcomeMessage.setBackgroundColor(Color.GREEN);
        }
        else if(gameBoard.getGameOverStatus() == true){
            TextView outcomeMessage = findViewById(R.id.textView);
            outcomeMessage.setText("You did not sweep the mines \uD83D\uDC80 ");
            outcomeMessage.setTextColor(Color.BLACK);
            outcomeMessage.setBackgroundColor(Color.RED);
        }
    }


    /**
     * enables the user to toggle flagging mode
     * @param v
     */
    public void enableFlaggingMode(View v){
        gameBoard.setFlaggingMode();
        updateMineField();
    }

    /**
     * allows the user to reset the game board
     * @param v
     */
    public void resetGame(View v){
        gameBoard.createNewMineField();
        updateMineField();
    }

    /**
     * click methods for each respective button on the gameBoard
     */
    public void click00(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,0);
        }
        else {
            gameBoard.click(0, 0);
        }
        updateMineField();
    }
    public void click01(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,1);
        }
        else {
            gameBoard.click(0, 1);
        }
        updateMineField();
    }
    public void click02(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,2);
        }
        else {
            gameBoard.click(0, 2);
        }
        updateMineField();
    }
    public void click03(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,3);
        }
        else {
            gameBoard.click(0, 3);
        }
        updateMineField();
    }
    public void click04(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,4);
        }
        else {
            gameBoard.click(0, 4);
        }
        updateMineField();
    }
    public void click05(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,5);
        }
        else {
            gameBoard.click(0, 5);
        }
        updateMineField();
    }
    public void click06(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,6);
        }
        else {
            gameBoard.click(0, 6);
        }
        updateMineField();
    }
    public void click07(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(0,7);
        }
        else {
            gameBoard.click(0, 7);
        }
        updateMineField();
    }

    //CLICK METHODS FOR ROW 1
    public void click10(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,0);
        }
        else {
            gameBoard.click(1, 0);
        }
        updateMineField();
    }
    public void click11(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,1);
        }
        else {
            gameBoard.click(1, 1);
        }
        updateMineField();
    }
    public void click12(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,2);
        }
        else {
            gameBoard.click(1, 2);
        }
        updateMineField();
    }
    public void click13(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,3);
        }
        else {
            gameBoard.click(1, 3);
        }
        updateMineField();
    }
    public void click14(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,4);
        }
        else {
            gameBoard.click(1, 4);
        }
        updateMineField();
    }
    public void click15(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,5);
        }
        else {
            gameBoard.click(1, 5);
        }
        updateMineField();
    }
    public void click16(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,6);
        }
        else {
            gameBoard.click(1, 6);
        }
        updateMineField();
    }
    public void click17(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(1,7);
        }
        else {
            gameBoard.click(1, 7);
        }
        updateMineField();
    }
    //ROW 2 CLICK METHODS
    public void click20(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,0);
        }
        else {
            gameBoard.click(2, 0);
        }
        updateMineField();
    }
    public void click21(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,1);
        }
        else {
            gameBoard.click(2, 1);
        }
        updateMineField();
    }
    public void click22(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,2);
        }
        else {
            gameBoard.click(2, 2);
        }
        updateMineField();
    }
    public void click23(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,3);
        }
        else {
            gameBoard.click(2, 3);
        }
        updateMineField();
    }
    public void click24(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,4);
        }
        else {
            gameBoard.click(2, 4);
        }
        updateMineField();
    }
    public void click25(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,5);
        }
        else {
            gameBoard.click(2, 5);
        }
        updateMineField();
    }
    public void click26(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,6);
        }
        else {
            gameBoard.click(2, 6);
        }
        updateMineField();
    }
    public void click27(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(2,7);
        }
        else {
            gameBoard.click(2, 7);
        }
        updateMineField();
    }
    //click for row 3
    public void click30(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,0);
        }
        else {
            gameBoard.click(3, 0);
        }
        updateMineField();
    }
    public void click31(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,1);
        }
        else {
            gameBoard.click(3, 1);
        }
        updateMineField();
    }
    public void click32(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,2);
        }
        else {
            gameBoard.click(3, 2);
        }
        updateMineField();
    }
    public void click33(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,3);
        }
        else {
            gameBoard.click(3, 3);
        }
        updateMineField();
    }
    public void click34(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,4);
        }
        else {
            gameBoard.click(3, 4);
        }
        updateMineField();
    }
    public void click35(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,5);
        }
        else {
            gameBoard.click(3, 5);
        }
        updateMineField();
    }
    public void click36(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,6);
        }
        else {
            gameBoard.click(3, 6);
        }
        updateMineField();
    }
    public void click37(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(3,7);
        }
        else {
            gameBoard.click(3, 7);
        }
        updateMineField();
    }
    //click for row 4
    public void click40(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,0);
        }
        else {
            gameBoard.click(4, 0);
        }
        updateMineField();
    }
    public void click41(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,1);
        }
        else {
            gameBoard.click(4, 1);
        }
        updateMineField();
    }
    public void click42(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,2);
        }
        else {
            gameBoard.click(4, 2);
        }
        updateMineField();
    }
    public void click43(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,3);
        }
        else {
            gameBoard.click(4, 3);
        }
        updateMineField();
    }
    public void click44(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,4);
        }
        else {
            gameBoard.click(4, 4);
        }
        updateMineField();
    }
    public void click45(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,5);
        }
        else {
            gameBoard.click(4, 5);
        }
        updateMineField();
    }
    public void click46(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,6);
        }
        else {
            gameBoard.click(4, 6);
        }
        updateMineField();
    }
    public void click47(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(4,7);
        }
        else {
            gameBoard.click(4, 7);
        }
        updateMineField();
    }
    //row 5 clicks
    public void click50(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,0);
        }
        else {
            gameBoard.click(5, 0);
        }
        updateMineField();
    }
    public void click51(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,1);
        }
        else {
            gameBoard.click(5, 1);
        }
        updateMineField();
    }
    public void click52(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,2);
        }
        else {
            gameBoard.click(5, 2);
        }
        updateMineField();
    }
    public void click53(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,3);
        }
        else {
            gameBoard.click(5, 3);
        }
        updateMineField();
    }
    public void click54(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,4);
        }
        else {
            gameBoard.click(5, 4);
        }
        updateMineField();
    }
    public void click55(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,5);
        }
        else {
            gameBoard.click(5, 5);
        }
        updateMineField();
    }
    public void click56(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,6);
        }
        else {
            gameBoard.click(5, 6);
        }
        updateMineField();
    }
    public void click57(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(5,7);
        }
        else {
            gameBoard.click(5, 7);
        }
        updateMineField();
    }
    //row 6 click methods
    public void click60(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,0);
        }
        else {
            gameBoard.click(6, 0);
        }
        updateMineField();
    }
    public void click61(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,1);
        }
        else {
            gameBoard.click(6, 1);
        }
        updateMineField();
    }
    public void click62(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,2);
        }
        else {
            gameBoard.click(6, 2);
        }
        updateMineField();
    }
    public void click63(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,3);
        }
        else {
            gameBoard.click(6, 3);
        }
        updateMineField();
    }
    public void click64(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,4);
        }
        else {
            gameBoard.click(6, 4);
        }
        updateMineField();
    }
    public void click65(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,5);
        }
        else {
            gameBoard.click(6, 5);
        }
        updateMineField();
    }
    public void click66(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,6);
        }
        else {
            gameBoard.click(6, 6);
        }
        updateMineField();
    }
    public void click67(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(6,7);
        }
        else {
            gameBoard.click(6, 7);
        }
        updateMineField();
    }
    //row 7 clicks
    public void click70(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,0);
        }
        else {
            gameBoard.click(7, 0);
        }
        updateMineField();
    }
    public void click71(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,1);
        }
        else {
            gameBoard.click(7, 1);
        }
        updateMineField();
    }
    public void click72(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,2);
        }
        else {
            gameBoard.click(7, 2);
        }
        updateMineField();
    }
    public void click73(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,3);
        }
        else {
            gameBoard.click(7, 3);
        }
        updateMineField();
    }
    public void click74(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,4);
        }
        else {
            gameBoard.click(7, 4);
        }
        updateMineField();
    }
    public void click75(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,5);
        }
        else {
            gameBoard.click(7, 5);
        }
        updateMineField();
    }
    public void click76(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,6);
        }
        else {
            gameBoard.click(7, 6);
        }
        updateMineField();
    }
    public void click77(View v){
        if(gameBoard.getFlaggingMode() == true) {
            gameBoard.placeFlag(7,7);
        }
        else {
            gameBoard.click(7, 7);
        }
        updateMineField();
    }











}
