import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Inventory implements Serializable {
    private int capacity;
    private GameObject[] objects;

    public Inventory(int capacity){
        this.capacity = capacity;

        objects = new GameObject[capacity];
    }

    public void getObject(int id){
        Stream<GameObject> stream = Arrays.stream(objects);
    }

    public void setInventory(GameObject[] objs){
        objects = objs;
    }

    public void addItem(GameObject obj){
        if(size() < capacity){
            // Put null last
            GameObject[] newObjects = Arrays.stream(objects)
                                        .sorted(Comparator.nullsLast(Comparator.comparing(GameObject::getName)))
                                        .toArray(GameObject[]::new);

            // Add last
            newObjects[size()] = obj;
            this.objects = newObjects;
        }
    }

    public GameObject[] findItem(String name){
        GameObject[] newObjects = Arrays.stream(objects)
                                    .filter(Objects::nonNull)
                                    .filter( obj -> obj.getName().equals(name))
                                    .toArray(GameObject[]::new);
        return newObjects;
    }

    public void removeItem(GameObject obj){
        this.objects = Arrays.stream(objects)
                        .filter(obj2 -> obj2 != obj)
                        .toArray(GameObject[]::new);
    }

    public void switchItem(Inventory inv, String name){
        // Full inventory
        if(capacity == size()
            || inv.capacity == inv.size())
            return;

        // Switch
        GameObject obj = Arrays.stream(inv.objects)
                            .filter(Objects::nonNull)
                            .filter(x -> x.getName().equals(name)
                                    && !(x instanceof Container)) // Disable picking up containers
                            .findFirst().orElse(null);

        if(obj != null){
            inv.removeItem(obj);
            addItem(obj);
        }
    }

    public void switchAll(Inventory inventory) {
        Arrays.stream(objects)
                .filter(Objects::nonNull)
                .forEach(obj -> {
                    inventory.addItem(obj);
                    removeItem(obj);
                });
    }

    public Container[] getContainers(){
        return Arrays.stream(objects)
                .filter(Objects::nonNull)
                .filter(x -> x instanceof Container)
                .toArray(Container[]::new);
    }

    public Key[] getKeys(){
        return Arrays.stream(objects)
                .filter(Objects::nonNull)
                .filter(x -> x instanceof Key)
                .toArray(Key[]::new);
    }

    public int size(){
        for(int i = 0; i < capacity; i++){
            if(objects[i] == null)
                return i;
        }

        return objects.length;
    }

    public String showItems(){
        StringBuilder res = new StringBuilder();
        GameObject[] items = Arrays.stream(objects)
                .filter(Objects::nonNull)
                .toArray(GameObject[]::new);

        for(int i = 0; i < items.length; i++){
            res.append(items[i]).append(" - ");
        }

        return res.toString();
    }

    public String toString(){
        return showItems();
    }

    // Take a random
    public void takeRandom(Inventory inv) {
        List<GameObject> items = Arrays.stream(inv.objects)
                                .filter(Objects::nonNull)
                                .filter(x -> !(x instanceof Container)) // Prevent picking up container
                                .collect(Collectors.toList());

        Collections.shuffle(items);

        // Only take if it has items
        if(items.size() > 0){
            GameObject item = items.get(0);
            this.addItem(item);
            inv.removeItem(item);
        }
    }

    // Drop random
    public void dropRandom(Inventory inv) {
        List<GameObject> items = Arrays.stream(objects)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Collections.shuffle(items);

        // Only take if it has items
        if(items.size() > 0){
            GameObject item = items.get(0);
            inv.addItem(item);
            this.removeItem(item);
        }
    }

    public boolean checkEnd() {
        return Arrays.stream(objects)
                .filter(Objects::nonNull)
                .filter(x -> x instanceof EndObject)
                .toArray(GameObject[]::new).length > 0;
    }
}
