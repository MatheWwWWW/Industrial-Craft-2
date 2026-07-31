/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.base.features.ISoundBattery;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BatteryItem
extends IC2ElectricItem
implements IItemModel,
ISoundBattery,
IBaseArmorModule {
    public static final ResourceLocation AUDIO_FILE = new ResourceLocation("ic2", "sounds/tools/battery.ogg");
    private String textureName;
    private boolean hasStates;
    private int armor_transfer;

    public BatteryItem(String itemName, int capacity, int transferLimit, int tier, boolean provider, String textureName, boolean hasStates, int armor_transfer, @Nullable PropertiesBuilder props) {
        super(itemName, props);
        this.capacity = capacity;
        this.tier = tier;
        this.transferLimit = transferLimit;
        this.provider = provider;
        this.textureName = textureName;
        this.hasStates = hasStates;
        this.armor_transfer = armor_transfer;
    }

    public BatteryItem(String itemName, int capacity, int transferLimit, int tier, boolean provider, String textureName, boolean hasStates, int armor_transfer) {
        super(itemName);
        this.capacity = capacity;
        this.tier = tier;
        this.transferLimit = transferLimit;
        this.provider = provider;
        this.textureName = textureName;
        this.hasStates = hasStates;
        this.armor_transfer = armor_transfer;
    }

    public BatteryItem(String itemName, int capacity, int transferLimit, int tier, boolean provider, String textureName, boolean hasStates) {
        super(itemName);
        this.capacity = capacity;
        this.tier = tier;
        this.transferLimit = transferLimit;
        this.provider = provider;
        this.textureName = textureName;
        this.hasStates = hasStates;
        this.armor_transfer = -1;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, "tooltip.item.ic2.battery.recharge", new Object[0]));
        if (this.armor_transfer <= 0) {
            return;
        }
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    @Override
    public List<ItemStack> getModelTypes() {
        if (this.hasStates) {
            ObjectList subTypes = CollectionUtils.createList();
            for (int i = 0; i < 5; ++i) {
                ItemStack battery = new ItemStack((ItemLike)this);
                ElectricItem.MANAGER.charge(battery, i * this.capacity / 4, Integer.MAX_VALUE, true, false);
                subTypes.add((ItemStack)battery);
            }
            return subTypes;
        }
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    protected int getEnergyCost(ItemStack stack) {
        return 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        if (this.hasStates) {
            int percentage = ElectricItem.MANAGER.getCharge(input) * 4 / this.capacity;
            return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "battery").get(this.textureName + "_" + percentage);
        }
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "battery").get(this.textureName);
    }

    @Override
    public boolean m_142522_(ItemStack stack) {
        return ElectricItem.MANAGER.getCharge(stack) > 0 && super.m_142522_(stack);
    }

    public int getMaxStackSize(ItemStack stack) {
        return ElectricItem.MANAGER.getCharge(stack) > 0 ? 1 : 16;
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        if (this.hasStates) {
            return ElectricItem.MANAGER.getCharge(stack) * 5 / this.capacity;
        }
        return 0;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (ElectricItem.MANAGER.getCharge(stack) > 0) {
            playerIn.m_6672_(handIn);
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19098_((Object)stack);
    }

    public void onUsingTick(ItemStack itemStack, LivingEntity entity, int count) {
        if (!(entity instanceof Player)) {
            return;
        }
        BatteryItem.shareEnergy((Player)entity, itemStack, this.transferLimit, count);
    }

    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.m_41720_() == newStack.m_41720_();
    }

    @Override
    public boolean wantsToPlay(ItemStack stack) {
        return true;
    }

    @Override
    public ResourceLocation getSound(ItemStack stack) {
        return AUDIO_FILE;
    }

    public int m_8105_(ItemStack stack) {
        return 20000;
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.m_41720_() != newStack.m_41720_() || slotChanged;
    }

    public static void shareEnergy(Player player, ItemStack itemStack, int transferLimit, int tier) {
        InteractionHand playerHand = player.m_7655_();
        boolean charged = false;
        if (playerHand == InteractionHand.OFF_HAND) {
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = player.m_150109_().m_8020_(i);
                Item item = stack.m_41720_();
                if (!(item instanceof IElectricItem)) continue;
                IElectricItem item2 = (IElectricItem)item;
                int transfer = ElectricItem.MANAGER.discharge(itemStack, 2 * transferLimit, item2.getTier(itemStack), true, false, true);
                transfer = ElectricItem.MANAGER.charge(stack, transfer, tier, true, false);
                ElectricItem.MANAGER.discharge(itemStack, transfer, item2.getTier(itemStack), true, false, false);
                if (transfer != 0) {
                    charged = true;
                    continue;
                }
                break;
            }
        } else {
            ItemStack stack;
            Object item;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack2;
                Item transfer;
                if (i == player.m_150109_().f_35977_ || !((transfer = (stack2 = player.m_150109_().m_8020_(i)).m_41720_()) instanceof IElectricItem)) continue;
                item = (IElectricItem)transfer;
                int transfer2 = ElectricItem.MANAGER.discharge(itemStack, 2 * transferLimit, item.getTier(itemStack), true, false, true);
                transfer2 = ElectricItem.MANAGER.charge(stack2, transfer2, tier, true, false);
                ElectricItem.MANAGER.discharge(itemStack, transfer2, item.getTier(itemStack), true, false, false);
                if (transfer2 == 0) break;
                charged = true;
            }
            if (!charged && (item = (stack = player.m_21120_(InteractionHand.OFF_HAND)).m_41720_()) instanceof IElectricItem) {
                IElectricItem item3 = (IElectricItem)item;
                int transfer = ElectricItem.MANAGER.discharge(itemStack, 2 * transferLimit, item3.getTier(itemStack), true, false, true);
                transfer = ElectricItem.MANAGER.charge(stack, transfer, tier, true, false);
                ElectricItem.MANAGER.discharge(itemStack, transfer, item3.getTier(itemStack), true, false, false);
                if (transfer > 0) {
                    charged = true;
                }
            }
        }
        if (!charged) {
            player.m_5810_();
        }
    }

    @Override
    public IArmorModule.ModuleType getType(ItemStack stack) {
        return IArmorModule.ModuleType.BATTERY;
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return this.armor_transfer > 0;
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_STORAGE, this.capacity);
        holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TIER, this.tier);
        holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TRANSFER, this.armor_transfer);
        ElectricItem.MANAGER.discharge(stack, ElectricItem.MANAGER.charge(armor, ElectricItem.MANAGER.getCharge(stack), Integer.MAX_VALUE, true, false), Integer.MAX_VALUE, true, false, false);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        ElectricItem.MANAGER.discharge(armor, ElectricItem.MANAGER.charge(stack, ElectricItem.MANAGER.getCharge(armor), Integer.MAX_VALUE, true, false), Integer.MAX_VALUE, true, false, false);
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_STORAGE, this.capacity);
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TIER, this.tier);
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TRANSFER, this.armor_transfer);
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }
}

