package net.tape.timm.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public static ObjectMetadata downloadFile(String file, String bucketName, AmazonS3Client client) {
        // create new file object at .minecraft/music/TIMM/<song>
        File localFile = new File(String.format("%s/music/TIMM/%s", String.valueOf(FabricLoader.getInstance().getGameDir()), file));
        // download file and return metadata
        return client.getObject(new GetObjectRequest(bucketName, file), localFile);
    }

    public static ArrayList<String> getDiffs() {
        // get list of all songs that need to update


        // initialize gson
        Gson gson = new Gson();
        // load local songList.json into a list of Song objects

        // load server songList.json into a list of Song objects
        // iterate through all server songs
        // query local version
        // if local version doesn't exist, add song name to diff list
        // if local version does exist, check for any changes
        // if changes exist, add song name to diff list

        // return list of differences
    }

}
