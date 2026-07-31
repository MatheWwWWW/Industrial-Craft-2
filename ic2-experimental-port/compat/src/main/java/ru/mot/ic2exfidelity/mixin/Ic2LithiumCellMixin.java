package ru.mot.ic2exfidelity.mixin;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.ItemReactorLithiumCell;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Restores the 1.12.2 lithium-cell completion behavior removed by ex119. */
@Mixin(value = ItemReactorLithiumCell.class, remap = false)
public abstract class Ic2LithiumCellMixin {
    @Shadow
    protected abstract int getUse(ItemStack stack);

    @Shadow
    public abstract void setUse(ItemStack stack, int use);

    @Shadow
    protected abstract int getMaxUse();

    @Inject(method = "acceptUraniumPulse", at = @At("HEAD"), cancellable = true)
    private void ic2ExperimentalFidelity$restoreCompletion(
            ItemStack stack,
            IReactor reactor,
            ItemStack pulsingStack,
            int x,
            int y,
            int pulseX,
            int pulseY,
            boolean heatRun,
            CallbackInfoReturnable<Boolean> callback) {
        if (heatRun) {
            int use = getUse(stack) + reactor.getHeat() / 3000;
            if (use >= getMaxUse()) {
                // In 2.8.222 the unregistered tritium_fuel_rod resolved to null,
                // so a fully bred lithium cell was removed from the reactor slot.
                reactor.setItemAt(x, y, null);
            } else {
                setUse(stack, use);
            }
        }
        callback.setReturnValue(true);
    }
}
