/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@NotClassic
public class TileEntityManualKineticGenerator
extends Ic2TileEntity
implements IKineticSource {
    public int clicks;
    public static final int maxClicksPerTick = 10;
    public final int maxKU = 1000;
    public int currentKU;
    private static final float outputModifier = Math.round(ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/manual"));

    public TileEntityManualKineticGenerator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.MANUAL_KINETIC_GENERATOR, blockPos, blockState);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.clicks = 0;
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        this.playerClicked(player);
        return InteractionResult.SUCCESS;
    }

    private void playerClicked(Player player) {
        if (player.m_36324_().m_38702_() <= 6) {
            return;
        }
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        if (this.clicks >= 10) {
            return;
        }
        int n = !Util.isFakePlayer(player, false) ? 400 : 20;
        n = (int)((float)n * outputModifier);
        this.currentKU = Math.min(this.currentKU + n, 1000);
        player.m_36399_(0.25f);
        ++this.clicks;
    }

    @Override
    public int maxrequestkineticenergyTick(Direction direction) {
        return this.drawKineticEnergy(direction, Integer.MAX_VALUE, true);
    }

    @Override
    public int getConnectionBandwidth(Direction direction) {
        return 1000;
    }

    @Override
    public int requestkineticenergy(Direction direction, int n) {
        return this.drawKineticEnergy(direction, n, false);
    }

    @Override
    public int drawKineticEnergy(Direction direction, int n, boolean bl) {
        int n2 = Math.min(this.currentKU, n);
        if (!bl) {
            this.currentKU -= n2;
        }
        return n2;
    }
}

