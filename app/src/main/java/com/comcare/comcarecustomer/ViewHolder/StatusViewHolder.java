package com.comcare.comcarecustomer.ViewHolder;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comcare.comcarecustomer.R;

public class StatusViewHolder extends RecyclerView.ViewHolder{

    public TextView txtProblem;
    public TextView txtDate;
    public TextView txtStatus;
    public ImageView img;
    public TextView txtTime;

    public StatusViewHolder(View itemView) {
        super(itemView);

        txtProblem = (TextView) itemView.findViewById(R.id.tvProblem);
        txtDate = (TextView) itemView.findViewById(R.id.tvDate);
        txtStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        img = (ImageView) itemView.findViewById(R.id.img);
        txtTime = (TextView) itemView.findViewById(R.id.tvTime);




        itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //for intent to status detail
        }
    });
}
}
