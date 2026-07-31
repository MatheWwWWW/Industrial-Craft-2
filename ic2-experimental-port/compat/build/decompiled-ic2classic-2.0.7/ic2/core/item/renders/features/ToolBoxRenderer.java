/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.renders.features;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.item.base.features.IScrollableInventory;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ToolBoxRenderer {
    public static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
    public static final ToolBoxRenderer INSTANCE = new ToolBoxRenderer();

    @OnlyIn(value=Dist.CLIENT)
    public void renderOverlay(PoseStack matrix, int width, int height) {
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91080_ != null || !IC2.KEYBOARD.isHudModeKeyDown((Player)mc.f_91074_)) {
            return;
        }
        Inventory player = mc.f_91074_.m_150109_();
        if (player.m_36056_().m_41720_() instanceof IScrollableInventory) {
            return;
        }
        ItemStack stack = this.getToolBox(player);
        if (stack.m_41619_()) {
            return;
        }
        IScrollableInventory.IndexedStack[] data = ((IScrollableInventory)stack.m_41720_()).getItemsToSwapWith(stack, mc.f_91074_.m_6144_());
        RenderSystem.m_157456_((int)0, (ResourceLocation)WIDGETS_TEX_PATH);
        matrix.m_85837_(0.0, 0.0, 1.0);
        int pos = width / 2 - 90 - 1 + player.f_35977_ * 20;
        int top = height - 66 - 1;
        int bottom = height - 44 - 1;
        mc.f_91065_.m_93228_(matrix, pos, bottom, 60, 22, 22, 24);
        mc.f_91065_.m_93228_(matrix, pos, top, 60, 22, 22, 24);
        Lighting.m_84930_();
        mc.m_91291_().m_115203_(data[0].getStack(), pos + 3, bottom + 4);
        mc.m_91291_().m_115169_(mc.f_91062_, data[0].getStack(), pos + 3, bottom + 4);
        mc.m_91291_().m_115203_(data[1].getStack(), pos + 3, top + 4);
        mc.m_91291_().m_115169_(mc.f_91062_, data[1].getStack(), pos + 3, top + 4);
        matrix.m_85837_(0.0, 0.0, -1.0);
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean onScrollToolbox() {
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91080_ != null || !IC2.KEYBOARD.isHudModeKeyDown((Player)mc.f_91074_)) {
            return false;
        }
        Inventory player = mc.f_91074_.m_150109_();
        if (player.m_36056_().m_41720_() instanceof IScrollableInventory) {
            return false;
        }
        return !this.getToolBox(player).m_41619_();
    }

    public boolean onServerScrollToolbox(Player owner, boolean result) {
        if (owner.m_150109_().m_36056_().m_41720_() instanceof IScrollableInventory) {
            return false;
        }
        PlayerHandler handler = PlayerHandler.getHandler(owner);
        if (!handler.hudModeKeyDown || handler.screenOpen) {
            return false;
        }
        ItemStack stack = this.getToolBox(owner.m_150109_());
        if (stack.m_41619_()) {
            return false;
        }
        IScrollableInventory item = (IScrollableInventory)stack.m_41720_();
        IScrollableInventory.IndexedStack[] data = item.getItemsToSwapWith(stack, owner.m_6144_());
        int slot = item.getScrollIndex(stack);
        int index = result ? 1 : 0;
        ItemStack toSwap = data[index].getStack();
        ItemStack toInsert = owner.m_150109_().m_36056_();
        if (item.swapItems(stack, data[index].getSlot(), slot, toInsert)) {
            owner.m_150109_().m_6836_(owner.m_150109_().f_35977_, toSwap);
            if (result) {
                item.setScrollIndex(stack, data[index].getSlot());
            } else {
                int newSlot = data[index].getSlot();
                item.setScrollIndex(stack, (newSlot == 0 ? item.getSlotCount(stack) : newSlot) - 1);
            }
        }
        return true;
    }

    public ItemStack getToolBox(Inventory inv) {
        ItemStack current = inv.m_36056_();
        for (int i = 0; i < 9; ++i) {
            IScrollableInventory scroll;
            ItemStack stack;
            if (i == inv.f_35977_ || !((stack = inv.m_8020_(i)).m_41720_() instanceof IScrollableInventory) || !(scroll = (IScrollableInventory)stack.m_41720_()).canScrollInventory(stack) || !current.m_41619_() && !scroll.isValidItem(stack, current)) continue;
            return stack;
        }
        return ItemStack.f_41583_;
    }
}

