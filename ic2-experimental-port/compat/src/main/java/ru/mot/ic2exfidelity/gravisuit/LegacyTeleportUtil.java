package ru.mot.ic2exfidelity.gravisuit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

/** IC2 Classic's exact relocator weight, distance and placement formulas. */
public final class LegacyTeleportUtil {
    private LegacyTeleportUtil() {
    }

    public static double getDistanceCost(
            Level sourceLevel, BlockPos sourcePosition,
            Level targetLevel, BlockPos targetPosition) {
        boolean dimensionSwitch = !sourceLevel.m_46472_().equals(targetLevel.m_46472_());
        double distanceScale = getDistanceScale(sourceLevel, targetLevel);
        return Math.pow(
                Math.sqrt(sourcePosition.m_123331_((Vec3i) targetPosition)
                        * distanceScale) + 10.0,
                dimensionSwitch ? 0.9 : 0.7);
    }

    private static double getDistanceScale(Level source, Level target) {
        double sourceScale = source == null ? 1.0 : source.m_6042_().f_63859_();
        double targetScale = target == null ? 1.0 : target.m_6042_().f_63859_();
        return Math.min(sourceScale, targetScale) / Math.max(sourceScale, targetScale);
    }

    public static int getWeightOfEntity(Entity entity, boolean includeInventory) {
        int weight = 0;
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.m_32055_();
            weight += 100 * stack.m_41613_() / stack.m_41741_();
        } else if (entity instanceof Animal
                || entity instanceof Minecart
                || entity instanceof Boat) {
            weight += 100;
        } else if (entity instanceof Player player) {
            weight += 1_000;
            if (includeInventory) {
                for (int i = 0; i < player.m_150109_().m_6643_(); i++) {
                    ItemStack stack = player.m_150109_().m_8020_(i);
                    if (!stack.m_41619_()) {
                        weight += getWeightOfItem(stack);
                    }
                }
            }
        } else if (entity instanceof Ghast) {
            weight += 2_500;
        } else if (entity instanceof EnderDragon || entity instanceof WitherBoss) {
            weight += 10_000;
        } else if (entity instanceof PathfinderMob) {
            weight += 500;
        }
        if (includeInventory
                && entity instanceof LivingEntity living
                && !(entity instanceof Player)) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = living.m_6844_(slot);
                if (!stack.m_41619_()) {
                    weight += getWeightOfItem(stack);
                }
            }
        }
        return weight;
    }

    private static int getWeightOfItem(ItemStack stack) {
        return 100 * (stack.m_41613_() / stack.m_41741_());
    }

    public static void teleportEntity(
            Entity entity, ServerLevel destinationLevel,
            BlockPos destination, Direction direction) {
        entity.m_19877_();
        double x = destination.m_123341_() + direction.m_122429_() + 0.5;
        double y = destination.m_123342_() + direction.m_122430_()
                + (direction.m_122434_() == Direction.Axis.Y
                        && direction.m_122421_() == Direction.AxisDirection.NEGATIVE
                                ? -1 : 0);
        double z = destination.m_123343_() + direction.m_122431_() + 0.5;
        if (entity instanceof ServerPlayer player) {
            player.m_8999_(destinationLevel, x, y, z,
                    player.m_146908_(), player.m_146909_());
        } else if (entity.f_19853_.m_46472_() != destinationLevel.m_46472_()) {
            changeDimension(entity, destinationLevel, new FixedTeleporter(x, y, z));
        } else {
            entity.m_6021_(x, y, z);
        }
    }

    /**
     * Forge adds this overload after the official Minecraft jar is transformed.
     * The companion is deliberately compiled against the untouched official jar,
     * so reflection keeps that build reproducible while still using Forge's
     * ITeleporter path at runtime.
     */
    private static void changeDimension(
            Entity entity, ServerLevel destinationLevel, ITeleporter teleporter) {
        try {
            Method method = Entity.class.getMethod(
                    "changeDimension", ServerLevel.class, ITeleporter.class);
            method.invoke(entity, destinationLevel, teleporter);
        } catch (NoSuchMethodException | IllegalAccessException exception) {
            throw new IllegalStateException(
                    "Forge ITeleporter changeDimension overload is unavailable", exception);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw new IllegalStateException("Relocator dimension transfer failed", cause);
        }
    }

    private static final class FixedTeleporter implements ITeleporter {
        private final double x;
        private final double y;
        private final double z;

        private FixedTeleporter(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public Entity placeEntity(
                Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                float yaw, Function<Boolean, Entity> repositionEntity) {
            return repositionEntity.apply(false);
        }

        @Override
        public PortalInfo getPortalInfo(
                Entity entity, ServerLevel destinationWorld,
                Function<ServerLevel, PortalInfo> defaultPortalInfo) {
            return new PortalInfo(new Vec3(x, y, z), Vec3.f_82478_,
                    entity.m_146908_(), entity.m_146909_());
        }
    }
}
