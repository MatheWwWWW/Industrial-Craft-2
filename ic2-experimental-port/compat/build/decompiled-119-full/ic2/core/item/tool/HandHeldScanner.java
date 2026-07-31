/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.tool;

import ic2.core.ContainerBase;
import ic2.core.item.tool.ContainerToolScanner;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.network.GrowingBuffer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HandHeldScanner
extends HandHeldInventory {
    ItemStack itemScanner;
    Player player;

    public HandHeldScanner(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        super(player, interactionHand, itemStack, 0);
        this.itemScanner = itemStack;
        this.player = player;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerToolScanner(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerToolScanner(n, inventory, this);
    }
}

