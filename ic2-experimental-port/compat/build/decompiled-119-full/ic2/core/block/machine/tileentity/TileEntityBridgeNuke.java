/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  org.apache.logging.log4j.Level
 */
package ic2.core.block.machine.tileentity;

import ic2.api.entity.block.ExplosiveEntity;
import ic2.core.IC2;
import ic2.core.block.machine.tileentity.TileEntityExplosive;
import ic2.core.entity.block.NukeEntity;
import ic2.core.init.MainConfig;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.Level;

public abstract class TileEntityBridgeNuke
extends TileEntityExplosive {
    protected TileEntityBridgeNuke(BlockEntityType<? extends TileEntityBridgeNuke> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityExplosive>)blockEntityType, blockPos, blockState);
    }

    @Override
    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
        super.onPlaced(itemStack, livingEntity, direction);
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            String string = player.m_36316_().getName() + "/" + player.m_36316_().getId();
            IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Player %s placed a nuke at %s.", string, Util.formatPosition(this));
        }
    }

    public abstract float getNukeExplosivePower();

    public abstract int getRadiationRange();

    @Override
    protected ExplosiveEntity getEntity(LivingEntity livingEntity) {
        if (!ConfigUtil.getBool(MainConfig.get(), "protection/enableNuke")) {
            return null;
        }
        float f = this.getNukeExplosivePower();
        if (f < 0.0f) {
            return null;
        }
        int n = this.getRadiationRange();
        return new NukeEntity(this.m_58904_(), (double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5, f, n);
    }

    @Override
    protected void onIgnite(LivingEntity livingEntity) {
        String string = livingEntity == null ? "indirectly" : "by " + livingEntity.getClass().getSimpleName() + " " + livingEntity.m_7755_();
        IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Nuke at %s was ignited %s.", Util.formatPosition(this), string);
    }

    public static class TileEntityClassicNuke
    extends TileEntityBridgeNuke {
        private static final float POWER = 35.0f;

        public TileEntityClassicNuke(BlockPos blockPos, BlockState blockState) {
            super((BlockEntityType<? extends TileEntityBridgeNuke>)Ic2BlockEntities.CLASSIC_NUKE, blockPos, blockState);
        }

        @Override
        public float getNukeExplosivePower() {
            return Math.min(35.0f, ConfigUtil.getFloat(MainConfig.get(), "protection/nukeExplosionPowerLimit"));
        }

        @Override
        public int getRadiationRange() {
            return 1;
        }
    }
}

