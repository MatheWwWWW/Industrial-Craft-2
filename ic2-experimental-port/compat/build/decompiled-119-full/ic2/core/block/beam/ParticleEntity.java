/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.beam;

import ic2.core.block.beam.TileEntityEmitter;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;

public class ParticleEntity
extends Entity {
    private static final double initialVelocity = 0.5;
    private static final double slowdown = 0.99;

    public ParticleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.f_19794_ = true;
    }

    public ParticleEntity(TileEntityEmitter tileEntityEmitter) {
        this(null, tileEntityEmitter.m_58904_());
        Direction direction = tileEntityEmitter.getFacing();
        double d = (double)tileEntityEmitter.m_58899_().m_123341_() + 0.5 + (double)direction.m_122429_() * 0.5;
        double d2 = (double)tileEntityEmitter.m_58899_().m_123342_() + 0.5 + (double)direction.m_122430_() * 0.5;
        double d3 = (double)tileEntityEmitter.m_58899_().m_123343_() + 0.5 + (double)direction.m_122431_() * 0.5;
        this.m_20248_(d, d2, d3);
        this.m_20334_((double)direction.m_122429_() * 0.5, (double)direction.m_122430_() * 0.5, (double)direction.m_122431_() * 0.5);
    }

    protected void m_8097_() {
    }

    protected void m_7378_(CompoundTag compoundTag) {
    }

    protected void m_7380_(CompoundTag compoundTag) {
    }

    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket((Entity)this);
    }

    public void m_8119_() {
        this.m_6478_(MoverType.SELF, this.m_20184_());
        this.m_20256_(this.m_20184_().m_82490_(0.99));
        if (this.m_20184_().m_82556_() < 1.0E-4) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
        }
    }
}

