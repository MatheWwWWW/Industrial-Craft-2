/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

@FunctionalInterface
public interface ResourceProvider {
    public Optional<Resource> m_213713_(ResourceLocation var1);

    default public Resource m_215593_(ResourceLocation p_215594_) throws FileNotFoundException {
        return this.m_213713_(p_215594_).orElseThrow(() -> new FileNotFoundException(p_215594_.toString()));
    }

    default public InputStream m_215595_(ResourceLocation p_215596_) throws IOException {
        return this.m_215593_(p_215596_).m_215507_();
    }

    default public BufferedReader m_215597_(ResourceLocation p_215598_) throws IOException {
        return this.m_215593_(p_215598_).m_215508_();
    }
}

