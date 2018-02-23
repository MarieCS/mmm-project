package com.example.mcs.mmm_project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.adapter.holder.RecyclerViewHolder_EventList;
import com.example.mcs.mmm_project.pojo.Event;
import java.util.List;

public class RecyclerViewAdapter_EventList extends RecyclerView.Adapter<RecyclerViewHolder_EventList> {
    private List<Event> eventList;

    public RecyclerViewAdapter_EventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * cette fonction permet de créer les viewHolder
     * @param viewGroup vue à inflater (à partir des layout xml)
     * @param itemType
     * @return
     */
    @Override
    public RecyclerViewHolder_EventList onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.event_list_card, viewGroup,false);
        return new RecyclerViewHolder_EventList(view);
    }

    /**
     * Remplir une card avec les données
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder_EventList holder, int position) {
        holder.bind(eventList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}