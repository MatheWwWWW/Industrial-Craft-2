/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BaseSpawner
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package ic2.core.platform.corehacks.mixins.server.info;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={BaseSpawner.class})
public interface SpawnerMixin {
    @Invoker(value="isNearPlayer")
    public boolean isActive(Level var1, BlockPos var2);

    @Accessor(value="spawnDelay")
    public int getDelay();

    @Accessor(value="maxSpawnDelay")
    public int getMaxDelay();
}

