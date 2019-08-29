package com.makaroni.usersphoto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.makaroni.usersphoto.data.User;
import com.makaroni.usersphoto.data.UsersAdapter;
import com.makaroni.usersphoto.loaders.UsersLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<User>>{
    private static final String JSON_REQUEST = "http://jsonplaceholder.typicode.com/users";
    private UsersAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LoaderManager manager = LoaderManager.getInstance(this);
        manager.initLoader(1,null,this);
    }

    @NonNull
    @Override
    public Loader<List<User>> onCreateLoader(int id, @Nullable Bundle args) {
        Toast.makeText(this, "on create", Toast.LENGTH_SHORT).show();
        return new UsersLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<User>> loader, final List<User> data) {
        showUsers(data);
        Toast.makeText(this, "on finish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<User>> loader) {

    }
    private void showUsers(List<User> users){
        recyclerViewAdapter = new UsersAdapter(users);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}
