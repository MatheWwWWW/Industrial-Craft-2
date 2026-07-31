/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.crop;

import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import net.minecraft.world.item.ItemStack;

public abstract class CropBase
extends Ic2CropCard {
    protected final ItemStack cropDrop;

    public CropBase(ICropType iCropType, ItemStack itemStack) {
        super(iCropType);
        this.cropDrop = itemStack;
    }
}

