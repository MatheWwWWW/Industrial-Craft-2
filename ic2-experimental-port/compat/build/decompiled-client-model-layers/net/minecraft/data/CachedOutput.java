/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.HashCode
 */
package net.minecraft.data;

import com.google.common.hash.HashCode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

public interface CachedOutput {
    public static final CachedOutput f_236016_ = (p_236019_, p_236020_, p_236021_) -> {
        Files.createDirectories(p_236019_.getParent(), new FileAttribute[0]);
        Files.write(p_236019_, p_236020_, new OpenOption[0]);
    };

    public void m_213871_(Path var1, byte[] var2, HashCode var3) throws IOException;
}

