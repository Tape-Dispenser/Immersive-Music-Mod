package net.fabricmc.mcmp;

import net.fabricmc.mcmp.MCMP_main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.Random;
import java.util.Vector;

import static net.fabricmc.mcmp.config_manager.biomes;



public class song_controls {

    public static MinecraftClient mc;
    public static PositionedSoundInstance currentlyPlaying;
    public static Integer minDelay;
    public static Integer maxDelay;
    public static Integer timer;
    public static boolean inTimer;
    public static Random song_rng;

    public static void init() {
        mc = MinecraftClient.getInstance();
        currentlyPlaying = null;
        minDelay = 12000;
        maxDelay = 24000;
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
        Integer index = 0;
        String biome = "menu";
        if (mc.world != null) {
            assert mc.player != null;
            biome = mc.world.getBiome(mc.player.getBlockPos()).getKey().get().getValue().toString();
            timer = song_rng.nextInt(minDelay,maxDelay);
        } else {
            timer = song_rng.nextInt(200,600);
        }
        inTimer = true;
        Vector<String> playlist = biomes.get(biome);											// get playlist
        if (playlist.isEmpty()){																// check if empty
            playlist = biomes.get("fallback");
        }
        index = Math.abs(song_rng.nextInt() % playlist.size());									// pick random index inside playlist
        String song = playlist.get(index).replaceAll("\"", "").strip();		// get the song
        if (song.equals("vanilla")) {
            index = Math.abs(song_rng.nextInt() % playlist.size());
            try {
                song = biomes.get(song).get(index).replaceAll("\"", "").strip(); // have fun understanding this when i'm not on a violent amount of cannabis
            }
            catch (ArrayIndexOutOfBoundsException e) {
                MCMP_main.LOGGER.error("Make sure the fallback playlist (by default the vanilla playlist) isn't empty!");
                throw new RuntimeException(e);
            }
        }

        try {
            return SoundEvent.of(Identifier.tryParse(song));
        }
        catch (NullPointerException e) {
            return null;
        }

    }


}
