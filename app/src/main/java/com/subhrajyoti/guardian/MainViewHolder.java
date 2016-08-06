package com.subhrajyoti.guardian;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView nameView;
    TextView numberView;
    ImageView imageView;

    MainViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.name);
        numberView = (TextView) itemView.findViewById(R.id.number);
        imageView = (ImageView) itemView.findViewById(R.id.letter_head);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}