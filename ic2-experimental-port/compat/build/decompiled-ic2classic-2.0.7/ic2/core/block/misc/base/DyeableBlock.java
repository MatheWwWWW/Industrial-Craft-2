/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
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
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.base;

import ic2.api.blocks.DyeableMap;
import ic2.api.blocks.PainterHelper;
import ic2.core.IC2;
import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.helpers.Tool;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DyeableBlock
extends IC2Block
implements IBlockModel,
PainterHelper.IPaintable {
    public static final BooleanProperty LIGHT = IC2Properties.LIGHT;
    public static final BooleanProperty REDSTONE = IC2Properties.REDSTONE;
    private DyeColor color;
    private DyeableMap colorMap;
    private String textureFolder;
    private CreativeModeTab group = IC2.CFOAM_GROUP;

    public DyeableBlock(String blockName, String textureFolder, DyeColor color, BlockBehaviour.Properties properties) {
        super(blockName + color.m_41065_(), properties.m_60953_(T -> T.m_61138_((Property)LIGHT) && (Boolean)T.m_61143_((Property)LIGHT) != false ? 15 : 0));
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)LIGHT, (Comparable)Boolean.valueOf(false))).m_61124_((Property)REDSTONE, (Comparable)Boolean.valueOf(false)));
        this.setHarvestTool(Tool.PICKAXE);
        this.textureFolder = textureFolder;
        this.color = color;
    }

    public static DyeableBlock createCFoamBlock(DyeColor color, DyeableMap colorMap) {
        return new DyeableBlock("cfoam_block_", "cfoam/normal", color, BlockBehaviour.Properties.m_60939_((Material)Material.f_76278_).m_60913_(3.0f, 30.0f).m_60918_(SoundType.f_56742_)).setColorGroup(colorMap);
    }

    public static DyeableBlock createCFoamWoolBlock(DyeColor color, DyeableMap colorMap) {
        return new DyeableBlock("cfoam_wool_", "cfoam/wool", color, BlockBehaviour.Properties.m_60939_((Material)Material.f_76278_).m_60913_(3.0f, 30.0f).m_60918_(SoundType.f_56745_)).setColorGroup(colorMap);
    }

    public DyeableBlock setColorGroup(DyeableMap colorMap) {
        this.colorMap = colorMap;
        colorMap.addBlock(this, this.color);
        return this;
    }

    public DyeableBlock setGroup(CreativeModeTab group) {
        this.group = group;
        return this;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{LIGHT, REDSTONE});
    }

    public DyeColor getColor() {
        return this.color;
    }

    public DyeableMap getColorMap() {
        return this.colorMap;
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(handIn);
        if (IC2.KEYBOARD.isAltKeyDown(player)) {
            if (stack.m_41720_() == Items.f_42451_) {
                worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)REDSTONE, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)REDSTONE) == false)));
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                if (IC2.PLATFORM.isSimulating()) {
                    player.m_5661_((Component)this.translate((Boolean)state.m_61143_((Property)REDSTONE) == false ? "tooltip.block.ic2.cfoam.redstone.enable" : "tooltip.block.ic2.cfoam.redstone.disable"), true);
                }
                return InteractionResult.SUCCESS;
            }
            if (stack.m_41720_() == Items.f_42525_) {
                worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)LIGHT, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)LIGHT) == false)));
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                if (IC2.PLATFORM.isSimulating()) {
                    player.m_5661_((Component)this.translate((Boolean)state.m_61143_((Property)LIGHT) == false ? "tooltip.block.ic2.cfoam.light.enable" : "tooltip.block.ic2.cfoam.light.disable"), true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
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

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(this.group));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.color.m_41065_());
    }

    public int m_6376_(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE) != false ? 15 : 0;
    }

    public int m_6378_(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE) != false ? 15 : 0;
    }

    public boolean shouldCheckWeakPower(BlockState state, LevelReader world, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE);
    }

    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return (Boolean)state.m_61143_((Property)REDSTONE);
    }
}

