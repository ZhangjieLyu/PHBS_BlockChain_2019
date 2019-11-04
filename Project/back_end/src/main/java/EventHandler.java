import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EventHandler {
    /**
     * Event handler is used to process multiple events in one time, which is prior to packing a block
     */
    private EventPool eventPool;
    private PersonalFileAccountUTXOPool personalFileAccountUTXOPool;

    public EventHandler(){
        this.eventPool = new EventPool();
        this.personalFileAccountUTXOPool = new PersonalFileAccountUTXOPool();
    }

    public EventHandler(EventPool eventPool, PersonalFileAccountUTXOPool personalFileAccountUTXOPool){
        this.eventPool = eventPool;
        this.personalFileAccountUTXOPool = personalFileAccountUTXOPool;
    }

    public boolean isValidEvent(Event event){
        if(!Crypto.verifySignature(event.getPublisher(), event.getRawData(), event.getSignature())) {
            System.out.println("You are not authorized to propose this event!");
            return false;
        }
        // check EOA's auth
        if(!this.eventPool.getExternalOwnedUser(event).get_auth().equals("common_user")){
            System.out.println("Only auth = 'common_user' is allowed to propose an event!");
            return false;
        }
        // check if a series_of_event statement valid
        if(this.eventPool.getEvent(event).getSeries_of_event()){
            PersonalFileAccount p_account = this.personalFileAccountUTXOPool.getPersonalFileAccount(event);
            if(event.getPrevEventHash()!=null){
                if(!p_account.getUtxoEventHash().contains(event.getPrevEventHash())){
                    System.out.println("This is not a valid personal file account UTXO!No previous event found!");
                    return false;
                }
            }
        }
        return true;
    }

    // process events, select those are valid to be included in a block;
    // the length of Arraylist<Event[]> is of length 2, where 0-th event[] is the valid Events,
    // and 1-st event[] are those invalid one, and will be broadcast to validator
    public ArrayList<Event[]> handleEvent(Event[] waitingEvents){
        final Set<Event> validEvent = new HashSet<Event>();
        Set<Event> invalidEvent = new HashSet<Event>();

        for(Event waitingEvent: waitingEvents){
            if(isValidEvent(waitingEvent)){
                validEvent.add(waitingEvent);
            }else{
                invalidEvent.add(waitingEvent);
            }
        }

        final Event[] validEvent_final = validEvent.toArray(new Event[validEvent.size()]);
        final Event[] invalidEvent_final = invalidEvent.toArray(new Event[invalidEvent.size()]);
        return new ArrayList<Event[]>(){
            {
                add(validEvent_final);
                add(invalidEvent_final);
            }
        };
    }
}
