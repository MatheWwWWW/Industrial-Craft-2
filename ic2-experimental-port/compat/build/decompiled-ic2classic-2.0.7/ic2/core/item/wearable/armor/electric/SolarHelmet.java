/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.item.wearable.armor.electric;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.IC2;
import ic2.core.block.generators.tiles.SolarPanelTileEntity;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;

public class SolarHelmet
extends IC2AdvancedArmorBase
implements IBaseArmorModule {
    private boolean advanced;

    public SolarHelmet(boolean advanced) {
        super(advanced ? "advanced_solar_helmet" : "solar_helmet", EquipmentSlot.HEAD, new PropertiesBuilder().maxDamage(0).rarity(advanced ? Rarity.UNCOMMON : Rarity.COMMON));
        this.advanced = advanced;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
        HashMultimap modifiers = HashMultimap.create();
        if (equipmentSlot == EquipmentSlot.HEAD) {
            modifiers.put((Object)Attributes.f_22284_, (Object)new AttributeModifier(UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"), "Armor modifier", 1.0, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.f_43901_;
    }

    @Override
    public String getTextureFolder() {
        return "armor/solar_helmet";
    }

    @Override
    public String getTextureName() {
        return this.advanced ? "advanced" : "base";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/solar";
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addDataTooltip("tooltip.item.ic2.solar_helmet_production", this.getProduction());
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        if (SolarPanelTileEntity.isSunVisible(world, player.m_20183_())) {
            ElectricItem.chargeArmor(player, this.getProduction());
        }
    }

    private int getProduction() {
        return this.advanced ? 5 : 1;
    }

    @Override
    public IArmorModule.ModuleType getType(ItemStack stack) {
        return IArmorModule.ModuleType.CHARGER;
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        ElectricItem.chargeArmor(player, this.getProduction());
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }
}

