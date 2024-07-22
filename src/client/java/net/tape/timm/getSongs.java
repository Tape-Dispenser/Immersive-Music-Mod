package net.tape.timm;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import net.fabricmc.loader.api.FabricLoader;
import net.tape.timm.util.awsHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class getSongs {

    static final String bucketName = "timmsballs";
    static final Region region = Region.getRegion(Regions.US_EAST_2);
    static final AmazonS3Client client = (AmazonS3Client) AmazonS3ClientBuilder
            .standard()
            .withRegion(String.valueOf(region))
            .build();


    public static void update() {
        File timmCfgDir = new File(FabricLoader.getInstance().getConfigDir().toFile(), "timm");
        File musicDir = new File(FabricLoader.getInstance().getGameDir().toFile(), "music");
        File timmMusicDir = new File(musicDir, "TIMM");

        // we assume timmCfgDir exists as that would have been checked by configManager.java
        // however musicDir and timmMusicDir may not yet exist

        if (!musicDir.isDirectory()) {
            try {
                Files.createDirectories(musicDir.toPath());
                if (modConfig.debugLogging) {timmMain.LOGGER.info(String.format("successfully created config directory at %s", musicDir.getPath()));}
            } catch (IOException e) {
                timmMain.LOGGER.error("Failed to create .minecraft/music folder!");
                timmMain.LOGGER.warn("This may be because .minecraft does not exist, or because of some permissions issue.");
                throw new RuntimeException(e);
            }
        }

        if (!timmMusicDir.isDirectory()) {
            try {
                Files.createDirectories(musicDir.toPath());
                if (modConfig.debugLogging) {timmMain.LOGGER.info(String.format("successfully created config directory at %s", musicDir.getPath()));}
            } catch (IOException e) {
                timmMain.LOGGER.error("Failed to create .minecraft/music/TIMM folder!");
                timmMain.LOGGER.warn("This may be because .minecraft/music does not exist, or because of some permissions issue.");
                throw new RuntimeException(e);
            }
        }

        File playlistsFile = new File(timmCfgDir, "biomePlaylists.json");
        awsHelper.updateVersionedJsonFile(playlistsFile, bucketName, client);

        File songsFile = new File(timmMusicDir, "songList.json");
        awsHelper.updateVersionedJsonFile(songsFile, bucketName, client);

        // TODO: Rewrite getDiffs() and validateLocal()

        List<String> filesToUpdate = awsHelper.getDiffs(bucketName, client);
        awsHelper.validateLocal(bucketName, client);


        for (String file : filesToUpdate) {
            timmMain.LOGGER.info(String.format("Updating file %s...", file));
            // TODO: make this use file objects to declare the subdirectories from game dir
            File localFile = new File(String.format("%s/music/TIMM/%s", FabricLoader.getInstance().getGameDir(), file));
            awsHelper.downloadFile(file, localFile , bucketName, client);
        }
    }
}
