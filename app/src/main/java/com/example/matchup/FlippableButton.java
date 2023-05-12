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




    public void flip(ObjectAnimator flipOut, ObjectAnimator flipIn) {
        if (isFlipped) {
            setBackground(frontDrawable);
            isFlipped = false;
            Log.d("FLIPPED", "FRONT");
        } else {
            setBackground(backDrawable);
            isFlipped = true;
            Log.d("FLIPPED", "BACK");
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
        flipAnimation.setStartDelay(1000);











        flipOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setEnabled(false);
                Game.disableAllButtons();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                flip(flipOut, flipIn);
                setEnabled(true);
                Game.enableAllButtons();
                Log.d("ENABLED BUTTONS", "BUTTONS ENABLED");
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
                    flipAnimation.start();

                }
            }
        });
    }
}