/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 */
package ic2.core.block.machines.components.mv.planner;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.block.machines.containers.mv.ReactorPlannerContainer;
import ic2.core.block.machines.logic.planner.ComponentInfo;
import ic2.core.block.machines.logic.planner.PlannerRegistry;
import ic2.core.block.machines.logic.planner.encoder.EncoderRegistry;
import ic2.core.block.machines.logic.planner.newLogic.ReactorLogic;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class MainScreenComponent
extends GuiWidget {
    ReactorPlannerTileEntity tile;
    SliderComponent slider = this.addChild(new SliderComponent(new Box2i(277, 21, 14, 106), new Box2i(90, 212, 12, 15), 1).setListener(this::setOffset)).setNonEmptyRows(5);
    int lastIndex = -1;
    int[] lastColors = new int[0];

    public MainScreenComponent(ReactorPlannerTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        gui.setContainerOffset(-18, 2);
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ExtendedButton(x + 6, y + 7, 10, 10, (Component)this.string("-"), T -> this.tile.sendToServer(1, -1)));
        gui.addRenderableWidget(1, new ExtendedButton(x + 157, y + 7, 10, 10, (Component)this.string("+"), T -> this.tile.sendToServer(1, 1)));
        gui.addRenderableWidget(2, new ToolTipButton(x + 220, y + 7, 70, 10, this.getName(), T -> this.tile.sendToServer(0, Screen.m_96638_() ? 1 : 0)).setToolTip(this.getName()));
        gui.addRenderableWidget(3, new ToolTipButton(x + 220, y + 166, 55, 12, (Component)this.translate("gui.ic2.reactor_planner.button.import"), T -> this.importSetup())).setToolTip("gui.ic2.reactor_planner.button.import.desc");
        gui.addRenderableWidget(4, new ToolTipButton(x + 220, y + 181, 55, 12, (Component)this.translate("gui.ic2.reactor_planner.button.export"), T -> this.exportSetup())).setToolTip("gui.ic2.reactor_planner.button.export.desc");
        this.slider.setCurrent(this.tile.selection.getOffset()).setMax(this.tile.selection.getTotalItems() / 3);
    }

    private Component getName() {
        byte index = this.tile.selection.getCurrentIndex();
        return index == -1 ? this.translate("gui.ic2.reactor_planner.component.any") : IReactorPlannerComponent.ComponentType.byID(index).getName();
    }

    private void importSetup() {
        Tuple<String, CompoundTag> data = EncoderRegistry.INSTANCE.createDecodedMessage(Minecraft.m_91087_().f_91068_.m_90876_().trim());
        if (data == null) {
            this.gui.getPlayer().m_213846_((Component)this.string("No Decoder found"));
            return;
        }
        CompoundTag nbt = new CompoundTag();
        nbt.m_128359_("decoder", (String)data.m_14418_());
        nbt.m_128365_("data", (Tag)data.m_14419_());
        this.tile.sendToServer("import", new NBTBuffer(nbt));
    }

    private void exportSetup() {
        String encoder = EncoderRegistry.INSTANCE.getDefaultEncoderID();
        if (Screen.m_96638_()) {
            StringBuilder joiner = new StringBuilder("");
            joiner.append("Please Selectione the Following Encoders: ");
            joiner.append(this.buildEmptyRange((240 - this.gui.getFont().m_92895_("Please Selectione the Following Encoders: ")) / 4));
            ObjectArrayList list = new ObjectArrayList(EncoderRegistry.INSTANCE.getChoiceList().iterator());
            for (Tuple entry : list) {
                String s = (String)entry.m_14419_() + ": [ " + (String)entry.m_14418_() + " ],";
                joiner.append(s);
                joiner.append(this.buildEmptyRange((290 - this.gui.getFont().m_92895_(s)) / 4));
            }
            String result = TinyFileDialogs.tinyfd_inputBox((CharSequence)"Select Encoder", (CharSequence)joiner.toString(), (CharSequence)encoder);
            if (EncoderRegistry.INSTANCE.getEncoderFromID(result) == null) {
                this.gui.getPlayer().m_5661_((Component)this.translate("gui.ic2.reactor_planner.io.no_encoder"), false);
                return;
            }
            encoder = result;
        }
        this.tile.sendToServer("export", new StringDataBuffer(1, encoder));
    }

    private String buildEmptyRange(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton((int)0).f_93623_ = this.tile.getReactorSize() > 0;
        boolean bl = gui.getButton((int)1).f_93623_ = this.tile.getReactorSize() < 6;
        if (this.tile.selection.getCurrentIndex() != this.lastIndex) {
            this.lastIndex = this.tile.selection.getCurrentIndex();
            Component name = this.getName();
            gui.getCastedButton(2, ToolTipButton.class).setToolTip(name).m_93666_(name);
        }
        this.slider.setCurrent(this.tile.selection.getOffset()).setMax(this.tile.selection.getTotalItems() / 3);
        gui.getButton((int)4).f_93623_ = this.tile.simulation.isValid;
        if (gui.getClock() % 10 == 0) {
            gui.getCastedContainer(ReactorPlannerContainer.class).onSlotSelected();
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        ItemStack stack;
        Item item;
        Slot slot;
        int offset;
        int row;
        int remove = this.tile.getReactorSize() * 9;
        int m = 3 + this.tile.getReactorSize();
        for (int i = 0; i < m; ++i) {
            this.drawRow(matrix, 60 + i * 18 - remove, 20, 6, i);
        }
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        this.gui.drawTextureRegion(matrix, x + 200, y, 117.0f, 0.0f, 95.0f, 212.0f);
        for (int i = 0; i < 3; ++i) {
            this.drawRow(matrix, 220 + i * 18, 20, 6, -1);
        }
        this.gui.drawTextureRegion(matrix, x + 276, y + 20, 75.0f, 212.0f, 14.0f, 1.0f);
        this.gui.drawTextureRegion(matrix, x + 276, y + 21, 75.0f, 213.0f, 14.0f, 106.0f, 14.0f, 31.0f);
        this.gui.drawTextureRegion(matrix, x + 276, y + 127, 75.0f, 244.0f, 14.0f, 1.0f);
        if (this.tile.selectedSlot != -1 && (row = this.tile.selectedSlot / 3) >= (offset = this.tile.selection.getOffset()) && row < offset + 6) {
            int slot2 = this.tile.selectedSlot % 3;
            this.gui.drawTextureRegion(matrix, x + 220 + slot2 * 18, y + 20 + 18 * (row -= offset), 0.0f, 230.0f, 18.0f, 18.0f);
        }
        if ((slot = this.gui.getSlotUnderMouse()) instanceof ReactorPlannerContainer.SimulationSlot && !((IC2Container)this.gui.m_6262_()).m_142621_().m_41619_() && (item = (stack = ((IC2Container)this.gui.m_6262_()).m_142621_()).m_41720_()) instanceof IReactorPlannerComponent) {
            IReactorPlannerComponent comp = (IReactorPlannerComponent)item;
            IntLinkedOpenHashSet slots = new IntLinkedOpenHashSet();
            int index = slot.getSlotIndex();
            comp.addAffectedSlots(index % 9, index / 9, (arg_0, arg_1) -> MainScreenComponent.lambda$drawBackground$5((IntSet)slots, arg_0, arg_1));
            for (int slotId : slots.toIntArray()) {
                Slot drawSlot = ((IC2Container)this.gui.m_6262_()).m_38853_(slotId);
                if (drawSlot == null) continue;
                RenderSystem.m_69478_();
                this.gui.drawColoredRegion(matrix, drawSlot.f_40220_ + this.gui.getGuiLeft(), drawSlot.f_40221_ + this.gui.getGuiTop(), 16.0f, 16.0f, ColorUtils.rgb(255, 255, 0, 150));
                RenderSystem.m_69461_();
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot != null && slot.m_6657_() && ((IC2Container)this.gui.m_6262_()).m_142621_().m_41619_()) {
            Object2ObjectMap<IReactorPlannerComponent.ReactorStat, NumericTag> stats;
            if (slot instanceof ReactorPlannerContainer.SimulationSlot) {
                for (Component comp : slot.m_7993_().m_41651_((Player)this.gui.getPlayer(), (TooltipFlag)(this.gui.getMinecraft().f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL))) {
                    tooltips.accept(comp);
                }
            } else {
                tooltips.accept(slot.m_7993_().m_41786_());
            }
            ItemStack stack = slot.m_7993_();
            ComponentInfo info = PlannerRegistry.getInfo(stack);
            if (info == null) {
                return;
            }
            Item item = stack.m_41720_();
            if (item instanceof IReactorPlannerComponent) {
                IReactorPlannerComponent comp = (IReactorPlannerComponent)item;
                comp.addToolTip(stack, tooltips);
            }
            if ((stats = this.getStats(info, slot)).isEmpty()) {
                return;
            }
            tooltips.accept((Component)this.string());
            tooltips.accept((Component)this.translate("gui.ic2.reactor_planner.component.stats"));
            StringBuilder builder = new StringBuilder();
            for (Object2ObjectMap.Entry entry : stats.object2ObjectEntrySet()) {
                tooltips.accept((Component)this.string(builder.append(((IReactorPlannerComponent.ReactorStat)((Object)entry.getKey())).getName((NumericTag)entry.getValue()).getString()).append(" ").toString()));
                builder.setLength(0);
            }
        }
    }

    private Object2ObjectMap<IReactorPlannerComponent.ReactorStat, NumericTag> getStats(ComponentInfo info, Slot slot) {
        int y;
        if (!(slot instanceof ReactorPlannerContainer.SimulationSlot) || !(slot.m_7993_().m_41720_() instanceof IReactorPlannerComponent)) {
            return info.getStats();
        }
        int index = slot.getSlotIndex();
        int x = index % 9;
        SimulatedStack stack = this.tile.simulation.getItem(x, y = index / 9);
        if (stack == null) {
            return Object2ObjectMaps.emptyMap();
        }
        Object2ObjectSortedMap map = CollectionUtils.createLinkedMap();
        ReactorLogic logic = this.tile.simulation;
        int fuelPulse = logic.fuelPulse;
        int breedingPulse = logic.breedingPulse;
        float production = logic.production;
        logic.production = 0.0f;
        logic.fuelPulse = 0;
        logic.breedingPulse = 0;
        for (IReactorPlannerComponent.ReactorStat stat : info.getStats().keySet()) {
            map.put((Object)stat, (Object)stack.getStat(stat, logic, x, y));
        }
        logic.fuelPulse = fuelPulse;
        logic.breedingPulse = breedingPulse;
        logic.production = production;
        return map;
    }

    private void setOffset(int value) {
        this.tile.sendToServer(3, value);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot instanceof LockedSlot && !(slot instanceof ReactorPlannerContainer.SimulationSlot)) {
            this.tile.sendToServer(4, mouseButton == 1 ? -1 : this.tile.selection.getOffset() * 3 + slot.getSlotIndex());
            return true;
        }
        if (slot == null && mouseButton == 1 && this.tile.selectedSlot != -1) {
            this.tile.sendToServer(4, -1);
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot instanceof LockedSlot && !(slot instanceof ReactorPlannerContainer.SimulationSlot) || this.box.isInBox(mouseX, mouseY)) {
            this.tile.sendToServer(2, -scroll);
            return true;
        }
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void drawRow(PoseStack matrix, int xOffset, int yOffset, int count, int index) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        for (int i = 0; i < count; ++i) {
            boolean broken = index >= 0 && i * 9 + index == this.tile.simulation.brokenSlot;
            this.gui.drawTextureRegion(matrix, x + xOffset, y + yOffset + 18 * i, 0.0f, 212.0f, 18.0f, 18.0f, 18.0f, 18.0f, broken ? ColorUtils.RED : -1);
        }
    }

    private static /* synthetic */ boolean lambda$drawBackground$5(IntSet slots, Integer X, Integer Y) {
        if (X < 0 || X >= 9 || Y < 0 || Y >= 6) {
            return false;
        }
        slots.add(Y * 9 + X);
        return true;
    }
}

