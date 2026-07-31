/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.server.packs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;

public interface PackResources
extends AutoCloseable {
    public static final String f_143748_ = ".mcmeta";
    public static final String f_143749_ = "pack.mcmeta";

    @Nullable
    public InputStream m_5542_(String var1) throws IOException;

    public InputStream m_8031_(PackType var1, ResourceLocation var2) throws IOException;

    public Collection<ResourceLocation> m_214146_(PackType var1, String var2, String var3, Predicate<ResourceLocation> var4);

    public boolean m_7211_(PackType var1, ResourceLocation var2);

    public Set<String> m_5698_(PackType var1);

    @Nullable
    public <T> T m_5550_(MetadataSectionSerializer<T> var1) throws IOException;

    public String m_8017_();

    @Override
    public void close();
}

