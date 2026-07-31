/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.system.MemoryUtil$MemoryAllocator
 */
package com.mojang.blaze3d.platform;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;

public class MemoryTracker {
    private static final MemoryUtil.MemoryAllocator f_182525_ = MemoryUtil.getAllocator((boolean)false);

    public static ByteBuffer m_182527_(int p_182528_) {
        long $$1 = f_182525_.malloc((long)p_182528_);
        if ($$1 == 0L) {
            throw new OutOfMemoryError("Failed to allocate " + p_182528_ + " bytes");
        }
        return MemoryUtil.memByteBuffer((long)$$1, (int)p_182528_);
    }

    public static ByteBuffer m_182529_(ByteBuffer p_182530_, int p_182531_) {
        long $$2 = f_182525_.realloc(MemoryUtil.memAddress0((Buffer)p_182530_), (long)p_182531_);
        if ($$2 == 0L) {
            throw new OutOfMemoryError("Failed to resize buffer from " + p_182530_.capacity() + " bytes to " + p_182531_ + " bytes");
        }
        return MemoryUtil.memByteBuffer((long)$$2, (int)p_182531_);
    }
}

