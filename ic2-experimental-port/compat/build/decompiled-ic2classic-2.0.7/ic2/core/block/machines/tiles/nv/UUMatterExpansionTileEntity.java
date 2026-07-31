/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkClientEventListener;
import ic2.api.recipes.registries.IUUMatterRegistry;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.special.UUMatterComparator;
import ic2.core.block.base.tiles.impls.BaseExpansionTileEntity;
import ic2.core.block.machines.containers.nv.UUMatterExpansionContainer;
import ic2.core.block.machines.logic.crafter.CraftingList;
import ic2.core.block.machines.logic.crafter.Snapshot;
import ic2.core.block.machines.logic.crafter.WrappedInteger;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.platform.recipes.misc.UUMatterRegistry;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class UUMatterExpansionTileEntity
extends BaseExpansionTileEntity
implements IntConsumer,
ITileGui,
ITickListener,
INetworkClientEventListener {
    static final IntConsumer EMPTY = T -> {};
    @NetworkInfo
    public int maxUUMatter = 1024000;
    @NetworkInfo
    public int uuMatter = 0;
    public SimpleInventory uuInsertion = new SimpleInventory(1);
    public SimpleInventory filter = new SimpleInventory(9);
    boolean inserting = false;

    public UUMatterExpansionTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("maxUUMatter", "uuMatter");
        this.addComparator(new UUMatterComparator("uu_storage", ComparatorNames.STORED_UU_MATTER, this));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.UU_EXPANSION;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("filter", (Tag)this.filter.save(new CompoundTag()));
        compound.m_128365_("uu_inv", (Tag)this.uuInsertion.save(new CompoundTag()));
        compound.m_128405_("max", this.maxUUMatter);
        compound.m_128405_("current", this.uuMatter);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.filter.load(compound.m_128469_("filter"));
        this.uuInsertion.load(compound.m_128469_("uu_inv"));
        this.maxUUMatter = compound.m_128451_("max");
        this.uuMatter = compound.m_128451_("current");
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new UUMatterExpansionContainer(this, player, windowID);
    }

    @Override
    public void onTick() {
        ItemStack stack = this.uuInsertion.getStackInSlot(0);
        if (stack.m_41720_() == IC2Items.UUMATTER) {
            int wanted = (this.maxUUMatter - this.uuMatter) / 1000;
            if (wanted <= 0) {
                return;
            }
            this.uuMatter += stack.m_41620_(wanted).m_41613_() * 1000;
            this.updateGuiField("uuMatter");
        }
        this.handleComparators();
    }

    @Override
    public void accept(int value) {
        this.uuMatter -= value;
        this.updateGuiField("uuMatter");
    }

    @Override
    public void createMainTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
        ObjectList entries = CollectionUtils.createList();
        UUMatterRegistry registry = IC2.RECIPES.get((boolean)this.isSimulating()).UU;
        Map<ItemStack, IUUMatterRegistry.UUMatterEntry> mapped = registry.getMappedEntries();
        int m = this.filter.getSlotCount();
        for (int i = 0; i < m; ++i) {
            IUUMatterRegistry.UUMatterEntry uu;
            ItemStack entry = this.filter.getStackInSlot(i);
            if (entry.m_41619_() || (uu = mapped.get(entry)) == null) continue;
            entries.add((IUUMatterRegistry.UUMatterEntry)uu);
        }
        if (entries.isEmpty()) {
            entries.addAll(registry.getEntries());
        }
        if (entries.size() > 0) {
            extraction.add(new UUTransporter((List<IUUMatterRegistry.UUMatterEntry>)entries, this.uuMatter, simulate ? EMPTY : this));
        }
    }

    @Override
    public void createTrashTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
    }

    @Override
    public CraftingList getExpansionRecipeList() {
        return null;
    }

    @Override
    public void createSnapshot() {
        this.snapshot = new Snapshot(1);
        this.snapshot.set(0, new WrappedInteger(this.uuMatter));
    }

    @Override
    public void releaseSnapshot() {
        this.uuMatter = this.snapshot.get(0, WrappedInteger.class).getValue();
        this.updateGuiField("uuMatter");
    }

    @Override
    public void triggerRestock(IItemTransporter transporter, IFluidHandler handler, Player player) {
        int wanted = (this.maxUUMatter - this.uuMatter) / 1000;
        if (wanted <= 0) {
            return;
        }
        this.inserting = true;
        ItemStack stack = this.uuInsertion.getStackInSlot(0);
        if (stack.m_41720_() == IC2Items.UUMATTER) {
            this.uuMatter += stack.m_41620_(wanted).m_41613_() * 1000;
            this.updateGuiField("uuMatter");
        }
        this.inserting = false;
        wanted = (this.maxUUMatter - this.uuMatter) / 1000;
        if (wanted <= 0) {
            return;
        }
        this.uuMatter += transporter.removeItem(new SimpleFilter((ItemLike)IC2Items.UUMATTER), null, wanted, false).m_41613_() * 1000;
        this.updateGuiField("uuMatter");
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 1) {
            if (value < 0) {
                int removed;
                ItemStack stack;
                int added;
                int amount = this.uuMatter / 1000;
                for (removed = 0; amount > removed; removed += added - stack.m_41613_()) {
                    added = amount - removed;
                    stack = new ItemStack((ItemLike)IC2Items.UUMATTER, added);
                    entity.m_150109_().m_36054_(stack);
                    if (stack.m_41613_() <= 0) continue;
                    break;
                }
                if (removed > 0) {
                    this.uuMatter -= removed * 1000;
                    this.updateGuiField("uuMatter");
                }
            } else if (value > 0 && value <= 64) {
                if ((value = Math.min(value, this.uuMatter / 1000)) <= 0) {
                    return;
                }
                ItemStack stack = new ItemStack((ItemLike)IC2Items.UUMATTER, value);
                entity.m_150109_().m_36054_(stack);
                this.uuMatter -= (value - stack.m_41613_()) * 1000;
                this.updateGuiField("uuMatter");
            }
        }
    }

    public static class UUTransporter
    implements IItemTransporter {
        List<IUUMatterRegistry.UUMatterEntry> entries;
        int uuLeft;
        IntConsumer actual;

        public UUTransporter(List<IUUMatterRegistry.UUMatterEntry> entries, int uuLeft, IntConsumer actual) {
            this.entries = entries;
            this.uuLeft = uuLeft;
            this.actual = actual;
        }

        @Override
        public int addItem(ItemStack stack, Direction dir, boolean simulate) {
            return 0;
        }

        @Override
        public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
            if (amount <= 0) {
                return ItemStack.f_41583_;
            }
            int m = this.entries.size();
            for (int i = 0; i < m; ++i) {
                IUUMatterRegistry.UUMatterEntry entry = this.entries.get(i);
                ItemStack stack = entry.getStack();
                if (!filter.matches(stack)) continue;
                int perItem = entry.getUUNeeded();
                if ((amount = Math.min(amount, this.uuLeft / perItem)) > 0) {
                    int value = amount * perItem;
                    this.actual.accept(value);
                    this.uuLeft -= value;
                }
                return StackUtil.copyWithSize(stack, amount);
            }
            return ItemStack.f_41583_;
        }

        @Override
        public int getInventorySize(Direction dir) {
            return 0;
        }

        @Override
        public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
            return Object2IntMaps.emptyMap();
        }

        @Override
        public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
            return new IItemTransporter.InvResult(compareNBT);
        }
    }
}

