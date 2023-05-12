package com.example.matchup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {

    private Context mContext;

    public static FlippableButton firstFlippedCard;
    public static FlippableButton secondFlippedCard;
    public List<FlippableButton> matchedCards = new ArrayList<>();

    public static int[] cardIds;
    public static TextView scoreView;
    public static int score = 0;

    private EasyActivity easyActivity;
    public static GridLayout cardGridLayout;
    public static FlippableButton[] allButtons;
    Game(Context context) {
        mContext = context;
    }


    public Game() {

    }
    public void destroy() {
        score = 0;
        firstFlippedCard = null;
        secondFlippedCard = null;
        mContext = null;
        matchedCards = null;
        cardIds = null;

    }
    public void setTextView(TextView textview) {

        scoreView = textview;
    }
    public void addFlippedCard(FlippableButton drawable) {
        matchedCards.add(drawable);
    }

    public Drawable[] createGameBoard(int count) {
        Drawable[] drawables = new Drawable[] {
                mContext.getResources().getDrawable(R.drawable.batman),
                mContext.getResources().getDrawable(R.drawable.captainamerica),
                mContext.getResources().getDrawable(R.drawable.flash),
                mContext.getResources().getDrawable(R.drawable.hulk),
                mContext.getResources().getDrawable(R.drawable.ironman),
                mContext.getResources().getDrawable(R.drawable.superman),
                mContext.getResources().getDrawable(R.drawable.thor),
                // Add more drawables here as needed
        };

        // Create a new array to store the game board drawables
        Drawable[] gameBoard = new Drawable[count];

        // Create a new array to store the IDs for each drawable
        int[] ids = new int[count];

        // Create a map to store unique IDs for each image
        Map<Drawable, Integer> idMap = new HashMap<>();

        // Assign a unique ID to each image
        int currentId = 0;
        for (Drawable drawable : drawables) {
            if (!idMap.containsKey(drawable)) {
                idMap.put(drawable, currentId);
                currentId++;
            }
        }

        // Populate the game board with pairs of drawables and their assigned IDs
        for (int i = 0; i < count; i += 2) {
            // Pick a random index in the shuffled drawables array
            int index = (int) (Math.random() * drawables.length);

            // Use the drawable at the selected index for both pairs
            gameBoard[i] = drawables[index];
            gameBoard[i + 1] = drawables[index];

            // Assign the unique ID for the drawable to both positions in the ids array
            ids[i] = idMap.get(drawables[index]);
            ids[i + 1] = idMap.get(drawables[index]);
        }

        // Randomly shuffle the IDs and game board arrays together
        for (int i = count - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            // Swap the game board elements at indices i and j
            Drawable tempDrawable = gameBoard[i];
            gameBoard[i] = gameBoard[j];
            gameBoard[j] = tempDrawable;
            // Swap the IDs at indices i and j
            int tempId = ids[i];
            ids[i] = ids[j];
            ids[j] = tempId;
        }

        // Set the cardIds field to the generated IDs
        cardIds = ids;

        // Return the game board array
        return gameBoard;

    }


    public static void disableAllButtons() {

        int count = cardGridLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = cardGridLayout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setEnabled(false);
            }
        }
        
    }

    public static void enableAllButtons() {


        int count = cardGridLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = cardGridLayout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setEnabled(true);
            }
        }
    }


    public void updateScore() {
        Game.scoreView.setText(Integer.toString(score));
    }
    public void newGame(GridLayout gridLayout, int numColumns, int numRows) {

        // Getting the gridlayout of the cards for future use
        cardGridLayout = gridLayout;


        // Set the number of columns and rows for the grid

        gridLayout.setRowCount(numRows);
        gridLayout.setColumnCount(numColumns);


        // Layout the Grid
        ViewTreeObserver vto = gridLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int spacing = 30;

                int width = gridLayout.getWidth();

                // Calculate the width of each button based on the number of columns and grid layout width
                int totalSpacing = spacing * (numColumns - 1);
                int buttonWidth = (width - totalSpacing) / numColumns;

                // Create buttons and add them to the GridLayout
                Drawable[] drawables = new Game(mContext).createGameBoard(numColumns * numRows);

                Log.d("CARDPOSITIONS: ", Arrays.toString(Game.cardIds));

                FlippableButton[] storeButtons = new FlippableButton[numColumns * numRows];

                for (int i = 0; i < numColumns * numRows; i++) {
                    FlippableButton button = new FlippableButton(mContext, drawables[i]);
                    button.cardValue = Game.cardIds[i];
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                            new ViewGroup.LayoutParams(buttonWidth, buttonWidth));
                    params.setMargins(0, 0, spacing, spacing);

                    button.setLayoutParams(params);
                    gridLayout.addView(button);

                    storeButtons[i] = button;
                }
                allButtons = storeButtons;

                // Remove the listener to avoid multiple calls
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);


            }
        });
    }



}
