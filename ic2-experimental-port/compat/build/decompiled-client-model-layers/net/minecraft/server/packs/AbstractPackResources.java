/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

public abstract class AbstractPackResources
implements PackResources {
    private static final Logger f_10204_ = LogUtils.getLogger();
    protected final File f_10203_;

    public AbstractPackResources(File p_10207_) {
        this.f_10203_ = p_10207_;
    }

    private static String m_10226_(PackType p_10227_, ResourceLocation p_10228_) {
        return String.format(Locale.ROOT, "%s/%s/%s", p_10227_.m_10305_(), p_10228_.m_135827_(), p_10228_.m_135815_());
    }

    protected static String m_10217_(File p_10218_, File p_10219_) {
        return p_10218_.toURI().relativize(p_10219_.toURI()).getPath();
    }

    @Override
    public InputStream m_8031_(PackType p_10210_, ResourceLocation p_10211_) throws IOException {
        return this.m_5541_(AbstractPackResources.m_10226_(p_10210_, p_10211_));
    }

    @Override
    public boolean m_7211_(PackType p_10222_, ResourceLocation p_10223_) {
        return this.m_6105_(AbstractPackResources.m_10226_(p_10222_, p_10223_));
    }

    protected abstract InputStream m_5541_(String var1) throws IOException;

    @Override
    public InputStream m_5542_(String p_10225_) throws IOException {
        if (p_10225_.contains("/") || p_10225_.contains("\\")) {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
        return this.m_5541_(p_10225_);
    }

    protected abstract boolean m_6105_(String var1);

    protected void m_10230_(String p_10231_) {
        f_10204_.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", (Object)p_10231_, (Object)this.f_10203_);
    }

    @Override
    @Nullable
    public <T> T m_5550_(MetadataSectionSerializer<T> p_10213_) throws IOException {
        try (InputStream $$1 = this.m_5541_("pack.mcmeta");){
            T t = AbstractPackResources.m_10214_(p_10213_, $$1);
            return t;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static <T> T m_10214_(MetadataSectionSerializer<T> p_10215_, InputStream p_10216_) {
        void $$6;
        try (BufferedReader $$2 = new BufferedReader(new InputStreamReader(p_10216_, StandardCharsets.UTF_8));){
            JsonObject $$3 = GsonHelper.m_13859_($$2);
        }
        catch (Exception $$5) {
            f_10204_.error("Couldn't load {} metadata", (Object)p_10215_.m_7991_(), (Object)$$5);
            return null;
        }
        if (!$$6.has(p_10215_.m_7991_())) {
            return null;
        }
        try {
            return p_10215_.m_6322_(GsonHelper.m_13930_((JsonObject)$$6, p_10215_.m_7991_()));
        }
        catch (Exception $$7) {
            f_10204_.error("Couldn't load {} metadata", (Object)p_10215_.m_7991_(), (Object)$$7);
            return null;
        }
    }

    @Override
    public String m_8017_() {
        return this.f_10203_.getName();
    }
}

