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
    private String mURL;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String mURL) {
        this.mURL = mURL;
        mDownloadStatus = DownloadStatus.IDLE;
    }

    public void reset() {
        mDownloadStatus = DownloadStatus.IDLE;
        mURL = null;
        mData = null;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public String getmData() {
        return mData;
    }

    public void execute() {
        mDownloadStatus = DownloadStatus.PROCESSING;
        new DownloadRawData().execute(mURL);
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
            mData = result;
            Log.v(LOG_TAG, "Data returned was: " + mData);

            if (mData == null)
                if (mURL == null)
                    mDownloadStatus = DownloadStatus.NOT_INTIALISED;
                else
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            else
                mDownloadStatus = DownloadStatus.OK;
        }
    }
}
