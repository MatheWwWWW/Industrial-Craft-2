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
import ic2.core.block.personal.ContainerEnergyOMatClosed;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.init.Localization;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiEnergyOMatClosed
extends GuiIC2<ContainerEnergyOMatClosed> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIEnergyOMatClosed.png");

    public GuiEnergyOMatClosed(ContainerEnergyOMatClosed container) {
        super(container);
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        this.field_146289_q.func_78276_b(Localization.translate("container.inventory"), 8, this.field_147000_g - 96 + 2, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.want"), 12, 21, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.offer"), 12, 39, 0x404040);
        this.field_146289_q.func_78276_b(((TileEntityEnergyOMat)((ContainerEnergyOMatClosed)this.container).base).euOffer + " EU", 50, 39, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTraderEnergy.paidFor", ((TileEntityEnergyOMat)((ContainerEnergyOMatClosed)this.container).base).paidFor), 12, 57, 0x404040);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

