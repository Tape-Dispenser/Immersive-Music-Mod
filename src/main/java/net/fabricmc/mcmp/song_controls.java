package net.fabricmc.mcmp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.*;


public class song_controls {

    public static MinecraftClient mc;
    public static Map<String, String[]> bp = biome_playlists.biomePlaylists;
    public static PositionedSoundInstance currentlyPlaying;
    public static Integer minDelay;
    public static Integer maxDelay;
    public static Integer timer;
    public static boolean inTimer;
    public static Random song_rng;


    public static void init() {
        mc = MinecraftClient.getInstance();
        currentlyPlaying = null;
        minDelay = 1;
        maxDelay = 2;
        timer = 1;
        inTimer = true;
        song_rng = new Random();
    }


    public static PositionedSoundInstance nowPlaying() {
        return currentlyPlaying;
    }


    public static void playSong(SoundEvent songToPlay) {
        // literally just the vanilla playSong function but it won't be called by vanilla minecraft...
        if (songToPlay != null) {
            if (currentlyPlaying != null)
                mc.getSoundManager().stop(currentlyPlaying);

            currentlyPlaying = PositionedSoundInstance.music(songToPlay);

            mc.getSoundManager().play(currentlyPlaying);
        }
    }






    public static SoundEvent pickSong() {
        String playlistName= "menu";

        if (mc.world != null) {
            assert mc.player != null;
            Optional<RegistryKey<Biome>> temp = mc.world.getBiome(mc.player.getBlockPos()).getKey();
            if (temp.isPresent()) {
                playlistName = temp.get().getValue().toString();
                MCMP_main.LOGGER.info(playlistName);
                timer = song_rng.nextInt(minDelay,maxDelay);
            }
        } else {
            timer = song_rng.nextInt(200,600);
        }
        inTimer = true;

        List<String> playlist = Arrays.asList(bp.get(playlistName));										// get playlist

        if (playlist.isEmpty()){
            playlist = Arrays.asList(bp.get("fallback"));
        }

        int index = Math.abs(song_rng.nextInt() % playlist.size());
        String songName = playlist.get(index);

        try {
            return SoundEvent.of(Identifier.tryParse(songName));
        }
        catch (NullPointerException e) {
            MCMP_main.LOGGER.error(String.format("failed to find %s", songName));
            throw new RuntimeException(e);
        }

    }


}
