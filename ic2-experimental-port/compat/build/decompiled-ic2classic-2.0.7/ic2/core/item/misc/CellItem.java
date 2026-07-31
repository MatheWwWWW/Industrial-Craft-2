/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.cauldron.CauldronInteraction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.ItemUtils
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple$Consumable
 */
package ic2.core.item.misc;

import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

public class CellItem
extends IC2Item
implements ISimpleItemModel {
    String texture;
    Fluid owner;

    public CellItem(String itemName, String texture, Fluid fluid) {
        super(itemName, fluid == Fluids.f_76191_ ? null : new PropertiesBuilder().containerItem(IC2Items.CELL_EMPTY));
        this.texture = texture;
        this.owner = fluid;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("cells").get(this.texture);
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new FluidCellHandler(stack);
    }

    public static void addCauldrenInteraction(Item output, SoundEvent event, Predicate<BlockState> cauldrenValidator, Map<Item, CauldronInteraction> map) {
        map.put(IC2Items.CELL_EMPTY, CellItem.createInteraction(output, event, cauldrenValidator));
    }

    public static CauldronInteraction createInteraction(Item output, SoundEvent sound, Predicate<BlockState> cauldrenValidator) {
        return (state, level, pos, player, hand, input) -> CellItem.fillCell(state, level, pos, player, hand, input, output, cauldrenValidator, sound);
    }

    static InteractionResult fillCell(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack input, Item output, Predicate<BlockState> filter, SoundEvent sound) {
        if (!filter.test(state)) {
            return InteractionResult.PASS;
        }
        if (!level.f_46443_) {
            Item item = input.m_41720_();
            player.m_21008_(hand, ItemUtils.m_41813_((ItemStack)input, (Player)player, (ItemStack)new ItemStack((ItemLike)output)));
            player.m_36220_(Stats.f_12944_);
            player.m_36246_(Stats.f_12982_.m_12902_((Object)item));
            level.m_46597_(pos, Blocks.f_50256_.m_49966_());
            level.m_5594_((Player)null, pos, sound, SoundSource.BLOCKS, 1.0f, 2.0f);
            level.m_142346_((Entity)null, GameEvent.f_157816_, pos);
        }
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        Block block;
        ItemStack stack = playerIn.m_21120_(handIn);
        if (this.owner != Fluids.f_76191_) {
            return InteractionResultHolder.m_19098_((Object)stack);
        }
        BlockHitResult block2 = CellItem.m_41435_((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.SOURCE_ONLY);
        if (block2.m_6662_() == HitResult.Type.MISS) {
            return InteractionResultHolder.m_19098_((Object)stack);
        }
        BlockPos pos = block2.m_82425_();
        if (!worldIn.m_7966_(playerIn, pos) || !playerIn.m_36204_(pos.m_121945_(block2.m_82434_()), block2.m_82434_(), stack)) {
            return InteractionResultHolder.m_19098_((Object)stack);
        }
        BlockState state = worldIn.m_8055_(pos);
        FluidState fluid = state.m_60819_();
        ItemStack drop = this.getDrop(fluid.m_76152_());
        if (drop.m_41619_() || !((block = state.m_60734_()) instanceof BucketPickup)) {
            return InteractionResultHolder.m_19098_((Object)stack);
        }
        BucketPickup bucket = (BucketPickup)block;
        if (!playerIn.m_36356_(drop)) {
            return InteractionResultHolder.m_19098_((Object)stack);
        }
        bucket.m_142598_((LevelAccessor)worldIn, pos, state);
        stack.m_41774_(1);
        playerIn.m_5496_(fluid.m_76152_() == Fluids.f_76193_ ? SoundEvents.f_11781_ : SoundEvents.f_11783_, 1.0f, 2.0f);
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    private ItemStack getDrop(Fluid fluid) {
        if (fluid == Fluids.f_76193_) {
            return new ItemStack((ItemLike)IC2Items.CELL_WATER);
        }
        if (fluid == Fluids.f_76195_) {
            return new ItemStack((ItemLike)IC2Items.CELL_LAVA);
        }
        return ItemStack.f_41583_;
    }

    public static class FluidCellHandler
    extends FluidHandlerItemStackSimple.Consumable {
        public FluidCellHandler(ItemStack itemstack) {
            super(itemstack, 1000);
        }

        public FluidStack getFluid() {
            Item item = this.container.m_41720_();
            if (item instanceof CellItem) {
                CellItem result = (CellItem)item;
                return new FluidStack(result.owner, 1000);
            }
            return FluidStack.EMPTY;
        }

        public void setFluid(FluidStack resource) {
            Fluid fluid = resource.getFluid();
            if (fluid == Fluids.f_76191_) {
                this.container = new ItemStack((ItemLike)IC2Items.CELL_EMPTY);
            } else if (fluid == Fluids.f_76193_) {
                this.container = new ItemStack((ItemLike)IC2Items.CELL_WATER);
            } else if (fluid == Fluids.f_76195_) {
                this.container = new ItemStack((ItemLike)IC2Items.CELL_LAVA);
            } else if (fluid == IC2Fluids.WOOD_GAS) {
                this.container = new ItemStack((ItemLike)IC2Items.CELL_WOODGAS);
            } else if (fluid == IC2Fluids.BIO_FUEL) {
                this.container = new ItemStack((ItemLike)IC2Items.CELL_FLUID_BIO_FUEL);
            } else if (fluid == IC2Fluids.ALCOHOL) {
                this.container = new ItemStack((ItemLike)IC2Items.CELL_ALCOHOL);
            } else if (fluid == IC2Fluids.BLAZING_LAVA) {
                this.container = new ItemStack((ItemLike)IC2Items.CELL_BLAZING_LAVA);
            }
        }

        public boolean canFillFluidType(FluidStack fluid) {
            return fluid != null && (fluid.getFluid() == Fluids.f_76195_ || fluid.getFluid() == Fluids.f_76193_ || fluid.getFluid() == IC2Fluids.WOOD_GAS || fluid.getFluid() == IC2Fluids.BIO_FUEL || fluid.getFluid() == IC2Fluids.ALCOHOL || fluid.getFluid() == IC2Fluids.BLAZING_LAVA) && fluid.getAmount() >= this.capacity;
        }

        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            if (maxDrain < this.capacity) {
                return FluidStack.EMPTY;
            }
            return super.drain(maxDrain, action);
        }

        protected void setContainerToEmpty() {
            this.setFluid(FluidStack.EMPTY);
        }
    }
}

