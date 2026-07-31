/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.storage.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.util.DirectionList;
import ic2.core.block.storage.tiles.RedirectorMasterTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.networking.buffers.data.NodeBuffer;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class MasterComponent
extends GuiWidget {
    public static final Predicate<String> PREDICATE = T -> {
        try {
            int value = Integer.parseInt(T);
            return value >= 0 && value <= 100;
        }
        catch (Exception exception) {
            return T.length() <= 0;
        }
    };
    public static final Predicate<String> NUMBER_PREDICATE = T -> {
        try {
            return Integer.parseInt(T) != 0;
        }
        catch (Exception exception) {
            return T.length() <= 0;
        }
    };
    RedirectorMasterTileEntity tile;

    public MasterComponent(RedirectorMasterTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        DirectionList dirs = DirectionList.ofNumber(this.tile.sides);
        for (int i = 0; i < 6; ++i) {
            boolean enabled = i != this.tile.getFacing().m_122411_() && dirs.contains(Direction.m_122376_((int)i));
            EditBox widget = new EditBox(gui.getFont(), x + 41, y + i * 14 + 19, 54, 14, (Component)this.string());
            widget.m_94144_(enabled ? Integer.toString(this.tile.shares[i]) : "0");
            widget.m_94190_(true);
            widget.m_94182_(false);
            widget.m_94199_(3);
            widget.m_94153_(PREDICATE);
            widget.m_94186_(enabled);
            gui.addRenderableWidget(i, widget);
        }
        gui.addRenderableWidget(7, new ExtendedButton(x + 15, y + 114, 40, 12, (Component)this.translate("gui.ic2.master_node.apply"), T -> this.confirmData(gui)));
        EditBox widget = new EditBox(gui.getFont(), x + 66, y + 116, 54, 14, (Component)this.string());
        widget.m_94153_(NUMBER_PREDICATE);
        widget.m_94144_("128");
        widget.m_94190_(true);
        widget.m_94182_(false);
        widget.m_94199_(4);
        gui.addRenderableWidget(8, widget);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        for (int i = 0; i < 6; ++i) {
            this.gui.drawTextureRegion(matrix, x + 40, y + i * 14 + 17, 0.0f, 133.0f, 20.0f, 12.0f);
        }
        this.gui.drawTextureRegion(matrix, x + 65, y + 114, 0.0f, 145.0f, 38.0f, 12.0f);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int total = 0;
        float[] entry = new float[6];
        for (Direction dir : DirectionList.ALL) {
            this.gui.drawRightString(matrix, (Component)DirectionList.getName(dir), 35, 14 * dir.m_122411_() + 20, 0x404040);
            String s = this.gui.getCastedButton(dir.m_122411_(), EditBox.class).m_94155_();
            entry[dir.m_122411_()] = s.length() <= 0 ? 0.0f : (float)Integer.parseInt(s);
            total = (int)((float)total + entry[dir.m_122411_()]);
        }
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.master_node.average", total), 35, 105, 0x404040);
        this.gui.getButton((int)7).f_93623_ = total == 100;
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.master_node.eu"), 73, 105, 0x404040);
        String s = this.gui.getCastedButton(8, EditBox.class).m_94155_();
        int eu = s.length() <= 0 ? 0 : Integer.parseInt(s);
        for (int i = 0; i < 6; ++i) {
            int myEU = (int)Math.ceil((float)eu * (entry[i] / (float)total));
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.master_node.eu_average", entry[i] == 0.0f ? 0 : myEU), 61, 14 * i + 19, 0x404040);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void confirmData(IC2Screen gui) {
        float total = 0.0f;
        int[] data = new int[6];
        for (int i = 0; i < 6; ++i) {
            String s = gui.getCastedButton(i, EditBox.class).m_94155_();
            data[i] = s.length() <= 0 ? 0 : Integer.parseInt(s);
            total += (float)data[i];
        }
        if (total != 100.0f) {
            return;
        }
        this.tile.sendToServer("", new NodeBuffer(data));
    }
}

