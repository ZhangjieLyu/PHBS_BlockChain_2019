import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class PersonalFileAccount {
    /**
     * Personal file account are the account being managed, they can only be viewed(cannot edit)
     * by external user(means they are have no auth in the chain)
     * social_id_key: social identification number
     * personal Log: hashMap, dictionary illustrates your personal file
     * nonce: another paired number, make search space more sparse
     * utxoPool: Array list of HashMap<EventHash, Event>, used to store personal event with attribute series_of_event = true
     */
    private String social_id_key;
    private HashMap<String, Pile> personalLog;
    private BigInteger nonce;
//    private ArrayList<HashMap<byte[], Event>> utxoPool;
    private HashMap<byte[], Event> utxoPool;

    public PersonalFileAccount(){
        this.social_id_key = new String();
        this.personalLog = new HashMap<String, Pile>();
        this.personalLog.put("education",new Pile());
        this.personalLog.put("household",new Pile());
        this.nonce = null;
//        this.utxoPool = new ArrayList<HashMap<byte[], Event>>();
        this.utxoPool = new HashMap<byte[], Event>();
    }

    public PersonalFileAccount(String social_id_key, BigInteger nonce){
        this.social_id_key = social_id_key;
        this.personalLog = new HashMap<String, Pile>();
        this.personalLog.put("education",new Pile());
        this.personalLog.put("household",new Pile());
        this.nonce = nonce;
//        this.utxoPool = new ArrayList<HashMap<byte[], Event>>();
        this.utxoPool = new HashMap<byte[], Event>();
    }

    //get attributes
    public HashMap<String, Pile> getPersonalLog() {
        return this.personalLog;
    }

//    public ArrayList<HashMap<byte[], Event>> getUtxoPool(){
//        return this.utxoPool;
//    }

    public HashMap<byte[], Event> getUtxoPool() {
        return utxoPool;
    }

    public ArrayList<byte[]> getUtxoEventHash(){
        ArrayList<byte[]> eventHashArray = new ArrayList<byte[]>();
        if(this.utxoPool == null){
            return new ArrayList<byte[]>();
        }else{
//            for(int i=0; i<this.utxoPool.size();i++){
//                eventHashArray.add(this.utxoPool.keySet().iterator().next());
//            }
            eventHashArray.addAll(this.utxoPool.keySet());
            return eventHashArray;
        }
    }

    public void printPersonalLog(){
        for(String key: personalLog.keySet()){
            System.out.println("category:"+key);
            for(int i=0; i<personalLog.get(key).get_length();i++){
                System.out.println("log  "+i+"th: "+personalLog.get(key).get_element(i));
            }
        }
    }

    public String getSocial_id_key(){
        return this.social_id_key;
    }

    //add element
    public void pushLog(String tag, String log){
        this.personalLog.get(tag).push_log(log);
    }

    //set nonce
    public void set_nonce(String nonce){
        if(this.nonce==null&nonce!=null){
            this.nonce = new BigInteger(nonce);
        }else{
            System.out.println("has been given value, cannot be modified any more");
        }
    }
}
