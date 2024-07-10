package net.tape.timm.audio;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SongRegistry {

    public static Map<String, Song> songList = new HashMap<String, Song>();

    public static void init() {
        // open songList.json
        String json;
        String songListPath = String.format("%s/music/TIMM/songList.json", FabricLoader.getInstance().getGameDir());
        try {
            json = Files.readString(Path.of(songListPath));
        } catch (IOException e) {
            timmMain.LOGGER.error("Failed to find songList.json!");
            throw new RuntimeException(e);
        }

        // load json file into JsonObject
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        // get songs as a set of key pairs
        JsonObject songs = obj.get("songs").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> localEntries = songs.entrySet();

        // iterate through all song json objects
        for (Map.Entry<String, JsonElement> entry : localEntries) {
            Song song = null;

            // get song metadata
            String songID = entry.getKey();
            JsonObject songObj = entry.getValue().getAsJsonObject();
            String songName = songObj.get("song_name").getAsString();
            String author = songObj.get("author").getAsString();

            if (songObj.get("is_file?").getAsBoolean()) {
                String fileName = songObj.get("file/id").getAsString();
                String filePath = String.format("%s/music/TIMM/%s", FabricLoader.getInstance().getGameDir(), fileName);
                File temp = new File(filePath);
                if (temp.exists()) {
                    song = new Song(filePath, songName, author);
                } else {
                    timmMain.LOGGER.warn(String.format("Failed to find file with path '%s'", filePath));
                }
            } else {
                // parse file/id as an identifier and get the sound event associated with it
                String id = songObj.get("file/id").getAsString();
                Identifier identifier = Identifier.tryParse(id);
                if (identifier != null) {
                    SoundEvent se = SoundEvent.of(identifier);
                    song = new Song(se, songName, author);
                } else if (modConfig.debugLogging) {
                    timmMain.LOGGER.warn(String.format("Failed to find sound event with identifier '%s'", id));
                }
            }

            if (song != null) {
                songList.put(songID, song);
            }
        }
    }

    public static Song songFromID(String id) {
        return songList.get(id);
    }

    public static Song songFromName(String name) {
        Set<Map.Entry<String, Song>> songs = songList.entrySet();
        for (Map.Entry<String, Song> entry : songs) {
            if (entry.getValue().getSongName().equals(name)) {
                return entry.getValue();
            }
        }

        // song not found
        return null;
    }
}
