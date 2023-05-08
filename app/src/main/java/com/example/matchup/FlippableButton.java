package com.example.matchup;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;



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


    public void flip(ObjectAnimator flipOut, ObjectAnimator flipIn) {
        if (isFlipped) {
            setBackground(frontDrawable);
            isFlipped = false;
            flipIn.start();
        } else {
            setBackground(backDrawable);
            isFlipped = true;
            flipIn.start();
        }

    }
    private void init() {
        // Create the front and back drawables
        frontDrawable = getResources().getDrawable(R.drawable.backcard2);
        backDrawable = backimage;

        // Set the front drawable as the background of the button
        setBackground(frontDrawable);

        // Create the flip animation
        ObjectAnimator flipOut = ObjectAnimator.ofFloat(this, "rotationY", 0f, 90f);
        ObjectAnimator flipIn = ObjectAnimator.ofFloat(this, "rotationY", -90f, 0f);
        flipIn.setStartDelay(200);
        flipAnimation = new AnimatorSet();
        flipAnimation.playSequentially(flipOut, flipIn);
        flipAnimation.setDuration(300);
        flipAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        flipOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Disable the button during the animation
                setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                flip(flipOut, flipIn);
                // Enable the button after the animation is complete
                setEnabled(true);

            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        // Set an OnClickListener on the button to start the animation
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only start the animation if the button is enabled
                if (isEnabled()) {
                    Game game = new Game();
                    if (game.firstFlippedCard == null) {
                        game.firstFlippedCard = FlippableButton.this;
                        game.addFlippedCard(FlippableButton.this);
                        flipAnimation.start();
                    }
                    else if (game.secondFlippedCard == null) {
                        game.secondFlippedCard = FlippableButton.this;
                        game.addFlippedCard(FlippableButton.this);
                        flipAnimation.start();
                    }
                    else {
                        if (game.firstFlippedCard.cardValue == game.secondFlippedCard.cardValue) {
                            game.firstFlippedCard.setEnabled(false);
                            game.secondFlippedCard.setEnabled(false);

                            game.firstFlippedCard = null;
                            game.secondFlippedCard = null;

                            game.score += 10;
                            game.updateScore();

                            Log.d("TAG", "Matched");
                        } else {
                            game.firstFlippedCard.flipAnimation.start();
                            game.secondFlippedCard.flipAnimation.start();

                            game.firstFlippedCard = null;
                            game.secondFlippedCard = null;
                            Log.d("TAG", "Not Matched");
                        }

                    }

                }
            }
        });
    }
}