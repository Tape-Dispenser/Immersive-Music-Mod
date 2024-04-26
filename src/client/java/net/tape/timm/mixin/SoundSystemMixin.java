package net.tape.timm.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.SharedConstants;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

	@Inject(method = "start()V",
			at = @At(value = "INVOKE", target = "org/slf4j/Logger.info (Lorg/slf4j/Marker;Ljava/lang/String;)V", ordinal = 0),
			slice = @Slice(
					 from = @At(value = "CONSTANT", args = "stringValue=Sound engine started")
			)
	) // TODO: this will run every tick, a cleaner system is needed!!!!!
	private void soundStarted(CallbackInfo info) {
		songControls.soundEngineStarted = true;
	}
	
	@Override
	public String access() {
		return "i jailbroke the system.\nthe sound system has started.\nthe previous statement is ".concat(String.valueOf(this.started)).concat(".");
	}

	@Override
	public void play(Song song) {
		if (!this.started) {
			return;
		}

		PositionedSoundInstance sound2;

		Identifier identifier = sound2.getId();

		Sound sound22 = sound2.getSound();

		float f = 1.0f;
		float g = 0.0f;
		float h = this.getAdjustedVolume(f, SoundCategory.MUSIC);
		float i = 1.0f;
		SoundInstance.AttenuationType attenuationType = SoundInstance.AttenuationType.NONE;
		boolean bl = true;
		if (h == 0.0f && !sound2.shouldAlwaysPlay()) {
			timmMain.LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", (Object)sound22.getIdentifier());
			return;
		}
		Vec3d vec3d = new Vec3d(0.0, 0.0, 0.0);

		if (this.listener.getVolume() <= 0.0f) {
			timmMain.LOGGER.debug(MARKER, "Skipped playing soundEvent: {}, master volume was zero", (Object)identifier);
			return;
		}

		boolean bl2 = false;  // should audio repeat instantly? (no)

		boolean bl3 = false; // is audio streamed? (idk what this means i just know it needs to be false)

		CompletableFuture<Channel.SourceManager> completableFuture = this.channel.createSource(SoundEngine.RunMode.STATIC);
		Channel.SourceManager sourceManager = completableFuture.join();
		if (sourceManager == null) {
			if (SharedConstants.isDevelopment) {
				timmMain.LOGGER.warn("Failed to create new sound handle");
			}
			return;
		}

		// TODO: more sound2 & sound22 usages i need to figure out here
		timmMain.LOGGER.debug(MARKER, "Playing sound {} for event {}", (Object)sound22.getIdentifier(), (Object)identifier);
		this.soundEndTicks.put(sound2, this.ticks + 20);

		this.sources.put(sound2, sourceManager); // TODO: need to either remove this or spoof a SoundInstance...

		this.sounds.put(SoundCategory.MUSIC, sound2); // // TODO: need to either remove this or spoof a SoundInstance...

		sourceManager.run(source -> {
			source.setPitch(i);
			source.setVolume(h);
            source.disableAttenuation();
            source.setLooping(false);
			source.setPosition(vec3d);
			source.setRelative(bl);
		});
		if (!bl3) {
			this.soundLoader.loadStatic(sound22.getLocation()).thenAccept(sound -> sourceManager.run(source -> {
				source.setBuffer((StaticSound)sound);
				source.play();
			}));
		}
		if (sound2 instanceof TickableSoundInstance) {
			this.tickingSounds.add((TickableSoundInstance)sound2);
		}
	}
}
