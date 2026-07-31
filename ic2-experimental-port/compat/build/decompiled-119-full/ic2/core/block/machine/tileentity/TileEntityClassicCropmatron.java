/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.machine.container.ContainerClassicCropmatron;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.crop.TileEntityCrop;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityClassicCropmatron
extends TileEntityElectricMachine
implements IHasGui {
    public int scanX = -4;
    public int scanY = -1;
    public int scanZ = -4;
    public final InvSlotConsumable fertilizerSlot = new InvSlotConsumableItemStack(this, "fertilizer", 3, new ItemStack((ItemLike)Ic2Items.FERTILIZER));
    public final InvSlotConsumable hydrationSlot = new InvSlotConsumableItemStack(this, "hydration", 3, new ItemStack((ItemLike)Ic2Items.HYDRATION_CELL));
    public final InvSlotConsumable weedExSlot = new InvSlotConsumableItemStack(this, "weedEx", 3, new ItemStack((ItemLike)Ic2Items.WEED_EX_CELL));

    public TileEntityClassicCropmatron(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CLASSIC_CROPMATRON, blockPos, blockState, 1000, 1);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.fertilizerSlot.organize();
        this.hydrationSlot.organize();
        this.weedExSlot.organize();
        if (this.energy.getEnergy() >= 31.0) {
            this.scan();
        }
    }

    public void scan() {
        ++this.scanX;
        if (this.scanX > 5) {
            this.scanX = -5;
            ++this.scanZ;
            if (this.scanZ > 5) {
                this.scanZ = -5;
                ++this.scanY;
                if (this.scanY > 1) {
                    this.scanY = -1;
                }
            }
        }
        this.energy.useEnergy(1.0);
        BlockPos blockPos = this.f_58858_.m_7918_(this.scanX, this.scanY, this.scanZ);
        BlockEntity blockEntity = this.m_58904_().m_7702_(blockPos);
        if (blockEntity instanceof TileEntityCrop) {
            TileEntityCrop tileEntityCrop = (TileEntityCrop)blockEntity;
            if (!this.fertilizerSlot.isEmpty() && tileEntityCrop.applyFertilizer(false)) {
                this.energy.useEnergy(10.0);
                this.fertilizerSlot.consume(1);
            }
            if (!this.hydrationSlot.isEmpty() && Ic2Items.HYDRATION_CELL.useOnCrop(this.hydrationSlot.get(0), tileEntityCrop, false)) {
                this.energy.useEnergy(10.0);
            }
            if (!this.weedExSlot.isEmpty() && Ic2Items.WEED_EX_CELL.useOnCrop(this.weedExSlot.get(0), tileEntityCrop, false)) {
                this.energy.useEnergy(10.0);
            }
        }
    }

    public ContainerBase<TileEntityClassicCropmatron> createServerScreenHandler(int n, Player player) {
        return new ContainerClassicCropmatron(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerClassicCropmatron(n, inventory, this);
    }
}

