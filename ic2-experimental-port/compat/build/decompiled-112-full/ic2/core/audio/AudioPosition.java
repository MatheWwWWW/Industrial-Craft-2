/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.audio;

import ic2.core.audio.PositionSpec;
import java.lang.ref.WeakReference;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AudioPosition {
    private final WeakReference<World> worldRef;
    public final float x;
    public final float y;
    public final float z;

    public static AudioPosition getFrom(Object obj, PositionSpec positionSpec) {
        if (obj instanceof AudioPosition) {
            return (AudioPosition)obj;
        }
        if (obj instanceof Entity) {
            Entity e = (Entity)obj;
            return new AudioPosition(e.func_130014_f_(), (float)e.field_70165_t, (float)e.field_70163_u, (float)e.field_70161_v);
        }
        if (obj instanceof TileEntity) {
            TileEntity te = (TileEntity)obj;
            return new AudioPosition(te.func_145831_w(), (float)te.func_174877_v().func_177958_n() + 0.5f, (float)te.func_174877_v().func_177956_o() + 0.5f, (float)te.func_174877_v().func_177952_p() + 0.5f);
        }
        return null;
    }

    public AudioPosition(World world, float x, float y, float z) {
        this.worldRef = new WeakReference<World>(world);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AudioPosition(World world, BlockPos pos) {
        this(world, (float)pos.func_177958_n() + 0.5f, (float)pos.func_177956_o() + 0.5f, (float)pos.func_177952_p() + 0.5f);
    }

    public World getWorld() {
        return (World)this.worldRef.get();
    }
}

