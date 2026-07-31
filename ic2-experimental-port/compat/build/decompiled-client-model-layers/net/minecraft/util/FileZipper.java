/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import org.slf4j.Logger;

public class FileZipper
implements Closeable {
    private static final Logger f_144691_ = LogUtils.getLogger();
    private final Path f_144692_;
    private final Path f_144693_;
    private final FileSystem f_144694_;

    public FileZipper(Path p_144697_) {
        this.f_144692_ = p_144697_;
        this.f_144693_ = p_144697_.resolveSibling(p_144697_.getFileName().toString() + "_tmp");
        try {
            this.f_144694_ = Util.f_143778_.newFileSystem(this.f_144693_, (Map<String, ?>)ImmutableMap.of((Object)"create", (Object)"true"));
        }
        catch (IOException $$1) {
            throw new UncheckedIOException($$1);
        }
    }

    public void m_144703_(Path p_144704_, String p_144705_) {
        try {
            Path $$2 = this.f_144694_.getPath(File.separator, new String[0]);
            Path $$3 = $$2.resolve(p_144704_.toString());
            Files.createDirectories($$3.getParent(), new FileAttribute[0]);
            Files.write($$3, p_144705_.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (IOException $$4) {
            throw new UncheckedIOException($$4);
        }
    }

    public void m_144700_(Path p_144701_, File p_144702_) {
        try {
            Path $$2 = this.f_144694_.getPath(File.separator, new String[0]);
            Path $$3 = $$2.resolve(p_144701_.toString());
            Files.createDirectories($$3.getParent(), new FileAttribute[0]);
            Files.copy(p_144702_.toPath(), $$3, new CopyOption[0]);
        }
        catch (IOException $$4) {
            throw new UncheckedIOException($$4);
        }
    }

    public void m_144698_(Path p_144699_) {
        try {
            Path $$1 = this.f_144694_.getPath(File.separator, new String[0]);
            if (Files.isRegularFile(p_144699_, new LinkOption[0])) {
                Path $$2 = $$1.resolve(p_144699_.getParent().relativize(p_144699_).toString());
                Files.copy($$2, p_144699_, new CopyOption[0]);
                return;
            }
            try (Stream<Path> $$3 = Files.find(p_144699_, Integer.MAX_VALUE, (p_144707_, p_144708_) -> p_144708_.isRegularFile(), new FileVisitOption[0]);){
                for (Path $$4 : $$3.collect(Collectors.toList())) {
                    Path $$5 = $$1.resolve(p_144699_.relativize($$4).toString());
                    Files.createDirectories($$5.getParent(), new FileAttribute[0]);
                    Files.copy($$4, $$5, new CopyOption[0]);
                }
            }
        }
        catch (IOException $$6) {
            throw new UncheckedIOException($$6);
        }
    }

    @Override
    public void close() {
        try {
            this.f_144694_.close();
            Files.move(this.f_144693_, this.f_144692_, new CopyOption[0]);
            f_144691_.info("Compressed to {}", (Object)this.f_144692_);
        }
        catch (IOException $$0) {
            throw new UncheckedIOException($$0);
        }
    }
}

