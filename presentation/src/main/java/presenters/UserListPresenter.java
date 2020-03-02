package presenters;

import java.util.List;

import javax.inject.Inject;

import interactors.DefaultObserver;
import interactors.GetUserList;
import model.User;
import views.UserListView;

public class UserListPresenter {

    private GetUserList getUserList;
    private UserListView userListView;

    private int offset;

    @Inject
    public UserListPresenter(GetUserList getUserList) {
        this.getUserList = getUserList;
    }

    public void loadUsers() {
        getUserList.execute(new UserListObserver(), GetUserList.Params.setSince(offset));
    }

    public void setView(UserListView userListView){
        this.userListView = userListView;
    }

    public void onUserSelected(User user){
        this.userListView.openDetails(user);
    }

    private final class UserListObserver extends DefaultObserver<List<User>> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {
            userListView.showError("Ошибка соединения");
        }

        @Override public void onNext(List<User> users) {
            userListView.showUsers(users);
            offset += users.size();
        }
    }

}