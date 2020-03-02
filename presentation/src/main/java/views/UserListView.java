package views;

import java.util.List;

import model.User;

public interface UserListView {

    void showUsers(List<User> users);

    void showError(String error);

    void openDetails(User user);

}
