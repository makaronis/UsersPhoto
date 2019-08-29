package com.makaroni.usersphoto.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.makaroni.usersphoto.helper.QueryUtils;

import java.util.List;

public class AlbumsLoader extends AsyncTaskLoader<List<Integer>> {
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
    public List<Integer> loadInBackground() {
        List<Integer> albumIds = QueryUtils.extractAlbumIds();
        return albumIds;
    }
}
