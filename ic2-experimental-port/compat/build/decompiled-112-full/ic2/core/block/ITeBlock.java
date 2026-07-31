/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  mcp.MethodsReturnNonnullByDefault
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.block;

import ic2.core.block.BlockTileEntity;
import ic2.core.block.TeBlockRegistry;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.state.IIdProvider;
import ic2.core.item.block.ItemBlockTileEntity;
import ic2.core.ref.TeBlock;
import java.util.Set;
import javax.annotation.Nullable;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

@MethodsReturnNonnullByDefault
public interface ITeBlock
extends IIdProvider {
    public ResourceLocation getIdentifier();

    public boolean hasItem();

    @Nullable
    public Class<? extends TileEntityBlock> getTeClass();

    public boolean hasActive();

    public Set<EnumFacing> getSupportedFacings();

    public float getHardness();

    public float getExplosionResistance();

    public TeBlock.HarvestTool getHarvestTool();

    public TeBlock.DefaultDrop getDefaultDrop();

    public EnumRarity getRarity();

    public boolean allowWrenchRotating();

    default public Material getMaterial() {
        return TeBlockRegistry.getInfo(this.getIdentifier()).getDefaultMaterial();
    }

    default public boolean isTransparent() {
        return false;
    }

    default public void setPlaceHandler(TeBlock.ITePlaceHandler handler) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    default public TeBlock.ITePlaceHandler getPlaceHandler() {
        return null;
    }

    @Nullable
    @Deprecated
    public TileEntityBlock getDummyTe();

    public static interface ITeBlockCreativeRegisterer {
        public void addSubBlocks(NonNullList<ItemStack> var1, BlockTileEntity var2, ItemBlockTileEntity var3, CreativeTabs var4);
    }
}

