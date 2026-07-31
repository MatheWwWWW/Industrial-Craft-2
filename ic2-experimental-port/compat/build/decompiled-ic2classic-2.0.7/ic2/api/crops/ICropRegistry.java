/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.api.crops;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRenderer;
import ic2.api.crops.IFarmland;
import ic2.api.crops.ISubSoil;
import java.util.List;
import java.util.OptionalLong;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.Event;

public abstract class ICropRegistry {
    public static ICrop WEED = null;
    public static ICrop SEA_WEED = null;
    public static ICropRegistry INSTANCE;

    public abstract void registerCustomCropRenderer(ResourceLocation var1, ICropRenderer var2);

    public abstract void registerCustomStickRenderer(ICropRenderer var1);

    public abstract <T extends ICrop> T registerCrop(T var1);

    public ICrop getCrop(String id, String name) {
        return this.getCrop(new ResourceLocation(id, name));
    }

    public abstract ICrop getCrop(ResourceLocation var1);

    public abstract ICrop getRandomCrop(OptionalLong var1);

    public abstract ICrop getCrop(ItemStack var1);

    public abstract List<ICrop> getCrops();

    public abstract void registerDisplayItem(ResourceLocation var1, ItemStack var2);

    public abstract ItemStack getDisplayItem(ResourceLocation var1);

    public abstract void registerBaseSeed(Item var1, BaseSeed var2);

    public abstract BaseSeed getSeedForStack(ItemStack var1);

    public abstract ItemStack getSeedForCrop(ICrop var1);

    public abstract void addBiomeHumidityBonus(TagKey<Biome> var1, int var2);

    public abstract int getHumidityBonus(Holder<Biome> var1);

    public abstract void addBiomeNutrientBonus(TagKey<Biome> var1, int var2);

    public abstract int getNutrientBonus(Holder<Biome> var1);

    public abstract void addBiomeWaterQualityBonus(TagKey<Biome> var1, int var2);

    public abstract int getWaterQualityBonus(Holder<Biome> var1);

    public abstract void registerFarmland(IFarmland var1, Block ... var2);

    public abstract IFarmland getFarmland(Block var1);

    public abstract void registerSubSoil(ISubSoil var1, Block ... var2);

    public abstract ISubSoil getSubSoil(Block var1);

    public static class CropRegisterEvent
    extends Event {
        public void registerCrops(ICrop ... crops) {
            for (ICrop crop : crops) {
                INSTANCE.registerCrop(crop);
            }
        }

        public ICropRegistry getRegistry() {
            return INSTANCE;
        }
    }
}

