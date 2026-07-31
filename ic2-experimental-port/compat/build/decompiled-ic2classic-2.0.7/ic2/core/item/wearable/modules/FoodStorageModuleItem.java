/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.ItemStackHandler
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.food_and_drink.TinCanItem;
import ic2.core.item.inv.inventory.FoodStorageModuleInventory;
import ic2.core.item.wearable.base.IHasArmorInventory;
import ic2.core.item.wearable.modules.AutoFeedModuleItem;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class FoodStorageModuleItem
extends BaseModuleItem
implements IHasArmorInventory {
    public static final String DATA = "food_storage";

    public FoodStorageModuleItem(String textureFolder, String textureName) {
        super("food_storage_module", new PropertiesBuilder().maxStackSize(1), textureFolder, textureName, IArmorModule.ModuleType.STORAGE);
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        CompoundTag nbt = armor.m_41698_(DATA);
        nbt.m_128344_("listener", (byte)(nbt.m_128445_("listener") + 1));
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.CHEST;
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        CompoundTag nbt = StackUtil.getNbtData(armor).m_128469_(DATA);
        byte value = (byte)(nbt.m_128445_("listener") - 1);
        if (value <= 0) {
            armor.m_41749_(DATA);
            return;
        }
        nbt.m_128344_("listener", value);
    }

    @Override
    public void transferToArmor(ItemStack stack, ItemStack oldArmor, ItemStack newArmor) {
        CompoundTag nbt = StackUtil.getNbtData(oldArmor).m_128469_(DATA).m_6426_();
        nbt.m_128473_("listener");
        nbt.m_128344_("listener", StackUtil.getNbtData(newArmor).m_128469_(DATA).m_128445_("listener"));
        newArmor.m_41700_(DATA, (Tag)nbt);
    }

    @Override
    public boolean hasInventory(ItemStack stack) {
        return false;
    }

    @Override
    public boolean hasArmorInventory(ItemStack armor, ItemStack stack) {
        return true;
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new FoodStorageModuleInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new FoodStorageModuleInventory(player, this, stack, slot).load(stack);
    }

    static {
        AutoFeedModuleItem.addFoodSource((t, u) -> {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                CompoundTag nbt;
                if (slot.m_20743_() == EquipmentSlot.Type.HAND || !(nbt = StackUtil.getNbtData(t.m_6844_(slot))).m_128441_(DATA)) continue;
                return TransporterManager.getTransporter((Object)new CustomHandler(nbt.m_128469_(DATA)));
            }
            return null;
        });
    }

    public static class CustomHandler
    extends ItemStackHandler {
        CompoundTag nbt;

        public CustomHandler(CompoundTag nbt) {
            super(4);
            this.nbt = nbt;
            NBTUtils.loadItems(nbt.m_128469_("inventory"), (NonNullList<ItemStack>)this.stacks);
        }

        protected void onContentsChanged(int slot) {
            CompoundTag data = new CompoundTag();
            NBTUtils.saveItems(data, (NonNullList<ItemStack>)this.stacks);
            this.nbt.m_128365_("inventory", (Tag)data);
        }

        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.m_41720_() instanceof TinCanItem && (slot == 0 || slot == 1) || (slot == 2 || slot == 3) && stack.m_41720_() == IC2Items.TIN_CAN;
        }
    }
}

