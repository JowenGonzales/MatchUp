package com.example.matchup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {



    // Required empty public constructor
    public MyFragment() {
    }

    // Inflate the layout for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        // Set up the animation
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left);

        // Apply the animation to the view
        setExitTransition(animation);
        setEnterTransition(animation);
        view.startAnimation(animation);

        return view;
    }

}
