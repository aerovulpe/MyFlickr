package me.aerovulpe.myflickr;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by Aaron on 07/12/2014.
 */
public class BaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    public static final String FLICKR_QUERY = "me.aerovulpe.myflickr.FLICKR_QUERY";
    public static final String EXTRA_PHOTO_TRANSFER = "me.aerovulpe.myflickr.extra.PHOTO_TRANSFER";

    protected Toolbar activateToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(mToolbar);
        }

        return mToolbar;
    }

    protected Toolbar activateToolbarWithHome(boolean isOn) {
        activateToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(isOn);
        return mToolbar;
    }

}
