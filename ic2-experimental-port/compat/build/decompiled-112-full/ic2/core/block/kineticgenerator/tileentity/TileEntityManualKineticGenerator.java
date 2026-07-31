/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.core.block.TileEntityBlock;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

@NotClassic
public class TileEntityManualKineticGenerator
extends TileEntityBlock
implements IKineticSource {
    public int clicks;
    public static final int maxClicksPerTick = 10;
    public final int maxKU = 1000;
    public int currentKU;
    private static final float outputModifier = Math.round(ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/manual"));

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.clicks = 0;
    }

    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        this.playerClicked(player);
        return true;
    }

    private void playerClicked(EntityPlayer player) {
        if (player.func_71024_bL().func_75116_a() <= 6) {
            return;
        }
        if (!(player instanceof EntityPlayerMP)) {
            return;
        }
        if (this.clicks >= 10) {
            return;
        }
        int ku = !Util.isFakePlayer(player, false) ? 400 : 20;
        ku = (int)((float)ku * outputModifier);
        this.currentKU = Math.min(this.currentKU + ku, 1000);
        player.func_71020_j(0.25f);
        ++this.clicks;
    }

    @Override
    public int maxrequestkineticenergyTick(EnumFacing directionFrom) {
        return this.drawKineticEnergy(directionFrom, Integer.MAX_VALUE, true);
    }

    @Override
    public int getConnectionBandwidth(EnumFacing side) {
        return 1000;
    }

    @Override
    public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy) {
        return this.drawKineticEnergy(directionFrom, requestkineticenergy, false);
    }

    @Override
    public int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
        int max = Math.min(this.currentKU, request);
        if (!simulate) {
            this.currentKU -= max;
        }
        return max;
    }
}

