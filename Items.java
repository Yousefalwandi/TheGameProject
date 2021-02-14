import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Items {
    private static GameObject[] items = {
            new Item("Crowbar"),
            new Item("TEst"),
            new Item("QEFqf "),
            new Item("qdwqd"),
            new Item("fweopfwe"),
            new Item("Table"),
            new Item("Chair"),
            new Item("qwdfqjw"),
            new Item("wqfpqw"),
            new Item("Ma"),
            new Item("La"),
            new Item("Eye"),
            new Item("Head"),
            new Item("Skull")
    };

    public static GameObject[] getRandomItems(int total){
        if(total > items.length)
            return null;

        // Add some null values
        GameObject[] nullitems = new GameObject[items.length*2];
        for(int i = 0; i < items.length; i++){
            nullitems[i] = items[i];
        }

        List<GameObject> shuffle = Arrays.stream(nullitems)
                                    .collect(Collectors.toList());

        Collections.shuffle(shuffle);

        return shuffle.stream().limit(total).toArray(GameObject[]::new);
    }
}
