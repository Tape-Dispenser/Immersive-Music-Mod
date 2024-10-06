package net.tape.timm.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.tape.timm.audio.FileSong;
import net.tape.timm.audio.Song;
import net.tape.timm.audio.SongRegistry;
import net.tape.timm.aws.UpdateEntry;
import net.tape.timm.aws.awsHelper;
import net.tape.timm.aws.getSongs;
import net.tape.timm.configManager;
import net.tape.timm.gui.widget.SimpleButton;
import net.tape.timm.gui.widget.UpdatesListWidget;
import net.tape.timm.gui.widget.UpdatesListEntry;
import net.tape.timm.timmMain;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static net.tape.timm.timmMain.mc;

public class UpdateConfirmScreen extends Screen {
    public UpdateConfirmScreen(Screen parent) {
        super(Text.translatable("timm.update.text"));
        this.parent = parent;
    }

    int txtcol = 0xffffff;
    private final Screen parent;

    private UpdatesListWidget updateList;
    private SimpleButton acceptButton;
    private SimpleButton declineButton;

    private UpdatesListEntry selected;

    private double scrollPercent = 0;
    private boolean checkUpdatesError = false;


    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (updateList.isMouseOver(mouseX, mouseY)) {
            return this.updateList.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return false;
    }

    private Song searchForSongInJSON(File file) {
        File songListPath = new File(configManager.timmMusicDir, "songList.json");
        JsonObject songList = SongRegistry.getSongList();
        if (songList == null) {
            throw new RuntimeException("Error parsing songList.json: Either the file is missing or songList.json is missing the songs object");
        }
        Set<Map.Entry<String, JsonElement>> entrySet = songList.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            // check if song object is an actual JSON object
            JsonObject songObj;
            try {
                songObj = entry.getValue().getAsJsonObject();
            } catch (IllegalStateException e) {
                timmMain.LOGGER.warn(String.format("Song %s formatted incorrectly!", entry.getKey()));
                continue;
            }
            // get song metadata
            boolean isFile;
            String filePath;
            String author;
            String songName;
            try {
                isFile = songObj.get("is_file?").getAsBoolean();
            } catch (IllegalStateException e) {
                timmMain.LOGGER.warn(String.format("Song %s missing \"is_file?\" field!", entry.getKey()));
                continue;
            }
            try {
                filePath = songObj.get("file/id").getAsString();
            } catch (IllegalStateException e) {
                timmMain.LOGGER.warn(String.format("Song %s missing \"file/id\" field!", entry.getKey()));
                continue;
            }
            try {
                songName = songObj.get("song_name").getAsString();
            } catch (IllegalStateException e) {
                timmMain.LOGGER.warn(String.format("Song %s missing \"song_name\" field!", entry.getKey()));
                continue;
            }
            try {
                author = songObj.get("author").getAsString();
            } catch (IllegalStateException e) {
                timmMain.LOGGER.warn(String.format("Song %s missing \"author\" field!", entry.getKey()));
                continue;
            }

            if (!isFile) {
                continue;
            }

            if (!Objects.equals(filePath, file.getName())) {
                continue;
            }
            return new FileSong(filePath, songName, author);
        }
        return null;
    }

    @Override
    public void init() {
        if (getSongs.updates.code() != awsHelper.SUCCESS && getSongs.updates.code() != awsHelper.NO_LOCAL) {
            this.checkUpdatesError = true;
        }

        // initialize updateList
        this.updateList = new UpdatesListWidget(
                this.width - 20, // update list width
                this.height - 60, // update list height
                50, // update list ypos (ask mojang why it doesn't take an xpos as a constructor parameter)
                36, // update entry height
                this.updateList,
                this
        );
        this.updateList.setX(10);

        // add all files in GetUpdatesReturn to update list widget
        for (File fileToUpdate : getSongs.updates.filesToUpdate()) {
            Song song = searchForSongInJSON(fileToUpdate);
            if (song == null) {
                timmMain.LOGGER.warn("Could not find file \"{}\" in songList.json", fileToUpdate.getName());
                continue;
            }
            UpdateEntry entry = new UpdateEntry(song, fileToUpdate);
            this.updateList.addEntry(new UpdatesListEntry(entry, this.updateList));
        }

        // initialize accept button
        /*

        this.acceptButton = new SimpleButton(
                this,
                button -> {

                },

        );

         */

        // initialize decline button

        // initialize toggle updates button



        addSelectableChild(this.updateList);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);
        UpdatesListEntry selectedEntry = selected;
        if (selectedEntry != null) {
            // insert description list widget render call here
        }
        this.updateList.render(ctx, mouseX, mouseY, delta);


    }

    @Override
    public void close() {
        mc.setScreen(parent);
    }

    public double getScrollPercent() {
        return scrollPercent;
    }

    public void updateScrollPercent(double scrollPercent) {
        this.scrollPercent = scrollPercent;
    }

    public UpdatesListEntry getSelectedEntry() { return this.selected; }

    public void updateSelectedEntry(UpdatesListEntry entry) {
        if (entry == null) {
            return;
        }

        this.selected = entry;
    }

}
