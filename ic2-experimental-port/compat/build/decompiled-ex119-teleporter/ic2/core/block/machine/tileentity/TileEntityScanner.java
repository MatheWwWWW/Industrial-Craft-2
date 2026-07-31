/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IPatternStorage;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.invslot.InvSlotScannable;
import ic2.core.block.machine.container.ContainerScanner;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.item.ItemCrystalMemory;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.uu.UuGraph;
import ic2.core.uu.UuIndex;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityScanner
extends TileEntityElectricMachine
implements IHasGui,
INetworkClientTileEntityEventListener {
    private ItemStack currentStack = StackUtil.emptyStack;
    private ItemStack pattern = StackUtil.emptyStack;
    private final int energyusecycle = 256;
    public int progress = 0;
    public final int duration = 3300;
    public final InvSlotConsumable inputSlot;
    public final InvSlot diskSlot;
    private State state = State.IDLE;
    public double patternUu;
    public double patternEu;

    public TileEntityScanner(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.UU_SCANNER, blockPos, blockState, 512000, 4);
        this.inputSlot = new InvSlotScannable(this, "input", 1);
        this.diskSlot = new InvSlotConsumableId(this, "disk", InvSlot.Access.IO, 1, InvSlot.InvSide.ANY, Ic2Items.CRYSTAL_MEMORY);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.progress < 3300) {
            if (this.inputSlot.isEmpty() || !StackUtil.isEmpty(this.currentStack) && !StackUtil.checkItemEquality(this.currentStack, this.inputSlot.get())) {
                this.state = State.IDLE;
                this.reset();
            } else if (this.getPatternStorage() == null && this.diskSlot.isEmpty()) {
                this.state = State.NO_STORAGE;
                this.reset();
            } else if (this.energy.getEnergy() >= 256.0) {
                if (StackUtil.isEmpty(this.currentStack)) {
                    this.currentStack = StackUtil.copyWithSize(this.inputSlot.get(), 1);
                }
                this.pattern = UuGraph.find(this.currentStack);
                if (StackUtil.isEmpty(this.pattern)) {
                    this.state = State.FAILED;
                } else if (this.isPatternRecorded(this.pattern)) {
                    this.state = State.ALREADY_RECORDED;
                    this.reset();
                } else {
                    bl = true;
                    this.state = State.SCANNING;
                    this.energy.useEnergy(256.0);
                    ++this.progress;
                    if (this.progress >= 3300) {
                        this.refreshInfo();
                        if (this.patternUu != Double.POSITIVE_INFINITY) {
                            this.state = State.COMPLETED;
                            this.inputSlot.consume(1, false, true);
                            this.m_6596_();
                        } else {
                            this.state = State.FAILED;
                        }
                    }
                }
            } else {
                this.state = State.NO_ENERGY;
            }
        } else if (StackUtil.isEmpty(this.pattern)) {
            this.state = State.IDLE;
            this.progress = 0;
        }
        this.setActive(bl);
    }

    public void reset() {
        this.progress = 0;
        this.currentStack = StackUtil.emptyStack;
        this.pattern = StackUtil.emptyStack;
    }

    private boolean isPatternRecorded(ItemStack itemStack) {
        IPatternStorage iPatternStorage;
        if (!this.diskSlot.isEmpty() && this.diskSlot.get().m_41720_() instanceof ItemCrystalMemory && StackUtil.checkItemEquality(((ItemCrystalMemory)(iPatternStorage = this.diskSlot.get()).m_41720_()).readItemStack((ItemStack)iPatternStorage), itemStack)) {
            return true;
        }
        iPatternStorage = this.getPatternStorage();
        if (iPatternStorage == null) {
            return false;
        }
        for (ItemStack itemStack2 : iPatternStorage.getPatterns()) {
            if (!StackUtil.checkItemEquality(itemStack2, itemStack)) continue;
            return true;
        }
        return false;
    }

    private void record() {
        if (StackUtil.isEmpty(this.pattern) || this.patternUu == Double.POSITIVE_INFINITY) {
            this.reset();
            return;
        }
        if (!this.savetoDisk(this.pattern)) {
            IPatternStorage iPatternStorage = this.getPatternStorage();
            if (iPatternStorage != null) {
                if (!iPatternStorage.addPattern(this.pattern)) {
                    this.state = State.TRANSFER_ERROR;
                    return;
                }
            } else {
                this.state = State.TRANSFER_ERROR;
                return;
            }
        }
        this.reset();
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.progress = compoundTag.m_128451_("progress");
        CompoundTag compoundTag2 = compoundTag.m_128469_("currentStack");
        this.currentStack = ItemStack.m_41712_((CompoundTag)compoundTag2);
        compoundTag2 = compoundTag.m_128469_("pattern");
        this.pattern = ItemStack.m_41712_((CompoundTag)compoundTag2);
        int n = compoundTag.m_128451_("state");
        this.state = n < State.values().length ? State.values()[n] : State.IDLE;
        this.refreshInfo();
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        CompoundTag compoundTag2;
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("progress", this.progress);
        if (!StackUtil.isEmpty(this.currentStack)) {
            compoundTag2 = new CompoundTag();
            this.currentStack.m_41739_(compoundTag2);
            compoundTag.m_128365_("currentStack", (Tag)compoundTag2);
        }
        if (!StackUtil.isEmpty(this.pattern)) {
            compoundTag2 = new CompoundTag();
            this.pattern.m_41739_(compoundTag2);
            compoundTag.m_128365_("pattern", (Tag)compoundTag2);
        }
        compoundTag.m_128405_("state", this.state.ordinal());
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerScanner(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerScanner(n, inventory, this);
    }

    public IPatternStorage getPatternStorage() {
        Level level = this.m_58904_();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof IPatternStorage)) continue;
            return (IPatternStorage)blockEntity;
        }
        return null;
    }

    public boolean savetoDisk(ItemStack itemStack) {
        if (this.diskSlot.isEmpty() || itemStack == null) {
            return false;
        }
        if (this.diskSlot.get().m_41720_() instanceof ItemCrystalMemory) {
            ItemStack itemStack2 = this.diskSlot.get();
            ((ItemCrystalMemory)itemStack2.m_41720_()).writecontentsTag(itemStack2, itemStack);
            return true;
        }
        return false;
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        switch (n) {
            case 0: {
                this.reset();
                break;
            }
            case 1: {
                if (this.progress < 3300) break;
                this.record();
            }
        }
    }

    private void refreshInfo() {
        if (!StackUtil.isEmpty(this.pattern)) {
            this.patternUu = UuIndex.instance.getInBuckets(this.pattern);
        }
    }

    public int getPercentageDone() {
        return 100 * this.progress / 3300;
    }

    public int getSubPercentageDoneScaled(int n) {
        return n * (100 * this.progress % 3300) / 3300;
    }

    public boolean isDone() {
        return this.progress >= 3300;
    }

    public State getState() {
        return this.state;
    }

    public static enum State {
        IDLE,
        SCANNING,
        COMPLETED,
        FAILED,
        NO_STORAGE,
        NO_ENERGY,
        TRANSFER_ERROR,
        ALREADY_RECORDED;

    }
}

