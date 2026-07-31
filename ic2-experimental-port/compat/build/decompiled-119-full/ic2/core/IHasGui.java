/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.util.Util;
import java.io.IOException;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IHasGui
extends Container {
    public ContainerBase<?> createServerScreenHandler(int var1, Player var2);

    default public void writeScreenOpenData(Player player, InteractionHand interactionHand, GrowingBuffer growingBuffer) throws IOException {
    }

    public ContainerBase<?> createClientScreenHandler(int var1, Inventory var2, GrowingBuffer var3);

    default public void onScreenClosed(Player player) {
    }

    default public boolean openManagedBe(Player player, InteractionHand interactionHand) {
        GrowingBuffer growingBuffer = new GrowingBuffer(50);
        try {
            Ic2ScreenHandlers.writeManagedBeData((BlockEntity)this, growingBuffer);
            this.writeScreenOpenData(player, interactionHand, growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer.flip();
        return IC2.envProxy.openHandledScreen(player, new MenuProvider(){

            public AbstractContainerMenu m_7208_(int n, Inventory inventory, Player player) {
                return IHasGui.this.createServerScreenHandler(n, player);
            }

            public Component m_5446_() {
                return IHasGui.getBeName((BlockEntity)IHasGui.this);
            }
        }, growingBuffer);
    }

    public static Component getBeName(BlockEntity blockEntity) {
        ResourceLocation resourceLocation = Util.getName(blockEntity.m_58900_().m_60734_());
        return Component.m_237115_((String)String.format("container.%s.%s", resourceLocation.m_135827_(), resourceLocation.m_135815_().replace('/', '.')));
    }

    default public boolean openManagedItem(Player player, InteractionHand interactionHand, final Integer n) {
        GrowingBuffer growingBuffer = new GrowingBuffer(50);
        try {
            Ic2ScreenHandlers.writeManagedItemData(player, interactionHand, n, growingBuffer);
            this.writeScreenOpenData(player, interactionHand, growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer.flip();
        final Item item = player.m_21120_(interactionHand).m_41720_();
        return IC2.envProxy.openHandledScreen(player, new MenuProvider(){

            public AbstractContainerMenu m_7208_(int n2, Inventory inventory, Player player) {
                return IHasGui.this.createServerScreenHandler(n2, player);
            }

            public Component m_5446_() {
                return IHasGui.getItemName(item, n);
            }
        }, growingBuffer);
    }

    public static Component getItemName(Item item, Integer n) {
        ResourceLocation resourceLocation = Util.getName(item);
        return Component.m_237115_((String)String.format("container.%s.%s%s", resourceLocation.m_135827_(), resourceLocation.m_135815_().replace('/', '.'), n != null ? String.format(".%d", n) : ""));
    }
}

