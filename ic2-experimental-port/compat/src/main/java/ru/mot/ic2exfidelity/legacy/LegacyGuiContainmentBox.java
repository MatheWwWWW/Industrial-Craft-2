package ru.mot.ic2exfidelity.legacy;

import ic2.core.Ic2Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class LegacyGuiContainmentBox extends Ic2Gui<LegacyContainerContainmentBox> {
    private static final ResourceLocation BACKGROUND =
            new ResourceLocation("ic2", "textures/gui/guicontainmentbox.png");

    public LegacyGuiContainmentBox(
            LegacyContainerContainmentBox menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected ResourceLocation getTexture() {
        return BACKGROUND;
    }
}
