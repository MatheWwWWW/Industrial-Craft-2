/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.box;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityStorageBox
extends TileEntityInventory
implements IHasGui {
    protected final InvSlot inventory;

    public TileEntityStorageBox(BlockEntityType<? extends TileEntityStorageBox> blockEntityType, BlockPos blockPos, BlockState blockState, int n) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.inventory = new InvSlot(this, "inventory", InvSlot.Access.IO, n, InvSlot.InvSide.ANY);
    }

    @Override
    protected List<ItemStack> getAuxDrops(int n) {
        return Collections.emptyList();
    }

    @Override
    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
        super.onPlaced(itemStack, livingEntity, direction);
        if (!this.m_58904_().f_46443_) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            this.inventory.readFromNbt(compoundTag);
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack itemStack, boolean bl) {
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        if (!this.inventory.isEmpty()) {
            this.inventory.writeToNbt(compoundTag);
        }
        return itemStack;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void addInformation(ItemStack itemStack, List<String> list, TooltipFlag tooltipFlag) {
        list.add("Stores items even when broken");
        list.add("Inventory size: " + this.inventory.size());
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }
}

