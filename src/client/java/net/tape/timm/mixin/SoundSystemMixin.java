package net.tape.timm.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.minecraft.SharedConstants;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.tape.timm.access.SoundSystemAccess;
import net.tape.timm.songControls;
import net.tape.timm.timmMain;
import net.tape.timm.util.Song;
import org.slf4j.Marker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin implements SoundSystemAccess {
	@Shadow private boolean started;

	@Shadow @Final private SoundManager loader;

	@Shadow protected abstract float getAdjustedVolume(float volume, SoundCategory category);

	@Shadow protected abstract float getAdjustedPitch(SoundInstance sound);

	@Shadow @Final private static Marker MARKER;

	@Shadow @Final private List<SoundInstanceListener> listeners;

	@Shadow @Final private SoundListener listener;

	@Shadow @Final private Channel channel;

	@Shadow @Final private Map<SoundInstance, Integer> soundEndTicks;

	@Shadow private int ticks;

	@Shadow @Final private Map<SoundInstance, Channel.SourceManager> sources;

	@Shadow @Final private Multimap<SoundCategory, SoundInstance> sounds;

	@Shadow @Final private SoundLoader soundLoader;

	@Shadow @Final private List<TickableSoundInstance> tickingSounds;

	@Shadow public abstract void play(SoundInstance sound2);

	@Inject(method = "start()V",
			at = @At(value = "INVOKE", target = "org/slf4j/Logger.info (Lorg/slf4j/Marker;Ljava/lang/String;)V", ordinal = 0),
			slice = @Slice(
					 from = @At(value = "CONSTANT", args = "stringValue=Sound engine started")
			)
	)
	private void soundStarted(CallbackInfo info) {
		songControls.soundEngineStarted = this.started;
	}

	private Map<Song, Integer> songEndTicks = Maps.newHashMap();
	private Map<Song, Channel.SourceManager> songSources = Maps.newHashMap();
	private Multimap<SoundCategory, Song> songs = HashMultimap.create();
	
	@Override
	public String access() {
		return "i jailbroke the system.\nthe sound system has started.\nthe previous statement is ".concat(String.valueOf(this.started)).concat(".");
	}


	@Override
	public void play(Song song) {
		if (!this.started) {
			return;
		}
		if (!song.isFile()) {
			play(PositionedSoundInstance.music(song.getSoundEvent()));
			return;
		}

		PositionedSoundInstance soundInstance;
		Identifier identifier = song.getId();

		float volSlider = this.getAdjustedVolume(1.0f, SoundCategory.MUSIC);
		boolean bl = true;
		if (volSlider == 0.0f) {
			timmMain.LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", (Object) identifier);
			return;
		}
		Vec3d vec3d = new Vec3d(0.0, 0.0, 0.0);

		if (this.listener.getVolume() <= 0.0f) {
			timmMain.LOGGER.debug(MARKER, "Skipped playing soundEvent: {}, master volume was zero", (Object)identifier);
			return;
		}

		CompletableFuture<Channel.SourceManager> completableFuture = this.channel.createSource(SoundEngine.RunMode.STATIC);
		Channel.SourceManager sourceManager = completableFuture.join();
		if (sourceManager == null) {
			if (SharedConstants.isDevelopment) {
				timmMain.LOGGER.warn("Failed to create new sound handle");
			}
			return;
		}

		timmMain.LOGGER.debug(MARKER, "Playing sound {} for event {}", (Object) song.getId(), (Object)identifier);
		this.songEndTicks.put(song, this.ticks + 20);

		this.songSources.put(song, sourceManager);

		this.songs.put(SoundCategory.MUSIC, song);

		sourceManager.run(source -> {
			source.setPitch(1.0f);
			source.setVolume(volSlider);
            source.disableAttenuation();
            source.setLooping(false);
			source.setPosition(vec3d);
			source.setRelative(bl);
		});
		CompletableFuture<StaticSound> test = CompletableFuture.supplyAsync(() -> {
			try (InputStream inputStream = new FileInputStream(song.getFilePath())){
				StaticSound staticSound;
				try (OggAudioStream oggAudioStream = new OggAudioStream(inputStream);){
					ByteBuffer byteBuffer = oggAudioStream.getBuffer();
					staticSound = new StaticSound(byteBuffer, oggAudioStream.getFormat());
				}
				return staticSound;
			} catch (IOException e) {
				throw new CompletionException(e);
			}
		}, Util.getMainWorkerExecutor());

		test.thenAccept(sound -> sourceManager.run(source -> {
			source.setBuffer((StaticSound)sound);
			source.play();
		}));

		/*
		if (soundInstance instanceof TickableSoundInstance) {
			this.tickingSounds.add((TickableSoundInstance) soundInstance);
		}

		 */
	}
}
