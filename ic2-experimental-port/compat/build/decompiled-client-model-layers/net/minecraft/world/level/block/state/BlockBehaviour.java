/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.serialization.MapCodec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BlockBehaviour {
    protected static final Direction[] f_60441_ = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP};
    protected final Material f_60442_;
    protected final boolean f_60443_;
    protected final float f_60444_;
    protected final boolean f_60445_;
    protected final SoundType f_60446_;
    protected final float f_60447_;
    protected final float f_60448_;
    protected final float f_60449_;
    protected final boolean f_60438_;
    protected final Properties f_60439_;
    @Nullable
    protected ResourceLocation f_60440_;

    public BlockBehaviour(Properties p_60452_) {
        this.f_60442_ = p_60452_.f_60882_;
        this.f_60443_ = p_60452_.f_60884_;
        this.f_60440_ = p_60452_.f_60894_;
        this.f_60444_ = p_60452_.f_60887_;
        this.f_60445_ = p_60452_.f_60890_;
        this.f_60446_ = p_60452_.f_60885_;
        this.f_60447_ = p_60452_.f_60891_;
        this.f_60448_ = p_60452_.f_60892_;
        this.f_60449_ = p_60452_.f_60893_;
        this.f_60438_ = p_60452_.f_60903_;
        this.f_60439_ = p_60452_;
    }

    @Deprecated
    public void m_7742_(BlockState p_60520_, LevelAccessor p_60521_, BlockPos p_60522_, int p_60523_, int p_60524_) {
    }

    @Deprecated
    public boolean m_7357_(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        switch (p_60478_) {
            case LAND: {
                return !p_60475_.m_60838_(p_60476_, p_60477_);
            }
            case WATER: {
                return p_60476_.m_6425_(p_60477_).m_205070_(FluidTags.f_13131_);
            }
            case AIR: {
                return !p_60475_.m_60838_(p_60476_, p_60477_);
            }
        }
        return false;
    }

    @Deprecated
    public BlockState m_7417_(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        return p_60541_;
    }

    @Deprecated
    public boolean m_6104_(BlockState p_60532_, BlockState p_60533_, Direction p_60534_) {
        return false;
    }

    @Deprecated
    public void m_6861_(BlockState p_60509_, Level p_60510_, BlockPos p_60511_, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        DebugPackets.m_133708_(p_60510_, p_60511_);
    }

    @Deprecated
    public void m_6807_(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
    }

    @Deprecated
    public void m_6810_(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if (p_60515_.m_155947_() && !p_60515_.m_60713_(p_60518_.m_60734_())) {
            p_60516_.m_46747_(p_60517_);
        }
    }

    @Deprecated
    public InteractionResult m_6227_(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        return InteractionResult.PASS;
    }

    @Deprecated
    public boolean m_8133_(BlockState p_60490_, Level p_60491_, BlockPos p_60492_, int p_60493_, int p_60494_) {
        return false;
    }

    @Deprecated
    public RenderShape m_7514_(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Deprecated
    public boolean m_7923_(BlockState p_60576_) {
        return false;
    }

    @Deprecated
    public boolean m_7899_(BlockState p_60571_) {
        return false;
    }

    @Deprecated
    public PushReaction m_5537_(BlockState p_60584_) {
        return this.f_60442_.m_76338_();
    }

    @Deprecated
    public FluidState m_5888_(BlockState p_60577_) {
        return Fluids.f_76191_.m_76145_();
    }

    @Deprecated
    public boolean m_7278_(BlockState p_60457_) {
        return false;
    }

    public float m_142740_() {
        return 0.25f;
    }

    public float m_142627_() {
        return 0.2f;
    }

    @Deprecated
    public BlockState m_6843_(BlockState p_60530_, Rotation p_60531_) {
        return p_60530_;
    }

    @Deprecated
    public BlockState m_6943_(BlockState p_60528_, Mirror p_60529_) {
        return p_60528_;
    }

    @Deprecated
    public boolean m_6864_(BlockState p_60470_, BlockPlaceContext p_60471_) {
        return this.f_60442_.m_76336_() && (p_60471_.m_43722_().m_41619_() || !p_60471_.m_43722_().m_150930_(this.m_5456_()));
    }

    @Deprecated
    public boolean m_5946_(BlockState p_60535_, Fluid p_60536_) {
        return this.f_60442_.m_76336_() || !this.f_60442_.m_76333_();
    }

    @Deprecated
    public List<ItemStack> m_7381_(BlockState p_60537_, LootContext.Builder p_60538_) {
        ResourceLocation $$2 = this.m_60589_();
        if ($$2 == BuiltInLootTables.f_78712_) {
            return Collections.emptyList();
        }
        LootContext $$3 = p_60538_.m_78972_(LootContextParams.f_81461_, p_60537_).m_78975_(LootContextParamSets.f_81421_);
        ServerLevel $$4 = $$3.m_78952_();
        LootTable $$5 = $$4.m_7654_().m_129898_().m_79217_($$2);
        return $$5.m_230922_($$3);
    }

    @Deprecated
    public long m_7799_(BlockState p_60539_, BlockPos p_60540_) {
        return Mth.m_14057_(p_60540_);
    }

    @Deprecated
    public VoxelShape m_7952_(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return p_60578_.m_60808_(p_60579_, p_60580_);
    }

    @Deprecated
    public VoxelShape m_7947_(BlockState p_60581_, BlockGetter p_60582_, BlockPos p_60583_) {
        return this.m_5939_(p_60581_, p_60582_, p_60583_, CollisionContext.m_82749_());
    }

    @Deprecated
    public VoxelShape m_6079_(BlockState p_60547_, BlockGetter p_60548_, BlockPos p_60549_) {
        return Shapes.m_83040_();
    }

    @Deprecated
    public int m_7753_(BlockState p_60585_, BlockGetter p_60586_, BlockPos p_60587_) {
        if (p_60585_.m_60804_(p_60586_, p_60587_)) {
            return p_60586_.m_7469_();
        }
        return p_60585_.m_60631_(p_60586_, p_60587_) ? 0 : 1;
    }

    @Nullable
    @Deprecated
    public MenuProvider m_7246_(BlockState p_60563_, Level p_60564_, BlockPos p_60565_) {
        return null;
    }

    @Deprecated
    public boolean m_7898_(BlockState p_60525_, LevelReader p_60526_, BlockPos p_60527_) {
        return true;
    }

    @Deprecated
    public float m_7749_(BlockState p_60472_, BlockGetter p_60473_, BlockPos p_60474_) {
        return p_60472_.m_60838_(p_60473_, p_60474_) ? 0.2f : 1.0f;
    }

    @Deprecated
    public int m_6782_(BlockState p_60487_, Level p_60488_, BlockPos p_60489_) {
        return 0;
    }

    @Deprecated
    public VoxelShape m_5940_(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.m_83144_();
    }

    @Deprecated
    public VoxelShape m_5939_(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return this.f_60443_ ? p_60572_.m_60808_(p_60573_, p_60574_) : Shapes.m_83040_();
    }

    @Deprecated
    public boolean m_180643_(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return Block.m_49916_(p_181242_.m_60812_(p_181243_, p_181244_));
    }

    @Deprecated
    public boolean m_222958_(BlockState p_222959_, BlockGetter p_222960_, BlockPos p_222961_) {
        return Block.m_49916_(p_222959_.m_60768_(p_222960_, p_222961_));
    }

    @Deprecated
    public VoxelShape m_5909_(BlockState p_60479_, BlockGetter p_60480_, BlockPos p_60481_, CollisionContext p_60482_) {
        return this.m_5939_(p_60479_, p_60480_, p_60481_, p_60482_);
    }

    @Deprecated
    public void m_213898_(BlockState p_222954_, ServerLevel p_222955_, BlockPos p_222956_, RandomSource p_222957_) {
        this.m_213897_(p_222954_, p_222955_, p_222956_, p_222957_);
    }

    @Deprecated
    public void m_213897_(BlockState p_222945_, ServerLevel p_222946_, BlockPos p_222947_, RandomSource p_222948_) {
    }

    @Deprecated
    public float m_5880_(BlockState p_60466_, Player p_60467_, BlockGetter p_60468_, BlockPos p_60469_) {
        float $$4 = p_60466_.m_60800_(p_60468_, p_60469_);
        if ($$4 == -1.0f) {
            return 0.0f;
        }
        int $$5 = p_60467_.m_36298_(p_60466_) ? 30 : 100;
        return p_60467_.m_36281_(p_60466_) / $$4 / (float)$$5;
    }

    @Deprecated
    public void m_213646_(BlockState p_222949_, ServerLevel p_222950_, BlockPos p_222951_, ItemStack p_222952_, boolean p_222953_) {
    }

    @Deprecated
    public void m_6256_(BlockState p_60499_, Level p_60500_, BlockPos p_60501_, Player p_60502_) {
    }

    @Deprecated
    public int m_6378_(BlockState p_60483_, BlockGetter p_60484_, BlockPos p_60485_, Direction p_60486_) {
        return 0;
    }

    @Deprecated
    public void m_7892_(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity p_60498_) {
    }

    @Deprecated
    public int m_6376_(BlockState p_60559_, BlockGetter p_60560_, BlockPos p_60561_, Direction p_60562_) {
        return 0;
    }

    public final ResourceLocation m_60589_() {
        if (this.f_60440_ == null) {
            ResourceLocation $$0 = Registry.f_122824_.m_7981_(this.m_7374_());
            this.f_60440_ = new ResourceLocation($$0.m_135827_(), "blocks/" + $$0.m_135815_());
        }
        return this.f_60440_;
    }

    @Deprecated
    public void m_5581_(Level p_60453_, BlockState p_60454_, BlockHitResult p_60455_, Projectile p_60456_) {
    }

    public abstract Item m_5456_();

    protected abstract Block m_7374_();

    public MaterialColor m_60590_() {
        return this.f_60439_.f_60883_.apply(this.m_7374_().m_49966_());
    }

    public float m_155943_() {
        return this.f_60439_.f_60888_;
    }

    public static class Properties {
        Material f_60882_;
        Function<BlockState, MaterialColor> f_60883_;
        boolean f_60884_ = true;
        SoundType f_60885_ = SoundType.f_56742_;
        ToIntFunction<BlockState> f_60886_ = p_60929_ -> 0;
        float f_60887_;
        float f_60888_;
        boolean f_60889_;
        boolean f_60890_;
        float f_60891_ = 0.6f;
        float f_60892_ = 1.0f;
        float f_60893_ = 1.0f;
        ResourceLocation f_60894_;
        boolean f_60895_ = true;
        boolean f_60896_;
        StateArgumentPredicate<EntityType<?>> f_60897_ = (p_60935_, p_60936_, p_60937_, p_60938_) -> p_60935_.m_60783_(p_60936_, p_60937_, Direction.UP) && p_60935_.m_60791_() < 14;
        StatePredicate f_60898_ = (p_60985_, p_60986_, p_60987_) -> p_60985_.m_60767_().m_76337_() && p_60985_.m_60838_(p_60986_, p_60987_);
        StatePredicate f_60899_;
        StatePredicate f_60900_ = this.f_60899_ = (p_60974_, p_60975_, p_60976_) -> this.f_60882_.m_76334_() && p_60974_.m_60838_(p_60975_, p_60976_);
        StatePredicate f_60901_ = (p_60963_, p_60964_, p_60965_) -> false;
        StatePredicate f_60902_ = (p_60931_, p_60932_, p_60933_) -> false;
        boolean f_60903_;
        Function<BlockState, OffsetType> f_222978_ = p_222985_ -> OffsetType.NONE;

        private Properties(Material p_60905_, MaterialColor p_60906_) {
            this(p_60905_, (BlockState p_222993_) -> p_60906_);
        }

        private Properties(Material p_60908_, Function<BlockState, MaterialColor> p_60909_) {
            this.f_60882_ = p_60908_;
            this.f_60883_ = p_60909_;
        }

        public static Properties m_60939_(Material p_60940_) {
            return Properties.m_60944_(p_60940_, p_60940_.m_76339_());
        }

        public static Properties m_60941_(Material p_60942_, DyeColor p_60943_) {
            return Properties.m_60944_(p_60942_, p_60943_.m_41069_());
        }

        public static Properties m_60944_(Material p_60945_, MaterialColor p_60946_) {
            return new Properties(p_60945_, p_60946_);
        }

        public static Properties m_60947_(Material p_60948_, Function<BlockState, MaterialColor> p_60949_) {
            return new Properties(p_60948_, p_60949_);
        }

        public static Properties m_60926_(BlockBehaviour p_60927_) {
            Properties $$1 = new Properties(p_60927_.f_60442_, p_60927_.f_60439_.f_60883_);
            $$1.f_60882_ = p_60927_.f_60439_.f_60882_;
            $$1.f_60888_ = p_60927_.f_60439_.f_60888_;
            $$1.f_60887_ = p_60927_.f_60439_.f_60887_;
            $$1.f_60884_ = p_60927_.f_60439_.f_60884_;
            $$1.f_60890_ = p_60927_.f_60439_.f_60890_;
            $$1.f_60886_ = p_60927_.f_60439_.f_60886_;
            $$1.f_60883_ = p_60927_.f_60439_.f_60883_;
            $$1.f_60885_ = p_60927_.f_60439_.f_60885_;
            $$1.f_60891_ = p_60927_.f_60439_.f_60891_;
            $$1.f_60892_ = p_60927_.f_60439_.f_60892_;
            $$1.f_60903_ = p_60927_.f_60439_.f_60903_;
            $$1.f_60895_ = p_60927_.f_60439_.f_60895_;
            $$1.f_60896_ = p_60927_.f_60439_.f_60896_;
            $$1.f_60889_ = p_60927_.f_60439_.f_60889_;
            $$1.f_222978_ = p_60927_.f_60439_.f_222978_;
            return $$1;
        }

        public Properties m_60910_() {
            this.f_60884_ = false;
            this.f_60895_ = false;
            return this;
        }

        public Properties m_60955_() {
            this.f_60895_ = false;
            return this;
        }

        public Properties m_60911_(float p_60912_) {
            this.f_60891_ = p_60912_;
            return this;
        }

        public Properties m_60956_(float p_60957_) {
            this.f_60892_ = p_60957_;
            return this;
        }

        public Properties m_60967_(float p_60968_) {
            this.f_60893_ = p_60968_;
            return this;
        }

        public Properties m_60918_(SoundType p_60919_) {
            this.f_60885_ = p_60919_;
            return this;
        }

        public Properties m_60953_(ToIntFunction<BlockState> p_60954_) {
            this.f_60886_ = p_60954_;
            return this;
        }

        public Properties m_60913_(float p_60914_, float p_60915_) {
            return this.m_155954_(p_60914_).m_155956_(p_60915_);
        }

        public Properties m_60966_() {
            return this.m_60978_(0.0f);
        }

        public Properties m_60978_(float p_60979_) {
            this.m_60913_(p_60979_, p_60979_);
            return this;
        }

        public Properties m_60977_() {
            this.f_60890_ = true;
            return this;
        }

        public Properties m_60988_() {
            this.f_60903_ = true;
            return this;
        }

        public Properties m_222994_() {
            this.f_60894_ = BuiltInLootTables.f_78712_;
            return this;
        }

        public Properties m_60916_(Block p_60917_) {
            this.f_60894_ = p_60917_.m_60589_();
            return this;
        }

        public Properties m_60996_() {
            this.f_60896_ = true;
            return this;
        }

        public Properties m_60922_(StateArgumentPredicate<EntityType<?>> p_60923_) {
            this.f_60897_ = p_60923_;
            return this;
        }

        public Properties m_60924_(StatePredicate p_60925_) {
            this.f_60898_ = p_60925_;
            return this;
        }

        public Properties m_60960_(StatePredicate p_60961_) {
            this.f_60899_ = p_60961_;
            return this;
        }

        public Properties m_60971_(StatePredicate p_60972_) {
            this.f_60900_ = p_60972_;
            return this;
        }

        public Properties m_60982_(StatePredicate p_60983_) {
            this.f_60901_ = p_60983_;
            return this;
        }

        public Properties m_60991_(StatePredicate p_60992_) {
            this.f_60902_ = p_60992_;
            return this;
        }

        public Properties m_60999_() {
            this.f_60889_ = true;
            return this;
        }

        public Properties m_155949_(MaterialColor p_155950_) {
            this.f_60883_ = p_222988_ -> p_155950_;
            return this;
        }

        public Properties m_155954_(float p_155955_) {
            this.f_60888_ = p_155955_;
            return this;
        }

        public Properties m_155956_(float p_155957_) {
            this.f_60887_ = Math.max(0.0f, p_155957_);
            return this;
        }

        public Properties m_222979_(OffsetType p_222980_) {
            return this.m_222989_(p_222983_ -> p_222980_);
        }

        public Properties m_222989_(Function<BlockState, OffsetType> p_222990_) {
            this.f_222978_ = p_222990_;
            return this;
        }
    }

    public static interface StateArgumentPredicate<A> {
        public boolean m_61030_(BlockState var1, BlockGetter var2, BlockPos var3, A var4);
    }

    public static interface StatePredicate {
        public boolean m_61035_(BlockState var1, BlockGetter var2, BlockPos var3);
    }

    public static abstract class BlockStateBase
    extends StateHolder<Block, BlockState> {
        private final int f_60594_;
        private final boolean f_60595_;
        private final boolean f_60596_;
        private final Material f_60597_;
        private final MaterialColor f_60598_;
        private final float f_60599_;
        private final boolean f_60600_;
        private final boolean f_60601_;
        private final StatePredicate f_60602_;
        private final StatePredicate f_60603_;
        private final StatePredicate f_60604_;
        private final StatePredicate f_60605_;
        private final StatePredicate f_60606_;
        private final OffsetType f_222962_;
        @Nullable
        protected Cache f_60593_;

        protected BlockStateBase(Block p_60608_, ImmutableMap<Property<?>, Comparable<?>> p_60609_, MapCodec<BlockState> p_60610_) {
            super(p_60608_, p_60609_, p_60610_);
            Properties $$3 = p_60608_.f_60439_;
            this.f_60594_ = $$3.f_60886_.applyAsInt(this.m_7160_());
            this.f_60595_ = p_60608_.m_7923_(this.m_7160_());
            this.f_60596_ = $$3.f_60896_;
            this.f_60597_ = $$3.f_60882_;
            this.f_60598_ = $$3.f_60883_.apply(this.m_7160_());
            this.f_60599_ = $$3.f_60888_;
            this.f_60600_ = $$3.f_60889_;
            this.f_60601_ = $$3.f_60895_;
            this.f_60602_ = $$3.f_60898_;
            this.f_60603_ = $$3.f_60899_;
            this.f_60604_ = $$3.f_60900_;
            this.f_60605_ = $$3.f_60901_;
            this.f_60606_ = $$3.f_60902_;
            this.f_222962_ = $$3.f_222978_.apply(this.m_7160_());
        }

        public void m_60611_() {
            if (!this.m_60734_().m_49967_()) {
                this.f_60593_ = new Cache(this.m_7160_());
            }
        }

        public Block m_60734_() {
            return (Block)this.f_61112_;
        }

        public Holder<Block> m_222976_() {
            return ((Block)this.f_61112_).m_204297_();
        }

        public Material m_60767_() {
            return this.f_60597_;
        }

        public boolean m_60643_(BlockGetter p_60644_, BlockPos p_60645_, EntityType<?> p_60646_) {
            return this.m_60734_().f_60439_.f_60897_.m_61030_(this.m_7160_(), p_60644_, p_60645_, p_60646_);
        }

        public boolean m_60631_(BlockGetter p_60632_, BlockPos p_60633_) {
            if (this.f_60593_ != null) {
                return this.f_60593_.f_60847_;
            }
            return this.m_60734_().m_7420_(this.m_7160_(), p_60632_, p_60633_);
        }

        public int m_60739_(BlockGetter p_60740_, BlockPos p_60741_) {
            if (this.f_60593_ != null) {
                return this.f_60593_.f_60848_;
            }
            return this.m_60734_().m_7753_(this.m_7160_(), p_60740_, p_60741_);
        }

        public VoxelShape m_60655_(BlockGetter p_60656_, BlockPos p_60657_, Direction p_60658_) {
            if (this.f_60593_ != null && this.f_60593_.f_60849_ != null) {
                return this.f_60593_.f_60849_[p_60658_.ordinal()];
            }
            return Shapes.m_83121_(this.m_60768_(p_60656_, p_60657_), p_60658_);
        }

        public VoxelShape m_60768_(BlockGetter p_60769_, BlockPos p_60770_) {
            return this.m_60734_().m_7952_(this.m_7160_(), p_60769_, p_60770_);
        }

        public boolean m_60779_() {
            return this.f_60593_ == null || this.f_60593_.f_60843_;
        }

        public boolean m_60787_() {
            return this.f_60595_;
        }

        public int m_60791_() {
            return this.f_60594_;
        }

        public boolean m_60795_() {
            return this.f_60596_;
        }

        public MaterialColor m_60780_(BlockGetter p_60781_, BlockPos p_60782_) {
            return this.f_60598_;
        }

        public BlockState m_60717_(Rotation p_60718_) {
            return this.m_60734_().m_6843_(this.m_7160_(), p_60718_);
        }

        public BlockState m_60715_(Mirror p_60716_) {
            return this.m_60734_().m_6943_(this.m_7160_(), p_60716_);
        }

        public RenderShape m_60799_() {
            return this.m_60734_().m_7514_(this.m_7160_());
        }

        public boolean m_60788_(BlockGetter p_60789_, BlockPos p_60790_) {
            return this.f_60606_.m_61035_(this.m_7160_(), p_60789_, p_60790_);
        }

        public float m_60792_(BlockGetter p_60793_, BlockPos p_60794_) {
            return this.m_60734_().m_7749_(this.m_7160_(), p_60793_, p_60794_);
        }

        public boolean m_60796_(BlockGetter p_60797_, BlockPos p_60798_) {
            return this.f_60602_.m_61035_(this.m_7160_(), p_60797_, p_60798_);
        }

        public boolean m_60803_() {
            return this.m_60734_().m_7899_(this.m_7160_());
        }

        public int m_60746_(BlockGetter p_60747_, BlockPos p_60748_, Direction p_60749_) {
            return this.m_60734_().m_6378_(this.m_7160_(), p_60747_, p_60748_, p_60749_);
        }

        public boolean m_60807_() {
            return this.m_60734_().m_7278_(this.m_7160_());
        }

        public int m_60674_(Level p_60675_, BlockPos p_60676_) {
            return this.m_60734_().m_6782_(this.m_7160_(), p_60675_, p_60676_);
        }

        public float m_60800_(BlockGetter p_60801_, BlockPos p_60802_) {
            return this.f_60599_;
        }

        public float m_60625_(Player p_60626_, BlockGetter p_60627_, BlockPos p_60628_) {
            return this.m_60734_().m_5880_(this.m_7160_(), p_60626_, p_60627_, p_60628_);
        }

        public int m_60775_(BlockGetter p_60776_, BlockPos p_60777_, Direction p_60778_) {
            return this.m_60734_().m_6376_(this.m_7160_(), p_60776_, p_60777_, p_60778_);
        }

        public PushReaction m_60811_() {
            return this.m_60734_().m_5537_(this.m_7160_());
        }

        public boolean m_60804_(BlockGetter p_60805_, BlockPos p_60806_) {
            if (this.f_60593_ != null) {
                return this.f_60593_.f_60841_;
            }
            BlockState $$2 = this.m_7160_();
            if ($$2.m_60815_()) {
                return Block.m_49916_($$2.m_60768_(p_60805_, p_60806_));
            }
            return false;
        }

        public boolean m_60815_() {
            return this.f_60601_;
        }

        public boolean m_60719_(BlockState p_60720_, Direction p_60721_) {
            return this.m_60734_().m_6104_(this.m_7160_(), p_60720_, p_60721_);
        }

        public VoxelShape m_60808_(BlockGetter p_60809_, BlockPos p_60810_) {
            return this.m_60651_(p_60809_, p_60810_, CollisionContext.m_82749_());
        }

        public VoxelShape m_60651_(BlockGetter p_60652_, BlockPos p_60653_, CollisionContext p_60654_) {
            return this.m_60734_().m_5940_(this.m_7160_(), p_60652_, p_60653_, p_60654_);
        }

        public VoxelShape m_60812_(BlockGetter p_60813_, BlockPos p_60814_) {
            if (this.f_60593_ != null) {
                return this.f_60593_.f_60842_;
            }
            return this.m_60742_(p_60813_, p_60814_, CollisionContext.m_82749_());
        }

        public VoxelShape m_60742_(BlockGetter p_60743_, BlockPos p_60744_, CollisionContext p_60745_) {
            return this.m_60734_().m_5939_(this.m_7160_(), p_60743_, p_60744_, p_60745_);
        }

        public VoxelShape m_60816_(BlockGetter p_60817_, BlockPos p_60818_) {
            return this.m_60734_().m_7947_(this.m_7160_(), p_60817_, p_60818_);
        }

        public VoxelShape m_60771_(BlockGetter p_60772_, BlockPos p_60773_, CollisionContext p_60774_) {
            return this.m_60734_().m_5909_(this.m_7160_(), p_60772_, p_60773_, p_60774_);
        }

        public VoxelShape m_60820_(BlockGetter p_60821_, BlockPos p_60822_) {
            return this.m_60734_().m_6079_(this.m_7160_(), p_60821_, p_60822_);
        }

        public final boolean m_60634_(BlockGetter p_60635_, BlockPos p_60636_, Entity p_60637_) {
            return this.m_60638_(p_60635_, p_60636_, p_60637_, Direction.UP);
        }

        public final boolean m_60638_(BlockGetter p_60639_, BlockPos p_60640_, Entity p_60641_, Direction p_60642_) {
            return Block.m_49918_(this.m_60742_(p_60639_, p_60640_, CollisionContext.m_82750_(p_60641_)), p_60642_);
        }

        public Vec3 m_60824_(BlockGetter p_60825_, BlockPos p_60826_) {
            if (this.f_222962_ == OffsetType.NONE) {
                return Vec3.f_82478_;
            }
            Block $$2 = this.m_60734_();
            long $$3 = Mth.m_14130_(p_60826_.m_123341_(), 0, p_60826_.m_123343_());
            float $$4 = $$2.m_142740_();
            double $$5 = Mth.m_14008_(((double)((float)($$3 & 0xFL) / 15.0f) - 0.5) * 0.5, -$$4, $$4);
            double $$6 = this.f_222962_ == OffsetType.XYZ ? ((double)((float)($$3 >> 4 & 0xFL) / 15.0f) - 1.0) * (double)$$2.m_142627_() : 0.0;
            double $$7 = Mth.m_14008_(((double)((float)($$3 >> 8 & 0xFL) / 15.0f) - 0.5) * 0.5, -$$4, $$4);
            return new Vec3($$5, $$6, $$7);
        }

        public boolean m_60677_(Level p_60678_, BlockPos p_60679_, int p_60680_, int p_60681_) {
            return this.m_60734_().m_8133_(this.m_7160_(), p_60678_, p_60679_, p_60680_, p_60681_);
        }

        @Deprecated
        public void m_60690_(Level p_60691_, BlockPos p_60692_, Block p_60693_, BlockPos p_60694_, boolean p_60695_) {
            this.m_60734_().m_6861_(this.m_7160_(), p_60691_, p_60692_, p_60693_, p_60694_, p_60695_);
        }

        public final void m_60701_(LevelAccessor p_60702_, BlockPos p_60703_, int p_60704_) {
            this.m_60705_(p_60702_, p_60703_, p_60704_, 512);
        }

        public final void m_60705_(LevelAccessor p_60706_, BlockPos p_60707_, int p_60708_, int p_60709_) {
            this.m_60734_();
            BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
            for (Direction $$5 : f_60441_) {
                $$4.m_122159_(p_60707_, $$5);
                p_60706_.m_213683_($$5.m_122424_(), this.m_7160_(), $$4, p_60707_, p_60708_, p_60709_);
            }
        }

        public final void m_60758_(LevelAccessor p_60759_, BlockPos p_60760_, int p_60761_) {
            this.m_60762_(p_60759_, p_60760_, p_60761_, 512);
        }

        public void m_60762_(LevelAccessor p_60763_, BlockPos p_60764_, int p_60765_, int p_60766_) {
            this.m_60734_().m_7742_(this.m_7160_(), p_60763_, p_60764_, p_60765_, p_60766_);
        }

        public void m_60696_(Level p_60697_, BlockPos p_60698_, BlockState p_60699_, boolean p_60700_) {
            this.m_60734_().m_6807_(this.m_7160_(), p_60697_, p_60698_, p_60699_, p_60700_);
        }

        public void m_60753_(Level p_60754_, BlockPos p_60755_, BlockState p_60756_, boolean p_60757_) {
            this.m_60734_().m_6810_(this.m_7160_(), p_60754_, p_60755_, p_60756_, p_60757_);
        }

        public void m_222963_(ServerLevel p_222964_, BlockPos p_222965_, RandomSource p_222966_) {
            this.m_60734_().m_213897_(this.m_7160_(), p_222964_, p_222965_, p_222966_);
        }

        public void m_222972_(ServerLevel p_222973_, BlockPos p_222974_, RandomSource p_222975_) {
            this.m_60734_().m_213898_(this.m_7160_(), p_222973_, p_222974_, p_222975_);
        }

        public void m_60682_(Level p_60683_, BlockPos p_60684_, Entity p_60685_) {
            this.m_60734_().m_7892_(this.m_7160_(), p_60683_, p_60684_, p_60685_);
        }

        public void m_222967_(ServerLevel p_222968_, BlockPos p_222969_, ItemStack p_222970_, boolean p_222971_) {
            this.m_60734_().m_213646_(this.m_7160_(), p_222968_, p_222969_, p_222970_, p_222971_);
        }

        public List<ItemStack> m_60724_(LootContext.Builder p_60725_) {
            return this.m_60734_().m_7381_(this.m_7160_(), p_60725_);
        }

        public InteractionResult m_60664_(Level p_60665_, Player p_60666_, InteractionHand p_60667_, BlockHitResult p_60668_) {
            return this.m_60734_().m_6227_(this.m_7160_(), p_60665_, p_60668_.m_82425_(), p_60666_, p_60667_, p_60668_);
        }

        public void m_60686_(Level p_60687_, BlockPos p_60688_, Player p_60689_) {
            this.m_60734_().m_6256_(this.m_7160_(), p_60687_, p_60688_, p_60689_);
        }

        public boolean m_60828_(BlockGetter p_60829_, BlockPos p_60830_) {
            return this.f_60603_.m_61035_(this.m_7160_(), p_60829_, p_60830_);
        }

        public boolean m_60831_(BlockGetter p_60832_, BlockPos p_60833_) {
            return this.f_60604_.m_61035_(this.m_7160_(), p_60832_, p_60833_);
        }

        public BlockState m_60728_(Direction p_60729_, BlockState p_60730_, LevelAccessor p_60731_, BlockPos p_60732_, BlockPos p_60733_) {
            return this.m_60734_().m_7417_(this.m_7160_(), p_60729_, p_60730_, p_60731_, p_60732_, p_60733_);
        }

        public boolean m_60647_(BlockGetter p_60648_, BlockPos p_60649_, PathComputationType p_60650_) {
            return this.m_60734_().m_7357_(this.m_7160_(), p_60648_, p_60649_, p_60650_);
        }

        public boolean m_60629_(BlockPlaceContext p_60630_) {
            return this.m_60734_().m_6864_(this.m_7160_(), p_60630_);
        }

        public boolean m_60722_(Fluid p_60723_) {
            return this.m_60734_().m_5946_(this.m_7160_(), p_60723_);
        }

        public boolean m_60710_(LevelReader p_60711_, BlockPos p_60712_) {
            return this.m_60734_().m_7898_(this.m_7160_(), p_60711_, p_60712_);
        }

        public boolean m_60835_(BlockGetter p_60836_, BlockPos p_60837_) {
            return this.f_60605_.m_61035_(this.m_7160_(), p_60836_, p_60837_);
        }

        @Nullable
        public MenuProvider m_60750_(Level p_60751_, BlockPos p_60752_) {
            return this.m_60734_().m_7246_(this.m_7160_(), p_60751_, p_60752_);
        }

        public boolean m_204336_(TagKey<Block> p_204337_) {
            return this.m_60734_().m_204297_().m_203656_(p_204337_);
        }

        public boolean m_204338_(TagKey<Block> p_204339_, Predicate<BlockStateBase> p_204340_) {
            return this.m_204336_(p_204339_) && p_204340_.test(this);
        }

        public boolean m_204341_(HolderSet<Block> p_204342_) {
            return p_204342_.m_203333_(this.m_60734_().m_204297_());
        }

        public Stream<TagKey<Block>> m_204343_() {
            return this.m_60734_().m_204297_().m_203616_();
        }

        public boolean m_155947_() {
            return this.m_60734_() instanceof EntityBlock;
        }

        @Nullable
        public <T extends BlockEntity> BlockEntityTicker<T> m_155944_(Level p_155945_, BlockEntityType<T> p_155946_) {
            if (this.m_60734_() instanceof EntityBlock) {
                return ((EntityBlock)((Object)this.m_60734_())).m_142354_(p_155945_, this.m_7160_(), p_155946_);
            }
            return null;
        }

        public boolean m_60713_(Block p_60714_) {
            return this.m_60734_() == p_60714_;
        }

        public FluidState m_60819_() {
            return this.m_60734_().m_5888_(this.m_7160_());
        }

        public boolean m_60823_() {
            return this.m_60734_().m_6724_(this.m_7160_());
        }

        public long m_60726_(BlockPos p_60727_) {
            return this.m_60734_().m_7799_(this.m_7160_(), p_60727_);
        }

        public SoundType m_60827_() {
            return this.m_60734_().m_49962_(this.m_7160_());
        }

        public void m_60669_(Level p_60670_, BlockState p_60671_, BlockHitResult p_60672_, Projectile p_60673_) {
            this.m_60734_().m_5581_(p_60670_, p_60671_, p_60672_, p_60673_);
        }

        public boolean m_60783_(BlockGetter p_60784_, BlockPos p_60785_, Direction p_60786_) {
            return this.m_60659_(p_60784_, p_60785_, p_60786_, SupportType.FULL);
        }

        public boolean m_60659_(BlockGetter p_60660_, BlockPos p_60661_, Direction p_60662_, SupportType p_60663_) {
            if (this.f_60593_ != null) {
                return this.f_60593_.m_60861_(p_60662_, p_60663_);
            }
            return p_60663_.m_5588_(this.m_7160_(), p_60660_, p_60661_, p_60662_);
        }

        public boolean m_60838_(BlockGetter p_60839_, BlockPos p_60840_) {
            if (this.f_60593_ != null) {
                return this.f_60593_.f_60844_;
            }
            return this.m_60734_().m_180643_(this.m_7160_(), p_60839_, p_60840_);
        }

        protected abstract BlockState m_7160_();

        public boolean m_60834_() {
            return this.f_60600_;
        }

        public OffsetType m_222977_() {
            return this.f_222962_;
        }

        static final class Cache {
            private static final Direction[] f_60845_ = Direction.values();
            private static final int f_60846_ = SupportType.values().length;
            protected final boolean f_60841_;
            final boolean f_60847_;
            final int f_60848_;
            @Nullable
            final VoxelShape[] f_60849_;
            protected final VoxelShape f_60842_;
            protected final boolean f_60843_;
            private final boolean[] f_60850_;
            protected final boolean f_60844_;

            Cache(BlockState p_60853_) {
                Block $$1 = p_60853_.m_60734_();
                this.f_60841_ = p_60853_.m_60804_(EmptyBlockGetter.INSTANCE, BlockPos.f_121853_);
                this.f_60847_ = $$1.m_7420_(p_60853_, EmptyBlockGetter.INSTANCE, BlockPos.f_121853_);
                this.f_60848_ = $$1.m_7753_(p_60853_, EmptyBlockGetter.INSTANCE, BlockPos.f_121853_);
                if (!p_60853_.m_60815_()) {
                    this.f_60849_ = null;
                } else {
                    this.f_60849_ = new VoxelShape[f_60845_.length];
                    VoxelShape $$2 = $$1.m_7952_(p_60853_, EmptyBlockGetter.INSTANCE, BlockPos.f_121853_);
                    Direction[] directionArray = f_60845_;
                    int n = directionArray.length;
                    for (int i = 0; i < n; ++i) {
                        Direction $$3 = directionArray[i];
                        this.f_60849_[$$3.ordinal()] = Shapes.m_83121_($$2, $$3);
                    }
                }
                this.f_60842_ = $$1.m_5939_(p_60853_, EmptyBlockGetter.INSTANCE, BlockPos.f_121853_, CollisionContext.m_82749_());
                if (!this.f_60842_.m_83281_() && p_60853_.m_222977_() != OffsetType.NONE) {
                    throw new IllegalStateException(String.format(Locale.ROOT, "%s has a collision shape and an offset type, but is not marked as dynamicShape in its properties.", Registry.f_122824_.m_7981_($$1)));
                }
                this.f_60843_ = Arrays.stream(Direction.Axis.values()).anyMatch(p_60860_ -> this.f_60842_.m_83288_((Direction.Axis)p_60860_) < 0.0 || this.f_60842_.m_83297_((Direction.Axis)p_60860_) > 1.0);
                this.f_60850_ = new boolean[f_60845_.length * f_60846_];
                for (Direction $$4 : f_60845_) {
                    for (SupportType $$5 : SupportType.values()) {
                        this.f_60850_[Cache.m_60866_((Direction)$$4, (SupportType)$$5)] = $$5.m_5588_(p_60853_, EmptyBlockGetter.INSTANCE, BlockPos.f_121853_, $$4);
                    }
                }
                this.f_60844_ = Block.m_49916_(p_60853_.m_60812_(EmptyBlockGetter.INSTANCE, BlockPos.f_121853_));
            }

            public boolean m_60861_(Direction p_60862_, SupportType p_60863_) {
                return this.f_60850_[Cache.m_60866_(p_60862_, p_60863_)];
            }

            private static int m_60866_(Direction p_60867_, SupportType p_60868_) {
                return p_60867_.ordinal() * f_60846_ + p_60868_.ordinal();
            }
        }
    }

    public static final class OffsetType
    extends Enum<OffsetType> {
        public static final /* enum */ OffsetType NONE = new OffsetType();
        public static final /* enum */ OffsetType XZ = new OffsetType();
        public static final /* enum */ OffsetType XYZ = new OffsetType();
        private static final /* synthetic */ OffsetType[] $VALUES;

        public static OffsetType[] values() {
            return (OffsetType[])$VALUES.clone();
        }

        public static OffsetType valueOf(String p_60880_) {
            return Enum.valueOf(OffsetType.class, p_60880_);
        }

        private static /* synthetic */ OffsetType[] m_155948_() {
            return new OffsetType[]{NONE, XZ, XYZ};
        }

        static {
            $VALUES = OffsetType.m_155948_();
        }
    }
}

