/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.data.BuiltinRegistries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.entity.boss.wither.WitherBoss
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.entity.vehicle.Minecart
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.portal.PortalInfo
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.util.ITeleporter
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.utils.helpers;

import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fluids.FluidStack;

public class TeleportUtil {
    public static double getDistanceCost(Level sourceDim, BlockPos sourcePos, Level targetDim, BlockPos targetPos) {
        boolean dimSwitch = !sourceDim.m_46472_().equals((Object)targetDim.m_46472_());
        return Math.pow(Math.sqrt(sourcePos.m_123331_((Vec3i)targetPos) * TeleportUtil.getDistanceScale(sourceDim, targetDim)) + 10.0, dimSwitch ? 0.9 : 0.7);
    }

    public static double getDistanceScale(Level sourceDim, Level targetDim) {
        double source = TeleportUtil.getScale(sourceDim);
        double target = TeleportUtil.getScale(targetDim);
        return Math.min(source, target) / Math.max(source, target);
    }

    @OnlyIn(value=Dist.CLIENT)
    public static double getDistanceCost(ResourceKey<Level> sourceDim, BlockPos sourcePos, ResourceKey<Level> targetDim, BlockPos targetPos) {
        boolean dimSwitch = !sourceDim.equals(targetDim);
        return Math.pow(Math.sqrt(sourcePos.m_123331_((Vec3i)targetPos) * TeleportUtil.getDistanceScale(sourceDim, targetDim)) + 10.0, dimSwitch ? 0.9 : 0.7);
    }

    @OnlyIn(value=Dist.CLIENT)
    public static double getDistanceScale(ResourceKey<Level> sourceDim, ResourceKey<Level> targetDim) {
        Optional type = BuiltinRegistries.f_206379_.m_6632_(Registry.f_122819_);
        if (type.isEmpty()) {
            return 1.0;
        }
        Registry registry = (Registry)type.orElseThrow();
        double source = TeleportUtil.getScale((Level)registry.m_7745_(sourceDim.m_135782_()));
        double target = TeleportUtil.getScale((Level)registry.m_7745_(targetDim.m_135782_()));
        return Math.min(source, target) / Math.max(source, target);
    }

    private static double getScale(Level level) {
        return level == null ? 1.0 : level.m_6042_().f_63859_();
    }

    public static void teleportEntity(Entity entity, ServerLevel world, BlockPos destination, Direction dir) {
        entity.m_19877_();
        double x = (float)(destination.m_123341_() + dir.m_122429_()) + 0.5f;
        double y = destination.m_123342_() + dir.m_122430_() + (dir.m_122434_() == Direction.Axis.Y && dir.m_122421_() == Direction.AxisDirection.NEGATIVE ? -1 : 0);
        double z = (float)(destination.m_123343_() + dir.m_122431_()) + 0.5f;
        if (entity.f_19853_.m_46472_() != world.m_46472_()) {
            entity.changeDimension(world, (ITeleporter)new TeleportInfo(x, y, z));
        } else {
            entity.m_6021_(x, y, z);
        }
    }

    public static void teleportEntity(Entity entity, ServerLevel world, BlockPos destination) {
        entity.m_19877_();
        double x = destination.m_123341_();
        double y = destination.m_123342_();
        double z = destination.m_123343_();
        if (entity.f_19853_.m_46472_() != world.m_46472_()) {
            entity.changeDimension(world, (ITeleporter)new TeleportInfo(x, y, z));
        } else {
            entity.m_6021_(x, y, z);
        }
    }

    public static int getWeightOfFluid(FluidStack fluid) {
        return 1 + fluid.getAmount() / 10;
    }

    public static int getWeightOfItem(ItemStack stack) {
        return 100 * (stack.m_41613_() / stack.m_41741_());
    }

    public static int getWeightOfEntity(Entity entity, boolean includeInv) {
        int totalWeight = 0;
        if (entity instanceof ItemEntity) {
            ItemStack is = ((ItemEntity)entity).m_32055_();
            totalWeight += 100 * is.m_41613_() / is.m_41741_();
        } else if (entity instanceof Animal || entity instanceof Minecart || entity instanceof Boat) {
            totalWeight += 100;
        } else if (entity instanceof Player) {
            totalWeight += 1000;
            if (includeInv) {
                Inventory inv = ((Player)entity).m_150109_();
                int m = inv.m_6643_();
                for (int i = 0; i < m; ++i) {
                    ItemStack stack = inv.m_8020_(i);
                    if (stack.m_41619_()) continue;
                    totalWeight += 100 * (stack.m_41613_() / stack.m_41741_());
                }
            }
        } else if (entity instanceof Ghast) {
            totalWeight += 2500;
        } else if (entity instanceof EnderDragon || entity instanceof WitherBoss) {
            totalWeight += 10000;
        } else if (entity instanceof PathfinderMob) {
            totalWeight += 500;
        }
        if (includeInv && entity instanceof LivingEntity && !(entity instanceof Player)) {
            LivingEntity living = (LivingEntity)entity;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = living.m_6844_(slot);
                if (stack.m_41619_()) continue;
                totalWeight += 100 * (stack.m_41613_() / stack.m_41741_());
            }
        }
        return totalWeight;
    }

    private static final class TeleportInfo
    implements ITeleporter {
        private final double x;
        private final double y;
        private final double z;

        public TeleportInfo(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            return repositionEntity.apply(false);
        }

        @Nullable
        public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
            return new PortalInfo(new Vec3(this.x, this.y, this.z), Vec3.f_82478_, entity.m_146908_(), entity.m_146909_());
        }
    }
}

