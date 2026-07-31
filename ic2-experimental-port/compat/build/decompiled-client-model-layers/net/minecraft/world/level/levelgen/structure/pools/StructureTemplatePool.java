/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class StructureTemplatePool {
    private static final Logger f_210556_ = LogUtils.getLogger();
    private static final int f_210557_ = Integer.MIN_VALUE;
    public static final Codec<StructureTemplatePool> f_210554_ = RecordCodecBuilder.create(p_210575_ -> p_210575_.group((App)ResourceLocation.f_135803_.fieldOf("name").forGetter(StructureTemplatePool::m_210587_), (App)ResourceLocation.f_135803_.fieldOf("fallback").forGetter(StructureTemplatePool::m_210573_), (App)Codec.mapPair((MapCodec)StructurePoolElement.f_210468_.fieldOf("element"), (MapCodec)Codec.intRange((int)1, (int)150).fieldOf("weight")).codec().listOf().fieldOf("elements").forGetter(p_210579_ -> p_210579_.f_210559_)).apply((Applicative)p_210575_, StructureTemplatePool::new));
    public static final Codec<Holder<StructureTemplatePool>> f_210555_ = RegistryFileCodec.m_135589_(Registry.f_122884_, f_210554_);
    private final ResourceLocation f_210558_;
    private final List<Pair<StructurePoolElement, Integer>> f_210559_;
    private final ObjectArrayList<StructurePoolElement> f_210560_;
    private final ResourceLocation f_210561_;
    private int f_210562_ = Integer.MIN_VALUE;

    public StructureTemplatePool(ResourceLocation p_210565_, ResourceLocation p_210566_, List<Pair<StructurePoolElement, Integer>> p_210567_) {
        this.f_210558_ = p_210565_;
        this.f_210559_ = p_210567_;
        this.f_210560_ = new ObjectArrayList();
        for (Pair<StructurePoolElement, Integer> $$3 : p_210567_) {
            StructurePoolElement $$4 = (StructurePoolElement)$$3.getFirst();
            for (int $$5 = 0; $$5 < (Integer)$$3.getSecond(); ++$$5) {
                this.f_210560_.add((Object)$$4);
            }
        }
        this.f_210561_ = p_210566_;
    }

    public StructureTemplatePool(ResourceLocation p_210569_, ResourceLocation p_210570_, List<Pair<Function<Projection, ? extends StructurePoolElement>, Integer>> p_210571_, Projection p_210572_) {
        this.f_210558_ = p_210569_;
        this.f_210559_ = Lists.newArrayList();
        this.f_210560_ = new ObjectArrayList();
        for (Pair<Function<Projection, ? extends StructurePoolElement>, Integer> $$4 : p_210571_) {
            StructurePoolElement $$5 = (StructurePoolElement)((Function)$$4.getFirst()).apply(p_210572_);
            this.f_210559_.add((Pair<StructurePoolElement, Integer>)Pair.of((Object)$$5, (Object)((Integer)$$4.getSecond())));
            for (int $$6 = 0; $$6 < (Integer)$$4.getSecond(); ++$$6) {
                this.f_210560_.add((Object)$$5);
            }
        }
        this.f_210561_ = p_210570_;
    }

    public int m_227357_(StructureTemplateManager p_227358_) {
        if (this.f_210562_ == Integer.MIN_VALUE) {
            this.f_210562_ = this.f_210560_.stream().filter(p_210577_ -> p_210577_ != EmptyPoolElement.f_210175_).mapToInt(p_227361_ -> p_227361_.m_214015_(p_227358_, BlockPos.f_121853_, Rotation.NONE).m_71057_()).max().orElse(0);
        }
        return this.f_210562_;
    }

    public ResourceLocation m_210573_() {
        return this.f_210561_;
    }

    public StructurePoolElement m_227355_(RandomSource p_227356_) {
        return (StructurePoolElement)this.f_210560_.get(p_227356_.m_188503_(this.f_210560_.size()));
    }

    public List<StructurePoolElement> m_227362_(RandomSource p_227363_) {
        return Util.m_214611_(this.f_210560_, p_227363_);
    }

    public ResourceLocation m_210587_() {
        return this.f_210558_;
    }

    public int m_210590_() {
        return this.f_210560_.size();
    }

    public static final class Projection
    extends Enum<Projection>
    implements StringRepresentable {
        public static final /* enum */ Projection TERRAIN_MATCHING = new Projection("terrain_matching", (ImmutableList<StructureProcessor>)ImmutableList.of((Object)new GravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1)));
        public static final /* enum */ Projection RIGID = new Projection("rigid", (ImmutableList<StructureProcessor>)ImmutableList.of());
        public static final StringRepresentable.EnumCodec<Projection> f_210593_;
        private final String f_210595_;
        private final ImmutableList<StructureProcessor> f_210596_;
        private static final /* synthetic */ Projection[] $VALUES;

        public static Projection[] values() {
            return (Projection[])$VALUES.clone();
        }

        public static Projection valueOf(String p_210613_) {
            return Enum.valueOf(Projection.class, p_210613_);
        }

        private Projection(String p_210602_, ImmutableList<StructureProcessor> p_210603_) {
            this.f_210595_ = p_210602_;
            this.f_210596_ = p_210603_;
        }

        public String m_210604_() {
            return this.f_210595_;
        }

        public static Projection m_210607_(String p_210608_) {
            return f_210593_.m_216455_(p_210608_);
        }

        public ImmutableList<StructureProcessor> m_210609_() {
            return this.f_210596_;
        }

        @Override
        public String m_7912_() {
            return this.f_210595_;
        }

        private static /* synthetic */ Projection[] m_210611_() {
            return new Projection[]{TERRAIN_MATCHING, RIGID};
        }

        static {
            $VALUES = Projection.m_210611_();
            f_210593_ = StringRepresentable.m_216439_(Projection::values);
        }
    }
}

