import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;


public class Block {
    /**
     * For simplicity, we use list instead of MP tree in this class, each block maintains following attributes
     * block's hash
     * previous block's hash
     * special message COINBASE, which sends rewards back to the miner/common_user involved.
     * list Events, analogue to tx list in canonical cryptocurrency block
     * HashMap EOAs, storing all current EOA in current system
     * HashMap PersonalFiles, storing all current personal files being managed
     */
    public static final int COINBASE = 10;

    private byte[] hash;
    private byte[] prevBlockHash;
    private ArrayList<Event> events;
    private ExternalOwnedUser miner;

    public Block(){
        this.prevBlockHash = null;
        this.events = new ArrayList<Event>();
        this.miner = null;
        System.out.println("You are trying to create a null block!");
    }

    public Block(byte[] prevBlockHash, ExternalOwnedUser miner){
        this.prevBlockHash = prevBlockHash;
        this.events = new ArrayList<Event>();
        if(!miner.get_auth().equals("miner")){
            System.out.println("The account has no auth to mine a block!");
            this.miner = null;
        }else if(!miner.get_canAction()) {
            System.out.println("Miner must take a break!");
            this.miner = null;
        }else{
            this.miner = miner;
        }
    }

    public byte[] getPrevBlockHash(){ return this.prevBlockHash; }

    public byte[] getHash(){ return this.hash; }

    public ArrayList<Event> getEvents(){ return this.events; }

    public ExternalOwnedUser getMiner() {
        return miner;
    }

    public Event getEvent(int eventIndex){ return this.events.get(eventIndex); }

    public void addEvent(Event event){ this.events.add(event); }

    public byte[] getRawBlock() {
        ArrayList<Byte> rawBlock = new ArrayList<Byte>();
        if (prevBlockHash != null)
            for (int i = 0; i < prevBlockHash.length; i++)
                rawBlock.add(prevBlockHash[i]);
        for (int i = 0; i < this.events.size(); i++) {
            byte[] rawEvents = this.events.get(i).getRawData();
            for (int j = 0; j < rawEvents.length; j++) {
                rawBlock.add(rawEvents[j]);
            }
        }
        byte[] raw = new byte[rawBlock.size()];
        for (int i = 0; i < raw.length; i++)
            raw[i] = rawBlock.get(i);
        return raw;
    }

    public void finalize() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(getRawBlock());
            hash = md.digest();
        } catch (NoSuchAlgorithmException x) {
            x.printStackTrace(System.err);
        }
    }

}
