/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.api.items.armor.IArmorModule;
import ic2.api.items.readers.IThermometer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.tooltips.ToolTipHelper;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ThermometerTool
extends IC2Item
implements ISimpleItemModel,
IToolModel,
IThermometer,
IBaseArmorModule {
    public ThermometerTool() {
        this("thermometer", null);
    }

    protected ThermometerTool(String itemName, @Nullable PropertiesBuilder properties) {
        super(itemName, (properties == null ? new PropertiesBuilder() : properties).group(IC2.IC2_MAIN_GROUP).maxDamage(200).maxStackSize(1));
    }

    @Override
    public boolean isThermometer(ItemStack stack) {
        return true;
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("thermometer");
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        IReactor reactor;
        if (IC2.PLATFORM.isSimulating() && (reactor = this.getReactor(context.m_43725_().m_7702_(context.m_8083_()))) != null) {
            Player player = context.m_43723_();
            player.m_5661_((Component)this.translate("info.item.ic2.thermometer", new Object[]{reactor.getHeat(), reactor.getMaxHeat()}), false);
            if (!player.m_7500_()) {
                player.m_21120_(context.m_43724_()).m_41622_(1, (LivingEntity)player, ThermometerTool.get(context.m_43724_()));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public IReactor getReactor(BlockEntity tile) {
        if (tile instanceof IReactor) {
            return (IReactor)tile;
        }
        if (tile instanceof IReactorChamber) {
            return ((IReactorChamber)tile).getReactor();
        }
        return null;
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
        holder.addAddModifier(armor, IArmorModule.ArmorMod.THERMOMETER, 1);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.THERMOMETER, 1);
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        return false;
    }
}

