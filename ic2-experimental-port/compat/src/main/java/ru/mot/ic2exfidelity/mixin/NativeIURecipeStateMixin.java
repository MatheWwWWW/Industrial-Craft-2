package ru.mot.ic2exfidelity.mixin;

import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.StandardFluidItem;
import ic2.core.item.reactor.AbstractDamageableReactorComponent;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.mot.ic2exfidelity.iu.NativeWateringCan;

/** Native upgrades retain fuel usage and water instead of resetting durability. */
@Mixin(value = ShapedRecipe.class, remap = false)
public abstract class NativeIURecipeStateMixin {
    @Inject(method = "m_5874_(Lnet/minecraft/world/inventory/CraftingContainer;)Lnet/minecraft/world/item/ItemStack;", at = @At("RETURN"))
    private void ic2Native$carryState(CraftingContainer grid, CallbackInfoReturnable<ItemStack> callback) {
        ShapedRecipe recipe = (ShapedRecipe)(Object)this;
        if (!recipe.m_6423_().m_135815_().startsWith("native/")) return;
        ItemStack result = callback.getReturnValue();
        int used = 0;
        for (int i = 0; i < grid.m_6643_(); i++) {
            ItemStack input = grid.m_8020_(i);
            if (input.m_41720_() instanceof AbstractDamageableReactorComponent && input.m_41783_() != null) used = Math.max(used, input.m_41783_().m_128451_("use"));
            if (result.m_41720_() instanceof NativeWateringCan can && input.m_41720_() instanceof NativeWateringCan) {
                Ic2FluidStack water = Ic2FluidStack.get(input);
                if (water != null && !water.isEmpty()) StandardFluidItem.setFs(result, water.copyWithAmountMb(Math.min(water.getAmountMb(), can.getCapacityMb(result))));
            }
            if (input.m_41783_() != null && input.m_41783_().m_128425_("ic2NativeMachine", 10)) result.m_41784_().m_128365_("ic2NativeMachine", input.m_41783_().m_128469_("ic2NativeMachine").m_6426_());
        }
        if (used > 0 && result.m_41720_() instanceof AbstractDamageableReactorComponent component) component.setUse(result, used);
    }
}
