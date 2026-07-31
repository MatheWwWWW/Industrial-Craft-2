/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  ic2.api.addons.IModule
 *  ic2.api.items.armor.IArmorModule$ModuleType
 *  ic2.api.items.electric.ElectricItem
 *  ic2.api.network.buffer.INetworkDataBuffer
 *  ic2.core.IC2
 *  ic2.core.item.base.PropertiesBuilder
 *  ic2.core.item.wearable.base.IBaseArmorModule
 *  ic2.core.item.wearable.base.IC2AdvancedArmorBase
 *  ic2.core.platform.registries.IC2Items
 *  ic2.core.utils.tooltips.ToolTipHelper
 *  ic2.curioplugin.core.CurioPlugin
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.items.IItemHandler
 */
package trinsdar.advancedsolars.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.addons.IModule;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.IC2;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.tooltips.ToolTipHelper;
import ic2.curioplugin.core.CurioPlugin;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.items.IItemHandler;
import trinsdar.advancedsolars.blocks.BlockEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

public class ItemArmorAdvancedSolarHelmet
extends IC2AdvancedArmorBase
implements IBaseArmorModule {
    private int production;
    private int lowerProduction;
    private int tier;

    public ItemArmorAdvancedSolarHelmet(String name, int pro, int lowPro, int tier) {
        super(name + "_solar_helmet", EquipmentSlot.HEAD, new PropertiesBuilder().maxDamage(0).rarity(Rarity.RARE));
        this.production = pro;
        this.lowerProduction = lowPro;
        this.tier = tier;
        IC2Items.registerItem((Item)this);
    }

    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        helper.addDataTooltip(AdvancedSolarLang.helmetProduction, new Object[]{this.production});
        helper.addDataTooltip(AdvancedSolarLang.helmetLowerProduction, new Object[]{this.lowerProduction});
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        super.onArmorTick(stack, world, player);
        if (!IC2.PLATFORM.isRendering() && world.m_6042_().f_223549_() && world.m_46861_(player.m_20183_())) {
            if (BlockEntityAdvancedSolarPanel.isSunVisible(world, player.m_20183_())) {
                this.chargeInventory(player, this.production, this.tier, stack);
            } else {
                this.chargeInventory(player, this.lowerProduction, this.tier, stack);
            }
        }
    }

    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        this.onArmorTick(armor, world, player);
    }

    public String getArmorTexture() {
        return "advanced_solars:textures/models/" + this.getRegistryName().m_135815_();
    }

    public String getTextureName() {
        return this.getRegistryName().m_135815_();
    }

    public String getTextureFolder() {
        return "solar_helmets";
    }

    public int chargeInventory(Player player, int provided, int tier, ItemStack helmet) {
        List<NonNullList> invList = Arrays.asList(player.m_150109_().f_35975_, player.m_150109_().f_35976_, player.m_150109_().f_35974_);
        if (ElectricItem.MANAGER.getCharge(helmet) != ElectricItem.MANAGER.getCapacity(helmet)) {
            int charged = ElectricItem.MANAGER.charge(helmet, provided, this.tier, false, false);
            provided -= charged;
        } else {
            int i;
            for (NonNullList inventory : invList) {
                int inventorySize = inventory.size();
                for (i = 0; i < inventorySize && provided > 0; ++i) {
                    ItemStack tStack = (ItemStack)inventory.get(i);
                    if (tStack.m_41619_()) continue;
                    int charged = ElectricItem.MANAGER.charge(tStack, provided, this.tier, false, false);
                    provided -= charged;
                }
            }
            IModule plugin = IC2.PLUGINS.getModule("curio");
            if (plugin != null) {
                IItemHandler inv = new CurioPlugin().getCurioHandler(player);
                for (i = 0; i < inv.getSlots() && provided > 0; ++i) {
                    provided = (int)((double)provided - (double)ElectricItem.MANAGER.charge(inv.getStackInSlot(i), provided, tier, false, false));
                }
            }
        }
        return provided;
    }

    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
        HashMultimap modifiers = HashMultimap.create();
        if (equipmentSlot == EquipmentSlot.HEAD) {
            modifiers.put((Object)Attributes.f_22284_, (Object)new AttributeModifier(UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"), "Armor modifier", 1.0, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    public IArmorModule.ModuleType getType(ItemStack itemStack) {
        return IArmorModule.ModuleType.CHARGER;
    }

    public boolean handlePacket(Player player, ItemStack itemStack, ItemStack itemStack1, String s, INetworkDataBuffer iNetworkDataBuffer, Dist dist) {
        return false;
    }

    public Ingredient getRepairMaterial() {
        return Ingredient.f_43901_;
    }
}

