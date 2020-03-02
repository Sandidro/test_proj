package internal.di.components;


import android.content.Context;


import javax.inject.Singleton;

import dagger.Component;
import executors.PostExecutionThread;
import executors.ThreadExecutor;
import internal.di.modules.ApplicationModule;
import repository.UserRepository;
import ui.MainActivity;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(MainActivity baseActivity);

  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  UserRepository userRepository();
}
