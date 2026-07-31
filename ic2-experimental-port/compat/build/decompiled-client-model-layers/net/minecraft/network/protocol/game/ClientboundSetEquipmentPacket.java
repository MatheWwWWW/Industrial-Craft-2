/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ClientboundSetEquipmentPacket
implements Packet<ClientGamePacketListener> {
    private static final byte f_179295_ = -128;
    private final int f_133198_;
    private final List<Pair<EquipmentSlot, ItemStack>> f_133199_;

    public ClientboundSetEquipmentPacket(int p_133202_, List<Pair<EquipmentSlot, ItemStack>> p_133203_) {
        this.f_133198_ = p_133202_;
        this.f_133199_ = p_133203_;
    }

    public ClientboundSetEquipmentPacket(FriendlyByteBuf p_179297_) {
        byte $$2;
        this.f_133198_ = p_179297_.m_130242_();
        EquipmentSlot[] $$1 = EquipmentSlot.values();
        this.f_133199_ = Lists.newArrayList();
        do {
            $$2 = p_179297_.readByte();
            EquipmentSlot $$3 = $$1[$$2 & 0x7F];
            ItemStack $$4 = p_179297_.m_130267_();
            this.f_133199_.add((Pair<EquipmentSlot, ItemStack>)Pair.of((Object)((Object)$$3), (Object)$$4));
        } while (($$2 & 0xFFFFFF80) != 0);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133212_) {
        p_133212_.m_130130_(this.f_133198_);
        int $$1 = this.f_133199_.size();
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            Pair<EquipmentSlot, ItemStack> $$3 = this.f_133199_.get($$2);
            EquipmentSlot $$4 = (EquipmentSlot)((Object)$$3.getFirst());
            boolean $$5 = $$2 != $$1 - 1;
            int $$6 = $$4.ordinal();
            p_133212_.writeByte($$5 ? $$6 | 0xFFFFFF80 : $$6);
            p_133212_.m_130055_((ItemStack)$$3.getSecond());
        }
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133209_) {
        p_133209_.m_7277_(this);
    }

    public int m_133210_() {
        return this.f_133198_;
    }

    public List<Pair<EquipmentSlot, ItemStack>> m_133213_() {
        return this.f_133199_;
    }
}

