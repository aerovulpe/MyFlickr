package me.aerovulpe.myflickr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aaron on 02/12/2014.
 */
public class FlickerImageViewHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnail;
    protected TextView title;

    public FlickerImageViewHolder(View view) {
        super(view);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        title = (TextView) view.findViewById(R.id.title);
    }
}
