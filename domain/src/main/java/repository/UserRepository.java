package repository;


import java.util.List;

import io.reactivex.Observable;
import model.User;

public interface UserRepository {

    Observable<List<User>> fetchUsers(int since);

}