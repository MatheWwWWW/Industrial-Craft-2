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

import ic2.api.network.buffer.NetworkInfo;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.tiles.impls.BaseExpansionTileEntity;
import ic2.core.block.machines.containers.nv.CraftingExpansionContainer;
import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.block.machines.logic.crafter.CraftingList;
import ic2.core.block.machines.logic.crafter.IMemorySlotProvider;
import ic2.core.block.machines.logic.crafter.Snapshot;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.inventory.inv.ListenerInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.item.misc.MemoryStickItem;
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

public class MemoryExpansionTileEntity
extends BaseExpansionTileEntity
implements ITileGui,
INotifyInventory,
IMemorySlotProvider,
IDropProvider {
    public CraftingList crafting = new CraftingList(18);
    public SimpleInventory inventory = new ListenerInventory(2, this);
    @NetworkInfo
    int enabledSlots = 0;

    public MemoryExpansionTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("enabledSlots");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MEMORY_EXPANSION;
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        this.inventory.addToDrops(drops);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("recipes", (Tag)this.crafting.save(new CompoundTag()));
        compound.m_128365_("sticks", (Tag)this.inventory.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.crafting.load(compound.m_128469_("recipes"));
        this.inventory.load(compound.m_128469_("sticks"));
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new CraftingExpansionContainer(this, player, windowID);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isRendering()) {
            return;
        }
        for (int i = 0; i < 2; ++i) {
            int x;
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.m_41619_()) {
                for (x = 0; x < 9; ++x) {
                    this.crafting.removeRecipe(i * 9 + x);
                }
                this.enabledSlots |= 511 << 9 * (i + 1);
                continue;
            }
            for (x = 0; x < 9; ++x) {
                CraftRecipe recipe = MemoryStickItem.loadRecipe(stack, x);
                if (recipe != null && recipe.validate(this.m_58904_())) {
                    this.crafting.saveRecipe(i * 9 + x, recipe);
                    continue;
                }
                this.crafting.removeRecipe(i * 9 + x);
            }
            this.enabledSlots &= ~(511 << 9 * (i + 1));
        }
    }

    @Override
    public void onNotify(IHasInventory inventory, int slot) {
        if (this.isRendering()) {
            return;
        }
        ItemStack stack = inventory.getStackInSlot(slot);
        if (stack.m_41619_()) {
            for (int i = 0; i < 9; ++i) {
                this.crafting.removeRecipe(slot * 9 + i);
            }
            this.enabledSlots |= 511 << 9 * (slot + 1);
        } else {
            for (int i = 0; i < 9; ++i) {
                CraftRecipe recipe = MemoryStickItem.loadRecipe(stack, i);
                if (recipe != null && recipe.validate(this.m_58904_())) {
                    this.crafting.saveRecipe(slot * 9 + i, recipe);
                    continue;
                }
                this.crafting.removeRecipe(slot * 9 + i);
            }
            this.enabledSlots &= ~(511 << 9 * (slot + 1));
        }
    }

    @Override
    public void createMainTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
    }

    @Override
    public void createTrashTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
    }

    @Override
    public CraftingList getExpansionRecipeList() {
        return this.crafting;
    }

    @Override
    public void createSnapshot() {
        if (this.snapshot != null) {
            throw new RuntimeException("Snapshot already existed");
        }
        this.snapshot = new Snapshot(0);
    }

    @Override
    public void releaseSnapshot() {
        if (this.snapshot == null) {
            throw new IllegalStateException("Snapshot is missing");
        }
        this.snapshot = null;
    }

    @Override
    public void triggerRestock(IItemTransporter transporter, IFluidHandler handler, Player player) {
    }

    @Override
    public int getEnabledSlots() {
        return this.enabledSlots;
    }

    @Override
    public boolean isServerSided() {
        return this.isSimulating();
    }

    @Override
    public CraftingList getRecipes() {
        return this.crafting;
    }
}

