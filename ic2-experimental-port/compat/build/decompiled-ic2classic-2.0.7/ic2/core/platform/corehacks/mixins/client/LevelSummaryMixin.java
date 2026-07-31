/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.storage.PrimaryLevelData
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package ic2.core.platform.corehacks.mixins.client;

import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PrimaryLevelData.class})
public class LevelSummaryMixin {
    @Inject(method={"hasConfirmedExperimentalWarning"}, at={@At(value="HEAD")}, cancellable=true, remap=false)
    private void ignoreExperimentalSettingsScreen(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((Object)true);
    }
}

