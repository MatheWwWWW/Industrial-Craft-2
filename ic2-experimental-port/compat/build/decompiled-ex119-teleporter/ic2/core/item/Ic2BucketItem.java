/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BucketItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.ItemUtils
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.LiquidBlockContainer
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.material.FlowingFluid
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraftforge.event.ForgeEventFactory
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.item;

import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

public abstract class Ic2BucketItem
extends BucketItem {
    protected Fluid fluid;
    private final List<Fluid> drainableFluidList;

    public Ic2BucketItem(Fluid fluid, Item.Properties properties) {
        super(() -> fluid, properties);
        this.fluid = fluid;
        this.drainableFluidList = this.getDrainableFluidList();
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        BlockPos blockPos;
        BlockHitResult blockHitResult;
        ItemStack itemStack = player.m_21120_(interactionHand);
        InteractionResultHolder interactionResultHolder = ForgeEventFactory.onBucketUse((Player)player, (Level)level, (ItemStack)itemStack, (HitResult)(blockHitResult = Ic2BucketItem.m_41435_((Level)level, (Player)player, (ClipContext.Fluid)(this.fluid == Fluids.f_76191_ ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE))));
        if (interactionResultHolder != null) {
            return interactionResultHolder;
        }
        if (blockHitResult.m_6662_() == HitResult.Type.MISS) {
            return InteractionResultHolder.m_19098_((Object)itemStack);
        }
        if (blockHitResult.m_6662_() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.m_19098_((Object)itemStack);
        }
        BlockPos blockPos2 = blockHitResult.m_82425_();
        Direction direction = blockHitResult.m_82434_();
        BlockPos blockPos3 = blockPos2.m_121945_(direction);
        if (!level.m_7966_(player, blockPos2) || !player.m_36204_(blockPos3, direction, itemStack)) {
            return InteractionResultHolder.m_19100_((Object)itemStack);
        }
        if (this.fluid == Fluids.f_76191_) {
            BlockState blockState = level.m_8055_(blockPos2);
            Block block = blockState.m_60734_();
            if (block instanceof BucketPickup) {
                BucketPickup bucketPickup = (BucketPickup)block;
                block = this.tryDrainFluid((LevelAccessor)level, blockPos2, blockState);
                if (!block.m_41619_()) {
                    player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
                    bucketPickup.getPickupSound(blockState).ifPresent(soundEvent -> player.m_5496_(soundEvent, 1.0f, 1.0f));
                    level.m_142346_((Entity)player, GameEvent.f_157816_, blockPos2);
                    ItemStack itemStack2 = ItemUtils.m_41813_((ItemStack)itemStack, (Player)player, (ItemStack)block);
                    if (!level.f_46443_) {
                        CriteriaTriggers.f_10576_.m_38772_((ServerPlayer)player, (ItemStack)block);
                    }
                    return InteractionResultHolder.m_19092_((Object)itemStack2, (boolean)level.m_5776_());
                }
            }
            return InteractionResultHolder.m_19100_((Object)itemStack);
        }
        BlockState blockState = level.m_8055_(blockPos2);
        BlockPos blockPos4 = blockPos = this.canBlockContainFluid(level, blockPos2, blockState) ? blockPos2 : blockPos3;
        if (this.bucketUseOnBlock(new UseOnContext(player, interactionHand, blockHitResult))) {
            return InteractionResultHolder.m_19098_((Object)itemStack);
        }
        if (this.m_142073_(player, level, blockPos, blockHitResult)) {
            this.m_142131_(player, level, itemStack, blockPos);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)player, blockPos, itemStack);
            }
            player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
            ItemStack itemStack3 = ItemUtils.m_41813_((ItemStack)itemStack, (Player)player, (ItemStack)this.getEmptiedBucketStack(itemStack, player));
            return InteractionResultHolder.m_19092_((Object)itemStack3, (boolean)level.m_5776_());
        }
        return InteractionResultHolder.m_19100_((Object)itemStack);
    }

    private ItemStack getEmptiedBucketStack(ItemStack itemStack, Player player) {
        if (!player.m_150110_().f_35937_) {
            return new ItemStack((ItemLike)this.getEmptiedBucketItem());
        }
        return itemStack;
    }

    public ItemStack tryDrainFluid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        Fluid fluid;
        if (blockState.m_60734_() instanceof LiquidBlock && this.drainableFluidList.contains(fluid = blockState.m_60819_().m_76152_())) {
            levelAccessor.m_7731_(blockPos, Blocks.f_50016_.m_49966_(), 11);
            return new ItemStack((ItemLike)this.getBucketItem(fluid));
        }
        if (blockState.m_60734_() instanceof SimpleWaterloggedBlock && ((Boolean)blockState.m_61143_((Property)BlockStateProperties.f_61362_)).booleanValue()) {
            levelAccessor.m_7731_(blockPos, (BlockState)blockState.m_61124_((Property)BlockStateProperties.f_61362_, (Comparable)Boolean.valueOf(false)), 3);
            if (!blockState.m_60710_((LevelReader)levelAccessor, blockPos)) {
                levelAccessor.m_46961_(blockPos, true);
            }
            return new ItemStack((ItemLike)this.getBucketItem((Fluid)Fluids.f_76193_));
        }
        return this.tryDrain(levelAccessor, blockPos, blockState);
    }

    public abstract Item getEmptiedBucketItem();

    public abstract List<Fluid> getDrainableFluidList();

    public abstract Item getBucketItem(Fluid var1);

    public ItemStack tryDrain(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        return ItemStack.f_41583_;
    }

    public boolean bucketUseOnBlock(UseOnContext useOnContext) {
        return false;
    }

    public boolean m_142073_(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult blockHitResult) {
        if (!(this.fluid instanceof FlowingFluid)) {
            return false;
        }
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        Material material = blockState.m_60767_();
        boolean bl = blockState.m_60722_(this.fluid);
        boolean bl2 = blockState.m_60795_() || bl || block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).m_6044_((BlockGetter)level, blockPos, blockState, this.fluid);
        boolean bl3 = bl2;
        if (!bl2) {
            return blockHitResult != null && this.m_142073_(player, level, blockHitResult.m_82425_().m_121945_(blockHitResult.m_82434_()), null);
        }
        if (level.m_6042_().f_63857_() && this.fluid.m_205067_(FluidTags.f_13131_)) {
            int n = blockPos.m_123341_();
            int n2 = blockPos.m_123342_();
            int n3 = blockPos.m_123343_();
            level.m_5594_(player, blockPos, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5f, 2.6f + (level.f_46441_.m_188501_() - level.f_46441_.m_188501_()) * 0.8f);
            for (int i = 0; i < 8; ++i) {
                level.m_7106_((ParticleOptions)ParticleTypes.f_123755_, (double)n + Math.random(), (double)n2 + Math.random(), (double)n3 + Math.random(), 0.0, 0.0, 0.0);
            }
            return true;
        }
        if (block instanceof LiquidBlockContainer && this.fluid == Fluids.f_76193_) {
            ((LiquidBlockContainer)block).m_7361_((LevelAccessor)level, blockPos, blockState, ((FlowingFluid)this.fluid).m_76068_(false));
            this.m_7718_(player, (LevelAccessor)level, blockPos);
            return true;
        }
        if (!level.f_46443_ && bl && !material.m_76332_()) {
            level.m_46961_(blockPos, true);
        }
        if (level.m_7731_(blockPos, this.fluid.m_76145_().m_76188_(), 11) || blockState.m_60819_().m_76170_()) {
            this.m_7718_(player, (LevelAccessor)level, blockPos);
            return true;
        }
        return false;
    }

    private boolean canBlockContainFluid(Level level, BlockPos blockPos, BlockState blockState) {
        return blockState.m_60734_() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockState.m_60734_()).m_6044_((BlockGetter)level, blockPos, blockState, this.fluid);
    }

    protected void m_7718_(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos) {
        SoundEvent soundEvent = this.fluid.m_205067_(FluidTags.f_13132_) ? SoundEvents.f_11780_ : SoundEvents.f_11778_;
        levelAccessor.m_5594_(player, blockPos, soundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);
        levelAccessor.m_142346_((Entity)player, GameEvent.f_157769_, blockPos);
    }
}

