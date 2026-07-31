/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.IC2;
import ic2.core.item.wearable.base.IArmorToolTip;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParachuteModuleItem
extends BaseModuleItem
implements IArmorToolTip {
    public static final String GLIDING = "gliding";

    public ParachuteModuleItem(String textureFolder, String textureName) {
        super("parachute_module", null, textureFolder, textureName, IArmorModule.ModuleType.BACK_SLOT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        this.handleToolTip(stack, helper::addSimpleToolTip);
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.JUMP_KEY, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.parashoot.name", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.JUMP_KEY, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.parashoot.name", new Object[0]));
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        CompoundTag nbt = armor.m_41698_("para");
        boolean gliding = nbt.m_128471_(GLIDING);
        if (!gliding && IC2.KEYBOARD.isJumpKeyDown(player) && IC2.KEYBOARD.isSneakKeyDown(player) && !player.m_20096_() && player.m_20184_().f_82480_ < 0.0 && !player.m_21255_() && !player.m_20069_()) {
            nbt.m_128379_(GLIDING, true);
            nbt.m_128356_("lastPress", world.m_46467_());
        } else if (gliding) {
            if (player.m_20096_() || player.m_20069_() || IC2.KEYBOARD.isJumpKeyDown(player) && IC2.KEYBOARD.isSneakKeyDown(player) && world.m_46467_() - nbt.m_128454_("lastPress") >= 5L) {
                nbt.m_128379_(GLIDING, false);
                return;
            }
            Vec3 motion = player.m_20184_();
            player.m_20334_(motion.m_7096_(), Math.max(-0.2, motion.m_7098_()), motion.m_7094_());
            player.m_19920_(0.02f, new Vec3(0.0, 0.0, 1.0));
            IC2.PLATFORM.resetPlayerInAirTime((Entity)player);
            player.f_19789_ = 0.0f;
        }
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        armor.m_41749_("para");
    }
}

