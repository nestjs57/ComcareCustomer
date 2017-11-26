package com.comcare.comcarecustomer.ViewHolder;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comcare.comcarecustomer.R;

public class StatusViewHolder extends RecyclerView.ViewHolder{

    Intent intent;
    public TextView txtName;
    public TextView txtDate;
    public TextView txtStatus;
    public ImageView img;

    public StatusViewHolder(View itemView) {
        super(itemView);

        txtName = (TextView) itemView.findViewById(R.id.tvName);
        txtDate = (TextView) itemView.findViewById(R.id.tvDate);
        txtStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        img = (ImageView) itemView.findViewById(R.id.img);



        itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //for intent to status detail
        }
    });
}
}
