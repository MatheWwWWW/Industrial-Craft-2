/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features.item;

import ic2.core.platform.rendering.features.item.IMultiItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomItemModel
extends IMultiItemModel {
    public List<ItemStack> getCustomTypes();

    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getModel(ItemStack var1);
}

