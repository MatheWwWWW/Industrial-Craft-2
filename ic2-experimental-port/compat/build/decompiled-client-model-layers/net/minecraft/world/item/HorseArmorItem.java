/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HorseArmorItem
extends Item {
    private static final String f_150882_ = "textures/entity/horse/";
    private final int f_41361_;
    private final String f_41362_;

    public HorseArmorItem(int p_41364_, String p_41365_, Item.Properties p_41366_) {
        super(p_41366_);
        this.f_41361_ = p_41364_;
        this.f_41362_ = "textures/entity/horse/armor/horse_armor_" + p_41365_ + ".png";
    }

    public ResourceLocation m_41367_() {
        return new ResourceLocation(this.f_41362_);
    }

    public int m_41368_() {
        return this.f_41361_;
    }
}

