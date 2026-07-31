/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class SolidBucketItem
extends BlockItem
implements DispensibleContainerItem {
    private final SoundEvent f_151185_;

    public SolidBucketItem(Block p_151187_, SoundEvent p_151188_, Item.Properties p_151189_) {
        super(p_151187_, p_151189_);
        this.f_151185_ = p_151188_;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_151197_) {
        InteractionResult $$1 = super.m_6225_(p_151197_);
        Player $$2 = p_151197_.m_43723_();
        if ($$1.m_19077_() && $$2 != null && !$$2.m_7500_()) {
            InteractionHand $$3 = p_151197_.m_43724_();
            $$2.m_21008_($$3, Items.f_42446_.m_7968_());
        }
        return $$1;
    }

    @Override
    public String m_5524_() {
        return this.m_41467_();
    }

    @Override
    protected SoundEvent m_40587_(BlockState p_151199_) {
        return this.f_151185_;
    }

    @Override
    public boolean m_142073_(@Nullable Player p_151192_, Level p_151193_, BlockPos p_151194_, @Nullable BlockHitResult p_151195_) {
        if (p_151193_.m_46739_(p_151194_) && p_151193_.m_46859_(p_151194_)) {
            if (!p_151193_.f_46443_) {
                p_151193_.m_7731_(p_151194_, this.m_40614_().m_49966_(), 3);
            }
            p_151193_.m_142346_(p_151192_, GameEvent.f_157769_, p_151194_);
            p_151193_.m_5594_(p_151192_, p_151194_, this.f_151185_, SoundSource.BLOCKS, 1.0f, 1.0f);
            return true;
        }
        return false;
    }
}

