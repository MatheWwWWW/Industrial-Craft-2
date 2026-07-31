/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ShearsItem
extends Item {
    public ShearsItem(Item.Properties p_43074_) {
        super(p_43074_);
    }

    @Override
    public boolean m_6813_(ItemStack p_43078_, Level p_43079_, BlockState p_43080_, BlockPos p_43081_, LivingEntity p_43082_) {
        if (!p_43079_.f_46443_ && !p_43080_.m_204336_(BlockTags.f_13076_)) {
            p_43078_.m_41622_(1, p_43082_, p_43076_ -> p_43076_.m_21166_(EquipmentSlot.MAINHAND));
        }
        if (p_43080_.m_204336_(BlockTags.f_13035_) || p_43080_.m_60713_(Blocks.f_50033_) || p_43080_.m_60713_(Blocks.f_50034_) || p_43080_.m_60713_(Blocks.f_50035_) || p_43080_.m_60713_(Blocks.f_50036_) || p_43080_.m_60713_(Blocks.f_152548_) || p_43080_.m_60713_(Blocks.f_50191_) || p_43080_.m_60713_(Blocks.f_50267_) || p_43080_.m_204336_(BlockTags.f_13089_)) {
            return true;
        }
        return super.m_6813_(p_43078_, p_43079_, p_43080_, p_43081_, p_43082_);
    }

    @Override
    public boolean m_8096_(BlockState p_43087_) {
        return p_43087_.m_60713_(Blocks.f_50033_) || p_43087_.m_60713_(Blocks.f_50088_) || p_43087_.m_60713_(Blocks.f_50267_);
    }

    @Override
    public float m_8102_(ItemStack p_43084_, BlockState p_43085_) {
        if (p_43085_.m_60713_(Blocks.f_50033_) || p_43085_.m_204336_(BlockTags.f_13035_)) {
            return 15.0f;
        }
        if (p_43085_.m_204336_(BlockTags.f_13089_)) {
            return 5.0f;
        }
        if (p_43085_.m_60713_(Blocks.f_50191_) || p_43085_.m_60713_(Blocks.f_152475_)) {
            return 2.0f;
        }
        return super.m_8102_(p_43084_, p_43085_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_186371_) {
        GrowingPlantHeadBlock $$5;
        BlockPos $$2;
        Level $$1 = p_186371_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_186371_.m_8083_());
        Block $$4 = $$3.m_60734_();
        if ($$4 instanceof GrowingPlantHeadBlock && !($$5 = (GrowingPlantHeadBlock)$$4).m_187440_($$3)) {
            Player $$6 = p_186371_.m_43723_();
            ItemStack $$7 = p_186371_.m_43722_();
            if ($$6 instanceof ServerPlayer) {
                CriteriaTriggers.f_10562_.m_220040_((ServerPlayer)$$6, $$2, $$7);
            }
            $$1.m_5594_($$6, $$2, SoundEvents.f_184217_, SoundSource.BLOCKS, 1.0f, 1.0f);
            $$1.m_46597_($$2, $$5.m_187438_($$3));
            if ($$6 != null) {
                $$7.m_41622_(1, $$6, p_186374_ -> p_186374_.m_21190_(p_186371_.m_43724_()));
            }
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        return super.m_6225_(p_186371_);
    }
}

