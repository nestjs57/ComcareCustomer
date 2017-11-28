package com.comcare.comcarecustomer.FragmentStatus;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comcare.comcarecustomer.Adapter.StatusAdapter;
import com.comcare.comcarecustomer.Models.StatusModel;
import com.comcare.comcarecustomer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStatus1 extends Fragment {

    private ArrayList<StatusModel> dataSet;
    private StatusAdapter adapter;
    ValueEventListener valueEventListener;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser firebaseDatabase = FirebaseAuth.getInstance().getCurrentUser();
    SwipeRefreshLayout swipeRefreshLayout;
    private Runnable runable;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;


    public FragmentStatus1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_fragment_status1, container, false);

        dataSet = new ArrayList<StatusModel>();
        adapter = new StatusAdapter(dataSet);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.rcvyStatus);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLayoutManager = new LinearLayoutManager(getActivity());

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);

        //dataSet.add(new StatusModel("toey", "เมื่อวาน", "กำลังดำเนินดาร"));
        connectToFirebase();
        // Inflate the layout for this fragment
        pullDown(inflate);


        return inflate;

    }

    private void pullDown(View inflate) {
        swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do something.
                final Handler handle = new Handler();
                runable = new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);


                        handle.removeCallbacks(runable);
                    }
                };
                handle.postDelayed(runable, 2000); // delay 2 s.
            }
        });
    }

    private void connectToFirebase() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String problem, date, status, type, time, uid;

                for (DataSnapshot itemSnap : dataSnapshot.getChildren()) {

                    StatusModel statusModel = itemSnap.getValue(StatusModel.class);

                    problem = statusModel.getproblem1() + "";
                    date = statusModel.getDate() + "";
                    status = statusModel.getStatus() + "";
                    type = statusModel.getType() + "";
                    time = statusModel.getTime() + "";
                    uid = statusModel.getUser_id();


                    if (firebaseDatabase.getUid().equals(uid)) {
                        dataSet.add(new StatusModel(problem, date, status, type, time, uid));
                    }


                }
                Collections.reverse(dataSet);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference childref = dbref.child("order");
        childref.addValueEventListener(valueEventListener);
    }

}
