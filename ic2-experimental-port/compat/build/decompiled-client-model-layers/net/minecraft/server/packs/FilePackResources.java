/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.ResourcePackFileNotFoundException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class FilePackResources
extends AbstractPackResources {
    private static final Logger f_215322_ = LogUtils.getLogger();
    public static final Splitter f_10232_ = Splitter.on((char)'/').omitEmptyStrings().limit(3);
    @Nullable
    private ZipFile f_10233_;

    public FilePackResources(File p_10236_) {
        super(p_10236_);
    }

    private ZipFile m_10247_() throws IOException {
        if (this.f_10233_ == null) {
            this.f_10233_ = new ZipFile(this.f_10203_);
        }
        return this.f_10233_;
    }

    @Override
    protected InputStream m_5541_(String p_10246_) throws IOException {
        ZipFile $$1 = this.m_10247_();
        ZipEntry $$2 = $$1.getEntry(p_10246_);
        if ($$2 == null) {
            throw new ResourcePackFileNotFoundException(this.f_10203_, p_10246_);
        }
        return $$1.getInputStream($$2);
    }

    @Override
    public boolean m_6105_(String p_10249_) {
        try {
            return this.m_10247_().getEntry(p_10249_) != null;
        }
        catch (IOException $$1) {
            return false;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Set<String> m_5698_(PackType p_10238_) {
        void $$3;
        try {
            ZipFile $$1 = this.m_10247_();
        }
        catch (IOException $$2) {
            return Collections.emptySet();
        }
        Enumeration<? extends ZipEntry> $$4 = $$3.entries();
        HashSet $$5 = Sets.newHashSet();
        while ($$4.hasMoreElements()) {
            ArrayList $$8;
            ZipEntry $$6 = $$4.nextElement();
            String $$7 = $$6.getName();
            if (!$$7.startsWith(p_10238_.m_10305_() + "/") || ($$8 = Lists.newArrayList((Iterable)f_10232_.split((CharSequence)$$7))).size() <= 1) continue;
            String $$9 = (String)$$8.get(1);
            if ($$9.equals($$9.toLowerCase(Locale.ROOT))) {
                $$5.add($$9);
                continue;
            }
            this.m_10230_($$9);
        }
        return $$5;
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    @Override
    public void close() {
        if (this.f_10233_ != null) {
            IOUtils.closeQuietly((Closeable)this.f_10233_);
            this.f_10233_ = null;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Collection<ResourceLocation> m_214146_(PackType p_215324_, String p_215325_, String p_215326_, Predicate<ResourceLocation> p_215327_) {
        void $$6;
        try {
            ZipFile $$4 = this.m_10247_();
        }
        catch (IOException $$5) {
            return Collections.emptySet();
        }
        Enumeration<? extends ZipEntry> $$7 = $$6.entries();
        ArrayList $$8 = Lists.newArrayList();
        String $$9 = p_215324_.m_10305_() + "/" + p_215325_ + "/";
        String $$10 = $$9 + p_215326_ + "/";
        while ($$7.hasMoreElements()) {
            String $$12;
            ZipEntry $$11 = $$7.nextElement();
            if ($$11.isDirectory() || ($$12 = $$11.getName()).endsWith(".mcmeta") || !$$12.startsWith($$10)) continue;
            String $$13 = $$12.substring($$9.length());
            ResourceLocation $$14 = ResourceLocation.m_214293_(p_215325_, $$13);
            if ($$14 == null) {
                f_215322_.warn("Invalid path in datapack: {}:{}, ignoring", (Object)p_215325_, (Object)$$13);
                continue;
            }
            if (!p_215327_.test($$14)) continue;
            $$8.add($$14);
        }
        return $$8;
    }
}

