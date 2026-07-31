/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.readers.ISpeedMachine;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpeedComponent
extends GuiWidget {
    public static Vec2i DEFAULT = new Vec2i(5, 36);
    ISpeedMachine machine;
    Component text;
    Vec2i pos;

    public SpeedComponent(ISpeedMachine machine, Component text) {
        this(machine, text, DEFAULT);
    }

    public SpeedComponent(ISpeedMachine machine, Component text, Vec2i pos) {
        super(Box2i.EMPTY_BOX);
        this.machine = machine;
        this.text = text;
        this.pos = pos;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, this.text, this.pos.getX(), this.pos.getY(), 0x404040);
        this.gui.drawString(matrix, (Component)this.string(ItemStack.f_41584_.format((float)this.machine.getSpeed() / (float)this.machine.getMaxSpeed() * 100.0f) + "%"), this.pos.getX(), this.pos.getY() + 8, 0x404040);
    }
}

