/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.mv;

import ic2.core.block.machines.components.mv.planner.LiveDataTabComponent;
import ic2.core.block.machines.components.mv.planner.LogicButtonComponent;
import ic2.core.block.machines.components.mv.planner.MainScreenComponent;
import ic2.core.block.machines.components.mv.planner.NamingTabComponent;
import ic2.core.block.machines.components.mv.planner.PredictionTabComponent;
import ic2.core.block.machines.components.mv.planner.ReactorButtonsComponent;
import ic2.core.block.machines.components.mv.planner.StatisticsTabComponent;
import ic2.core.block.machines.logic.planner.PlannerInventory;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.LockedSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReactorPlannerContainer
extends ContainerComponent<ReactorPlannerTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_reactor_planner.png");

    public ReactorPlannerContainer(ReactorPlannerTileEntity key, Player player, int id) {
        super(key, player, id);
        int y;
        int x;
        int i;
        int remove = key.getReactorSize() * 9;
        for (i = 0; i < 54; ++i) {
            x = i % 9;
            y = i / 9;
            this.m_38897_(new SimulationSlot(key, i, 61 + x * 18 - remove, 21 + y * 18));
        }
        for (i = 0; i < 18; ++i) {
            x = i % 3;
            y = i / 3;
            this.m_38897_(new LockedSlot(key.selection, i, 221 + 18 * x, 21 + 18 * y));
        }
        this.addHiddenPlayerInventory(player.m_150109_());
        this.addComponent(new MainScreenComponent(key));
        this.addComponent(new ReactorButtonsComponent(key));
        this.addComponent(new LiveDataTabComponent(key));
        this.addComponent(new StatisticsTabComponent(key));
        this.addComponent(new PredictionTabComponent(key));
        this.addComponent(new NamingTabComponent(key));
        this.addComponent(new LogicButtonComponent(key));
    }

    public void updateSlots() {
        for (Slot slot : this.f_38839_) {
            if (!(slot instanceof SimulationSlot)) continue;
            ((SimulationSlot)slot).updatePos();
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onSlotSelected() {
        int slot = ((ReactorPlannerTileEntity)this.getHolder()).selectedSlot;
        if (slot == -1) {
            this.m_142503_(ItemStack.f_41583_);
            return;
        }
        PlannerInventory inventory = ((ReactorPlannerTileEntity)this.getHolder()).selection;
        if (slot >= inventory.getTotalItems()) {
            return;
        }
        this.m_142503_(inventory.getCurrentItem(slot).m_41777_());
    }

    @OnlyIn(value=Dist.CLIENT)
    public void setupNameChanged() {
        this.getComponent(NamingTabComponent.class).onChanged();
        this.getComponent(LogicButtonComponent.class).onChanged();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(212, 212);
        screen.clearFlag(33);
        screen.setFlag(4);
    }

    @Override
    public void m_6877_(Player playerIn) {
        this.m_142503_(ItemStack.f_41583_);
        super.m_6877_(playerIn);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId < 0) {
            return;
        }
        Slot slot = this.m_38853_(slotId);
        if (slot instanceof SimulationSlot) {
            if (dragType == 2) {
                ((ReactorPlannerTileEntity)this.getHolder()).pickItem(slot.getSlotIndex());
                return;
            }
            ((ReactorPlannerTileEntity)this.getHolder()).setComponentInSlot(slot.getSlotIndex(), dragType == 1);
            if (((ReactorPlannerTileEntity)this.getHolder()).isSimulating()) {
                this.clearSlots(SimulationSlot.class);
                this.m_38946_();
            }
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }

    public static class SimulationSlot
    extends LockedSlot {
        ReactorPlannerTileEntity tile;

        public SimulationSlot(ReactorPlannerTileEntity tile, int index, int xPosition, int yPosition) {
            super(tile.components, index, xPosition, yPosition);
            this.tile = tile;
        }

        public void updatePos() {
            this.setX(61 + (this.getSlotIndex() % 9 * 18 - this.tile.getReactorSize() * 9));
        }

        public boolean m_6659_() {
            return this.getSlotIndex() % 9 < this.tile.getReactorSize() + 3;
        }
    }
}

