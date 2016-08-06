package com.subhrajyoti.guardian;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


class RecyclerAdapter extends RecyclerView.Adapter<MainViewHolder> {


    private ArrayList<ReportModel> arrayList;


    RecyclerAdapter(ArrayList<ReportModel> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainViewHolder mainViewHolder, int i) {
        mainViewHolder.dateView.setText(arrayList.get(i).getDate());
        mainViewHolder.crimeText.setText(arrayList.get(i).getCrime());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}