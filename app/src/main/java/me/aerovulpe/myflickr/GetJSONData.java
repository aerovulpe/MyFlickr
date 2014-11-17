package me.aerovulpe.myflickr;

import android.net.Uri;

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
        buildUri(tags, matchAll);
    }

    private boolean buildUri(String tags, boolean matchAll) {
        final String FLICKER_API_SCHEME = "https";
        final String FLICKER_API_AUTHORITY = "api.flickr.com";
        final String FLICKER_API_PUBLIC_PHOTO_PATH = "services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NOJSONCALLBACK_PARAM = "nojsoncallback";

        Uri.Builder builder = new Uri.Builder();

        builder.scheme(FLICKER_API_SCHEME)
                .authority(FLICKER_API_AUTHORITY)
                .appendPath(FLICKER_API_PUBLIC_PHOTO_PATH)
                .appendQueryParameter(TAGS_PARAM, tags)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "all" : "any")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NOJSONCALLBACK_PARAM, "1");
        uri = builder.build();

        return uri != null;
    }

    public class DownloadJSONData extends  DownloadRawData{
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

        }
    }
}
