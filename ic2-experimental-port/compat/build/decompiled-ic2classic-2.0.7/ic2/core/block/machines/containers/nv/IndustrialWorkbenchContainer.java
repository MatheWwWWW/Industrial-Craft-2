/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.DataSlot
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.components.nv.IndustrialWorkbenchComponent;
import ic2.core.block.machines.components.nv.IndustrialWorkbenchRequest;
import ic2.core.block.machines.logic.crafter.Snapshot;
import ic2.core.block.machines.tiles.nv.IndustrialWorkbenchTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.GhostSlot;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.inventory.slot.MemorySlot;
import ic2.core.inventory.slot.RecursiveCraftSlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.item.misc.MemoryStickItem;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class IndustrialWorkbenchContainer
extends ContainerComponent<IndustrialWorkbenchTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_work_table.png");
    DataSlot saving = DataSlot.m_39401_();

    public IndustrialWorkbenchContainer(IndustrialWorkbenchTileEntity key, Player player, int id) {
        super(key, player, id);
        int x;
        int y;
        int i;
        this.disablePreviewer();
        this.m_38897_(new RecursiveCraftSlot(key.crafting_inventory, 9, 124, 35));
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.m_38897_(new LockedSlot.DragableLockedSlot(key.crafting_inventory, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
        for (y = 0; y < 3; ++y) {
            for (x = 0; x < 9; ++x) {
                this.m_38897_(new SlotBase(key, x + y * 9, 8 + 18 * x, 74 + 18 * y));
            }
        }
        for (int x2 = 0; x2 < 9; ++x2) {
            this.m_38897_(new SlotBase(key.garbageSlot, x2, 8 + 18 * x2, 131));
        }
        for (y = 0; y < 9; ++y) {
            for (x = 0; x < 3; ++x) {
                this.m_38897_(new MemorySlot(key, x + y * 3, 180 + 18 * x, 6 + 18 * y));
            }
        }
        this.m_38897_(new FilterSlot(key.memory, 0, 152, 17, new ClassFilter(MemoryStickItem.class)));
        this.m_38897_(new FilterSlot(key.memory, 1, 152, 35, new ClassFilter(MemoryStickItem.class)));
        for (i = 0; i < 12; ++i) {
            x = i % 6 * 18;
            int y2 = i / 6 * 18;
            this.m_38897_(new RequestSlot(key.requestCache, i, x - 112, y2 + 42, key.viewOpen));
        }
        this.m_38897_(new ClosableGhostSlot(key.requestedItem, 0, -104, 18, SpecialFilters.NO_TAGS, key.viewOpen));
        this.addLockablePlayerInventoryWithOffset(player.m_150109_(), 0, 75, this::isSaving);
        this.addComponent(new IndustrialWorkbenchComponent(key));
        this.addComponent(new IndustrialWorkbenchRequest(key, key.viewOpen));
        this.addComponent(new FilterComponent(new Vec2i(0, 83)).blockClosing());
        this.m_38895_(this.saving);
    }

    @Override
    public void m_6877_(Player playerIn) {
        if (this.isSaving()) {
            this.m_142503_(ItemStack.f_41583_);
        }
        super.m_6877_(playerIn);
    }

    @Override
    public int getInventorySize() {
        return 75;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(237, 240);
        screen.setPlayerInventoryOffset(0, 2);
        screen.setContainerOffset(-25, 0);
    }

    public boolean isSaving() {
        return this.saving.m_6501_() == 1;
    }

    public void setSaving(boolean value) {
        this.saving.m_6422_(value ? 1 : 0);
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId >= 0) {
            Slot slot = this.m_38853_(slotId);
            if (this.isSaving()) {
                if (slot instanceof MemorySlot && ((IndustrialWorkbenchTileEntity)this.getHolder()).isSimulating() && ((IndustrialWorkbenchTileEntity)this.getHolder()).saveRecipe(slot.getSlotIndex())) {
                    this.m_142503_(ItemStack.f_41583_);
                    ((ServerPlayer)player).f_8906_.m_9829_((Packet)new ClientboundContainerSetSlotPacket(-1, this.m_182425_(), -1, this.m_142621_()));
                    this.setSaving(false);
                    this.m_38946_();
                }
                return;
            }
            if (slot instanceof ClosableGhostSlot) {
                ItemStack stack = this.m_142621_();
                ((IndustrialWorkbenchTileEntity)this.getHolder()).requestedItem.setStackInSlot(0, stack.m_41619_() ? ItemStack.f_41583_ : StackUtil.copyWithSize(stack, 1));
                return;
            }
            if (slot instanceof LockedSlot.DragableLockedSlot) {
                ((LockedSlot.DragableLockedSlot)slot).onClick(clickTypeIn == ClickType.QUICK_CRAFT ? 1 : dragType, clickTypeIn == ClickType.QUICK_MOVE, clickTypeIn == ClickType.QUICK_CRAFT ? StackUtil.copyWithSize(this.m_142621_(), 1) : this.m_142621_());
                ((IndustrialWorkbenchTileEntity)this.getHolder()).updateRecipe();
                this.m_38946_();
                return;
            }
            if (slot instanceof MemorySlot) {
                if (((IndustrialWorkbenchTileEntity)this.getHolder()).isSimulating()) {
                    if (dragType == 2) {
                        ((IndustrialWorkbenchTileEntity)this.getHolder()).deleteRecipe(slot.getSlotIndex());
                    } else if (dragType == 0) {
                        ((IndustrialWorkbenchTileEntity)this.getHolder()).setRecipe(slot.getSlotIndex());
                    }
                    this.m_38946_();
                }
                return;
            }
            if (slot instanceof RequestSlot) {
                return;
            }
            if (slot instanceof RecursiveCraftSlot) {
                if (((IndustrialWorkbenchTileEntity)this.getHolder()).isRendering()) {
                    this.disableDoubleClick();
                    return;
                }
                if (!slot.m_6657_()) {
                    return;
                }
                IndustrialWorkbenchTileEntity work = (IndustrialWorkbenchTileEntity)this.getHolder();
                work.createSnapshot();
                Snapshot[] snap = work.takeSnapshot();
                boolean needsRefill = false;
                if (clickTypeIn == ClickType.QUICK_MOVE) {
                    ItemStack copy;
                    int max = work.getCraftLimit();
                    for (int count = 0; count < max && work.canCraft(player); count += copy.m_41613_()) {
                        work.createSnapshot();
                        ItemStack output = work.craft(player);
                        copy = output.m_41777_();
                        if (this.m_38903_(output, this.getInventorySize(), this.f_38839_.size(), true)) {
                            work.destroySnapshot();
                            needsRefill = true;
                            continue;
                        }
                        if (copy.m_41613_() != output.m_41613_()) {
                            player.m_36176_(output, true);
                            work.destroySnapshot();
                        } else {
                            work.releaseSnapshot();
                        }
                        break;
                    }
                } else if (this.canFitIntoPlayerHand(slot.m_7993_(), player) && work.canCraft(player)) {
                    ItemStack output = work.craft(player);
                    ItemStack playerHeld = this.m_142621_();
                    if (playerHeld.m_41619_()) {
                        this.m_142503_(output);
                    } else {
                        int left = Math.min(output.m_41613_(), StackUtil.getStackSizeLeft(playerHeld));
                        output.m_41774_(left);
                        playerHeld.m_41769_(left);
                        this.m_142503_(playerHeld);
                        if (output.m_41613_() > 0) {
                            player.m_36176_(output, true);
                        }
                    }
                    ((ServerPlayer)player).f_8906_.m_9829_((Packet)new ClientboundContainerSetSlotPacket(-1, this.m_182425_(), -1, this.m_142621_()));
                    needsRefill = true;
                }
                if (needsRefill) {
                    work.putSnapshot(snap);
                    work.triggerRestock(player);
                    this.m_38946_();
                }
                return;
            }
        }
        if (this.isSaving()) {
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
        this.m_38946_();
    }

    public boolean canFitIntoPlayerHand(ItemStack stack, Player player) {
        ItemStack playerHand = this.m_142621_();
        if (!playerHand.m_41619_()) {
            return StackUtil.isStackEqual(stack, playerHand) && StackUtil.getStackSizeLeft(playerHand) >= stack.m_41613_();
        }
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void disableDoubleClick() {
        Screen screen = Minecraft.m_91087_().f_91080_;
        if (screen instanceof IC2Screen) {
            ((IC2Screen)screen).preventDoubleClick();
        }
    }

    public static class RequestSlot
    extends LockedSlot {
        MutableBoolean value;

        public RequestSlot(IHasInventory inv, int index, int xPosition, int yPosition, MutableBoolean value) {
            super(inv, index, xPosition, yPosition);
            this.value = value;
        }

        @OnlyIn(value=Dist.CLIENT)
        public boolean m_6659_() {
            return this.value.isTrue();
        }
    }

    public static class ClosableGhostSlot
    extends GhostSlot {
        MutableBoolean value;

        public ClosableGhostSlot(IHasInventory inv, int index, int xPosition, int yPosition, IFilter filter, MutableBoolean value) {
            super(inv, index, xPosition, yPosition, filter);
            this.value = value;
        }

        @OnlyIn(value=Dist.CLIENT)
        public boolean m_6659_() {
            return this.value.isTrue();
        }
    }
}

