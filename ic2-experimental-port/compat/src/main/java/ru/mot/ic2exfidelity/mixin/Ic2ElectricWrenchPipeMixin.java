package ru.mot.ic2exfidelity.mixin;

import ic2.core.item.tool.ItemToolWrenchElectric;
import ic2.core.ref.Ic2SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.mot.ic2exfidelity.integration.PipeWrenchCompat;

@Mixin(value = ItemToolWrenchElectric.class, remap = false)
public abstract class Ic2ElectricWrenchPipeMixin {
    @Inject(method = "onItemUseFirst", at = @At("HEAD"), cancellable = true)
    private void fidelity$usePipe(
            ItemStack stack,
            UseOnContext context,
            CallbackInfoReturnable<InteractionResult> callback) {
        if (!PipeWrenchCompat.isPipe(context)) {
            return;
        }
        ItemToolWrenchElectric wrench = (ItemToolWrenchElectric) (Object) this;
        Player player = context.m_43723_();
        if (player == null) {
            callback.setReturnValue(InteractionResult.PASS);
            return;
        }
        if (!wrench.canTakeDamage(stack, 1.0D)) {
            callback.setReturnValue(InteractionResult.FAIL);
            return;
        }
        if (context.m_43725_().f_46443_) {
            player.m_5496_(Ic2SoundEvents.ITEM_WRENCH_USE, 1.0F, 1.0F);
        } else {
            PipeWrenchCompat.flip(context);
            wrench.consumeEnergy(stack, 1.0D, player);
        }
        callback.setReturnValue(InteractionResult.SUCCESS);
    }
}
