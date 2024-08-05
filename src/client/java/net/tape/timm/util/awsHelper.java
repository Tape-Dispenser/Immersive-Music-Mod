package net.tape.timm.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class awsHelper {

    /*
        Collection of functions to make interfacing with AWS easier and shorter
     */


    public static void downloadFile(String serverFile, File dest, String bucketName, AmazonS3Client client) {
        if (modConfig.debugLogging) {
            timmMain.LOGGER.info(String.format("Attempting to download aws file %s to local file %s", serverFile, dest.getPath()));
        }

        try {
            // download serverFile and return metadata
            client.getObject(new GetObjectRequest(bucketName, serverFile), dest);
        } catch (AmazonS3Exception e) {
            timmMain.LOGGER.warn(String.format("Failed to download file %s from aws server!", serverFile));
            timmMain.LOGGER.warn(e.getMessage());
        }
    }

    public static void updateVersionedJsonFile(File localFile, String bucketName, AmazonS3Client client) {
        String json;

        try {
            json = Files.readString(localFile.toPath());
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("Failed to find file '%s' while trying to update", localFile.getPath()));
            timmMain.LOGGER.info("Downloading now...");
            downloadFile(localFile.getName(), localFile, bucketName, client);
            return;
        }

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        JsonElement custom = obj.get("custom");
        if (custom != null && custom.getAsBoolean()) {
            if (modConfig.debugLogging) {
                timmMain.LOGGER.info(String.format("config file %s is customized, ignoring updates", localFile.getPath()));
            }
            return;
        }

        int localVersion = obj.get("version").getAsInt();

        String fileDirectory = localFile.getParent();
        String fileName = localFile.getName();
        File serverFile = new File(fileDirectory, "server_" + fileName);
        downloadFile(fileName, serverFile, bucketName, client);
        try {
            json = Files.readString(Path.of(serverFile.getPath()));
        } catch (IOException e) {
            timmMain.LOGGER.error(String.format("Failed to find and download %s on AWS server!", fileName));
            throw new RuntimeException(e);
        }

        obj = JsonParser.parseString(json).getAsJsonObject();
        int serverVersion = obj.get("version").getAsInt();
        if (localVersion >= serverVersion) {
            serverFile.delete();
            return;
        }

        if (!localFile.delete()) {
            timmMain.LOGGER.error(String.format("Failed to delete %s while updating!", localFile.getPath()));
            timmMain.LOGGER.warn("This may be due to some file permission error");
            throw new RuntimeException();
        }

        if (!serverFile.renameTo(localFile)) {
            timmMain.LOGGER.error(String.format("Failed to rename server file to %s while updating!", localFile.getPath()));
            timmMain.LOGGER.warn("This may be due to some file permission error");
            throw new RuntimeException();
        }
    }

    public static void validateLocal(String bucket, AmazonS3Client client) {
        // check local songList.json and ensure all referenced files exist locally
        String json;
        String filePath = String.format("%s/music/TIMM/songList.json", FabricLoader.getInstance().getGameDir());
        try {
            json = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            timmMain.LOGGER.error("Failed to find songList.json!");
            throw new RuntimeException(e);
        }
        // load json serverFile into JsonObject
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        // get songs as a set of key pairs
        JsonObject songList = obj.get("songs").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> localEntries = songList.entrySet();

        for (Map.Entry<String, JsonElement> entry : localEntries) {
            // each key will be a song id string and the value associated will be a song JSON object
            JsonObject song = entry.getValue().getAsJsonObject();
            // check if song is a serverFile
            boolean isFile = song.get("is_file?").getAsBoolean();
            if (!isFile) {
                continue;
            }

            // check if serverFile exists
            String fileName = song.get("file/id").getAsString();
            String path = String.format("%s/music/TIMM/%s", FabricLoader.getInstance().getGameDir(), fileName);
            File f = new File(path);
            if (f.isFile()) {
                continue;
            }

            if (modConfig.debugLogging) {
                timmMain.LOGGER.info(String.format("%s not found, downloading now...", fileName));
            }
            downloadFile(fileName, f, bucket, client);
        }
    }


    public static ArrayList<String> getDiffs(String bucketName, AmazonS3Client client) {
        // get list of all songs that need to update

        // get client-side song list
        String json;
        String filePath = String.format("%s/music/TIMM/songList.json", FabricLoader.getInstance().getGameDir());
        try {
            json = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("Error while accessing file %s", filePath), e);
            timmMain.LOGGER.info("Attempting to download now...");
            File songListFile = new File(String.format("%s/music/TIMM/songList.json", FabricLoader.getInstance().getGameDir()));
            downloadFile("songList.json", songListFile, bucketName, client);
            validateLocal(bucketName, client);
            try {
                json = Files.readString(Path.of(filePath));
            } catch (IOException f) {
                timmMain.LOGGER.error("something really bad happened", f);
                throw new RuntimeException(f);
            }
        }
        // load json serverFile into JsonObject
        JsonObject localObj = JsonParser.parseString(json).getAsJsonObject();


        // get server-side song list
        File serverSongListFile = new File(String.format("%s/music/TIMM/serverSongList.json", FabricLoader.getInstance().getGameDir()));
        downloadFile("songList.json", serverSongListFile, bucketName, client);
        // load json
        filePath = String.format("%s/music/TIMM/serverSongList.json", FabricLoader.getInstance().getGameDir());
        try {
            json = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            timmMain.LOGGER.error("Error while accessing server side song list", e);
            throw new RuntimeException(e);
        }
        // load json serverFile into JsonObject
        JsonObject serverObj = JsonParser.parseString(json).getAsJsonObject();


        ArrayList<String> filesToUpdate = new ArrayList<>();

        int serverVersion = serverObj.get("version").getAsInt();
        int localVersion = localObj.get("version").getAsInt();

        if (serverVersion > localVersion) {
            filesToUpdate.add("songList.json");
        }

        serverObj = serverObj.get("songs").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> serverSongs = serverObj.entrySet();
        localObj = localObj.get("songs").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> temp = localObj.entrySet();
        // convert set of map entries to hashmap
        HashMap<String, JsonElement> localSongs = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : temp) {
            localSongs.put(entry.getKey(), entry.getValue());
        }

        // iterate through all key pairs
        for (Map.Entry<String, JsonElement> serverEntry : serverSongs) {
            String key = serverEntry.getKey();
            JsonObject serverSong = serverEntry.getValue().getAsJsonObject();
            JsonObject localSong = localSongs.get(key).getAsJsonObject();

            if (localSong == null) {
                if (serverSong.get("is_file?").getAsBoolean()) {
                    filesToUpdate.add(serverSong.get("file/id").getAsString());
                }
            } else {
                serverVersion = serverSong.get("revision").getAsInt();
                localVersion = localSong.get("revision").getAsInt();

                if (serverVersion > localVersion) {
                    if (serverSong.get("is_file?").getAsBoolean()) {
                        filesToUpdate.add(serverSong.get("file/id").getAsString());
                    }
                }
            }
        }

        return filesToUpdate;
    }


}
