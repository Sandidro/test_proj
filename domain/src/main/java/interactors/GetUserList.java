package interactors;

import java.util.List;

import javax.inject.Inject;

import executors.PostExecutionThread;
import executors.ThreadExecutor;
import io.reactivex.Observable;
import model.User;
import repository.UserRepository;


public class GetUserList extends UseCase<List<User>, GetUserList.Params> {

    private final UserRepository repository;

    @Inject
    GetUserList(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = userRepository;
    }

    @Override
    protected Observable<List<User>> buildUseCaseObservable(Params params) {
        return repository.fetchUsers(params.since);
    }

    public static final class Params {

        private final int since;

        private Params(int since) {
            this.since = since;
        }

        public static Params setSince(int since) {
            return new Params(since);
        }
    }


}
