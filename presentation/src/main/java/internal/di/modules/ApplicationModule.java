package internal.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import executors.JobExecutor;
import executors.PostExecutionThread;
import executors.ThreadExecutor;
import repository.UserRepository;
import ru.production.data.UserDataRepository;
import ru.production.test.AndroidApplication;
import utils.UIThread;

@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }


  @Provides
  @Singleton
  UserRepository provideRepository(UserDataRepository repository) {
    return repository;
  }

}
