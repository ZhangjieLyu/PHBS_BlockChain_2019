import java.nio.ByteBuffer;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Event {
    /**
     * event is a class used to record message proposed by common users
     * attributes:
     * social_id_key: string used to indicate whose personal file account are going to be changed
     * eventMsg: string, log of event, recording what is happening
     * tag: string, paired with EOA.personalLog's keys
     * series of event: boolean
     * pervEventHash: byte, will only be useful when series of event is true
     * publisher: PubKey, which EOA publish the event
     * approval: boolean, a simplified version of receipt state, reveal whether the action is approved or not.
     */
    private byte[] prevEventHash;
    private byte[] signature;
    private String social_id_key;
    private String eventMsg;
    private String tag;
    private boolean series_of_event;
    private byte[] hash;
    private PublicKey publisher;
    private String approval;

    public Event(){
        this.social_id_key = "";
        this.eventMsg = "";
        this.tag = "";
        this.series_of_event = false;
        this.prevEventHash = null;
        this.publisher = null;
        this.approval = "NA";
    }

    public Event(String social_id_key, String eventMsg, String tag, boolean series_of_event, byte[] prevEventHash, PublicKey publisher){
        this.social_id_key = social_id_key;
        this.eventMsg = eventMsg;
        this.tag = tag;
        this.series_of_event = series_of_event;
        if(!series_of_event){
            this.prevEventHash = null;
        }else{
            this.prevEventHash = prevEventHash;
        }
        this.publisher = publisher;
        this.approval = "NA";
    }

    public void addSignature(byte[] sig){
        if (sig == null)
            signature = null;
        else
            signature = Arrays.copyOf(sig, sig.length);
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + Arrays.hashCode(prevEventHash);
        hash = hash * 31 + Arrays.hashCode(signature);
        return hash;
    }

    public byte[] getRawData(){
        ArrayList<Byte> rawData = new ArrayList<Byte>();

        byte[] prevEventHash = this.prevEventHash;

        if (this.social_id_key!=null) {
            for(int i=0; i<this.social_id_key.length();i++) {
                rawData.add((byte) this.social_id_key.charAt(i));
            }
        }

        if(this.eventMsg!=null){
            for(int i=0; i<this.eventMsg.length();i++){
                rawData.add((byte) this.eventMsg.charAt(i));
            }
        }
        if(this.tag!=null){
            for(int i=0; i<this.tag.length();i++){
                rawData.add((byte) this.tag.charAt(i));
            }
        }
        int series_of_event_int = this.series_of_event ? 1 : 0;
        rawData.add((byte) series_of_event_int);

        if (prevEventHash != null) {
            for (int i = 0; i < prevEventHash.length; i++) {
                rawData.add(prevEventHash[i]);
            }
        }

        byte[] publisherAddress = this.getPublisher().getEncoded();
        for(int i=0; i<publisherAddress.length;i++){
            rawData.add(publisherAddress[i]);
        }

        byte[] data = new byte[rawData.size()];
        int i = 0;
        for (Byte bb : rawData)
            data[i++] = bb;
        return data;
    }

    public void finalize(){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(getRawData());
            hash = md.digest();
        } catch (NoSuchAlgorithmException x) {
            x.printStackTrace(System.err);
        }
    }

    // get attributes
    public byte[] getHash() {
        return hash;
    }

    public String getSocial_id_key(){
        return this.social_id_key;
    }

    public String getEventMsg(){
        return this.eventMsg;
    }

    public String getTag(){
        return this.tag;
    }

    public boolean getSeries_of_event(){
        return this.series_of_event;
    }

    public byte[] getSignature(){
        return this.signature;
    }

    public PublicKey getPublisher(){
        return this.publisher;
    }

    public byte[] getPrevEventHash() {
        return prevEventHash;
    }

    public String getApproval() {
        return approval;
    }

    //approval state
    public void setApproval(String approval){
        Set<String> validKeyWords = new HashSet<String>(){
            {
                add("NA");
                add("pending");
                add("approve");
                add("reject");
            }
        };
        if(validKeyWords.contains(approval)){
            this.approval = approval;
        }else{
            System.out.println("invalid reply! please choose from 'pending','approve','reject'.");
        }
    }

    public void signEvent(PrivateKey sk) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(sk);
        sig.update(this.getRawData());
        this.addSignature(sig.sign());
        // hash the transaction
        this.finalize();
    }
}

