/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.storage.tiles.pad;

import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MVChargepadTileEntity
extends BaseChargePadTileEntity {
    public MVChargepadTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, new BaseChargePadTileEntity.ChargePadData(600000, 2, 2, 2, 0.5f, 2.5f));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MV_CHARGEPAD;
    }

    @Override
    public float getEffectHeight() {
        return 0.5f;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected int getMaxParticleAge() {
        return 5;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected float[] getParticleColour(RandomSource rand) {
        float red = (this.installedUpgrades & 1 << BaseChargePadTileEntity.PadUpgrade.DRAIN.getIndex()) != 0 || (this.installedUpgrades & 1 << BaseChargePadTileEntity.PadUpgrade.DAMAGE.getIndex()) != 0 ? 1.0f : 0.0f;
        float green = (this.installedUpgrades & 1 << BaseChargePadTileEntity.PadUpgrade.DAMAGE.getIndex()) != 0 ? 0.0f : 0.6f;
        return new float[]{red, green + rand.m_188501_() * 0.4f, 0.0f};
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected double[] getParticleVelocity(RandomSource rand) {
        return new double[]{0.0, 3.0, 0.0};
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    protected int getParticleAmount(RandomSource rand) {
        return 6;
    }
}

