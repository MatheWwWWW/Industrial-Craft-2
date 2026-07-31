/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.block.wiring;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.block.wiring.ContainerChargepadBlock;
import ic2.core.block.wiring.tileentity.TileEntityChargepadBlock;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.TextLabel;
import ic2.core.gui.VanillaButton;
import ic2.core.gui.dynamic.TextProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

@Environment(value=EnvType.CLIENT)
public class GuiChargepadBlock
extends Ic2Gui<ContainerChargepadBlock> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guichargepadblock.png");

    public GuiChargepadBlock(final ContainerChargepadBlock containerChargepadBlock, Inventory inventory, Component component) {
        super(containerChargepadBlock, inventory, component, 161);
        this.addElement(EnergyGauge.asBar(this, 79, 38, (Ic2TileEntity)containerChargepadBlock.base));
        this.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(this, 152, 4, 20, 20, this.createEventSender(0)).withIcon((java.util.function.Supplier<ItemStack>)new Supplier<ItemStack>(){

            public ItemStack get() {
                return new ItemStack((ItemLike)Items.f_42451_);
            }
        })).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                return ((TileEntityChargepadBlock)containerChargepadBlock.base).getRedstoneMode();
            }
        }));
        this.addElement(TextLabel.create(this, 79, 25, TextProvider.ofTranslated("ic2.EUStorage.gui.info.level"), 0x404040, false));
        this.addElement(TextLabel.create(this, 110, 35, TextProvider.of(new Supplier<String>(){

            public String get() {
                return " " + (int)Math.min(((TileEntityChargepadBlock)containerChargepadBlock.base).energy.getEnergy(), ((TileEntityChargepadBlock)containerChargepadBlock.base).energy.getCapacity());
            }
        }), 0x404040, false));
        this.addElement(TextLabel.create(this, 110, 45, TextProvider.of(new Supplier<String>(){

            public String get() {
                return "/" + (int)((TileEntityChargepadBlock)containerChargepadBlock.base).energy.getCapacity();
            }
        }), 0x404040, false));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

