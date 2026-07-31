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
package ic2.core.block.wiring;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.wiring.ContainerTransformer;
import ic2.core.block.wiring.tileentity.TileEntityTransformer;
import ic2.core.gui.GuiElement;
import ic2.core.gui.ItemImage;
import ic2.core.gui.TextLabel;
import ic2.core.gui.VanillaButton;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import ic2.core.ref.Ic2Items;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GuiTransformer
extends Ic2Gui<ContainerTransformer> {
    public String[] mode = new String[]{"", "", "", ""};
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guitransfomer.png");

    public GuiTransformer(ContainerTransformer containerTransformer, Inventory inventory, Component component) {
        super(containerTransformer, inventory, component, 219);
        this.addElement(TextLabel.create(this, 6, 56, TextProvider.ofTranslated("ic2.Transformer.gui.Output"), 0x404040, true));
        this.addElement(TextLabel.create(this, 6, 70, TextProvider.ofTranslated("ic2.Transformer.gui.Input"), 0x404040, true));
        this.addElement(TextLabel.create(this, 52, 56, TextProvider.of((Supplier<String>)((Supplier)() -> ((TileEntityTransformer)((ContainerTransformer)this.f_97732_).base).getoutputflow() + " " + Localization.translate("ic2.generic.text.EUt"))), 2157374, true));
        this.addElement(TextLabel.create(this, 52, 72, TextProvider.of((Supplier<String>)((Supplier)() -> ((TileEntityTransformer)((ContainerTransformer)this.f_97732_).base).getinputflow() + " " + Localization.translate("ic2.generic.text.EUt"))), 2157374, true));
        this.addElement((GuiElement<?>)new VanillaButton(this, 7, 65, 144, 20, this.createEventSender(0)).withText(Localization.translate("ic2.Transformer.gui.switch.mode1")));
        this.addElement((GuiElement<?>)new VanillaButton(this, 7, 85, 144, 20, this.createEventSender(1)).withText(Localization.translate("ic2.Transformer.gui.switch.mode2")));
        this.addElement((GuiElement<?>)new VanillaButton(this, 7, 105, 144, 20, this.createEventSender(2)).withText(Localization.translate("ic2.Transformer.gui.switch.mode3")));
        this.addElement((GuiElement<?>)new ItemImage(this, 152, 67, () -> new ItemStack((ItemLike)Ic2Items.WRENCH)).withEnableHandler(() -> ((TileEntityTransformer)((ContainerTransformer)this.f_97732_).base).getMode() == TileEntityTransformer.Mode.redstone));
        this.addElement((GuiElement<?>)new ItemImage(this, 152, 87, () -> new ItemStack((ItemLike)Ic2Items.WRENCH)).withEnableHandler(() -> ((TileEntityTransformer)((ContainerTransformer)this.f_97732_).base).getMode() == TileEntityTransformer.Mode.stepdown));
        this.addElement((GuiElement<?>)new ItemImage(this, 152, 107, () -> new ItemStack((ItemLike)Ic2Items.WRENCH)).withEnableHandler(() -> ((TileEntityTransformer)((ContainerTransformer)this.f_97732_).base).getMode() == TileEntityTransformer.Mode.stepup));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

