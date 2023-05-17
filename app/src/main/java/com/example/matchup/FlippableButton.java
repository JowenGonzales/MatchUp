package com.example.matchup;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;


public class FlippableButton extends AppCompatButton {
    private Drawable frontDrawable;
    private Drawable backDrawable;
    private AnimatorSet flipAnimation;
    private boolean isFlipped = false;

    public int cardValue;


    public Drawable backimage;
    public FlippableButton(Context context, Drawable drawable) {
        super(context);
        backimage = drawable;
        init();
    }


    public FlippableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlippableButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }




    public void flip() {
        if (isFlipped) {
            setBackground(frontDrawable);
            isFlipped = false;

        } else {
            setBackground(backDrawable);
            isFlipped = true;

        }
    }

    private void init() {



        // Create the front and back drawables
        frontDrawable = getResources().getDrawable(R.drawable.back);
        backDrawable = backimage;

        // Set the front drawable as the background of the button
        setBackground(frontDrawable);

        // Create the flip animation
        ObjectAnimator flipOut = ObjectAnimator.ofFloat(this, "rotationY", 0f, 90f);
        ObjectAnimator flipIn = ObjectAnimator.ofFloat(this, "rotationY", -90f, 0f);


        flipAnimation = new AnimatorSet();
        flipAnimation.playSequentially(flipOut, flipIn);

        flipAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        flipAnimation.setStartDelay(100);



        setEnabled(false);
        // Delay before flipping the cards again
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the second flip animation here
                flipAnimation.start();
            }
        }, 1500);


        // Delay before flipping the cards again
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the second flip animation here
                flipAnimation.start();
                setEnabled(true);
            }
        }, 4000);



        flipOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                // Flip the Card
                flip();


            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        // Set an OnClickListener on the button to start the animation
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only start the animation if the button is enabled
                if (isEnabled()) {
                    if (Game.firstFlippedCard == null && Game.secondFlippedCard == null) {


                        Log.d("COUNT", "First Card Flipped");
                        Game.firstFlippedCard = FlippableButton.this;
                        flipAnimation.start();

                        Game.firstFlippedCard.setEnabled(false);

                    } else if (Game.firstFlippedCard != null && Game.secondFlippedCard == null) {

                        Log.d("COUNT", "Second Card Flipped");
                        Game.secondFlippedCard = FlippableButton.this;

                        Game.secondFlippedCard.setEnabled(false);
                        flipAnimation.start();

                        Game.secondFlippedCard.setEnabled(false);
                        // Delay before flipping the first card
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("CHECK", "Checking Cards");
                                // Matched
                                if (Game.firstFlippedCard != null && Game.firstFlippedCard.cardValue == Game.secondFlippedCard.cardValue) {

                                    Game.matchedCards.add(Game.firstFlippedCard);
                                    Game.matchedCards.add(Game.secondFlippedCard);

                                    // Add scores and show it
                                    Game.score += 10;
                                    Game.updateScore();

                                    if (Game.matchedCards.size() >= Game.cardCount) {

                                        Game.addScoreProgress();
                                        Game.gameFinished();

                                    } else {
                                        Log.d("TAG", Integer.toString(Game.matchedCards.size()));
                                        // Small-scale animation for matched cards
                                        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(Game.firstFlippedCard, "scaleX", 1f, 0.8f, 1f);
                                        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(Game.firstFlippedCard, "scaleY", 1f, 0.8f, 1f);
                                        AnimatorSet animatorSet = new AnimatorSet();
                                        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                                        animatorSet.setDuration(300);
                                        animatorSet.start();

                                        ObjectAnimator scaleXAnimator2 = ObjectAnimator.ofFloat(Game.secondFlippedCard, "scaleX", 1f, 0.8f, 1f);
                                        ObjectAnimator scaleYAnimator2 = ObjectAnimator.ofFloat(Game.secondFlippedCard, "scaleY", 1f, 0.8f, 1f);
                                        AnimatorSet animatorSet2 = new AnimatorSet();
                                        animatorSet2.playTogether(scaleXAnimator2, scaleYAnimator2);
                                        animatorSet2.setDuration(300);
                                        animatorSet2.start();




                                        Game.firstFlippedCard.setEnabled(false);
                                        Game.secondFlippedCard.setEnabled(false);


                                        Game.addScoreProgress();
                                        // Make the button Greyish




                                    }
                                } else {
                                    Game.firstFlippedCard.flipAnimation.start();
                                    Game.secondFlippedCard.flipAnimation.start();

                                    Game.firstFlippedCard.setEnabled(true);
                                    Game.secondFlippedCard.setEnabled(true);
                                }
                                Game.tries += 1;
                                Game.triesView.setText(Integer.toString(Game.tries));
                                Game.resetFlippedCards();
                            }
                        }, 1000); // Adjust the delay duration as needed
                    }
                }


            }
        });
    }


}