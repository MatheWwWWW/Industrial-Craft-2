/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.inventory.SmithingMenu
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package ic2.core.platform.corehacks.mixins.server;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={SmithingMenu.class})
public class SmithingMixin {
    @Inject(method={"shrinkStackInSlot"}, at={@At(value="RETURN")}, locals=LocalCapture.CAPTURE_FAILEXCEPTION)
    public void shrinkStack(int slot, CallbackInfo info, ItemStack stack) {
        stack.m_41769_(1);
        if (stack.hasCraftingRemainingItem()) {
            ((Slot)((SmithingMenu)this).f_38839_.get(slot)).m_5852_(stack.getCraftingRemainingItem());
        } else {
            stack.m_41774_(1);
        }
    }
}

