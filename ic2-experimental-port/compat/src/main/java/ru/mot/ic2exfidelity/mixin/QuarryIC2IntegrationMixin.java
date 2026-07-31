package ru.mot.ic2exfidelity.mixin;

import com.yogpc.qp.machines.PowerTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.mot.ic2exfidelity.integration.AdditionalEnchantedMinerBridge;

@Mixin(targets = "com.yogpc.qp.integration.ic2.QuarryIC2Integration", remap = false)
public abstract class QuarryIC2IntegrationMixin {
    @Inject(method = "registerIc2Tile", at = @At("HEAD"), cancellable = true)
    private static void ic2ExperimentalFidelity$register(PowerTile tile, CallbackInfo callback) {
        AdditionalEnchantedMinerBridge.register(tile);
        callback.cancel();
    }

    @Inject(method = "unloadIc2Tile", at = @At("HEAD"), cancellable = true)
    private static void ic2ExperimentalFidelity$unregister(PowerTile tile, CallbackInfo callback) {
        AdditionalEnchantedMinerBridge.unregister(tile);
        callback.cancel();
    }
}

