/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.personal.tile;

import ic2.api.network.buffer.NetworkInfo;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.BucketFiller;
import ic2.core.block.base.tiles.impls.BasePersonalTileEntity;
import ic2.core.block.personal.container.PersonalTankContainer;
import ic2.core.fluid.IC2Tank;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.NBTUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PersonalTankTileEntity
extends BasePersonalTileEntity
implements ITickListener,
IClickable,
IDropProvider {
    @NetworkInfo
    public IC2Tank tank = new IC2Tank(128000);
    public SimpleInventory inventory = new SimpleInventory(4);
    protected BucketFiller filler = new BucketFiller(this.inventory, this, (IFluidHandler)this.tank, 0, 1);
    protected BucketFiller drainer = new BucketFiller(this.inventory, this, (IFluidHandler)this.tank, 2, 3);

    public PersonalTankTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("tank");
        this.tank.addListener(T -> this.updateGuiField("tank"));
        this.addPersonalCapability(ForgeCapabilities.FLUID_HANDLER, this.tank);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new PersonalTankContainer(this, player, windowID);
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        this.inventory.addToDrops(drops);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PERSONAL_TANK;
    }

    @Override
    public boolean isAllowingUI() {
        return false;
    }

    @Override
    public void voidStorage() {
        this.tank.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public CompoundTag writeItemData(CompoundTag nbt) {
        super.writeItemData(nbt);
        NBTUtils.put(nbt, "tank", this.tank.writeToNBT(new CompoundTag()));
        NBTUtils.put(nbt, "inv", this.inventory.save(new CompoundTag()));
        return nbt;
    }

    @Override
    public void readItemData(CompoundTag nbt, boolean place) {
        super.readItemData(nbt, place);
        this.tank.readFromNBT(nbt.m_128469_("tank"));
        this.inventory.load(nbt.m_128469_("inv"));
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        if (this.canDoAction(player.m_20148_(), Action.PERSONAL_BLOCKS_EDIT, false)) {
            if (FluidHelper.drainContainers(player.m_21120_(hand), player, (IFluidHandler)this.tank)) {
                return true;
            }
            return FluidHelper.fillContainers(player.m_21120_(hand), player, (IFluidHandler)this.tank);
        }
        return false;
    }

    @Override
    public void onTick() {
        this.filler.fillTank();
        this.drainer.fillItem();
    }
}

