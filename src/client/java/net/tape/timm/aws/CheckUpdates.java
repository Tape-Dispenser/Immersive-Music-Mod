package net.tape.timm.aws;

import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

public class CheckUpdates extends Thread{
    public void run() {
        try {
            getSongs.initS3Client();
            getSongs.updates = getSongs.checkForUpdates();
        } catch (Exception e) {
            if (modConfig.debugLogging) {
                timmMain.LOGGER.warn("Error in thread while attempting to check for updates!", e);
                timmMain.LOGGER.warn("This is most likely due to the cancel button being pressed and can safely be ignored.");
                getSongs.updates = new GetUpdatesReturn(awsHelper.UNKNOWN_ERROR, null);
            }
        }
    }
}
