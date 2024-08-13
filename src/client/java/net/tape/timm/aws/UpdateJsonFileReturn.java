package net.tape.timm.aws;

import java.io.File;

public record UpdateJsonFileReturn(int code, File local, File server) {
    public UpdateJsonFileReturn(int code) {
        this(code, null, null);
    }
}