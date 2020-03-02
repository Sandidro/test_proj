package presenters;

import java.util.List;

import javax.inject.Inject;

import interactors.DefaultObserver;
import interactors.GetUserUseCase;
import model.User;
import views.UserDetailsView;

public class UserDetailsPresenter {

    private UserDetailsView userDetailsView;

    private GetUserUseCase getUserUseCase;

    @Inject
    public UserDetailsPresenter(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public void setUser(String login) {
        getUserUseCase.execute(new UserObserver(), GetUserUseCase.Params.setLogin(login));
    }

    public void setView(UserDetailsView userListView){
        this.userDetailsView = userListView;
    }

    private final class UserObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            userDetailsView.showError("Ошибка соединения");
        }

        @Override
        public void onNext(User user) {
            userDetailsView.showContainer();
            userDetailsView.showImage(user.getImage());
            userDetailsView.showTitle(user.getTitle());
            userDetailsView.showToolbarTitle(user.getTitle());
        }
    }

}