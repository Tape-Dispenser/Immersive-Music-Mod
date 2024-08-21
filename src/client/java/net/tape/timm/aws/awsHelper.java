package net.tape.timm.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.tape.timm.configManager;
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

    public static final int UP_TO_DATE = 2;
    public static final int NO_UPDATE = 3;
    public static final int NO_LOCAL = 1;
    public static final int ERROR = -1;
    public static final int UPDATE = 0;

    public static final int SUCCESS = 0;
    public static final int MISSING_CLIENT_ERROR = -1;
    public static final int MISSING_SERVER_ERROR = -2;
    public static final int UNKNOWN_ERROR = -255; // what the fuck how did you do this pls god no

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

    public static UpdateJsonFileReturn updateVersionedJsonFile(File localFile, String bucketName, AmazonS3Client client) {
        String json;

        try {
            json = Files.readString(localFile.toPath());
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("Failed to find file '%s' while trying to update", localFile.getPath()));
            timmMain.LOGGER.info("Downloading now...");
            downloadFile(localFile.getName(), localFile, bucketName, client);
            if (localFile.isFile()) {
                return new UpdateJsonFileReturn(NO_LOCAL, null, localFile);
            }
            return new UpdateJsonFileReturn(ERROR, null, null);
        }

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        JsonElement custom = obj.get("custom");
        if (custom != null && custom.getAsBoolean()) {
            if (modConfig.debugLogging) {
                timmMain.LOGGER.info(String.format("config file %s is customized, ignoring updates", localFile.getPath()));
            }
            return new UpdateJsonFileReturn(NO_UPDATE, localFile, null);
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
            return new UpdateJsonFileReturn(ERROR, localFile, null);
        }

        obj = JsonParser.parseString(json).getAsJsonObject();
        int serverVersion = obj.get("version").getAsInt();
        if (localVersion >= serverVersion) {
            return new UpdateJsonFileReturn(UP_TO_DATE, localFile, serverFile);
        }

        return new UpdateJsonFileReturn(UPDATE, localFile, serverFile);
    }

    public static GetUpdatesReturn validateLocal(File songListFile, String bucket, AmazonS3Client client) {
        HashSet<File> filesToUpdate = new HashSet<>();
        // check local songList.json and ensure all referenced files exist locally

        // load json file into JsonObject
        String json;
        try {
            json = Files.readString(songListFile.toPath());
        } catch (IOException e) {
            timmMain.LOGGER.error(String.format("Failed to find Song List file \"%s\"", songListFile.getPath()));
            return new GetUpdatesReturn(ERROR, filesToUpdate);
        }
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        // iterate through all songs
        JsonObject songList = obj.get("songs").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : songList.entrySet()) {
            // each key will be a song id string and the value associated will be a song JSON object
            JsonObject song = entry.getValue().getAsJsonObject();
            // check if song is a file
            boolean isFile = song.get("is_file?").getAsBoolean();
            if (!isFile) {
                continue;
            }

            // check if file exists
            String fileName = song.get("file/id").getAsString();
            File fileToCheck = new File(configManager.timmMusicDir, fileName);
            if (fileToCheck.isFile()) {
                continue;
            }
            filesToUpdate.add(fileToCheck);
        }

        return new GetUpdatesReturn(SUCCESS, filesToUpdate);
    }


    public static GetUpdatesReturn getDiffs(File localSongsFile, File serverSongsFile, String bucketName, AmazonS3Client client) {
        HashSet<File> filesToUpdate = new HashSet<>();
        // get list of all songs that need to update

        // read local song list
        String json;
        try {
            json = Files.readString(localSongsFile.toPath());
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("Failed to find local song list file \"%s\"", localSongsFile.getPath()));
            return new GetUpdatesReturn(MISSING_CLIENT_ERROR, filesToUpdate);
        }
        // load local song list into JsonObject
        JsonObject localObj = JsonParser.parseString(json).getAsJsonObject();

        // get server-side song list
        try {
            json = Files.readString(serverSongsFile.toPath());
        } catch (IOException e) {
            timmMain.LOGGER.error("Error while accessing server side song list", e);
            return new GetUpdatesReturn(MISSING_SERVER_ERROR, filesToUpdate);
        }
        // load json serverFile into JsonObject
        JsonObject serverObj = JsonParser.parseString(json).getAsJsonObject();


        int serverVersion;
        int localVersion;

        JsonObject serverSongsObj = serverObj.get("songs").getAsJsonObject();
        JsonObject localSongsObj = localObj.get("songs").getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> serverSongs = serverSongsObj.entrySet();
        Set<Map.Entry<String, JsonElement>> temp = localSongsObj.entrySet();
        // convert set of map entries to hashmap
        HashMap<String, JsonElement> localSongs = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : temp) {
            localSongs.put(entry.getKey(), entry.getValue());
        }

        // iterate through all server key pairs
        for (Map.Entry<String, JsonElement> serverEntry : serverSongs) {
            String key = serverEntry.getKey();
            JsonObject serverSong = serverEntry.getValue().getAsJsonObject();
            JsonObject localSong = localSongs.get(key).getAsJsonObject();

            if (localSong == null) {
                if (!serverSong.get("is_file?").getAsBoolean()) {
                    continue;
                }
                filesToUpdate.add(new File(serverSong.get("file/id").getAsString()));
            } else {
                serverVersion = serverSong.get("revision").getAsInt();
                localVersion = localSong.get("revision").getAsInt();

                if (serverVersion <= localVersion && !serverSong.get("is_file?").getAsBoolean()) {
                    continue;
                }
                filesToUpdate.add(new File(serverSong.get("file/id").getAsString()));

            }
        }

        serverVersion = serverObj.get("version").getAsInt();
        localVersion = localObj.get("version").getAsInt();

        if (serverVersion > localVersion) {
            filesToUpdate.add(localSongsFile);
        }

        return new GetUpdatesReturn(SUCCESS, filesToUpdate);
    }


}
