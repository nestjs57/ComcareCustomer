package com.comcare.comcarecustomer.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.comcare.comcarecustomer.Models.StatusModel;
import com.comcare.comcarecustomer.R;
import com.comcare.comcarecustomer.StatusDetail;
import com.comcare.comcarecustomer.ViewHolder.StatusViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder>{



    private ImageView imageView;
    private ArrayList <StatusModel> statusSet;

    public StatusAdapter(ArrayList<StatusModel> statusSet) {
        this.statusSet = statusSet;
    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View viewh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status,parent,false);
        final StatusViewHolder holder = new StatusViewHolder(viewh);



        return holder;
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, final int position) {

        final StatusModel data = statusSet.get(position);

        if (data.getType().equals("2")){
            holder.img.setBackgroundResource(R.drawable.laptop);
        }

        if (data.getStatus().equals("2")){
            holder.txtStatus.setText("กำลังดำเนินการ");
            holder.txtStatus.setTextColor(Color.parseColor("#FF0000"));
        }else if (data.getStatus().equals("3")){
            holder.txtStatus.setText("เสร็จสิ้น");
            holder.txtStatus.setTextColor(Color.parseColor("#00FF00"));
        }



        holder.txtProblem.setText(data.getproblem1());
        holder.txtDate.setText(data.getDate());
        holder.txtTime.setText(data.getTime());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(view.getContext(),StatusDetail.class);
                intent.putExtra("key",data.getOrder_id());
                view.getContext().startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return statusSet.size();
    }







}

