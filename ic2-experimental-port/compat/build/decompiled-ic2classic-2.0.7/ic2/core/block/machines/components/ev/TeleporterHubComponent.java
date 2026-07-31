/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.ev;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.block.machines.tiles.luv.TeleporterHubTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class TeleporterHubComponent
extends GuiWidget {
    static final Box2i TELEPORT_BOX = new Box2i(17, 17, 100, 9);
    TeleporterHubTileEntity tile;
    int offset = 0;
    boolean rename;
    int renameIndex;

    public TeleporterHubComponent(TeleporterHubTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        List<Tuple<TeleporterTarget, String>> targets = this.tile.targets.getClientTargets();
        for (int i = 0; i < 8; ++i) {
            int index = i;
            gui.addRenderableWidget((int)i, new IconButton((int)(x + 7), (int)(y + 17 + i * 11), (int)10, (int)10, (ItemStack)IC2Items.ICON_DISPLAY.create((int)1), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$0(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleporterHubComponent)this, (int)index)).setIconOnly().setToolTip((String)"gui.ic2.teleporter_hub.delete")).f_93624_ = targets.size() > index;
            gui.addRenderableWidget((int)(8 + i), new ExtendedButton((int)(x + 118), (int)(y + 17 + i * 11), (int)50, (int)10, (Component)this.translate((String)"gui.ic2.teleporter_hub.rename"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleporterHubComponent)this, (int)index))).f_93624_ = targets.size() > index;
        }
        gui.addRenderableWidget((int)100, new ExtendedButton((int)(x + 6), (int)(y + 106), (int)20, (int)10, (Component)this.string((String)"+"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleporterHubComponent)this))).f_93623_ = this.offset < Math.max(targets.size() - 8, 0);
        gui.addRenderableWidget((int)101, new ExtendedButton((int)(x + 6), (int)(y + 5), (int)20, (int)10, (Component)this.string((String)"-"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleporterHubComponent)this))).f_93623_ = this.offset > 0;
        gui.addRenderableWidget(102, new ExtendedButton(x + 27, y + 106, 50, 10, (Component)this.translate("gui.ic2.teleporter_hub.rename"), T -> this.openRename(-1)));
        EditBox text = gui.addRenderableWidget(103, new EditBox(gui.getFont(), x + 80, y + 106, 70, 10, (Component)this.string()));
        text.m_94199_(19);
        text.f_93624_ = false;
        gui.addRenderableWidget((int)104, new IconButton((int)(x + 152), (int)(y + 106), (int)16, (int)16, (ItemStack)IC2Items.ICON_DISPLAY.create((int)1), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$5(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleporterHubComponent)this)).setToolTip((String)"gui.ic2.teleporter_hub.rename_cancel")).f_93624_ = false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        List<Tuple<TeleporterTarget, String>> targets = this.tile.targets.getClientTargets();
        for (int i = 0; i < 8; ++i) {
            boolean visible;
            gui.getButton((int)i).f_93624_ = visible = targets.size() > i + this.offset;
            gui.getButton((int)(i + 8)).f_93624_ = visible;
        }
        gui.getButton((int)100).f_93623_ = this.offset < Math.max(targets.size() - 8, 0);
        gui.getButton((int)101).f_93623_ = this.offset > 0;
        EditBox text = gui.getCastedButton(103, EditBox.class);
        text.f_93624_ = this.rename;
        if (!this.rename) {
            gui.getButton(104).m_5755_(false);
        }
        gui.getButton((int)104).f_93624_ = this.rename;
        if (this.rename && gui.m_7222_() != text) {
            gui.m_94718_((GuiEventListener)text);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        List<Tuple<TeleporterTarget, String>> targets = this.tile.targets.getClientTargets();
        for (int i = 0; i < 8 && i + this.offset < targets.size(); ++i) {
            Tuple<TeleporterTarget, String> target = targets.get(i + this.offset);
            this.gui.getFont().m_92883_(matrix, (String)target.m_14419_(), 18.0f, (float)(18 + 11 * i), this.tile.hasEnergy(this.tile.getCost((TeleporterTarget)targets.get(i + this.offset).m_14418_(), (Player)this.gui.getPlayer())) ? -1 : -61696);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (this.rename && this.gui.getButton(103).m_5953_((double)(mouseX + this.gui.getGuiLeft()), (double)(mouseY + this.gui.getGuiTop()))) {
            tooltips.accept((Component)this.translate("gui.ic2.teleporter_hub.enter"));
        }
        LocalPlayer player = this.gui.getPlayer();
        List<Tuple<TeleporterTarget, String>> targets = this.tile.targets.getClientTargets();
        for (int i = 0; i < 8; ++i) {
            int index = i + this.offset;
            if (targets.size() <= index || !TELEPORT_BOX.isInBox(mouseX, mouseY - 11 * i) || TELEPORT_BOX.getX() < mouseX - this.gui.getFont().m_92895_((String)targets.get(index).m_14419_())) continue;
            int cost = this.tile.getCost((TeleporterTarget)targets.get(i + this.offset).m_14418_(), (Player)player);
            tooltips.accept((Component)this.translate("gui.ic2.teleporter_hub.cost", Formatters.EU_FORMAT.format(cost)));
            if (this.tile.hasEnergy(cost)) {
                tooltips.accept((Component)this.translate("gui.ic2.teleporter_hub.teleport"));
                continue;
            }
            tooltips.accept((Component)this.translate("gui.ic2.teleporter_hub.missing"));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        LocalPlayer player = this.gui.getPlayer();
        List<Tuple<TeleporterTarget, String>> targets = this.tile.targets.getClientTargets();
        for (int i = 0; i < 8; ++i) {
            int index = i + this.offset;
            if (targets.size() <= index || !TELEPORT_BOX.isInBox(mouseX, mouseY - 11 * i) || TELEPORT_BOX.getX() < mouseX - this.gui.getFont().m_92895_((String)targets.get(index).m_14419_()) || !this.tile.hasEnergy(this.tile.getCost((TeleporterTarget)targets.get(index).m_14418_(), (Player)player))) continue;
            this.teleport(i);
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        if (this.rename) {
            if (keyCode == 69) {
                return true;
            }
            if (keyCode == 256) {
                this.cancelRename();
                return true;
            }
            if (keyCode == 257) {
                this.applyRename(this.gui);
                return true;
            }
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        if (this.rename) {
            return false;
        }
        this.offset = Mth.m_14045_((int)(this.offset - scroll), (int)0, (int)Math.max(0, this.tile.targets.getClientTargets().size() - 8));
        return true;
    }

    protected void move(int index) {
        if (this.rename) {
            return;
        }
        this.offset = Mth.m_14045_((int)(this.offset + index), (int)0, (int)Math.max(0, this.tile.targets.getClientTargets().size() - 8));
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void applyRename(IC2Screen gui) {
        this.rename = false;
        String name = gui.getCastedButton(103, EditBox.class).m_94155_();
        if (this.renameIndex < 0) {
            this.tile.sendToServer("internal_rename", new NBTBuffer("newName", (Tag)StringTag.m_129297_((String)name)));
            return;
        }
        CompoundTag nbt = new CompoundTag();
        nbt.m_128359_("newName", name);
        ((TeleporterTarget)this.tile.targets.getClientTargets().get(this.offset + this.renameIndex).m_14418_()).write(nbt);
        this.tile.sendToServer("rename", new NBTBuffer(nbt));
    }

    protected void cancelRename() {
        this.rename = false;
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void openRename(int index) {
        this.rename = true;
        this.renameIndex = index;
        EditBox text = this.gui.getCastedButton(103, EditBox.class);
        if (index < 0) {
            text.m_94144_((this.tile.m_8077_() ? this.tile.m_7770_() : this.tile.m_5446_()).getString());
            return;
        }
        text.m_94144_((String)this.tile.targets.getClientTargets().get(index + this.offset).m_14419_());
    }

    protected void teleport(int index) {
        this.tile.sendToServer("teleport", new NBTBuffer(((TeleporterTarget)this.tile.targets.getClientTargets().get(this.offset + index).m_14418_()).write(new CompoundTag())));
    }

    protected void delete(int index) {
        this.tile.sendToServer("delete", new NBTBuffer(((TeleporterTarget)this.tile.targets.getClientTargets().get(this.offset + index).m_14418_()).write(new CompoundTag())));
    }

    private /* synthetic */ void lambda$init$5(Button T) {
        this.cancelRename();
    }

    private /* synthetic */ void lambda$init$3(Button T) {
        this.move(-1);
    }

    private /* synthetic */ void lambda$init$2(Button T) {
        this.move(1);
    }

    private /* synthetic */ void lambda$init$1(int index, Button T) {
        this.openRename(index);
    }

    private /* synthetic */ void lambda$init$0(int index, Button T) {
        this.delete(index);
    }
}

