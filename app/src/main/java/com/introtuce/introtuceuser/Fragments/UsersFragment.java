package com.introtuce.introtuceuser.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.introtuce.introtuceuser.Adapter.UserAdapter;
import com.introtuce.introtuceuser.Model.DatabaseUser;
import com.introtuce.introtuceuser.R;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<DatabaseUser> dbList = new ArrayList<>();
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView.LayoutManager layoutManager;



    public static UsersFragment newInstance(int index) {
        UsersFragment fragment = new UsersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview);
        dbList = new ArrayList<>();

        adapter = new UserAdapter(dbList);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        getdata();
    }

    private void getdata() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop data all user

                dbList=new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //instance object to get and set data
                    final DatabaseUser modelDB = postSnapshot.getValue(DatabaseUser.class);

                    //adding data list from object
                    dbList.add(modelDB);
                   // adapter.notifyDataSetChanged();

                }
                adapter = new UserAdapter(dbList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}