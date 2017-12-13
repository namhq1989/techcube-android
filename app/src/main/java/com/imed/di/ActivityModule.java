package com.imed.di;

import com.imed.ui.create.CreateUserActivity;
import com.imed.ui.login.LoginActivity;
import com.imed.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vinhnguyen.it.vn on 2017, September 19
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector
    abstract CreateUserActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity contributeLoginActivity();

}
