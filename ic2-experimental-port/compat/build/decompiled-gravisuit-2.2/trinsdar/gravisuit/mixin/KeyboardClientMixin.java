/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.platform.player.KeyboardClient
 *  net.minecraft.client.KeyMapping
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package trinsdar.gravisuit.mixin;

import ic2.core.platform.player.KeyboardClient;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.GravisuitKeys;

@Mixin(value={KeyboardClient.class})
public class KeyboardClientMixin {
    @Shadow
    private KeyMapping[] array;

    @Inject(method={"init"}, at={@At(value="TAIL")}, remap=false)
    private void injectInit(CallbackInfo info) {
        ArrayList<KeyMapping> list = new ArrayList<KeyMapping>(Arrays.asList(this.array));
        list.add(GravisuitKeys.G_KEY);
        this.array = list.toArray(new KeyMapping[0]);
    }
}

