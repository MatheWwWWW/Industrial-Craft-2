/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class JukeboxBlock
extends BaseEntityBlock {
    public static final BooleanProperty f_54254_ = BlockStateProperties.f_61439_;

    protected JukeboxBlock(BlockBehaviour.Properties p_54257_) {
        super(p_54257_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54254_, false));
    }

    @Override
    public void m_6402_(Level p_54264_, BlockPos p_54265_, BlockState p_54266_, @Nullable LivingEntity p_54267_, ItemStack p_54268_) {
        super.m_6402_(p_54264_, p_54265_, p_54266_, p_54267_, p_54268_);
        CompoundTag $$5 = BlockItem.m_186336_(p_54268_);
        if ($$5 != null && $$5.m_128441_("RecordItem")) {
            p_54264_.m_7731_(p_54265_, (BlockState)p_54266_.m_61124_(f_54254_, true), 2);
        }
    }

    @Override
    public InteractionResult m_6227_(BlockState p_54281_, Level p_54282_, BlockPos p_54283_, Player p_54284_, InteractionHand p_54285_, BlockHitResult p_54286_) {
        if (p_54281_.m_61143_(f_54254_).booleanValue()) {
            this.m_54260_(p_54282_, p_54283_);
            p_54281_ = (BlockState)p_54281_.m_61124_(f_54254_, false);
            p_54282_.m_220407_(GameEvent.f_238649_, p_54283_, GameEvent.Context.m_223722_(p_54281_));
            p_54282_.m_7731_(p_54283_, p_54281_, 2);
            p_54282_.m_220407_(GameEvent.f_157792_, p_54283_, GameEvent.Context.m_223719_(p_54284_, p_54281_));
            return InteractionResult.m_19078_(p_54282_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public void m_238345_(@Nullable Entity p_238346_, LevelAccessor p_238347_, BlockPos p_238348_, BlockState p_238349_, ItemStack p_238350_) {
        BlockEntity $$5 = p_238347_.m_7702_(p_238348_);
        if ($$5 instanceof JukeboxBlockEntity) {
            JukeboxBlockEntity $$6 = (JukeboxBlockEntity)$$5;
            $$6.m_59517_(p_238350_.m_41777_());
            $$6.m_239936_();
            p_238347_.m_7731_(p_238348_, (BlockState)p_238349_.m_61124_(f_54254_, true), 2);
            p_238347_.m_220407_(GameEvent.f_157792_, p_238348_, GameEvent.Context.m_223719_(p_238346_, p_238349_));
        }
    }

    private void m_54260_(Level p_54261_, BlockPos p_54262_) {
        if (p_54261_.f_46443_) {
            return;
        }
        BlockEntity $$2 = p_54261_.m_7702_(p_54262_);
        if (!($$2 instanceof JukeboxBlockEntity)) {
            return;
        }
        JukeboxBlockEntity $$3 = (JukeboxBlockEntity)$$2;
        ItemStack $$4 = $$3.m_59524_();
        if ($$4.m_41619_()) {
            return;
        }
        p_54261_.m_46796_(1010, p_54262_, 0);
        $$3.m_6211_();
        float $$5 = 0.7f;
        double $$6 = (double)(p_54261_.f_46441_.m_188501_() * 0.7f) + (double)0.15f;
        double $$7 = (double)(p_54261_.f_46441_.m_188501_() * 0.7f) + 0.06000000238418579 + 0.6;
        double $$8 = (double)(p_54261_.f_46441_.m_188501_() * 0.7f) + (double)0.15f;
        ItemStack $$9 = $$4.m_41777_();
        ItemEntity $$10 = new ItemEntity(p_54261_, (double)p_54262_.m_123341_() + $$6, (double)p_54262_.m_123342_() + $$7, (double)p_54262_.m_123343_() + $$8, $$9);
        $$10.m_32060_();
        p_54261_.m_7967_($$10);
    }

    @Override
    public void m_6810_(BlockState p_54288_, Level p_54289_, BlockPos p_54290_, BlockState p_54291_, boolean p_54292_) {
        if (p_54288_.m_60713_(p_54291_.m_60734_())) {
            return;
        }
        this.m_54260_(p_54289_, p_54290_);
        super.m_6810_(p_54288_, p_54289_, p_54290_, p_54291_, p_54292_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153451_, BlockState p_153452_) {
        return new JukeboxBlockEntity(p_153451_, p_153452_);
    }

    @Override
    public boolean m_7278_(BlockState p_54275_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_54277_, Level p_54278_, BlockPos p_54279_) {
        Item $$4;
        BlockEntity $$3 = p_54278_.m_7702_(p_54279_);
        if ($$3 instanceof JukeboxBlockEntity && ($$4 = ((JukeboxBlockEntity)$$3).m_59524_().m_41720_()) instanceof RecordItem) {
            return ((RecordItem)$$4).m_43049_();
        }
        return 0;
    }

    @Override
    public RenderShape m_7514_(BlockState p_54296_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54294_) {
        p_54294_.m_61104_(f_54254_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_239682_, BlockState p_239683_, BlockEntityType<T> p_239684_) {
        if (p_239683_.m_61143_(f_54254_).booleanValue()) {
            return JukeboxBlock.m_152132_(p_239684_, BlockEntityType.f_58921_, JukeboxBlockEntity::m_239937_);
        }
        return null;
    }
}

