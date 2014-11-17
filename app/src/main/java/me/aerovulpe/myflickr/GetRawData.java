package me.aerovulpe.myflickr;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aaron on 17/11/2014.
 */

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INTIALISED, FAILED_OR_EMPTY, OK
}

public class GetRawData {
    private static final String LOG_TAG = GetRawData.class.getSimpleName();
    private String url;
    private String data;
    private DownloadStatus downloadStatus;

    public GetRawData() {
        downloadStatus = DownloadStatus.IDLE;
    }

    public void reset() {
        downloadStatus = DownloadStatus.IDLE;
        url = null;
        data = null;
    }

    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    public String getData() {
        return data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void execute() {
        downloadStatus = DownloadStatus.PROCESSING;
        new DownloadRawData().execute(url);
    }

    class DownloadRawData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if (params == null)
                return null;

            try {
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null)
                    return null;

                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error!", e);
                return null;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();

                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing the reader", e);
                    }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            data = result;
            Log.v(LOG_TAG, "Data returned was: " + data);

            if (data == null)
                if (url == null)
                    downloadStatus = DownloadStatus.NOT_INTIALISED;
                else
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            else
                downloadStatus = DownloadStatus.OK;
        }
    }
}
