package com.subhrajyoti.guardian;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;


class RecyclerAdapter extends RecyclerView.Adapter<MainViewHolder> {


    private ArrayList<ContactModel> arrayList;
    private ColorGenerator generator = ColorGenerator.MATERIAL;


    RecyclerAdapter(ArrayList<ContactModel> arrayList) {
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
        mainViewHolder.nameView.setText(arrayList.get(i).getName());
        mainViewHolder.numberView.setText(arrayList.get(i).getPhone());
        TextDrawable drawable = TextDrawable.builder().buildRound(String.valueOf(arrayList.get(i).getName().charAt(0)).toUpperCase(), generator.getColor(arrayList.get(i).getName()));
        mainViewHolder.imageView.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}