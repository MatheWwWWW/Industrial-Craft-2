/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.wiring;

import com.google.common.base.Supplier;
import ic2.core.GuiIC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.VanillaButton;
import ic2.core.init.Localization;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiElectricBlock
extends GuiIC2<ContainerElectricBlock> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIElectricBlock.png");

    public GuiElectricBlock(final ContainerElectricBlock container) {
        super(container, 196);
        this.addElement(EnergyGauge.asBar(this, 79, 38, (TileEntityBlock)container.base));
        this.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(this, 152, 4, 20, 20, this.createEventSender(0)).withIcon(new Supplier<ItemStack>(){

            public ItemStack get() {
                return new ItemStack(Items.field_151137_ax);
            }
        })).withTooltip(new Supplier<String>(){

            public String get() {
                return ((TileEntityElectricBlock)container.base).getRedstoneMode();
            }
        }));
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.EUStorage.gui.info.armor"), 8, this.field_147000_g - 126 + 3, 0x404040);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.EUStorage.gui.info.level"), 79, 25, 0x404040);
        int e = (int)Math.min(((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).energy.getEnergy(), ((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).energy.getCapacity());
        this.field_146289_q.func_78276_b(" " + e, 110, 35, 0x404040);
        this.field_146289_q.func_78276_b("/" + (int)((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).energy.getCapacity(), 110, 45, 0x404040);
        String output = Localization.translate("ic2.EUStorage.gui.info.output", ((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).output);
        this.field_146289_q.func_78276_b(output, 85, 60, 0x404040);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

