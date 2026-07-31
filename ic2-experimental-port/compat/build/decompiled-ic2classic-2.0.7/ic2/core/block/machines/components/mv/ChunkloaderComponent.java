/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.block.machines.tiles.mv.ChunkloaderTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.utils.math.MathUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Objects;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChunkloaderComponent
extends GuiWidget {
    static final Component[] COMPONENTS = new Component[]{Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.public")}), Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.protected")}), Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.private")})};
    static final ItemStack[] COMPONENT_ITEMS = new ItemStack[]{new ItemStack((ItemLike)Items.f_42430_), new ItemStack((ItemLike)Items.f_42411_), new ItemStack((ItemLike)Items.f_42740_)};
    private ChunkloaderTileEntity entity;
    int lastRadius;
    int lastMode;
    boolean lastProcessing;

    public ChunkloaderComponent(ChunkloaderTileEntity entity) {
        super(Box2i.EMPTY_BOX);
        this.entity = entity;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ToolTipButton(gui.getGuiLeft() + 28, gui.getGuiTop() + 40, 16, 16, (Component)this.string("-"), T -> this.modifyRadius(-1)));
        gui.addRenderableWidget(1, new ToolTipButton(gui.getGuiLeft() + 75, gui.getGuiTop() + 40, 16, 16, (Component)this.string("+"), T -> this.modifyRadius(1)));
        gui.addRenderableWidget(2, new ToolTipButton(gui.getGuiLeft() + 5, gui.getGuiTop() + 79, 110, 14, (Component)this.translate(this.entity.doesChunkProcessing ? "gui.ic2.chunkloader.tick.yes" : "gui.ic2.chunkloader.tick.no"), T -> this.modifyProcessing()));
        gui.addRenderableWidget(3, new ToolTipButton(gui.getGuiLeft() + 5, gui.getGuiTop() + 94, 110, 14, (Component)this.translate("gui.ic2.chunkloader.confirm"), T -> this.validate()));
        gui.addRenderableWidget(4, new IconButton(x + 106, y - 16, 16, 16, COMPONENT_ITEMS[this.lastMode], T -> this.entity.sendToServer(1, 0)).setToolTip(COMPONENTS[this.entity.mode]));
        this.lastRadius = this.entity.radius;
        this.lastProcessing = this.entity.doesChunkProcessing;
        this.lastMode = this.entity.mode;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton(2).m_93666_((Component)this.translate(this.entity.doesChunkProcessing ? "gui.ic2.chunkloader.tick.yes" : "gui.ic2.chunkloader.tick.no"));
        gui.getButton((int)3).f_93623_ = this.lastProcessing != this.entity.doesChunkProcessing || this.lastRadius != this.entity.radius;
    }

    private void modifyRadius(int change) {
        this.entity.radius = Mth.m_14045_((int)(this.entity.radius + change), (int)0, (int)(ChunkloaderTileEntity.POWER_COST.length - 1));
    }

    private void modifyProcessing() {
        this.entity.doesChunkProcessing = !this.entity.doesChunkProcessing;
    }

    public void onSync() {
        this.lastRadius = this.entity.radius;
        this.lastProcessing = this.entity.doesChunkProcessing;
    }

    private void validate() {
        IC2.NETWORKING.get(false).sendClientTileEvent(this.entity, 0, MathUtils.putInt(this.entity.radius, this.entity.doesChunkProcessing ? 1 : 0));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int value = this.entity.radius + this.entity.radius + 1;
        MutableComponent mutableComponent = this.string(Integer.toString(this.entity.getRadius()));
        Objects.requireNonNull(this.gui.getFont());
        this.gui.drawCenterString(matrix, (Component)mutableComponent, 60, 41 + 9 / 2, 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.chunkloader.loaded_chunks", value * value), 60, 60, 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.chunkloader.cost", ChunkloaderTileEntity.POWER_COST[this.entity.radius] * (this.entity.doesChunkProcessing ? 2 : 1)), 60, 70, 0x404040);
    }
}

