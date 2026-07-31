/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.personal;

import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.block.personal.ContainerTradeOMatOpen;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.init.Localization;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiTradeOMatOpen
extends GuiIC2<ContainerTradeOMatOpen> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUITradeOMatOpen.png");
    private final boolean isAdmin;

    public GuiTradeOMatOpen(ContainerTradeOMatOpen container, boolean isAdmin) {
        super(container);
        this.isAdmin = isAdmin;
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        if (this.isAdmin) {
            this.field_146292_n.add(new GuiButton(0, (this.field_146294_l - this.field_146999_f) / 2 + 152, (this.field_146295_m - this.field_147000_g) / 2 + 4, 20, 20, "\u221e"));
        }
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        this.field_146289_q.func_78276_b(Localization.translate("container.inventory"), 8, this.field_147000_g - 96 + 2, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.want"), 12, 23, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.offer"), 12, 57, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.totalTrades0"), 108, 28, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.totalTrades1"), 108, 36, 0x404040);
        this.field_146289_q.func_78276_b("" + ((TileEntityTradeOMat)((ContainerTradeOMatOpen)this.container).base).totalTradeCount, 112, 44, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.stock") + " " + (((TileEntityTradeOMat)((ContainerTradeOMatOpen)this.container).base).stock < 0 ? "\u221e" : "" + ((TileEntityTradeOMat)((ContainerTradeOMatOpen)this.container).base).stock), 108, 60, 0x404040);
    }

    protected void func_146284_a(GuiButton guibutton) throws IOException {
        super.func_146284_a(guibutton);
        if (guibutton.field_146127_k == 0) {
            IC2.network.get(false).initiateClientTileEntityEvent((TileEntity)((ContainerTradeOMatOpen)this.container).base, 0);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

