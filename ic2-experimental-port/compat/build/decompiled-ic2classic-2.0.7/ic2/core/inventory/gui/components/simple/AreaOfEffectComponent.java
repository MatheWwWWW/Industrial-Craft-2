/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.inventory.gui.components.simple;

import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class AreaOfEffectComponent
extends GuiWidget {
    BlockEntity tile;
    IAreaOfEffect effect;
    Box2i buttonBox;

    public <T extends BlockEntity> AreaOfEffectComponent(T tile, Box2i buttonBox) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.effect = (IAreaOfEffect)tile;
        this.buttonBox = buttonBox;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.m_142416_(new ExtendedButton(x + this.buttonBox.getX(), y + this.buttonBox.getY(), this.buttonBox.getWidth(), this.buttonBox.getHeight(), (Component)this.translate("gui.ic2.player_detector.visualize"), T -> this.visualize()));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void visualize() {
        if (this.effect.getVisualizationId() != -1) {
            this.effect.setVisualizationId(-1);
            return;
        }
        this.effect.setVisualizationId(this.tile.m_58904_().f_46441_.m_188502_());
        BlockHighlighter.INSTANCE.addHighlight(this.tile.m_58899_(), 500, this.effect.getAreaOfEffectColor(), true, T -> {
            T.renderBox = this.effect.getAreaOfEffect();
            if (T.ticksLeft == 1) {
                this.effect.setVisualizationId(-1);
            }
            return this.effect.getVisualizationId() != -1 && !this.tile.m_58901_();
        });
    }
}

