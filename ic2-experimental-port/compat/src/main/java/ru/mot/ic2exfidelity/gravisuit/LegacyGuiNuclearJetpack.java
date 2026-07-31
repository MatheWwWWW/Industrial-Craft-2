package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.Ic2Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/** IC2 Classic's exact 176x222 nuclear-jetpack screen. */
public final class LegacyGuiNuclearJetpack
        extends Ic2Gui<LegacyContainerNuclearJetpack> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(
            "ic2", "textures/gui_sprites/items/gui_nuclear_jetpack.png");

    public LegacyGuiNuclearJetpack(
            LegacyContainerNuclearJetpack menu,
            Inventory inventory,
            Component title) {
        super(menu, inventory, title, 222);
    }

    @Override
    protected ResourceLocation getTexture() {
        return BACKGROUND;
    }
}
