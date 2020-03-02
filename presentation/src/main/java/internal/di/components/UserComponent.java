package internal.di.components;

import dagger.Component;
import internal.di.PerActivity;
import internal.di.modules.ActivityModule;
import internal.di.modules.UserModule;
import ui.fragments.UserDetailsFragment;
import ui.fragments.UsersListFragment;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
  void inject(UsersListFragment usersListFragment);
  void inject(UserDetailsFragment userDetailsFragment);
}
