/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 */
package ic2.core.block;

import ic2.core.block.EntityIC2Explosive;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import net.minecraft.world.World;

public class EntityItnt
extends EntityIC2Explosive {
    public EntityItnt(World world, double x, double y, double z) {
        super(world, x, y, z, 60, 5.5f, 0.9f, 0.3f, BlockName.te.getBlockState(TeBlock.itnt), 0);
    }

    public EntityItnt(World world) {
        this(world, 0.0, 0.0, 0.0);
    }
}

