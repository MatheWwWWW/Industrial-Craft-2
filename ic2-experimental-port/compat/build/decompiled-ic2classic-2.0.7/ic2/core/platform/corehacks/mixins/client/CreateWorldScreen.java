/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  net.minecraft.client.gui.screens.worldselection.WorldCreationContext
 *  net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package ic2.core.platform.corehacks.mixins.client;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={WorldGenSettingsComponent.class})
public class CreateWorldScreen {
    @Shadow
    WorldCreationContext f_101394_;

    @Inject(method={"updateSettings(Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;)V"}, at={@At(value="HEAD")}, cancellable=true, locals=LocalCapture.CAPTURE_FAILEXCEPTION)
    void updateSettings(WorldCreationContext setting, CallbackInfo info) {
        Lifecycle cycle = setting.f_232988_();
        this.f_101394_ = new WorldCreationContext(setting.f_232987_(), cycle == Lifecycle.stable() || cycle == Lifecycle.experimental() ? Lifecycle.stable() : cycle, setting.f_232989_(), setting.f_232990_());
        info.cancel();
    }
}

