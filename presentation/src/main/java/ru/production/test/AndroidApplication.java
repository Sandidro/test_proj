package ru.production.test;

import android.app.Application;

import internal.di.components.ApplicationComponent;
import internal.di.components.DaggerApplicationComponent;
import internal.di.modules.ApplicationModule;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
