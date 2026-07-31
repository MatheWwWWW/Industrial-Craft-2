/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.machines.components.nv.ArmorComponent;
import ic2.core.block.machines.containers.nv.ArmorConfiguratorContainer;
import ic2.core.block.machines.logic.armor.ArmorInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ArmorConfiguratorTileEntity
extends BaseInventoryTileEntity
implements ITileGui,
IWrenchRemovable,
IDropProvider {
    public ArmorInventory armory = new ArmorInventory(this);
    @NetworkInfo
    public int offset = 0;

    public ArmorConfiguratorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
        this.addGuiFields("offset");
    }

    public void syncArmor() {
        this.updateGuiFields("offset");
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        ItemStack stack = this.armory.getArmor().getStackInSlot(0);
        if (!stack.m_41619_()) {
            drops.add(stack);
        }
    }

    @Override
    public boolean isAllowingUI() {
        return false;
    }

    public void syncOffset() {
        this.updateGuiField("offset");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        this.armory.writeToNBT(compound);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.armory.readFromNBT(compound);
    }

    @Override
    public boolean allowsUI() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ARMOR_CONFIGURATOR;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.85;
    }

    @Override
    public void onGuiFieldChanged(Set<String> fields, Player player) {
        ArmorConfiguratorContainer container;
        super.onGuiFieldChanged(fields, player);
        if (fields.contains("offset") && (container = (ArmorConfiguratorContainer)this.getContainer(player, ArmorConfiguratorContainer.class)) != null) {
            container.getComponent(ArmorComponent.class).syncScroll();
        }
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ArmorConfiguratorContainer(this, player, windowID);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 1) {
            int old = this.offset;
            this.offset = Mth.m_14045_((int)value, (int)0, (int)this.armory.getTypes());
            if (this.offset == old) {
                return;
            }
            IC2Container container = (IC2Container)this.getContainer(entity, IC2Container.class);
            if (container != null) {
                container.clearSlots(ArmorConfiguratorContainer.ModuleSlot.class);
            }
            this.syncOffset();
        }
    }
}

