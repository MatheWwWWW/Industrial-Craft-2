/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  ic2.core.inventory.gui.IC2Screen
 *  ic2.core.inventory.gui.components.GuiWidget
 *  ic2.core.inventory.gui.components.GuiWidget$ActionRequest
 *  ic2.core.utils.helpers.StackUtil
 *  ic2.core.utils.math.geometry.Box2i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package trinsdar.gravisuit.items.container;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.items.container.GuiCompRelocatorBar;

public class GuiCompRelocatorDisplay
extends GuiWidget {
    List<GuiCompRelocatorBar> buttons;
    ItemStack relocator;
    Player player;
    InteractionHand hand;

    public GuiCompRelocatorDisplay(Player player, InteractionHand hand) {
        super(new Box2i(3, 3, 170, 110));
        this.relocator = player.m_21120_(hand);
        this.player = player;
        this.hand = hand;
        this.buttons = new ArrayList<GuiCompRelocatorBar>();
    }

    public void init(IC2Screen gui) {
        super.init(gui);
        this.reload();
    }

    private void reload() {
        CompoundTag map;
        CompoundTag nbt = StackUtil.getNbtData((ItemStack)this.relocator);
        this.buttons.clear();
        if (nbt.m_128441_("Locations") && (map = nbt.m_128469_("Locations")).m_128440_() > 0 && map.m_128440_() < 11) {
            int i = 0;
            for (String name : map.m_128431_()) {
                GuiCompRelocatorBar bar = new GuiCompRelocatorBar(this.hand, i, this.player, name, this.relocator);
                bar.setOwner(this.gui);
                this.buttons.add(bar);
                ++i;
            }
        }
    }

    protected void addRequests(Set<GuiWidget.ActionRequest> set) {
        set.addAll(Arrays.asList(GuiWidget.ActionRequest.GUI_INIT, GuiWidget.ActionRequest.MOUSE_INPUT, GuiWidget.ActionRequest.DRAW_BACKGROUND, GuiWidget.ActionRequest.DRAW_FOREGROUND, GuiWidget.ActionRequest.TOOLTIP));
    }

    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.buttons.forEach(g -> g.drawBackground(matrix, mouseX, mouseY, partialTicks));
    }

    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.buttons.forEach(g -> g.drawForeground(matrix, mouseX, mouseY));
    }

    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        boolean changed = this.buttons.stream().anyMatch(g -> g.onMouseClick(mouseX, mouseY, mouseButton));
        if (changed) {
            this.reload();
        }
        return changed;
    }
}

