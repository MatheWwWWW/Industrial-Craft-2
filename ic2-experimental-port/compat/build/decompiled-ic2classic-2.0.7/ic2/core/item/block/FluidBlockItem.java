/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.common.SoundActions
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.block;

import ic2.core.IC2;
import ic2.core.block.base.misc.color.IItemColorListener;
import ic2.core.platform.rendering.features.item.IColoredItemModel;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.plugins.IRegistryProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidBlockItem
extends BlockItem
implements ISimpleItemModel,
IColoredItemModel,
IItemColorListener,
IRegistryProvider {
    ResourceLocation id;

    public FluidBlockItem(Block blockIn) {
        super(blockIn, new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP));
        IC2.MOD_CACHE.setActiveMod("minecraft");
        this.id = ForgeRegistries.BLOCKS.getKey((Object)blockIn);
        IC2.MOD_CACHE.reset();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(IClientFluidTypeExtensions.of((FluidState)this.m_40614_().m_49966_().m_60819_()).getStillTexture());
    }

    @Override
    public int getTintedIndexForModel(ItemStack stack, int layer) {
        return 0;
    }

    protected SoundEvent m_40587_(BlockState state) {
        SoundEvent event = state.m_60819_().m_76152_().getFluidType().getSound(SoundActions.BUCKET_FILL);
        if (event == null) {
            event = state.m_60819_().m_76152_().m_205067_(FluidTags.f_13132_) ? SoundEvents.f_11780_ : SoundEvents.f_11778_;
        }
        return event;
    }

    protected boolean m_7429_(BlockPlaceContext context, BlockState state) {
        if (context.m_43725_().m_46472_().equals((Object)Level.f_46429_) && this.m_40614_() == Blocks.f_49990_) {
            return false;
        }
        return super.m_7429_(context, state);
    }

    protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
        return this.m_40587_(state);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getItemColor(ItemStack stack, int tintIndex) {
        return IClientFluidTypeExtensions.of((FluidState)this.m_40614_().m_49966_().m_60819_()).getTintColor();
    }
}

