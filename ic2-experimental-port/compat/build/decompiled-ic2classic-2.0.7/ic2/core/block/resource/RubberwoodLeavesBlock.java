/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.resource;

import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.IToolProvider;
import ic2.core.block.base.misc.color.IBlockColorListener;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IColoredBlockModel;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.plugins.IRegistryProvider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RubberwoodLeavesBlock
extends LeavesBlock
implements IAutoCreator,
IBlockModel,
IColoredBlockModel,
IBlockColorListener,
IRenderType,
IRegistryProvider,
IToolProvider {
    Tool harvestTool = Tool.HOE;

    public RubberwoodLeavesBlock() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50050_).m_60955_().m_60922_((S, W, P, E) -> E == EntityType.f_20505_ || E == EntityType.f_20508_).m_60960_((K, V, E) -> false).m_60971_((K, V, E) -> false));
    }

    @Override
    public void registerTools() {
        this.harvestTool.register((Block)this);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation("ic2", "rubber_leaves");
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    @Override
    public List<BlockState> getModelStates() {
        return this.m_49965_().m_61056_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(new ResourceLocation("block/birch_leaves"));
    }

    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }

    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getItemColor(ItemStack stack, int tintIndex) {
        return 8431445;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getBlockColor(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
        return 8431445;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return Minecraft.m_91405_() ? RenderType.m_110457_() : RenderType.m_110451_();
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        ItemStack stack = (ItemStack)builder.m_78982_(LootContextParams.f_81463_);
        if (stack.m_41720_() == Items.f_42574_ || EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44985_, (ItemStack)stack) > 0) {
            drops.add(new ItemStack((ItemLike)this));
        }
        if (builder.m_78962_().f_46441_.m_188503_(35) == 0) {
            drops.add(new ItemStack((ItemLike)IC2Blocks.RUBBER_SAPLING));
        }
        return drops;
    }

    @Override
    public int getTintedIndexFor(BlockState state, Direction dir, int layer) {
        return 1;
    }
}

