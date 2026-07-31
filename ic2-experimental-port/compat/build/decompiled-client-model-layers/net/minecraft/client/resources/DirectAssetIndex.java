/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.resources;

import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class DirectAssetIndex
extends AssetIndex {
    private static final Logger f_202441_ = LogUtils.getLogger();
    private final File f_118633_;

    public DirectAssetIndex(File p_118635_) {
        this.f_118633_ = p_118635_;
    }

    @Override
    public File m_7879_(ResourceLocation p_118653_) {
        return new File(this.f_118633_, p_118653_.toString().replace(':', '/'));
    }

    @Override
    public File m_7974_(String p_118637_) {
        return new File(this.f_118633_, p_118637_);
    }

    @Override
    public Collection<ResourceLocation> m_214011_(String p_235016_, String p_235017_, Predicate<ResourceLocation> p_235018_) {
        block10: {
            Collection collection;
            block9: {
                Path $$3 = this.f_118633_.toPath().resolve(p_235017_);
                Stream<Path> $$42 = Files.walk($$3.resolve(p_235016_), new FileVisitOption[0]);
                try {
                    collection = $$42.filter(p_118655_ -> Files.isRegularFile(p_118655_, new LinkOption[0])).filter(p_118648_ -> !p_118648_.endsWith(".mcmeta")).map(p_235022_ -> new ResourceLocation(p_235017_, $$3.relativize((Path)p_235022_).toString().replaceAll("\\\\", "/"))).filter(p_235018_).collect(Collectors.toList());
                    if ($$42 == null) break block9;
                }
                catch (Throwable throwable) {
                    try {
                        if ($$42 != null) {
                            try {
                                $$42.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (NoSuchFileException $$42) {
                        break block10;
                    }
                    catch (IOException $$5) {
                        f_202441_.warn("Unable to getFiles on {}", (Object)p_235016_, (Object)$$5);
                    }
                }
                $$42.close();
            }
            return collection;
        }
        return Collections.emptyList();
    }
}

