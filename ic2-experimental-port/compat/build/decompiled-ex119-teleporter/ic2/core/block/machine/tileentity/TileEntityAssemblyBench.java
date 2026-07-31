/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.IHasGui;
import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
import ic2.core.init.Localization;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityAssemblyBench
extends TileEntityBatchCrafter
implements IHasGui,
IUpgradableBlock {
    public static final List<CraftingRecipe> RECIPES = new ArrayList<CraftingRecipe>();

    public TileEntityAssemblyBench(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityBatchCrafter>)Ic2BlockEntities.UU_ASSEMBLY_BENCH, blockPos, blockState);
    }

    @Override
    protected CraftingRecipe findRecipe() {
        for (CraftingRecipe craftingRecipe : RECIPES) {
            if (!craftingRecipe.m_5818_((Container)this.crafting, this.m_58904_())) continue;
            return craftingRecipe;
        }
        return null;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> list, TooltipFlag tooltipFlag) {
        list.add("You probably want the " + Localization.translate(Ic2Items.REPLICATOR.m_5524_()));
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

