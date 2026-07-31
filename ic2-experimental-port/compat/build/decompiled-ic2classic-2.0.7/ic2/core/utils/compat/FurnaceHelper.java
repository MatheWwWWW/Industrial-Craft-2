/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.utils.compat;

import ic2.api.tiles.readers.IActivityProvider;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.core.platform.corehacks.mixins.server.info.FurnaceMixin;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FurnaceHelper
implements IFuelStorage,
IActivityProvider,
IProgressMachine {
    FurnaceMixin tile;

    public FurnaceHelper(BlockEntity tile) {
        this((AbstractFurnaceBlockEntity)tile);
    }

    public FurnaceHelper(AbstractFurnaceBlockEntity tile) {
        this.tile = (FurnaceMixin)tile;
    }

    @Override
    public float getProgress() {
        return this.tile.getProgress();
    }

    @Override
    public float getMaxProgress() {
        return this.tile.getMaxProgress();
    }

    @Override
    public boolean isActivated() {
        return this.tile.getCurrentFuel() > 0;
    }

    @Override
    public int getFuel() {
        return this.tile.getCurrentFuel();
    }

    @Override
    public int getMaxFuel() {
        return this.tile.getMaxFuel();
    }
}

