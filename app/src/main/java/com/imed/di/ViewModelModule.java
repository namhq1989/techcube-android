package com.imed.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.imed.ui.create.CreateUserViewModel;
import com.imed.ui.create.SendCodeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CreateUserViewModel.class)
    abstract ViewModel bindCreateUserViewModel(CreateUserViewModel createUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SendCodeViewModel.class)
    abstract ViewModel bindSendCodeViewModel(SendCodeViewModel sendCodeViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}