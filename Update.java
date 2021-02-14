public class Update implements Runnable{
    @Override
    public void run() {
        while(Game.gameIsOn){
            int current_room = Game.player.getCurrent_room();

            // Set room
            Game.gui.setShowRoom(Game.rooms[current_room-1].show());

            // Set player inventory
            Game.gui.setShowInventory(Game.player.getInventory());

            // Set NPCS in room
            Game.gui.setShowPersons(Game.getNpcs(current_room));

            // Check if end found
            if(Game.player.getInventory().findItem("End").length > 0){
                System.out.println("End item found, quiting game");

                Game.exit();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
