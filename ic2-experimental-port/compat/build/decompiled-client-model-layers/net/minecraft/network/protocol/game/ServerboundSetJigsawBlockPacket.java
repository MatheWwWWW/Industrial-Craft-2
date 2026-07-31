/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

public class ServerboundSetJigsawBlockPacket
implements Packet<ServerGamePacketListener> {
    private final BlockPos f_134565_;
    private final ResourceLocation f_134566_;
    private final ResourceLocation f_134567_;
    private final ResourceLocation f_134568_;
    private final String f_134569_;
    private final JigsawBlockEntity.JointType f_134570_;

    public ServerboundSetJigsawBlockPacket(BlockPos p_134573_, ResourceLocation p_134574_, ResourceLocation p_134575_, ResourceLocation p_134576_, String p_134577_, JigsawBlockEntity.JointType p_134578_) {
        this.f_134565_ = p_134573_;
        this.f_134566_ = p_134574_;
        this.f_134567_ = p_134575_;
        this.f_134568_ = p_134576_;
        this.f_134569_ = p_134577_;
        this.f_134570_ = p_134578_;
    }

    public ServerboundSetJigsawBlockPacket(FriendlyByteBuf p_179766_) {
        this.f_134565_ = p_179766_.m_130135_();
        this.f_134566_ = p_179766_.m_130281_();
        this.f_134567_ = p_179766_.m_130281_();
        this.f_134568_ = p_179766_.m_130281_();
        this.f_134569_ = p_179766_.m_130277_();
        this.f_134570_ = JigsawBlockEntity.JointType.m_59457_(p_179766_.m_130277_()).orElse(JigsawBlockEntity.JointType.ALIGNED);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134587_) {
        p_134587_.m_130064_(this.f_134565_);
        p_134587_.m_130085_(this.f_134566_);
        p_134587_.m_130085_(this.f_134567_);
        p_134587_.m_130085_(this.f_134568_);
        p_134587_.m_130070_(this.f_134569_);
        p_134587_.m_130070_(this.f_134570_.m_7912_());
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134584_) {
        p_134584_.m_8019_(this);
    }

    public BlockPos m_134585_() {
        return this.f_134565_;
    }

    public ResourceLocation m_134588_() {
        return this.f_134566_;
    }

    public ResourceLocation m_134589_() {
        return this.f_134567_;
    }

    public ResourceLocation m_134590_() {
        return this.f_134568_;
    }

    public String m_134591_() {
        return this.f_134569_;
    }

    public JigsawBlockEntity.JointType m_134592_() {
        return this.f_134570_;
    }
}

