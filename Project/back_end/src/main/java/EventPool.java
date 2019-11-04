import java.util.ArrayList;
import java.util.HashMap;

public class EventPool {
    /** A class map Event with it proposer EOA
     */
    private Event event;
    private ExternalOwnedUser externalOwnedUser;
    private HashMap<Event, ExternalOwnedUser> proposedEvents;

    public EventPool(){
        this.proposedEvents = new HashMap<Event, ExternalOwnedUser>();
    }

    public EventPool(EventPool eventPool){
        this.proposedEvents = new HashMap<Event, ExternalOwnedUser>(eventPool.proposedEvents);
    }

    public void addEvent(Event event, ExternalOwnedUser externalOwnedUser){
        if(event.getPublisher().equals(externalOwnedUser.get_id_key())){
            this.proposedEvents.put(event, externalOwnedUser);
        }else{
            System.out.println("Publisher and EOA does not match.");
        }
    }

    public void removeEvent(Event event, ExternalOwnedUser externalOwnedUser){
        this.proposedEvents.remove(event);
    }

    public void removeAllEvents(){
        for(Event event:this.proposedEvents.keySet()){
            this.proposedEvents.remove(event);
        }
    }

    public Event getEvent(Event event){
        if(this.proposedEvents.containsKey(event)){
            return event;
        }else{
            return new Event();
        }
    }

    public ExternalOwnedUser getExternalOwnedUser(Event event){
        if(this.proposedEvents.containsKey(event)){
            return this.proposedEvents.get(event);
        }else{
            return new ExternalOwnedUser();
        }
    }

    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> allEvents = new ArrayList<Event>();
        for(Event event:this.proposedEvents.keySet()){
            allEvents.add(event);
        }
        return allEvents;
    }

    public boolean contains(Event event){
        return this.proposedEvents.containsKey(event);
    }
}

