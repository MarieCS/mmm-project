package com.example.mcs.mmm_project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.pojo.Event;
import com.squareup.picasso.Picasso;

public class EventFragment extends Fragment {
    @BindView(R.id.title_fr) TextView title_fr;
    @BindView(R.id.adresse) TextView adresse;
    @BindView(R.id.description_fr) TextView description_fr;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.dates) TextView dates;
    @BindView(R.id.ages) TextView ages;

    private static final String ARG_EVENT = "event";
    private Event event;
    private Unbinder unbinder;

    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     */
    public static EventFragment newInstance(Event event) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment, container, false);
        this.unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            event = (Event) getArguments().getSerializable(ARG_EVENT);

            title_fr.setText(event.titre_fr);
            adresse.setText(event.adresse);
            description_fr.setText(event.description_fr);
            Picasso.with(image.getContext()).load(event.image).centerCrop().fit().into(image);
            dates.setText("Du " + event.date_debut + "\nAu " + event.date_fin);
            if (event.tranche != null) {
                ages.setText("Age : " + event.tranche);
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
