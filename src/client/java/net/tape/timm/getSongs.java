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



    public static ArrayList<String> getLocalSongs() {
        // get a list of files in .minecraft/music/TIMM

        // List of file objects in .minecraft/music/TIMM
        File[] temp = new File(String.valueOf(FabricLoader.getInstance().getGameDir()).concat("/music/TIMM")).listFiles();
        ArrayList<String> out = new ArrayList<>();

        if (temp != null) {
            // filter temp for files only and return a set of all file names
            Set<String> x = Stream.of(temp)
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toSet());
            // convert set of file names to ArrayList of file names
            out.addAll(x);
        }
        return out;
    }

    public static void getNewSongs() {
        // get list of files on server
        ArrayList<S3ObjectSummary> objectSummaries = awsHelper.getFileList(bucketName, client);
        ArrayList<String> songsToGet = new ArrayList<>();
        // get list of local songs
        ArrayList<String> songsOnDisk = getLocalSongs();

        for (S3ObjectSummary objectSummary : objectSummaries) {
            String fileName = objectSummary.getKey();
            int x = songsOnDisk.indexOf(fileName);
            if (x < 0) {
                songsToGet.add(fileName);
            }
        }

        for (String song : songsToGet) {
            timmMain.LOGGER.info(String.format("Missing song %s, downloading now...", song));
            awsHelper.downloadFile(song, bucketName, client);
        }
    }

    /*
    public static ArrayList<String> getDiffs() {
        // get list of all songs that need to update

        // load local songList.json into a list of Song objects
        Map<String, >
        // load server songList.json into a list of Song objects
        // iterate through all server songs
            // query local version
                // if local version doesn't exist, add song name to diff list
                // if local version does exist, check for any changes
                    // if changes exist, add song name to diff list

                // return list of differences
    }
    */

}
