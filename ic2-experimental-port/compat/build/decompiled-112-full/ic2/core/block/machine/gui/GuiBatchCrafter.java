/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import ic2.core.GuiIC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.container.ContainerBatchCrafter;
import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.ItemStackImage;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiBatchCrafter
extends GuiIC2<ContainerBatchCrafter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIBatchCrafter.png");

    public GuiBatchCrafter(ContainerBatchCrafter container) {
        super(container, 206);
        this.addElement(EnergyGauge.asBolt(this, 12, 45, (TileEntityBlock)container.base));
        this.addElement(new LinkedGauge(this, 90, 35, (IGuiValueProvider)container.base, "progress", Gauge.GaugeStyle.ProgressArrow));
        this.addElement(new ItemStackImage(this, 94, 14, new Supplier<ItemStack>(){

            public ItemStack get() {
                return StackUtil.wrapEmpty(((TileEntityBatchCrafter)((ContainerBatchCrafter)((GuiBatchCrafter)GuiBatchCrafter.this).container).base).recipeOutput);
            }
        }));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

