/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.critereon.InventoryChangeTrigger
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package ic2.core.platform.corehacks.mixins.server;

import ic2.core.platform.registries.IC2Advancements;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={InventoryChangeTrigger.class})
public class TriggerMixin {
    @Inject(method={"trigger"}, at={@At(value="HEAD")})
    private void onTrigger(ServerPlayer player, Inventory inventory, ItemStack stack, CallbackInfo info) {
        if (stack.m_41619_()) {
            return;
        }
        IC2Advancements.RECIPE_ITEM_TRIGGER.onTrigger((Player)player, stack);
    }
}

