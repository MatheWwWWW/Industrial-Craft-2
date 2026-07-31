/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IPatternStorage;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.machine.container.ContainerPatternStorage;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.item.ItemCrystalMemory;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import ic2.core.uu.UuIndex;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityPatternStorage
extends TileEntityInventory
implements IHasGui,
INetworkClientTileEntityEventListener,
IPatternStorage {
    public final InvSlotConsumableId diskSlot;
    private final List<ItemStack> patterns = new ArrayList<ItemStack>();
    public int index = 0;
    public int maxIndex;
    public ItemStack pattern;
    public double patternUu;
    public double patternEu;

    public TileEntityPatternStorage(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.PATTERN_STORAGE, blockPos, blockState);
        this.diskSlot = new InvSlotConsumableId(this, "SaveSlot", InvSlot.Access.IO, 1, InvSlot.InvSide.ANY, Ic2Items.CRYSTAL_MEMORY);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.readContents(compoundTag);
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        this.writeContentsAsNbtList(compoundTag);
    }

    @Override
    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
        super.onPlaced(itemStack, livingEntity, direction);
        if (!this.m_58904_().f_46443_) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            this.readContents(compoundTag);
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack itemStack, boolean bl) {
        itemStack = super.adjustDrop(itemStack, bl);
        if (bl || this.teBlock.getDefaultDrop() == Ic2TileEntityBlock.DefaultDrop.Self) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            this.writeContentsAsNbtList(compoundTag);
        }
        return itemStack;
    }

    public void readContents(CompoundTag compoundTag) {
        ListTag listTag = compoundTag.m_128437_("patterns", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag2 = listTag.m_128728_(i);
            ItemStack itemStack = ItemStack.m_41712_((CompoundTag)compoundTag2);
            this.addPattern(itemStack);
        }
        this.refreshInfo();
    }

    private void writeContentsAsNbtList(CompoundTag compoundTag) {
        ListTag listTag = new ListTag();
        for (ItemStack itemStack : this.patterns) {
            CompoundTag compoundTag2 = new CompoundTag();
            itemStack.m_41739_(compoundTag2);
            listTag.add((Object)compoundTag2);
        }
        compoundTag.m_128365_("patterns", (Tag)listTag);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerPatternStorage(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerPatternStorage(n, inventory, this);
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        switch (n) {
            case 0: {
                if (this.patterns.isEmpty()) break;
                this.index = this.index <= 0 ? this.patterns.size() - 1 : --this.index;
                this.refreshInfo();
                break;
            }
            case 1: {
                if (this.patterns.isEmpty()) break;
                this.index = this.index >= this.patterns.size() - 1 ? 0 : ++this.index;
                this.refreshInfo();
                break;
            }
            case 2: {
                ItemStack itemStack;
                if (this.index < 0 || this.index >= this.patterns.size() || this.diskSlot.isEmpty() || !((itemStack = this.diskSlot.get()).m_41720_() instanceof ItemCrystalMemory)) break;
                ((ItemCrystalMemory)itemStack.m_41720_()).writecontentsTag(itemStack, this.patterns.get(this.index));
                break;
            }
            case 3: {
                ItemStack itemStack;
                ItemStack itemStack2;
                if (this.diskSlot.isEmpty() || !((itemStack2 = this.diskSlot.get()).m_41720_() instanceof ItemCrystalMemory) || (itemStack = ((ItemCrystalMemory)itemStack2.m_41720_()).readItemStack(itemStack2)) == null) break;
                this.addPattern(itemStack);
            }
        }
    }

    public void refreshInfo() {
        if (this.index < 0 || this.index >= this.patterns.size()) {
            this.index = 0;
        }
        this.maxIndex = this.patterns.size();
        if (this.patterns.isEmpty()) {
            this.pattern = null;
        } else {
            this.pattern = this.patterns.get(this.index);
            this.patternUu = UuIndex.instance.getInBuckets(this.pattern);
        }
    }

    @Override
    public boolean addPattern(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            throw new IllegalArgumentException("empty stack: " + StackUtil.toStringSafe(itemStack));
        }
        for (ItemStack itemStack2 : this.patterns) {
            if (!StackUtil.checkItemEquality(itemStack2, itemStack)) continue;
            return false;
        }
        this.patterns.add(itemStack);
        this.refreshInfo();
        return true;
    }

    @Override
    public List<ItemStack> getPatterns() {
        return this.patterns;
    }
}

