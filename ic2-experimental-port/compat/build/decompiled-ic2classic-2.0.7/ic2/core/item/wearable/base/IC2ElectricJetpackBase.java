/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.base;

import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class IC2ElectricJetpackBase
extends IC2JetpackBase
implements IDamagelessElectricItem,
IElectricEnchantable,
IBaseArmorModule {
    public static final String MODULE_DATA = "jetpack_data";

    public IC2ElectricJetpackBase(String itemName, EquipmentSlot slot, @Nullable PropertiesBuilder props) {
        super(itemName, slot, (props == null ? new PropertiesBuilder() : props).maxDamage(0));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    @Override
    public int getMaxFuel(ItemStack stack) {
        return this.getCapacity(stack);
    }

    @Override
    public int getFuel(ItemStack stack) {
        return ElectricItem.MANAGER.getCharge(stack);
    }

    @Override
    public void useEnergy(Player player, ItemStack stack, int amount) {
        if (stack.m_41720_() != this) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        } else if (!ElectricItem.MANAGER.use(stack, amount, (LivingEntity)player)) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        }
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, items);
    }

    @Override
    public int m_142158_(ItemStack stack) {
        return IC2ElectricItem.getElectricWidth(stack);
    }

    @Override
    public int m_142159_(ItemStack stack) {
        return IC2ElectricItem.getRGBDurability(stack);
    }

    @Override
    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, this.getFuelCost(stack, IC2JetpackBase.HoverMode.NONE) * amount), Integer.MAX_VALUE, true, false, false);
        return 0;
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44962_ ? InteractionResult.FAIL : InteractionResult.PASS;
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        if (!(stack.m_41720_() instanceof IC2ElectricJetpackBase)) {
            return null;
        }
        IC2ElectricJetpackBase item = (IC2ElectricJetpackBase)stack.m_41720_();
        switch (item.m_40402_()) {
            case CHEST: {
                return EnchantmentCategory.ARMOR_CHEST;
            }
            case FEET: {
                return EnchantmentCategory.ARMOR_FEET;
            }
            case HEAD: {
                return EnchantmentCategory.ARMOR_HEAD;
            }
            case LEGS: {
                return EnchantmentCategory.ARMOR_LEGS;
            }
        }
        return EnchantmentCategory.ARMOR;
    }

    @Override
    public CompoundTag getNBTData(ItemStack stack, boolean create) {
        CompoundTag data = super.getNBTData(stack, create);
        if (stack.m_41720_() != this) {
            CompoundTag subData = data.m_128469_(MODULE_DATA);
            if (create) {
                data.m_128365_(MODULE_DATA, (Tag)subData);
            }
            return subData;
        }
        return data;
    }

    @Override
    public IArmorModule.ModuleType getType(ItemStack stack) {
        return IArmorModule.ModuleType.BACK_SLOT;
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.CHEST;
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        CompoundTag nbt = StackUtil.getNbtData(stack).m_6426_();
        nbt.m_128473_("charge");
        armor.m_41698_(MODULE_DATA).m_128391_(nbt);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        stack.m_41784_().m_128391_(StackUtil.getNbtData(armor).m_128469_(MODULE_DATA));
        armor.m_41749_(MODULE_DATA);
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        this.onArmorTick(armor, world, player);
    }

    @Override
    public void transferToArmor(ItemStack stack, ItemStack oldArmor, ItemStack newArmor) {
        newArmor.m_41698_(MODULE_DATA).m_128391_(StackUtil.getNbtData(newArmor).m_128469_(MODULE_DATA));
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }
}

