/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.misc;

import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RubberSheet
extends IC2Block
implements IBlockModel,
IBlockModifiers {
    public static final ResourceLocation AUDIO_FILE = new ResourceLocation("ic2", "sounds/misc/trampoline.ogg");
    public static final BooleanProperty SUPPORTED = BooleanProperty.m_61465_((String)"supported");
    public static final AABB BOX = new AABB(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape SHAPE = Block.m_49796_((double)0.0, (double)14.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0);

    public RubberSheet() {
        super("rubber_sheet", BlockBehaviour.Properties.m_60939_((Material)Material.f_76272_).m_60918_(SoundType.f_56745_).m_60913_(0.8f, 2.0f));
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)SUPPORTED, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("misc").get("rubber_sheet");
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        return BOX;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{SUPPORTED});
    }

    public void m_142072_(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (worldIn.m_8055_(pos.m_7495_()).m_60796_((BlockGetter)worldIn, pos.m_7495_())) {
            super.m_142072_(worldIn, state, pos, entityIn, fallDistance);
            return;
        }
        if (!this.isSupporting(worldIn, pos)) {
            super.m_142072_(worldIn, state, pos, entityIn, fallDistance);
            Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)IC2Blocks.RUBBER_SHEET));
            worldIn.m_7471_(pos, false);
            return;
        }
        entityIn.f_19789_ = 0.0f;
    }

    public void m_5548_(BlockGetter worldIn, Entity entityIn) {
        BlockPos pos = entityIn.m_20183_().m_6625_(2);
        if (entityIn.m_20184_().m_7098_() < -0.4 && !worldIn.m_8055_(pos).m_60796_(worldIn, pos)) {
            IC2.AUDIO.playSound(entityIn, AUDIO_FILE, AudioManager.SoundType.STATIC, 1.0f, 1.0f);
            entityIn.m_20256_(entityIn.m_20184_().m_82542_(1.1, entityIn.m_6144_() ? -0.1 : -0.8, 1.1));
            return;
        }
        super.m_5548_(worldIn, entityIn);
    }

    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (((Boolean)state.m_61143_((Property)SUPPORTED)).booleanValue()) {
            worldIn.m_46597_(pos, this.m_49966_());
            state = this.m_49966_();
        }
        if (!this.m_7898_(state, (LevelReader)worldIn, pos)) {
            Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)IC2Blocks.RUBBER_SHEET));
            worldIn.m_7471_(pos, isMoving);
        }
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        return this.isBlockSupporter(worldIn, pos.m_122029_(), false) || this.isBlockSupporter(worldIn, pos.m_122024_(), false) || this.isBlockSupporter(worldIn, pos.m_122012_(), false) || this.isBlockSupporter(worldIn, pos.m_122019_(), false);
    }

    public boolean isBlockSupporter(LevelReader world, BlockPos pos, boolean rubberOnly) {
        BlockState state = world.m_8055_(pos);
        if (rubberOnly) {
            return state.m_60734_() == this;
        }
        return state.m_60796_((BlockGetter)world, pos) || state.m_60734_() == this;
    }

    public boolean isSupporting(Level world, BlockPos pos) {
        BlockState state;
        if (((Boolean)world.m_8055_(pos).m_61143_((Property)SUPPORTED)).booleanValue()) {
            return true;
        }
        boolean up = false;
        boolean down = false;
        BlockPos west = pos;
        while (true) {
            if ((state = world.m_8055_(west)).m_60796_((BlockGetter)world, west)) {
                down = true;
                break;
            }
            if (state.m_60734_() != this) break;
            west = west.m_122024_();
        }
        BlockPos east = pos;
        while (true) {
            if ((state = world.m_8055_(east)).m_60796_((BlockGetter)world, east)) {
                up = true;
                break;
            }
            if (state.m_60734_() != this) break;
            east = east.m_122029_();
        }
        if (up && down) {
            world.m_46597_(pos, (BlockState)this.m_49966_().m_61124_((Property)SUPPORTED, (Comparable)Boolean.valueOf(true)));
            return true;
        }
        down = false;
        up = false;
        BlockPos north = pos;
        while (true) {
            if ((state = world.m_8055_(north)).m_60796_((BlockGetter)world, north)) {
                down = true;
                break;
            }
            if (state.m_60734_() != this) break;
            north = north.m_122012_();
        }
        BlockPos south = pos;
        while (true) {
            if ((state = world.m_8055_(south)).m_60796_((BlockGetter)world, south)) {
                up = true;
                break;
            }
            if (state.m_60734_() != this) break;
            south = south.m_122019_();
        }
        if (up && down) {
            world.m_46597_(pos, (BlockState)this.m_49966_().m_61124_((Property)SUPPORTED, (Comparable)Boolean.valueOf(true)));
            return true;
        }
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
        return new float[]{0.0f, 14.0f, 16.0f, 16.0f};
    }
}

