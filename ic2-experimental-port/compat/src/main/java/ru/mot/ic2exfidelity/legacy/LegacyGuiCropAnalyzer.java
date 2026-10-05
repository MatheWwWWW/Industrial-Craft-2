package ru.mot.ic2exfidelity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class LegacyGuiCropAnalyzer extends Ic2Gui<LegacyContainerCropAnalyzer> {
    private static final ResourceLocation BACKGROUND =
            new ResourceLocation("ic2", "textures/gui/guicropnalyzer.png");

    public LegacyGuiCropAnalyzer(
            LegacyContainerCropAnalyzer menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 223);
    }

    @Override
    protected void drawBackgroundAndTitle(PoseStack pose, float partialTick, int mouseX, int mouseY) {
        bindTexture();
        m_93228_(pose, f_97735_, f_97736_, 0, 0, f_97726_, f_97727_);
    }

    @Override
    protected void drawForegroundLayer(PoseStack pose, int mouseX, int mouseY) {
        LegacyHandHeldCropAnalyzer analyzer = getContainer().base;
        drawXCenteredString(pose, 88, 11, Component.m_237115_("item.ic2.cropnalyzer"), 0x404040, false);
        int level = analyzer.getScannedLevel();
        if (level == 0) {
            drawString(pose, 8, 37, Component.m_237115_("gui.ic2.cropnalyzer.unknown").getString(), 0xFFFFFF, false);
        }
        if (level >= 1) {
            drawString(pose, 8, 37, analyzer.getSeedName(), 0xFFFFFF, false);
        }
        if (level >= 2) {
            drawString(pose, 8, 50, Component.m_237110_("gui.ic2.cropnalyzer.tier", analyzer.getSeedTier()).getString(), 0xFFFFFF, false);
            drawString(pose, 8, 73, Component.m_237115_("gui.ic2.cropnalyzer.discovered_by").getString(), 0xFFFFFF, false);
            drawString(pose, 8, 86, analyzer.getSeedDiscovered(), 0xFFFFFF, false);
        }
        if (level >= 3) {
            drawString(pose, 8, 109, analyzer.getSeedDesc(0), 0xFFFFFF, false);
            drawString(pose, 8, 122, analyzer.getSeedDesc(1), 0xFFFFFF, false);
        }
        if (level >= 4) {
            drawString(pose, 118, 37, Component.m_237115_("gui.ic2.cropnalyzer.growth").getString(), 0xADFF2F, false);
            drawString(pose, 118, 50, Integer.toString(analyzer.getSeedGrowth()), 0xADFF2F, false);
            drawString(pose, 118, 73, Component.m_237115_("gui.ic2.cropnalyzer.gain").getString(), 0xEEC900, false);
            drawString(pose, 118, 86, Integer.toString(analyzer.getSeedGain()), 0xEEC900, false);
            drawString(pose, 118, 109, Component.m_237115_("gui.ic2.cropnalyzer.resistance").getString(), 0x00CED1, false);
            drawString(pose, 118, 122, Integer.toString(analyzer.getSeedResistance()), 0x00CED1, false);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return BACKGROUND;
    }
}
