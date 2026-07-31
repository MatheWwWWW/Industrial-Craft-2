/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.core;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.Util;

public final class UUIDUtil {
    public static final Codec<UUID> f_235867_ = Codec.INT_STREAM.comapFlatMap(p_235884_ -> Util.m_137539_(p_235884_, 4).map(UUIDUtil::m_235885_), p_235888_ -> Arrays.stream(UUIDUtil.m_235881_(p_235888_)));
    public static final int f_235868_ = 16;
    private static final String f_235869_ = "OfflinePlayer:";

    private UUIDUtil() {
    }

    public static UUID m_235885_(int[] p_235886_) {
        return new UUID((long)p_235886_[0] << 32 | (long)p_235886_[1] & 0xFFFFFFFFL, (long)p_235886_[2] << 32 | (long)p_235886_[3] & 0xFFFFFFFFL);
    }

    public static int[] m_235881_(UUID p_235882_) {
        long $$1 = p_235882_.getMostSignificantBits();
        long $$2 = p_235882_.getLeastSignificantBits();
        return UUIDUtil.m_235872_($$1, $$2);
    }

    private static int[] m_235872_(long p_235873_, long p_235874_) {
        return new int[]{(int)(p_235873_ >> 32), (int)p_235873_, (int)(p_235874_ >> 32), (int)p_235874_};
    }

    public static byte[] m_241191_(UUID p_241285_) {
        byte[] $$1 = new byte[16];
        ByteBuffer.wrap($$1).order(ByteOrder.BIG_ENDIAN).putLong(p_241285_.getMostSignificantBits()).putLong(p_241285_.getLeastSignificantBits());
        return $$1;
    }

    public static UUID m_235877_(Dynamic<?> p_235878_) {
        int[] $$1 = p_235878_.asIntStream().toArray();
        if ($$1.length != 4) {
            throw new IllegalArgumentException("Could not read UUID. Expected int-array of length 4, got " + $$1.length + ".");
        }
        return UUIDUtil.m_235885_($$1);
    }

    public static UUID m_235875_(GameProfile p_235876_) {
        UUID $$1 = p_235876_.getId();
        if ($$1 == null) {
            $$1 = UUIDUtil.m_235879_(p_235876_.getName());
        }
        return $$1;
    }

    public static UUID m_235879_(String p_235880_) {
        return UUID.nameUUIDFromBytes((f_235869_ + p_235880_).getBytes(StandardCharsets.UTF_8));
    }
}

