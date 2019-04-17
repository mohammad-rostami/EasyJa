package ir.MVP;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Mohammad on 7/17/2018.
 */

public class UserActivity extends Activity implements IUserView {

    UserPresenter userPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPresenter = new UserPresenter(this, new UserInteractor());
        userPresenter.callGetUser();
    }

    @Override
    public void successGetUser(String message) {

    }

    @Override
    public void failureGetUser() {

    }
}
