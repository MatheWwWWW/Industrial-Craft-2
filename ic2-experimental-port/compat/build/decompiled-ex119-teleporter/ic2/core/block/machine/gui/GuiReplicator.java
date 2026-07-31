/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerReplicator;
import ic2.core.block.machine.tileentity.TileEntityReplicator;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.CustomButton;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.ItemImage;
import ic2.core.gui.TankGauge;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import ic2.core.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GuiReplicator
extends Ic2Gui<ContainerReplicator> {
    public GuiReplicator(final ContainerReplicator containerReplicator, Inventory inventory, Component component) {
        super(containerReplicator, inventory, component, 184);
        this.addElement(EnergyGauge.asBolt(this, 136, 84, (Ic2TileEntity)containerReplicator.base));
        this.addElement(TankGauge.createNormal(this, 27, 30, ((TileEntityReplicator)containerReplicator.base).fluidTank));
        this.addElement((GuiElement<?>)new ItemImage(this, 91, 17, (java.util.function.Supplier<ItemStack>)new Supplier<ItemStack>(){

            public ItemStack get() {
                return ((TileEntityReplicator)containerReplicator.base).pattern;
            }
        }).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                TileEntityReplicator tileEntityReplicator = (TileEntityReplicator)containerReplicator.base;
                if (tileEntityReplicator.pattern == null) {
                    return null;
                }
                String string = Util.toSiString(tileEntityReplicator.patternUu, 4) + Localization.translate("ic2.generic.text.bucketUnit");
                String string2 = Util.toSiString(tileEntityReplicator.patternEu, 4) + Localization.translate("ic2.generic.text.EU");
                return tileEntityReplicator.pattern.m_41786_() + " UU: " + string + " EU: " + string2;
            }
        }));
        this.addElement((GuiElement<?>)new CustomButton(this, 80, 16, 9, 18, this.createEventSender(0)).withTooltip("ic2.Replicator.gui.info.last"));
        this.addElement((GuiElement<?>)new CustomButton(this, 109, 16, 9, 18, this.createEventSender(1)).withTooltip("ic2.Replicator.gui.info.next"));
        this.addElement((GuiElement<?>)new CustomButton(this, 75, 82, 16, 16, this.createEventSender(3)).withTooltip("ic2.Replicator.gui.info.Stop"));
        this.addElement((GuiElement<?>)new CustomButton(this, 92, 82, 16, 16, this.createEventSender(4)).withTooltip("ic2.Replicator.gui.info.single"));
        this.addElement((GuiElement<?>)new CustomButton(this, 109, 82, 16, 16, this.createEventSender(5)).withTooltip("ic2.Replicator.gui.info.repeat"));
        this.addElement(TextLabel.create(this, 49, 36, 96, 16, TextProvider.of(new Supplier<String>(){

            public String get() {
                TileEntityReplicator tileEntityReplicator = (TileEntityReplicator)containerReplicator.base;
                if (tileEntityReplicator.getMode() == TileEntityReplicator.Mode.STOPPED) {
                    return Localization.translate("ic2.Replicator.gui.info.Waiting");
                }
                int n = 0;
                int n2 = 0;
                if (tileEntityReplicator.patternUu != 0.0) {
                    n = Math.min((int)Math.round(100.0 * tileEntityReplicator.uuProcessed / tileEntityReplicator.patternUu), 100);
                }
                return String.format("UU:%d%%  EU:%d%%  >%s", n, n2, tileEntityReplicator.getMode() == TileEntityReplicator.Mode.SINGLE ? "" : ">");
            }
        }), () -> ((TileEntityReplicator)containerReplicator.base).getMode() == TileEntityReplicator.Mode.STOPPED ? 15461152 : 2157374, false, 4, 0, false, true));
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guireplicator.png");
    }
}

