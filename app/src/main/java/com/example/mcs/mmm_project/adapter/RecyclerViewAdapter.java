package com.example.mcs.mmm_project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.pojo.Event;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<Event> list;

    public RecyclerViewAdapter(List<Event> list) {
        this.list = list;
    }

    /**
     * cette fonction permet de créer les viewHolder
     * @param viewGroup vue à inflater (à partir des layout xml)
     * @param itemType
     * @return
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_list_card,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    /**
     * Remplir une card avec les données
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Event myObject = list.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}