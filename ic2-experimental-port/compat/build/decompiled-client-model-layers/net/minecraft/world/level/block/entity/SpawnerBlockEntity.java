/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnerBlockEntity
extends BlockEntity {
    private final BaseSpawner f_59788_ = new BaseSpawner(){

        @Override
        public void m_142523_(Level p_155767_, BlockPos p_155768_, int p_155769_) {
            p_155767_.m_7696_(p_155768_, Blocks.f_50085_, p_155769_, 0);
        }

        @Override
        public void m_142667_(@Nullable Level p_155771_, BlockPos p_155772_, SpawnData p_155773_) {
            super.m_142667_(p_155771_, p_155772_, p_155773_);
            if (p_155771_ != null) {
                BlockState $$3 = p_155771_.m_8055_(p_155772_);
                p_155771_.m_7260_(p_155772_, $$3, $$3, 4);
            }
        }
    };

    public SpawnerBlockEntity(BlockPos p_155752_, BlockState p_155753_) {
        super(BlockEntityType.f_58925_, p_155752_, p_155753_);
    }

    @Override
    public void m_142466_(CompoundTag p_155760_) {
        super.m_142466_(p_155760_);
        this.f_59788_.m_151328_(this.f_58857_, this.f_58858_, p_155760_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187521_) {
        super.m_183515_(p_187521_);
        this.f_59788_.m_186381_(p_187521_);
    }

    public static void m_155754_(Level p_155755_, BlockPos p_155756_, BlockState p_155757_, SpawnerBlockEntity p_155758_) {
        p_155758_.f_59788_.m_151319_(p_155755_, p_155756_);
    }

    public static void m_155761_(Level p_155762_, BlockPos p_155763_, BlockState p_155764_, SpawnerBlockEntity p_155765_) {
        p_155765_.f_59788_.m_151311_((ServerLevel)p_155762_, p_155763_);
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        CompoundTag $$0 = this.m_187482_();
        $$0.m_128473_("SpawnPotentials");
        return $$0;
    }

    @Override
    public boolean m_7531_(int p_59797_, int p_59798_) {
        if (this.f_59788_.m_151316_(this.f_58857_, p_59797_)) {
            return true;
        }
        return super.m_7531_(p_59797_, p_59798_);
    }

    @Override
    public boolean m_6326_() {
        return true;
    }

    public BaseSpawner m_59801_() {
        return this.f_59788_;
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }
}

