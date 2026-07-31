/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.container.ContainerMetalFormer;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityMetalFormer
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>
implements INetworkClientTileEntityEventListener {
    private int mode;
    public static final int EventSwitch = 0;

    public TileEntityMetalFormer(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.METAL_FORMER, blockPos, blockState, 10, 200, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, (Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>>)Recipes.metalformerExtruding);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.setMode(compoundTag.m_128451_("mode"));
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("mode", this.mode);
    }

    @Override
    public ContainerBase<TileEntityMetalFormer> createServerScreenHandler(int n, Player player) {
        return new ContainerMetalFormer(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerMetalFormer(n, inventory, this);
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        switch (n) {
            case 0: {
                this.cycleMode();
            }
        }
    }

    @Override
    public void onNetworkUpdate(String string) {
        super.onNetworkUpdate(string);
        if (string.equals("mode")) {
            this.setMode(this.mode);
        }
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int n) {
        InvSlotProcessableGeneric invSlotProcessableGeneric = (InvSlotProcessableGeneric)this.inputSlot;
        switch (n) {
            case 0: {
                invSlotProcessableGeneric.setRecipeManager(Recipes.metalformerExtruding);
                break;
            }
            case 1: {
                invSlotProcessableGeneric.setRecipeManager(Recipes.metalformerRolling);
                break;
            }
            case 2: {
                invSlotProcessableGeneric.setRecipeManager(Recipes.metalformerCutting);
                break;
            }
            default: {
                throw new RuntimeException("invalid mode: " + n);
            }
        }
        this.mode = n;
    }

    private void cycleMode() {
        this.setMode((this.getMode() + 1) % 3);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

