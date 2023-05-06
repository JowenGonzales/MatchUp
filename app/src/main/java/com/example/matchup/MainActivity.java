package com.example.matchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.gridlayout.widget.GridLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout4);
        // Set the number of columns for the grid
        int numColumns = 3;
        gridLayout.setColumnCount(numColumns);

        // Set the number of rows for the grid
        int numRows = 4;
        gridLayout.setRowCount(numRows);

        // Get the width of the grid layout
        ViewTreeObserver vto = gridLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = gridLayout.getWidth();

                // Calculate the width of each button based on the number of columns and grid layout width
                int buttonWidth = width / numColumns;

                // Create buttons and add them to the GridLayout
                for (int i = 0; i < numColumns * numRows; i++) {
                    FlippableButton button = new FlippableButton(MainActivity.this);
                    button.setLayoutParams(new GridLayout.LayoutParams(
                            new ViewGroup.LayoutParams(buttonWidth, buttonWidth)));
                    gridLayout.addView(button);
                }

                // Remove the listener to avoid multiple calls
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }
    public class FlippableButton extends AppCompatButton {
        private Drawable frontDrawable;
        private Drawable backDrawable;
        private AnimatorSet flipAnimation;
        private boolean isFlipped = false;

        public FlippableButton(Context context) {
            super(context);
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

        private void init() {
            // Create the front and back drawables
            frontDrawable = getResources().getDrawable(R.drawable.picture);
            backDrawable = getResources().getDrawable(R.drawable.chick);

            // Set the front drawable as the background of the button
            setBackground(frontDrawable);

            // Create the flip animation
            ObjectAnimator flipOut = ObjectAnimator.ofFloat(this, "rotationY", 0f, 90f);
            ObjectAnimator flipIn = ObjectAnimator.ofFloat(this, "rotationY", -90f, 0f);
            flipIn.setStartDelay(200);
            flipAnimation = new AnimatorSet();
            flipAnimation.playSequentially(flipOut, flipIn);
            flipAnimation.setDuration(500);
            flipAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

            flipOut.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}

                @Override
                public void onAnimationEnd(Animator animation) {
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

                @Override
                public void onAnimationCancel(Animator animation) {}

                @Override
                public void onAnimationRepeat(Animator animation) {}
            });

            // Set an OnClickListener on the button to start the animation
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flipAnimation.start();
                }
            });
        }
    }




}