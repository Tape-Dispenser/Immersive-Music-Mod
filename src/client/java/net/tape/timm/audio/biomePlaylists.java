package net.tape.timm.audio;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static net.tape.timm.timmMain.LOGGER;


public class biomePlaylists {
    public static Map<String, Set<String>> playlists = new HashMap<>();

    public static void registerPlaylists() {
        String json;

        try {
            json = Files.readString(Path.of(String.format("%s/timm/biomePlaylists.json", FabricLoader.getInstance().getConfigDir().toString())));
        } catch (IOException e) {
            LOGGER.warn("Cannot find biome playlists file! Starting mod in single-playlist mode!");
            modConfig.singlePlaylist = true;
            return;
        }

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            // iterate through all playlists in json file
            Set<String> songs = new HashSet<>();
            if (!entry.getValue().isJsonArray()) {
                continue;
            }

            JsonArray playlist = entry.getValue().getAsJsonArray();
            for (JsonElement element : playlist) {
                // iterate through each song group in playlist
                String songGroup = element.getAsString();
                ArrayList<String> parts = new ArrayList<>(List.of(songGroup.split(":")));
                if (!Objects.equals(parts.get(0), "timm")) {
                    // only timm songs follow song group format (technically mc songs do too but idgaf)
                    songs.add(songGroup);
                    continue;
                }

                songGroup = parts.get(1);
                songs.addAll(SongRegistry.songsInGroup(songGroup));
            }
            playlists.put(entry.getKey(), songs);
        }

        if (!modConfig.debugLogging) {return;}

        for (Map.Entry<String, Set<String>> entry : playlists.entrySet()) {
            String message = String.format("%s:\n", entry.getKey());
            for (String song : entry.getValue()) {
                message = String.format("%s\t%s\n", message, song);
            }
            LOGGER.info(message);
        }
    }
}
