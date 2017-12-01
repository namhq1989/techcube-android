package com.imed.di;

import android.app.Application;

import com.imed.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by vinhnguyen.it.vn on 2017, September 19
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, ActivityModule.class, ViewModelModule.class, AppModule.class, ServiceModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
