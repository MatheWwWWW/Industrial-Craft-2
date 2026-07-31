/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.IC2;
import ic2.core.crop.CropBase;
import java.util.ArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropBaseSapling
extends CropBase {
    protected final Block cropBlock;
    protected final String saplingName;
    protected final ItemStack cropSapling;

    public CropBaseSapling(ICropType iCropType, Block block, String string, ItemStack itemStack, ItemStack itemStack2) {
        super(iCropType, itemStack);
        this.cropBlock = block;
        this.saplingName = "ic2.crop." + string;
        this.cropSapling = itemStack2;
    }

    @Override
    public Block getCropBlock() {
        return this.cropBlock;
    }

    @Override
    public String getSeedType() {
        return this.saplingName;
    }

    @Override
    public String getDiscoveredBy() {
        return "Speiger";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(3, 1, 0, 4, 4, 0);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Leaves", "Sapling", "Green"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < this.getMaxAge() && iCropTile.getLightLevel() >= 9;
    }

    @Override
    public ItemStack[] getGains(ICropTile iCropTile) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        arrayList.add(this.cropDrop.m_41777_());
        if (IC2.random.m_188503_(100) >= 75) {
            arrayList.add(this.cropSapling.m_41777_());
        }
        if (this.getId().equalsIgnoreCase("oak_sapling") && IC2.random.m_188503_(100) >= 75) {
            arrayList.add(new ItemStack((ItemLike)Items.f_42410_));
        }
        return arrayList.toArray(new ItemStack[arrayList.size()]);
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() >= this.getMaxAge() - 1 ? 150 : 600;
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return this.getMaxAge() - 1;
    }
}

