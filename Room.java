import java.io.Serializable;

public class Room implements Serializable {
    private int number;
    private String name;

    private Inventory inventory;

    private static final int CAPACITY = 20;

    public Room(String name, int i){
        this.number = i;
        this.name = name;
        this.inventory = new Inventory(CAPACITY);
    }

    public String show() {
        // ska visa items i rummet
        String result = this.name + "\n" +
                "Inventory: " + this.inventory.showItems();

        return result;
    }

    public void fillRoom(){
        this.inventory.setInventory(Items.getRandomItems(10));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
