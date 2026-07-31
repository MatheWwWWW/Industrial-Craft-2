/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Rarity
 */
package ic2.core.item.base;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class PropertiesBuilder {
    private Integer maxStackSize = null;
    private Integer maxDamage = null;
    private Item containerItem = null;
    private CreativeModeTab group = null;
    private Rarity rarity = null;
    private FoodProperties food = null;
    private Boolean canRepair = null;
    private Boolean foiled;

    public PropertiesBuilder maxStackSize(int maxStackSize) {
        if (this.maxStackSize == null) {
            this.maxStackSize = maxStackSize;
        }
        return this;
    }

    public PropertiesBuilder maxStackSizeOverride(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public PropertiesBuilder maxDamage(int maxDamage) {
        if (this.maxDamage == null) {
            this.maxDamage = maxDamage;
        }
        return this;
    }

    public PropertiesBuilder maxDamageOverride(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

    public PropertiesBuilder containerItem(Item containerItem) {
        if (this.containerItem == null) {
            this.containerItem = containerItem;
        }
        return this;
    }

    public PropertiesBuilder containerItemOverride(Item containerItem) {
        this.containerItem = containerItem;
        return this;
    }

    public PropertiesBuilder setFoiled(boolean value) {
        if (this.foiled == null) {
            this.foiled = value;
        }
        return this;
    }

    public PropertiesBuilder setFoiledOverride(boolean value) {
        this.foiled = value;
        return this;
    }

    public PropertiesBuilder group(CreativeModeTab group) {
        if (this.group == null) {
            this.group = group;
        }
        return this;
    }

    public PropertiesBuilder groupOverride(CreativeModeTab group) {
        this.group = group;
        return this;
    }

    public PropertiesBuilder rarity(Rarity rarity) {
        if (this.rarity == null) {
            this.rarity = rarity;
        }
        return this;
    }

    public PropertiesBuilder rarityOverride(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public PropertiesBuilder food(FoodProperties food) {
        if (this.food == null) {
            this.food = food;
        }
        return this;
    }

    public PropertiesBuilder foodOverride(FoodProperties food) {
        this.food = food;
        return this;
    }

    public PropertiesBuilder canRepair(boolean canRepair) {
        if (this.canRepair == null) {
            this.canRepair = canRepair;
        }
        return this;
    }

    public PropertiesBuilder canRepairOverride(boolean canRepair) {
        this.canRepair = canRepair;
        return this;
    }

    public PropertiesBuilder setRepair() {
        return this.canRepair(true);
    }

    public PropertiesBuilder setRepairOverride() {
        return this.canRepairOverride(true);
    }

    public PropertiesBuilder setNoRepair() {
        return this.canRepair(false);
    }

    public PropertiesBuilder setNoRepairOverride() {
        return this.canRepairOverride(false);
    }

    public Boolean isFoiled() {
        return this.foiled;
    }

    public Item.Properties build() {
        Item.Properties props = new Item.Properties();
        if (this.maxStackSize != null) {
            props.m_41487_(this.maxStackSize.intValue());
        }
        if (this.maxDamage != null) {
            props.m_41503_(this.maxDamage.intValue());
        }
        if (this.containerItem != null) {
            props.m_41495_(this.containerItem);
        }
        if (this.group != null) {
            props.m_41491_(this.group);
        }
        if (this.rarity != null) {
            props.m_41497_(this.rarity);
        }
        if (this.food != null) {
            props.m_41489_(this.food);
        }
        if (this.canRepair != null && !this.canRepair.booleanValue()) {
            props.setNoRepair();
        }
        return props;
    }
}

