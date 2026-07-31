/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;

public class ClientboundCustomPayloadPacket
implements Packet<ClientGamePacketListener> {
    private static final int f_178834_ = 0x100000;
    public static final ResourceLocation f_132012_ = new ResourceLocation("brand");
    public static final ResourceLocation f_132013_ = new ResourceLocation("debug/path");
    public static final ResourceLocation f_132014_ = new ResourceLocation("debug/neighbors_update");
    public static final ResourceLocation f_132016_ = new ResourceLocation("debug/structures");
    public static final ResourceLocation f_132017_ = new ResourceLocation("debug/worldgen_attempt");
    public static final ResourceLocation f_132018_ = new ResourceLocation("debug/poi_ticket_count");
    public static final ResourceLocation f_132019_ = new ResourceLocation("debug/poi_added");
    public static final ResourceLocation f_132020_ = new ResourceLocation("debug/poi_removed");
    public static final ResourceLocation f_132021_ = new ResourceLocation("debug/village_sections");
    public static final ResourceLocation f_132022_ = new ResourceLocation("debug/goal_selector");
    public static final ResourceLocation f_132023_ = new ResourceLocation("debug/brain");
    public static final ResourceLocation f_132024_ = new ResourceLocation("debug/bee");
    public static final ResourceLocation f_132025_ = new ResourceLocation("debug/hive");
    public static final ResourceLocation f_132026_ = new ResourceLocation("debug/game_test_add_marker");
    public static final ResourceLocation f_132027_ = new ResourceLocation("debug/game_test_clear");
    public static final ResourceLocation f_132028_ = new ResourceLocation("debug/raids");
    public static final ResourceLocation f_178832_ = new ResourceLocation("debug/game_event");
    public static final ResourceLocation f_178833_ = new ResourceLocation("debug/game_event_listeners");
    private final ResourceLocation f_132029_;
    private final FriendlyByteBuf f_132030_;

    public ClientboundCustomPayloadPacket(ResourceLocation p_132034_, FriendlyByteBuf p_132035_) {
        this.f_132029_ = p_132034_;
        this.f_132030_ = p_132035_;
        if (p_132035_.writerIndex() > 0x100000) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    public ClientboundCustomPayloadPacket(FriendlyByteBuf p_178836_) {
        this.f_132029_ = p_178836_.m_130281_();
        int $$1 = p_178836_.readableBytes();
        if ($$1 < 0 || $$1 > 0x100000) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
        this.f_132030_ = new FriendlyByteBuf(p_178836_.readBytes($$1));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132044_) {
        p_132044_.m_130085_(this.f_132029_);
        p_132044_.writeBytes(this.f_132030_.copy());
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132041_) {
        p_132041_.m_7413_(this);
    }

    public ResourceLocation m_132042_() {
        return this.f_132029_;
    }

    public FriendlyByteBuf m_132045_() {
        return new FriendlyByteBuf(this.f_132030_.copy());
    }
}

