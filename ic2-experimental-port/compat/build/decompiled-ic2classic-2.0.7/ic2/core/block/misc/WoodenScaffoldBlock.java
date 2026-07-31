/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.core.block.misc.base.ScaffoldBlock;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IDoubleSidedModel;
import ic2.core.utils.helpers.Tool;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WoodenScaffoldBlock
extends ScaffoldBlock
implements IBlockModel,
IDoubleSidedModel {
    public static IntegerProperty STRENGTH = IntegerProperty.m_61631_((String)"strength", (int)0, (int)5);

    public WoodenScaffoldBlock() {
        super("scaffold_wooden", 2, 5, BlockBehaviour.Properties.m_60939_((Material)Material.f_76320_).m_60913_(0.5f, 0.2f));
        this.setHarvestTool(Tool.AXE.withLevel(0));
    }

    @Override
    public IntegerProperty createProps() {
        return STRENGTH;
    }

    @Override
    public boolean m_6864_(BlockState state, BlockPlaceContext useContext) {
        return useContext.m_43722_().m_41720_() == IC2Blocks.BARREL.m_5456_() || super.m_6864_(state, useContext);
    }

    @Override
    public ItemStack getUpgradeItem() {
        return new ItemStack((ItemLike)Items.f_42398_, 2);
    }

    @Override
    public List<BlockState> getModelStates() {
        return this.m_49965_().m_61056_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("scaffolding").get(side.m_122434_().m_122478_() ? "wood_top" : (this.getStrength(state) == this.maxStrength ? "wood_reinforced" : "wood_side"));
    }

    @Override
    public double getMaxSpeed(boolean fast) {
        return fast ? 0.5 : 0.25;
    }
}

