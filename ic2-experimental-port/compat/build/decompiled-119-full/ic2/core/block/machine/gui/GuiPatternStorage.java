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
import ic2.core.block.machine.container.ContainerPatternStorage;
import ic2.core.block.machine.tileentity.TileEntityPatternStorage;
import ic2.core.gui.CustomButton;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.ItemImage;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import ic2.core.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GuiPatternStorage
extends Ic2Gui<ContainerPatternStorage> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guipatternstorage.png");

    public GuiPatternStorage(final ContainerPatternStorage containerPatternStorage, Inventory inventory, Component component) {
        super(containerPatternStorage, inventory, component);
        this.addElement((GuiElement<?>)new CustomButton(this, 7, 19, 9, 18, this.createEventSender(0)).withTooltip("ic2.PatternStorage.gui.info.last"));
        this.addElement((GuiElement<?>)new CustomButton(this, 36, 19, 9, 18, this.createEventSender(1)).withTooltip("ic2.PatternStorage.gui.info.next"));
        this.addElement((GuiElement<?>)new CustomButton(this, 10, 37, 16, 8, this.createEventSender(2)).withTooltip("ic2.PatternStorage.gui.info.export"));
        this.addElement((GuiElement<?>)new CustomButton(this, 26, 37, 16, 8, this.createEventSender(3)).withTooltip("ic2.PatternStorage.gui.info.import"));
        this.addElement(TextLabel.create(this, this.f_97726_ / 2, 30, TextProvider.of(new Supplier<String>(){

            public String get() {
                TileEntityPatternStorage tileEntityPatternStorage = (TileEntityPatternStorage)containerPatternStorage.base;
                return Math.min(tileEntityPatternStorage.index + 1, tileEntityPatternStorage.maxIndex) + " / " + tileEntityPatternStorage.maxIndex;
            }
        }), 0x404040, false, true, false));
        this.addElement(TextLabel.create(this, 10, 48, TextProvider.ofTranslated("ic2.generic.text.Name"), 0xFFFFFF, false));
        this.addElement(TextLabel.create(this, 10, 59, TextProvider.ofTranslated("ic2.generic.text.UUMatte"), 0xFFFFFF, false));
        this.addElement(TextLabel.create(this, 10, 70, TextProvider.ofTranslated("ic2.generic.text.Energy"), 0xFFFFFF, false));
        IEnableHandler iEnableHandler = new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityPatternStorage)containerPatternStorage.base).pattern != null;
            }
        };
        this.addElement((GuiElement<?>)TextLabel.create(this, 80, 48, TextProvider.of(new Supplier<String>(){

            public String get() {
                ItemStack itemStack = ((TileEntityPatternStorage)containerPatternStorage.base).pattern;
                return itemStack != null ? itemStack.m_41786_().getString() : null;
            }
        }), 0xFFFFFF, false).withEnableHandler(iEnableHandler));
        this.addElement((GuiElement<?>)TextLabel.create(this, 80, 59, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Util.toSiString(((TileEntityPatternStorage)containerPatternStorage.base).patternUu, 4) + Localization.translate("ic2.generic.text.bucketUnit");
            }
        }), 0xFFFFFF, false).withEnableHandler(iEnableHandler));
        this.addElement((GuiElement<?>)TextLabel.create(this, 80, 70, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Util.toSiString(((TileEntityPatternStorage)containerPatternStorage.base).patternEu, 4) + Localization.translate("ic2.generic.text.EU");
            }
        }), 0xFFFFFF, false).withEnableHandler(iEnableHandler));
        this.addElement(new ItemImage(this, 152, 29, (java.util.function.Supplier<ItemStack>)new Supplier<ItemStack>(){

            public ItemStack get() {
                return ((TileEntityPatternStorage)containerPatternStorage.base).pattern;
            }
        }));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

