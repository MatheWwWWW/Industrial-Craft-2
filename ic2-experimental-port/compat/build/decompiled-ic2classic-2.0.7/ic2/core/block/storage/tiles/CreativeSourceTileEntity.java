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
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.IElectrolyzerProvider;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.storage.container.CreativeSourceContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeSourceTileEntity
extends BaseTileEntity
implements IMultiEnergySource,
IElectrolyzerProvider,
ITileGui,
IWrenchRemovable {
    @NetworkInfo
    public int packets = 4;
    @NetworkInfo
    public int packetEnergy = 32;
    boolean addedToEnet;

    public CreativeSourceTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("packets", "packetEnergy");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CREATIVE_SOURCE;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new CreativeSourceContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("energy", this.packetEnergy);
        compound.m_128376_("packets", (short)this.packets);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.packetEnergy = compound.m_128451_("energy");
        this.packets = compound.m_128451_("packets");
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnet) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public int getSourceTier() {
        return EnergyNet.INSTANCE.getTierFromPower(this.packetEnergy);
    }

    @Override
    public int getMaxEnergyOutput() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getProvidedEnergy() {
        return this.packetEnergy;
    }

    @Override
    public void consumeEnergy(int consumed) {
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return true;
    }

    @Override
    public boolean hasMultiplePackets() {
        return this.packets > 0;
    }

    @Override
    public int getPacketCount() {
        return this.packets;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            this.packets = Mth.m_14045_((int)value, (int)1, (int)64);
            this.updateGuiField("packets");
        } else if (key == 1) {
            this.packetEnergy = Mth.m_14045_((int)value, (int)1, (int)32768);
            this.updateGuiField("packetEnergy");
        }
    }

    @Override
    public int getStoredEU() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxEU() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getTier() {
        return this.getSourceTier();
    }

    @Override
    public int addEnergy(int power) {
        return 0;
    }

    @Override
    public int drawEnergy(int power) {
        return power;
    }

    @Override
    public double getChargeLevel() {
        return 1.0;
    }

    @Override
    public int getProcessRate() {
        return 2048;
    }
}

