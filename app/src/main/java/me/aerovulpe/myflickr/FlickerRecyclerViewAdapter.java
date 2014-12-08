package me.aerovulpe.myflickr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aaron on 02/12/2014.
 */
public class FlickerRecyclerViewAdapter extends RecyclerView.Adapter<FlickerImageViewHolder> {
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickerRecyclerViewAdapter(Context context, List<Photo> photoList) {
        mContext = context;
        mPhotoList = photoList;
    }

    @Override
    public FlickerImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        FlickerImageViewHolder flickerImageViewHolder = new FlickerImageViewHolder(view);

        return flickerImageViewHolder;
    }

    @Override
    public void onBindViewHolder(FlickerImageViewHolder holder, int position) {
        Photo photo = mPhotoList.get(position);
        Picasso.with(mContext).load(photo.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != mPhotoList) ? mPhotoList.size() : 0;
    }

    public void loadNewData(List<Photo> newPhotos){
        mPhotoList = newPhotos;
        notifyDataSetChanged();
    }
}
