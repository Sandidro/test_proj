package interactors;

import javax.inject.Inject;

import executors.PostExecutionThread;
import executors.ThreadExecutor;
import io.reactivex.Observable;
import model.User;
import repository.UserRepository;


public class GetUserUseCase extends UseCase<User, GetUserUseCase.Params> {

    private final UserRepository repository;

    @Inject
    GetUserUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = userRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable(Params params) {
        return repository.fetchUser(params.login);
    }

    public static final class Params {

        private final String login;

        private Params(String login) {
            this.login = login;
        }

        public static Params setLogin(String login) {
            return new Params(login);
        }
    }


}
