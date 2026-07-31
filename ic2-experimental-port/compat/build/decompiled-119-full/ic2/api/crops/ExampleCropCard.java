/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.api.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ExampleCropCard
extends CropCard {
    public ExampleCropCard(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public String getId() {
        return "example";
    }

    @Override
    public String getOwner() {
        return "myaddon";
    }

    @Override
    public Block getCropBlock() {
        return null;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(1, 0, 4, 0, 0, 2);
    }

    @Override
    public int getMaxAge() {
        return 5;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)Items.f_42415_, 1);
    }

    @Override
    public List<ResourceLocation> getTexturesLocation() {
        ArrayList<ResourceLocation> arrayList = new ArrayList<ResourceLocation>(this.getMaxAge());
        for (int i = 1; i <= this.getMaxAge(); ++i) {
            arrayList.add(new ResourceLocation("myaddon", "blocks/crop/" + this.getId() + "_" + i));
        }
        return arrayList;
    }
}

