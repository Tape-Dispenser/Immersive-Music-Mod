package net.tape.timm.audio;

import net.tape.timm.songControls;
import net.tape.timm.timmMain;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.ArrayList;
import java.util.List;

public class SongSelector {

    public static Song pickSong() {

        // playlists aren't implemented properly yet, just return a random song
        ArrayList<InjectorGroupInfo.Map.Entry<String, Song>> x = new ArrayList<>(SongRegistry.songList.entrySet());
        int len = x.size();
        int songIndex = songControls.song_rng.nextInt(len);
        return x.get(songIndex).getValue();


        /*
        // determine playlist and set delay
        String playlistName = "menu";
        if (mc.world != null) {
            assert mc.player != null;
            Optional<RegistryKey<Biome>> temp = mc.world.getBiome(mc.player.getBlockPos()).getKey();
            if (temp.isPresent()) {
                playlistName = temp.get().getValue().toString();

                if (modConfig.debugLogging) {
                    timmMain.LOGGER.info(playlistName);
                }
            }
        }

        // get playlist
        List<String> playlist;
        try {
            playlist = Arrays.asList(bp.get(playlistName));
        } catch (NullPointerException e) {
            playlist = Arrays.asList(bp.get("fallback"));
        }
        if (playlist.isEmpty()) {
            playlist = Arrays.asList(bp.get("fallback"));
        }

        // pick song in playlist
        int index = Math.abs(song_rng.nextInt() % playlist.size());
        String songName = playlist.get(index);
        try {
            return new Song(SoundEvent.of(Identifier.tryParse(songName)), "", "Unknown");
        } catch (NullPointerException e) {
            timmMain.LOGGER.warn(String.format("song \"%s\" does not exist!", songName));
            return null;
        }

         */

    }

    public static ArrayList<Song> getPlaylists() {
        ArrayList<Song> selectionPool = new ArrayList<>();

        if (timmMain.mc.world == null) {
            // game has not started, therefore must be in main menu

        }


        return selectionPool;
    }


    public static ArrayList<Song> getPlaylist(String name) {
        ArrayList<Song> playlist = new ArrayList<>();



        return playlist;
    }
}
