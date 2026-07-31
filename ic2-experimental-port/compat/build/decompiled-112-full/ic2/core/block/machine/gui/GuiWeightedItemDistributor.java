/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.block.machine.gui;

import ic2.core.block.machine.container.ContainerWeightedItemDistributor;
import ic2.core.block.machine.gui.GuiWeightedDistributor;
import net.minecraft.util.ResourceLocation;

public class GuiWeightedItemDistributor
extends GuiWeightedDistributor<ContainerWeightedItemDistributor> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIWeightedItemDistributor.png");

    public GuiWeightedItemDistributor(ContainerWeightedItemDistributor container) {
        super(container, 211);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

