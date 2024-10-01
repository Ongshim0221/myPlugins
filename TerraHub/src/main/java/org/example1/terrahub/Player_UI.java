package org.example1.terrahub;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static org.example1.terrahub.TerraHub.Config;

public class Player_UI implements Listener {
    Inventory Player_UI;
    Server server;
    ItemStack BlockPlace_icon;

    public void getserver(Server server) {
        this.server = server;
    }

    public void setup_icon() {

    }

    public void setup_UI() {
        if (Config.getString("Language").equals("en")) {
            Player_UI = server.createInventory(null, 54, "Chunk Settings");
        }else if (Config.getString("Language").equals("kr")) {
            Player_UI = server.createInventory(null, 54, "청크 설정");
        }

    }
    public void show_UI(Player player) {
        player.openInventory(Player_UI);
    }
}
