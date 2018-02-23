package com.example.mcs.mmm_project.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.adapter.RecyclerViewAdapter;
import com.example.mcs.mmm_project.pojo.Event;
import com.example.mcs.mmm_project.pojo.EventPosition;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private List<Event> events = new ArrayList<>();

    @BindView(R.id.recyclerView) public RecyclerView recyclerView;

    public void update(List<Event> events){
        if(events == null){
            this.events.clear();
        }else{
            this.events = events;
            recyclerView.setAdapter(new RecyclerViewAdapter(events));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.event_list_fragment, container, false);
        ButterKnife.bind(this, view);

//        mDatabase = FirebaseDatabase.getInstance().getReference("features");
//        Query query = mDatabase.limitToFirst(50);
//
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Event event = dataSnapshot.child("properties").getValue(Event.class);
//                event.setFirebaseIndex(dataSnapshot.getKey());
//                events.add(event);
//                recyclerView.setAdapter(new RecyclerViewAdapter(events));
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EventListFragment fragment = new EventListFragment();

        ft.add(R.id.fragment, fragment);
        ft.commit();
    }
}