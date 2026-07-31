/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.nv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.containers.nv.IndustrialWorkbenchContainer;
import ic2.core.block.machines.tiles.nv.IndustrialWorkbenchTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.slot.MemorySlot;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class IndustrialWorkbenchComponent
extends GuiWidget {
    IndustrialWorkbenchTileEntity tile;

    public IndustrialWorkbenchComponent(IndustrialWorkbenchTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ExtendedButton(x + 97, y + 16, 40, 10, (Component)this.translate("gui.ic2.industrial_workbench.save"), T -> this.tile.sendToServer(2, 0)));
        gui.addRenderableWidget(1, new ExtendedButton(x + 84, y + 16, 10, 10, (Component)this.string("X"), T -> this.tile.sendToServer(2, 1)));
        gui.addRenderableWidget(2, new ItemCheckBox(x + 152, y + 54, 16, 16, T -> this.flipRecipeFlag(16), new ItemStack((ItemLike)Items.f_42516_), (this.tile.currentRecipe.flags & 0x10) != 0)).setToolTip("gui.ic2.industrial_workbench.nbt");
        gui.addRenderableWidget(5, new ItemCheckBox(x + 8, y + 35, 16, 16, T -> this.flipFlag(2), new ItemStack((ItemLike)Items.f_42155_), (this.tile.flags & 2) != 0)).setToolTip("gui.ic2.industrial_workbench.restock");
        gui.addRenderableWidget(6, new ItemCheckBox(x + 8, y + 53, 16, 16, T -> this.flipFlag(1), new ItemStack((ItemLike)Items.f_41960_), (this.tile.flags & 1) != 0)).setToolTip("gui.ic2.industrial_workbench.craft");
        gui.m_142416_(new IconButton(x + 98, y + 53, 16, 16, new ItemStack((ItemLike)Items.f_42694_), T -> this.tile.sendToServer(6, 0))).setToolTip("gui.ic2.industrial_workbench.tube_net");
    }

    public void flipRecipeFlag(int flag) {
        this.tile.currentRecipe.flags ^= flag;
        this.tile.sendToServer(0, this.tile.currentRecipe.flags);
    }

    public void flipFlag(int flag) {
        this.tile.flags ^= flag;
        this.tile.sendToServer(1, this.tile.flags);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getCastedButton(2, ItemCheckBox.class).setChecked((this.tile.currentRecipe.flags & 0x10) != 0);
        gui.getCastedButton(5, ItemCheckBox.class).setChecked((this.tile.flags & 2) != 0);
        gui.getCastedButton(6, ItemCheckBox.class).setChecked((this.tile.flags & 1) != 0);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        for (Slot slot : this.gui.getCastedContainer(IndustrialWorkbenchContainer.class).f_38839_) {
            if (!(slot instanceof MemorySlot) || slot.m_6659_()) continue;
            this.gui.makeSlotFunction(matrix, slot);
        }
    }
}

