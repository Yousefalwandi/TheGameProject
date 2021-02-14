import java.io.Serializable;

public class Player implements Serializable {
    private Gui gameGui;
    private int current_room = 0;

    private Inventory inventory;

    public Player(){

        this.inventory = new Inventory(10);
    }

    public void switchRoom(int i){
        int current = current_room;

        if(i < 1
                || i > 4
                || Math.abs(current - i) > 1
                || current_room == i){
            return;
        }


        this.current_room = i;
    }

    public int getCurrent_room() {
        return current_room;
    }

    public Inventory getInventory() {
        return inventory;
    }
}

