/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import ic2.core.block.machine.container.ContainerWeightedItemDistributor;
import ic2.core.block.machine.gui.GuiWeightedDistributor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiWeightedItemDistributor
extends GuiWeightedDistributor<ContainerWeightedItemDistributor> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/guiweighteditemdistributor.png");

    public GuiWeightedItemDistributor(ContainerWeightedItemDistributor containerWeightedItemDistributor, Inventory inventory, Component component) {
        super(containerWeightedItemDistributor, inventory, component, 211);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

