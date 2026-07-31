/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerMetalFormer;
import ic2.core.block.machine.tileentity.TileEntityMetalFormer;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.CustomGauge;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.RecipeButton;
import ic2.core.gui.VanillaButton;
import ic2.core.init.Localization;
import ic2.core.ref.Ic2Items;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GuiMetalFormer
extends Ic2Gui<ContainerMetalFormer> {
    public GuiMetalFormer(final ContainerMetalFormer containerMetalFormer, Inventory inventory, Component component) {
        super(containerMetalFormer, inventory, component);
        this.addElement(EnergyGauge.asBolt(this, 20, 37, (Ic2TileEntity)containerMetalFormer.base));
        this.addElement(CustomGauge.create(this, 52, 39, new CustomGauge.IGaugeRatioProvider(){

            @Override
            public double getRatio() {
                return ((TileEntityMetalFormer)containerMetalFormer.base).getProgress();
            }
        }, Gauge.GaugeStyle.ProgressMetalFormer));
        this.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(this, 65, 53, 20, 20, this.createEventSender(0)).withIcon((java.util.function.Supplier<ItemStack>)new Supplier<ItemStack>(){

            public ItemStack get() {
                switch (((TileEntityMetalFormer)containerMetalFormer.base).getMode()) {
                    case 0: {
                        return new ItemStack((ItemLike)Ic2Items.COPPER_CABLE);
                    }
                    case 1: {
                        return new ItemStack((ItemLike)Ic2Items.FORGE_HAMMER);
                    }
                    case 2: {
                        return new ItemStack((ItemLike)Ic2Items.CUTTER);
                    }
                }
                return null;
            }
        })).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                switch (((TileEntityMetalFormer)containerMetalFormer.base).getMode()) {
                    case 0: {
                        return Localization.translate("ic2.MetalFormer.gui.switch.Extruding");
                    }
                    case 1: {
                        return Localization.translate("ic2.MetalFormer.gui.switch.Rolling");
                    }
                    case 2: {
                        return Localization.translate("ic2.MetalFormer.gui.switch.Cutting");
                    }
                }
                return null;
            }
        }));
        if (RecipeButton.canUse()) {
            int n = 0;
            while (n < 3) {
                final int n2 = n++;
                this.addElement((GuiElement<?>)new RecipeButton(this, 52, 39, 46, 9, new String[]{"metal_former" + n2}).withEnableHandler(new IEnableHandler(){

                    @Override
                    public boolean isEnabled() {
                        return ((TileEntityMetalFormer)containerMetalFormer.base).getMode() == n2;
                    }
                }));
            }
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guimetalformer.png");
    }
}

