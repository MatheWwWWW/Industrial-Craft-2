/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.common.hash.HashingOutputStream
 *  com.google.gson.JsonElement
 *  com.google.gson.stream.JsonWriter
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.ToIntFunction;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.util.GsonHelper;

public interface DataProvider {
    public static final ToIntFunction<String> f_236067_ = (ToIntFunction)Util.m_137469_(new Object2IntOpenHashMap(), p_236070_ -> {
        p_236070_.put((Object)"type", 0);
        p_236070_.put((Object)"parent", 1);
        p_236070_.defaultReturnValue(2);
    });
    public static final Comparator<String> f_236068_ = Comparator.comparingInt(f_236067_).thenComparing(p_236077_ -> p_236077_);

    public void m_213708_(CachedOutput var1) throws IOException;

    public String m_6055_();

    public static void m_236072_(CachedOutput p_236073_, JsonElement p_236074_, Path p_236075_) throws IOException {
        ByteArrayOutputStream $$3 = new ByteArrayOutputStream();
        HashingOutputStream $$4 = new HashingOutputStream(Hashing.sha1(), (OutputStream)$$3);
        OutputStreamWriter $$5 = new OutputStreamWriter((OutputStream)$$4, StandardCharsets.UTF_8);
        JsonWriter $$6 = new JsonWriter((Writer)$$5);
        $$6.setSerializeNulls(false);
        $$6.setIndent("  ");
        GsonHelper.m_216207_($$6, p_236074_, f_236068_);
        $$6.close();
        p_236073_.m_213871_(p_236075_, $$3.toByteArray(), $$4.hash());
    }
}

