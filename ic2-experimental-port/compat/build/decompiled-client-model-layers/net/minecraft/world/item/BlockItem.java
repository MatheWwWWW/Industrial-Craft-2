/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;

public class BlockItem
extends Item {
    private static final String f_150696_ = "BlockEntityTag";
    public static final String f_150697_ = "BlockStateTag";
    @Deprecated
    private final Block f_40563_;

    public BlockItem(Block p_40565_, Item.Properties p_40566_) {
        super(p_40566_);
        this.f_40563_ = p_40565_;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_40581_) {
        InteractionResult $$1 = this.m_40576_(new BlockPlaceContext(p_40581_));
        if (!$$1.m_19077_() && this.m_41472_()) {
            InteractionResult $$2 = this.m_7203_(p_40581_.m_43725_(), p_40581_.m_43723_(), p_40581_.m_43724_()).m_19089_();
            return $$2 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : $$2;
        }
        return $$1;
    }

    public InteractionResult m_40576_(BlockPlaceContext p_40577_) {
        if (!p_40577_.m_7059_()) {
            return InteractionResult.FAIL;
        }
        BlockPlaceContext $$1 = this.m_7732_(p_40577_);
        if ($$1 == null) {
            return InteractionResult.FAIL;
        }
        BlockState $$2 = this.m_5965_($$1);
        if ($$2 == null) {
            return InteractionResult.FAIL;
        }
        if (!this.m_7429_($$1, $$2)) {
            return InteractionResult.FAIL;
        }
        BlockPos $$3 = $$1.m_8083_();
        Level $$4 = $$1.m_43725_();
        Player $$5 = $$1.m_43723_();
        ItemStack $$6 = $$1.m_43722_();
        BlockState $$7 = $$4.m_8055_($$3);
        if ($$7.m_60713_($$2.m_60734_())) {
            $$7 = this.m_40602_($$3, $$4, $$6, $$7);
            this.m_7274_($$3, $$4, $$5, $$6, $$7);
            $$7.m_60734_().m_6402_($$4, $$3, $$7, $$5, $$6);
            if ($$5 instanceof ServerPlayer) {
                CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)$$5, $$3, $$6);
            }
        }
        SoundType $$8 = $$7.m_60827_();
        $$4.m_5594_($$5, $$3, this.m_40587_($$7), SoundSource.BLOCKS, ($$8.m_56773_() + 1.0f) / 2.0f, $$8.m_56774_() * 0.8f);
        $$4.m_220407_(GameEvent.f_157797_, $$3, GameEvent.Context.m_223719_($$5, $$7));
        if ($$5 == null || !$$5.m_150110_().f_35937_) {
            $$6.m_41774_(1);
        }
        return InteractionResult.m_19078_($$4.f_46443_);
    }

    protected SoundEvent m_40587_(BlockState p_40588_) {
        return p_40588_.m_60827_().m_56777_();
    }

    @Nullable
    public BlockPlaceContext m_7732_(BlockPlaceContext p_40609_) {
        return p_40609_;
    }

    protected boolean m_7274_(BlockPos p_40597_, Level p_40598_, @Nullable Player p_40599_, ItemStack p_40600_, BlockState p_40601_) {
        return BlockItem.m_40582_(p_40598_, p_40599_, p_40597_, p_40600_);
    }

    @Nullable
    protected BlockState m_5965_(BlockPlaceContext p_40613_) {
        BlockState $$1 = this.m_40614_().m_5573_(p_40613_);
        return $$1 != null && this.m_40610_(p_40613_, $$1) ? $$1 : null;
    }

    private BlockState m_40602_(BlockPos p_40603_, Level p_40604_, ItemStack p_40605_, BlockState p_40606_) {
        BlockState $$4 = p_40606_;
        CompoundTag $$5 = p_40605_.m_41783_();
        if ($$5 != null) {
            CompoundTag $$6 = $$5.m_128469_(f_150697_);
            StateDefinition<Block, BlockState> $$7 = $$4.m_60734_().m_49965_();
            for (String $$8 : $$6.m_128431_()) {
                Property<?> $$9 = $$7.m_61081_($$8);
                if ($$9 == null) continue;
                String $$10 = $$6.m_128423_($$8).m_7916_();
                $$4 = BlockItem.m_40593_($$4, $$9, $$10);
            }
        }
        if ($$4 != p_40606_) {
            p_40604_.m_7731_(p_40603_, $$4, 2);
        }
        return $$4;
    }

    private static <T extends Comparable<T>> BlockState m_40593_(BlockState p_40594_, Property<T> p_40595_, String p_40596_) {
        return p_40595_.m_6215_(p_40596_).map(p_40592_ -> (BlockState)p_40594_.m_61124_(p_40595_, p_40592_)).orElse(p_40594_);
    }

    protected boolean m_40610_(BlockPlaceContext p_40611_, BlockState p_40612_) {
        Player $$2 = p_40611_.m_43723_();
        CollisionContext $$3 = $$2 == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_($$2);
        return (!this.m_6652_() || p_40612_.m_60710_(p_40611_.m_43725_(), p_40611_.m_8083_())) && p_40611_.m_43725_().m_45752_(p_40612_, p_40611_.m_8083_(), $$3);
    }

    protected boolean m_6652_() {
        return true;
    }

    protected boolean m_7429_(BlockPlaceContext p_40578_, BlockState p_40579_) {
        return p_40578_.m_43725_().m_7731_(p_40578_.m_8083_(), p_40579_, 11);
    }

    public static boolean m_40582_(Level p_40583_, @Nullable Player p_40584_, BlockPos p_40585_, ItemStack p_40586_) {
        BlockEntity $$6;
        MinecraftServer $$4 = p_40583_.m_7654_();
        if ($$4 == null) {
            return false;
        }
        CompoundTag $$5 = BlockItem.m_186336_(p_40586_);
        if ($$5 != null && ($$6 = p_40583_.m_7702_(p_40585_)) != null) {
            if (!(p_40583_.f_46443_ || !$$6.m_6326_() || p_40584_ != null && p_40584_.m_36337_())) {
                return false;
            }
            CompoundTag $$7 = $$6.m_187482_();
            CompoundTag $$8 = $$7.m_6426_();
            $$7.m_128391_($$5);
            if (!$$7.equals($$8)) {
                $$6.m_142466_($$7);
                $$6.m_6596_();
                return true;
            }
        }
        return false;
    }

    @Override
    public String m_5524_() {
        return this.m_40614_().m_7705_();
    }

    @Override
    public void m_6787_(CreativeModeTab p_40569_, NonNullList<ItemStack> p_40570_) {
        if (this.m_220152_(p_40569_)) {
            this.m_40614_().m_49811_(p_40569_, p_40570_);
        }
    }

    @Override
    public void m_7373_(ItemStack p_40572_, @Nullable Level p_40573_, List<Component> p_40574_, TooltipFlag p_40575_) {
        super.m_7373_(p_40572_, p_40573_, p_40574_, p_40575_);
        this.m_40614_().m_5871_(p_40572_, p_40573_, p_40574_, p_40575_);
    }

    public Block m_40614_() {
        return this.f_40563_;
    }

    public void m_6192_(Map<Block, Item> p_40607_, Item p_40608_) {
        p_40607_.put(this.m_40614_(), p_40608_);
    }

    @Override
    public boolean m_142095_() {
        return !(this.f_40563_ instanceof ShulkerBoxBlock);
    }

    @Override
    public void m_142023_(ItemEntity p_150700_) {
        ItemStack $$1;
        CompoundTag $$2;
        if (this.f_40563_ instanceof ShulkerBoxBlock && ($$2 = BlockItem.m_186336_($$1 = p_150700_.m_32055_())) != null && $$2.m_128425_("Items", 9)) {
            ListTag $$3 = $$2.m_128437_("Items", 10);
            ItemUtils.m_150952_(p_150700_, $$3.stream().map(CompoundTag.class::cast).map(ItemStack::m_41712_));
        }
    }

    @Nullable
    public static CompoundTag m_186336_(ItemStack p_186337_) {
        return p_186337_.m_41737_(f_150696_);
    }

    public static void m_186338_(ItemStack p_186339_, BlockEntityType<?> p_186340_, CompoundTag p_186341_) {
        if (p_186341_.m_128456_()) {
            p_186339_.m_41749_(f_150696_);
        } else {
            BlockEntity.m_187468_(p_186341_, p_186340_);
            p_186339_.m_41700_(f_150696_, p_186341_);
        }
    }
}

