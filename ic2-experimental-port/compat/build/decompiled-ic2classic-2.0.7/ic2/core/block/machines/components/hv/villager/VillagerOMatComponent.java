/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.entity.LevelEntityGetter
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.hv.villager;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.util.DirectionList;
import ic2.core.block.machines.components.hv.villager.VillagerSelectorComponent;
import ic2.core.block.machines.logic.villager.VillagerTrade;
import ic2.core.block.machines.logic.villager.VillagerTradeList;
import ic2.core.block.machines.tiles.hv.VillagerOMatTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.networking.buffers.data.VillagerBuffer;
import ic2.core.platform.corehacks.mixins.server.LevelMixin;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerOMatComponent
extends GuiWidget {
    private static final Box2i XP_BOX = new Box2i(30, 25, 102, 5);
    VillagerOMatTileEntity tile;
    VillagerSelectorComponent.VillagerEntry target;
    List<TradeEntry> entries = CollectionUtils.createList();
    SliderComponent slider = this.addChild(new SliderComponent(new Box2i(135, 35, 12, 80), new Box2i(0, 241, 12, 15))).setNonEmptyRows(1);

    public VillagerOMatComponent(VillagerOMatTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    public void setVillager(VillagerSelectorComponent.VillagerEntry target) {
        boolean changed = this.target != target;
        this.target = target;
        ObjectList trades = CollectionUtils.createList();
        for (Villager villager : this.getVillagers()) {
            if (!target.target().matches(villager)) continue;
            trades.addAll(this.tile.trades.getTrade(villager.m_20148_()).getKnownTrades());
        }
        this.entries.clear();
        if (target.target().isGroup()) {
            Object2ObjectSortedMap<SimpleTrade, List> mappedTrades = CollectionUtils.createLinkedMap();
            for (Object trade : trades) {
                mappedTrades.computeIfAbsent(new SimpleTrade((VillagerTrade)trade), this::create).add(trade);
            }
            for (Object trade : mappedTrades.values()) {
                this.entries.add(new TradeEntry((List<VillagerTrade>)trade));
            }
        } else {
            for (VillagerTrade villagerTrade : trades) {
                this.entries.add(new TradeEntry(villagerTrade));
            }
        }
        if (changed) {
            this.slider.setCurrent(0);
        }
        this.slider.setMax(this.entries.size());
    }

    private List<VillagerTrade> create(SimpleTrade simple) {
        return CollectionUtils.createList();
    }

    private List<Villager> getVillagers() {
        LevelEntityGetter<Entity> getter = ((LevelMixin)this.gui.getMinecraft().f_91073_).getEntityAccessor();
        ObjectList villagers = CollectionUtils.createList();
        for (VillagerTradeList list : this.tile.trades.getVisibleVillagerTrades()) {
            Entity entity = (Entity)getter.m_142694_(list.getOwner());
            if (!(entity instanceof Villager)) continue;
            Villager villager = (Villager)entity;
            villagers.add((Villager)villager);
        }
        return villagers;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int index;
        int i;
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        for (int i2 = 0; i2 < 4; ++i2) {
            int index2 = i2;
            gui.addRenderableWidget(10 + i2, new ToolTipButton(x + 30, y + 35 + i2 * 20, 102, 20, (Component)this.string(), T -> this.trigger(index2))).setToolTip("gui.ic2.villager.toggle");
        }
        String s = "DUNSWE";
        for (i = 0; i < 6; ++i) {
            index = i;
            gui.addRenderableWidget(20 + i, new ToolTipButton(x + 4 + i % 2 * 12, y + 80 + i / 2 * 12, 12, 12, (Component)this.string(Character.toString(s.charAt(i))), T -> this.setTarget(index, true))).setToolTip((Component)DirectionList.getName(Direction.m_122376_((int)i)));
        }
        for (i = 0; i < 6; ++i) {
            index = i;
            gui.addRenderableWidget(30 + i, new ToolTipButton(x + 149 + i % 2 * 12, y + 80 + i / 2 * 12, 12, 12, (Component)this.string(Character.toString(s.charAt(i))), T -> this.setTarget(index, false))).setToolTip((Component)DirectionList.getName(Direction.m_122376_((int)i)));
        }
        gui.m_142416_(new IconButton(x + 135, y + 20, 12, 12, new ItemStack((ItemLike)Items.f_42612_), T -> this.drainXP()).setToolTip("gui.ic2.villager.drain"));
    }

    private void drainXP() {
        this.tile.sendToServer(2, 0);
    }

    private void trigger(int i) {
        TradeEntry entry = this.entries.get(i + this.slider.getCurrent());
        VillagerTrade main = entry.getMainTrade();
        boolean result = !main.getState();
        for (VillagerTrade trade : entry.trades()) {
            int index;
            VillagerTradeList list = this.tile.trades.getTrade(trade.getOwner());
            if (list == null || (index = list.findIndex(trade)) == -1) continue;
            trade.setState(result);
            this.tile.sendToServer("info", new VillagerBuffer(trade.getOwner(), index, result));
        }
    }

    private void setTarget(int dir, boolean input) {
        if (input) {
            this.tile.importDir = Direction.m_122376_((int)dir);
        } else {
            this.tile.exportDir = Direction.m_122376_((int)dir);
        }
        this.tile.sendToServer(input ? 0 : 1, dir);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int i;
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        this.gui.drawTextureRegion(matrix, x + XP_BOX.getX(), y + XP_BOX.getY(), 0.0f, 222.0f, XP_BOX.getWidth(), XP_BOX.getHeight());
        if (this.tile.storedXP > 0) {
            float progress = (float)this.tile.storedXP / 100000.0f;
            this.gui.drawTextureRegion(matrix, x + XP_BOX.getX(), y + XP_BOX.getY(), 0.0f, 227.0f, (float)XP_BOX.getWidth() * progress, XP_BOX.getHeight());
        }
        for (i = 0; i < 4; ++i) {
            this.gui.getButton((int)(10 + i)).f_93624_ = i + this.slider.getCurrent() < this.entries.size();
        }
        for (i = 0; i < 6; ++i) {
            this.gui.getButton((int)(20 + i)).f_93623_ = this.tile.importDir.m_122411_() != i;
            this.gui.getButton((int)(30 + i)).f_93623_ = this.tile.exportDir.m_122411_() != i;
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        for (int i = 0; i < 4 && i + this.slider.getCurrent() < this.entries.size(); ++i) {
            VillagerTrade entry = this.entries.get(i + this.slider.getCurrent()).getMainTrade();
            this.gui.drawItemStackWithDeco(matrix, 30.0f, 34 + i * 20, this.adjustStack(entry.getMainItem()));
            this.gui.drawItemStackWithDeco(matrix, 60.0f, 34 + i * 20, this.adjustStack(entry.getSubItem()));
            this.gui.drawItemStackWithDeco(matrix, 98.0f, 34 + i * 20, this.adjustStack(entry.getOutputItem()));
            this.gui.bindDefaultTexture();
            this.gui.drawTextureRegion(matrix, 85.0f, 40 + i * 20, entry.isOutOfStock() ? 25.0f : 15.0f, 232.0f, 10.0f, 9.0f, -1);
            if (!this.target.target().isGroup() && entry.isDifferentPrice()) {
                this.gui.drawTextureRegion(matrix, 38.0f, 43 + i * 20, 0.0f, 232.0f, 9.0f, 9.0f, -1);
                this.gui.drawCenterString(matrix, (Component)Component.m_237113_((String)Integer.toString(entry.getRealPrice())), 56, 45 + i * 20, -1);
            }
            this.gui.bindTexture(InventoryMenu.f_39692_);
            IC2Screen.drawTexture(matrix, 119.0f, 40 + i * 20, this.gui.m_93252_(), IC2Textures.getMappedEntriesIC2("misc/gui").get(entry.getState() ? "yes" : "no"), 10.0f, 10.0f);
        }
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.villager.in"), 17, 52, 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.villager.out"), 162, 52, 0x404040);
        this.gui.drawItemStack(matrix, 6.0f, 60.0f, this.findBlock(this.tile.importDir));
        this.gui.drawItemStack(matrix, 151.0f, 60.0f, this.findBlock(this.tile.exportDir));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (XP_BOX.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.villager.xp", this.tile.storedXP));
        }
    }

    private ItemStack findBlock(Direction dir) {
        Block block = this.tile.m_58904_().m_8055_(this.tile.m_58899_().m_121945_(dir)).m_60734_();
        if (!block.m_49966_().m_60795_() && block.m_5456_() != Items.f_41852_) {
            return new ItemStack((ItemLike)block);
        }
        return new ItemStack((ItemLike)Items.f_42127_);
    }

    private ItemStack adjustStack(ItemStack stack) {
        return this.target.target().isGroup() ? (stack.m_41619_() ? stack : StackUtil.copyWithSize(stack, 1)) : stack;
    }

    record SimpleTrade(Item main, Item sub, Item out) {
        SimpleTrade(VillagerTrade trade) {
            this(trade.getMainItem().m_41720_(), trade.getSubItem().m_41720_(), trade.getOutputItem().m_41720_());
        }
    }

    record TradeEntry(List<VillagerTrade> trades) {
        TradeEntry(VillagerTrade trade) {
            this((List<VillagerTrade>)ObjectLists.singleton((Object)trade));
        }

        public VillagerTrade getMainTrade() {
            return this.trades.get(0);
        }
    }
}

