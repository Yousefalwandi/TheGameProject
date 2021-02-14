import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class NpcActions implements Runnable{

    public Person[] npcs;
    private String[] npc_names = {"Test1" ,"Test2", "Test3", "Test4"};
    private String[] npc_phrases = {"ASfgjqiwf qpwf qwjfq wopjfwqopjf ",
                                    "wqefgpeqgfjp eeqwgpo wg",
                                    "fjpwejfopwef", "qfpojqwejfkopwq qwf okqwf"};

    public NpcActions(){
        npcs = new Person[npc_names.length];

        for(int i = 0; i < npc_names.length; i++){
            npcs[i] = new Person(npc_names[i]);
            npcs[i].forceRoom((int) (Math.random() * 4) + 1); // Force setting a room
            npcs[i].setPhrase(npc_phrases[i]);
            npcs[i].fillInventory();
        }
    }

    public Person findNpc(String name){
        return Arrays.stream(npcs)
                .filter(x -> x.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Person[] getPersonInRoom(int room){
        Person[] found = new Person[npcs.length];

        for(int i = 0; i < npcs.length; i++){
            if(npcs[i].getCurrent_room() == room){
                found[i] = npcs[i];
            }
        }

        return Arrays.stream(found)
                .filter(Objects::nonNull)
                .toArray(Person[]::new);
    }

    @Override
    public void run() {
        while(Game.gameIsOn){
            // Do an npc action for each
            Arrays.stream(npcs)
                .forEach(person -> {
                    int action = (int) (Math.random() * 5) + 1;

                    switch(action){
                        case 1:
                            // Switch room left side
                            person.switchRoom(person.getCurrent_room() - 1);
                            break;
                        case 2:
                            // Switch room right side
                            person.switchRoom(person.getCurrent_room() + 1);
                            break;
                        case 3:
                            // Pick up some random shit
                            person.getInventory().takeRandom(Game.rooms[person.getCurrent_room() - 1].getInventory());
                            break;
                        case 4:
                            // Drop some random shit
                            person.getInventory().dropRandom(Game.rooms[person.getCurrent_room() - 1].getInventory());
                            break;
                        case 5:
                            // Say special phrase
                            if(Game.player.getCurrent_room() == person.getCurrent_room())
                                Game.gui.setPhrase(person.getPhrase());
                            break;
                    }
                });

            // Wait 5 sec after each action
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
