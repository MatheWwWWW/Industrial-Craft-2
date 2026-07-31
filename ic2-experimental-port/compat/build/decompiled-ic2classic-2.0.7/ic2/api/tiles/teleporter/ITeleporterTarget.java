/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.api.tiles.teleporter;

import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.api.util.ILocation;
import net.minecraft.core.Direction;

public interface ITeleporterTarget
extends ILocation {
    public Direction getFacing();

    public TeleportType getSendType();

    public boolean canReceive(TeleportType var1);

    public boolean setTarget(TeleporterTarget var1);

    public boolean hasTarget(TeleporterTarget var1);

    public static enum TeleportType {
        ENTITY,
        SPAWNER,
        ITEM,
        FLUID,
        ENERGY,
        NOTHING;


        public boolean matches(TeleportType other) {
            if (other == SPAWNER) {
                return this == ENTITY;
            }
            if (this == SPAWNER) {
                return other == ENTITY;
            }
            return this == other;
        }
    }
}

