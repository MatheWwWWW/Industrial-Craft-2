/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  org.apache.logging.log4j.Level
 */
package ic2.core.block.machine.tileentity;

import ic2.core.IC2;
import ic2.core.block.EntityIC2Explosive;
import ic2.core.block.EntityNuke;
import ic2.core.block.machine.tileentity.Explosive;
import ic2.core.block.machine.tileentity.TileEntityNuke;
import ic2.core.init.MainConfig;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.apache.logging.log4j.Level;

@TeBlock.Delegated(current=TileEntityNuke.class, old=TileEntityClassicNuke.class)
public abstract class TileEntityBridgeNuke
extends Explosive {
    @Override
    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        super.onPlaced(stack, placer, facing);
        if (placer instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)placer;
            String playerName = player.func_146103_bH().getName() + "/" + player.func_146103_bH().getId();
            IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Player %s placed a nuke at %s.", playerName, Util.formatPosition(this));
        }
    }

    public abstract float getNukeExplosivePower();

    public abstract int getRadiationRange();

    @Override
    protected EntityIC2Explosive getEntity(EntityLivingBase igniter) {
        if (!ConfigUtil.getBool(MainConfig.get(), "protection/enableNuke")) {
            return null;
        }
        float power = this.getNukeExplosivePower();
        if (power < 0.0f) {
            return null;
        }
        int radiationRange = this.getRadiationRange();
        return new EntityNuke(this.func_145831_w(), (double)this.field_174879_c.func_177958_n() + 0.5, (double)this.field_174879_c.func_177956_o() + 0.5, (double)this.field_174879_c.func_177952_p() + 0.5, power, radiationRange);
    }

    @Override
    protected void onIgnite(EntityLivingBase igniter) {
        String cause = igniter == null ? "indirectly" : "by " + igniter.getClass().getSimpleName() + " " + igniter.func_70005_c_();
        IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Nuke at %s was ignited %s.", Util.formatPosition(this), cause);
    }

    public static class TileEntityClassicNuke
    extends TileEntityBridgeNuke {
        private static final float POWER = 35.0f;

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

