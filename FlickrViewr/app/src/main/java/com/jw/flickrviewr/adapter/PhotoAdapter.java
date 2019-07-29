package com.jw.flickrviewr.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jw.flickrviewr.R;
import com.jw.flickrviewr.data.Photo;

import java.util.List;

import static androidx.core.view.ViewCompat.setTransitionName;

/**
 * Adapter for the Recycler View of Gallery and Favorites screens.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> photos;

    private Context context;

    private OnItemClickListener listener;

    public PhotoAdapter(List<Photo> photos, OnItemClickListener listener) {
        this.photos = photos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photos.get(position);

        holder.bind(photo, listener);

        setTransitionName(holder.thumbnail, photo.getId());

        Glide.with(context)
                .load(photo.url())
                .apply(new RequestOptions()
                        .optionalCenterCrop()
                        .override(dpToPx(context, context.getResources().getInteger(R.integer.grid_view_overridden_size)))
                )
                .into(holder.thumbnail);

        if (photo.getTitle().isEmpty())
            holder.title.setVisibility(View.GONE);
        holder.title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void loadNewData(List<Photo> newPhotos){
        photos = newPhotos;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, Photo item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }

        void bind(final Photo item, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                listener.onItemClick(thumbnail, item);
            });
        }
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}