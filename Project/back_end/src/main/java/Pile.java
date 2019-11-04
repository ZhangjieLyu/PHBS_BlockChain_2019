import java.util.ArrayList;

public class Pile {
    /**
     * Pile is a data structure,
     * can only add string element, no pop attribute
     */
    private ArrayList<String> Pile;

    public Pile(){
        this.Pile = new ArrayList<String>();
    }

    public Pile(ArrayList<String> log){
        this.Pile = log;
    }

    public void push_log(String log){
        this.Pile.add(log);
    }

    public int get_length(){
        return this.Pile.size();
    }

    public ArrayList<String> get_elements(){
        return this.Pile;
    }

    public String get_element(int index){
        return this.Pile.get(index);
    }
}
