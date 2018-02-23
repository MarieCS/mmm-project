package com.example.mcs.mmm_project.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.pojo.Evaluation;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Une case (CardView) de la liste (RecyclerView)
 */
public class RecyclerViewHolder_EvaluationList extends RecyclerView.ViewHolder  {

    @BindView(R.id.text) public TextView textView;
    @BindView(R.id.ratingBar) public RatingBar ratingBar;

    public RecyclerViewHolder_EvaluationList(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    /**
     * Remplir la card
     * @param evaluation Objet à utiliser pour remplir la card
     */
    public void bind(Evaluation evaluation){
        this.textView.setText(evaluation.comment);
        this.ratingBar.setRating(evaluation.rating);
        this.ratingBar.setVisibility((evaluation.rating == 0) ?  View.GONE : View.VISIBLE);
    }
}