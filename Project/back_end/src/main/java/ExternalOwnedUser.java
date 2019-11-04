import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExternalOwnedUser {
    /**
     * EOA are common users/miners/validator in p-chain, they're processor instead of owner of each personal file.
     * 5 attributes
     * id_key: institute identification code, public key
     * balance: altcoin owned by EOA
     * auth: type of EOA, miner/common_user/validator
     * coolDownTime: for miner/validator, restriction constant mining/validating
     * nonce: counter, an incentive, major usage is to work with "coolDownTime", can also be used as an incentive
     */
    private PublicKey id_key;
    private double balance;
    private String auth;
    private int coolDownTime;
    private int nonce;
    private boolean canAction;

    private static int COOL_DOWN_TIME = 10;

    // a constructor
    public ExternalOwnedUser(){
        this.id_key = null;
        this.balance = 0;
        this.auth = new String("common_user");
        this.coolDownTime = COOL_DOWN_TIME; // for simplicity, validator and miner share the same cool down time
        this.nonce = 0;
        this.canAction = true;
    }

    // another constructor
    public ExternalOwnedUser(PublicKey id_key, String auth){
        this.id_key = id_key;
        this.balance = 0;
        this.auth = auth;
        this.coolDownTime = COOL_DOWN_TIME;
        this.nonce = 0;
        this.canAction = true;
    }

    // get attributes
    public PublicKey get_id_key(){
        return this.id_key;
    }
    public double get_balance(){
        return this.balance;
    }
    public String get_auth(){
        return this.auth;
    }
    public int get_coolDownTime(){
        return this.coolDownTime;
    }
    public int get_nonce(){
        return this.nonce;
    }
    public boolean get_canAction(){ return this.canAction; }

    // change attribute
    public void set_auth(String auth){
        Set<String> validAuth = new HashSet<String>(Arrays.asList("common_user","miner","validator"));
        if(!validAuth.contains(auth)||auth == null){
            System.out.println("invalid authority level!");
        }else{
            this.auth = auth;
        }
    }

    // this is a back door for test
    public void set_balance(int balance){
        if(balance<0){
            System.out.println("invalid balance!should be non-negative");
        }else{
            this.balance = balance;
        }
    }

    // add balance
    public void add_balance(double balance){
        if(balance<=0){
            System.out.println("Invalid add balance");
        }else{
            this.balance += balance;
        }
    }

    // embedded EOA handler
    public void EOAHandler(){
        this.nonce += 1;
        if(nonce % COOL_DOWN_TIME == 0){
//            this.coolDownTime += 1;
            this.canAction = false;
        }else{
            this.canAction = true;
        }
        if(this.auth.equals("common_user")){
            this.coolDownTime = 0;
            this.canAction = true;
        }
    }

}
