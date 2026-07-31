/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.TypeAdapter
 *  com.google.gson.TypeAdapterFactory
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonToken
 *  com.google.gson.stream.JsonWriter
 *  javax.annotation.Nullable
 */
package net.minecraft.util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.annotation.Nullable;

public class LowerCaseEnumTypeAdapterFactory
implements TypeAdapterFactory {
    @Nullable
    public <T> TypeAdapter<T> create(Gson p_13982_, TypeToken<T> p_13983_) {
        Class $$2 = p_13983_.getRawType();
        if (!$$2.isEnum()) {
            return null;
        }
        final HashMap $$3 = Maps.newHashMap();
        for (Object $$4 : $$2.getEnumConstants()) {
            $$3.put(this.m_13979_($$4), $$4);
        }
        return new TypeAdapter<T>(){

            public void write(JsonWriter p_13992_, T p_13993_) throws IOException {
                if (p_13993_ == null) {
                    p_13992_.nullValue();
                } else {
                    p_13992_.value(LowerCaseEnumTypeAdapterFactory.this.m_13979_(p_13993_));
                }
            }

            @Nullable
            public T read(JsonReader p_13990_) throws IOException {
                if (p_13990_.peek() == JsonToken.NULL) {
                    p_13990_.nextNull();
                    return null;
                }
                return $$3.get(p_13990_.nextString());
            }
        };
    }

    String m_13979_(Object p_13980_) {
        if (p_13980_ instanceof Enum) {
            return ((Enum)p_13980_).name().toLowerCase(Locale.ROOT);
        }
        return p_13980_.toString().toLowerCase(Locale.ROOT);
    }
}

