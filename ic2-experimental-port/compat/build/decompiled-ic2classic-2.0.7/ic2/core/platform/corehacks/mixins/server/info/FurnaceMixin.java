/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package ic2.core.platform.corehacks.mixins.server.info;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={AbstractFurnaceBlockEntity.class})
public interface FurnaceMixin {
    @Accessor(value="litTime")
    public int getCurrentFuel();

    @Accessor(value="litDuration")
    public int getMaxFuel();

    @Accessor(value="cookingProgress")
    public int getProgress();

    @Accessor(value="cookingTotalTime")
    public int getMaxProgress();
}

