/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 */
package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.function.IntFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

public class ServerboundContainerClickPacket
implements Packet<ServerGamePacketListener> {
    private static final int f_182731_ = 128;
    private final int f_133939_;
    private final int f_182732_;
    private final int f_133940_;
    private final int f_133941_;
    private final ClickType f_133944_;
    private final ItemStack f_179568_;
    private final Int2ObjectMap<ItemStack> f_179569_;

    public ServerboundContainerClickPacket(int p_182734_, int p_182735_, int p_182736_, int p_182737_, ClickType p_182738_, ItemStack p_182739_, Int2ObjectMap<ItemStack> p_182740_) {
        this.f_133939_ = p_182734_;
        this.f_182732_ = p_182735_;
        this.f_133940_ = p_182736_;
        this.f_133941_ = p_182737_;
        this.f_133944_ = p_182738_;
        this.f_179568_ = p_182739_;
        this.f_179569_ = Int2ObjectMaps.unmodifiable(p_182740_);
    }

    public ServerboundContainerClickPacket(FriendlyByteBuf p_179578_) {
        this.f_133939_ = p_179578_.readByte();
        this.f_182732_ = p_179578_.m_130242_();
        this.f_133940_ = p_179578_.readShort();
        this.f_133941_ = p_179578_.readByte();
        this.f_133944_ = p_179578_.m_130066_(ClickType.class);
        IntFunction<Int2ObjectOpenHashMap> $$1 = FriendlyByteBuf.m_182695_(Int2ObjectOpenHashMap::new, 128);
        this.f_179569_ = Int2ObjectMaps.unmodifiable((Int2ObjectMap)((Int2ObjectMap)p_179578_.m_236841_($$1, p_179580_ -> p_179580_.readShort(), FriendlyByteBuf::m_130267_)));
        this.f_179568_ = p_179578_.m_130267_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133961_) {
        p_133961_.writeByte(this.f_133939_);
        p_133961_.m_130130_(this.f_182732_);
        p_133961_.writeShort(this.f_133940_);
        p_133961_.writeByte(this.f_133941_);
        p_133961_.m_130068_(this.f_133944_);
        p_133961_.m_236831_(this.f_179569_, FriendlyByteBuf::writeShort, FriendlyByteBuf::m_130055_);
        p_133961_.m_130055_(this.f_179568_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133958_) {
        p_133958_.m_5914_(this);
    }

    public int m_133959_() {
        return this.f_133939_;
    }

    public int m_133962_() {
        return this.f_133940_;
    }

    public int m_133963_() {
        return this.f_133941_;
    }

    public ItemStack m_179581_() {
        return this.f_179568_;
    }

    public Int2ObjectMap<ItemStack> m_179582_() {
        return this.f_179569_;
    }

    public ClickType m_133966_() {
        return this.f_133944_;
    }

    public int m_182741_() {
        return this.f_182732_;
    }
}

