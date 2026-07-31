/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.base;

import ic2.core.inventory.container.IC2Container;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IHasGui {
    default public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return true;
    }

    public IC2Container createContainer(Player var1, InteractionHand var2, Direction var3, int var4);

    @OnlyIn(value=Dist.CLIENT)
    public Screen createGui(Player var1, InteractionHand var2, Direction var3, IC2Container var4);

    public boolean canInteractWith(Player var1);

    default public void onGuiClosed(Player player) {
    }
}

