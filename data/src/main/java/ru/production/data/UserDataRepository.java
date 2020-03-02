package ru.production.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import model.User;
import repository.UserRepository;
import ru.production.mapper.UserMapper;
import ru.production.net.NetworkService;

@Singleton
public class UserDataRepository implements UserRepository {

    private final UserMapper userMapper;

    @Inject
    public UserDataRepository(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public Observable<List<User>> fetchUsers(int since) {
        return  NetworkService.getInstance().getGithubApi().userList(since)
                .subscribeOn(Schedulers.io())
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .map(item -> userMapper.transform(item))
                                .toList()
                                .toObservable()
                );
    }


}
