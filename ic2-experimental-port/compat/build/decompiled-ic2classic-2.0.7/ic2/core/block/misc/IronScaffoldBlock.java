/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.core.block.misc.base.ScaffoldBlock;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IDoubleSidedModel;
import ic2.core.utils.helpers.Tool;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IronScaffoldBlock
extends ScaffoldBlock
implements IBlockModel,
IDoubleSidedModel {
    public static IntegerProperty STRENGTH = IntegerProperty.m_61631_((String)"strength", (int)0, (int)12);

    public IronScaffoldBlock() {
        super("scaffold_iron", 5, 12, BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60913_(0.8f, 10.0f));
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    @Override
    public IntegerProperty createProps() {
        return STRENGTH;
    }

    @Override
    public ItemStack getUpgradeItem() {
        return new ItemStack((ItemLike)IC2Items.INGOT_REFINED_IRON);
    }

    @Override
    public double getMaxSpeed(boolean fast) {
        return fast ? 0.6 : 0.3;
    }

    @Override
    public List<BlockState> getModelStates() {
        return this.m_49965_().m_61056_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("scaffolding").get(side.m_122434_().m_122478_() ? "iron_top" : (this.getStrength(state) == this.maxStrength ? "iron_reinforced" : "iron_side"));
    }

    @Override
    protected void onScaffoldPlaced(Player player, Level level, BlockPos clickPos, BlockPos placePos, boolean vertical) {
        if (clickPos.m_123342_() <= level.m_141937_() + 10 && placePos.m_123342_() >= level.m_151558_() - 1) {
            IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:scaffold"));
        }
    }
}

