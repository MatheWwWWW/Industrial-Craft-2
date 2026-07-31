/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.entity.minecarts;

import ic2.core.entity.minecarts.ElectricMinecart;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MFEMinecart
extends ElectricMinecart {
    public MFEMinecart(EntityType<?> type, Level level) {
        super(type, level);
    }

    public MFEMinecart(EntityType<?> type, Level level, double x, double y, double z) {
        super(type, level, x, y, z);
    }

    @Override
    public int getMaxEU() {
        return 600000;
    }

    @Override
    public int getTier() {
        return 2;
    }

    @Override
    public int getGuiOffset() {
        return 0;
    }

    @Override
    public BlockState m_6390_() {
        return IC2Blocks.MFE.m_49966_();
    }

    @Override
    protected Item m_213728_() {
        return IC2Items.MFE_MINECART;
    }
}

