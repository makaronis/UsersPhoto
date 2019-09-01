package com.makaroni.usersphoto.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makaroni.usersphoto.R;
import com.makaroni.usersphoto.cache.ImageLoader;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {
    private List<PhotoImage> photoImages;
    private Context context;
    private ImageLoader imageLoader;

    public PhotosAdapter(List<PhotoImage> photoImages,Context context,ImageLoader imageLoader) {
        this.context = context;
        this.photoImages = photoImages;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item_recycler,parent,false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.bind(photoImages.get(position));
    }

    @Override
    public int getItemCount() {
        return photoImages.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleText;
        private ProgressBar progressBar;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photoView);
            titleText = itemView.findViewById(R.id.titleText);
            progressBar = itemView.findViewById(R.id.progressBarViewHolder);
        }
        public void bind(PhotoImage photo){
            titleText.setText(photo.getTitle());
            imageLoader.DisplayImage(photo.getUrl(),imageView,progressBar);

        }
    }
}
