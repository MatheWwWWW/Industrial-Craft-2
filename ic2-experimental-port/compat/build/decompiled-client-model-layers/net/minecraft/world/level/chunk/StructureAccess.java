/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public interface StructureAccess {
    @Nullable
    public StructureStart m_213652_(Structure var1);

    public void m_213792_(Structure var1, StructureStart var2);

    public LongSet m_213649_(Structure var1);

    public void m_213843_(Structure var1, long var2);

    public Map<Structure, LongSet> m_62769_();

    public void m_62737_(Map<Structure, LongSet> var1);
}

