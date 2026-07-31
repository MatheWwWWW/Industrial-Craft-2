/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.gui;

import ic2.core.GuiIC2;
import ic2.core.block.BlockIC2Fence;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.container.ContainerMagnetizer;
import ic2.core.gui.EnergyGauge;
import ic2.core.init.Localization;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiMagnetizer
extends GuiIC2<ContainerMagnetizer> {
    public GuiMagnetizer(ContainerMagnetizer container) {
        super(container);
        this.addElement(EnergyGauge.asBolt(this, 11, 28, (TileEntityBlock)container.base));
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/GUIMagnetizer.png");
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        if (BlockIC2Fence.hasMetalShoes(((ContainerMagnetizer)this.container).player)) {
            this.field_146289_q.func_78276_b(Localization.translate("ic2.Magnetizer.gui.hasMetalShoes"), 18, 66, 0x40FF40);
        } else {
            this.field_146289_q.func_78276_b(Localization.translate("ic2.Magnetizer.gui.noMetalShoes"), 18, 66, 0xFF4040);
        }
    }
}

