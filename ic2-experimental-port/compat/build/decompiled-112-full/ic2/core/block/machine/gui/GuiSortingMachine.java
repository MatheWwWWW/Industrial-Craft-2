/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import ic2.core.GuiIC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.container.ContainerSortingMachine;
import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
import ic2.core.gui.CustomButton;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.FixedSizeOverlaySupplier;
import ic2.core.gui.GuiElement;
import ic2.core.gui.Image;
import ic2.core.util.StackUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class GuiSortingMachine
extends GuiIC2<ContainerSortingMachine> {
    private static final ResourceLocation texture = new ResourceLocation("ic2", "textures/gui/GUISortingMachine.png");

    public GuiSortingMachine(final ContainerSortingMachine container) {
        super(container, 212, 243);
        this.addElement(EnergyGauge.asBolt(this, 174, 220, (TileEntityBlock)container.base));
        EnumFacing[] enumFacingArray = EnumFacing.field_82609_l;
        int n = enumFacingArray.length;
        for (int i = 0; i < n; ++i) {
            EnumFacing dir;
            final EnumFacing cDir = dir = enumFacingArray[i];
            this.addElement(Image.create(this, 60, 18 + dir.ordinal() * 20, 18, 18, texture, 256, 256, new FixedSizeOverlaySupplier(18){

                @Override
                public int getUS() {
                    return 212;
                }

                @Override
                public int getVS() {
                    if (StackUtil.getAdjacentInventory((TileEntity)container.base, cDir) != null) {
                        return 15;
                    }
                    return 33;
                }
            }));
            this.addElement((GuiElement<?>)new CustomButton(this, 42, 18 + dir.ordinal() * 20, 18, 18, new FixedSizeOverlaySupplier(18){

                @Override
                public int getUS() {
                    return 230;
                }

                @Override
                public int getVS() {
                    if (((TileEntitySortingMachine)container.base).defaultRoute != cDir) {
                        return 15;
                    }
                    return 33;
                }
            }, texture, this.createEventSender(dir.ordinal())).withTooltip(new Supplier<String>(){

                public String get() {
                    if (((TileEntitySortingMachine)container.base).defaultRoute != cDir) {
                        return "ic2.SortingMachine.whitelist";
                    }
                    return "ic2.SortingMachine.default";
                }
            }));
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return texture;
    }
}

