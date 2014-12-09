package me.aerovulpe.myflickr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends BaseActivity implements PhotoHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private FlickerRecyclerViewAdapter flickerRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        flickerRecyclerViewAdapter = new FlickerRecyclerViewAdapter(this, null);
        mRecyclerView.setAdapter(flickerRecyclerViewAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Normal Tap", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "Long Tap", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ViewPhotoDetailsActivity.class);
                        intent.putExtra(EXTRA_PHOTO_TRANSFER, flickerRecyclerViewAdapter.getPhoto(position));
                        startActivity(intent);
                    }
                }));

//        GetRawData rawJson = new GetRawData("https://api.flickr.com/services/feeds/photos_public.gne?format=json&tags=android,lollipop&nojsoncallback=1");
//        rawJson.execute();
//        new GetJSONData("cat", true).execute(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String query = getSavedPreferenceData(FLICKR_QUERY);
        if (!query.isEmpty()) {
            new GetJSONData(query, true).execute(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        ;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPhotosReceivedListener(List<Photo> photos) {
        flickerRecyclerViewAdapter.loadNewData(photos);
    }

    private String getSavedPreferenceData(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, "");
    }
}
