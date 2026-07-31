/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.platform.wind;

import ic2.core.platform.wind.IWindCalculation;
import ic2.core.platform.wind.impl.SimpleWindCalculation;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class WindManager {
    public static final WindManager INSTANCE = new WindManager();
    Map<Level, IWindCalculation> worlds = CollectionUtils.createLinkedMap();

    public void onTick(Level world) {
        IWindCalculation calc = this.worlds.get(world);
        if (calc == null) {
            calc = new SimpleWindCalculation();
            this.worlds.put(world, calc);
        }
        calc.update(world);
    }

    public void registerWorld(Level world, IWindCalculation calculation) {
        this.worlds.put(world, calculation);
    }

    public void onWorldUnload(Level world) {
        this.worlds.remove(world);
    }

    public double getAirSpeed(Level world, BlockPos box, float direction, float angle) {
        return this.getAirSpeed(world, new AABB(box), direction, angle);
    }

    public double getAirSpeed(Level world, AABB box, float direction, float angle) {
        IWindCalculation calc = this.worlds.get(world);
        if (calc == null) {
            calc = new SimpleWindCalculation();
            this.worlds.put(world, calc);
        }
        return calc.getWindSpeed(box, direction, angle);
    }
}

