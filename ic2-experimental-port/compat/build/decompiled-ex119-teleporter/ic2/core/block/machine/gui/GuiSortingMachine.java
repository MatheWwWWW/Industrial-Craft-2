/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerSortingMachine;
import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.CustomButton;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.FixedSizeOverlaySupplier;
import ic2.core.gui.GuiElement;
import ic2.core.gui.Image;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GuiSortingMachine
extends Ic2Gui<ContainerSortingMachine> {
    private static final ResourceLocation texture = new ResourceLocation("ic2", "textures/gui/guisortingmachine.png");

    public GuiSortingMachine(final ContainerSortingMachine containerSortingMachine, Inventory inventory, Component component) {
        super(containerSortingMachine, inventory, component, 212, 243);
        this.addElement(EnergyGauge.asBolt(this, 174, 220, (Ic2TileEntity)containerSortingMachine.base));
        Direction[] directionArray = Util.ALL_DIRS;
        int n = directionArray.length;
        for (int i = 0; i < n; ++i) {
            Direction direction;
            final Direction direction2 = direction = directionArray[i];
            this.addElement(Image.create(this, 60, 18 + direction.ordinal() * 20, 18, 18, texture, 256, 256, new FixedSizeOverlaySupplier(18){

                @Override
                public int getUS() {
                    return 212;
                }

                @Override
                public int getVS() {
                    if (StackUtil.ENV.getAdjacentInventory((BlockEntity)containerSortingMachine.base, direction2) != null) {
                        return 15;
                    }
                    return 33;
                }
            }));
            this.addElement((GuiElement<?>)new CustomButton(this, 42, 18 + direction.ordinal() * 20, 18, 18, new FixedSizeOverlaySupplier(18){

                @Override
                public int getUS() {
                    return 230;
                }

                @Override
                public int getVS() {
                    if (((TileEntitySortingMachine)containerSortingMachine.base).defaultRoute != direction2) {
                        return 15;
                    }
                    return 33;
                }
            }, texture, this.createEventSender(direction.ordinal())).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

                public String get() {
                    if (((TileEntitySortingMachine)containerSortingMachine.base).defaultRoute != direction2) {
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

