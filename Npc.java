public abstract class Npc{
    private int current_room = 0;

    private String name;
    private Inventory inventory;

    private int capacity;

    public Npc(int capacity){
        this.inventory = new Inventory(capacity);
        this.capacity = capacity;
    }

    public void switchRoom(int i){
        if(i < 1
                || i > 4
                || Math.abs(current_room - i) > 1
                || current_room == i){
            return;
        }


        this.current_room = i;
    }

    public void forceRoom(int i){
        this.current_room = i;
    }

    public int getCurrent_room() {
        return current_room;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void fillInventory(){
        this.inventory.setInventory(Items.getRandomItems(capacity));
    }

    protected void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String showPerson(){
        return this.name + " - (" + inventory.toString() + ")";
    }
}
