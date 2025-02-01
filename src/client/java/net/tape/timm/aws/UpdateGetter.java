package net.tape.timm.aws;

import net.tape.timm.audio.FileSong;
import net.tape.timm.audio.Song;
import net.tape.timm.audio.biomePlaylists;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;
import net.tape.timm.audio.SongRegistry;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class UpdateGetter extends Thread {

    // add a set or maybe a map to track download progress of each file
    protected HashSet<File> downloadedSongs = new HashSet<>();

    public void run() {
        if (!SongRegistry.isStarted()) {
            timmMain.LOGGER.warn("Warning! TIMM Song Registry not started!");
            timmMain.LOGGER.warn("Any songs downloaded will not be applied to the mod until next restart!");
        }
        try {
            for (File fileToUpdate : getSongs.updates.filesToUpdate()) {
                // download file
                String awsFile = fileToUpdate.getName();
                awsHelper.downloadFile(awsFile, fileToUpdate, getSongs.bucketName, getSongs.client);
                // once file is downloaded, add it to song registry and mark as downloaded
                this.downloadedSongs.add(fileToUpdate);
                if (!SongRegistry.isStarted()) {
                    continue;
                }
                Song newSong = SongRegistry.searchForSongInJSON(fileToUpdate);

                int returnCode = SongRegistry.addSong(newSong);

                switch (returnCode) {
                    case 0:
                        assert newSong != null;
                        if (modConfig.debugLogging) {
                            timmMain.LOGGER.info("Successfully registered {}", newSong.getPathOrId());
                            // refresh biome playlists (maybe this will fix my problems????)
                            biomePlaylists.registerPlaylists();
                        }
                        break;
                    case SongRegistry.MISSING_FAIL:
                        assert newSong != null;
                        timmMain.LOGGER.warn("Warning! File {} does not exist, skipping!", newSong.getPathOrId());
                        break;
                    case SongRegistry.RESOURCE_FAIL:
                        assert newSong != null;
                        timmMain.LOGGER.warn("Warning! Song {} is not a local file! It cannot be registered on the fly!", newSong.getPathOrId());
                        break;
                    case SongRegistry.EXISTS_FAIL:
                        assert newSong != null;
                        timmMain.LOGGER.warn("Warning! File {} is already registered!", newSong.getPathOrId());
                        break;
                    case SongRegistry.JSON_FAIL:
                        assert newSong != null;
                        timmMain.LOGGER.warn("Warning! File {} does not exist in songList.json!", newSong.getPathOrId());
                        break;
                    case SongRegistry.NULL_FAIL:
                        timmMain.LOGGER.warn("Warning! File {} returned a null song! This is likely due to it not existing in your current songList.json", fileToUpdate);
                        break;
                }



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
