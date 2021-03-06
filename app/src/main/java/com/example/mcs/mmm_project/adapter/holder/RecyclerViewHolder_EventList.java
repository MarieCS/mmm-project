package com.example.mcs.mmm_project.adapter.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mcs.mmm_project.EventActivity;
import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.pojo.Event;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Une case (CardView) de la liste (RecyclerView)
 */
public class RecyclerViewHolder_EventList extends RecyclerView.ViewHolder  {

    @BindView(R.id.text) public TextView textView;
    @BindView(R.id.image) public ImageView imageView;

    public View view;
    private Event event;

    public RecyclerViewHolder_EventList(View view) {
        super(view);
        this.view = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventActivity.class);
                intent.putExtra("event", event);
                v.getContext().startActivity(intent);
            }
        });
        ButterKnife.bind(this, view);
    }

    /**
     * Remplir la card
     * @param event Objet à utiliser pour remplir la card
     */
    public void bind(Event event){
        this.event = event;
        textView.setText(event.toString());
        Picasso.with(imageView.getContext()).load(event.apercu).centerCrop().fit().into(imageView);
    }
}