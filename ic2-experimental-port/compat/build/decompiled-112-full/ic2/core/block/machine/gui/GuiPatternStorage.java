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
import ic2.core.block.machine.container.ContainerPatternStorage;
import ic2.core.block.machine.tileentity.TileEntityPatternStorage;
import ic2.core.gui.CustomButton;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.ItemImage;
import ic2.core.gui.Text;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import ic2.core.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiPatternStorage
extends GuiIC2<ContainerPatternStorage> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIPatternStorage.png");

    public GuiPatternStorage(final ContainerPatternStorage container) {
        super(container);
        this.addElement((GuiElement<?>)new CustomButton(this, 7, 19, 9, 18, this.createEventSender(0)).withTooltip("ic2.PatternStorage.gui.info.last"));
        this.addElement((GuiElement<?>)new CustomButton(this, 36, 19, 9, 18, this.createEventSender(1)).withTooltip("ic2.PatternStorage.gui.info.next"));
        this.addElement((GuiElement<?>)new CustomButton(this, 10, 37, 16, 8, this.createEventSender(2)).withTooltip("ic2.PatternStorage.gui.info.export"));
        this.addElement((GuiElement<?>)new CustomButton(this, 26, 37, 16, 8, this.createEventSender(3)).withTooltip("ic2.PatternStorage.gui.info.import"));
        this.addElement(Text.create(this, this.field_146999_f / 2, 30, TextProvider.of(new Supplier<String>(){

            public String get() {
                TileEntityPatternStorage te = (TileEntityPatternStorage)container.base;
                return Math.min(te.index + 1, te.maxIndex) + " / " + te.maxIndex;
            }
        }), 0x404040, false, true, false));
        this.addElement(Text.create(this, 10, 48, TextProvider.ofTranslated("ic2.generic.text.Name"), 0xFFFFFF, false));
        this.addElement(Text.create(this, 10, 59, TextProvider.ofTranslated("ic2.generic.text.UUMatte"), 0xFFFFFF, false));
        this.addElement(Text.create(this, 10, 70, TextProvider.ofTranslated("ic2.generic.text.Energy"), 0xFFFFFF, false));
        IEnableHandler patternInfoEnabler = new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityPatternStorage)container.base).pattern != null;
            }
        };
        this.addElement((GuiElement<?>)Text.create(this, 80, 48, TextProvider.of(new Supplier<String>(){

            public String get() {
                ItemStack pattern = ((TileEntityPatternStorage)container.base).pattern;
                return pattern != null ? pattern.func_82833_r() : null;
            }
        }), 0xFFFFFF, false).withEnableHandler(patternInfoEnabler));
        this.addElement((GuiElement<?>)Text.create(this, 80, 59, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Util.toSiString(((TileEntityPatternStorage)container.base).patternUu, 4) + Localization.translate("ic2.generic.text.bucketUnit");
            }
        }), 0xFFFFFF, false).withEnableHandler(patternInfoEnabler));
        this.addElement((GuiElement<?>)Text.create(this, 80, 70, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Util.toSiString(((TileEntityPatternStorage)container.base).patternEu, 4) + Localization.translate("ic2.generic.text.EU");
            }
        }), 0xFFFFFF, false).withEnableHandler(patternInfoEnabler));
        this.addElement(new ItemImage(this, 152, 29, new Supplier<ItemStack>(){

            public ItemStack get() {
                return ((TileEntityPatternStorage)container.base).pattern;
            }
        }));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

