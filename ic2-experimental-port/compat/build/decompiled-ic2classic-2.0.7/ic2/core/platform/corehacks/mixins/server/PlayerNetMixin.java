/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.network.ServerGamePacketListenerImpl
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package ic2.core.platform.corehacks.mixins.server;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ServerGamePacketListenerImpl.class})
public interface PlayerNetMixin {
    @Accessor(value="aboveGroundTickCount")
    public void setAboveGroundTickCount(int var1);

    @Accessor(value="aboveGroundVehicleTickCount")
    public void setAboveGroundVehicleTickCount(int var1);
}

