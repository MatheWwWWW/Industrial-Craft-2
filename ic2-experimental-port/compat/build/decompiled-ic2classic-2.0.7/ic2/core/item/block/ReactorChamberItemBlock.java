/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 */
package ic2.core.item.block;

import ic2.core.item.base.IC2BlockItem;
import ic2.core.utils.helpers.StackUtil;
import java.util.function.Supplier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class ReactorChamberItemBlock
extends IC2BlockItem {
    Supplier<Block> reactor;

    public ReactorChamberItemBlock(Block block, Supplier<Block> reactor) {
        super(block);
        this.reactor = reactor;
    }

    public InteractionResult m_40576_(BlockPlaceContext context) {
        if (!context.m_7059_()) {
            return InteractionResult.FAIL;
        }
        BlockPlaceContext placeContext = this.m_7732_(context);
        if (placeContext == null) {
            return InteractionResult.FAIL;
        }
        BlockState state = this.m_5965_(placeContext);
        if (state == null) {
            if (!this.placeReactor(placeContext.m_43725_(), placeContext.m_8083_().m_7494_(), placeContext.m_43723_(), placeContext)) {
                return InteractionResult.FAIL;
            }
            state = this.m_5965_(placeContext);
            if (state == null) {
                return InteractionResult.FAIL;
            }
        }
        if (!this.m_7429_(placeContext, state)) {
            return InteractionResult.FAIL;
        }
        BlockPos pos = placeContext.m_8083_();
        Level level = placeContext.m_43725_();
        Player player = placeContext.m_43723_();
        ItemStack stack = placeContext.m_43722_();
        BlockState existing = level.m_8055_(pos);
        if (existing.m_60713_(state.m_60734_())) {
            existing = this.updateBlockStateFromTag(pos, level, stack, existing);
            this.m_7274_(pos, level, player, stack, existing);
            existing.m_60734_().m_6402_(level, pos, existing, (LivingEntity)player, stack);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)player, pos, stack);
            }
        }
        level.m_220407_(GameEvent.f_157797_, pos, GameEvent.Context.m_223719_((Entity)player, (BlockState)existing));
        SoundType soundtype = existing.getSoundType((LevelReader)level, pos, (Entity)context.m_43723_());
        level.m_5594_(player, pos, this.getPlaceSound(existing, level, pos, context.m_43723_()), SoundSource.BLOCKS, (soundtype.m_56773_() + 1.0f) / 2.0f, soundtype.m_56774_() * 0.8f);
        if (player == null || !player.m_150110_().f_35937_) {
            stack.m_41774_(1);
        }
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }

    protected boolean placeReactor(Level level, BlockPos tryPlacePosition, Player player, BlockPlaceContext context) {
        if (player == null || this.reactor == null) {
            return false;
        }
        Block reactor = this.reactor.get();
        if (reactor == null) {
            return false;
        }
        int reactorSlot = this.findReactor(player, reactor);
        if (reactorSlot == -1) {
            return false;
        }
        if (!this.canPlace(player, level, tryPlacePosition, reactor.m_49966_(), this.createContext(context, StackUtil.copyWithSize((ItemStack)player.m_150109_().f_35974_.get(reactorSlot), 1), tryPlacePosition))) {
            return false;
        }
        if (!level.m_7731_(tryPlacePosition, reactor.m_49966_(), 11)) {
            return false;
        }
        if (!player.m_150110_().f_35937_) {
            player.m_150109_().m_7407_(reactorSlot, 1);
        }
        return true;
    }

    protected int findReactor(Player player, Block reactor) {
        Item item = reactor.m_5456_();
        Inventory inv = player.m_150109_();
        for (int i = 0; i < inv.f_35974_.size(); ++i) {
            if (!((ItemStack)inv.f_35974_.get(i)).m_150930_(item)) continue;
            return i;
        }
        return -1;
    }

    private BlockPlaceContext createContext(BlockPlaceContext existing, ItemStack stack, BlockPos newPos) {
        Direction dir = existing.m_43719_();
        BlockHitResult hit = new BlockHitResult(new Vec3((double)newPos.m_123341_() + 0.5 + (double)dir.m_122429_() * 0.5, (double)newPos.m_123342_() + 0.5 + (double)dir.m_122430_() * 0.5, (double)newPos.m_123343_() + 0.5 + (double)dir.m_122431_() * 0.5), dir, newPos, existing.m_43721_());
        return BlockPlaceContext.m_43644_((BlockPlaceContext)new BlockPlaceContext(existing.m_43723_(), existing.m_43724_(), stack, hit), (BlockPos)newPos, (Direction)existing.m_43719_());
    }

    protected boolean canPlace(Player player, Level level, BlockPos pos, BlockState state, BlockPlaceContext context) {
        return (!this.m_6652_() || state.m_60710_((LevelReader)level, pos)) && level.m_8055_(pos).m_60629_(context) && level.m_45752_(state, pos, player == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_((Entity)player));
    }

    private BlockState updateBlockStateFromTag(BlockPos pos, Level level, ItemStack stack, BlockState state) {
        BlockState blockstate = state;
        CompoundTag compoundtag = stack.m_41783_();
        if (compoundtag != null) {
            CompoundTag compoundtag1 = compoundtag.m_128469_("BlockStateTag");
            StateDefinition statedefinition = state.m_60734_().m_49965_();
            for (String s : compoundtag1.m_128431_()) {
                Property property = statedefinition.m_61081_(s);
                if (property == null) continue;
                String s1 = compoundtag1.m_128423_(s).m_7916_();
                blockstate = ReactorChamberItemBlock.updateState(blockstate, property, s1);
            }
        }
        if (blockstate != state) {
            level.m_7731_(pos, blockstate, 2);
        }
        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState state, Property<T> property, String value) {
        return property.m_6215_(value).map(actualValue -> (BlockState)state.m_61124_(property, actualValue)).orElse(state);
    }
}

