package com.makaroni.usersphoto.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class UsersLoader extends AsyncTaskLoader<List<User>> {
    private String mUrl;

    public UsersLoader(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<User> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<User> users = QueryUtils.getNetworkRequest(mUrl);
        return users;
    }

}
