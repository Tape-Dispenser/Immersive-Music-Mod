package net.tape.timm;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class AWStest {

    public static void test() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();

        File localFile = new File(String.valueOf(FabricLoader.getInstance().getGameDir()).concat("/music/my_sound.ogg"));
        ObjectMetadata object = s3Client.getObject(new GetObjectRequest("timmsballs", "my_sound.ogg"), localFile);

        localFile = new File(String.valueOf(FabricLoader.getInstance().getGameDir()).concat("/music/menu-01.ogg"));
        object = s3Client.getObject(new GetObjectRequest("timmsballs", "menu-01.ogg"), localFile);
    }

}
