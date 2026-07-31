/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.block.wiring;

import com.google.common.base.Supplier;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.tileentity.TileEntityElectricBlock;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.VanillaButton;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class GuiElectricBlock
extends Ic2Gui<ContainerElectricBlock> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guielectricblock.png");

    public GuiElectricBlock(final ContainerElectricBlock containerElectricBlock, Inventory inventory, Component component) {
        super(containerElectricBlock, inventory, component, 196);
        this.addElement(EnergyGauge.asBar(this, 79, 38, (Ic2TileEntity)containerElectricBlock.base));
        this.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(this, 152, 4, 20, 20, this.createEventSender(0)).withIcon((java.util.function.Supplier<ItemStack>)new Supplier<ItemStack>(){

            public ItemStack get() {
                return new ItemStack((ItemLike)Items.f_42451_);
            }
        })).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                return ((TileEntityElectricBlock)containerElectricBlock.base).getRedstoneMode();
            }
        }));
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 8, 74, Localization.translate("ic2.EUStorage.gui.info.armor"), 0x404040);
        this.drawString(poseStack, 79, 40, Localization.translate("ic2.EUStorage.gui.info.level"), 0x404040);
        int n3 = (int)Math.min(((TileEntityElectricBlock)((ContainerElectricBlock)this.f_97732_).base).energy.getEnergy(), ((TileEntityElectricBlock)((ContainerElectricBlock)this.f_97732_).base).energy.getCapacity());
        this.drawString(poseStack, 110, 50, " " + n3, 0x404040);
        this.drawString(poseStack, 110, 60, "/" + (int)((TileEntityElectricBlock)((ContainerElectricBlock)this.f_97732_).base).energy.getCapacity(), 0x404040);
        String string = Localization.translate("ic2.EUStorage.gui.info.output", ((TileEntityElectricBlock)((ContainerElectricBlock)this.f_97732_).base).getOutput());
        this.drawString(poseStack, 85, 75, string, 0x404040);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

