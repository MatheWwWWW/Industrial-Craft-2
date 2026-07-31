/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.generators.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.energy.EnergyNet;
import ic2.core.block.storage.tiles.CreativeSourceTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class CreativeSourceComponent
extends GuiWidget {
    public static final String[] TIERS = new String[]{"UV", "LV", "MV", "HV", "EV", "IV", "LuV"};
    CreativeSourceTileEntity tile;
    int lastPacket;
    int lastEnergy;

    public CreativeSourceComponent(CreativeSourceTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
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
        this.lastEnergy = this.tile.packetEnergy;
        this.lastPacket = this.tile.packets;
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        MutableComponent ctrl = this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.ctrl.name"), "10x");
        MutableComponent shift = this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.shift.name"), "100x");
        MutableComponent alt = this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.alt.name"), "1000x");
        MutableComponent all = this.string().m_7220_((Component)ctrl).m_130946_(Character.toString('\n')).m_7220_((Component)shift).m_130946_(Character.toString('\n')).m_7220_((Component)alt);
        gui.addRenderableWidget(0, new ToolTipButton(x + 157, y + 18, 12, 12, (Component)this.string("+"), T -> this.onPacket(1)).setToolTip((Component)ctrl));
        gui.addRenderableWidget(1, new ToolTipButton(x + 157, y + 35, 12, 12, (Component)this.string("+"), T -> this.onEnergy(1)).setToolTip((Component)all));
        gui.addRenderableWidget(2, new ToolTipButton(x + 7, y + 18, 12, 12, (Component)this.string("-"), T -> this.onPacket(-1)).setToolTip((Component)ctrl));
        gui.addRenderableWidget(3, new ToolTipButton(x + 7, y + 35, 12, 12, (Component)this.string("-"), T -> this.onEnergy(-1)).setToolTip((Component)all));
        gui.addRenderableWidget((int)4, new ToolTipButton((int)(x + 119), (int)(y + 70), (int)50, (int)12, (Component)this.translate((String)"gui.ic2.creative_source.confirm"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$4(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((CreativeSourceComponent)this)).setToolTip((Component)all)).f_93623_ = false;
        int xOffset = 13;
        for (int i = 0; i < TIERS.length; ++i) {
            int index = i;
            int extra = gui.getFont().m_92895_(TIERS[i]) + 6;
            gui.m_142416_(new ExtendedButton(x + xOffset, y + 55, extra, 12, (Component)this.string(TIERS[i]), T -> this.sendTier(index)));
            xOffset += extra + 3;
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton((int)0).f_93623_ = this.tile.packets < 64;
        gui.getButton((int)1).f_93623_ = this.tile.packetEnergy <= Short.MAX_VALUE;
        gui.getButton((int)2).f_93623_ = this.tile.packets > 1;
        gui.getButton((int)3).f_93623_ = this.tile.packetEnergy > 1;
        gui.getButton((int)4).f_93623_ = this.tile.packetEnergy != this.lastEnergy || this.tile.packets != this.lastPacket;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void confirmChange() {
        this.tile.sendToServer(0, this.tile.packets);
        this.tile.sendToServer(1, this.tile.packetEnergy);
        this.lastPacket = this.tile.packets;
        this.lastEnergy = this.tile.packetEnergy;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onPacket(int effect) {
        this.tile.packets = Mth.m_14045_((int)(this.tile.packets + (effect *= Screen.m_96637_() ? 10 : 1)), (int)1, (int)64);
    }

    public void sendTier(int tier) {
        this.tile.packetEnergy = EnergyNet.INSTANCE.getPowerFromTier(tier);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onEnergy(int effect) {
        effect *= Screen.m_96637_() ? 10 : 1;
        effect *= Screen.m_96638_() ? 100 : 1;
        this.tile.packetEnergy = Mth.m_14045_((int)(this.tile.packetEnergy + (effect *= Screen.m_96639_() ? 1000 : 1)), (int)1, (int)32768);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.creative_source.eu", Formatters.EU_FORMAT.format(this.tile.packetEnergy)), 83, 37, 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.creative_source.packets", Formatters.EU_FORMAT.format(this.tile.packets)), 83, 20, 0x404040);
    }

    private /* synthetic */ void lambda$init$4(Button T) {
        this.confirmChange();
    }
}

