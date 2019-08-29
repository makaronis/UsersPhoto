package com.makaroni.usersphoto.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.makaroni.usersphoto.data.User;
import com.makaroni.usersphoto.helper.QueryUtils;

import java.util.List;

public class UsersLoader extends AsyncTaskLoader<List<User>> {

    public UsersLoader(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<User> loadInBackground() {

        List<User> users = QueryUtils.extractUsers();
        return users;
    }

}
