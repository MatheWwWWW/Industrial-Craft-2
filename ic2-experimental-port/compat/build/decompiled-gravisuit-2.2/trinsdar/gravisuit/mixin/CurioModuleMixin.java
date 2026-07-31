/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.platform.registries.IC2Tags
 *  ic2.curioplugin.CurioModule
 *  net.minecraft.world.item.Item
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package trinsdar.gravisuit.mixin;

import ic2.core.platform.registries.IC2Tags;
import ic2.curioplugin.CurioModule;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.Registry;

@Mixin(value={CurioModule.class})
public abstract class CurioModuleMixin {
    @Inject(method={"postInit"}, at={@At(value="TAIL")}, remap=false)
    private void injectPostInit(CallbackInfo info) {
        IC2Tags.registerSimpleTag((String)"curios", (String)"back", (Item[])new Item[]{Registry.ADVANCED_ELECTRIC_JETPACK, Registry.ADVANCED_NUCLEAR_JETPACK, Registry.GRAVITATION_JETPACK, Registry.NUCLEAR_GRAVITATION_JETPACK, Registry.ADVANCED_LAPPACK, Registry.ULTIMATE_LAPPACK});
    }
}

