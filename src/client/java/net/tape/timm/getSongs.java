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


    public static void update() {
        List<String> filesToUpdate = awsHelper.getDiffs(bucketName, client);
        awsHelper.validateLocal(bucketName, client);


        for (String file : filesToUpdate) {
            timmMain.LOGGER.info(String.format("Updating file %s...", file));
            awsHelper.downloadFile(file, String.format("%s/music/TIMM/%s", FabricLoader.getInstance().getGameDir(), file), bucketName, client);
        }

        File serverSL = new File(String.format("%s/music/TIMM/serverSongList.json", FabricLoader.getInstance().getGameDir()));
        serverSL.delete();
    }
}
