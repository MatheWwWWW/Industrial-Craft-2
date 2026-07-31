/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.item.wearable.armor.electric;

import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.IC2;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;

public class NightVisionGoggles
extends IC2AdvancedArmorBase
implements IDamagelessElectricItem,
IBaseArmorModule {
    public NightVisionGoggles() {
        super("nightvision_goggles", EquipmentSlot.HEAD, new PropertiesBuilder().maxDamage(0));
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 20000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 200;
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        ElectricItem.MANAGER.discharge(stack, ElectricItem.applyEnchantmentEffect(stack, 10 * amount), Integer.MAX_VALUE, true, false, false);
        return 0;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.f_43901_;
    }

    @Override
    public String getTextureFolder() {
        return "armor";
    }

    @Override
    public String getTextureName() {
        return "nightvision_goggles";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/nightvision";
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, items);
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.HUD_KEY, KeyHelper.MODE_KEY, "tooltip.item.ic2.nightvision_goggles.hud_key", new Object[0]));
        helper.addDataTooltip("tooltip.ic2.nightvision_seeinthedark", new Object[0]);
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (world.m_5776_()) {
            return;
        }
        CompoundTag nbt = stack.m_41784_();
        int ticker = nbt.m_128451_("Ticker");
        if (ticker > 0) {
            --ticker;
        }
        boolean enabled = nbt.m_128471_("Enabled");
        PlayerHandler handler = PlayerHandler.getHandler(player);
        if (ticker <= 0 && handler.hudModeKeyDown && handler.modeSwitchKeyDown && !IC2.KEYBOARD.isSneakKeyDown(player) && !handler.screenOpen) {
            ticker = 20;
            enabled = !enabled;
            nbt.m_128379_("Enabled", enabled);
            player.m_5661_((Component)this.translate("info.item.ic2.night_vision." + (enabled ? "enabled" : "disabled")), false);
        }
        if (enabled && ElectricItem.MANAGER.use(stack, 1, (LivingEntity)player)) {
            player.m_21195_(MobEffects.f_19610_);
            player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 250, 0, true, false));
        }
        nbt.m_128405_("Ticker", ticker);
    }

    public int m_142158_(ItemStack stack) {
        return IC2ElectricItem.getElectricWidth(stack);
    }

    public int m_142159_(ItemStack stack) {
        return IC2ElectricItem.getRGBDurability(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    @Override
    public IArmorModule.ModuleType getType(ItemStack stack) {
        return IArmorModule.ModuleType.GENERIC;
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        if (world.m_5776_()) {
            return;
        }
        CompoundTag nbt = armor.m_41698_("night");
        int ticker = nbt.m_128451_("Ticker");
        if (ticker > 0) {
            --ticker;
        }
        boolean enabled = nbt.m_128471_("Enabled");
        if (ticker <= 0 && IC2.KEYBOARD.isHudModeKeyDown(player) && !PlayerHandler.getHandler((Player)player).screenOpen) {
            ticker = 20;
            enabled = !enabled;
            nbt.m_128379_("Enabled", enabled);
            player.m_5661_((Component)this.translate("info.item.ic2.night_vision." + (enabled ? "enabled" : "disabled")), false);
        }
        if (enabled && ElectricItem.MANAGER.discharge(armor, 1, Integer.MAX_VALUE, true, false, false) >= 1) {
            player.m_21195_(MobEffects.f_19610_);
            player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 250, 0, true, false));
        }
        nbt.m_128405_("Ticker", ticker);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        armor.m_41749_("night");
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }
}

