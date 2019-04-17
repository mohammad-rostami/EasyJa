package ir.MVP;

/**
 * Created by Mohammad on 7/17/2018.
 */

public class UserInteractor implements IUserInteractor {


    @Override
    public void getUser(IGetUserFinishedListener iGetUserFinishedListener) {
        iGetUserFinishedListener.successGetUser("test");
        iGetUserFinishedListener.failureGetUser();
    }
}
