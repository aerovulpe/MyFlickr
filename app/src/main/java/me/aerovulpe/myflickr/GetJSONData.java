package me.aerovulpe.myflickr;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 17/11/2014.
 */
public class GetJSONData extends GetRawData {
    private static final String LOG_TAG = GetJSONData.class.getSimpleName();
    private List<Photo> photos;
    private Uri uri;

    public GetJSONData(String tags, boolean matchAll) {
        super(null);
        photos = new ArrayList<Photo>();
        buildUri(tags, matchAll);
    }

    @Override
    public void execute() {
        String url = uri.toString();
        super.setmURL(url);
        new DownloadJSONData().execute(url);
        Log.v(LOG_TAG, "Built URI = " + url);
    }

    private boolean buildUri(String tags, boolean matchAll) {
        final String FLICKR_API_BASE_URI = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NOJSONCALLBACK_PARAM = "nojsoncallback";

        uri = Uri.parse(FLICKR_API_BASE_URI).buildUpon()
                .appendQueryParameter(TAGS_PARAM, tags)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "all" : "any")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NOJSONCALLBACK_PARAM, "1")
                .build();

        return uri != null;
    }

    public class DownloadJSONData extends DownloadRawData {
        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            processResult();
        }

        private void processResult() {
            if (getmDownloadStatus() != DownloadStatus.OK) {
                Log.e(LOG_TAG, "Error downloading raw data file!");
                return;
            }

            final String FLICKR_ITEMS = "items";
            final String FLICKR_TITLE = "title";
            final String FLICKR_MEDIA = "media";
            final String FLICKR_PHOTO_URL = "m";
            final String FLICKR_AUTHOR = "author";
            final String FLICKR_AUTHOR_ID = "author_id";
            final String FLICKR_LINK = "link";
            final String FLICKR_TAGS = "tags";

            try {
                JSONObject rootObject = new JSONObject(getmData());
                JSONArray itemsArray = rootObject.getJSONArray(FLICKR_ITEMS);
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject photoObject = itemsArray.getJSONObject(i);
                    String title = photoObject.getString(FLICKR_TITLE);
                    String author = photoObject.getString(FLICKR_AUTHOR);
                    String authorID = photoObject.getString(FLICKR_AUTHOR_ID);
                    String link = photoObject.getString(FLICKR_LINK);
                    String tags = photoObject.getString(FLICKR_TAGS);

                    JSONObject mediaObject = photoObject.getJSONObject(FLICKR_MEDIA);
                    String photoUrl = mediaObject.getString(FLICKR_PHOTO_URL);

                    Photo photo = new Photo(title, author, authorID, link, tags, photoUrl);

                    photos.add(photo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error processing JSON data");
            }

            for (Photo photo : photos) {
                Log.d(LOG_TAG, photo.toString());
            }
        }
    }
}
