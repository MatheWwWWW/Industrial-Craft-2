/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.crops;

import ic2.api.crops.ICrop;
import ic2.api.crops.IFarmland;
import ic2.api.util.ILocation;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface ICropTile
extends ILocation {
    public IFarmland getFarmland();

    public boolean isWaterLogged();

    public void setWaterlogged(boolean var1);

    public ICrop getCrop();

    public void setCrop(ICrop var1);

    public int getGrowthStage();

    public void setGrowthStage(int var1);

    public int getGrowthPoints();

    public void setGrowthPoints(int var1);

    public int getScanLevel();

    public void setScanLevel(int var1);

    public boolean isCrossBreeding();

    public void setCrossBreeding(boolean var1);

    public int getGainStat();

    public void setGainStat(int var1);

    public int getGrowthStat();

    public void setGrowthStat(int var1);

    public int getResistanceStat();

    public void setResistanceStat(int var1);

    public boolean canBreed();

    public void requestStateUpdate();

    public void onCustomDataChanged();

    public int calculateGrowthSpeed();

    public boolean performManualHarvest();

    public List<ItemStack> performHarvest(boolean var1);

    public boolean pickCrop();

    public void removeCrop();

    public boolean isBlockBelow(BlockState var1);

    public boolean isBlockBelow(Block var1);

    public boolean isBlockBelow(TagKey<Block> var1);

    public Set<Block> getBlocksBelow();

    public CompoundTag getCustomData();

    public int getLightLevel();

    public int getEnvironmentQuality();

    public int getNutrients();

    public int getHumidity();

    public int getWaterStorage();

    public void setWaterStorage(int var1);

    public int getFertilizerStorage();

    public void setFertilizerStorage(int var1);

    public int getWeedExStorage();

    public void setWeedExStorage(int var1);

    public ItemStack createSeeds(ICrop var1, int var2, int var3, int var4, int var5);
}

