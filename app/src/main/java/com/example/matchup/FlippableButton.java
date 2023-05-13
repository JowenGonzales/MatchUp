package com.example.matchup;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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











        flipOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Game.disableAllButtons();
            }

            @Override
            public void onAnimationEnd(Animator animation) {


                // Flip the Card
                flip();
                Game.enableAllButtons();

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
                    if (Game.firstFlippedCard == null) {

                        Log.d("COUNT", "First Card Flipped");
                        Game.firstFlippedCard = FlippableButton.this;
                        flipAnimation.start();
                        
                    } else if (Game.secondFlippedCard == null) {
                        Log.d("COUNT", "First Card Flipped");
                        Game.secondFlippedCard = FlippableButton.this;
                        flipAnimation.start();


                        // Delay before flipping the first card
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Matched
                                if (Game.firstFlippedCard.cardValue == Game.secondFlippedCard.cardValue) {

                                    Game.matchedCards.add(Game.firstFlippedCard);
                                    Game.matchedCards.add(Game.secondFlippedCard);

                                    // Add scores and show it
                                    Game.score += 10;
                                    Game.updateScore();
                                    Game.enableAllButtons();
                                } else {
                                    Game.firstFlippedCard.flipAnimation.start();
                                    Game.secondFlippedCard.flipAnimation.start();
                                }
                                Game.resetFlippedCards();
                            }
                        }, 1000); // Adjust the delay duration as needed
                    }
                }


            }
        });
    }


}