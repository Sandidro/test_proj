package ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import internal.di.components.UserComponent;
import model.User;
import presenters.UserDetailsPresenter;
import ru.production.test.R;
import views.UserDetailsView;

public class UserDetailsFragment extends BaseFragment implements UserDetailsView {

    @BindView(R.id.image) ImageView image;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.title) TextView toolbarTitle;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.loadingPanel) FrameLayout loadingPanel;
    @BindView(R.id.scrollContainer) ScrollView scrollContainer;

    @Inject
    UserDetailsPresenter userDetailsPresenter;

    public static UserDetailsFragment newInstance(User user) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("login", user.getTitle());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.getComponent(UserComponent.class).inject(this);
        userDetailsPresenter.setUser(getArguments().getString("login"));
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDetailsPresenter.setView(this);
    }

    @Override
    public void showError(String error) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                error, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showContainer() {
        loadingPanel.setVisibility(View.GONE);
        scrollContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTitle(String title) {
        name.setText(title);
    }

    @Override
    public void showImage(String url) {
        Glide.with(getActivity()).load(url).into(image);
    }

    @Override
    public void showToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }
}