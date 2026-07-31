/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.base;

import ic2.api.blocks.DyeableMap;
import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.IC2Block;
import ic2.core.block.misc.base.DyeableBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.helpers.Tool;
import java.util.Objects;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2CarpetBlock
extends IC2Block
implements IBlockModel,
IBlockModifiers,
PainterHelper.IPaintable {
    private static final VoxelShape SHAPE = Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)1.0, (double)16.0);
    protected DyeableMap colorMap;
    private DyeColor color;
    private String textureName;
    private String textureFolder;
    private Block material;

    public IC2CarpetBlock(String name, Block material, String textureFolder, String textureName) {
        super(name, BlockBehaviour.Properties.m_60926_((BlockBehaviour)material));
        this.textureName = textureName;
        this.textureFolder = textureFolder;
        this.material = material;
    }

    public IC2CarpetBlock(DyeableBlock material, String name, String texture, DyeableMap colorMap) {
        this(name + material.getColor().m_41065_(), material, texture, material.getColor().m_41065_());
        this.color = material.getColor();
        this.colorMap = colorMap;
        colorMap.addBlock(this, this.color);
        this.setHarvestTool(Tool.HOE.withLevel(1));
    }

    public static IC2CarpetBlock createCFoamCarpet(DyeableBlock material, DyeableMap colorMap) {
        return new IC2CarpetBlock(material, "cfoam_wool_carpet_", "cfoam/wool", colorMap);
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(Objects.requireNonNull(this.material.m_5456_().m_41471_())));
    }

    @Override
    public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
        if (this.colorMap == null) {
            return false;
        }
        Block block = this.colorMap.getBlock(color);
        return block != null && world.m_46597_(pos, PainterHelper.copyProperties(state, block.m_49966_()));
    }

    @Override
    public DyeColor getColor(BlockState state) {
        return this.color;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public BlockState m_7417_(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        return !state.m_60710_((LevelReader)world, currentPos) ? Blocks.f_50016_.m_49966_() : state;
    }

    public boolean m_7898_(BlockState state, LevelReader world, BlockPos pos) {
        return !world.m_46859_(pos.m_7495_());
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        return new AABB(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.textureName);
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean hasTextureRotation(BlockState state, Direction side) {
        return false;
    }

    @Override
    public int getTextureRotation(BlockState state, Direction side) {
        return 0;
    }

    @Override
    public boolean hasCustomTextureUVs(BlockState state, Direction side) {
        return side.m_122434_().m_122479_();
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        return new float[]{0.0f, 0.0f, 16.0f, 1.0f};
    }
}

