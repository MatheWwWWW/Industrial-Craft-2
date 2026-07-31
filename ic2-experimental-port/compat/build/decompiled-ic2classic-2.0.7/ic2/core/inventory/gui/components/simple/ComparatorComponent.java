/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.comparator.ComparatorListener;
import ic2.core.block.base.misc.comparator.ComparatorManager;
import ic2.core.block.base.misc.comparator.ComparingList;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.OpenerButton;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.inventory.gui.feature.ICloseableComponent;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.lang.invoke.LambdaMetafactory;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ComparatorComponent<T extends BaseTileEntity>
extends GuiWidget
implements ICloseableComponent {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/misc/comparator_helper.png");
    public static final Component[] NAMES = new Component[]{Component.m_237115_((String)"gui.ic2.comparator.evaluator.min"), Component.m_237115_((String)"gui.ic2.comparator.evaluator.max"), Component.m_237115_((String)"gui.ic2.comparator.evaluator.average")};
    public static final Box2i PRESENT_BOX = new Box2i(-117, 31, 52, 15);
    public static final Box2i COMPARATOR_BOX = new Box2i(-117, 87, 90, 15);
    public static final Box2i FIRST_SCROLL = new Box2i(-18, 31, 11, 30);
    public static final Box2i SECOND_SCROLL = new Box2i(-18, 87, 11, 45);
    BaseTileEntity tile;
    ComparatorManager manager;
    int selected = 6;
    SliderComponent selectedSlider;
    SliderComponent optionSlider;
    Vec2i buttonOffset;
    Vec2i offset;
    IntList ourSlots = new IntArrayList();

    public ComparatorComponent(T comparator, Vec2i buttonOffset, Vec2i offset) {
        super(new Box2i(-122 + offset.getX(), offset.getY(), 122, 139));
        this.manager = ((BaseTileEntity)comparator).getManager();
        this.tile = comparator;
        this.buttonOffset = buttonOffset;
        this.offset = offset;
        this.selectedSlider = this.addChild(new SliderComponent(new Box2i(-19 + offset.getX(), 31 + offset.getY(), 12, 30), new Box2i(139, 0, 12, 11)).setNonEmptyRows(1)).setCustomTexture(TEXTURE);
        this.optionSlider = this.addChild(new SliderComponent(new Box2i(-19 + offset.getX(), 87 + offset.getY(), 12, 45), new Box2i(151, 0, 12, 15)).setNonEmptyRows(2)).setCustomTexture(TEXTURE);
        this.setVisible(false);
        this.selectedSlider.setVisible(false);
        this.optionSlider.setVisible(false);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void closeComponent(IC2Screen screen) {
        if (this.isVisible()) {
            this.setVisible(false);
            this.toggleVisibility(screen, false);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private void toggleVisibility(IC2Screen screen, boolean visible) {
        screen.closeAllComponents();
        this.setVisible(visible);
        this.selectedSlider.setVisible(visible);
        this.optionSlider.setVisible(visible);
        this.tile.sendToServer(65534, this.isVisible() ? 1 : 0);
        this.tick(screen);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int[] indexes;
        this.ourSlots.clear();
        int x = gui.getGuiLeft() - 122 + this.offset.getX();
        int y = gui.getGuiTop() + this.offset.getY();
        this.selectedSlider.setMax(this.getList().size());
        for (int i = 0; i < 4; ++i) {
            boolean active = i < this.selectedSlider.getMax() && this.isVisible();
            int index = i;
            int flags = active ? this.getList().get(i).getFlags() : 0;
            this.updateVisibility(gui.addRenderableWidget((int)(200 + i * 3), new ItemCheckBox((int)(x + 56), (int)(y + 31 + i * 15), (int)15, (int)15, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$0(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ComparatorComponent)this, (int)index), (ItemStack)new ItemStack((ItemLike)Items.f_41978_), (boolean)((flags & 1) != 0 ? true : false)).setToolTip((String)"gui.ic2.comparator.invert"))).f_93624_ = active;
            this.updateVisibility(gui.addRenderableWidget((int)(200 + i * 3 + 1), new ItemCheckBox((int)(x + 70), (int)(y + 31 + i * 15), (int)15, (int)15, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ComparatorComponent)this, (int)index), (ItemStack)new ItemStack((ItemLike)Items.f_42350_), (boolean)((flags & 2) != 0 ? true : false)).setToolTip((String)"gui.ic2.comparator.sign"))).f_93624_ = active;
            this.updateVisibility(gui.addRenderableWidget((int)(200 + i * 3 + 2), new ItemCheckBox((int)(x + 84), (int)(y + 31 + i * 15), (int)15, (int)15, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ComparatorComponent)this, (int)index), (ItemStack)new ItemStack((ItemLike)Items.f_41978_), (boolean)((flags & 4) != 0 ? true : false)).setToolTip((String)"gui.ic2.comparator.post_invert"))).f_93624_ = active;
            this.ourSlots.add(200 + i * 3);
            this.ourSlots.add(200 + i * 3 + 1);
            this.ourSlots.add(200 + i * 3 + 2);
        }
        String s = "DUNSWEV";
        for (int i : indexes = new int[]{1, 0, 2, 5, 3, 4, 6}) {
            this.updateVisibility(gui.addRenderableWidget((int)(250 + i), new ToolTipButton((int)(x + 15 + i * 13), (int)(y + 17), (int)12, (int)12, (Component)this.string((String)Character.toString((char)s.charAt((int)i))), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ComparatorComponent)this, (int)i))).setToolTip((Component)(i == 6 ? Blocks.f_50328_.m_49954_() : DirectionList.getName((Direction)Direction.m_122376_((int)i)).m_130946_((String)Character.toString((char)'\n')).m_7220_((Component)IC2Blocks.ADVANCED_COMPARATOR.m_49954_())))).f_93623_ = i != 6;
            this.ourSlots.add(250 + i);
        }
        this.ourSlots.add(260);
        this.updateVisibility(gui.addRenderableWidget(260, new ToolTipButton(x + 48, y + 62, 52, 12, NAMES[this.getList().getEvaluator()], T -> this.flipMax()).setToolTip("gui.ic2.comparator.evaluator")));
        gui.addRenderableWidget(261, new OpenerButton(gui.getGuiLeft() + 16 + this.buttonOffset.getX(), gui.getGuiTop() + 5 + this.buttonOffset.getY(), 10, 10, (Component)this.string("C"), T -> this.toggleVisibility(gui, !this.isVisible())).setToolTip("gui.ic2.comparator.settings"));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        int i;
        this.selectedSlider.setMax(this.getList().size());
        this.optionSlider.setMax(this.getVisible().size());
        int current = this.selectedSlider.getCurrent();
        boolean empty = this.getList().size() > 0;
        for (i = 0; i < 2; ++i) {
            boolean active = this.selectedSlider.inRange(i);
            int flags = active ? this.getList().get(i + current).getFlags() : 0;
            ItemCheckBox box = this.updateVisibility(gui.getCastedButton(200 + i * 3, ItemCheckBox.class).setChecked((flags & 1) != 0));
            box.f_93624_ = active && this.isVisible();
            box.f_93623_ = empty;
            box = this.updateVisibility(gui.getCastedButton(200 + i * 3 + 1, ItemCheckBox.class).setChecked((flags & 2) != 0));
            box.f_93624_ = active && this.isVisible();
            box.f_93623_ = empty;
            box = this.updateVisibility(gui.getCastedButton(200 + i * 3 + 2, ItemCheckBox.class).setChecked((flags & 4) != 0));
            box.f_93624_ = active && this.isVisible();
            box.f_93623_ = empty;
        }
        for (i = 0; i < 7; ++i) {
            this.updateVisibility(gui.getButton((int)(250 + i))).f_93623_ = i != this.selected && this.isVisible();
        }
        this.updateVisibility(gui.getButton(260)).m_93666_(NAMES[this.getList().getEvaluator()]);
    }

    @OnlyIn(value=Dist.CLIENT)
    private <K extends AbstractWidget> K updateVisibility(K button) {
        button.f_93624_ = this.isVisible();
        button.f_93623_ = this.isVisible();
        return button;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int i;
        this.selectedSlider.setMax(this.getList().size());
        this.optionSlider.setMax(this.getVisible().size());
        this.gui.bindTexture(TEXTURE);
        int x = this.gui.getGuiLeft() - 122 + this.offset.getX();
        int y = this.gui.getGuiTop() + this.offset.getY();
        this.gui.drawTextureRegion(matrix, x, y, 0.0f, 0.0f, 122.0f, 139.0f);
        for (i = 0; i < 2 && this.selectedSlider.inRange(i); ++i) {
            this.gui.drawTextureRegion(matrix, x + 5, y + 31 + i * 15, 163.0f, PRESENT_BOX.isInBox(mouseX - this.offset.getX(), mouseY - 15 * i - this.offset.getY()) ? 15.0f : 0.0f, 52.0f, 15.0f);
        }
        for (i = 0; i < 3 && this.optionSlider.inRange(i); ++i) {
            this.gui.drawTextureRegion(matrix, x + 5, y + 87 + i * 15, 163.0f, COMPARATOR_BOX.isInBox(mouseX - this.offset.getX(), mouseY - 15 * i - this.offset.getY()) ? 45.0f : 30.0f, 90.0f, 15.0f);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.comparator.settings"), -62 + this.offset.getX(), 5 + this.offset.getY(), 0x404040);
        float scale = 0.5f;
        float invScale = 1.0f / scale;
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.comparator.out", this.getList().getValue()), -117 + this.offset.getX(), 63 + this.offset.getY(), 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.comparator.sources"), -65 + this.offset.getX(), 76 + this.offset.getY(), 0x404040);
        matrix.m_85841_(scale, scale, scale);
        for (int i = 0; i < 2 && this.selectedSlider.inRange(i); ++i) {
            ComparatorListener listener = this.getList().get(i + this.selectedSlider.getCurrent());
            this.gui.drawString(matrix, listener.getName(), (int)((float)(-114 - this.offset.getX()) * invScale), (int)((float)(34 + this.offset.getY() + 15 * i) * invScale), -1);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.comparator.output.dual", listener.getBaseValue(), listener.getValue()), (int)((float)(-114 - this.offset.getX()) * invScale), (int)((float)(39 + this.offset.getY() + 15 * i) * invScale), -1);
        }
        List<BaseComparator> visible = this.getVisible();
        Direction dir = this.selected == 6 ? null : Direction.m_122376_((int)this.selected);
        for (int i = 0; i < 3 && this.optionSlider.inRange(i); ++i) {
            BaseComparator comp;
            int index = i + this.optionSlider.getCurrent();
            if (index >= visible.size() || (comp = visible.get(index)) == null) continue;
            this.gui.drawString(matrix, comp.getName(), (int)((float)(-114 - this.offset.getX()) * invScale), (int)((float)(90 + this.offset.getY() + 15 * i) * invScale), -1);
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.comparator.output.single", comp.getValue(dir)), (int)((float)(-114 - this.offset.getX()) * invScale), (int)((float)(95 + this.offset.getY() + 15 * i) * invScale), -1);
        }
        matrix.m_85841_(1.0f / scale, 1.0f / scale, 1.0f / scale);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        int i;
        if (!this.isVisible()) {
            return false;
        }
        IntListIterator intListIterator = this.ourSlots.iterator();
        while (intListIterator.hasNext()) {
            int id = (Integer)intListIterator.next();
            AbstractWidget widget = this.gui.getButton(id);
            if (widget == null || !widget.m_198029_()) continue;
            return false;
        }
        for (i = 0; i < 2 && this.selectedSlider.inRange(i); ++i) {
            if (!PRESENT_BOX.isInBox(mouseX - this.offset.getX(), mouseY - 15 * i - this.offset.getY())) continue;
            this.tile.sendToServer(65536, i + this.selectedSlider.getCurrent() << 3 | this.selected);
            return true;
        }
        for (i = 0; i < 3 && this.optionSlider.inRange(i); ++i) {
            if (!COMPARATOR_BOX.isInBox(mouseX - this.offset.getX(), mouseY - 15 * i - this.offset.getY())) continue;
            this.tile.sendToServer(65535, this.getIndex(i + this.optionSlider.getCurrent()) << 3 | this.selected);
            return true;
        }
        return false;
    }

    public ComparingList getList() {
        return this.manager.getList(this.selected);
    }

    public int getIndex(int baseIndex) {
        List<BaseComparator> visible = this.getVisible();
        if (baseIndex >= visible.size()) {
            return baseIndex;
        }
        return this.manager.getIndex(visible.get(baseIndex));
    }

    public List<BaseComparator> getVisible() {
        return this.manager.getComparators();
    }

    public void flipMax() {
        this.tile.sendToServer(65538, this.selected);
    }

    public void changeSide(int side) {
        this.selected = side;
        this.selectedSlider.setCurrent(0);
    }

    public void flipBit(int index, int bitIndex) {
        this.tile.sendToServer(65537, index + this.selectedSlider.getCurrent() << 5 | (bitIndex & 3) << 3 | this.selected);
    }

    private /* synthetic */ void lambda$init$3(int i, Button T) {
        this.changeSide(i);
    }

    private /* synthetic */ void lambda$init$2(int index, Button T) {
        this.flipBit(index, 2);
    }

    private /* synthetic */ void lambda$init$1(int index, Button T) {
        this.flipBit(index, 1);
    }

    private /* synthetic */ void lambda$init$0(int index, Button T) {
        this.flipBit(index, 0);
    }
}

