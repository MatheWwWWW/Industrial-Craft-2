/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.mv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.block.machines.tiles.mv.BaseTeleporterTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.networking.buffers.data.BaseTeleporterBuffer;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class BaseTeleporterComponent
extends GuiWidget {
    BaseTeleporterTileEntity tile;
    SliderComponent slider = this.addChild(new SliderComponent(new Box2i(154, 33, 11, 127), new Box2i(0, 166, 12, 15))).setNonEmptyRows(8);
    boolean open = false;

    public BaseTeleporterComponent(BaseTeleporterTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.clearFlag(1);
        int limit = this.tile.targetList.size();
        List list = (List)this.tile.targetList.getCasted();
        boolean powered = this.tile.isActive();
        for (int i = 0; i < 8; ++i) {
            int index = i;
            ExtendedButton button = new ExtendedButton(x + 15, y + i * 15 + 34, 134, 14, (Component)(list.size() > i ? this.string(((BaseTeleporterTileEntity.LocalTarget)list.get(i)).getName()) : this.translate("gui.ic2.base_teleporter.unknown")), T -> this.teleport(index));
            gui.addRenderableWidget(i, button);
            button.f_93624_ = i < limit;
            button.f_93623_ = powered;
        }
        gui.addRenderableWidget(100, new ToolTipButton(x + 154, y + 4, 12, 12, (Component)this.string("S"), T -> this.toggle(gui)).setToolTip("gui.ic2.base_teleporter.settings"));
        EditBox text = new EditBox(gui.getFont(), x - 93, y + 19, 90, 10, null, (Component)this.string());
        text.m_94190_(true);
        text.m_94182_(false);
        text.m_94144_(this.tile.name);
        text.f_93624_ = this.open;
        gui.addRenderableWidget(101, text);
        text = new EditBox(gui.getFont(), x - 93, y + 47, 90, 10, null, (Component)this.string());
        text.m_94190_(true);
        text.m_94182_(false);
        text.m_94144_(this.tile.networkID);
        text.f_93624_ = this.open;
        gui.addRenderableWidget(102, text);
        gui.addRenderableWidget((int)103, new ExtendedButton((int)(x - 96), (int)(y + 60), (int)45, (int)14, (Component)this.translate((String)"gui.ic2.base_teleporter.apply"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(ic2.core.inventory.gui.IC2Screen net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((BaseTeleporterComponent)this, (IC2Screen)gui))).f_93624_ = this.open;
        gui.addRenderableWidget((int)104, new ExtendedButton((int)(x - 49), (int)(y + 60), (int)45, (int)14, (Component)this.translate((String)"gui.ic2.base_teleporter.cancel"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(ic2.core.inventory.gui.IC2Screen net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((BaseTeleporterComponent)this, (IC2Screen)gui))).f_93624_ = this.open;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void apply(IC2Screen gui) {
        String network;
        String name = gui.getCastedButton(101, EditBox.class).m_94155_();
        int flag = 0;
        if (!name.equalsIgnoreCase(this.tile.m_7770_().getString())) {
            flag = 2;
        }
        if (!(network = gui.getCastedButton(102, EditBox.class).m_94155_()).equals(this.tile.networkID)) {
            ++flag;
        }
        this.tile.sendToServer("", new BaseTeleporterBuffer(network, name, (byte)flag));
        this.toggle(gui);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void cancel(IC2Screen gui) {
        gui.getCastedButton(101, EditBox.class).m_94144_(this.tile.name);
        gui.getCastedButton(102, EditBox.class).m_94144_(this.tile.networkID);
        this.toggle(gui);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void toggle(IC2Screen gui) {
        boolean bl = this.open = !this.open;
        if (!this.open) {
            AbstractWidget widget = gui.getButton(101);
            if (widget.m_93696_()) {
                widget.m_5755_(false);
            }
            if ((widget = gui.getButton(102)).m_93696_()) {
                widget.m_5755_(false);
            }
        } else {
            gui.getCastedButton(101, EditBox.class).m_94144_(this.tile.name);
            gui.getCastedButton(102, EditBox.class).m_94144_(this.tile.networkID);
        }
        gui.getButton((int)101).f_93624_ = this.open;
        gui.getButton((int)102).f_93624_ = this.open;
        gui.getButton((int)103).f_93624_ = this.open;
        gui.getButton((int)104).f_93624_ = this.open;
    }

    public void teleport(int index) {
        IC2.NETWORKING.get(false).sendClientTileEvent(this.tile, 1, this.slider.getCurrent() + index);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        return this.open && keyCode == 69 && (this.gui.getCastedButton(101, EditBox.class).m_93696_() || this.gui.getCastedButton(102, EditBox.class).m_93696_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        int limit = this.tile.targetList.size();
        boolean powered = this.tile.isActive();
        List list = (List)this.tile.targetList.getCasted();
        for (int i = 0; i < 8; ++i) {
            AbstractWidget widget = gui.getButton(i);
            widget.f_93624_ = limit > this.slider.getCurrent() + i;
            widget.f_93623_ = powered;
            widget.m_93666_((Component)(widget.f_93624_ ? this.string(((BaseTeleporterTileEntity.LocalTarget)list.get(this.slider.getCurrent() + i)).getName()) : this.translate("gui.ic2.base_teleporter.unknown")));
        }
        this.slider.setMax(limit);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        if (this.open) {
            this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() - 100, this.gui.getGuiTop(), 24.0f, 166.0f, 100.0f, 79.0f);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (this.open) {
            this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.base_teleporter.name"), -50, 9, ColorUtils.DARK_GRAY);
            this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.base_teleporter.network"), -50, 37, ColorUtils.DARK_GRAY);
        }
        this.gui.drawCenterString(matrix, (Component)this.string(this.tile.name + ":" + this.tile.networkID), 85, 20, ColorUtils.DARK_GRAY);
    }

    private /* synthetic */ void lambda$init$3(IC2Screen gui, Button T) {
        this.cancel(gui);
    }

    private /* synthetic */ void lambda$init$2(IC2Screen gui, Button T) {
        this.apply(gui);
    }
}

