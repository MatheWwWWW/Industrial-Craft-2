/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P1
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

public abstract class StateTestingPredicate
implements BlockPredicate {
    protected final Vec3i f_190539_;

    protected static <P extends StateTestingPredicate> Products.P1<RecordCodecBuilder.Mu<P>, Vec3i> m_190546_(RecordCodecBuilder.Instance<P> p_190547_) {
        return p_190547_.group((App)Vec3i.m_194650_(16).optionalFieldOf("offset", (Object)Vec3i.f_123288_).forGetter(p_190549_ -> p_190549_.f_190539_));
    }

    protected StateTestingPredicate(Vec3i p_190541_) {
        this.f_190539_ = p_190541_;
    }

    @Override
    public final boolean test(WorldGenLevel p_190543_, BlockPos p_190544_) {
        return this.m_183454_(p_190543_.m_8055_(p_190544_.m_121955_(this.f_190539_)));
    }

    protected abstract boolean m_183454_(BlockState var1);

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

