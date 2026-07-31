/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv.planner;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.components.mv.planner.BaseInfoTabComponent;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.networking.buffers.data.StringDataBuffer;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NamingTabComponent
extends BaseInfoTabComponent {
    public static final Predicate<String> NUMBERS_ONLY = T -> {
        try {
            return T.isEmpty() || Integer.parseInt(T) >= 0;
        }
        catch (Exception exception) {
            return false;
        }
    };
    @OnlyIn(value=Dist.CLIENT)
    ImprovedTextWidget naming;
    @OnlyIn(value=Dist.CLIENT)
    ImprovedTextWidget startHeat;
    @OnlyIn(value=Dist.CLIENT)
    ImprovedTextWidget customTime;
    boolean isUpdating = false;

    public NamingTabComponent(ReactorPlannerTileEntity tile) {
        super(178, tile, (Component)Component.m_237115_((String)"gui.ic2.reactor_planner.settings"), new ItemStack((ItemLike)Items.f_42614_), 3);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        super.addRequests(requests);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        this.isUpdating = true;
        this.naming = new ImprovedTextWidget(gui.getFont(), gui.getGuiLeft() + 8, gui.getGuiTop() + 143, 150, 10, (Component)this.string());
        this.naming.m_94144_(this.tile.setupName);
        this.naming.m_94151_(T -> {
            if (!this.isUpdating) {
                this.tile.sendToServer("", new StringDataBuffer(0, (String)T));
            }
        });
        this.naming.m_94190_(true);
        this.naming.setAutoUpdating(true);
        this.naming.f_93624_ = this.isSelected();
        gui.m_142416_(this.naming);
        this.startHeat = new ImprovedTextWidget(gui.getFont(), gui.getGuiLeft() + 8, gui.getGuiTop() + 166, 150, 10, (Component)this.string());
        this.startHeat.m_94144_(Integer.toString(this.tile.getSettings().startingHeat));
        this.startHeat.m_94153_(NUMBERS_ONLY);
        this.startHeat.m_94151_(T -> {
            if (!this.isUpdating) {
                this.tile.sendToServer(11, T.isEmpty() ? 0 : Integer.parseInt(T));
            }
        });
        this.startHeat.m_94190_(true);
        this.startHeat.setAutoUpdating(true);
        this.startHeat.f_93624_ = this.isSelected();
        gui.m_142416_(this.startHeat);
        this.customTime = new ImprovedTextWidget(gui.getFont(), gui.getGuiLeft() + 8, gui.getGuiTop() + 189, 150, 10, (Component)this.string());
        this.customTime.m_94144_(Integer.toString(this.tile.getSettings().maxTicks));
        this.customTime.m_94153_(NUMBERS_ONLY);
        this.customTime.m_94151_(T -> {
            if (!this.isUpdating) {
                this.tile.sendToServer(12, T.isEmpty() ? 0 : Integer.parseInt(T));
            }
        });
        this.customTime.m_94190_(true);
        this.customTime.setAutoUpdating(true);
        this.customTime.f_93624_ = this.isSelected();
        gui.m_142416_(this.customTime);
        this.isUpdating = false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (this.isSelected()) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.settings.name"), 8, 133, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.settings.heat"), 8, 158, 0x404040);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.settings.sim"), 8, 180, 0x404040);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        this.naming.f_93624_ = this.isSelected();
        this.startHeat.f_93624_ = this.isSelected();
        this.customTime.f_93624_ = this.isSelected();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        return this.naming.m_93696_() && keyCode == 69;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onChanged() {
        this.isUpdating = true;
        this.naming.m_94144_(this.tile.setupName);
        this.startHeat.m_94144_(Integer.toString(this.tile.getSettings().startingHeat));
        this.customTime.m_94144_(Integer.toString(this.tile.getSettings().maxTicks));
        this.isUpdating = false;
    }
}

