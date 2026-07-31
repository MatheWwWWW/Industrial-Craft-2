/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnderChestBlockEntity
extends BlockEntity
implements LidBlockEntity {
    private final ChestLidController f_155510_ = new ChestLidController();
    private final ContainerOpenersCounter f_155511_ = new ContainerOpenersCounter(){

        @Override
        protected void m_142292_(Level p_155531_, BlockPos p_155532_, BlockState p_155533_) {
            p_155531_.m_6263_(null, (double)p_155532_.m_123341_() + 0.5, (double)p_155532_.m_123342_() + 0.5, (double)p_155532_.m_123343_() + 0.5, SoundEvents.f_11889_, SoundSource.BLOCKS, 0.5f, p_155531_.f_46441_.m_188501_() * 0.1f + 0.9f);
        }

        @Override
        protected void m_142289_(Level p_155541_, BlockPos p_155542_, BlockState p_155543_) {
            p_155541_.m_6263_(null, (double)p_155542_.m_123341_() + 0.5, (double)p_155542_.m_123342_() + 0.5, (double)p_155542_.m_123343_() + 0.5, SoundEvents.f_11888_, SoundSource.BLOCKS, 0.5f, p_155541_.f_46441_.m_188501_() * 0.1f + 0.9f);
        }

        @Override
        protected void m_142148_(Level p_155535_, BlockPos p_155536_, BlockState p_155537_, int p_155538_, int p_155539_) {
            p_155535_.m_7696_(EnderChestBlockEntity.this.f_58858_, Blocks.f_50265_, 1, p_155539_);
        }

        @Override
        protected boolean m_142718_(Player p_155529_) {
            return p_155529_.m_36327_().m_150633_(EnderChestBlockEntity.this);
        }
    };

    public EnderChestBlockEntity(BlockPos p_155513_, BlockState p_155514_) {
        super(BlockEntityType.f_58920_, p_155513_, p_155514_);
    }

    public static void m_155517_(Level p_155518_, BlockPos p_155519_, BlockState p_155520_, EnderChestBlockEntity p_155521_) {
        p_155521_.f_155510_.m_155374_();
    }

    @Override
    public boolean m_7531_(int p_59285_, int p_59286_) {
        if (p_59285_ == 1) {
            this.f_155510_.m_155377_(p_59286_ > 0);
            return true;
        }
        return super.m_7531_(p_59285_, p_59286_);
    }

    public void m_155515_(Player p_155516_) {
        if (!this.f_58859_ && !p_155516_.m_5833_()) {
            this.f_155511_.m_155452_(p_155516_, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    public void m_155522_(Player p_155523_) {
        if (!this.f_58859_ && !p_155523_.m_5833_()) {
            this.f_155511_.m_155468_(p_155523_, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    public boolean m_59282_(Player p_59283_) {
        if (this.f_58857_.m_7702_(this.f_58858_) != this) {
            return false;
        }
        return !(p_59283_.m_20275_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5) > 64.0);
    }

    public void m_155524_() {
        if (!this.f_58859_) {
            this.f_155511_.m_155476_(this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    @Override
    public float m_6683_(float p_59281_) {
        return this.f_155510_.m_155375_(p_59281_);
    }
}

