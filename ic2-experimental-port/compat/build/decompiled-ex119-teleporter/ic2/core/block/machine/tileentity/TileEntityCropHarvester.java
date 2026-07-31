/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerCropHarvester;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.crop.TileEntityCrop;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityCropHarvester
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock {
    public final InvSlot contentSlot = new InvSlot(this, "content", InvSlot.Access.IO, 15);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    public int scanX = -4;
    public int scanY = -1;
    public int scanZ = -4;

    public TileEntityCropHarvester(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CROP_HARVESTER, blockPos, blockState, 10000, 1, false);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.upgradeSlot.tick();
        if (this.f_58857_.m_46467_() % 10L == 0L && this.energy.getEnergy() >= 21.0) {
            this.scan();
        }
    }

    public void scan() {
        TileEntityCrop tileEntityCrop;
        ++this.scanX;
        if (this.scanX > 4) {
            this.scanX = -4;
            ++this.scanZ;
            if (this.scanZ > 4) {
                this.scanZ = -4;
                ++this.scanY;
                if (this.scanY > 1) {
                    this.scanY = -1;
                }
            }
        }
        this.energy.useEnergy(1.0);
        Level level = this.m_58904_();
        BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_7918_(this.scanX, this.scanY, this.scanZ));
        if (blockEntity instanceof TileEntityCrop && !this.isInvFull() && (tileEntityCrop = (TileEntityCrop)blockEntity).getCrop() != null) {
            List<ItemStack> list = null;
            if (tileEntityCrop.getCurrentAge() == tileEntityCrop.getCrop().getOptimalHarvestAge(tileEntityCrop)) {
                list = tileEntityCrop.performHarvest();
            } else if (tileEntityCrop.getCurrentAge() == tileEntityCrop.getCrop().getMaxAge()) {
                list = tileEntityCrop.performHarvest();
            }
            if (list != null) {
                list.forEach(itemStack -> {
                    if (StackUtil.putInInventory(this, Direction.WEST, itemStack, true) == 0) {
                        StackUtil.dropAsEntity(level, this.f_58858_, itemStack);
                    } else {
                        StackUtil.putInInventory(this, Direction.WEST, itemStack, false);
                    }
                    this.energy.useEnergy(20.0);
                });
            }
        }
    }

    private boolean isInvFull() {
        for (int i = 0; i < this.contentSlot.size(); ++i) {
            ItemStack itemStack = this.contentSlot.get(i);
            if (!StackUtil.isEmpty(itemStack) && StackUtil.getSize(itemStack) >= Math.min(itemStack.m_41741_(), this.contentSlot.getStackSizeLimit())) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemProducing);
    }

    public ContainerBase<TileEntityCropHarvester> createServerScreenHandler(int n, Player player) {
        return new ContainerCropHarvester(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerCropHarvester(n, inventory, this);
    }
}

