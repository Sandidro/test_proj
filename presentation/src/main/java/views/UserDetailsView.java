package views;

import java.util.List;

import model.User;

public interface UserDetailsView {

    void showTitle(String title);

    void showImage(String url);

    void showToolbarTitle(String title);

    void showError(String error);

    void showContainer();

}
