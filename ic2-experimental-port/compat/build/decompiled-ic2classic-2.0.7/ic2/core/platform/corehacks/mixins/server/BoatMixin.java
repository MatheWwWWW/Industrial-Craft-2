/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.entity.vehicle.Boat$Status
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package ic2.core.platform.corehacks.mixins.server;

import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={Boat.class})
public interface BoatMixin {
    @Accessor(value="status")
    public Boat.Status getActualStatus();

    @Accessor(value="lastYd")
    public void setLastYDelta(double var1);
}

