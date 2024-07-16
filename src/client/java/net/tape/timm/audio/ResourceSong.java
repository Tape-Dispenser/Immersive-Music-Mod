package net.tape.timm.audio;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.tape.timm.timmMain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceSong extends Song{
    /**
     * @param id can either be an id referencing a sound registered in sounds.json (example: {@code minecraft:music.game})
     * or a location identifier returned by {@code ResourceFinder.toResourcePath()} (example: {@code minecraft:sounds/music/game/danny.ogg})
     * @param songName can be any string and is only used in getting resources and printing info
     * @param author can be any string and is only used in printing info
     */
    public ResourceSong(String id, String songName, String author) {
        super(id, songName, author);
    }

    @Override
    public ByteArrayOutputStream loadByteStream() {
        Identifier id = Identifier.tryParse(this.pathOrId);
        if (id == null) {
            timmMain.LOGGER.warn(String.format("Error loading song from resource: bad identifier '%s' passed", this.pathOrId));
            return null;
        }

        RegistryEntry<SoundEvent> registryEntry = RegistryEntry.of(SoundEvent.of(id));
        timmMain.LOGGER.info(registryEntry.value().getId().toString());

        Random rng = Random.create();

        PositionedSoundInstance soundInstance = new PositionedSoundInstance(id, SoundCategory.MUSIC, 1.0f, 1.0f, rng, false, 0, SoundInstance.AttenuationType.NONE, 0.0, 0.0, 0.0, true);
        WeightedSoundSet soundSet = timmMain.mc.getSoundManager().get(id);

        Identifier locationId;

        if (soundSet == null) {
            // if game fails to find a sound set it may still be a location identifier
            locationId = id;
        } else {
            Sound sound = soundSet.getSound(rng);
            locationId = sound.getLocation();
        }

        timmMain.LOGGER.info(String.format("Attempting to load song from %s", locationId.toString()));

        try(InputStream inputStream = timmMain.mc.getResourceManager().open(locationId)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            return outputStream;
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("Error while loading '%s' from file", this.pathOrId), e);
            return null;
        }
    }
}
