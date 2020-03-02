package ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import adapters.OnBottomReachedListener;
import adapters.UsersAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import internal.di.components.UserComponent;
import model.User;
import presenters.UserListPresenter;
import ru.production.test.R;
import views.UserListView;

public class UsersListFragment extends BaseFragment implements UserListView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private UserListListener userListListener;

    @Inject
    UserListPresenter userListPresenter;

    private UsersAdapter adapter;

    public interface UserListListener {
        void onUserClicked(User user);
    }

    public static UsersListFragment newInstance() {
        UsersListFragment fragment = new UsersListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserListListener) {
            this.userListListener = (UserListListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.getComponent(UserComponent.class).inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userListPresenter.setView(this);

        if (adapter == null) {
            adapter = new UsersAdapter(getContext());
            userListPresenter.loadUsers();
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showUsers(List<User> users) {
        if (adapter.getItemCount() == 1) {
            adapter.addUsers(users);
            adapter.notifyDataSetChanged();
            adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                @Override
                public void onBottomReached(int position) {
                    userListPresenter.loadUsers();
                }
            });

            adapter.setOnBottomReachedListener((position -> {
                userListPresenter.loadUsers();
            }));

            adapter.setOnItemClickListener((user -> {
                userListPresenter.onUserSelected(user);
            }));

        } else {
            adapter.addUsers(users);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showError(String error) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                error, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void openDetails(User user) {
        userListListener.onUserClicked(user);
    }

    private UsersAdapter.OnItemClickListener onItemClickListener = (user -> {
        UsersListFragment.this.userListPresenter.onUserSelected(user);
    });

}