package com.example.mcs.mmm_project.pojo;

import java.util.List;

/**
 * Created by aurelienanne on 22/02/18.
 */

public class Parcours {

    private List<Event> eventList;

    private static final Parcours parcours = new Parcours();
    public static Parcours getInstance(){return parcours;}

    public List<Event> getParcours(){
        return eventList;
    }

    public void addEvent(Event event){
        eventList.add(event);
    }

    public void removeEvent(Event event){
        if(event != null && eventList.contains(event)) eventList.remove(event);
    }

}
