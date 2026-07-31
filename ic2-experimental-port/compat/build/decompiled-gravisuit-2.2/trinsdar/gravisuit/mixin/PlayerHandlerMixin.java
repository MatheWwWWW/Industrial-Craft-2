/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.platform.player.PlayerHandler
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package trinsdar.gravisuit.mixin;

import ic2.core.platform.player.PlayerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.IGravisuitPlayerHandler;

@Mixin(value={PlayerHandler.class})
public class PlayerHandlerMixin
implements IGravisuitPlayerHandler {
    @Unique
    boolean isFlightKeyDown;

    @Inject(method={"onKeyChanged"}, at={@At(value="TAIL")}, remap=false)
    private void injectOnKeyChanged(int key, CallbackInfo info) {
        this.isFlightKeyDown = (key & 0x100) != 0;
    }

    @Override
    public boolean isFlightKeyDown() {
        return this.isFlightKeyDown;
    }
}

