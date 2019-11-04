import org.junit.Test;

import java.security.*;
import java.util.Arrays;

public class testValidSignature {
    @Test
    public void testCase() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPair user = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        PublicKey pk = user.getPublic();
        PrivateKey sk = user.getPrivate();

        String social_id_key = "001";
        String eventMsg = "Mike is gonna SZ";
        String tag = "household";

        Event sampleEvent = new Event(social_id_key, eventMsg, tag, false, null, pk);
        sampleEvent.signEvent(sk);
        System.out.println("signature: "+ Arrays.toString(sampleEvent.getSignature()));
        System.out.println("raw message:"+ Arrays.toString(sampleEvent.getRawData()));
        System.out.println(Crypto.verifySignature(pk,sampleEvent.getRawData(),sampleEvent.getSignature()));
    }
}
