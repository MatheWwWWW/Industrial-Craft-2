/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.tiles.teleporter.TeleporterTarget
 *  ic2.core.utils.helpers.TeleportUtil
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package trinsdar.gravisuit.block;

import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.utils.helpers.TeleportUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.util.Registry;

public class BlockEntityPlasmaPortal
extends BlockEntity {
    ItemRelocator.TeleportData otherEnd = null;
    boolean insidePortal = false;
    List<LivingEntity> entitesToTeleport = new ArrayList<LivingEntity>();
    int ticker = 0;

    public BlockEntityPlasmaPortal(BlockPos arg2, BlockState arg3) {
        super(Registry.PLASMA_PORTAL_BLOCK_ENTITY, arg2, arg3);
    }

    public void setOtherEnd(ItemRelocator.TeleportData otherEnd) {
        this.otherEnd = otherEnd;
    }

    public void setInsidePortal(boolean insidePortal) {
        this.insidePortal = insidePortal;
    }

    public void addEntityToTeleport(LivingEntity entity) {
        this.entitesToTeleport.add(entity);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof BlockEntityPlasmaPortal) {
            BlockEntityPlasmaPortal portal = (BlockEntityPlasmaPortal)blockEntity;
            portal.onTick(level, pos, state);
        }
    }

    public void onTick(Level level, BlockPos pos, BlockState state) {
        ++this.ticker;
        if (!this.entitesToTeleport.isEmpty() && this.otherEnd != null) {
            this.entitesToTeleport.forEach(e -> this.teleportEntity((LivingEntity)e, this.otherEnd.toTeleportTarget(), e.m_6350_()));
            this.entitesToTeleport.clear();
        }
        if (this.ticker >= 500) {
            level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
        }
    }

    public void teleportEntity(LivingEntity player, TeleporterTarget target, Direction dir) {
        int weight = TeleportUtil.getWeightOfEntity((Entity)player, (boolean)true);
        if (weight != 0) {
            ServerLevel server = target.getWorld();
            BlockPos pos = target.getTargetPosition();
            TeleportUtil.teleportEntity((Entity)player, (ServerLevel)server, (BlockPos)pos, (Direction)dir);
        }
    }
}

