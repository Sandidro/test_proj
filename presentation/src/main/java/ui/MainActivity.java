package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import butterknife.ButterKnife;
import internal.di.HasComponent;
import internal.di.components.ApplicationComponent;
import internal.di.components.DaggerUserComponent;
import internal.di.components.UserComponent;
import internal.di.modules.ActivityModule;
import model.User;
import ru.production.test.AndroidApplication;
import ru.production.test.R;
import ui.fragments.UserDetailsFragment;
import ui.fragments.UsersListFragment;

public class MainActivity extends AppCompatActivity implements HasComponent<UserComponent>, UsersListFragment.UserListListener {

    private UserComponent userComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.initializeInjector();
        this.getApplicationComponent().inject(this);
        showFragment(UsersListFragment.newInstance());
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!getSupportFragmentManager().getFragments().isEmpty()) {
            transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        transaction.replace( R.id.container, fragment );
        transaction.addToBackStack(fragment.toString());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }


    @Override
    public UserComponent getComponent() {
        return userComponent;
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    public void onUserClicked(User user) {
        showFragment(UserDetailsFragment.newInstance(user));
    }


}
