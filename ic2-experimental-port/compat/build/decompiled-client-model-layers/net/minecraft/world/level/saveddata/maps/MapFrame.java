/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.saveddata.maps;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public class MapFrame {
    private final BlockPos f_77862_;
    private final int f_77863_;
    private final int f_77864_;

    public MapFrame(BlockPos p_77866_, int p_77867_, int p_77868_) {
        this.f_77862_ = p_77866_;
        this.f_77863_ = p_77867_;
        this.f_77864_ = p_77868_;
    }

    public static MapFrame m_77872_(CompoundTag p_77873_) {
        BlockPos $$1 = NbtUtils.m_129239_(p_77873_.m_128469_("Pos"));
        int $$2 = p_77873_.m_128451_("Rotation");
        int $$3 = p_77873_.m_128451_("EntityId");
        return new MapFrame($$1, $$2, $$3);
    }

    public CompoundTag m_77869_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128365_("Pos", NbtUtils.m_129224_(this.f_77862_));
        $$0.m_128405_("Rotation", this.f_77863_);
        $$0.m_128405_("EntityId", this.f_77864_);
        return $$0;
    }

    public BlockPos m_77874_() {
        return this.f_77862_;
    }

    public int m_77875_() {
        return this.f_77863_;
    }

    public int m_77876_() {
        return this.f_77864_;
    }

    public String m_77877_() {
        return MapFrame.m_77870_(this.f_77862_);
    }

    public static String m_77870_(BlockPos p_77871_) {
        return "frame-" + p_77871_.m_123341_() + "," + p_77871_.m_123342_() + "," + p_77871_.m_123343_();
    }
}

