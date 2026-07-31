/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.cauldron.CauldronInteraction
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.DyeableLeatherItem
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
import ic2.core.block.base.misc.color.IItemColorListener;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.features.item.IColoredItemModel;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class QuantumSuit
extends IC2ModularElectricArmor
implements DyeableLeatherItem,
IItemColorListener,
IColoredItemModel {
    public QuantumSuit(String itemName, EquipmentSlot slot) {
        super(itemName, slot, new PropertiesBuilder().rarity(Rarity.RARE));
        this.addSlotType(IArmorModule.ModuleType.BATTERY, 1);
        this.addSlotType(IArmorModule.ModuleType.CHARGER, 1);
        switch (slot) {
            case CHEST: {
                this.addSlotType(IArmorModule.ModuleType.GENERIC, 1);
                this.addSlotType(IArmorModule.ModuleType.STORAGE, 2);
                this.addSlotType(IArmorModule.ModuleType.BACK_SLOT, 1);
                break;
            }
            case FEET: {
                this.addSlotType(IArmorModule.ModuleType.GENERIC, 1);
                this.addSlotType(IArmorModule.ModuleType.MOVEMENT, 2);
                break;
            }
            case HEAD: {
                this.addSlotType(IArmorModule.ModuleType.GENERIC, 3);
                break;
            }
            case LEGS: {
                this.addSlotType(IArmorModule.ModuleType.GENERIC, 1);
                this.addSlotType(IArmorModule.ModuleType.STORAGE, 2);
                this.addSlotType(IArmorModule.ModuleType.MOVEMENT, 1);
                break;
            }
        }
        MinecraftForge.EVENT_BUS.post((Event)new ArmorSlotEvent((Item)this, "Quantum Suit", slot, (Object2IntMap<IArmorModule.ModuleType>)this.types));
        CauldronInteraction.f_175607_.put(this, CauldronInteraction.f_175615_);
    }

    @Override
    public ItemStack createDefaultArmor() {
        ItemStack stack = new ItemStack((ItemLike)this);
        Object2ObjectSortedMap<IArmorModule.ModuleType, ObjectArrayList> types = CollectionUtils.createLinkedMap();
        types.put(IArmorModule.ModuleType.BATTERY, ObjectArrayList.wrap((Object[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.LAPATRON_CRYSTAL)}));
        switch (this.f_40377_) {
            case HEAD: {
                types.put(IArmorModule.ModuleType.GENERIC, ObjectArrayList.wrap((Object[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.AUTO_FEED_MODULE), new ItemStack((ItemLike)IC2Items.AIR_REFILL_MODULE), new ItemStack((ItemLike)IC2Items.PROTECTION_MODULE)}));
                break;
            }
            case CHEST: {
                types.put(IArmorModule.ModuleType.GENERIC, ObjectArrayList.wrap((Object[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.EXTINGUISHING_MODULE)}));
                break;
            }
            case LEGS: {
                types.put(IArmorModule.ModuleType.MOVEMENT, ObjectArrayList.wrap((Object[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.SPEED_MODULE)}));
                break;
            }
            case FEET: {
                types.put(IArmorModule.ModuleType.MOVEMENT, ObjectArrayList.wrap((Object[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.JUMP_BOOST_MODULE)}));
                break;
            }
        }
        QuantumSuit.setAndInstallTypes(stack, types);
        return stack;
    }

    @Override
    public double getDamageAbsorptionRatio(ItemStack stack) {
        return this.f_40377_ == EquipmentSlot.FEET ? 1.1 : 1.0;
    }

    @Override
    public int getEnergyPerDamage(ItemStack stack) {
        return 900;
    }

    @Override
    public boolean isFullyAbsorbingFallDamage(ItemStack stack) {
        return true;
    }

    @Override
    public String getTextureFolder() {
        return "armor/quantum";
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
        return "ic2:textures/models/armor/quantum";
    }

    @Override
    public int getItemColor(ItemStack stack, int tintIndex) {
        return tintIndex == 1 && this.m_41113_(stack) ? super.m_41121_(stack) : -1;
    }

    public int m_41121_(ItemStack stack) {
        return this.getItemColor(stack, 1);
    }

    @Override
    public int getTintedIndexForModel(ItemStack stack, int layer) {
        return 1;
    }
}

