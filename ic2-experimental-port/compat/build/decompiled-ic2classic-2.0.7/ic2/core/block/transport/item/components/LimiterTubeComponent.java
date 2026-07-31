/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.transport.item.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.transport.item.tubes.LimiterTubeTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class LimiterTubeComponent
extends GuiWidget {
    public static final Box2i PROGRESS_BOX = new Box2i(155, 17, 11, 112);
    LimiterTubeTileEntity tile;
    IHasInventory inv;

    public LimiterTubeComponent(LimiterTubeTileEntity tile, IHasInventory inv) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.inv = inv;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        for (int i = 0; i < 16; ++i) {
            int xOff = i % 7;
            int yOff = i / 7;
            int index = i;
            boolean visible = i < this.tile.usedColors.size();
            gui.addRenderableWidget((int)i, new IconButton((int)(x + 8 + xOff * 20), (int)(y + 17 + yOff * 20), (int)20, (int)20, (ItemStack)(visible ? new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)((DyeColor)this.tile.usedColors.get((int)i)))) : ItemStack.f_41583_), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$0(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((LimiterTubeComponent)this, (int)index))).f_93624_ = visible;
        }
        gui.addRenderableWidget(110, new ExtendedButton(x + 97, y + 82, 36, 14, (Component)this.translate("gui.ic2.color_tube.add"), T -> this.add()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        for (int i = 0; i < 16; ++i) {
            boolean visible = i < this.tile.usedColors.size();
            gui.getCastedButton((int)i, IconButton.class).setDisplay((ItemStack)(visible ? new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)((DyeColor)this.tile.usedColors.get((int)i)))) : ItemStack.f_41583_)).f_93624_ = visible;
        }
        gui.getButton((int)110).f_93623_ = !this.inv.getStackInSlot(0).m_41619_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        for (int i = 0; i < 16; ++i) {
            IconButton button = this.gui.getCastedButton(i, IconButton.class);
            if (!button.m_198029_() || !button.f_93624_) continue;
            tooltips.accept(button.getDisplay().m_41786_());
            tooltips.accept((Component)this.buildKeyDescription(KeyHelper.SNEAK_KEY, "gui.ic2.color_tube.to_delete", new Object[0]).m_130940_(ChatFormatting.GRAY));
            break;
        }
    }

    private void removeIndex(int index) {
        if (Screen.m_96638_()) {
            this.tile.sendToServer(1, index);
        }
    }

    private void add() {
        if (this.inv.getStackInSlot(0).m_41619_()) {
            return;
        }
        DyeColor color = DyeColor.getColor((ItemStack)this.inv.getStackInSlot(0));
        if (color == null) {
            return;
        }
        this.tile.sendToServer(2, color.m_41060_());
    }

    private /* synthetic */ void lambda$init$0(int index, Button T) {
        this.removeIndex(index);
    }
}

