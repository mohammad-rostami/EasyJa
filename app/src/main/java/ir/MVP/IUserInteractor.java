package ir.MVP;

/**
 * Created by Mohammad on 7/17/2018.
 */

public interface IUserInteractor {
    void getUser(IGetUserFinishedListener iGetUserFinishedListener);

    interface IGetUserFinishedListener {
        void successGetUser(String message);

        void failureGetUser();


    }
}
