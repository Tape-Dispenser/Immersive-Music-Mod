package net.tape.timm.audio;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.Identifier;
import net.tape.timm.configManager;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class SongRegistry {

    private static boolean started = false;

    static final int ADD_SUCCESS = 0;
    static final int MISSING_FAIL = -1; // you tried to add a song that doesn't exist locally
    static final int RESOURCE_FAIL = -2; // you tried to add a resource song on the fly
    static final int EXISTS_FAIL = -3; // you tried to add a song that already exists in the registry
    static final int JSON_FAIL = -4; // you tried to add a song that doesn't exist in songList.json

    public static Map<String, Song> songList = new HashMap<>();

    public static JsonObject getSongList() {
        String json;
        File songListPath = new File(configManager.timmMusicDir, "songList.json");

        try {
            json = Files.readString(songListPath.toPath());
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("Error while parsing JSON file: Failed to find file %s!", songListPath.getPath()));
            return null;
        }

        // load json file into JsonObject
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        JsonElement tmp = obj.get("songs");
        if (tmp == null) {
            timmMain.LOGGER.error("Error: songList.json is missing songs object!");
            return null;
        }
        return tmp.getAsJsonObject();
    }

    public static void init() {
        // open songList.json
        String json;
        File songListPath = new File(configManager.timmMusicDir, "songList.json");
        try {
            json = Files.readString(songListPath.toPath());
        } catch (IOException e) {
            timmMain.LOGGER.warn("Failed to find songList.json!");
            return;
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
                File temp = new File(configManager.timmMusicDir, fileName);
                if (temp.exists()) {
                    song = new FileSong(temp.getPath(), songName, author);
                } else {
                    timmMain.LOGGER.warn(String.format("Failed to find file with path '%s'", temp.getPath()));
                }
            } else {
                // parse file/id as an identifier and get the sound event associated with it
                String id = songObj.get("file/id").getAsString();
                Identifier identifier = Identifier.tryParse(id);
                if (identifier != null) {
                    song = new ResourceSong(id, songName, author);
                } else if (modConfig.debugLogging) {
                    timmMain.LOGGER.warn(String.format("Failed to find sound event with identifier '%s'", id));
                }
            }

            if (song != null) {
                songList.put(songID, song);
            }
        }
        started = true;
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

    public static Set<String> songsInGroup(String groupName) {
        // get set of all registered songs under a specific song group
        Set<String> matches = new HashSet<>();
        for (String songId : SongRegistry.songList.keySet()) {
            ArrayList <String> parts = new ArrayList<>(List.of(songId.split("-")));
            if (Objects.equals(parts.get(0), groupName)) {
                matches.add(songId);
            }
        }
        return matches;
    }

    public static int addSong(Song songToAdd) {
        if (!songToAdd.isFile()) {
            return RESOURCE_FAIL;
        }

        String json;
        File songListPath = new File(configManager.timmMusicDir, "songList.json");
        try {
            json = Files.readString(songListPath.toPath());
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
            File addedSongPath = new File(songToAdd.getPathOrId());
            String addedSongFile = addedSongPath.getName();
            String songID = entry.getKey();
            JsonObject songObj = entry.getValue().getAsJsonObject();
            String fileName = songObj.get("file/id").getAsString();
            if (!Objects.equals(addedSongFile, fileName)) {
                continue;
            }
            if (!addedSongPath.exists()) {
                return MISSING_FAIL;
            }
            if (songList.containsKey(songID)) {
                return EXISTS_FAIL;
            }
            songList.put(songID, songToAdd);
            return ADD_SUCCESS;

        }
        return JSON_FAIL;
    }

    public static boolean isStarted() {return started;}
}
