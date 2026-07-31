/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.gui;

import ic2.core.GuiIC2;
import ic2.core.block.machine.container.ContainerMatter;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.gui.TankGauge;
import ic2.core.init.Localization;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiMatter
extends GuiIC2<ContainerMatter> {
    public String progressLabel;
    public String amplifierLabel;

    public GuiMatter(ContainerMatter container) {
        super(container);
        this.addElement(TankGauge.createNormal(this, 96, 22, (IFluidTank)((TileEntityMatter)container.base).fluidTank));
        this.progressLabel = Localization.translate("ic2.Matter.gui.info.progress");
        this.amplifierLabel = Localization.translate("ic2.Matter.gui.info.amplifier");
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        this.field_146289_q.func_78276_b(this.progressLabel, 8, 22, 0x404040);
        this.field_146289_q.func_78276_b(((TileEntityMatter)((ContainerMatter)this.container).base).getProgressAsString(), 18, 31, 0x404040);
        if (((TileEntityMatter)((ContainerMatter)this.container).base).scrap > 0) {
            this.field_146289_q.func_78276_b(this.amplifierLabel, 8, 46, 0x404040);
            this.field_146289_q.func_78276_b("" + ((TileEntityMatter)((ContainerMatter)this.container).base).scrap, 8, 58, 0x404040);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/GUIMatter.png");
    }
}

