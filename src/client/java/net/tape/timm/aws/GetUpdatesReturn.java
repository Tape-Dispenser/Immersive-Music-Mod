package net.tape.timm.aws;

import java.io.File;
import java.util.HashSet;

public record GetUpdatesReturn(int code, HashSet<File> filesToUpdate) {
}
