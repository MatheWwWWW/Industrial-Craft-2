package ru.mot.ic2exfidelity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.ItemImage;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollableList;
import ic2.core.ref.Ic2Items;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

/** Preserves the intentionally unfinished/debug Trading Terminal screen from 2.8.222. */
public final class LegacyGuiTradingTerminal extends Ic2Gui<LegacyContainerTradingTerminal> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("ic2", "textures/gui/gui_trading_terminal.png");
    private final ScrollableList list = new ScrollableList(this, 4, 20, 168, 99);

    public LegacyGuiTradingTerminal(
            LegacyContainerTradingTerminal menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 227);
        addElement(list);
        addElement(new ItemImage(this, 156, 4, () -> new ItemStack(Ic2Items.WRENCH)) {
            private int count = 1;

            @Override
            protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
                switch (button) {
                    case left -> {
                        int item = count++;
                        LegacyGuiTradingTerminal.this.list.addItem(new TerminalListItem(item));
                    }
                    case right -> {
                        if (count > 1) {
                            LegacyGuiTradingTerminal.this.list.removeItem(count-- - 2);
                        }
                    }
                }
                return true;
            }
        }.withTooltip("Settings"));
    }

    @Override
    protected void drawBackgroundAndTitle(
            PoseStack pose, float partialTick, int mouseX, int mouseY) {
        bindTexture();
        m_93228_(pose, f_97735_, f_97736_, 0, 0, f_97726_, f_97727_);
        drawXCenteredString(pose, f_97726_ / 2, 8, f_96539_, 0x404040, false);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    private final class TerminalListItem implements ScrollableList.IListItem {
        private final int item;

        private TerminalListItem(int item) {
            this.item = item;
        }

        @Override
        public void draw(
                PoseStack pose, int x, int y, int width, int height, int mouseX, int mouseY) {
            drawString(pose, x + 2, y + 1, "Trader " + item, 0xFFFFFF, false);
        }

        @Override
        public boolean onClick(MouseButton button, int mouseX, int mouseY) {
            System.out.println(item + " clicked with " + button);
            return false;
        }
    }
}
