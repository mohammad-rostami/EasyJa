package ir.MVP;

/**
 * Created by Mohammad on 7/17/2018.
 */

public class UserPresenter implements IUserPresenter {
    IUserView iUserView;
    IUserInteractor iUserInteractor;

    public UserPresenter(IUserView iUserView, IUserInteractor iUserInteractor) {
        this.iUserView = iUserView;
        this.iUserInteractor = iUserInteractor;
    }

    @Override
    public void callGetUser() {

        iUserInteractor.getUser(new IUserInteractor.IGetUserFinishedListener() {
            @Override
            public void successGetUser(String message) {
                
            }

            @Override
            public void failureGetUser() {

            }
        });
    }
}
