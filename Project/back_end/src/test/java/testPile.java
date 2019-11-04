import org.junit.Test;

import java.util.ArrayList;

public class testPile {
    //add element in sequent
    public Pile add_sequence(String s1, String s2, String s3){
        Pile pile = new Pile();
        pile.push_log(s1);
        pile.push_log(s2);
        pile.push_log(s3);
        return pile;
    }
    @Test
    public void printPile(){
        Pile pile = add_sequence("me", "you", "ss");
        for(int i=0; i<pile.get_length();i++){
            String element = pile.get_element(i);
            System.out.println(i+"th element is: "+element);
        }
    }
}
