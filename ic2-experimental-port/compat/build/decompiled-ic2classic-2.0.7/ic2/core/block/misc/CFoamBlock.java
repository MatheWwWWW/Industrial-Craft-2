/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.ColorMaps;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.IBlockModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CFoamBlock
extends IC2Block
implements IBlockModel,
IRenderType {
    public static final EnumProperty<FoamType> FOAM_TYPE = EnumProperty.m_61587_((String)"type", FoamType.class);

    public CFoamBlock() {
        super("wet_cfoam", BlockBehaviour.Properties.m_60939_((Material)Material.f_76272_).m_60955_().m_60918_(SoundType.f_56745_).m_60977_().m_60913_(0.1f, 10.0f));
        this.m_49959_((BlockState)this.m_49966_().m_61124_(FOAM_TYPE, (Comparable)((Object)FoamType.NORMAL)));
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FOAM_TYPE});
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        state = worldIn.m_8055_(pos);
        Block id = state.m_60734_();
        return id == Blocks.f_50016_ || id == Blocks.f_50083_ || state.m_60795_() || state.m_60767_().m_76332_();
    }

    public VoxelShape m_5939_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.m_83040_();
    }

    public int m_7753_(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockState dustState;
        ItemStack stack = player.m_21120_(handIn);
        if (stack.m_41619_() || state.m_61143_(FOAM_TYPE) == FoamType.CABLE) {
            return InteractionResult.PASS;
        }
        if (stack.m_204117_(ItemTags.f_13137_)) {
            worldIn.m_46597_(pos, (state.m_61143_(FOAM_TYPE) == FoamType.NORMAL ? IC2Blocks.CFOAM_BLOCK_LIGHT_GRAY : IC2Blocks.REINFORCED_STONE).m_49966_());
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return InteractionResult.SUCCESS;
        }
        if (stack.m_204117_(ItemTags.f_13167_)) {
            DyeColor color = PainterHelper.INSTANCE.getColor(Block.m_49814_((Item)stack.m_41720_()).m_49966_());
            worldIn.m_46597_(pos, ColorMaps.CFOAM_WOOL.getBlock(color == null ? DyeColor.LIGHT_GRAY : color).m_49966_());
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return InteractionResult.SUCCESS;
        }
        if (state.m_61143_(FOAM_TYPE) == FoamType.NORMAL && PainterHelper.CONCRETE_DUST.contains((dustState = Block.m_49814_((Item)stack.m_41720_()).m_49966_()).m_60734_())) {
            DyeColor color = PainterHelper.INSTANCE.getColor(dustState);
            worldIn.m_46597_(pos, ColorMaps.CFOAM_BLOCKS.getBlock(color == null ? DyeColor.LIGHT_GRAY : color).m_49966_());
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        FoamType type = (FoamType)((Object)state.m_61143_(FOAM_TYPE));
        if (type == FoamType.CABLE) {
            return;
        }
        if (worldIn.m_46472_().equals((Object)Level.f_46429_)) {
            if (worldIn.f_46441_.m_188503_(5) == 0) {
                worldIn.m_46597_(pos, (type == FoamType.NORMAL ? IC2Blocks.CFOAM_BLOCK_LIGHT_GRAY : IC2Blocks.REINFORCED_STONE).m_49966_());
            } else {
                worldIn.m_186460_(pos, (Block)this, this.tickRate((Level)worldIn));
            }
            return;
        }
        if (worldIn.m_46803_(pos) * 6 > worldIn.f_46441_.m_188503_(1000)) {
            worldIn.m_46597_(pos, (type == FoamType.NORMAL ? IC2Blocks.CFOAM_BLOCK_LIGHT_GRAY : IC2Blocks.REINFORCED_STONE).m_49966_());
        } else {
            worldIn.m_186460_(pos, (Block)this, this.tickRate((Level)worldIn));
        }
    }

    public int tickRate(Level worldIn) {
        return worldIn.m_46472_().equals((Object)Level.f_46429_) ? 40 : 500;
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile) {
        return ItemStack.f_41583_;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("cfoam").get(state.m_61143_(FOAM_TYPE) == FoamType.CABLE ? "wet_containing" : "wet");
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    public static enum FoamType implements StringRepresentable
    {
        NORMAL("normal"),
        REINFORCED("reinforced"),
        CABLE("cable");

        String name;

        private FoamType(String name) {
            this.name = name;
        }

        public String m_7912_() {
            return this.name;
        }
    }
}

