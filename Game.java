import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game {
    static Gui gui = new Gui();

    private final static String PATH_SAVE = "saved_file.obj";

    public static boolean gameIsOn = true;

    public static Player player = new Player();

    private static NpcActions npcActions = new NpcActions();
    private Thread npcThread;

    private static Update update = new Update();
    private Thread updateThread;

    private static final int totalRooms = 4;
    public static Room[] rooms = new Room[totalRooms];

    public Game(){
        if(!load_game()){
            init();
        }

        // npc AI thread
        npcThread = new Thread(npcActions);
        npcThread.start();

        // Keep gui up to date
        updateThread = new Thread(update);
        updateThread.start();
    }

    public void init(){
        // Create rooms
        createRooms();

        // Create key and container, put in random room
        GameObject[] keyContainer = Container.createContainerAndKey();

        ((Container) keyContainer[0]).getInventory().addItem(new EndObject("End Item"));

        rooms[(int) (Math.random() * 3)].getInventory().addItem(keyContainer[0]);
        rooms[(int) (Math.random() * 3)].getInventory().addItem(keyContainer[1]);

        // Set player in room 1
        player.switchRoom(1);
    }

    public static Person[] getNpcs(int room){
        return npcActions.getPersonInRoom(room);
    }

    private void createRooms(){
        // Information
        String[] roomName = {"Room 1", "Room 2", "Room 3", "Room 4"};

        // Create
        for(int i = 0; i < totalRooms; i++){
            rooms[i] = new Room(roomName[i], i);

            rooms[i].fillRoom();
        }
    }

    public static void command(String command){
        try {
            if (command.startsWith("Room")) {
                String level = command.split(" ")[1];

                player.switchRoom(Integer.parseInt(level));
            } else if (command.startsWith("Trade")) {
                String npc_name = command.split(" ")[1];
                String npc_item = command.split(" ")[2];
                String my_item = command.split(" ")[3];

                Npc npc = npcActions.findNpc(npc_name);
                if (npc != null) {
                    // Check if in same room
                    if(player.getCurrent_room() == npc.getCurrent_room()){
                        // Check if both have item
                        if (player.getInventory().findItem(my_item).length > 0
                                && npc.getInventory().findItem(npc_item).length > 0) {
                            npc.getInventory().switchItem(player.getInventory(), my_item);
                            player.getInventory().switchItem(npc.getInventory(), npc_item);
                        }
                    }
                }
            } else if (command.startsWith("Drop")) {
                String item = command.split(" ")[1];

                rooms[player.getCurrent_room() - 1].getInventory().switchItem(player.getInventory(), item);
            } else if (command.startsWith("Pickup")) {
                String item = command.split(" ")[1];

                player.getInventory().switchItem(rooms[player.getCurrent_room() - 1].getInventory(), item);
            } else if (command.equals("Open")) {
                System.out.println("trying to open");
                Key[] keys = player.getInventory().getKeys();
                if (keys.length > 0) {
                    Container[] containers = rooms[player.getCurrent_room() - 1].getInventory().getContainers();

                    // Find matching container
                    Container cont = null;
                    Key key = null;
                    for (Key keyl : keys) {
                        for (Container container : containers) {
                            if (keyl.isKeyTo(container)) {
                                cont = container;
                                break;
                            }
                        }

                        if (cont != null) {
                            key = keyl;
                            break;
                        }
                    }

                    if (cont != null) {
                        // Unlock container && move items
                        if (cont.getInventory().checkEnd()) {
                            System.out.println("Won the game");
                            exit();
                        }
                        cont.getInventory().switchAll(rooms[player.getCurrent_room() - 1].getInventory());

                        // Rewmove from room
                        rooms[player.getCurrent_room() - 1].getInventory().removeItem(cont);
                        player.getInventory().removeItem(key);
                    }

                }
            } else if(command.equals("Save")){
                save_game();
            } else if (command.equals("Exit")) {
                exit();
            }
        }
        catch(Exception ignored){}
    }

    public static void exit(){
        gui.dispose();
        gameIsOn = false;
    }


    public static void save_game(){
        try {
            FileOutputStream fileOut = new FileOutputStream(PATH_SAVE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(player);
            objectOut.writeObject(rooms);
            objectOut.writeObject(npcActions.npcs);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean load_game(){
        try {

            FileInputStream fileIn = new FileInputStream(PATH_SAVE);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object player_ = objectIn.readObject();
            Object npcs_ = objectIn.readObject();
            Object rooms_ = objectIn.readObject();

            player = (Player) player_;
            rooms = (Room[]) rooms_;
            npcActions.npcs = (Person[]) npcs_;

            System.out.println("The Object has been read from the file");
            objectIn.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
