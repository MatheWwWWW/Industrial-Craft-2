/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerSortingMachine;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.item.EnvItemHandler;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntitySortingMachine
extends TileEntityElectricMachine
implements IHasGui,
INetworkClientTileEntityEventListener,
IUpgradableBlock {
    public static final int defaultTier = 2;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlot buffer;
    private final ItemStack[][] filters;
    public Direction defaultRoute = Direction.DOWN;

    public TileEntitySortingMachine(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.SORTING_MACHINE, blockPos, blockState, 15000, 2, false);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3);
        this.buffer = new InvSlot(this, "Buffer", InvSlot.Access.IO, 11);
        this.filters = new ItemStack[6][7];
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        int n;
        super.m_142466_(compoundTag);
        ListTag listTag = compoundTag.m_128437_("filters", 10);
        for (n = 0; n < listTag.size(); ++n) {
            ItemStack itemStack;
            CompoundTag compoundTag2 = listTag.m_128728_(n);
            int n2 = compoundTag2.m_128445_("index") & 0xFF;
            this.filters[n2 / 7][n2 % 7] = itemStack = ItemStack.m_41712_((CompoundTag)compoundTag2);
        }
        n = compoundTag.m_128445_("defaultroute");
        if (n >= 0 && n < Util.ALL_DIRS.length) {
            this.defaultRoute = Util.ALL_DIRS[n];
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        ListTag listTag = new ListTag();
        for (int i = 0; i < 42; ++i) {
            ItemStack itemStack = this.filters[i / 7][i % 7];
            if (itemStack == null) continue;
            CompoundTag compoundTag2 = new CompoundTag();
            compoundTag2.m_128344_("index", (byte)i);
            itemStack.m_41739_(compoundTag2);
            listTag.add((Object)compoundTag2);
        }
        compoundTag.m_128365_("filters", (Tag)listTag);
        compoundTag.m_128344_("defaultroute", (byte)this.defaultRoute.ordinal());
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (IC2.sideProxy.isSimulating()) {
            this.setUpgradableBlock();
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        block0: for (int i = 0; i < this.buffer.size(); ++i) {
            if (this.energy.getEnergy() < 20.0) {
                return;
            }
            ItemStack itemStack = this.buffer.get(i);
            if (StackUtil.isEmpty(itemStack)) continue;
            block1: for (EnvItemHandler.AdjacentInventory adjacentInventory : StackUtil.ENV.getAdjacentInventories(this)) {
                if (adjacentInventory.getSide() != this.defaultRoute) {
                    for (ItemStack itemStack2 : this.getFilterSlots(adjacentInventory.getSide())) {
                        if (StackUtil.isEmpty(itemStack2)) continue;
                        int n = StackUtil.getSize(itemStack2);
                        if (StackUtil.getSize(itemStack) < n || !StackUtil.checkItemEquality(itemStack2, itemStack) || !this.energy.canUseEnergy(n * 20)) continue;
                        ItemStack itemStack3 = StackUtil.copyWithSize(itemStack, n);
                        int n2 = StackUtil.ENV.deposit(adjacentInventory, itemStack3, true);
                        if (n2 != n) continue block1;
                        n2 = StackUtil.ENV.deposit(adjacentInventory, itemStack3, false);
                        itemStack = StackUtil.decSize(itemStack, n2);
                        this.buffer.put(i, itemStack);
                        this.energy.useEnergy(n2 * 20);
                        if (!StackUtil.isEmpty(itemStack)) continue block1;
                        continue block0;
                    }
                    continue;
                }
                boolean bl = false;
                ItemStack[][] itemStackArray = this.filters;
                int n = itemStackArray.length;
                block3: for (int j = 0; j < n; ++j) {
                    ItemStack itemStack4;
                    for (ItemStack itemStack5 : itemStack4 = itemStackArray[j]) {
                        if (!StackUtil.checkItemEquality(itemStack5, itemStack)) continue;
                        bl = true;
                        break block3;
                    }
                }
                if (bl) continue;
                int n3 = StackUtil.ENV.deposit(adjacentInventory, StackUtil.copyWithSize(itemStack, 1), false);
                if (n3 <= 0) continue block0;
                itemStack = StackUtil.decSize(itemStack, n3);
                this.buffer.put(i, itemStack);
                this.energy.useEnergy(20.0);
                if (StackUtil.isEmpty(itemStack)) continue block0;
            }
        }
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (n >= 0 && n <= 5) {
            this.defaultRoute = Util.ALL_DIRS[n];
        }
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerSortingMachine(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerSortingMachine(n, inventory, this);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer);
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (IC2.sideProxy.isSimulating()) {
            this.setUpgradableBlock();
        }
    }

    public void setUpgradableBlock() {
        this.energy.setSinkTier(this.upgradeSlot.getTier(2));
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
    }

    public ItemStack[] getFilterSlots(Direction direction) {
        return this.filters[direction.ordinal()];
    }
}

