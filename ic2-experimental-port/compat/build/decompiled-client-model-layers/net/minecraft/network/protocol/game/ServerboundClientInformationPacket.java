/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;

public record ServerboundClientInformationPacket(String f_133863_, int f_133864_, ChatVisiblity f_133865_, boolean f_133866_, int f_133867_, HumanoidArm f_133868_, boolean f_179550_, boolean f_195812_) implements Packet<ServerGamePacketListener>
{
    public static final int f_179549_ = 16;

    public ServerboundClientInformationPacket(FriendlyByteBuf p_179560_) {
        this(p_179560_.m_130136_(16), p_179560_.readByte(), p_179560_.m_130066_(ChatVisiblity.class), p_179560_.readBoolean(), p_179560_.readUnsignedByte(), p_179560_.m_130066_(HumanoidArm.class), p_179560_.readBoolean(), p_179560_.readBoolean());
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133884_) {
        p_133884_.m_130070_(this.f_133863_);
        p_133884_.writeByte(this.f_133864_);
        p_133884_.m_130068_(this.f_133865_);
        p_133884_.writeBoolean(this.f_133866_);
        p_133884_.writeByte(this.f_133867_);
        p_133884_.m_130068_(this.f_133868_);
        p_133884_.writeBoolean(this.f_179550_);
        p_133884_.writeBoolean(this.f_195812_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133882_) {
        p_133882_.m_5617_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ServerboundClientInformationPacket.class, "language;viewDistance;chatVisibility;chatColors;modelCustomisation;mainHand;textFilteringEnabled;allowsListing", "f_133863_", "f_133864_", "f_133865_", "f_133866_", "f_133867_", "f_133868_", "f_179550_", "f_195812_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ServerboundClientInformationPacket.class, "language;viewDistance;chatVisibility;chatColors;modelCustomisation;mainHand;textFilteringEnabled;allowsListing", "f_133863_", "f_133864_", "f_133865_", "f_133866_", "f_133867_", "f_133868_", "f_179550_", "f_195812_"}, this);
    }

    @Override
    public final boolean equals(Object p_195827_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ServerboundClientInformationPacket.class, "language;viewDistance;chatVisibility;chatColors;modelCustomisation;mainHand;textFilteringEnabled;allowsListing", "f_133863_", "f_133864_", "f_133865_", "f_133866_", "f_133867_", "f_133868_", "f_179550_", "f_195812_"}, this, p_195827_);
    }
}

