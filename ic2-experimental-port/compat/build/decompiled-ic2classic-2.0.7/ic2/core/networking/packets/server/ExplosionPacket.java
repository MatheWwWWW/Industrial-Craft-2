/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.networking.packets.server;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExplosionPacket
extends IC2Packet {
    List<ParticleSpawn> spawns = CollectionUtils.createList();
    Int2ObjectMap<Vec3> motions = new Int2ObjectLinkedOpenHashMap();
    Vec3 origin;

    public ExplosionPacket() {
    }

    public ExplosionPacket(List<ParticleSpawn> spawns, Int2ObjectMap<Vec3> motions, Vec3 origin) {
        this.spawns = spawns;
        this.origin = origin;
        this.motions = motions;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        int i;
        buffer.writeDouble(this.origin.f_82479_);
        buffer.writeDouble(this.origin.f_82480_);
        buffer.writeDouble(this.origin.f_82481_);
        int max = Math.min(this.spawns.size(), Short.MAX_VALUE);
        buffer.writeShort(max);
        for (i = 0; i < max; ++i) {
            this.spawns.get(i).send(buffer);
        }
        max = Math.min(this.motions.size(), Short.MAX_VALUE);
        buffer.writeShort(max);
        i = 0;
        for (Int2ObjectMap.Entry entry : this.motions.int2ObjectEntrySet()) {
            if (i >= max) break;
            buffer.m_130130_(entry.getIntKey());
            Vec3 vec = (Vec3)entry.getValue();
            buffer.writeDouble(vec.f_82479_);
            buffer.writeDouble(vec.f_82480_);
            buffer.writeDouble(vec.f_82481_);
            ++i;
        }
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        int i;
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        this.origin = new Vec3(x, y, z);
        int count = buffer.readShort();
        for (i = 0; i < count; ++i) {
            this.spawns.add(new ParticleSpawn(buffer));
        }
        count = buffer.readShort();
        for (i = 0; i < count; ++i) {
            int id = buffer.m_130242_();
            double mX = buffer.readDouble();
            double mY = buffer.readDouble();
            double mZ = buffer.readDouble();
            this.motions.put(id, (Object)new Vec3(mX, mY, mZ));
        }
    }

    @Override
    public void handlePacket(Player source) {
        Level world = source.f_19853_;
        int m = this.spawns.size();
        for (int i = 0; i < m; ++i) {
            ParticleSpawn spawn = this.spawns.get(i);
            BlockPos pos = spawn.getPos();
            float power = spawn.power;
            double x = (float)pos.m_123341_() + world.f_46441_.m_188501_();
            double y = (float)pos.m_123342_() + world.f_46441_.m_188501_();
            double z = (float)pos.m_123343_() + world.f_46441_.m_188501_();
            double offsetX = x - this.origin.f_82479_;
            double offsetY = y - this.origin.f_82480_;
            double offsetZ = z - this.origin.f_82481_;
            double distance = Math.sqrt(offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ);
            offsetX /= distance;
            offsetY /= distance;
            offsetZ /= distance;
            double randomOffset = 0.5 / (distance / (double)power + 0.1);
            world.m_7106_((ParticleOptions)ParticleTypes.f_123759_, (x + this.origin.f_82479_) / 2.0, (y + this.origin.f_82480_) / 2.0, (z + this.origin.f_82481_) / 2.0, offsetX *= (randomOffset *= (double)(world.f_46441_.m_188501_() * world.f_46441_.m_188501_() + 0.3f)), offsetY *= randomOffset, offsetZ *= randomOffset);
            world.m_7106_((ParticleOptions)ParticleTypes.f_123762_, x, y, z, offsetX, offsetY, offsetZ);
        }
        world.m_7106_((ParticleOptions)ParticleTypes.f_123813_, this.origin.f_82479_, this.origin.f_82480_, this.origin.f_82481_, 1.0, 0.0, 0.0);
        for (Int2ObjectMap.Entry entry : this.motions.int2ObjectEntrySet()) {
            Entity entity = world.m_6815_(entry.getIntKey());
            if (entity == null) continue;
            entity.m_20256_(((Vec3)entry.getValue()).m_82542_(0.01, 0.01, 0.01));
        }
    }

    public static class ParticleSpawn {
        long position;
        float power;

        public ParticleSpawn(long position, float power) {
            this.position = position;
            this.power = power;
        }

        public ParticleSpawn(FriendlyByteBuf buffer) {
            this.position = buffer.readLong();
            this.power = buffer.readFloat();
        }

        public BlockPos getPos() {
            return BlockPos.m_122022_((long)this.position);
        }

        public void send(FriendlyByteBuf buffer) {
            buffer.writeLong(this.position);
            buffer.writeFloat(this.power);
        }
    }
}

