/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BrewingStandBlockEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package ic2.core.platform.corehacks.mixins.server.info;

import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={BrewingStandBlockEntity.class})
public interface BrewingMixin {
    @Accessor(value="fuel")
    public int getFuel();

    @Accessor(value="brewTime")
    public int getProgress();
}

