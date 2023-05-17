package com.example.matchup;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    public static List<FlippableButton> matchedCards = new ArrayList<>();
    public static List<FlippableButton> disabledButtons = new ArrayList<>();

    public static int[] cardIds;
    public static TextView scoreView;
    public static TextView triesView;
    public static LinearLayout finishedView;
    public static int score = 0;
    public static int tries = 0;

    private EasyActivity easyActivity;
    public static GridLayout cardGridLayout;
    public static FlippableButton[] allButtons;
    public static boolean matched = false;
    public static int cardCount;

    public static ProgressBar scoreProgressBar;

    public static TextView gameFinishedTextScore;
    public static DisplayMetrics displayMetrics;
    Game(Context context) {
        mContext = context;
    }


    public Game() {

    }
    public void destroy() {
        if (matchedCards != null) {
            matchedCards.clear();
        }
        if (disabledButtons != null) {
            disabledButtons.clear();
        }
        score = 0;
        firstFlippedCard = null;
        secondFlippedCard = null;



    }
    public static void addScoreProgress() {
        int totalCards = Game.cardCount; // Replace with the actual total number of cards
        int cardsFound = Game.matchedCards.size(); // Replace with the actual number of cards found by the user
        int maxProgress = 100;

        int targetProgress = (int) ((float) cardsFound / totalCards * maxProgress);

// Create the ObjectAnimator to animate the progress property
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(scoreProgressBar, "progress", targetProgress);

// Set the duration of the animation in milliseconds
        progressAnimator.setDuration(1000); // 1000 milliseconds = 1 second

// Start the animation
        progressAnimator.start();
    }
    public static void makeCardGrey(FlippableButton button) {


            // Get the background drawable of the button
            Drawable backgroundDrawable = button.getBackground();

            // Apply a color filter to darken the drawable
            backgroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

            // Set the modified drawable as the button's background
            button.setBackground(backgroundDrawable);


    }
    public static List<ImageView> starButton = new ArrayList<>();
    public static TextView triesFinishedView;
    public static void gameFinished() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(Game.finishedView, "scaleX", 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(Game.finishedView, "scaleY", 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
        Game.finishedView.setVisibility(View.VISIBLE);

        gameFinishedTextScore.setText("SCORE " + Game.score);
        triesFinishedView.setText("TRIES  " + Game.tries);

        



        // Define the animation duration and delay between each ImageView
        int animationDuration = 500; // in milliseconds
        int animationDelay = 200; // in milliseconds

// Iterate over the ImageViews and apply the animation
        for (int i = 0; i < starButton.size(); i++) {
            ImageView imageView = starButton.get(i);

            // Set the initial scale to 0 and rotation to a random value
            imageView.setScaleX(0);
            imageView.setScaleY(0);
            imageView.setRotation((float) (Math.random() * 360));

            // Set the final alpha to 1
            imageView.setAlpha(1f);

            // Create and start the animation
            ObjectAnimator scaleXAnimators = ObjectAnimator.ofFloat(imageView, "scaleX", 1);
            ObjectAnimator scaleYAnimators = ObjectAnimator.ofFloat(imageView, "scaleY", 1);
            ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1);

            scaleXAnimators.setDuration(animationDuration);
            scaleYAnimators.setDuration(animationDuration);
            rotationAnimator.setDuration(animationDuration);
            alphaAnimator.setDuration(animationDuration);

            scaleXAnimators.setStartDelay(animationDelay * i);
            scaleYAnimators.setStartDelay(animationDelay * i);
            rotationAnimator.setStartDelay(animationDelay * i);
            alphaAnimator.setStartDelay(animationDelay * i);

            scaleXAnimators.setInterpolator(new OvershootInterpolator());
            scaleYAnimators.setInterpolator(new OvershootInterpolator());
            rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

            scaleXAnimators.start();
            scaleYAnimators.start();
            rotationAnimator.start();
            alphaAnimator.start();
        }

    }
    public void setGameFinishedView(LinearLayout linearLayout) {
        finishedView = linearLayout;
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
                mContext.getResources().getDrawable(R.drawable.blackwidow),
                mContext.getResources().getDrawable(R.drawable.captainamerica),
                mContext.getResources().getDrawable(R.drawable.deadpool),
                mContext.getResources().getDrawable(R.drawable.doctorstrange),
                mContext.getResources().getDrawable(R.drawable.flash),
                mContext.getResources().getDrawable(R.drawable.greenarrow),
                mContext.getResources().getDrawable(R.drawable.greenlantern),
                mContext.getResources().getDrawable(R.drawable.hawkeye),
                mContext.getResources().getDrawable(R.drawable.hulk),
                mContext.getResources().getDrawable(R.drawable.ironman),
                mContext.getResources().getDrawable(R.drawable.spiderman),
                mContext.getResources().getDrawable(R.drawable.superman),
                mContext.getResources().getDrawable(R.drawable.thor),
                mContext.getResources().getDrawable(R.drawable.wolverine),
                mContext.getResources().getDrawable(R.drawable.wonderwoman),
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
                button.setClickable(false);
            }
        }
    }
    private static boolean isButtonInMatchedCards(Button button) {
        for (FlippableButton matchedButton : Game.matchedCards) {
            if (matchedButton.equals(button)) {
                return true;
            }
        }
        return false;
    }
    private static boolean isButtonInDisabledButtons(Button button) {
        for (FlippableButton matchedButton : Game.disabledButtons) {
            if (matchedButton.equals(button)) {
                return true;
            }
        }
        return false;
    }
    public static void enableAllButtons() {
        int count = cardGridLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = cardGridLayout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (!isButtonInMatchedCards(button)) {
                    button.setClickable(true);
                } else {
                    button.setClickable(false);
                }
            }
            if (child instanceof Button) {
                Button button = (Button) child;
                if (!isButtonInDisabledButtons(button)) {
                    button.setClickable(true);
                } else {
                    button.setClickable(false);
                }
            }

        }
    }



    public static void updateScore() {
        Game.scoreView.setText(Integer.toString(score));
    }
    public void newGame(GridLayout gridLayout, int numColumns, int numRows) {

        // Getting the gridlayout of the cards for future use
        cardGridLayout = gridLayout;
        cardCount = numColumns * numRows;


        // Set the number of columns and rows for the grid

        gridLayout.setRowCount(numRows);
        gridLayout.setColumnCount(numColumns);


        // Layout the Grid
        ViewTreeObserver vto = gridLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int spacing = 15;

                int width = gridLayout.getWidth();
                int height = gridLayout.getHeight();

                int buttonWidth = (width - spacing * (numColumns - 1)) / numColumns;
                int buttonHeight = (height - spacing * (numRows - 1)) / numRows;

                Drawable[] drawables = new Game(mContext).createGameBoard(numColumns * numRows);
                FlippableButton[] storeButtons = new FlippableButton[numColumns * numRows];

                for (int i = 0; i < numColumns * numRows; i++) {
                    FlippableButton button = new FlippableButton(mContext, drawables[i]);
                    button.cardValue = Game.cardIds[i];

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                            new ViewGroup.LayoutParams(buttonWidth, buttonHeight));
                    params.setMargins(0, 0, spacing, spacing);

                    button.setLayoutParams(params);
                    gridLayout.addView(button);

                    storeButtons[i] = button;
                }
                allButtons = storeButtons;

                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);


            }
        });


       






    }
    public static void resetFlippedCards() {
        Game.firstFlippedCard = null;
        Game.secondFlippedCard = null;
    }

}
