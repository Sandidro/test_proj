package internal.di.modules;



import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final AppCompatActivity fragmentActivity;

  public ActivityModule(AppCompatActivity fragmentActivity) {
    this.fragmentActivity = fragmentActivity;
  }

  @Provides
  AppCompatActivity activity() {
    return this.fragmentActivity;
  }

}
