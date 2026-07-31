/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.curioplugin.core.CurioPlugin
 *  ic2.curioplugin.modules.TickingCurio
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.fml.InterModComms
 *  net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  top.theillusivec4.curios.api.SlotTypePreset
 *  top.theillusivec4.curios.api.type.capability.ICurio
 */
package trinsdar.gravisuit.mixin;

import ic2.curioplugin.core.CurioPlugin;
import ic2.curioplugin.modules.TickingCurio;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import trinsdar.gravisuit.util.Registry;

@Mixin(value={CurioPlugin.class})
public abstract class CurioPluginMixin {
    @Inject(method={"loadIMC"}, at={@At(value="TAIL")}, remap=false)
    private void injectLoadIMC(InterModEnqueueEvent mod, CallbackInfo info) {
        InterModComms.sendTo((String)"gravisuit", (String)"curios", (String)"register_type", () -> SlotTypePreset.BACK.getMessageBuilder().build());
    }

    @Inject(method={"createForItem"}, at={@At(value="TAIL")}, remap=false, cancellable=true)
    private void injectCreateForItem(ItemStack stack, CallbackInfoReturnable<ICurio> info) {
        Item i = stack.m_41720_();
        if (i == Registry.ADVANCED_ELECTRIC_JETPACK || i == Registry.ADVANCED_NUCLEAR_JETPACK || i == Registry.GRAVITATION_JETPACK || i == Registry.NUCLEAR_GRAVITATION_JETPACK) {
            info.setReturnValue((Object)new TickingCurio(stack, false, true, true));
        }
        if (i == Registry.ADVANCED_LAPPACK || i == Registry.ULTIMATE_LAPPACK) {
            info.setReturnValue((Object)new TickingCurio(stack, false, false, true));
        }
    }
}

