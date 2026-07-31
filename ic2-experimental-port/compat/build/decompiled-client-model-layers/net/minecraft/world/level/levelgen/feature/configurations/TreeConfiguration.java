/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

public class TreeConfiguration
implements FeatureConfiguration {
    public static final Codec<TreeConfiguration> f_68184_ = RecordCodecBuilder.create(p_225468_ -> p_225468_.group((App)BlockStateProvider.f_68747_.fieldOf("trunk_provider").forGetter(p_161248_ -> p_161248_.f_68185_), (App)TrunkPlacer.f_70262_.fieldOf("trunk_placer").forGetter(p_161246_ -> p_161246_.f_68190_), (App)BlockStateProvider.f_68747_.fieldOf("foliage_provider").forGetter(p_161244_ -> p_161244_.f_161213_), (App)FoliagePlacer.f_68519_.fieldOf("foliage_placer").forGetter(p_191357_ -> p_191357_.f_68189_), (App)RootPlacer.f_225859_.optionalFieldOf("root_placer").forGetter(p_225478_ -> p_225478_.f_225455_), (App)BlockStateProvider.f_68747_.fieldOf("dirt_provider").forGetter(p_225476_ -> p_225476_.f_161212_), (App)FeatureSize.f_68281_.fieldOf("minimum_size").forGetter(p_225474_ -> p_225474_.f_68191_), (App)TreeDecorator.f_70021_.listOf().fieldOf("decorators").forGetter(p_225472_ -> p_225472_.f_68187_), (App)Codec.BOOL.fieldOf("ignore_vines").orElse((Object)false).forGetter(p_161232_ -> p_161232_.f_68193_), (App)Codec.BOOL.fieldOf("force_dirt").orElse((Object)false).forGetter(p_225470_ -> p_225470_.f_161215_)).apply((Applicative)p_225468_, TreeConfiguration::new));
    public final BlockStateProvider f_68185_;
    public final BlockStateProvider f_161212_;
    public final TrunkPlacer f_68190_;
    public final BlockStateProvider f_161213_;
    public final FoliagePlacer f_68189_;
    public final Optional<RootPlacer> f_225455_;
    public final FeatureSize f_68191_;
    public final List<TreeDecorator> f_68187_;
    public final boolean f_68193_;
    public final boolean f_161215_;

    protected TreeConfiguration(BlockStateProvider p_225457_, TrunkPlacer p_225458_, BlockStateProvider p_225459_, FoliagePlacer p_225460_, Optional<RootPlacer> p_225461_, BlockStateProvider p_225462_, FeatureSize p_225463_, List<TreeDecorator> p_225464_, boolean p_225465_, boolean p_225466_) {
        this.f_68185_ = p_225457_;
        this.f_68190_ = p_225458_;
        this.f_161213_ = p_225459_;
        this.f_68189_ = p_225460_;
        this.f_225455_ = p_225461_;
        this.f_161212_ = p_225462_;
        this.f_68191_ = p_225463_;
        this.f_68187_ = p_225464_;
        this.f_68193_ = p_225465_;
        this.f_161215_ = p_225466_;
    }

    public static class TreeConfigurationBuilder {
        public final BlockStateProvider f_68229_;
        private final TrunkPlacer f_68232_;
        public final BlockStateProvider f_161249_;
        private final FoliagePlacer f_68231_;
        private final Optional<RootPlacer> f_225479_;
        private BlockStateProvider f_161251_;
        private final FeatureSize f_68233_;
        private List<TreeDecorator> f_68234_ = ImmutableList.of();
        private boolean f_68236_;
        private boolean f_161252_;

        public TreeConfigurationBuilder(BlockStateProvider p_225481_, TrunkPlacer p_225482_, BlockStateProvider p_225483_, FoliagePlacer p_225484_, Optional<RootPlacer> p_225485_, FeatureSize p_225486_) {
            this.f_68229_ = p_225481_;
            this.f_68232_ = p_225482_;
            this.f_161249_ = p_225483_;
            this.f_161251_ = BlockStateProvider.m_191382_(Blocks.f_50493_);
            this.f_68231_ = p_225484_;
            this.f_225479_ = p_225485_;
            this.f_68233_ = p_225486_;
        }

        public TreeConfigurationBuilder(BlockStateProvider p_191359_, TrunkPlacer p_191360_, BlockStateProvider p_191361_, FoliagePlacer p_191362_, FeatureSize p_191363_) {
            this(p_191359_, p_191360_, p_191361_, p_191362_, Optional.empty(), p_191363_);
        }

        public TreeConfigurationBuilder m_161260_(BlockStateProvider p_161261_) {
            this.f_161251_ = p_161261_;
            return this;
        }

        public TreeConfigurationBuilder m_68249_(List<TreeDecorator> p_68250_) {
            this.f_68234_ = p_68250_;
            return this;
        }

        public TreeConfigurationBuilder m_68244_() {
            this.f_68236_ = true;
            return this;
        }

        public TreeConfigurationBuilder m_161262_() {
            this.f_161252_ = true;
            return this;
        }

        public TreeConfiguration m_68251_() {
            return new TreeConfiguration(this.f_68229_, this.f_68232_, this.f_161249_, this.f_68231_, this.f_225479_, this.f_161251_, this.f_68233_, this.f_68234_, this.f_68236_, this.f_161252_);
        }
    }
}

