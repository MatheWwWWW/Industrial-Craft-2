/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.personal;

import ic2.core.GuiIC2;
import ic2.core.block.personal.ContainerTradeOMatClosed;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.init.Localization;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiTradeOMatClosed
extends GuiIC2<ContainerTradeOMatClosed> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUITradeOMatClosed.png");

    public GuiTradeOMatClosed(ContainerTradeOMatClosed container) {
        super(container);
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        this.field_146289_q.func_78276_b(Localization.translate("container.inventory"), 8, this.field_147000_g - 96 + 2, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.want"), 12, 23, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.offer"), 12, 42, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.stock"), 12, 60, 0x404040);
        this.field_146289_q.func_78276_b(((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.container).base).stock < 0 ? "\u221e" : "" + ((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.container).base).stock, 50, 60, ((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.container).base).stock != 0 ? 0x404040 : 0xFF5555);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

