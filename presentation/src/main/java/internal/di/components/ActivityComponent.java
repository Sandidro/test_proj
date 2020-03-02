package internal.di.components;


import androidx.appcompat.app.AppCompatActivity;

import dagger.Component;
import internal.di.PerActivity;
import internal.di.modules.ActivityModule;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
  AppCompatActivity activity();
}
