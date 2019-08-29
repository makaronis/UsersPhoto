package com.makaroni.usersphoto.data;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makaroni.usersphoto.PhotosActivity;
import com.makaroni.usersphoto.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }
    public void swapUsers(List<User> newUsers){
        if(newUsers == null)
            return;
        users = newUsers;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_recycler,parent,false);
        final UsersViewHolder vh = new UsersViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), PhotosActivity.class);
                intent.putExtra("userId",vh.getId());
                parent.getContext().startActivity(intent);

            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private int id;

        public int getId() {
            return id;
        }

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameText);
        }
        public void bind (User user){
            name.setText(user.getName());
            id = user.getId();
        }

    }
}
