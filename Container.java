public class Container extends GameObject{
    private Inventory inventory;
    private boolean locked;

    private final static int MAX_CAP = 5;

    public Container(String name){
        super(name);
        this.inventory = new Inventory(MAX_CAP);
    }

    public static GameObject[] createContainerAndKey(){
        Key key = new Key("Key");
        Container container = new Container("Container");

        key.setContainer(container);

        return new GameObject[]{
                container,
                key
        };
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
