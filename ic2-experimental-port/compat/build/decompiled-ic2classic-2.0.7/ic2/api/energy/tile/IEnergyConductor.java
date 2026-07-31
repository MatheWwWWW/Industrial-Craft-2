/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public interface IEnergyConductor
extends IEnergyAcceptor,
IEnergyEmitter {
    default public boolean isWaterlogged() {
        BlockState state = ((BlockEntity)this).m_58900_();
        return state.m_61138_((Property)BlockStateProperties.f_61362_) && (Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_) != false;
    }

    public boolean isLavaLogged();

    public double getConductionLoss();

    public int getInsulationEnergyAbsorption();

    public int getInsulationBreakdownEnergy();

    public int getConductorBreakdownEnergy();

    public void removeInsulation();

    public void removeConductor();
}

