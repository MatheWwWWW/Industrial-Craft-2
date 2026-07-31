/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BrewingStandBlockEntity
 */
package ic2.core.utils.compat;

import ic2.api.tiles.readers.IActivityProvider;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.core.platform.corehacks.mixins.server.info.BrewingMixin;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;

public class BrewingStandHelper
implements IActivityProvider,
IFuelStorage,
IProgressMachine {
    BrewingMixin brewingStand;

    public BrewingStandHelper(BlockEntity brewingStand) {
        this((BrewingStandBlockEntity)brewingStand);
    }

    public BrewingStandHelper(BrewingStandBlockEntity brewingStand) {
        this.brewingStand = (BrewingMixin)brewingStand;
    }

    @Override
    public float getProgress() {
        return 400 - this.brewingStand.getProgress();
    }

    @Override
    public float getMaxProgress() {
        return 400.0f;
    }

    @Override
    public int getFuel() {
        return this.brewingStand.getFuel();
    }

    @Override
    public int getMaxFuel() {
        return 20;
    }

    @Override
    public boolean isActivated() {
        return this.brewingStand.getProgress() > 0;
    }
}

