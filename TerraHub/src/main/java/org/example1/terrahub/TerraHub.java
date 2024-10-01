package org.example1.terrahub;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class TerraHub extends JavaPlugin {

    private static Economy economy = null;
    public static FileConfiguration Config;
    public static File File;
    public static FileConfiguration CChunk;
    public static File ChunkFile;

    private Player_UI playerUi = new Player_UI();

    @Override
    public void onEnable() {
        getCommand("tr").setExecutor(new Commands());
        getCommand("tr").setTabCompleter(new Commands());
        getServer().getPluginManager().registerEvents(new Player_UI(), this);
        createFile();
        createChunkFile();
        setDataConfigDefault();
        playerUi.getserver(getServer());

        // Vault가 있는지 확인하고 경제 플러그인을 설정
        if (!setupEconomy()) {
            getLogger().severe("Vault not found!");
            getServer().getPluginManager().disablePlugin(this); // 플러그인 비활성화
            return;
        }

        getLogger().info("Vault found!");
    }

    @Override
    public void onDisable() {
        TerraHub.saveChunk();
    }

    // Vault의 경제 시스템 설정
    private boolean setupEconomy() {
        // Vault 플러그인이 설치되어 있는지 확인
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        // 경제 기능을 제공하는 플러그인을 찾아서 등록
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider(); // economy 변수에 경제 플러그인 연결
        return economy != null;
    }

    // 다른 클래스에서 Economy 객체를 사용할 수 있도록 getter 메서드 제공
    public static Economy getEconomy() {
        return economy;
    }

    public void createFile() {
        File = new File(getDataFolder(), "Setting.yml");
        if (!File.exists()) {
            File.getParentFile().mkdirs(); // 디렉토리 생성
            try {
                File.createNewFile();  // 파일 생성
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Config = YamlConfiguration.loadConfiguration(File); // 파일 불러오기
    }
    public static void saveDataFile() {
        try {
            Config.save(File);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setDataConfigDefault() {
        // 거점 크기 (반지름)
        Config.addDefault("StartPrice", 1000);
        Config.addDefault("Language", "en");
        Config.options().copyDefaults(true);
        saveDataFile();
    }

    public void createChunkFile() {
        ChunkFile = new File(getDataFolder(), "Chunk.yml");
        if (!ChunkFile.exists()) {
            ChunkFile.getParentFile().mkdirs(); // 디렉토리 생성
            try {
                ChunkFile.createNewFile();  // 파일 생성
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        CChunk = YamlConfiguration.loadConfiguration(ChunkFile); // 파일 불러오기
    }
    public static void saveChunk() {
        try {
            CChunk.save(ChunkFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
