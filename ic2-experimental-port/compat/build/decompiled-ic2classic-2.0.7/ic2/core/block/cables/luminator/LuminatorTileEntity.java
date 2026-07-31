/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.common.Tags$Blocks
 */
package ic2.core.block.cables.luminator;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.cables.luminator.LuminatorBlock;
import ic2.core.block.rendering.block.LuminatorModel;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.Tags;

public class LuminatorTileEntity
extends BaseTileEntity
implements ITickListener,
IEnergySink,
IClickable,
IWrenchRemovable,
IEUStorage,
ITileActivityProvider {
    int energy = 0;
    int maxEnergy = 50;
    boolean addedToEnet;
    @NetworkInfo
    ItemStack glassBlock = ItemStack.f_41583_;

    public LuminatorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("glassBlock");
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = NBTUtils.getInt(compound, "energy", this.maxEnergy);
        this.glassBlock = NBTUtils.loadItem(compound, "glass", ItemStack.f_41583_);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "energy", this.energy, this.maxEnergy);
        NBTUtils.saveItem(compound, "glass", this.glassBlock);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack held = player.m_21120_(hand);
        if (held.m_41619_() && !this.glassBlock.m_41619_() && player.m_6144_()) {
            this.setGlass(ItemStack.f_41583_);
            return true;
        }
        Block block = Block.m_49814_((Item)held.m_41720_());
        if (this.glassBlock.m_41619_() && (block.m_49966_().m_204336_(Tags.Blocks.GLASS) || block == IC2Blocks.REINFORCED_CLEAR_GLASS || block == IC2Blocks.REINFORCED_GLASS)) {
            this.setGlass(held);
            if (!player.m_7500_()) {
                held.m_41774_(1);
            }
            return true;
        }
        int adding = ElectricItem.MANAGER.discharge(held, 1000 - this.energy, 4, true, true, false);
        if (adding > 0) {
            this.energy += adding;
        }
        return false;
    }

    public BlockState getGlassState() {
        return this.glassBlock.m_41619_() ? null : Block.m_49814_((Item)this.glassBlock.m_41720_()).m_49966_();
    }

    public void setGlass(ItemStack stack) {
        if (stack.m_41619_() || !StackUtil.isStackEqual(stack, this.glassBlock)) {
            Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)this.glassBlock);
        }
        this.glassBlock = StackUtil.copyWithSize(stack, 1);
        this.updateTileField("glassBlock");
        this.onStateChanged();
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("glassBlock")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
        if (fields.contains("isActive") || fields.contains("lightLevel")) {
            this.f_58857_.m_7726_().m_7827_().m_7174_(this.m_58899_());
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.m_58900_().m_61138_((Property)LuminatorBlock.WATER) && ((Boolean)this.m_58900_().m_61143_((Property)LuminatorBlock.WATER)).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)this.f_58857_));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        BlockState state = this.getGlassState();
        if (state != null) {
            return ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, T -> LuminatorModel.createQuads(this.m_58904_(), this.m_58899_(), state)).build();
        }
        return super.getModelData();
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        EnergyNet.INSTANCE.updateTile(this);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (!this.addedToEnet) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.LUMINATOR;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return false;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }

    public int getLightLevel() {
        return this.isActive() ? 15 : 0;
    }

    @Override
    public int getMaxEU() {
        return this.maxEnergy;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getTier() {
        return this.getSinkTier();
    }

    @Override
    public void onTick() {
        if (this.clock(10)) {
            if (this.energy > 0) {
                if (!this.isActive()) {
                    --this.energy;
                }
                this.setActive(true);
                --this.energy;
            } else {
                this.setActive(false);
            }
        }
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return side == this.getFacing().m_122424_();
    }

    @Override
    public int getSinkTier() {
        return 4;
    }

    @Override
    public int getRequestedEnergy() {
        return this.maxEnergy - this.energy;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
        }
        return amount - added;
    }
}

