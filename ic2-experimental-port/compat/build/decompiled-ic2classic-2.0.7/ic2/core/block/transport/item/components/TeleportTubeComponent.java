/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.transport.item.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.transport.item.tubes.TeleportTubeTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class TeleportTubeComponent
extends GuiWidget {
    TeleportTubeTileEntity tile;
    String id = "";
    int flags = 0;

    public TeleportTubeComponent(TeleportTubeTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        boolean canChange = this.tile.canDoAction(gui.getPlayerID(), Action.TELEPORT_SETTINGS, false);
        gui.addRenderableWidget((int)0, new ItemCheckBox((int)(x + 5), (int)(y + 56), (int)16, (int)16, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$0(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleportTubeComponent)this), (ItemStack)IC2Items.ICON_DISPLAY.create((int)3), (boolean)((this.tile.state & 1) != 0 ? true : false))).setToolTip((String)"gui.ic2.teleport_tube.send").f_93623_ = this.tile.isPublic() || canChange;
        gui.addRenderableWidget((int)1, new ItemCheckBox((int)(x + 23), (int)(y + 56), (int)16, (int)16, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleportTubeComponent)this), (ItemStack)IC2Items.ICON_DISPLAY.create((int)4), (boolean)((this.tile.state & 2) != 0 ? true : false))).setToolTip((String)"gui.ic2.teleport_tube.receive").f_93623_ = this.tile.isPublic() || canChange;
        gui.addRenderableWidget((int)2, new ItemCheckBox((int)(x + 41), (int)(y + 56), (int)16, (int)16, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TeleportTubeComponent)this), (ItemStack)new ItemStack((ItemLike)Items.f_42740_), (boolean)((this.tile.state & 4) != 0 ? true : false))).setToolTip((String)"gui.ic2.personal.mode.private").f_93623_ = this.tile.canDoAction(gui.getPlayerID(), Action.TELEPORT_SETTINGS, true);
        gui.addRenderableWidget(4, new ExtendedButton(x + 5, y + 74, 89, 16, (Component)this.translate("gui.ic2.chunkloader.confirm"), T -> this.save()));
        ImprovedTextWidget text = gui.addRenderableWidget(3, new ImprovedTextWidget(x + 8, y + 43, 87, 14));
        text.m_94144_(this.tile.frequency);
        text.m_94182_(false);
        text.m_94199_(14);
        text.m_94190_(true);
        text.m_94151_(T -> {
            this.id = T;
        });
        this.flags = this.tile.state;
        this.id = this.tile.frequency;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        boolean canChange = this.tile.canDoAction(gui.getPlayerID(), Action.TELEPORT_SETTINGS, false);
        gui.getCastedButton((int)0, ItemCheckBox.class).setChecked((boolean)((this.flags & 1) != 0 ? true : false)).f_93623_ = this.tile.isPublic() || canChange;
        gui.getCastedButton((int)1, ItemCheckBox.class).setChecked((boolean)((this.flags & 2) != 0 ? true : false)).f_93623_ = this.tile.isPublic() || canChange;
        gui.getCastedButton((int)2, ItemCheckBox.class).setChecked((boolean)((this.flags & 4) != 0 ? true : false)).f_93623_ = this.tile.canDoAction(gui.getPlayerID(), Action.TELEPORT_SETTINGS, true);
        gui.getButton((int)4).f_93623_ = this.flags != this.tile.state || !this.id.equals(this.tile.frequency);
    }

    private void toggle(int flag) {
        this.flags ^= flag;
    }

    private void save() {
        this.tile.sendToServer("frequency", new NBTBuffer("frequency", (Tag)StringTag.m_129297_((String)this.id)));
        this.tile.sendToServer(0, this.flags);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.teleport_tube.networkid"), 7, 32, 0x404040);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        return keyCode == 69 && this.gui.getCastedButton(3, ImprovedTextWidget.class).m_93696_();
    }

    private /* synthetic */ void lambda$init$2(Button T) {
        this.toggle(4);
    }

    private /* synthetic */ void lambda$init$1(Button T) {
        this.toggle(2);
    }

    private /* synthetic */ void lambda$init$0(Button T) {
        this.toggle(1);
    }
}

