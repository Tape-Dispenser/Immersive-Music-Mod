package net.tape.timm.aws;

import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import java.io.File;

public class UpdateGetter extends Thread {
    public void run() {
        try {
            for (File update : getSongs.updates.filesToUpdate()) {
                timmMain.LOGGER.info(update.toString());
                // download file
                // once file is downloaded, add it to song registry

            }
        } catch (Exception e) {
            if (modConfig.debugLogging) {
                timmMain.LOGGER.warn("Error in thread while attempting to download updates!", e);
                timmMain.LOGGER.warn("This is most likely due to the cancel button being pressed and can safely be ignored.");
            }
        }
    }
    public void kill() {
        this.interrupt();
    }
}
