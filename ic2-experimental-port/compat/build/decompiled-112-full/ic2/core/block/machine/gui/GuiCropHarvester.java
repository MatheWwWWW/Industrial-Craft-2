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
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.container.ContainerCropHarvester;
import ic2.core.gui.EnergyGauge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiCropHarvester
extends GuiIC2<ContainerCropHarvester> {
    public GuiCropHarvester(ContainerCropHarvester container) {
        super(container);
        this.addElement(EnergyGauge.asBolt(this, 19, 37, (TileEntityBlock)container.base));
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/GUICropHarvester.png");
    }
}

