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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

    public static ArrayList<S3ObjectSummary> getServerSongs() {

        // get list of songs on the AWS S3 server

        // variable initialization
        String nextContinuationToken = null;
        int totalObjects = 0;
        ArrayList<S3ObjectSummary> objects = new ArrayList<>() {};

        do {
            // construct request to AWS server
            ListObjectsV2Request request = new ListObjectsV2Request();
            request.setBucketName(bucketName);
            request.setContinuationToken(nextContinuationToken);

            // send request
            ListObjectsV2Result result = client.listObjectsV2(request);

            // parse result and prepare for next page (if necessary)
            totalObjects += result.getKeyCount();
            objects.addAll(result.getObjectSummaries());
            nextContinuationToken = result.getContinuationToken();
        } while (nextContinuationToken != null);

        return objects;
    }

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
        ArrayList<S3ObjectSummary> objectSummaries = getServerSongs();
        ArrayList<String> songsToGet = new ArrayList<>();
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
            downloadSong(song);
        }
    }

    public static void downloadSong(String song) {
        File localFile = new File(String.format("%s/music/TIMM/%s", String.valueOf(FabricLoader.getInstance().getGameDir()), song));
        ObjectMetadata object = client.getObject(new GetObjectRequest(bucketName, song), localFile);
    }


}
