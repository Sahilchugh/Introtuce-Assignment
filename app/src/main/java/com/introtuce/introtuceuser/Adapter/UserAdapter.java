package com.introtuce.introtuceuser.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.introtuce.introtuceuser.Model.DatabaseUser;
import com.introtuce.introtuceuser.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<DatabaseUser> databaseUsers;


    TextView full_name, gender, age, location;
    ImageButton delete;
    ImageView profile;

    public UserAdapter(List<DatabaseUser> databaseUsers) {
        this.databaseUsers = databaseUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new UserAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // DatabaseUser databaseUser = DatabaseUser.get(position);

        full_name.setText(databaseUsers.get(position).getFirst_name()+" "+databaseUsers.get(position).getLast_name());
        location.setText(databaseUsers.get(position).getCountry()+ ", "+ databaseUsers.get(position).getState());
        gender.setText(databaseUsers.get(position).getGender());
        age.setText(databaseUsers.get(position).getDob());
        Picasso.get().load(databaseUsers.get(position).getImageUri123()).into(profile);


    }

    @Override
    public int getItemCount() {
        return databaseUsers.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.full_name);
            location = itemView.findViewById(R.id.location);
            delete = itemView.findViewById(R.id.delete);
            gender = itemView.findViewById(R.id.gender);
            age = itemView.findViewById(R.id.age);
            profile = itemView.findViewById(R.id.profile_view);

        }
    }


}
