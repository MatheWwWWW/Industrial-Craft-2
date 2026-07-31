/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

public abstract class StoredUserList<K, V extends StoredUserEntry<K>> {
    private static final Logger f_11374_ = LogUtils.getLogger();
    private static final Gson f_11375_ = new GsonBuilder().setPrettyPrinting().create();
    private final File f_11376_;
    private final Map<String, V> f_11377_ = Maps.newHashMap();

    public StoredUserList(File p_11380_) {
        this.f_11376_ = p_11380_;
    }

    public File m_11385_() {
        return this.f_11376_;
    }

    public void m_11381_(V p_11382_) {
        this.f_11377_.put(this.m_5981_(((StoredUserEntry)p_11382_).m_11373_()), p_11382_);
        try {
            this.m_11398_();
        }
        catch (IOException $$1) {
            f_11374_.warn("Could not save the list after adding a user.", (Throwable)$$1);
        }
    }

    @Nullable
    public V m_11388_(K p_11389_) {
        this.m_11400_();
        return (V)((StoredUserEntry)this.f_11377_.get(this.m_5981_(p_11389_)));
    }

    public void m_11393_(K p_11394_) {
        this.f_11377_.remove(this.m_5981_(p_11394_));
        try {
            this.m_11398_();
        }
        catch (IOException $$1) {
            f_11374_.warn("Could not save the list after removing a user.", (Throwable)$$1);
        }
    }

    public void m_11386_(StoredUserEntry<K> p_11387_) {
        this.m_11393_(p_11387_.m_11373_());
    }

    public String[] m_5875_() {
        return this.f_11377_.keySet().toArray(new String[0]);
    }

    public boolean m_11390_() {
        return this.f_11377_.size() < 1;
    }

    protected String m_5981_(K p_11384_) {
        return p_11384_.toString();
    }

    protected boolean m_11396_(K p_11397_) {
        return this.f_11377_.containsKey(this.m_5981_(p_11397_));
    }

    private void m_11400_() {
        ArrayList $$0 = Lists.newArrayList();
        for (StoredUserEntry $$1 : this.f_11377_.values()) {
            if (!$$1.m_7524_()) continue;
            $$0.add($$1.m_11373_());
        }
        for (Object $$2 : $$0) {
            this.f_11377_.remove(this.m_5981_($$2));
        }
    }

    protected abstract StoredUserEntry<K> m_6666_(JsonObject var1);

    public Collection<V> m_11395_() {
        return this.f_11377_.values();
    }

    public void m_11398_() throws IOException {
        JsonArray $$0 = new JsonArray();
        this.f_11377_.values().stream().map(p_11392_ -> Util.m_137469_(new JsonObject(), p_11392_::m_6009_)).forEach(arg_0 -> ((JsonArray)$$0).add(arg_0));
        try (BufferedWriter $$1 = Files.newWriter((File)this.f_11376_, (Charset)StandardCharsets.UTF_8);){
            f_11375_.toJson((JsonElement)$$0, (Appendable)$$1);
        }
    }

    public void m_11399_() throws IOException {
        if (!this.f_11376_.exists()) {
            return;
        }
        try (BufferedReader $$0 = Files.newReader((File)this.f_11376_, (Charset)StandardCharsets.UTF_8);){
            JsonArray $$1 = (JsonArray)f_11375_.fromJson((Reader)$$0, JsonArray.class);
            this.f_11377_.clear();
            for (JsonElement $$2 : $$1) {
                JsonObject $$3 = GsonHelper.m_13918_($$2, "entry");
                StoredUserEntry<K> $$4 = this.m_6666_($$3);
                if ($$4.m_11373_() == null) continue;
                this.f_11377_.put(this.m_5981_($$4.m_11373_()), $$4);
            }
        }
    }
}

