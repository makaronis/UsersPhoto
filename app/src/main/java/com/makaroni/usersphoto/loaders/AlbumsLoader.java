package com.makaroni.usersphoto.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.makaroni.usersphoto.data.PhotoImage;
import com.makaroni.usersphoto.helper.QueryUtils;

import java.util.List;

public class AlbumsLoader extends AsyncTaskLoader<List<PhotoImage>> {
    private int userId;
    public AlbumsLoader(@NonNull Context context,int userId) {
        super(context);
        this.userId = userId;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<PhotoImage> loadInBackground() {
        List<Integer> albumIds = QueryUtils.extractAlbumIds(userId);
        List<PhotoImage> photoImages = QueryUtils.loadPhotos(albumIds);
        return photoImages;
    }
}
