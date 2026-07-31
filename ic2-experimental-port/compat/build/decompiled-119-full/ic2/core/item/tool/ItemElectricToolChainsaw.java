/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Shearable
 *  net.minecraft.world.entity.monster.piglin.PiglinAi
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.item.tool;

import ic2.api.item.BlockBreakableItem;
import ic2.api.item.IEntityAttackableItem;
import ic2.core.IC2;
import ic2.core.IHitSoundOverride;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.ref.Ic2ToolMaterials;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Collections;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ItemElectricToolChainsaw
extends ItemElectricTool
implements IHitSoundOverride,
BlockBreakableItem,
IEntityAttackableItem {
    public ItemElectricToolChainsaw(Item.Properties properties) {
        super(properties, 100, Ic2ToolMaterials.CHAINSAW, Collections.singletonList(BlockTags.f_144280_));
        this.maxCharge = 30000;
        this.transferLimit = 100;
        this.tier = 1;
    }

    private boolean isShearMode(ItemStack itemStack) {
        return !StackUtil.getOrCreateNbtData(itemStack).m_128471_("disableShear");
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        if (level.f_46443_) {
            return super.m_7203_(level, player, interactionHand);
        }
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(StackUtil.get(player, interactionHand));
            if (compoundTag.m_128471_("disableShear")) {
                compoundTag.m_128379_("disableShear", false);
                IC2.sideProxy.messagePlayer(player, "ic2.tooltip.mode", "ic2.tooltip.mode.normal");
            } else {
                compoundTag.m_128379_("disableShear", true);
                IC2.sideProxy.messagePlayer(player, "ic2.tooltip.mode", "ic2.tooltip.mode.noShear");
            }
        }
        return super.m_7203_(level, player, interactionHand);
    }

    @Override
    public boolean m_8096_(BlockState blockState) {
        return super.m_8096_(blockState) || blockState.m_60713_(Blocks.f_50033_) || Util.canShear(blockState);
    }

    @Override
    public float m_8102_(ItemStack itemStack, BlockState blockState) {
        return this.canUse(itemStack) && (blockState.m_204336_(BlockTags.f_144280_) || blockState.m_60713_(Blocks.f_50033_) || Util.canShear(blockState)) ? this.f_40980_ : 1.0f;
    }

    @Override
    public boolean onAttackEntity(Player player, Entity entity) {
        ItemStack itemStack = player.m_21205_();
        if (this.consumeEnergy(itemStack, this.operationEnergyCost, (LivingEntity)player)) {
            this.playUsingSound((LivingEntity)player);
        }
        return true;
    }

    private void handleVanillaBlockBreakLogic(Player player, Level level, BlockPos blockPos, BlockState blockState) {
        level.m_5898_(player, 2001, blockPos, Block.m_49956_((BlockState)blockState));
        if (blockState.m_204336_(BlockTags.f_13088_)) {
            PiglinAi.m_34873_((Player)player, (boolean)false);
        }
        level.m_220407_(GameEvent.f_157794_, blockPos, GameEvent.Context.m_223719_((Entity)player, (BlockState)blockState));
    }

    @Override
    public InteractionResult onBlockStartBreak(Player player, Level level, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        BlockState blockState = level.m_8055_(blockPos);
        ItemStack itemStack = player.m_21120_(interactionHand);
        if (!this.isShearMode(itemStack) || !Util.canShear(blockState)) {
            return InteractionResult.PASS;
        }
        if (this.consumeEnergy(itemStack, this.operationEnergyCost, (LivingEntity)player)) {
            this.handleVanillaBlockBreakLogic(player, level, blockPos, blockState);
            StackUtil.dropAsEntity(level, blockPos, new ItemStack((ItemLike)blockState.m_60734_().m_5456_()));
            level.m_7731_(blockPos, Blocks.f_50016_.m_49966_(), 11);
            this.playUsingSound((LivingEntity)player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResult m_6880_(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity instanceof Shearable) {
            Shearable shearable = (Shearable)livingEntity;
            if (!StackUtil.getOrCreateNbtData(itemStack).m_128471_("disableShear") && this.consumeEnergy(itemStack, this.operationEnergyCost, (LivingEntity)player) && shearable.m_6220_()) {
                shearable.m_5851_(SoundSource.PLAYERS);
                this.playUsingSound((LivingEntity)player);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public void playUsingSound(LivingEntity livingEntity) {
        if (!livingEntity.m_9236_().f_46443_) {
            return;
        }
        livingEntity.m_5496_(this.getToolUsingSound(), 1.0f, 1.0f);
    }

    public SoundEvent getToolUsingSound() {
        return IC2.random.m_188499_() ? Ic2SoundEvents.ITEM_CHAINSAW_USE1 : Ic2SoundEvents.ITEM_CHAINSAW_USE2;
    }

    @Override
    public SoundEvent getHitSoundForBlock(LocalPlayer localPlayer, Level level, BlockPos blockPos, ItemStack itemStack) {
        return this.getToolUsingSound();
    }

    @Override
    public SoundEvent getBreakSoundForBlock(LocalPlayer localPlayer, Level level, BlockPos blockPos, ItemStack itemStack) {
        return null;
    }

    @Override
    protected SoundEvent getIdleSound(LivingEntity livingEntity, ItemStack itemStack) {
        return Ic2SoundEvents.ITEM_CHAINSAW_IDLE;
    }

    @Override
    protected SoundEvent getStopSound(LivingEntity livingEntity, ItemStack itemStack) {
        return Ic2SoundEvents.ITEM_CHAINSAW_STOP;
    }

    @Override
    public boolean beforeBlockBreak(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {
        return true;
    }

    @Override
    public void afterBlockBreak(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {
    }
}

