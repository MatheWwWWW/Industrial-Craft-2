/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.platform.wind;

import ic2.core.platform.wind.IWindStream;
import java.util.List;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public interface IWindCalculation {
    public void update(Level var1);

    public double getWindSpeed(AABB var1, float var2, float var3);

    public List<IWindStream> getStreams();
}

