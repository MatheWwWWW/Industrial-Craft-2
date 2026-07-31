/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.crops.soils;

import ic2.api.crops.ISubSoil;
import net.minecraft.world.level.block.state.BlockState;

public class BaseSubSoil
implements ISubSoil {
    int humidity;
    int nutrients;

    public BaseSubSoil(int humidity, int nutrients) {
        this.humidity = humidity;
        this.nutrients = nutrients;
    }

    @Override
    public int getHumidity(BlockState state) {
        return this.humidity;
    }

    @Override
    public int getNutrients(BlockState state) {
        return this.nutrients;
    }
}

