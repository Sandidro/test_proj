package interactors;

import executors.PostExecutionThread;
import executors.ThreadExecutor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Александр on 12.03.2018.
 */

public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    protected UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    abstract protected Observable<T> buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param observer {@link io.reactivex.observers.DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public void execute(DisposableObserver<T> observer, Params params) {

        if (observer == null) {
            throw new NullPointerException();
        }

        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {

        if (disposable == null || disposables == null) {
            throw new NullPointerException();
        }

        disposables.add(disposable);
    }
}