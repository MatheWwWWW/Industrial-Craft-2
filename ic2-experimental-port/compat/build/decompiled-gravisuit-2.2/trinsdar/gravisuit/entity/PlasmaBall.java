/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.items.electric.ElectricItem
 *  ic2.api.tiles.teleporter.TeleporterTarget
 *  ic2.core.utils.helpers.TeleportUtil
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.PlayMessages$SpawnEntity
 */
package trinsdar.gravisuit.entity;

import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.utils.helpers.TeleportUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import trinsdar.gravisuit.block.BlockEntityPlasmaPortal;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.util.Registry;

public class PlasmaBall
extends ThrowableProjectile {
    ItemRelocator.TeleportData data;
    ItemStack relocator;
    String uuid = "";
    Player shooter;
    InteractionHand hand;

    public PlasmaBall(Level level, Player shooter, ItemRelocator.TeleportData data, InteractionHand hand) {
        super(Registry.PLASMA_BALL_ENTITY_TYPE, level);
        this.data = data;
        this.relocator = shooter.m_21120_(hand);
        this.shooter = shooter;
        this.hand = hand;
        double y = shooter.m_20186_() + (double)shooter.m_20192_() - 0.1;
        double yaw = Math.toRadians(shooter.m_146908_());
        double pitch = Math.toRadians(shooter.m_146909_());
        this.uuid = shooter.m_20149_();
        this.m_6034_(shooter.m_20185_() - Math.cos(yaw) * 0.16, y, shooter.m_20189_() - Math.sin(yaw) * 0.16);
        this.setLaserHeading(-Math.sin(yaw) * Math.cos(pitch), -Math.sin(pitch), Math.cos(yaw) * Math.cos(pitch), 1.0);
    }

    public PlasmaBall(EntityType<PlasmaBall> plasmaBallEntityEntityType, Level level) {
        super(plasmaBallEntityEntityType, level);
    }

    public PlasmaBall(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(Registry.PLASMA_BALL_ENTITY_TYPE, level);
    }

    protected float m_7139_() {
        return 0.0f;
    }

    public void setLaserHeading(Vec3 motion, double speed) {
        this.setLaserHeading(motion.m_7096_(), motion.m_7098_(), motion.m_7094_(), speed);
    }

    public void setLaserHeading(double newMotionX, double newMotionY, double newMotionZ, double speed) {
        double newSpeed = 1.0 / Math.sqrt(newMotionX * newMotionX + newMotionY * newMotionY + newMotionZ * newMotionZ) * speed;
        this.m_20334_(newMotionX * newSpeed, newMotionY * newSpeed, newMotionZ * newSpeed);
        Vec3 motion = this.m_20184_();
        this.f_19859_ = (float)Math.toDegrees(Math.atan2(motion.m_7096_(), motion.m_7094_()));
        this.m_146922_(this.f_19859_);
        this.f_19860_ = (float)Math.toDegrees(Math.atan2(motion.m_7098_(), Math.sqrt(motion.m_7096_() * motion.m_7096_() + motion.m_7094_() * motion.m_7094_())));
        this.m_146926_(this.f_19860_);
    }

    protected void m_8097_() {
    }

    protected void m_5790_(EntityHitResult result) {
        Entity entity = result.m_82443_();
        if (entity instanceof LivingEntity) {
            CompoundTag nbt;
            LivingEntity livingEntity = (LivingEntity)entity;
            if (IC2.PLATFORM.isSimulating() && !this.relocator.m_41619_() && (nbt = this.relocator.m_41784_()).m_128445_("mode") == 1) {
                this.teleportEntity(livingEntity, this.data.toTeleportTarget(), entity.m_6374_(), this.relocator);
                this.m_146870_();
            }
        }
    }

    protected void m_8060_(BlockHitResult result) {
        CompoundTag nbt;
        if (IC2.PLATFORM.isSimulating() && !this.relocator.m_41619_() && ElectricItem.MANAGER.canUse(this.relocator, 10000000) && (nbt = this.relocator.m_41784_()).m_128445_("mode") == 2) {
            CompoundTag tag;
            TeleporterTarget lastTeleporterTarget;
            ServerLevel lastLevel;
            BlockPos pos = result.m_82425_().m_121945_(result.m_82434_());
            this.m_9236_().m_7731_(pos, Registry.PLASMA_PORTAL.m_49966_(), 3);
            BlockEntity be = this.m_9236_().m_7702_(pos);
            BlockPos teleportPos = BlockPos.m_122022_((long)this.data.getPos());
            ItemRelocator.TeleportData origin = new ItemRelocator.TeleportData(pos.m_121878_(), this.m_9236_().m_46472_().m_135782_().toString(), "origin");
            if (be instanceof BlockEntityPlasmaPortal) {
                BlockEntityPlasmaPortal portal = (BlockEntityPlasmaPortal)be;
                portal.setOtherEnd(this.data);
            }
            TeleporterTarget teleporterTarget = this.data.toTeleportTarget();
            ServerLevel teleportWorld = teleporterTarget.getWorld();
            teleportWorld.m_7731_(teleportPos, Registry.PLASMA_PORTAL.m_49966_(), 3);
            BlockEntity teleportBE = teleportWorld.m_7702_(teleportPos);
            if (teleportBE instanceof BlockEntityPlasmaPortal) {
                BlockEntityPlasmaPortal portal = (BlockEntityPlasmaPortal)teleportBE;
                portal.setOtherEnd(origin);
            }
            if (nbt.m_128441_("lastPosition") && (lastLevel = (lastTeleporterTarget = ItemRelocator.TeleportData.fromNBT(tag = nbt.m_128469_("lastPosition"), "lastPosition").toTeleportTarget()).getWorld()).m_8055_(lastTeleporterTarget.getTargetPosition()).m_60734_() == Registry.PLASMA_PORTAL) {
                lastLevel.m_7731_(lastTeleporterTarget.getTargetPosition(), Blocks.f_50016_.m_49966_(), 3);
            }
            nbt.m_128365_("lastPosition", (Tag)origin.writeToNBT());
        }
        this.m_146870_();
    }

    public void teleportEntity(LivingEntity player, TeleporterTarget target, Direction dir, ItemStack stack) {
        int weight = TeleportUtil.getWeightOfEntity((Entity)player, (boolean)true);
        if (weight != 0) {
            ServerLevel server = target.getWorld();
            BlockPos pos = target.getTargetPosition();
            if (ElectricItem.MANAGER.use(stack, (int)((double)weight * TeleportUtil.getDistanceCost((Level)player.m_9236_(), (BlockPos)player.m_20183_(), (Level)server, (BlockPos)pos) * 5.0), player)) {
                TeleportUtil.teleportEntity((Entity)player, (ServerLevel)server, (BlockPos)pos, (Direction)dir);
            }
        }
    }

    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128365_("relocator", (Tag)this.relocator.m_41739_(new CompoundTag()));
    }

    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        this.relocator = ItemStack.m_41712_((CompoundTag)compound.m_128469_("relocator"));
    }
}

