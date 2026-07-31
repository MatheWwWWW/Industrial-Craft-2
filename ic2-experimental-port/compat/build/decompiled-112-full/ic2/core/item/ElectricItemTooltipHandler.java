/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.core.init.Localization;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(value=Side.CLIENT)
public class ElectricItemTooltipHandler {
    public ElectricItemTooltipHandler() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SubscribeEvent
    public void drawTooltips(ItemTooltipEvent event) {
        String tooltip;
        ItemStack stack = event.getItemStack();
        if (stack != null && ElectricItem.manager.getMaxCharge(stack) > 0.0 && (tooltip = ElectricItem.manager.getToolTip(stack)) != null && !tooltip.trim().isEmpty()) {
            event.getToolTip().add(tooltip);
            if (Keyboard.isKeyDown((int)42)) {
                event.getToolTip().add(Localization.translate("ic2.item.tooltip.PowerTier", ElectricItem.manager.getTier(stack)));
            }
        }
    }
}

