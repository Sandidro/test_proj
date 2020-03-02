package presenters;

import java.util.List;

import javax.inject.Inject;

import interactors.DefaultObserver;
import interactors.GetUserListUseCase;
import model.User;
import views.UserListView;

public class UserListPresenter {

    private GetUserListUseCase getUserListUseCase;
    private UserListView userListView;

    private int offset;

    @Inject
    public UserListPresenter(GetUserListUseCase getUserListUseCase) {
        this.getUserListUseCase = getUserListUseCase;
    }

    public void loadUsers() {
        getUserListUseCase.execute(new UserListObserver(), GetUserListUseCase.Params.setSince(offset));
    }

    public void setView(UserListView userListView){
        this.userListView = userListView;
    }

    public void onUserSelected(User user){
        this.userListView.openDetails(user);
    }

    private final class UserListObserver extends DefaultObserver<List<User>> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            userListView.showError("Ошибка соединения");
        }

        @Override
        public void onNext(List<User> users) {
            userListView.showUsers(users);
            offset += users.size();
        }
    }

}