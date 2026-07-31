/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class AssetIndex {
    private static final Logger f_118524_ = LogUtils.getLogger();
    private final Map<String, File> f_118525_ = Maps.newHashMap();
    private final Map<ResourceLocation, File> f_118526_ = Maps.newHashMap();

    protected AssetIndex() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AssetIndex(File p_118530_, String p_118531_) {
        File $$2 = new File(p_118530_, "objects");
        File $$3 = new File(p_118530_, "indexes/" + p_118531_ + ".json");
        BufferedReader $$4 = null;
        try {
            $$4 = Files.newReader((File)$$3, (Charset)StandardCharsets.UTF_8);
            JsonObject $$5 = GsonHelper.m_13859_($$4);
            JsonObject $$6 = GsonHelper.m_13841_($$5, "objects", null);
            if ($$6 != null) {
                for (Map.Entry $$7 : $$6.entrySet()) {
                    JsonObject $$8 = (JsonObject)$$7.getValue();
                    String $$9 = (String)$$7.getKey();
                    String[] $$10 = $$9.split("/", 2);
                    String $$11 = GsonHelper.m_13906_($$8, "hash");
                    File $$12 = new File($$2, $$11.substring(0, 2) + "/" + $$11);
                    if ($$10.length == 1) {
                        this.f_118525_.put($$10[0], $$12);
                        continue;
                    }
                    this.f_118526_.put(new ResourceLocation($$10[0], $$10[1]), $$12);
                }
            }
        }
        catch (JsonParseException $$13) {
            f_118524_.error("Unable to parse resource index file: {}", (Object)$$3);
        }
        catch (FileNotFoundException $$14) {
            f_118524_.error("Can't find the resource index file: {}", (Object)$$3);
        }
        finally {
            IOUtils.closeQuietly((Reader)$$4);
        }
    }

    @Nullable
    public File m_7879_(ResourceLocation p_118542_) {
        return this.f_118526_.get(p_118542_);
    }

    @Nullable
    public File m_7974_(String p_118532_) {
        return this.f_118525_.get(p_118532_);
    }

    public Collection<ResourceLocation> m_214011_(String p_235000_, String p_235001_, Predicate<ResourceLocation> p_235002_) {
        return this.f_118526_.keySet().stream().filter(p_118541_ -> {
            String $$4 = p_118541_.m_135815_();
            return p_118541_.m_135827_().equals(p_235001_) && !$$4.endsWith(".mcmeta") && $$4.startsWith(p_235000_ + "/") && p_235002_.test((ResourceLocation)p_118541_);
        }).collect(Collectors.toList());
    }
}

