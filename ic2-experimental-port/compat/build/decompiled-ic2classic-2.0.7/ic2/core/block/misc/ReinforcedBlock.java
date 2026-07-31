/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.helpers.Tool;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReinforcedBlock
extends IC2Block
implements IBlockModel,
IRenderType {
    String texture;
    boolean glass;

    public ReinforcedBlock(String blockName, BlockBehaviour.Properties properties, boolean glass) {
        super("reinforced_" + blockName, properties);
        this.setHarvestTool(Tool.PICKAXE.withLevel(2));
        this.glass = glass;
        this.texture = blockName;
    }

    public static ReinforcedBlock createSolid(String name) {
        return new ReinforcedBlock(name, BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60918_(SoundType.f_56743_).m_60913_(80.0f, 150.0f), false);
    }

    public static ReinforcedBlock createGlass(String name) {
        return new ReinforcedBlock(name, BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60922_((B, W, P, E) -> false).m_60971_((B, W, P) -> false).m_60924_((K, V, Y) -> false).m_60955_().m_60918_(SoundType.f_56744_).m_60913_(15.0f, 150.0f), true);
    }

    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return false;
    }

    public VoxelShape m_5909_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.glass ? Shapes.m_83040_() : super.m_5909_(state, world, pos, context);
    }

    @OnlyIn(value=Dist.CLIENT)
    public float m_7749_(BlockState p_220080_1_, BlockGetter p_220080_2_, BlockPos p_220080_3_) {
        return this.glass ? 1.0f : 0.2f;
    }

    public boolean m_7420_(BlockState p_200123_1_, BlockGetter p_200123_2_, BlockPos p_200123_3_) {
        return !this.glass;
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean m_6104_(BlockState p_200122_1_, BlockState p_200122_2_, Direction p_200122_3_) {
        return this.glass && p_200122_2_.m_60713_((Block)this);
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return this.glass ? RenderType.m_110463_() : RenderType.m_110451_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("cfoam/reinforced").get(this.texture);
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile) {
        return this.glass && EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44985_, (ItemStack)tool) <= 0 ? ItemStack.f_41583_ : super.createDrop(state, tool, rand, tile);
    }
}

