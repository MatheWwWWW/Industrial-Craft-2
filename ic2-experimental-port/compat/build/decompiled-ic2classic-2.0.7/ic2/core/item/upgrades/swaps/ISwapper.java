/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.upgrades.swaps;

import ic2.api.blocks.PainterHelper;
import ic2.core.item.upgrades.swaps.BatteryStationSwapper;
import ic2.core.item.upgrades.swaps.ChargepadSwapper;
import ic2.core.item.upgrades.swaps.ChargingBenchSwapper;
import ic2.core.item.upgrades.swaps.EmptySwapper;
import ic2.core.item.upgrades.swaps.EnergyStorageSwapper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface ISwapper {
    public static ISwapper empty() {
        return EmptySwapper.INSTANCE;
    }

    public static ISwapper chargePad() {
        return ChargepadSwapper.INSTANCE;
    }

    public static ISwapper chargeBench() {
        return ChargingBenchSwapper.INSTANCE;
    }

    public static ISwapper batteryStation() {
        return BatteryStationSwapper.INSTANCE;
    }

    public static ISwapper energyStorage() {
        return EnergyStorageSwapper.INSTANCE;
    }

    public void transfer(BlockEntity var1, BlockEntity var2);

    default public BlockState transfer(BlockState from, Block to) {
        return PainterHelper.copyProperties(from, to.m_49966_());
    }
}

