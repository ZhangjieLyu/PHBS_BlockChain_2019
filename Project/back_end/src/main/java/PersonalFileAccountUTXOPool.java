import java.util.HashMap;

public class PersonalFileAccountUTXOPool {
    /**
     * A class, automatic combining with {@code EventPool}, to connect event, EOA, PersonalFileAccount
     */
    private HashMap<Event, PersonalFileAccount> H;

    public PersonalFileAccountUTXOPool(){
        this.H = new HashMap<Event, PersonalFileAccount>();
    }

    public PersonalFileAccountUTXOPool(PersonalFileAccountUTXOPool p_utxoPool){
        this.H = new HashMap<Event, PersonalFileAccount>(p_utxoPool.H);
    }

    public PersonalFileAccount getPersonalFileAccount(Event event){
        if(this.H.containsKey(event)){
            return this.H.get(event);
        }else{
            return new PersonalFileAccount();
        }
    }

    public void addElement(Event event, PersonalFileAccount personalFileAccount){
        if(event.getSocial_id_key().equals(personalFileAccount.getSocial_id_key())){
            this.H.put(event, personalFileAccount);
        }else{
            System.out.println("Announced social id key doesn't match!");
        }
    }

    public void removeElement(Event event){
        this.H.remove(event);
    }


}
