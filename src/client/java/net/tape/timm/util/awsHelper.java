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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class awsHelper {

    /*
        Collection of functions to make interfacing with AWS easier and shorter


     */

    public static ArrayList<S3ObjectSummary> getFileList(String bucketName, AmazonS3Client client) {

        // get list of songs on the AWS S3 server

        // variable initialization
        String nextContinuationToken = null;;
        ArrayList<S3ObjectSummary> objects = new ArrayList<>() {};

        do {
            // construct request to AWS server
            ListObjectsV2Request request = new ListObjectsV2Request();
            request.setBucketName(bucketName);
            request.setContinuationToken(nextContinuationToken);

            // send request
            ListObjectsV2Result result = client.listObjectsV2(request);

            // parse result and prepare for next page (if necessary)
            objects.addAll(result.getObjectSummaries());
            nextContinuationToken = result.getContinuationToken();
        } while (nextContinuationToken != null);

        return objects;
    }

    public static ObjectMetadata downloadFile(String file, String dest, String bucketName, AmazonS3Client client) {
        // create new file object at .minecraft/music/TIMM/<song>
        File localFile = new File(dest);
        // download file and return metadata
        return client.getObject(new GetObjectRequest(bucketName, file), localFile);
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
        // load json file into JsonObject
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        // get songs as a set of key pairs
        JsonObject songList = obj.get("songs").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> localEntries = songList.entrySet();

        for (Map.Entry<String, JsonElement> entry : localEntries) {
            // each key will be a song id string and the value associated will be a song JSON object
            JsonObject song = entry.getValue().getAsJsonObject();
            // check if song is a file
            boolean isFile = song.get("is_file?").getAsBoolean();
            if (isFile) {
                // check if file exists
                String fileName = song.get("file_name").getAsString();
                String path = FabricLoader.getInstance().getGameDir() + fileName;
                File f = new File(path);
                if (!f.isFile()) {

                    if (modConfig.debugLogging) {
                        timmMain.LOGGER.info(String.format("%s not found, downloading now...", fileName));
                    }

                    downloadFile(fileName, path, bucket, client);
                }
            }
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
            downloadFile("songList.json", String.format("%s/music/TIMM/songList.json", FabricLoader.getInstance().getGameDir()), bucketName, client);
            validateLocal(bucketName, client);
            try {
                json = Files.readString(Path.of(filePath));
            } catch (IOException f) {
                timmMain.LOGGER.error("something really bad happened", f);
                throw new RuntimeException(f);
            }
        }
        // load json file into JsonObject
        JsonObject localObj = JsonParser.parseString(json).getAsJsonObject();


        // get server-side song list
        downloadFile("songList.json", String.format("%s/music/TIMM/serverSongList.json", FabricLoader.getInstance().getGameDir()), bucketName, client);
        // load json
        filePath = String.format("%s/music/TIMM/serverSongList.json", FabricLoader.getInstance().getGameDir());
        try {
            json = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            timmMain.LOGGER.error("Error while accessing server side song list", e);
            throw new RuntimeException(e);
        }
        // load json file into JsonObject
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
                    filesToUpdate.add(serverSong.get("file_name").getAsString());
                }
            } else {
                serverVersion = serverSong.get("revision").getAsInt();
                localVersion = localSong.get("revision").getAsInt();

                if (serverVersion > localVersion) {
                    if (serverSong.get("is_file?").getAsBoolean()) {
                        filesToUpdate.add(serverSong.get("file_name").getAsString());
                    }
                }
            }
        }

        return filesToUpdate;
    }


}
