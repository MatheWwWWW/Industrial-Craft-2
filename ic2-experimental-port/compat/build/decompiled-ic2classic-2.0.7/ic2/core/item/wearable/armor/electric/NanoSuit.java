/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.item.wearable.armor.electric;

import ic2.api.events.ArmorSlotEvent;
import ic2.api.items.armor.IArmorModule;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class NanoSuit
extends IC2ModularElectricArmor {
    public static final String UPGRADED = "upgraded";
    protected Object2IntMap<IArmorModule.ModuleType> alternative = new Object2IntOpenHashMap();

    public NanoSuit(String itemName, EquipmentSlot slot) {
        super(itemName, slot, new PropertiesBuilder().rarity(Rarity.UNCOMMON));
        this.addSlotType(IArmorModule.ModuleType.BATTERY, 1);
        this.addSlotType(IArmorModule.ModuleType.CHARGER, 1);
        switch (slot) {
            case CHEST: {
                this.addSlotType(IArmorModule.ModuleType.STORAGE, 1);
                this.addSlotType(IArmorModule.ModuleType.BACK_SLOT, 1);
                break;
            }
            case FEET: {
                this.addSlotType(IArmorModule.ModuleType.MOVEMENT, 2);
                break;
            }
            case HEAD: {
                this.addSlotType(IArmorModule.ModuleType.GENERIC, 2);
                break;
            }
            case LEGS: {
                this.addSlotType(IArmorModule.ModuleType.STORAGE, 1);
                this.addSlotType(IArmorModule.ModuleType.MOVEMENT, 1);
                break;
            }
        }
        if (slot == EquipmentSlot.HEAD) {
            this.alternative.put((Object)IArmorModule.ModuleType.HUD, 9);
        }
        this.alternative.mergeInt((Object)IArmorModule.ModuleType.BATTERY, 1, Integer::sum);
        this.alternative.mergeInt((Object)IArmorModule.ModuleType.CHARGER, 1, Integer::sum);
        switch (slot) {
            case CHEST: {
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.GENERIC, 1, Integer::sum);
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.STORAGE, 2, Integer::sum);
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.BACK_SLOT, 1, Integer::sum);
                break;
            }
            case FEET: {
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.GENERIC, 1, Integer::sum);
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.MOVEMENT, 2, Integer::sum);
                break;
            }
            case HEAD: {
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.GENERIC, 3, Integer::sum);
                break;
            }
            case LEGS: {
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.GENERIC, 1, Integer::sum);
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.STORAGE, 2, Integer::sum);
                this.alternative.mergeInt((Object)IArmorModule.ModuleType.MOVEMENT, 1, Integer::sum);
                break;
            }
        }
        MinecraftForge.EVENT_BUS.post((Event)new ArmorSlotEvent((Item)this, "Nano Suit", slot, (Object2IntMap<IArmorModule.ModuleType>)this.types));
        MinecraftForge.EVENT_BUS.post((Event)new ArmorSlotEvent((Item)this, "Nano Suit Alternative", slot, this.alternative));
    }

    @Override
    public ItemStack createDefaultArmor() {
        ItemStack stack = new ItemStack((ItemLike)this);
        Object2ObjectSortedMap<IArmorModule.ModuleType, ObjectArrayList> types = CollectionUtils.createLinkedMap();
        types.put(IArmorModule.ModuleType.BATTERY, ObjectArrayList.wrap((Object[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.ENERGY_CRYSTAL)}));
        NanoSuit.setAndInstallTypes(stack, types);
        return stack;
    }

    @Override
    public Object2IntMap<IArmorModule.ModuleType> getModuleLimits(ItemStack stack) {
        if (StackUtil.getNbtData(stack).m_128471_(UPGRADED)) {
            return Object2IntMaps.unmodifiable(this.alternative);
        }
        return Object2IntMaps.unmodifiable((Object2IntMap)this.types);
    }

    @Override
    public double getDamageAbsorptionRatio(ItemStack stack) {
        return 0.9;
    }

    @Override
    public int getEnergyPerDamage(ItemStack stack) {
        return 800;
    }

    @Override
    public boolean isFullyAbsorbingFallDamage(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128471_(UPGRADED);
    }

    @Override
    public String getTextureFolder() {
        return "armor/nano";
    }

    @Override
    public String getTextureName() {
        switch (this.f_40377_) {
            case HEAD: {
                return "helmet";
            }
            case CHEST: {
                return "bodyarmor";
            }
            case LEGS: {
                return "leggings";
            }
            case FEET: {
                return "boots";
            }
        }
        return "";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/nano";
    }
}

