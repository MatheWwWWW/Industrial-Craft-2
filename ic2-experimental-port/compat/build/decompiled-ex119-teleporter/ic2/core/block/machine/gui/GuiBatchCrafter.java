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
import ic2.core.block.machine.container.ContainerBatchCrafter;
import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.ItemStackImage;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.util.StackUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GuiBatchCrafter
extends Ic2Gui<ContainerBatchCrafter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/guibatchcrafter.png");

    public GuiBatchCrafter(ContainerBatchCrafter containerBatchCrafter, Inventory inventory, Component component) {
        super(containerBatchCrafter, inventory, component, 206);
        this.addElement(EnergyGauge.asBolt(this, 12, 45, (Ic2TileEntity)containerBatchCrafter.base));
        this.addElement(new LinkedGauge(this, 90, 35, (IGuiValueProvider)containerBatchCrafter.base, "progress", Gauge.GaugeStyle.ProgressArrow));
        this.addElement(new ItemStackImage(this, 94, 14, new Supplier<ItemStack>(){

            public ItemStack get() {
                return StackUtil.wrapEmpty(((TileEntityBatchCrafter)((ContainerBatchCrafter)((GuiBatchCrafter)GuiBatchCrafter.this).f_97732_).base).recipeOutput);
            }
        }));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

