/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundEditBookPacket
implements Packet<ServerGamePacketListener> {
    public static final int f_182742_ = 4;
    private static final int f_182743_ = 128;
    private static final int f_182744_ = 8192;
    private static final int f_182745_ = 200;
    private final int f_133997_;
    private final List<String> f_182746_;
    private final Optional<String> f_182747_;

    public ServerboundEditBookPacket(int p_182749_, List<String> p_182750_, Optional<String> p_182751_) {
        this.f_133997_ = p_182749_;
        this.f_182746_ = ImmutableList.copyOf(p_182750_);
        this.f_182747_ = p_182751_;
    }

    public ServerboundEditBookPacket(FriendlyByteBuf p_179592_) {
        this.f_133997_ = p_179592_.m_130242_();
        this.f_182746_ = p_179592_.m_236838_(FriendlyByteBuf.m_182695_(Lists::newArrayListWithCapacity, 200), p_182763_ -> p_182763_.m_130136_(8192));
        this.f_182747_ = p_179592_.m_236860_(p_182757_ -> p_182757_.m_130136_(128));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134011_) {
        p_134011_.m_130130_(this.f_133997_);
        p_134011_.m_236828_(this.f_182746_, (p_182759_, p_182760_) -> p_182759_.m_130072_((String)p_182760_, 8192));
        p_134011_.m_236835_(this.f_182747_, (p_182753_, p_182754_) -> p_182753_.m_130072_((String)p_182754_, 128));
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134008_) {
        p_134008_.m_6829_(this);
    }

    public List<String> m_182755_() {
        return this.f_182746_;
    }

    public Optional<String> m_182761_() {
        return this.f_182747_;
    }

    public int m_134013_() {
        return this.f_133997_;
    }
}

