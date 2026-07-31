/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 */
package ic2.core.inventory.container;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.slot.HiddenPlayerSlot;
import ic2.core.inventory.slot.LockablePlayerSlot;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.function.BooleanSupplier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public abstract class ContainerHasGui<T extends IHasGui>
extends IC2Container {
    T gui;
    Player player;
    protected boolean curioAdded;
    public boolean cosmeticCurio = false;
    protected boolean addingCurio = false;
    Vec2i curioPos;
    int scrollOffset;

    public ContainerHasGui(T gui, Player player, int id) {
        super(id);
        this.gui = gui;
        this.player = player;
    }

    public boolean m_6875_(Player playerIn) {
        return this.gui.canInteractWith(playerIn);
    }

    public void m_6877_(Player playerIn) {
        this.gui.onGuiClosed(playerIn);
        super.m_6877_(playerIn);
    }

    public T getHolder() {
        return this.gui;
    }

    public Player getPlayer() {
        return this.player;
    }

    protected void addCurioSlots() {
        this.addCurioSlots(new Vec2i());
    }

    protected void addCurioSlots(Vec2i button) {
        this.addCurioSlots(new Vec2i(), button);
    }

    protected void addCurioSlots(Vec2i curioPos, Vec2i button) {
        if (IC2.CURIO_PLUGIN == null) {
            return;
        }
        this.curioPos = curioPos;
        this.addingCurio = true;
        this.curioAdded = IC2.CURIO_PLUGIN.addCurioSlots(this, this.player, curioPos, button, 0, false);
        this.addingCurio = false;
    }

    public void addCurioSlots(Slot slot) {
        if (IC2.CURIO_PLUGIN == null || !this.addingCurio) {
            return;
        }
        this.m_38897_(slot);
    }

    public void setCurioOffset(int offset) {
        if (!this.curioAdded || this.scrollOffset == offset) {
            return;
        }
        this.scrollOffset = offset;
        this.cosmeticCurio = false;
        this.addingCurio = true;
        IC2.CURIO_PLUGIN.addCurioSlots(this, this.player, this.curioPos, new Vec2i(0, 0), offset, true);
        this.addingCurio = false;
    }

    protected void addInternalSlots(Inventory player) {
    }

    public void addHiddenPlayerInventory(Inventory player) {
        this.addInternalSlots(player);
        for (int i = 0; i < 36; ++i) {
            this.m_38897_(new HiddenPlayerSlot((Container)player, i));
        }
    }

    public void addLockablePlayerInventory(Inventory player, BooleanSupplier isLocked) {
        this.addInternalSlots(player);
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.m_38897_(new LockablePlayerSlot((Container)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18, isLocked));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.m_38897_(new LockablePlayerSlot((Container)player, j, 8 + j * 18, 142, isLocked));
        }
    }

    public void addLockablePlayerInventoryWithOffset(Inventory player, int x, int y, BooleanSupplier isLocked) {
        this.addInternalSlots(player);
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.m_38897_(new LockablePlayerSlot((Container)player, k + i * 9 + 9, x + 8 + k * 18, y + 84 + i * 18, isLocked));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.m_38897_(new LockablePlayerSlot((Container)player, j, x + 8 + j * 18, 142 + y, isLocked));
        }
    }

    public void addPlayerInventory(Inventory player) {
        this.addInternalSlots(player);
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.m_38897_(new Slot((Container)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot((Container)player, j, 8 + j * 18, 142));
        }
    }

    public void addPlayerInventoryWithOffset(Inventory player, int x, int y) {
        this.addInternalSlots(player);
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.m_38897_(new Slot((Container)player, k + i * 9 + 9, x + 8 + k * 18, y + 84 + i * 18));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot((Container)player, j, x + 8 + j * 18, 142 + y));
        }
    }
}

