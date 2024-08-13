package net.tape.timm.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import net.fabricmc.loader.api.FabricLoader;

import net.tape.timm.configManager;
import net.tape.timm.timmMain;


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class getSongs {

    static final String bucketName = "timmsballs";
    static final Region region = Region.getRegion(Regions.US_EAST_2);
    static AmazonS3Client client;

    public static void initS3Client() {
        client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(String.valueOf(region))
                .build();
    }

    public static void destroyS3Client() {
        client.shutdown();
    }


    public static GetUpdatesReturn checkForUpdates() {
        HashSet<File> filesToUpdate = new HashSet<File>();

        File songsFile = new File(configManager.timmMusicDir, "songList.json");
        UpdateJsonFileReturn returnObj = awsHelper.updateVersionedJsonFile(songsFile, bucketName, client);
        int returnCode = returnObj.code();
        File serverFile = returnObj.server();
        File localFile = returnObj.local();

        GetUpdatesReturn updatesReturnObj;

        switch (returnCode) {
            case awsHelper.ERROR:
                return null;
            case awsHelper.NO_UPDATE:
                if (serverFile != null) {
                    serverFile.delete();
                }
                break;
            case awsHelper.NO_LOCAL:
                serverFile.renameTo(localFile);
                updatesReturnObj = awsHelper.validateLocal(localFile, bucketName, client);
                if (updatesReturnObj.code() != awsHelper.SUCCESS) {
                    return new GetUpdatesReturn(updatesReturnObj.code(), null);
                }
                filesToUpdate.addAll(updatesReturnObj.filesToUpdate());
                break;
            case awsHelper.UP_TO_DATE:
                updatesReturnObj = awsHelper.validateLocal(localFile, bucketName, client);
                if (updatesReturnObj.code() != awsHelper.SUCCESS) {
                    return new GetUpdatesReturn(updatesReturnObj.code(), null);
                }
                filesToUpdate.addAll(updatesReturnObj.filesToUpdate());
                break;
            case awsHelper.UPDATE:
                // loop through both server and local version, add song to list if server version is greater than local version
                // also add files in server list that don't exist locally
                updatesReturnObj = awsHelper.validateLocal(localFile, bucketName, client);
                if (updatesReturnObj.code() != awsHelper.SUCCESS) {
                    return new GetUpdatesReturn(updatesReturnObj.code(), null);
                }
                filesToUpdate.addAll(updatesReturnObj.filesToUpdate());

                updatesReturnObj = awsHelper.getDiffs(localFile, serverFile, bucketName, client);
                if (updatesReturnObj.code() != awsHelper.SUCCESS) {
                    return new GetUpdatesReturn(updatesReturnObj.code(), null);
                }
                filesToUpdate.addAll(updatesReturnObj.filesToUpdate());
                break;
            default:
                return new GetUpdatesReturn(-255, null);
        }

        return new GetUpdatesReturn(awsHelper.SUCCESS, filesToUpdate);
    }

    public static void update() {

        /*
        // TODO: Rewrite getDiffs() and validateLocal()

        List<String> filesToUpdate = awsHelper.getDiffs(bucketName, client);
        awsHelper.validateLocal(bucketName, client);


        for (String file : filesToUpdate) {
            timmMain.LOGGER.info(String.format("Updating file %s...", file));
            // TODO: make this use file objects to declare the subdirectories from game dir
            File localFile = new File(String.format("%s/music/TIMM/%s", FabricLoader.getInstance().getGameDir(), file));
            awsHelper.downloadFile(file, localFile , bucketName, client);
        }
        */
    }
}
