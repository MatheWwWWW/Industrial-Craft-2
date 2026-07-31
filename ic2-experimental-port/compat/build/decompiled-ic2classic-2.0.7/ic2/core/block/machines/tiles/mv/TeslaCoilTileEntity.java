/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.common.util.FakePlayerFactory
 */
package ic2.core.block.machines.tiles.mv;

import com.mojang.math.Vector3f;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.IC2DamageSource;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.energy.EnergyNetLocal;
import ic2.core.platform.registries.IC2Tiles;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayerFactory;

public class TeslaCoilTileEntity
extends BaseElectricTileEntity
implements ITickListener {
    @NetworkInfo
    public HitBuffer buffer = new HitBuffer();

    public TeslaCoilTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0, 128, 50000);
    }

    @Override
    public boolean allowsUI() {
        return false;
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TESLA_COIL;
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        this.handleComparators();
        if (this.isRedstonePowered()) {
            return;
        }
        if (this.clock(32)) {
            int damage = this.energy / this.getCost();
            if (damage <= 0) {
                return;
            }
            this.useEnergy(1);
            if (this.shock(damage)) {
                this.useEnergy(damage * this.getCost());
            }
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        if (fields.contains("buffer")) {
            this.buffer.applyEffects(this.m_58904_());
        }
        super.onNetworkFieldChanged(fields, player);
    }

    public boolean shock(int damage) {
        this.buffer.clear();
        this.buffer.setDamage(damage);
        IC2DamageSource source = IC2DamageSource.newShockDamage((Entity)FakePlayerFactory.getMinecraft((ServerLevel)((ServerLevel)this.m_58904_())));
        List list = this.f_58857_.m_6443_(LivingEntity.class, new AABB(this.m_58899_()).m_82400_(4.0), EnergyNetLocal.SHOCK_PREDICATE);
        for (int l = 0; l < list.size(); ++l) {
            LivingEntity victim = (LivingEntity)list.get(l);
            victim.m_6469_((DamageSource)source, (float)damage);
            this.buffer.addEntity((Entity)victim);
        }
        if (list.size() > 0) {
            this.updateTileField("buffer");
            return true;
        }
        return false;
    }

    public int getCost() {
        return 400;
    }

    public static class HitBuffer
    implements INetworkDataBuffer {
        int damage;
        IntList entities = new IntArrayList();

        public void clear() {
            this.damage = -1;
            this.entities.clear();
        }

        public void setDamage(int damage) {
            this.damage = damage;
        }

        public void addEntity(Entity entity) {
            if (entity == null) {
                return;
            }
            this.entities.add(entity.m_19879_());
        }

        public void applyEffects(Level world) {
            if (this.damage == -1 || this.entities.size() == 0) {
                return;
            }
            int m = this.entities.size();
            for (int i = 0; i < m; ++i) {
                Entity entity = world.m_6815_(this.entities.getInt(i));
                if (!(entity instanceof LivingEntity)) continue;
                for (int x = 0; x < this.damage; ++x) {
                    world.m_7106_((ParticleOptions)new DustParticleOptions(Vector3f.f_122227_, 1.0f), entity.m_20185_() - 0.5 + (double)world.f_46441_.m_188501_(), entity.m_20186_() + (double)(world.f_46441_.m_188501_() * 2.0f), entity.m_20189_() - 0.5 + (double)world.f_46441_.m_188501_(), 0.0, 0.0, 1.0);
                }
            }
        }

        @Override
        public void write(IOutputBuffer buffer) {
            if (this.damage == -1) {
                buffer.writeBoolean(true);
                return;
            }
            buffer.writeBoolean(false);
            buffer.writeInt(this.damage);
            buffer.writeInt(this.entities.size());
            int m = this.entities.size();
            for (int i = 0; i < m; ++i) {
                buffer.writeInt(this.entities.getInt(i));
            }
        }

        @Override
        public void read(IInputBuffer buffer) {
            this.clear();
            if (buffer.readBoolean()) {
                return;
            }
            this.damage = buffer.readInt();
            int size = buffer.readInt();
            for (int i = 0; i < size; ++i) {
                this.entities.add(buffer.readInt());
            }
        }
    }
}

