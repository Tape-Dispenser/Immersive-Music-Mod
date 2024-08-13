package net.tape.timm.audio;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.biome.Biome;
import net.tape.timm.BaseManager;
import net.tape.timm.modConfig;
import net.tape.timm.songControls;
import net.tape.timm.timmMain;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static net.tape.timm.timmMain.LOGGER;

public class SongSelector {

    public static Song pickSong(Random rng) {
        // playlists aren't implemented properly yet, just return a random song
        ArrayList<Song> selectionPool = getPlaylists();
        int songCount = selectionPool.size();
        if (songCount == 0) {
            LOGGER.warn("Warning: no available songs to play!");
            return null;
        }

        if (songCount == 1) {
            LOGGER.warn("Warning: only one song to play!");
            return selectionPool.get(0);
        }

        Song tempSong = songControls.lastSong;
        while (tempSong == songControls.lastSong) {
            tempSong = selectionPool.get(rng.nextInt(songCount));
        }

        return tempSong;
    }

    public static ArrayList<Song> getPlaylists() {
        ArrayList<Song> selectionPool = new ArrayList<>();

        // main menu
        if (timmMain.mc.world == null) {
            // game has not started, therefore must be in main menu
            selectionPool.addAll(biomePlaylists.getPlaylist("menu"));
            return selectionPool;
        }
        if (timmMain.mc.player == null) {
            // if you actually manage to get here i'm impressed
            if (modConfig.debugLogging) {
                LOGGER.info("how tf did this end up running");
            }
            return selectionPool;
        }

        // single playlist mode
        if (modConfig.singlePlaylist) {
            selectionPool.addAll(biomePlaylists.getPlaylist("master"));
            return selectionPool;
        }

        // test for creative mode
        if (timmMain.mc.interactionManager == null) {
            return selectionPool;
        }
        if (timmMain.mc.interactionManager.getCurrentGameMode() == GameMode.CREATIVE) {
            selectionPool.addAll(biomePlaylists.getPlaylist("creative"));
        }

        // test if in base
        if (BaseManager.isInBase()) {
            selectionPool.addAll(biomePlaylists.getPlaylist("base"));
            if (!modConfig.mixBaseDefault) {
                return selectionPool;
            }
        }

        // get dimension and biome
        String dimension = timmMain.mc.world.getDimensionKey().getValue().toString();
        Optional<RegistryKey<Biome>> biomeEntry = timmMain.mc.world.getBiome(timmMain.mc.player.getBlockPos()).getKey();
        String biome = biomeEntry.isEmpty()? null : biomeEntry.get().getValue().toString();

        switch (dimension) {
        case "minecraft:the_end":
            // do end ring selection

            selectionPool.addAll(endSongSelection());
            return selectionPool;
        case "minecraft:overworld":
            boolean night = false;
            long time = timmMain.mc.world.getTimeOfDay();
            if (time >= 13000) {
                night = true;
            }
            if (modConfig.superflatMode) {
                selectionPool.addAll(biomePlaylists.getPlaylist(dimension));
                if (night) {
                    selectionPool.addAll(biomePlaylists.getPlaylist(String.format("%s_night", dimension)));
                }
                return selectionPool;
            }

            if (timmMain.mc.player.getBlockY() < 50) {
                selectionPool.addAll(biomePlaylists.getPlaylist("underground_overworld"));
                return selectionPool;
            }

            if (night) {
                selectionPool.addAll(biomePlaylists.getPlaylist(String.format("%s_night", biome)));
            }
            break;
        case "minecraft:the_nether":
            if (timmMain.mc.player.getBlockY() < 30) {
                selectionPool.addAll(biomePlaylists.getPlaylist("underground_nether"));
                return selectionPool;
            }
            break;
        default:
            break;
        }

        selectionPool.addAll(biomePlaylists.getPlaylist(biome));
        return selectionPool;
    }

    public static ArrayList<Song> endSongSelection() {
        assert timmMain.mc.player != null;
        int xPos = timmMain.mc.player.getBlockX();
        int zPos = timmMain.mc.player.getBlockZ();
        ArrayList<Song> ringList = new ArrayList<>();
        double distance = sqrt(xPos*xPos + zPos*zPos);
        if (distance < 300) {
            ringList.addAll(biomePlaylists.getPlaylist("end_ring0"));
        } else if (distance < 1000) {
            ringList.addAll(biomePlaylists.getPlaylist("end_ring1"));
        } else if (distance < 1700) {
            ringList.addAll(biomePlaylists.getPlaylist("end_ring2"));
        } else if (distance < 2400) {
            ringList.addAll(biomePlaylists.getPlaylist("end_ring3"));
        } else {
            ringList.addAll(biomePlaylists.getPlaylist("end_ring4"));
        }
        return ringList;
    }
}
