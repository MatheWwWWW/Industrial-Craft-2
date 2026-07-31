/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class LegacyStuffWrapper {
    @Deprecated
    public static int[] m_118726_(ResourceManager p_118727_, ResourceLocation p_118728_) throws IOException {
        try (InputStream $$2 = p_118727_.m_215595_(p_118728_);){
            NativeImage $$3 = NativeImage.m_85058_($$2);
            try {
                int[] nArray = $$3.m_85118_();
                if ($$3 != null) {
                    $$3.close();
                }
                return nArray;
            }
            catch (Throwable throwable) {
                if ($$3 != null) {
                    try {
                        $$3.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
        }
    }
}

