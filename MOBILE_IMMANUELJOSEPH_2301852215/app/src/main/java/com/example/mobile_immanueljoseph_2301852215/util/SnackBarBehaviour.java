package com.example.mobile_immanueljoseph_2301852215.util;

import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;

public class SnackBarBehaviour extends BaseTransientBottomBar.Behavior {

    @Override
    public boolean canSwipeDismissView(View child) {
        return false;
    }
}
