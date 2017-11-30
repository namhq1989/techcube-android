package com.zodyapp.qrcode.di;

import com.zodyapp.qrcode.ui.create.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vinhnguyen.it.vn on 2017, September 19
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}
