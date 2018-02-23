package com.example.mcs.mmm_project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.adapter.holder.RecyclerViewHolder_EvaluationList;
import com.example.mcs.mmm_project.pojo.Evaluation;
import java.util.List;

public class RecyclerViewAdapter_EvaluationList extends RecyclerView.Adapter<RecyclerViewHolder_EvaluationList> {
    private List<Evaluation> evaluationList;

    public RecyclerViewAdapter_EvaluationList(List<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
    }

    /**
     * cette fonction permet de créer les viewHolder
     * @param viewGroup vue à inflater (à partir des layout xml)
     * @param itemType
     * @return
     */
    @Override
    public RecyclerViewHolder_EvaluationList onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.evaluation_list_card, viewGroup,false);
        return new RecyclerViewHolder_EvaluationList(view);
    }

    /**
     * Remplir une card avec les données
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder_EvaluationList holder, int position) {
        holder.bind(evaluationList.get(position));
    }

    @Override
    public int getItemCount() {
        return evaluationList.size();
    }
}