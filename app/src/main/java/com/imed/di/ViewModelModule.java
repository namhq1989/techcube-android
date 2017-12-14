package com.imed.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.imed.ui.area.AreaViewModel;
import com.imed.ui.create.CreateUserViewModel;
import com.imed.ui.history.HistoryViewModel;
import com.imed.ui.login.LoginViewModel;
import com.imed.ui.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateUserViewModel.class)
    abstract ViewModel bindCreateUserViewModel(CreateUserViewModel createUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AreaViewModel.class)
    abstract ViewModel bindAreaViewModel(AreaViewModel areaViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryViewModel(HistoryViewModel historyViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
