/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.machine.tileentity;

import com.mojang.math.Vector3f;
import ic2.core.IC2;
import ic2.core.Ic2DamageSource;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.ref.Ic2BlockEntities;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityTesla
extends Ic2TileEntity {
    private static final DustParticleOptions effect = new DustParticleOptions(new Vector3f(0.1f, 0.1f, 1.0f), 1.0f);
    protected final Redstone redstone;
    protected final Energy energy;
    private int ticker = IC2.random.m_188503_(32);

    public TileEntityTesla(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.TESLA_COIL, blockPos, blockState);
        this.redstone = this.addComponent(new Redstone(this));
        this.energy = this.addComponent(Energy.asBasicSink(this, 10000.0, 2));
    }

    @Override
    protected void updateEntityServer() {
        int n;
        super.updateEntityServer();
        if (!this.redstone.hasRedstoneInput()) {
            return;
        }
        if (this.energy.useEnergy(1.0) && ++this.ticker % 32 == 0 && (n = (int)this.energy.getEnergy() / 400) > 0 && this.shock(n)) {
            System.out.println(n);
            this.energy.useEnergy(n * 400);
        }
    }

    protected boolean shock(int n) {
        Level level = this.m_58904_();
        if (level == null) {
            return false;
        }
        List list = level.m_6443_(LivingEntity.class, new AABB((double)(this.f_58858_.m_123341_() - 4), (double)(this.f_58858_.m_123342_() - 4), (double)(this.f_58858_.m_123343_() - 4), (double)(this.f_58858_.m_123341_() + 4 + 1), (double)(this.f_58858_.m_123342_() + 4 + 1), (double)(this.f_58858_.m_123343_() + 4 + 1)), EntitySelector.f_20406_);
        if (list.size() == 0) {
            return false;
        }
        boolean bl = false;
        int n2 = n / list.size();
        for (LivingEntity livingEntity : list) {
            if (ItemArmorHazmat.hasCompleteHazmat(livingEntity) || !livingEntity.m_6469_((DamageSource)Ic2DamageSource.electricity, (float)n2)) continue;
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                RandomSource randomSource = level.f_46441_;
                System.out.println(livingEntity);
                for (int i = 0; i < n2; ++i) {
                    serverLevel.m_7106_((ParticleOptions)effect, livingEntity.m_20185_() + (double)randomSource.m_188501_() - 0.5, livingEntity.m_20186_() + (double)(randomSource.m_188501_() * 2.0f) - 1.0, livingEntity.m_20189_() + (double)randomSource.m_188501_() - 0.5, 0.0, 0.0, 0.0);
                }
            }
            bl = true;
        }
        return bl;
    }
}

