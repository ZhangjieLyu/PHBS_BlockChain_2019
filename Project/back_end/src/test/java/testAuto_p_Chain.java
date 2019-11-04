import org.junit.Test;

import java.math.BigInteger;
import java.security.*;
import java.util.*;

public class testAuto_p_Chain {
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

    public void printEvent(Event event){
        System.out.println("-----------------------Start of An Event------------------------");
        System.out.println("Personal Social ID: "+event.getSocial_id_key());
        System.out.println("Description of Event: "+event.getEventMsg());
        System.out.println("Tag of Event: "+event.getTag());
        System.out.println("Belong to Series? "+event.getSeries_of_event());
        System.out.println("Publisher: "+event.getPublisher().hashCode());
        System.out.println("Receipt of Event: "+event.getApproval());
        System.out.println("------------------------End of An Event-------------------------");
    }

    public void printEOA(ExternalOwnedUser eoa){
        System.out.println("-----------------------Start of An External Owned User-----------------");
        System.out.println("EOA Public Key: "+eoa.get_id_key().hashCode());
        System.out.println("Balance: "+eoa.get_balance());
        System.out.println("Authority Level: "+eoa.get_auth());
        System.out.println("Maximum Constant Working Limit: "+eoa.get_coolDownTime());
        System.out.println("#Processed Cases: "+eoa.get_nonce());
        System.out.println("Can Action? "+eoa.get_canAction());
        System.out.println("-----------------------End of An External Owned User-------------------");
    }

    public void printPersonalLog(PersonalFileAccount p_account){
        System.out.println("Personal Log Start:");
        for(String tag:p_account.getPersonalLog().keySet()){
            System.out.println("Tag of log: "+tag);
            for(int i=0;i<p_account.getPersonalLog().get(tag).get_length();i++){
                System.out.println(p_account.getPersonalLog().get(tag).get_element(i));
            }
            System.out.println("Tag of log" + tag+" ==End!");
        }
        System.out.println("Personal Log End!");
    }

    public void printPFA(PersonalFileAccount p_account){
        System.out.println("-----------------------Start of A Personal File Account ----------------");
        System.out.println("Personal Social ID:" + p_account.getSocial_id_key());
        printPersonalLog(p_account);
        System.out.println("Record of Event Series: ");
        for(byte[] b: p_account.getUtxoPool().keySet()){
            System.out.println("series of event element: "+ Arrays.toString(b));
        }
        System.out.println("----------------------End of A Personal File Account ----------------------");
    }

    // init EOAs
    public HashMap<ExternalOwnedUser, PrivateKey> init_EOAs(int NumberOfEOA) throws NoSuchAlgorithmException {
        KeyPair[] init_users = init_user(NumberOfEOA);
//        ArrayList<ExternalOwnedUser> EOAs = new ArrayList<ExternalOwnedUser>();
        HashMap<ExternalOwnedUser, PrivateKey> EOAs = new HashMap<ExternalOwnedUser, PrivateKey>();
        for(int i=0;i<NumberOfEOA;i++){
            ExternalOwnedUser sampleEOA = new ExternalOwnedUser(init_users[i].getPublic(), "common_user");
            EOAs.put(sampleEOA,init_users[i].getPrivate());
        }
        return EOAs;
    }

    // get random string with fixed length
    public static String getRandomString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    // get random social key
    public String[] init_social_id_key(int NumberOfSIK, int length){
        String[] socialKeys = new String[NumberOfSIK];
        for(int i=0; i<NumberOfSIK; i++){
            socialKeys[i] = getRandomString(length);
        }
        return socialKeys;
    }

    // init Personal File Accounts
    public ArrayList<PersonalFileAccount> init_p_accounts(int NumberOfPAccount) {
        ArrayList<PersonalFileAccount> p_accounts = new ArrayList<PersonalFileAccount>();
        String[] SIKs = init_social_id_key(NumberOfPAccount,5);
//        String[] BigIntegers = init_social_id_key(NumberOfPAccount, 5);
        for(int i=0; i<NumberOfPAccount; i++){
            p_accounts.add(new PersonalFileAccount(SIKs[i],new BigInteger("1111")));
        }
        return p_accounts;
    }

    // test block chain
    @Test
    public void testCase() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        // init EOA
        HashMap<ExternalOwnedUser, PrivateKey> EOA_sk_pairs = init_EOAs(10);
        ArrayList<ExternalOwnedUser> EOAs = new ArrayList<ExternalOwnedUser>();
        for(ExternalOwnedUser eoa:EOA_sk_pairs.keySet()){
            EOAs.add(eoa);
        }

        ArrayList<PrivateKey> skCollection = new ArrayList<PrivateKey>();
        for(ExternalOwnedUser eoa:EOA_sk_pairs.keySet()){
            skCollection.add(EOA_sk_pairs.get(eoa));
        }

        // init Personal File Accounts
        final ArrayList<PersonalFileAccount> personalFileAccounts = init_p_accounts(10);

        // miner 0
        final ExternalOwnedUser miner0 = EOAs.get(0);
        miner0.set_auth("miner");
        PublicKey miner0_pk = EOAs.get(0).get_id_key();
        PrivateKey miner0_sk = skCollection.get(0);

        // miner 1
        final ExternalOwnedUser miner1 = EOAs.get(1);
        miner1.set_auth("miner");
        PublicKey miner1_pk = EOAs.get(1).get_id_key();
        PrivateKey miner1_sk = skCollection.get(1);

        // common_user0
        final ExternalOwnedUser c_user0 = EOAs.get(2);
        PublicKey c_user0_pk = EOAs.get(2).get_id_key();
        PrivateKey c_user0_sk = skCollection.get(2);

        // common_user1
        final ExternalOwnedUser c_user1 = EOAs.get(3);
        PublicKey c_user1_pk = EOAs.get(3).get_id_key();
        PrivateKey c_user1_sk = skCollection.get(3);

        // sample events
        Event event0 = generateEvent(personalFileAccounts.get(0).getSocial_id_key(),"get MA from PKU","education",false,null,c_user0_sk,c_user0_pk);
        event0.setApproval("approve");

        Event event1 = generateEvent(personalFileAccounts.get(0).getSocial_id_key(), "get Ph.D. from THU", "education",false,null,c_user1_sk,c_user1_pk);
        event1.setApproval("approve");

        Event event2_1 = generateEvent(personalFileAccounts.get(0).getSocial_id_key(), "transfer household out of BJ","household",true,null,c_user0_sk,c_user0_pk);
        event2_1.setApproval("approve");

        Event event2_2 = generateEvent(personalFileAccounts.get(0).getSocial_id_key(), "transfer household into SZ","household",true,event2_1.getHash(),c_user0_sk,c_user0_pk);
        event2_2.setApproval("reject");

        // start block chain!
        Block genesisBlock = new Block(null,miner0);
        ArrayList<ExternalOwnedUser> awaitEOAs = new ArrayList<ExternalOwnedUser>(){
            {
                add(miner0);
                add(miner1);
                add(c_user0);
                add(c_user1);
            }
        };
        ArrayList<PersonalFileAccount> awaitPFA = new ArrayList<PersonalFileAccount>(){
            {
                add(personalFileAccounts.get(0));
                add(personalFileAccounts.get(1));
                add(personalFileAccounts.get(2));
            }
        };
        genesisBlock.finalize();
        BlockChain blockChain = new BlockChain(genesisBlock, awaitEOAs, awaitPFA);

        // build a new block
        Block block1 = new Block(genesisBlock.getHash(), miner1);
        block1.addEvent(event0);
        block1.addEvent(event1);
        blockChain.automaticExtendBlockChain(block1,
                new ArrayList<ExternalOwnedUser>(),
                new ArrayList<ExternalOwnedUser>(),
                new ArrayList<PersonalFileAccount>(),
                new ArrayList<PersonalFileAccount>());

        //  print block content
        blockChain.printLatestBlockContent();
    }
}
