/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.electric;

import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.items.readers.ICropReader;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.item.INetworkItemEvent;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.inv.container.CropBreederContainer;
import ic2.core.item.inv.inventory.CropAnalyzerInventory;
import ic2.core.item.inv.inventory.CropBreederInventory;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CropAnalyzer
extends IC2ElectricItem
implements ISimpleItemModel,
IHasHeldSlotInventory,
INetworkItemEvent,
ICropReader,
IBaseArmorModule,
IElectricEnchantable {
    public CropAnalyzer() {
        super("crop_analyzer");
        this.capacity = 50000;
        this.transferLimit = 100;
        this.tier = 1;
    }

    @Override
    public boolean isCropReader(ItemStack stack) {
        return true;
    }

    @Override
    protected int getEnergyCost(ItemStack stack) {
        return 100;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("crop_analyzer");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (IC2.PLATFORM.isSimulating() && !playerIn.m_6144_()) {
            if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
                CompoundTag nbt = stack.m_41784_();
                boolean breed = !nbt.m_128471_("breed");
                nbt.m_128379_("breed", breed);
                if (!breed) {
                    nbt.m_128473_("results");
                    nbt.m_128473_("crops");
                }
                playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.crop_analyzer.mode", new Object[]{this.translate("tooltip.item.ic2.crop_analyzer." + (nbt.m_128471_("breed") ? "breeding" : "analyzing"))}), false);
            } else {
                IC2.PLATFORM.launchGui(playerIn, handIn, Direction.NORTH, this.getInventory(playerIn, handIn, stack));
            }
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        helper.addSimpleToolTip("tooltip.item.ic2.crop_analyzer.mode", this.translate("tooltip.item.ic2.crop_analyzer." + (nbt.m_128471_("breed") ? "breeding" : "analyzing")));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "tooltip.item.ic2.crop_analyzer.toggleMode", new Object[0]));
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        if (StackUtil.getNbtData(stack).m_128471_("breed")) {
            return new CropBreederInventory(player, this, stack, null).load(stack);
        }
        return new CropAnalyzerInventory(player, this, stack, null, IC2.PLATFORM.isSimulating()).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        if (StackUtil.getNbtData(stack).m_128471_("breed")) {
            return new CropBreederInventory(player, this, stack, slot).load(stack);
        }
        return new CropAnalyzerInventory(player, this, stack, slot, IC2.PLATFORM.isSimulating()).load(stack);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer() && player.f_36096_ instanceof CropBreederContainer) {
            CropBreederInventory inventory = (CropBreederInventory)((CropBreederContainer)player.f_36096_).getHolder();
            switch (key) {
                case 0: {
                    inventory.allCrops.offset(value);
                    break;
                }
                case 1: {
                    inventory.results.offset(value);
                    break;
                }
                case 2: {
                    inventory.results.clear();
                    break;
                }
                case 3: {
                    inventory.breed();
                    break;
                }
                case 4: {
                    inventory.waterlogged = !inventory.waterlogged;
                }
            }
        }
    }

    @Override
    public IArmorModule.ModuleType getType(ItemStack stack) {
        return IArmorModule.ModuleType.HUD;
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.addAddModifier(armor, IArmorModule.ArmorMod.CROP_SCANNER, 1);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.CROP_SCANNER, 1);
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44986_ ? InteractionResult.SUCCESS : (ench == Enchantments.f_44962_ ? InteractionResult.FAIL : InteractionResult.PASS);
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.BREAKABLE;
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }
}

