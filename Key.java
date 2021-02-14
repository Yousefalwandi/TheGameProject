public class Key extends GameObject {
    private Container container;

    public Key(String name) {
        super(name);
    }

    public void setContainer(Container container){
        this.container = container;
    }

    public boolean isKeyTo(Container container){
        return this.container == container;
    }
}
