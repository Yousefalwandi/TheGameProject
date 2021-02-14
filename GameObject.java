import java.io.Serializable;

public abstract class GameObject implements Serializable {
    private String name;

    public GameObject(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name;
    }
}
