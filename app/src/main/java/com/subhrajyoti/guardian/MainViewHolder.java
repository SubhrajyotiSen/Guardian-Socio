package com.subhrajyoti.guardian;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView crimeText;
    TextView dateView;

    MainViewHolder(View itemView) {
        super(itemView);
        crimeText = (TextView) itemView.findViewById(R.id.crimeText);
        dateView = (TextView) itemView.findViewById(R.id.dateView);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}