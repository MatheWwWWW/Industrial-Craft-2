/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.utils.IC2ItemGroup
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 */
package trinsdar.gravisuit.items;

import ic2.core.utils.IC2ItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import trinsdar.gravisuit.util.Registry;

public class ItemComponents
extends Item {
    private int index;

    public ItemComponents(String name) {
        super(new Item.Properties().m_41491_(IC2ItemGroup.f_40753_));
        Registry.REGISTRY.put(new ResourceLocation("gravisuit", name), this);
    }
}

