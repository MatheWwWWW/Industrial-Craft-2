/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.slot;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LockedSlot
extends SlotBase {
    public LockedSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
    }

    public boolean m_5857_(ItemStack stack) {
        return false;
    }

    public boolean m_8010_(Player playerIn) {
        return false;
    }

    public static class DragableLockedSlot
    extends LockedSlot {
        public DragableLockedSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
            super(inv, index, xPosition, yPosition);
        }

        @Override
        public boolean m_5857_(ItemStack stack) {
            return IC2.PLATFORM.isRendering() && this.isDragging();
        }

        @OnlyIn(value=Dist.CLIENT)
        public boolean isDragging() {
            Screen screen = Minecraft.m_91087_().f_91080_;
            return screen instanceof IC2Screen && ((IC2Screen)screen).isSlotDragging();
        }

        public void onClick(int mouseButton, boolean half, ItemStack itemStack) {
            ItemStack stack;
            if (mouseButton == 2) {
                this.m_5852_(ItemStack.f_41583_);
                return;
            }
            if (mouseButton == 0) {
                if (!this.m_6657_()) {
                    if (!itemStack.m_41619_()) {
                        this.m_5852_(StackUtil.copyWithSize(itemStack, half ? itemStack.m_41613_() : 1));
                    }
                    return;
                }
                stack = this.m_7993_();
                int decrease = 1;
                if (half && stack.m_41613_() > 1) {
                    decrease = stack.m_41613_() / 2;
                }
                stack.m_41774_(decrease);
            }
            if (mouseButton == 1) {
                if (!this.m_6657_()) {
                    if (!itemStack.m_41619_()) {
                        this.m_5852_(StackUtil.copyWithSize(itemStack, 1));
                    }
                    return;
                }
                stack = this.m_7993_();
                int increase = 1;
                if (half) {
                    increase = stack.m_41613_() / 2;
                }
                stack.m_41764_(Math.min(this.m_5866_(stack), stack.m_41613_() + increase));
            }
        }
    }
}

