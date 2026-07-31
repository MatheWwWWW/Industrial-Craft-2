/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.api.network.buffer.EmptyDataBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.IC2;
import ic2.core.block.rendering.world.impl.SonarOverlay;
import ic2.core.item.base.features.ISonarProvider;
import ic2.core.item.wearable.base.IArmorToolTip;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SonarModuleItem
extends BaseModuleItem
implements ISonarProvider,
IArmorToolTip {
    public static final String DATA = "sonar";
    public static final int MAX_RADIUS = 250;
    public static final int SMALL_SCAN_RADIUS = 25;

    public SonarModuleItem(String textureFolder, String textureName) {
        super("sonar_module", null, textureFolder, textureName, IArmorModule.ModuleType.HUD);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        this.handleToolTip(stack, helper::addSimpleToolTip);
        helper.addKeybindingTooltip(this.buildKeyDescription(new KeyHelper[]{KeyHelper.SNEAK_KEY, KeyHelper.ALT_KEY, KeyHelper.HUD_KEY}, "tooltip.item.ic2.sonar_module.toggle", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(new KeyHelper[]{KeyHelper.ALT_KEY, KeyHelper.HUD_KEY}, "tooltip.item.ic2.sonar_module.long_scan", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(new KeyHelper[]{KeyHelper.SNEAK_KEY, KeyHelper.ALT_KEY, KeyHelper.HUD_KEY}, "tooltip.item.ic2.sonar_module.toggle", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(new KeyHelper[]{KeyHelper.ALT_KEY, KeyHelper.HUD_KEY}, "tooltip.item.ic2.sonar_module.long_scan", new Object[0]));
        helper.addSimpleToolTip(StackUtil.getNbtData(armor).m_128469_(DATA).m_128471_("scan") ? "tooltip.item.ic2.sonar_module.enabled" : "tooltip.item.ic2.sonar_module.disabled", new Object[0]);
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        PlayerHandler handler = PlayerHandler.getHandler(player);
        CompoundTag data = armor.m_41698_(DATA);
        if (handler.hudModeKeyDown && handler.altKeyDown) {
            if (player.m_6144_() && world.m_46467_() >= data.m_128454_("last_toggle")) {
                data.m_128356_("last_toggle", world.m_46467_() + 20L);
                boolean result = !data.m_128471_("scan");
                data.m_128379_("scan", result);
                if (IC2.PLATFORM.isSimulating()) {
                    player.m_5661_((Component)this.translate("tooltip.item.ic2.sonar_module." + (result ? "enable" : "disable")), false);
                }
            } else if (!player.m_6144_() && !data.m_128441_("positions") && this.canUseEnergy(armor, 20000)) {
                this.useEnergy(armor, 20000, (LivingEntity)player);
                List entities = world.m_45976_(LivingEntity.class, new AABB(player.m_20183_()).m_82400_(250.0));
                int[] ids = new int[entities.size()];
                int m = entities.size();
                for (int i = 0; i < m; ++i) {
                    ids[i] = ((LivingEntity)entities.get(i)).m_19879_();
                }
                data.m_128385_("positions", ids);
                data.m_128356_("pos", player.m_20183_().m_121878_());
                if (IC2.PLATFORM.isSimulating()) {
                    IC2.NETWORKING.get(true).sendItemBuffer(player, armor, DATA, EmptyDataBuffer.INSTANCE);
                }
            }
        }
        if (data.m_128471_("scan") && world.m_46467_() % 20L == 0L) {
            for (Entity entity : world.m_45976_(LivingEntity.class, new AABB(player.m_20183_()).m_82400_(25.0))) {
                if (!(entity instanceof LivingEntity) || entity == player) continue;
                ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19619_, 30, 0, false, false));
            }
        }
        if (data.m_128441_("positions")) {
            double progress = data.m_128459_("progress");
            AABB box = new AABB(BlockPos.m_122022_((long)data.m_128454_("pos"))).m_82400_(250.0 * progress);
            for (int id : data.m_128465_("positions")) {
                Entity entity = world.m_6815_(id);
                if (!(entity instanceof LivingEntity) || entity == player || !box.m_82381_(entity.m_20191_())) continue;
                ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19619_, 100, 0, false, false));
            }
            if (progress >= 1.0) {
                data.m_128473_("positions");
                data.m_128473_("progress");
                return;
            }
            data.m_128347_("progress", progress + 0.005);
        }
    }

    @Override
    public boolean handlePacket(Player player, ItemStack module, ItemStack armor, String id, INetworkDataBuffer buffer, Dist targetSide) {
        if (id.equals(DATA)) {
            EquipmentSlot find = this.findPlayerSlot(player, armor);
            if (find == null) {
                return true;
            }
            player.m_6844_(find).m_41751_(armor.m_41783_());
            SonarOverlay.INSTANCE.addSonar(this, () -> player.m_6844_(find));
            return true;
        }
        return false;
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        armor.m_41749_(DATA);
    }

    @Override
    public int getViewRadius(ItemStack stack) {
        return (int)(255.0 * StackUtil.getNbtData(stack).m_128469_(DATA).m_128459_("progress"));
    }

    @Override
    public BlockPos getStartPosition(ItemStack stack) {
        return BlockPos.m_122022_((long)StackUtil.getNbtData(stack).m_128469_(DATA).m_128454_("pos"));
    }

    @Override
    public boolean isDone(ItemStack stack) {
        return !StackUtil.getNbtData(stack).m_128469_(DATA).m_128441_("positions");
    }

    @Override
    public int getSonarColor(ItemStack stack) {
        return 0x79AAAAAA;
    }
}

