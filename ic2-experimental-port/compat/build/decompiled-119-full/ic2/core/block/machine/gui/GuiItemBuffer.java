/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerItemBuffer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiItemBuffer
extends Ic2Gui<ContainerItemBuffer> {
    public GuiItemBuffer(ContainerItemBuffer containerItemBuffer, Inventory inventory, Component component) {
        super(containerItemBuffer, inventory, component, 176, 232);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guiitembuffer.png");
    }
}

