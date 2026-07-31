/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class ClientboundMapItemDataPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132415_;
    private final byte f_132416_;
    private final boolean f_132418_;
    @Nullable
    private final List<MapDecoration> f_132419_;
    @Nullable
    private final MapItemSavedData.MapPatch f_178968_;

    public ClientboundMapItemDataPacket(int p_178970_, byte p_178971_, boolean p_178972_, @Nullable Collection<MapDecoration> p_178973_, @Nullable MapItemSavedData.MapPatch p_178974_) {
        this.f_132415_ = p_178970_;
        this.f_132416_ = p_178971_;
        this.f_132418_ = p_178972_;
        this.f_132419_ = p_178973_ != null ? Lists.newArrayList(p_178973_) : null;
        this.f_178968_ = p_178974_;
    }

    public ClientboundMapItemDataPacket(FriendlyByteBuf p_178976_) {
        this.f_132415_ = p_178976_.m_130242_();
        this.f_132416_ = p_178976_.readByte();
        this.f_132418_ = p_178976_.readBoolean();
        this.f_132419_ = (List)p_178976_.m_236868_(p_237731_ -> p_237731_.m_236845_(p_178981_ -> {
            MapDecoration.Type $$1 = p_178981_.m_130066_(MapDecoration.Type.class);
            byte $$2 = p_178981_.readByte();
            byte $$3 = p_178981_.readByte();
            byte $$4 = (byte)(p_178981_.readByte() & 0xF);
            Component $$5 = (Component)p_178981_.m_236868_(FriendlyByteBuf::m_130238_);
            return new MapDecoration($$1, $$2, $$3, $$4, $$5);
        }));
        short $$1 = p_178976_.readUnsignedByte();
        if ($$1 > 0) {
            short $$2 = p_178976_.readUnsignedByte();
            short $$3 = p_178976_.readUnsignedByte();
            short $$4 = p_178976_.readUnsignedByte();
            byte[] $$5 = p_178976_.m_130052_();
            this.f_178968_ = new MapItemSavedData.MapPatch($$3, $$4, $$1, $$2, $$5);
        } else {
            this.f_178968_ = null;
        }
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132447_) {
        p_132447_.m_130130_(this.f_132415_);
        p_132447_.writeByte(this.f_132416_);
        p_132447_.writeBoolean(this.f_132418_);
        p_132447_.m_236821_(this.f_132419_, (p_237728_, p_237729_) -> p_237728_.m_236828_(p_237729_, (p_237725_, p_237726_) -> {
            p_237725_.m_130068_(p_237726_.m_77803_());
            p_237725_.writeByte(p_237726_.m_77804_());
            p_237725_.writeByte(p_237726_.m_77805_());
            p_237725_.writeByte(p_237726_.m_77806_() & 0xF);
            p_237725_.m_236821_(p_237726_.m_77810_(), FriendlyByteBuf::m_130083_);
        }));
        if (this.f_178968_ != null) {
            p_132447_.writeByte(this.f_178968_.f_164823_);
            p_132447_.writeByte(this.f_178968_.f_164824_);
            p_132447_.writeByte(this.f_178968_.f_164821_);
            p_132447_.writeByte(this.f_178968_.f_164822_);
            p_132447_.m_130087_(this.f_178968_.f_164825_);
        } else {
            p_132447_.writeByte(0);
        }
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132444_) {
        p_132444_.m_7633_(this);
    }

    public int m_132445_() {
        return this.f_132415_;
    }

    public void m_132437_(MapItemSavedData p_132438_) {
        if (this.f_132419_ != null) {
            p_132438_.m_164801_(this.f_132419_);
        }
        if (this.f_178968_ != null) {
            this.f_178968_.m_164832_(p_132438_);
        }
    }

    public byte m_178982_() {
        return this.f_132416_;
    }

    public boolean m_178983_() {
        return this.f_132418_;
    }
}

