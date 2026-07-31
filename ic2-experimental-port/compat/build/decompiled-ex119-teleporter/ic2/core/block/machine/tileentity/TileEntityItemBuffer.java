/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerItemBuffer;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityItemBuffer
extends TileEntityInventory
implements IHasGui,
IUpgradableBlock {
    public final InvSlot rightcontentSlot = new InvSlot(this, "rightcontent", InvSlot.Access.IO, 24, InvSlot.InvSide.SIDE);
    public final InvSlot leftcontentSlot = new InvSlot(this, "leftcontent", InvSlot.Access.IO, 24, InvSlot.InvSide.NOTSIDE);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
    private boolean tick = true;

    public TileEntityItemBuffer(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.ITEM_BUFFER, blockPos, blockState);
        this.comparator.setUpdate(() -> TileEntityItemBuffer.calcRedstoneFromInvSlots(this.rightcontentSlot, this.leftcontentSlot));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        ItemStack itemStack = this.upgradeSlot.get(0);
        ItemStack itemStack2 = this.upgradeSlot.get(1);
        if (!StackUtil.isEmpty(itemStack) && !StackUtil.isEmpty(itemStack2)) {
            if (this.tick) {
                if (((IUpgradeItem)itemStack.m_41720_()).onTick(itemStack, this)) {
                    super.m_6596_();
                }
            } else if (((IUpgradeItem)itemStack2.m_41720_()).onTick(itemStack2, this)) {
                super.m_6596_();
            }
            this.tick = !this.tick;
        } else {
            if (!StackUtil.isEmpty(itemStack)) {
                this.tick = true;
                if (((IUpgradeItem)itemStack.m_41720_()).onTick(itemStack, this)) {
                    super.m_6596_();
                }
            }
            if (!StackUtil.isEmpty(itemStack2)) {
                this.tick = false;
                if (((IUpgradeItem)itemStack2.m_41720_()).onTick(itemStack2, this)) {
                    super.m_6596_();
                }
            }
        }
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerItemBuffer(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerItemBuffer(n, inventory, this);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemProducing);
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return true;
    }
}

