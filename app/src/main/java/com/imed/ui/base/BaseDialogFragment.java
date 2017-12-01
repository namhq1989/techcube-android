package com.imed.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.imed.di.Injectable;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by vinhnguyen.it.vn on 2017, September 19
 */

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (this instanceof Injectable) {
            AndroidSupportInjection.inject(this);
        }
        super.onCreate(savedInstanceState);
    }
}
