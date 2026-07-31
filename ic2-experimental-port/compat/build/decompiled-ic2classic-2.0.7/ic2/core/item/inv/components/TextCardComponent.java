/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.world.item.DyeColor
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.components;

import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ColorButton;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.inventory.gui.components.simple.TextFieldComponent;
import ic2.core.item.inv.inventory.TextCardInventory;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TextCardComponent
extends GuiWidget {
    private static final Box2i SLIDER_POS = new Box2i(156, 26, 12, 67);
    private static final Box2i SLIDER_TEXTURE = new Box2i(176, 0, 12, 15);
    private static final Box2i TEXT_BOUNDS = new Box2i(8, 26, 140, 67);
    TextCardInventory inventory;
    TextFieldComponent text = this.addChild(new TextFieldComponent(TEXT_BOUNDS, this::getText, this::setText, T -> T.length() < 50000)).setFocused(true);
    SliderComponent slider = this.addChild(new SliderComponent(SLIDER_POS, SLIDER_TEXTURE)).setListener(this.text::setOffset);

    public TextCardComponent(TextCardInventory inventory) {
        super(Box2i.EMPTY_BOX);
        this.inventory = inventory;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ColorButton(x + 153, y + 8, 16, 16, this.inventory.color, this::updatePaint)).setToolTip("Text Color");
        this.slider.setMax(this.text.getMaxLanes() - this.text.getVisibleLanes());
        this.slider.setCurrent(this.text.getOffset());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        this.slider.setMax(this.text.getMaxLanes() - this.text.getVisibleLanes());
        this.slider.setCurrent(this.text.getOffset());
    }

    private String getText() {
        return this.inventory.text;
    }

    @OnlyIn(value=Dist.CLIENT)
    private void updatePaint(ColorButton button) {
        this.handleColorClick(button.getColor(), (AbstractWidget)button, false, T -> {
            this.inventory.color = T;
            button.setColor((DyeColor)T);
            IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 0, T.m_41060_());
        });
    }

    private void setText(String s) {
        if (this.inventory.text.equals(s)) {
            return;
        }
        if (s.startsWith(this.inventory.text)) {
            IC2.NETWORKING.get(false).sendClientItemBuffer(this.inventory.getInventoryStack(), "text", new StringDataBuffer(0, s.substring(this.inventory.text.length())));
        } else if (this.inventory.text.startsWith(s)) {
            IC2.NETWORKING.get(false).sendClientItemBuffer(this.inventory.getInventoryStack(), "text", new StringDataBuffer(1, this.inventory.text.substring(s.length())));
        } else {
            IC2.NETWORKING.get(false).sendClientItemBuffer(this.inventory.getInventoryStack(), "text", new StringDataBuffer(2, s));
        }
        this.inventory.text = s;
    }
}

