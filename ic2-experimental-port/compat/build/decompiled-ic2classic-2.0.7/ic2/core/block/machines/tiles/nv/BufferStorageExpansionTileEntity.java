/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.machines.tiles.nv;

import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.InventoryComparator;
import ic2.core.block.base.tiles.impls.BaseExpansionTileEntity;
import ic2.core.block.machines.containers.nv.DumpingStorageExpansionContainer;
import ic2.core.block.machines.logic.crafter.CraftingList;
import ic2.core.block.machines.logic.crafter.Snapshot;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.inventory.inv.ListenerInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Tiles;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BufferStorageExpansionTileEntity
extends BaseExpansionTileEntity
implements ITileGui,
INotifyInventory,
IDropProvider {
    public SimpleInventory inventory = new ListenerInventory(9, this);

    public BufferStorageExpansionTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addComparator(new InventoryComparator("slots", ComparatorNames.FILLED_SLOTS, this.inventory, true));
        this.addComparator(new InventoryComparator("items", ComparatorNames.FILLED_INVENTORY, this.inventory, false));
    }

    @Override
    public void onNotify(IHasInventory inventory, int slot) {
        if (this.isSimulating()) {
            this.handleComparators(true);
        }
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        this.inventory.addToDrops(drops);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.BUFFER_STORAGE_EXPANSION;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new DumpingStorageExpansionContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("inv", (Tag)this.inventory.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.inventory.load(compound.m_128469_("inv"));
    }

    @Override
    public void createMainTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
    }

    @Override
    public void createTrashTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
        IItemTransporter transporter = TransporterManager.getTransporter(simulate ? SimpleInventory.createCopy(this.inventory) : this.inventory);
        insertion.add(transporter);
        extraction.add(transporter);
    }

    @Override
    public CraftingList getExpansionRecipeList() {
        return null;
    }

    @Override
    public void createSnapshot() {
        if (this.snapshot != null) {
            throw new RuntimeException("Snapshot already existed");
        }
        this.snapshot = new Snapshot(1);
        this.snapshot.set(0, SimpleInventory.createCopy(this.inventory));
    }

    @Override
    public void releaseSnapshot() {
        if (this.snapshot == null) {
            throw new IllegalStateException("Snapshot is missing");
        }
        this.snapshot.get(0, SimpleInventory.class).overrideTo(this.inventory);
        this.snapshot = null;
    }

    @Override
    public void triggerRestock(IItemTransporter transporter, IFluidHandler handler, Player player) {
    }
}

