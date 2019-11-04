import org.junit.Test;

import java.math.BigInteger;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;

public class testEvent {
    public KeyPair[] init_user(int user_number) throws NoSuchAlgorithmException {
        KeyPair[] keyPairs = new KeyPair[user_number];
        for(int i =0; i<user_number;i++){
            keyPairs[i] = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        }
        return keyPairs;
    }

    public Event generateEvent(String social_id_key, String eventMsg, String tag,
                               boolean series_of_event, byte[] prevEventHash, PrivateKey sk, PublicKey pk) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Event sampleEvent = new Event(social_id_key, eventMsg, tag, series_of_event, prevEventHash,pk);
        sampleEvent.signEvent(sk);
        return sampleEvent;
    }

    public void printEvent(String social_id_key, String eventMsg, String tag,
                           boolean series_of_event, byte[] prevEventHash, PrivateKey sk, PublicKey pk) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Event sampleEvent = generateEvent(social_id_key, eventMsg, tag, series_of_event, prevEventHash, sk, pk);
        System.out.println("Hash code: "+sampleEvent.hashCode());
        System.out.println("getHash(): "+ Arrays.toString(sampleEvent.getHash()));
        System.out.println("Social ID key: "+sampleEvent.getSocial_id_key());
        System.out.println("Event message: "+sampleEvent.getEventMsg());
        System.out.println("Tag: "+sampleEvent.getTag());
    }

    // test the class of Event work properly
    @Test
    public void testCase() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        KeyPair user = init_user(1)[0];
        PrivateKey user_sk = user.getPrivate();
        PublicKey user_pk = user.getPublic();
        String social_id_key = "001";
        String eventMsg = "Mike is gonna SZ";
        String tag = "household";
        boolean series_of_event = true;
        byte[] prevEventHash = null;
        printEvent(social_id_key, eventMsg, tag, true, null, user_sk, user_pk);
    }

    // test pubKey and privateKey must be paired
    @Test
    public void testCase2() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        KeyPair[] users = init_user(2);
        PrivateKey user0_sk = users[0].getPrivate();
        PrivateKey user1_sk = users[1].getPrivate();
        PublicKey user1_pk = users[1].getPublic();
        String social_id_key = "001";
        String eventMsg = "Mike is gonna SZ";
        String tag = "household";
        boolean series_of_event = true;
        byte[] prevEventHash = null;

        Event sampleEvent = generateEvent(social_id_key, eventMsg, tag, true, null, user1_sk, user1_pk);

        ExternalOwnedUser sampleEOA = new ExternalOwnedUser(user1_pk, "common_user");
        PersonalFileAccount samplePFA = new PersonalFileAccount(social_id_key, new BigInteger("123409"));

        // test point
        sampleEvent.setApproval("not approve");

        EventPool eventPool = new EventPool();
        eventPool.addEvent(sampleEvent, sampleEOA);

        PersonalFileAccountUTXOPool p_utxoPool = new PersonalFileAccountUTXOPool();
        p_utxoPool.addElement(sampleEvent, samplePFA);

        EventHandler eventHandler = new EventHandler(eventPool, p_utxoPool);

        // test point
        System.out.println("is valid event? "+eventHandler.isValidEvent(sampleEvent));
    }
}
