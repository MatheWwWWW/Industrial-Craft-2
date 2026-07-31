/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.inventory.base.IPortableInventory
 *  ic2.core.inventory.container.ItemContainer
 *  ic2.core.inventory.gui.IC2Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package trinsdar.gravisuit.items.container;

import ic2.core.IC2;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.container.ItemContainer;
import ic2.core.inventory.gui.IC2Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.gravisuit.items.container.GuiCompRelocatorAdd;
import trinsdar.gravisuit.items.container.ItemInventoryRelocator;

public class ItemContainerRelocatorAdd
extends ItemContainer<ItemInventoryRelocator> {
    public static ResourceLocation TEXTURE = new ResourceLocation("gravisuit", "textures/gui/relocator_add.png");

    public ItemContainerRelocatorAdd(ItemInventoryRelocator inv, int id, InteractionHand hand, Player player, int windowId) {
        super((IPortableInventory)inv, player, id, windowId);
        if (IC2.PLATFORM.isRendering()) {
            this.addComponent(new GuiCompRelocatorAdd(hand, player));
        }
    }

    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        super.onGuiLoaded(screen);
        screen.setYSize(66);
        screen.setGuiName((Component)Component.m_237119_());
        screen.clearFlag(1);
    }
}

