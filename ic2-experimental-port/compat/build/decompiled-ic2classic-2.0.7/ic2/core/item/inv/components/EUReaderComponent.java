/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.components;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconCheckBox;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.item.inv.inventory.EUReaderInventory;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EUReaderComponent
extends GuiWidget {
    public static final int BORDER_COLOR = 0;
    public static final int BACKGROUND_COLOR = ColorUtils.DARK_GRAY;
    public static final int PROGRESS_COLOR = ColorUtils.BLUE;
    public static final int TEXT_COLOR = ColorUtils.darker(-1);
    public static final Box2i LEFT_SLIDER = new Box2i(104, 32, 12, 156);
    public static final Box2i RIGHT_SLIDER = new Box2i(225, 32, 12, 156);
    public static final Box2i TEXTURE = new Box2i(0, 241, 12, 15);
    boolean mergeSources = false;
    boolean mergeSinks = false;
    EUReaderInventory inventory;
    SliderComponent leftSlider = this.addChild(new SliderComponent(LEFT_SLIDER, TEXTURE).setNonEmptyRows(5));
    SliderComponent rightSlider = this.addChild(new SliderComponent(RIGHT_SLIDER, TEXTURE).setNonEmptyRows(5));

    public EUReaderComponent(EUReaderInventory inventory) {
        super(Box2i.EMPTY_BOX);
        this.inventory = inventory;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
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
        gui.m_142416_(new IconCheckBox(x + 5, y + 190, 14, 14, (Component)this.string(), this.mergeSources).setToolTip("Merge Sources").setListener(T -> {
            this.mergeSources = !this.mergeSources;
        }));
        gui.m_142416_(new IconCheckBox(x + 126, y + 190, 14, 14, (Component)this.string(), this.mergeSinks).setToolTip("Merge Sinks").setListener(T -> {
            this.mergeSinks = !this.mergeSinks;
        }));
    }

    protected List<EUReaderInventory.SourceInfo> mergeSources() {
        if (!this.mergeSources) {
            return this.inventory.sortedEmitters;
        }
        Object2ObjectSortedMap<Item, List> infos = CollectionUtils.createLinkedMap();
        int m = this.inventory.sortedEmitters.size();
        for (int i = 0; i < m; ++i) {
            Object info = this.inventory.sortedEmitters.get(i);
            List entry = (List)infos.get(((EUReaderInventory.SourceInfo)info).item.m_41720_());
            if (entry == null) {
                entry = new ObjectArrayList();
                infos.put(((EUReaderInventory.SourceInfo)info).item.m_41720_(), entry);
            }
            entry.add(info);
        }
        ObjectList compactedInfo = CollectionUtils.createList();
        for (Object info : infos.values()) {
            compactedInfo.add((EUReaderInventory.SourceInfo)new EUReaderInventory.SourceInfo((List<EUReaderInventory.SourceInfo>)info));
        }
        return compactedInfo;
    }

    protected List<EUReaderInventory.SinkInfo> mergeSinks() {
        if (!this.mergeSinks) {
            return this.inventory.sortedReceivers;
        }
        Object2ObjectSortedMap<Item, List> infos = CollectionUtils.createLinkedMap();
        int m = this.inventory.sortedReceivers.size();
        for (int i = 0; i < m; ++i) {
            Object info = this.inventory.sortedReceivers.get(i);
            List entry = (List)infos.get(((EUReaderInventory.SinkInfo)info).item.m_41720_());
            if (entry == null) {
                entry = new ObjectArrayList();
                infos.put(((EUReaderInventory.SinkInfo)info).item.m_41720_(), entry);
            }
            entry.add(info);
        }
        ObjectList compactedInfo = CollectionUtils.createList();
        for (Object info : infos.values()) {
            compactedInfo.add((EUReaderInventory.SinkInfo)new EUReaderInventory.SinkInfo((List<EUReaderInventory.SinkInfo>)info));
        }
        return compactedInfo;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        double amount;
        Object info;
        int offset;
        int i;
        Lighting.m_84930_();
        List<EUReaderInventory.SourceInfo> infos = this.mergeSources();
        List<EUReaderInventory.SinkInfo> receivers = this.mergeSinks();
        this.leftSlider.setMax(infos.size());
        this.rightSlider.setMax(receivers.size());
        int current = this.leftSlider.getCurrent();
        Lighting.m_84931_();
        for (i = 0; i < 5 && i + current < infos.size(); ++i) {
            offset = 31 * i + 10;
            info = infos.get(i + current);
            amount = Math.min(1.0, ((EUReaderInventory.SourceInfo)info).getEnergy() / (double)((EUReaderInventory.SourceInfo)info).output);
            this.gui.drawItemStack(matrix, this.gui.getGuiLeft() + 8, this.gui.getGuiTop() + 30 + offset, ((EUReaderInventory.SourceInfo)info).item);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 30, this.gui.getGuiTop() + 44 + offset, 60.0f, 9.0f, 0);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 31, this.gui.getGuiTop() + 45 + offset, 58.0f, 7.0f, BACKGROUND_COLOR);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 31, this.gui.getGuiTop() + 45 + offset, (int)(58.0 * amount), 7.0f, PROGRESS_COLOR);
            amount = ((EUReaderInventory.SourceInfo)info).maxEU == 0 ? 0.0 : Math.min(1.0, (double)((EUReaderInventory.SourceInfo)info).storedEU / (double)((EUReaderInventory.SourceInfo)info).maxEU);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 30, this.gui.getGuiTop() + 34 + offset, 60.0f, 9.0f, 0);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 31, this.gui.getGuiTop() + 35 + offset, 58.0f, 7.0f, BACKGROUND_COLOR);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 31, this.gui.getGuiTop() + 35 + offset, (int)(58.0 * amount), 7.0f, PROGRESS_COLOR);
            if (i == 4) continue;
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 5, this.gui.getGuiTop() + 54 + offset, 96.0f, 1.0f, -13158601);
        }
        current = this.rightSlider.getCurrent();
        for (i = 0; i < 5 && i + current < receivers.size(); ++i) {
            offset = 31 * i + 10;
            info = receivers.get(i + current);
            amount = Mth.m_14008_((double)(((EUReaderInventory.SinkInfo)info).getEnergy() / (double)((long)((EUReaderInventory.SinkInfo)info).max * ((EUReaderInventory.SinkInfo)info).getPackets())), (double)0.0, (double)1.0);
            this.gui.drawItemStack(matrix, this.gui.getGuiLeft() + 129, this.gui.getGuiTop() + 30 + offset, ((EUReaderInventory.SinkInfo)info).item);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 148, this.gui.getGuiTop() + 44 + offset, 65.0f, 9.0f, 0);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 149, this.gui.getGuiTop() + 45 + offset, 63.0f, 7.0f, BACKGROUND_COLOR);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 149, this.gui.getGuiTop() + 45 + offset, (int)(63.0 * amount), 7.0f, PROGRESS_COLOR);
            amount = ((EUReaderInventory.SinkInfo)info).maxEU == 0 ? 0.0 : Math.min(1.0, (double)((EUReaderInventory.SinkInfo)info).storedEU / (double)((EUReaderInventory.SinkInfo)info).maxEU);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 148, this.gui.getGuiTop() + 34 + offset, 65.0f, 9.0f, 0);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 149, this.gui.getGuiTop() + 35 + offset, 63.0f, 7.0f, BACKGROUND_COLOR);
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 149, this.gui.getGuiTop() + 35 + offset, (int)(63.0 * amount), 7.0f, PROGRESS_COLOR);
            if (i == 4) continue;
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 127, this.gui.getGuiTop() + 54 + offset, 95.0f, 1.0f, -13158601);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        DecimalFormat format = ItemStack.f_41584_;
        List<EUReaderInventory.SourceInfo> infos = this.mergeSources();
        this.gui.drawRightString(matrix, (Component)this.string(this.leftSlider.getCurrent() + "/" + Math.max(0, infos.size() - 5)), 117, 22, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.eu_reader.emitter"), 5, 22, 0x404040);
        int current = this.leftSlider.getCurrent();
        for (int i = 0; i < 5 && i + current < infos.size(); ++i) {
            EUReaderInventory.SourceInfo info = infos.get(i + current);
            int offset = 31 * i + 10;
            this.gui.drawCenterString(matrix, (Component)this.translate("misc.ic2.eu", format.format(info.output)), 62, 25 + offset, 0x404040);
            this.gui.drawCenterString(matrix, (Component)this.translate("misc.ic2.eu", format.format(info.storedEU)), 62, 35 + offset, TEXT_COLOR);
            this.gui.drawCenterString(matrix, (Component)this.translate("misc.ic2.eu", format.format(info.getEnergy())), 62, 45 + offset, TEXT_COLOR);
        }
        List<EUReaderInventory.SinkInfo> receivers = this.mergeSinks();
        this.gui.drawRightString(matrix, (Component)this.string(this.rightSlider.getCurrent() + "/" + Math.max(0, receivers.size() - 5)), 237, 22, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.eu_reader.receiver"), 126, 22, 0x404040);
        current = this.rightSlider.getCurrent();
        for (int i = 0; i < 5 && i + current < receivers.size(); ++i) {
            EUReaderInventory.SinkInfo info = receivers.get(i + current);
            int offset = 31 * i + 10;
            this.gui.drawCenterString(matrix, (Component)this.translate("misc.ic2.eu", format.format(info.max)), 180, 25 + offset, 0x404040);
            this.gui.drawCenterString(matrix, (Component)this.translate("misc.ic2.eu", format.format(info.storedEU)), 180, 35 + offset, TEXT_COLOR);
            this.gui.drawCenterString(matrix, (Component)this.translate("misc.ic2.eu", format.format(info.getEnergy())), 181, 45 + offset, TEXT_COLOR);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        DecimalFormat format = ItemStack.f_41584_;
        List<EUReaderInventory.SourceInfo> infos = this.mergeSources();
        int current = this.leftSlider.getCurrent();
        for (int i = 0; i < 5 && i + current < infos.size(); ++i) {
            int offset = 31 * i + 10;
            EUReaderInventory.SourceInfo info = infos.get(i + current);
            int minX = 8;
            int minY = 30 + offset;
            if (mouseX < minX || mouseX > minX + 16 || mouseY < minY || mouseY > minY + 16) continue;
            tooltips.accept(info.name);
            if (!this.mergeSources) {
                tooltips.accept((Component)this.translate("gui.ic2.eu_reader.position", info.position.m_123341_(), info.position.m_123342_(), info.position.m_123343_()));
            }
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.power.out", format.format(info.getEnergy()), info.output));
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.power.stored", format.format(info.storedEU), format.format(info.maxEU)));
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.tier", info.tier, info.max));
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.packets.send", info.getPackets()));
            if (this.mergeSources) continue;
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.highlight"));
        }
        current = this.rightSlider.getCurrent();
        List<EUReaderInventory.SinkInfo> receivers = this.mergeSinks();
        for (int i = 0; i < 5 && i + current < receivers.size(); ++i) {
            int offset = 31 * i + 10;
            EUReaderInventory.SinkInfo info = receivers.get(i + current);
            int minX = 129;
            int minY = 30 + offset;
            if (mouseX < minX || mouseX > minX + 16 || mouseY < minY || mouseY > minY + 16) continue;
            tooltips.accept(info.name);
            if (!this.mergeSinks) {
                tooltips.accept((Component)this.translate("gui.ic2.eu_reader.position", info.position.m_123341_(), info.position.m_123342_(), info.position.m_123343_()));
            }
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.power.in", format.format(info.getEnergy())));
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.power.stored", format.format(info.storedEU), format.format(info.maxEU)));
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.tier", info.tier, info.max));
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.packets.received", info.getPackets()));
            if (this.mergeSinks) continue;
            tooltips.accept((Component)this.translate("gui.ic2.eu_reader.highlight"));
        }
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        int minY;
        int minX;
        int i;
        int current;
        if (!this.mergeSources) {
            List<EUReaderInventory.SourceInfo> infos = this.inventory.sortedEmitters;
            current = this.leftSlider.getCurrent();
            for (i = 0; i < 5 && i + current < infos.size(); ++i) {
                minX = 8;
                minY = 40 + 31 * i;
                if (mouseX < minX || mouseX > minX + 16 || mouseY < minY || mouseY > minY + 16) continue;
                EUReaderInventory.SourceInfo info = infos.get(i + current);
                BlockHighlighter.INSTANCE.addHighlight(info.position);
                this.gui.getMinecraft().f_91074_.m_6915_();
                return true;
            }
        }
        if (!this.mergeSinks) {
            List<EUReaderInventory.SinkInfo> receivers = this.inventory.sortedReceivers;
            current = this.rightSlider.getCurrent();
            for (i = 0; i < 5 && i + current < receivers.size(); ++i) {
                minX = 129;
                minY = 40 + 31 * i;
                if (mouseX < minX || mouseX > minX + 16 || mouseY < minY || mouseY > minY + 16) continue;
                EUReaderInventory.SinkInfo info = receivers.get(i + current);
                BlockHighlighter.INSTANCE.addHighlight(info.position);
                this.gui.getMinecraft().f_91074_.m_6915_();
                return true;
            }
        }
        return false;
    }
}

