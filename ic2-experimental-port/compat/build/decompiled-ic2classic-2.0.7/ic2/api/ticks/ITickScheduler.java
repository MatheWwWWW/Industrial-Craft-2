/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.Level
 */
package ic2.api.ticks;

import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

public interface ITickScheduler {
    public void addWorldCallback(Level var1, ToIntFunction<Level> var2);

    public void addWorldCallback(Level var1, ToIntFunction<Level> var2, int var3);

    public void addServerCallback(ToIntFunction<MinecraftServer> var1);

    public void addServerCallback(ToIntFunction<MinecraftServer> var1, int var2);

    public void addClientCallback(IntSupplier var1);

    public void addClientCallback(IntSupplier var1, int var2);
}

