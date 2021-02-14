import java.io.Serializable;

public class Person extends Npc implements Serializable {

    private static final int CAPACITY = 5;

    private String phrase;

    public Person(String npc_name) {
        super(CAPACITY);
        this.setName(npc_name);
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public String toString(){
        return this.showPerson();
    }
}
