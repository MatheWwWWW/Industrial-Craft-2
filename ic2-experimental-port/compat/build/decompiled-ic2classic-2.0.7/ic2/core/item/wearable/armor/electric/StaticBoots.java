/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.item.wearable.armor.electric;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.armor.IMetalArmor;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;

public class StaticBoots
extends IC2AdvancedArmorBase
implements IBaseArmorModule,
IMetalArmor {
    public StaticBoots() {
        super("static_boots", EquipmentSlot.FEET, null);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
        HashMultimap modifiers = HashMultimap.create();
        if (equipmentSlot == EquipmentSlot.FEET) {
            modifiers.put((Object)Attributes.f_22284_, (Object)new AttributeModifier(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), "Armor modifier", 1.0, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.m_43929_((ItemLike[])new ItemLike[]{IC2Items.INGOT_REFINED_IRON});
    }

    @Override
    public String getTextureFolder() {
        return "armor/hazmat";
    }

    @Override
    public String getTextureName() {
        return "rubber_boots";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/rubber";
    }

    @Override
    public boolean isMetalArmor(ItemStack stack, Player player, EquipmentSlot targetSlot) {
        return true;
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addDataTooltip("tooltip.item.ic2.static_boots.produce_eu", new Object[0]);
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        int charge = this.getCharge(stack);
        if (world.m_46467_() % 10L == 0L && charge > 0) {
            this.chargeItems(player, stack);
        }
        if (charge < 20) {
            int posZ;
            int posX;
            boolean isNotWalking;
            CompoundTag nbt = stack.m_41784_();
            boolean bl = isNotWalking = player.m_20202_() != null || player.m_20069_() || !player.m_20096_();
            if (isNotWalking || !nbt.m_128441_("x")) {
                posX = (int)player.m_20185_();
                nbt.m_128405_("x", posX);
            } else {
                posX = nbt.m_128451_("x");
            }
            if (isNotWalking || !nbt.m_128441_("z")) {
                posZ = (int)player.m_20189_();
                nbt.m_128405_("z", posZ);
            } else {
                posZ = nbt.m_128451_("z");
            }
            int distX = posX - (int)player.m_20185_();
            int distZ = posZ - (int)player.m_20189_();
            double distance = Math.sqrt(distX * distX + distZ * distZ);
            if (distance >= 5.0) {
                nbt.m_128405_("x", (int)player.m_20185_());
                nbt.m_128405_("z", (int)player.m_20189_());
                boolean cloth = player.m_146900_().m_60767_() == Material.f_76299_;
                this.setCharge(stack, charge + Math.min(3, Mth.m_14107_((double)(cloth ? distance : distance / 5.0))));
            }
        }
    }

    public void chargeItems(Player player, ItemStack stack) {
        int charge = this.getCharge(stack);
        charge = ElectricItem.chargeArmor(player, charge, false, EquipmentSlot.FEET);
        this.setCharge(stack, charge);
    }

    public int getCharge(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("Charge");
    }

    public void setCharge(ItemStack stack, int charge) {
        stack.m_41784_().m_128405_("Charge", charge);
    }

    @Override
    public IArmorModule.ModuleType getType(ItemStack stack) {
        return IArmorModule.ModuleType.CHARGER;
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        armor.m_41749_("static");
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        CompoundTag nbt = armor.m_41698_("static");
        int energy = nbt.m_128451_("energy");
        if (world.m_46467_() % 10L == 0L && energy > 0) {
            energy = ElectricItem.chargeArmor(player, energy);
            nbt.m_128405_("energy", energy);
        }
        if (energy < 20) {
            boolean isNotWalking;
            boolean bl = isNotWalking = player.m_20202_() != null || player.m_20069_();
            if (isNotWalking || !nbt.m_128441_("x")) {
                nbt.m_128405_("x", (int)player.m_20185_());
            }
            if (isNotWalking || !nbt.m_128441_("z")) {
                nbt.m_128405_("z", (int)player.m_20189_());
            }
            int x = nbt.m_128451_("x");
            int z = nbt.m_128451_("z");
            double distance = Math.sqrt((x - (int)player.m_20185_()) * (x - (int)player.m_20185_()) + (z - (int)player.m_20189_()) * (z - (int)player.m_20189_()));
            if (distance >= 5.0) {
                nbt.m_128405_("x", (int)player.m_20185_());
                nbt.m_128405_("z", (int)player.m_20189_());
                boolean cloth = player.m_146900_().m_60767_() == Material.f_76299_;
                nbt.m_128405_("energy", energy + Math.min(3, Mth.m_14107_((double)(cloth ? distance : distance / 5.0))));
            }
        }
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }
}

