/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.ints.Int2IntMap$Entry
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.core.block.storage.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.base.tiles.impls.BaseChargingBenchTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class ChargingBenchComponent
extends GuiWidget {
    BaseChargingBenchTileEntity tile;
    boolean lastMode = false;

    public ChargingBenchComponent(BaseChargingBenchTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        if (this.tile.sortedMode != this.lastMode) {
            this.lastMode = this.tile.sortedMode;
            gui.setFlag(64);
        }
        gui.getCastedButton(0, ItemCheckBox.class).setChecked(this.tile.sortedMode);
        gui.getButton((int)1).f_93624_ = this.tile.sortedMode;
        gui.getButton((int)2).f_93624_ = this.tile.sortedMode;
        gui.getButton((int)3).f_93624_ = this.tile.sortedMode;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ItemCheckBox(x + 130, y + 45, 18, 18, T -> this.sendData(-1), new ItemStack((ItemLike)Items.f_42155_), this.tile.sortedMode).setToolTip("Toggle Sorted Mode"));
        gui.addRenderableWidget((int)1, new IconButton((int)(x + 130), (int)(y + 63), (int)18, (int)18, (ItemStack)IC2Items.ICON_DISPLAY.create((int)3), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ChargingBenchComponent)this))).setToolTip((String)"Insert into Bench").f_93624_ = this.tile.sortedMode;
        gui.addRenderableWidget((int)2, new IconButton((int)(x + 130), (int)(y + 81), (int)18, (int)18, (ItemStack)IC2Items.ICON_DISPLAY.create((int)5), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ChargingBenchComponent)this))).setToolTip((String)"Swap Items").f_93624_ = this.tile.sortedMode;
        gui.addRenderableWidget((int)3, new IconButton((int)(x + 130), (int)(y + 99), (int)18, (int)18, (ItemStack)IC2Items.ICON_DISPLAY.create((int)4), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ChargingBenchComponent)this))).setToolTip((String)"Extract from Bench").f_93624_ = this.tile.sortedMode;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.battery_station.eu"), 150, 20, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.battery_station.etc"), 150, 30, 0x404040);
        this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.battery_station.rem"), 25, 30, 0x404040);
        float scale = this.tile.getMaxEU() >= 10000000 && this.tile.getStoredEU() >= 10000000 ? 0.5f : 1.0f;
        float invScale = 1.0f / scale;
        matrix.m_85841_(scale, scale, scale);
        this.gui.drawRightString(matrix, (Component)this.string(Formatters.EU_FORMAT.format(this.tile.getStoredEU())), (int)(80.0f * invScale), (int)(20.0f * invScale), 0x55FF55);
        this.gui.drawCenterString(matrix, (Component)this.string(" / "), (int)(88.0f * invScale), (int)(20.0f * invScale), 0x55FF55);
        this.gui.drawString(matrix, (Component)this.string(Formatters.EU_FORMAT.format(this.tile.getMaxEU())), (int)(93.0f * invScale), (int)(20.0f * invScale), 0x55FF55);
        matrix.m_85841_(invScale, invScale, invScale);
        Int2IntMap.Entry missingTuple = this.tile.getMissingEnergy();
        int missing = missingTuple.getIntKey() + (this.tile.getMaxEU() - this.tile.getStoredEU());
        this.gui.drawString(matrix, (Component)(missing >= 100000000 ? this.translate("gui.ic2.battery_station.alot") : this.string(Formatters.EU_FORMAT.format(missing)).m_7220_((Component)this.translate("gui.ic2.battery_station.eu"))), 35, 30, 0x55FF55);
        if (missing <= 0) {
            this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.battery_station.done"), 145, 30, 0x55FF55);
        } else {
            int average = Math.min(this.tile.averager.getAverage(), missingTuple.getIntKey() <= 0 ? Integer.MAX_VALUE : missingTuple.getIntValue());
            if (average > 0) {
                this.gui.drawRightString(matrix, (Component)this.translate(DurationFormatUtils.formatDuration((long)Math.max(0L, (long)(missing / average) * 50L), (String)"HH:mm:ss")), 145, 30, 0x55FF55);
            } else {
                this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.battery_station.never"), 145, 30, 0x55FF55);
            }
        }
    }

    protected void sendData(int index) {
        this.tile.sendToServer(index + 1, 0);
    }

    private /* synthetic */ void lambda$init$3(Button T) {
        this.sendData(2);
    }

    private /* synthetic */ void lambda$init$2(Button T) {
        this.sendData(1);
    }

    private /* synthetic */ void lambda$init$1(Button T) {
        this.sendData(0);
    }
}

