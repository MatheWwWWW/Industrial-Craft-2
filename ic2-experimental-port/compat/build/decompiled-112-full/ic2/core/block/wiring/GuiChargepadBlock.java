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
import ic2.core.block.wiring.ContainerChargepadBlock;
import ic2.core.block.wiring.TileEntityChargepadBlock;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.Text;
import ic2.core.gui.VanillaButton;
import ic2.core.gui.dynamic.TextProvider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiChargepadBlock
extends GuiIC2<ContainerChargepadBlock> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIChargepadBlock.png");

    public GuiChargepadBlock(final ContainerChargepadBlock container) {
        super(container, 161);
        this.addElement(EnergyGauge.asBar(this, 79, 38, (TileEntityBlock)container.base));
        this.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(this, 152, 4, 20, 20, this.createEventSender(0)).withIcon(new Supplier<ItemStack>(){

            public ItemStack get() {
                return new ItemStack(Items.field_151137_ax);
            }
        })).withTooltip(new Supplier<String>(){

            public String get() {
                return ((TileEntityChargepadBlock)container.base).getRedstoneMode();
            }
        }));
        this.addElement(Text.create(this, 79, 25, TextProvider.ofTranslated("ic2.EUStorage.gui.info.level"), 0x404040, false));
        this.addElement(Text.create(this, 110, 35, TextProvider.of(new Supplier<String>(){

            public String get() {
                return " " + (int)Math.min(((TileEntityChargepadBlock)container.base).energy.getEnergy(), ((TileEntityChargepadBlock)container.base).energy.getCapacity());
            }
        }), 0x404040, false));
        this.addElement(Text.create(this, 110, 45, TextProvider.of(new Supplier<String>(){

            public String get() {
                return "/" + (int)((TileEntityChargepadBlock)container.base).energy.getCapacity();
            }
        }), 0x404040, false));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

