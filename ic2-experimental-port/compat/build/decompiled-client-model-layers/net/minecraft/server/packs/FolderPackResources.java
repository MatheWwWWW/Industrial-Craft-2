/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.CharMatcher
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.filefilter.DirectoryFileFilter
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.ResourcePackFileNotFoundException;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.slf4j.Logger;

public class FolderPackResources
extends AbstractPackResources {
    private static final Logger f_10252_ = LogUtils.getLogger();
    private static final boolean f_10253_ = Util.m_137581_() == Util.OS.WINDOWS;
    private static final CharMatcher f_10254_ = CharMatcher.is((char)'\\');

    public FolderPackResources(File p_10257_) {
        super(p_10257_);
    }

    public static boolean m_10273_(File p_10274_, String p_10275_) throws IOException {
        String $$2 = p_10274_.getCanonicalPath();
        if (f_10253_) {
            $$2 = f_10254_.replaceFrom((CharSequence)$$2, '/');
        }
        return $$2.endsWith(p_10275_);
    }

    @Override
    protected InputStream m_5541_(String p_10277_) throws IOException {
        File $$1 = this.m_10281_(p_10277_);
        if ($$1 == null) {
            throw new ResourcePackFileNotFoundException(this.f_10203_, p_10277_);
        }
        return new FileInputStream($$1);
    }

    @Override
    protected boolean m_6105_(String p_10279_) {
        return this.m_10281_(p_10279_) != null;
    }

    @Nullable
    private File m_10281_(String p_10282_) {
        try {
            File $$1 = new File(this.f_10203_, p_10282_);
            if ($$1.isFile() && FolderPackResources.m_10273_($$1, p_10282_)) {
                return $$1;
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public Set<String> m_5698_(PackType p_10259_) {
        HashSet $$1 = Sets.newHashSet();
        File $$2 = new File(this.f_10203_, p_10259_.m_10305_());
        File[] $$3 = $$2.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
        if ($$3 != null) {
            for (File $$4 : $$3) {
                String $$5 = FolderPackResources.m_10217_($$2, $$4);
                if ($$5.equals($$5.toLowerCase(Locale.ROOT))) {
                    $$1.add($$5.substring(0, $$5.length() - 1));
                    continue;
                }
                this.m_10230_($$5);
            }
        }
        return $$1;
    }

    @Override
    public void close() {
    }

    @Override
    public Collection<ResourceLocation> m_214146_(PackType p_215329_, String p_215330_, String p_215331_, Predicate<ResourceLocation> p_215332_) {
        File $$4 = new File(this.f_10203_, p_215329_.m_10305_());
        ArrayList $$5 = Lists.newArrayList();
        this.m_215333_(new File(new File($$4, p_215330_), p_215331_), p_215330_, $$5, p_215331_ + "/", p_215332_);
        return $$5;
    }

    private void m_215333_(File p_215334_, String p_215335_, List<ResourceLocation> p_215336_, String p_215337_, Predicate<ResourceLocation> p_215338_) {
        File[] $$5 = p_215334_.listFiles();
        if ($$5 != null) {
            for (File $$6 : $$5) {
                if ($$6.isDirectory()) {
                    this.m_215333_($$6, p_215335_, p_215336_, p_215337_ + $$6.getName() + "/", p_215338_);
                    continue;
                }
                if ($$6.getName().endsWith(".mcmeta")) continue;
                try {
                    String $$7 = p_215337_ + $$6.getName();
                    ResourceLocation $$8 = ResourceLocation.m_214293_(p_215335_, $$7);
                    if ($$8 == null) {
                        f_10252_.warn("Invalid path in datapack: {}:{}, ignoring", (Object)p_215335_, (Object)$$7);
                        continue;
                    }
                    if (!p_215338_.test($$8)) continue;
                    p_215336_.add($$8);
                }
                catch (ResourceLocationException $$9) {
                    f_10252_.error($$9.getMessage());
                }
            }
        }
    }
}

