/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.api.blocks.PainterHelper;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.audio.providers.SimplePosition;
import ic2.core.block.base.IC2Block;
import ic2.core.block.base.misc.color.IBlockColorListener;
import ic2.core.block.rendering.block.PixelFoamModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PixelFoamBlock
extends IC2Block
implements IBlockModel {
    private String name;
    private PixelFoamType type;

    public PixelFoamBlock(String name, PixelFoamType type) {
        super("pixelfoam_" + name, BlockBehaviour.Properties.m_60939_((Material)Material.f_76278_).m_60913_(3.0f, 30.0f).m_60918_(SoundType.f_56742_));
        this.setHarvestTool(Tool.PICKAXE);
        this.name = name;
        this.type = type;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(IC2.CFOAM_GROUP));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("cfoam/pixelfoam").get(this.name);
    }

    public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }
        Direction facing = hit.m_82434_();
        ItemStack heldItem = player.m_21120_(InteractionHand.MAIN_HAND);
        switch (this.type) {
            case HALF_LIFE: {
                player.m_20334_((double)facing.m_122429_() * 2.5, (double)facing.m_122430_(), (double)facing.m_122431_() * 2.5);
                return InteractionResult.SUCCESS;
            }
            case ROSE: {
                if (!(StackUtil.isStackEqual(heldItem, new ItemStack((ItemLike)Blocks.f_50112_)) | StackUtil.isStackEqual(heldItem, new ItemStack((ItemLike)Blocks.f_50357_)))) break;
                if (player.m_21223_() == player.m_21233_()) {
                    return InteractionResult.PASS;
                }
                player.m_5634_(player.m_21233_() - player.m_21223_());
                heldItem.m_41774_(1);
                return InteractionResult.SUCCESS;
            }
            case MUFFIN_BUTTON: {
                if (!heldItem.m_204117_(ItemTags.f_13170_)) break;
                return player.m_150109_().m_36054_(new ItemStack((ItemLike)Items.f_42572_)) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }
            case PORTAL: {
                BlockPos target = pos.m_121945_(facing.m_122424_());
                if (world.m_46859_(target.m_7495_())) {
                    player.m_6021_((double)target.m_123341_() + 0.5, (double)(target.m_123342_() - 1), (double)target.m_123343_() + 0.5);
                    return InteractionResult.SUCCESS;
                }
                if (!world.m_46859_(target.m_7494_())) break;
                player.m_6021_((double)target.m_123341_() + 0.5, (double)target.m_123342_(), (double)target.m_123343_() + 0.5);
                return InteractionResult.SUCCESS;
            }
            default: {
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.PASS;
    }

    public static enum PixelFoamType {
        NOOP,
        HALF_LIFE,
        ROSE,
        MUFFIN_BUTTON,
        PORTAL,
        SPACE_BEAN;

    }

    public static class SustainablePixelFoamBlock
    extends PixelFoamBlock
    implements IRenderType,
    ICustomBlockModel,
    IBlockColorListener,
    PainterHelper.IPaintable {
        public static final IntegerProperty TYPE = IntegerProperty.m_61631_((String)"type", (int)0, (int)3);
        public static final EnumProperty<DyeColor> COLOR = EnumProperty.m_61587_((String)"color", DyeColor.class);

        public SustainablePixelFoamBlock(String name) {
            super(name, PixelFoamType.SPACE_BEAN);
        }

        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            builder.m_61104_(new Property[]{TYPE}).m_61104_(new Property[]{COLOR});
        }

        @Override
        public List<BlockState> getModelStates() {
            return Collections.emptyList();
        }

        @Override
        public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
            return world.m_46597_(pos, (BlockState)state.m_61124_(COLOR, (Comparable)color));
        }

        @Override
        public DyeColor getColor(BlockState state) {
            return (DyeColor)state.m_61143_(COLOR);
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public RenderType getType() {
            return RenderType.m_110463_();
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
            return IC2Textures.getMappedEntriesBlockIC2("cfoam/normal").get("gray");
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public int getItemColor(ItemStack stack, int tintIndex) {
            return DyeColor.RED.m_41071_();
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public int getBlockColor(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
            return ((DyeColor)state.m_61143_(COLOR)).m_41071_();
        }

        public BlockState m_5573_(BlockPlaceContext context) {
            RandomSource rand = context.m_43725_().m_213780_();
            return (BlockState)((BlockState)super.m_5573_(context).m_61124_(COLOR, (Comparable)DyeColor.m_41053_((int)rand.m_188503_(16)))).m_61124_((Property)TYPE, (Comparable)Integer.valueOf(rand.m_188503_(64) == 0 ? 2 : 0));
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public BaseModel getForCustomState(BlockState state) {
            return new PixelFoamModel(state, this);
        }

        public boolean m_6724_(BlockState state) {
            return (Integer)state.m_61143_((Property)TYPE) == 2;
        }

        public void m_213898_(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
            if (random.m_188503_(16) != 0) {
                return;
            }
            ObjectList<BlockPos> positions = AABBUtil.getValidBlocks((Level)world, pos, Box.withRange(pos, 0).expand(DirectionList.HORIZONTAL, 5), Filter.INSTANCE, 34, DirectionList.ALL, Integer.MAX_VALUE).getPositions();
            if (positions.isEmpty()) {
                return;
            }
            BlockPos target = (BlockPos)positions.get(random.m_188503_(positions.size()));
            BlockState deadState = world.m_8055_(target);
            world.m_46597_(target, (BlockState)deadState.m_61124_((Property)TYPE, (Comparable)Integer.valueOf(1)));
            IC2.AUDIO.playSound(new SimplePosition((Level)world, pos), SoundEvents.f_11910_.m_11660_());
        }

        private static final class Filter
        implements AABBUtil.IBlockFilter {
            public static final AABBUtil.IBlockFilter INSTANCE = new Filter();

            private Filter() {
            }

            @Override
            public boolean isValid(BlockState state) {
                return state.m_60734_() == IC2Blocks.PIXELBLOCK_SPACE_BEAN && (Integer)state.m_61143_((Property)TYPE) == 0;
            }
        }
    }
}

