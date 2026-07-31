/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;

public class ServerboundSetStructureBlockPacket
implements Packet<ServerGamePacketListener> {
    private static final int f_179767_ = 1;
    private static final int f_179768_ = 2;
    private static final int f_179769_ = 4;
    private final BlockPos f_134593_;
    private final StructureBlockEntity.UpdateType f_134594_;
    private final StructureMode f_134595_;
    private final String f_134596_;
    private final BlockPos f_134597_;
    private final Vec3i f_134598_;
    private final Mirror f_134599_;
    private final Rotation f_134600_;
    private final String f_134601_;
    private final boolean f_134602_;
    private final boolean f_134603_;
    private final boolean f_134604_;
    private final float f_134605_;
    private final long f_134606_;

    public ServerboundSetStructureBlockPacket(BlockPos p_179771_, StructureBlockEntity.UpdateType p_179772_, StructureMode p_179773_, String p_179774_, BlockPos p_179775_, Vec3i p_179776_, Mirror p_179777_, Rotation p_179778_, String p_179779_, boolean p_179780_, boolean p_179781_, boolean p_179782_, float p_179783_, long p_179784_) {
        this.f_134593_ = p_179771_;
        this.f_134594_ = p_179772_;
        this.f_134595_ = p_179773_;
        this.f_134596_ = p_179774_;
        this.f_134597_ = p_179775_;
        this.f_134598_ = p_179776_;
        this.f_134599_ = p_179777_;
        this.f_134600_ = p_179778_;
        this.f_134601_ = p_179779_;
        this.f_134602_ = p_179780_;
        this.f_134603_ = p_179781_;
        this.f_134604_ = p_179782_;
        this.f_134605_ = p_179783_;
        this.f_134606_ = p_179784_;
    }

    public ServerboundSetStructureBlockPacket(FriendlyByteBuf p_179786_) {
        this.f_134593_ = p_179786_.m_130135_();
        this.f_134594_ = p_179786_.m_130066_(StructureBlockEntity.UpdateType.class);
        this.f_134595_ = p_179786_.m_130066_(StructureMode.class);
        this.f_134596_ = p_179786_.m_130277_();
        int $$1 = 48;
        this.f_134597_ = new BlockPos(Mth.m_14045_(p_179786_.readByte(), -48, 48), Mth.m_14045_(p_179786_.readByte(), -48, 48), Mth.m_14045_(p_179786_.readByte(), -48, 48));
        int $$2 = 48;
        this.f_134598_ = new Vec3i(Mth.m_14045_(p_179786_.readByte(), 0, 48), Mth.m_14045_(p_179786_.readByte(), 0, 48), Mth.m_14045_(p_179786_.readByte(), 0, 48));
        this.f_134599_ = p_179786_.m_130066_(Mirror.class);
        this.f_134600_ = p_179786_.m_130066_(Rotation.class);
        this.f_134601_ = p_179786_.m_130136_(128);
        this.f_134605_ = Mth.m_14036_(p_179786_.readFloat(), 0.0f, 1.0f);
        this.f_134606_ = p_179786_.m_130258_();
        byte $$3 = p_179786_.readByte();
        this.f_134602_ = ($$3 & 1) != 0;
        this.f_134603_ = ($$3 & 2) != 0;
        this.f_134604_ = ($$3 & 4) != 0;
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134631_) {
        p_134631_.m_130064_(this.f_134593_);
        p_134631_.m_130068_(this.f_134594_);
        p_134631_.m_130068_(this.f_134595_);
        p_134631_.m_130070_(this.f_134596_);
        p_134631_.writeByte(this.f_134597_.m_123341_());
        p_134631_.writeByte(this.f_134597_.m_123342_());
        p_134631_.writeByte(this.f_134597_.m_123343_());
        p_134631_.writeByte(this.f_134598_.m_123341_());
        p_134631_.writeByte(this.f_134598_.m_123342_());
        p_134631_.writeByte(this.f_134598_.m_123343_());
        p_134631_.m_130068_(this.f_134599_);
        p_134631_.m_130068_(this.f_134600_);
        p_134631_.m_130070_(this.f_134601_);
        p_134631_.writeFloat(this.f_134605_);
        p_134631_.m_130103_(this.f_134606_);
        int $$1 = 0;
        if (this.f_134602_) {
            $$1 |= 1;
        }
        if (this.f_134603_) {
            $$1 |= 2;
        }
        if (this.f_134604_) {
            $$1 |= 4;
        }
        p_134631_.writeByte($$1);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134628_) {
        p_134628_.m_7424_(this);
    }

    public BlockPos m_134629_() {
        return this.f_134593_;
    }

    public StructureBlockEntity.UpdateType m_134632_() {
        return this.f_134594_;
    }

    public StructureMode m_134633_() {
        return this.f_134595_;
    }

    public String m_134634_() {
        return this.f_134596_;
    }

    public BlockPos m_134635_() {
        return this.f_134597_;
    }

    public Vec3i m_179787_() {
        return this.f_134598_;
    }

    public Mirror m_134637_() {
        return this.f_134599_;
    }

    public Rotation m_134638_() {
        return this.f_134600_;
    }

    public String m_134639_() {
        return this.f_134601_;
    }

    public boolean m_134640_() {
        return this.f_134602_;
    }

    public boolean m_134641_() {
        return this.f_134603_;
    }

    public boolean m_134642_() {
        return this.f_134604_;
    }

    public float m_134643_() {
        return this.f_134605_;
    }

    public long m_134644_() {
        return this.f_134606_;
    }
}

