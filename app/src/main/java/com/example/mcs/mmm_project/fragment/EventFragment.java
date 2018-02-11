package com.example.mcs.mmm_project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mcs.mmm_project.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.mcs.mmm_project.pojo.Event;

public class EventFragment extends Fragment {

    @BindView(R.id.title_fr) TextView title_fr;
    @BindView(R.id.adresse) TextView adresse;
    @BindView(R.id.description_fr) TextView description_fr;

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
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
