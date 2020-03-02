package presenters;

import java.util.List;

import javax.inject.Inject;

import interactors.DefaultObserver;
import interactors.GetUserList;
import model.User;
import views.UserDetailsView;
import views.UserListView;

public class UserDetailsPresenter {

    private UserDetailsView userDetailsView;

    private User selectedUser;
    @Inject
    public UserDetailsPresenter() {
    }

    public void setUser(User user) {
        selectedUser = user;
    }

    public void setView(UserDetailsView userListView){
        this.userDetailsView = userListView;
    }

    public void init(){
        userDetailsView.showImage(selectedUser.getImage());
        userDetailsView.showTitle(selectedUser.getTitle());
        userDetailsView.showToolbarTitle(selectedUser.getTitle());
    }

}