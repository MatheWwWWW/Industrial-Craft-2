/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMapper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

public class Block
extends BlockBehaviour
implements ItemLike {
    private static final Logger f_49790_ = LogUtils.getLogger();
    private final Holder.Reference<Block> f_204296_ = Registry.f_122824_.m_203693_(this);
    public static final IdMapper<BlockState> f_49791_ = new IdMapper();
    private static final LoadingCache<VoxelShape, Boolean> f_49785_ = CacheBuilder.newBuilder().maximumSize(512L).weakKeys().build((CacheLoader)new CacheLoader<VoxelShape, Boolean>(){

        public Boolean load(VoxelShape p_49972_) {
            return !Shapes.m_83157_(Shapes.m_83144_(), p_49972_, BooleanOp.f_82687_);
        }

        public /* synthetic */ Object load(Object object) throws Exception {
            return this.load((VoxelShape)object);
        }
    });
    public static final int f_152393_ = 1;
    public static final int f_152394_ = 2;
    public static final int f_152395_ = 4;
    public static final int f_152396_ = 8;
    public static final int f_152397_ = 16;
    public static final int f_152398_ = 32;
    public static final int f_152399_ = 64;
    public static final int f_152400_ = 128;
    public static final int f_152401_ = 4;
    public static final int f_152402_ = 3;
    public static final int f_152388_ = 11;
    public static final float f_152389_ = -1.0f;
    public static final float f_152390_ = 0.0f;
    public static final int f_152391_ = 512;
    protected final StateDefinition<Block, BlockState> f_49792_;
    private BlockState f_49786_;
    @Nullable
    private String f_49787_;
    @Nullable
    private Item f_49788_;
    private static final int f_152392_ = 2048;
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<BlockStatePairKey>> f_49789_ = ThreadLocal.withInitial(() -> {
        Object2ByteLinkedOpenHashMap<BlockStatePairKey> $$0 = new Object2ByteLinkedOpenHashMap<BlockStatePairKey>(2048, 0.25f){

            protected void rehash(int p_49979_) {
            }
        };
        $$0.defaultReturnValue((byte)127);
        return $$0;
    });

    public static int m_49956_(@Nullable BlockState p_49957_) {
        if (p_49957_ == null) {
            return 0;
        }
        int $$1 = f_49791_.m_7447_(p_49957_);
        return $$1 == -1 ? 0 : $$1;
    }

    public static BlockState m_49803_(int p_49804_) {
        BlockState $$1 = f_49791_.m_7942_(p_49804_);
        return $$1 == null ? Blocks.f_50016_.m_49966_() : $$1;
    }

    public static Block m_49814_(@Nullable Item p_49815_) {
        if (p_49815_ instanceof BlockItem) {
            return ((BlockItem)p_49815_).m_40614_();
        }
        return Blocks.f_50016_;
    }

    public static BlockState m_49897_(BlockState p_49898_, BlockState p_49899_, LevelAccessor p_238252_, BlockPos p_49901_) {
        VoxelShape $$4 = Shapes.m_83148_(p_49898_.m_60812_(p_238252_, p_49901_), p_49899_.m_60812_(p_238252_, p_49901_), BooleanOp.f_82683_).m_83216_(p_49901_.m_123341_(), p_49901_.m_123342_(), p_49901_.m_123343_());
        if ($$4.m_83281_()) {
            return p_49899_;
        }
        List<Entity> $$5 = p_238252_.m_45933_(null, $$4.m_83215_());
        for (Entity $$6 : $$5) {
            double $$7 = Shapes.m_193135_(Direction.Axis.Y, $$6.m_20191_().m_82386_(0.0, 1.0, 0.0), List.of($$4), -1.0);
            $$6.m_6021_($$6.m_20185_(), $$6.m_20186_() + 1.0 + $$7, $$6.m_20189_());
        }
        return p_49899_;
    }

    public static VoxelShape m_49796_(double p_49797_, double p_49798_, double p_49799_, double p_49800_, double p_49801_, double p_49802_) {
        return Shapes.m_83048_(p_49797_ / 16.0, p_49798_ / 16.0, p_49799_ / 16.0, p_49800_ / 16.0, p_49801_ / 16.0, p_49802_ / 16.0);
    }

    public static BlockState m_49931_(BlockState p_49932_, LevelAccessor p_49933_, BlockPos p_49934_) {
        BlockState $$3 = p_49932_;
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (Direction $$5 : f_60441_) {
            $$4.m_122159_(p_49934_, $$5);
            $$3 = $$3.m_60728_($$5, p_49933_.m_8055_($$4), p_49933_, p_49934_, $$4);
        }
        return $$3;
    }

    public static void m_49902_(BlockState p_49903_, BlockState p_49904_, LevelAccessor p_49905_, BlockPos p_49906_, int p_49907_) {
        Block.m_49908_(p_49903_, p_49904_, p_49905_, p_49906_, p_49907_, 512);
    }

    public static void m_49908_(BlockState p_49909_, BlockState p_49910_, LevelAccessor p_49911_, BlockPos p_49912_, int p_49913_, int p_49914_) {
        if (p_49910_ != p_49909_) {
            if (p_49910_.m_60795_()) {
                if (!p_49911_.m_5776_()) {
                    p_49911_.m_7740_(p_49912_, (p_49913_ & 0x20) == 0, null, p_49914_);
                }
            } else {
                p_49911_.m_6933_(p_49912_, p_49910_, p_49913_ & 0xFFFFFFDF, p_49914_);
            }
        }
    }

    public Block(BlockBehaviour.Properties p_49795_) {
        super(p_49795_);
        String $$2;
        StateDefinition.Builder<Block, BlockState> $$1 = new StateDefinition.Builder<Block, BlockState>(this);
        this.m_7926_($$1);
        this.f_49792_ = $$1.m_61101_(Block::m_49966_, BlockState::new);
        this.m_49959_(this.f_49792_.m_61090_());
        if (SharedConstants.f_136183_ && !($$2 = this.getClass().getSimpleName()).endsWith("Block")) {
            f_49790_.error("Block classes should end with Block and {} doesn't.", (Object)$$2);
        }
    }

    public static boolean m_152463_(BlockState p_152464_) {
        return p_152464_.m_60734_() instanceof LeavesBlock || p_152464_.m_60713_(Blocks.f_50375_) || p_152464_.m_60713_(Blocks.f_50143_) || p_152464_.m_60713_(Blocks.f_50144_) || p_152464_.m_60713_(Blocks.f_50186_) || p_152464_.m_60713_(Blocks.f_50133_) || p_152464_.m_204336_(BlockTags.f_13083_);
    }

    public boolean m_6724_(BlockState p_49921_) {
        return this.f_60445_;
    }

    public static boolean m_152444_(BlockState p_152445_, BlockGetter p_152446_, BlockPos p_152447_, Direction p_152448_, BlockPos p_152449_) {
        BlockState $$5 = p_152446_.m_8055_(p_152449_);
        if (p_152445_.m_60719_($$5, p_152448_)) {
            return false;
        }
        if ($$5.m_60815_()) {
            BlockStatePairKey $$6 = new BlockStatePairKey(p_152445_, $$5, p_152448_);
            Object2ByteLinkedOpenHashMap<BlockStatePairKey> $$7 = f_49789_.get();
            byte $$8 = $$7.getAndMoveToFirst((Object)$$6);
            if ($$8 != 127) {
                return $$8 != 0;
            }
            VoxelShape $$9 = p_152445_.m_60655_(p_152446_, p_152447_, p_152448_);
            if ($$9.m_83281_()) {
                return true;
            }
            VoxelShape $$10 = $$5.m_60655_(p_152446_, p_152449_, p_152448_.m_122424_());
            boolean $$11 = Shapes.m_83157_($$9, $$10, BooleanOp.f_82685_);
            if ($$7.size() == 2048) {
                $$7.removeLastByte();
            }
            $$7.putAndMoveToFirst((Object)$$6, (byte)($$11 ? 1 : 0));
            return $$11;
        }
        return true;
    }

    public static boolean m_49936_(BlockGetter p_49937_, BlockPos p_49938_) {
        return p_49937_.m_8055_(p_49938_).m_60659_(p_49937_, p_49938_, Direction.UP, SupportType.RIGID);
    }

    public static boolean m_49863_(LevelReader p_49864_, BlockPos p_49865_, Direction p_49866_) {
        BlockState $$3 = p_49864_.m_8055_(p_49865_);
        if (p_49866_ == Direction.DOWN && $$3.m_204336_(BlockTags.f_13056_)) {
            return false;
        }
        return $$3.m_60659_(p_49864_, p_49865_, p_49866_, SupportType.CENTER);
    }

    public static boolean m_49918_(VoxelShape p_49919_, Direction p_49920_) {
        VoxelShape $$2 = p_49919_.m_83263_(p_49920_);
        return Block.m_49916_($$2);
    }

    public static boolean m_49916_(VoxelShape p_49917_) {
        return (Boolean)f_49785_.getUnchecked((Object)p_49917_);
    }

    public boolean m_7420_(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        return !Block.m_49916_(p_49928_.m_60808_(p_49929_, p_49930_)) && p_49928_.m_60819_().m_76178_();
    }

    public void m_214162_(BlockState p_220827_, Level p_220828_, BlockPos p_220829_, RandomSource p_220830_) {
    }

    public void m_6786_(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
    }

    public static List<ItemStack> m_49869_(BlockState p_49870_, ServerLevel p_49871_, BlockPos p_49872_, @Nullable BlockEntity p_49873_) {
        LootContext.Builder $$4 = new LootContext.Builder(p_49871_).m_230911_(p_49871_.f_46441_).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_49872_)).m_78972_(LootContextParams.f_81463_, ItemStack.f_41583_).m_78984_(LootContextParams.f_81462_, p_49873_);
        return p_49870_.m_60724_($$4);
    }

    public static List<ItemStack> m_49874_(BlockState p_49875_, ServerLevel p_49876_, BlockPos p_49877_, @Nullable BlockEntity p_49878_, @Nullable Entity p_49879_, ItemStack p_49880_) {
        LootContext.Builder $$6 = new LootContext.Builder(p_49876_).m_230911_(p_49876_.f_46441_).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_49877_)).m_78972_(LootContextParams.f_81463_, p_49880_).m_78984_(LootContextParams.f_81455_, p_49879_).m_78984_(LootContextParams.f_81462_, p_49878_);
        return p_49875_.m_60724_($$6);
    }

    public static void m_152460_(BlockState p_152461_, LootContext.Builder p_152462_) {
        ServerLevel $$2 = p_152462_.m_78962_();
        BlockPos $$3 = new BlockPos(p_152462_.m_78970_(LootContextParams.f_81460_));
        p_152461_.m_60724_(p_152462_).forEach(p_152406_ -> Block.m_49840_($$2, $$3, p_152406_));
        p_152461_.m_222967_($$2, $$3, ItemStack.f_41583_, true);
    }

    public static void m_49950_(BlockState p_49951_, Level p_49952_, BlockPos p_49953_) {
        if (p_49952_ instanceof ServerLevel) {
            Block.m_49869_(p_49951_, (ServerLevel)p_49952_, p_49953_, null).forEach(p_49944_ -> Block.m_49840_(p_49952_, p_49953_, p_49944_));
            p_49951_.m_222967_((ServerLevel)p_49952_, p_49953_, ItemStack.f_41583_, true);
        }
    }

    public static void m_49892_(BlockState p_49893_, LevelAccessor p_49894_, BlockPos p_49895_, @Nullable BlockEntity p_49896_) {
        if (p_49894_ instanceof ServerLevel) {
            Block.m_49869_(p_49893_, (ServerLevel)p_49894_, p_49895_, p_49896_).forEach(p_49859_ -> Block.m_49840_((ServerLevel)p_49894_, p_49895_, p_49859_));
            p_49893_.m_222967_((ServerLevel)p_49894_, p_49895_, ItemStack.f_41583_, true);
        }
    }

    public static void m_49881_(BlockState p_49882_, Level p_49883_, BlockPos p_49884_, @Nullable BlockEntity p_49885_, Entity p_49886_, ItemStack p_49887_) {
        if (p_49883_ instanceof ServerLevel) {
            Block.m_49874_(p_49882_, (ServerLevel)p_49883_, p_49884_, p_49885_, p_49886_, p_49887_).forEach(p_49925_ -> Block.m_49840_(p_49883_, p_49884_, p_49925_));
            p_49882_.m_222967_((ServerLevel)p_49883_, p_49884_, p_49887_, true);
        }
    }

    public static void m_49840_(Level p_49841_, BlockPos p_49842_, ItemStack p_49843_) {
        float $$3 = EntityType.f_20461_.m_20679_() / 2.0f;
        double $$4 = (double)((float)p_49842_.m_123341_() + 0.5f) + Mth.m_216263_(p_49841_.f_46441_, -0.25, 0.25);
        double $$5 = (double)((float)p_49842_.m_123342_() + 0.5f) + Mth.m_216263_(p_49841_.f_46441_, -0.25, 0.25) - (double)$$3;
        double $$6 = (double)((float)p_49842_.m_123343_() + 0.5f) + Mth.m_216263_(p_49841_.f_46441_, -0.25, 0.25);
        Block.m_152440_(p_49841_, () -> new ItemEntity(p_49841_, $$4, $$5, $$6, p_49843_), p_49843_);
    }

    public static void m_152435_(Level p_152436_, BlockPos p_152437_, Direction p_152438_, ItemStack p_152439_) {
        int $$4 = p_152438_.m_122429_();
        int $$5 = p_152438_.m_122430_();
        int $$6 = p_152438_.m_122431_();
        float $$7 = EntityType.f_20461_.m_20678_() / 2.0f;
        float $$8 = EntityType.f_20461_.m_20679_() / 2.0f;
        double $$9 = (double)((float)p_152437_.m_123341_() + 0.5f) + ($$4 == 0 ? Mth.m_216263_(p_152436_.f_46441_, -0.25, 0.25) : (double)((float)$$4 * (0.5f + $$7)));
        double $$10 = (double)((float)p_152437_.m_123342_() + 0.5f) + ($$5 == 0 ? Mth.m_216263_(p_152436_.f_46441_, -0.25, 0.25) : (double)((float)$$5 * (0.5f + $$8))) - (double)$$8;
        double $$11 = (double)((float)p_152437_.m_123343_() + 0.5f) + ($$6 == 0 ? Mth.m_216263_(p_152436_.f_46441_, -0.25, 0.25) : (double)((float)$$6 * (0.5f + $$7)));
        double $$12 = $$4 == 0 ? Mth.m_216263_(p_152436_.f_46441_, -0.1, 0.1) : (double)$$4 * 0.1;
        double $$13 = $$5 == 0 ? Mth.m_216263_(p_152436_.f_46441_, 0.0, 0.1) : (double)$$5 * 0.1 + 0.1;
        double $$14 = $$6 == 0 ? Mth.m_216263_(p_152436_.f_46441_, -0.1, 0.1) : (double)$$6 * 0.1;
        Block.m_152440_(p_152436_, () -> new ItemEntity(p_152436_, $$9, $$10, $$11, p_152439_, $$12, $$13, $$14), p_152439_);
    }

    private static void m_152440_(Level p_152441_, Supplier<ItemEntity> p_152442_, ItemStack p_152443_) {
        if (p_152441_.f_46443_ || p_152443_.m_41619_() || !p_152441_.m_46469_().m_46207_(GameRules.f_46136_)) {
            return;
        }
        ItemEntity $$3 = p_152442_.get();
        $$3.m_32060_();
        p_152441_.m_7967_($$3);
    }

    protected void m_49805_(ServerLevel p_49806_, BlockPos p_49807_, int p_49808_) {
        if (p_49806_.m_46469_().m_46207_(GameRules.f_46136_)) {
            ExperienceOrb.m_147082_(p_49806_, Vec3.m_82512_(p_49807_), p_49808_);
        }
    }

    public float m_7325_() {
        return this.f_60444_;
    }

    public void m_7592_(Level p_49844_, BlockPos p_49845_, Explosion p_49846_) {
    }

    public void m_141947_(Level p_152431_, BlockPos p_152432_, BlockState p_152433_, Entity p_152434_) {
    }

    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_49820_) {
        return this.m_49966_();
    }

    public void m_6240_(Level p_49827_, Player p_49828_, BlockPos p_49829_, BlockState p_49830_, @Nullable BlockEntity p_49831_, ItemStack p_49832_) {
        p_49828_.m_36246_(Stats.f_12949_.m_12902_(this));
        p_49828_.m_36399_(0.005f);
        Block.m_49881_(p_49830_, p_49827_, p_49829_, p_49831_, p_49828_, p_49832_);
    }

    public void m_6402_(Level p_49847_, BlockPos p_49848_, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
    }

    public boolean m_5568_() {
        return !this.f_60442_.m_76333_() && !this.f_60442_.m_76332_();
    }

    public MutableComponent m_49954_() {
        return Component.m_237115_(this.m_7705_());
    }

    public String m_7705_() {
        if (this.f_49787_ == null) {
            this.f_49787_ = Util.m_137492_("block", Registry.f_122824_.m_7981_(this));
        }
        return this.f_49787_;
    }

    public void m_142072_(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
        p_152429_.m_142535_(p_152430_, 1.0f, DamageSource.f_19315_);
    }

    public void m_5548_(BlockGetter p_49821_, Entity p_49822_) {
        p_49822_.m_20256_(p_49822_.m_20184_().m_82542_(1.0, 0.0, 1.0));
    }

    public ItemStack m_7397_(BlockGetter p_49823_, BlockPos p_49824_, BlockState p_49825_) {
        return new ItemStack(this);
    }

    public void m_49811_(CreativeModeTab p_49812_, NonNullList<ItemStack> p_49813_) {
        p_49813_.add(new ItemStack(this));
    }

    public float m_49958_() {
        return this.f_60447_;
    }

    public float m_49961_() {
        return this.f_60448_;
    }

    public float m_49964_() {
        return this.f_60449_;
    }

    protected void m_142387_(Level p_152422_, Player p_152423_, BlockPos p_152424_, BlockState p_152425_) {
        p_152422_.m_5898_(p_152423_, 2001, p_152424_, Block.m_49956_(p_152425_));
    }

    public void m_5707_(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) {
        this.m_142387_(p_49852_, p_49855_, p_49853_, p_49854_);
        if (p_49854_.m_204336_(BlockTags.f_13088_)) {
            PiglinAi.m_34873_(p_49855_, false);
        }
        p_49852_.m_220407_(GameEvent.f_157794_, p_49853_, GameEvent.Context.m_223719_(p_49855_, p_49854_));
    }

    public void m_141997_(BlockState p_152450_, Level p_152451_, BlockPos p_152452_, Biome.Precipitation p_152453_) {
    }

    public boolean m_6903_(Explosion p_49826_) {
        return true;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49915_) {
    }

    public StateDefinition<Block, BlockState> m_49965_() {
        return this.f_49792_;
    }

    protected final void m_49959_(BlockState p_49960_) {
        this.f_49786_ = p_49960_;
    }

    public final BlockState m_49966_() {
        return this.f_49786_;
    }

    public final BlockState m_152465_(BlockState p_152466_) {
        BlockState $$1 = this.m_49966_();
        for (Property<?> $$2 : p_152466_.m_60734_().m_49965_().m_61092_()) {
            if (!$$1.m_61138_($$2)) continue;
            $$1 = Block.m_152454_(p_152466_, $$1, $$2);
        }
        return $$1;
    }

    private static <T extends Comparable<T>> BlockState m_152454_(BlockState p_152455_, BlockState p_152456_, Property<T> p_152457_) {
        return (BlockState)p_152456_.m_61124_(p_152457_, p_152455_.m_61143_(p_152457_));
    }

    public SoundType m_49962_(BlockState p_49963_) {
        return this.f_60446_;
    }

    @Override
    public Item m_5456_() {
        if (this.f_49788_ == null) {
            this.f_49788_ = Item.m_41439_(this);
        }
        return this.f_49788_;
    }

    public boolean m_49967_() {
        return this.f_60438_;
    }

    public String toString() {
        return "Block{" + Registry.f_122824_.m_7981_(this) + "}";
    }

    public void m_5871_(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> p_49818_, TooltipFlag p_49819_) {
    }

    @Override
    protected Block m_7374_() {
        return this;
    }

    protected ImmutableMap<BlockState, VoxelShape> m_152458_(Function<BlockState, VoxelShape> p_152459_) {
        return (ImmutableMap)this.f_49792_.m_61056_().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), p_152459_));
    }

    @Deprecated
    public Holder.Reference<Block> m_204297_() {
        return this.f_204296_;
    }

    protected void m_220822_(ServerLevel p_220823_, BlockPos p_220824_, ItemStack p_220825_, IntProvider p_220826_) {
        int $$4;
        if (EnchantmentHelper.m_44843_(Enchantments.f_44985_, p_220825_) == 0 && ($$4 = p_220826_.m_214085_(p_220823_.f_46441_)) > 0) {
            this.m_49805_(p_220823_, p_220824_, $$4);
        }
    }

    public static final class BlockStatePairKey {
        private final BlockState f_49980_;
        private final BlockState f_49981_;
        private final Direction f_49982_;

        public BlockStatePairKey(BlockState p_49984_, BlockState p_49985_, Direction p_49986_) {
            this.f_49980_ = p_49984_;
            this.f_49981_ = p_49985_;
            this.f_49982_ = p_49986_;
        }

        public boolean equals(Object p_49988_) {
            if (this == p_49988_) {
                return true;
            }
            if (!(p_49988_ instanceof BlockStatePairKey)) {
                return false;
            }
            BlockStatePairKey $$1 = (BlockStatePairKey)p_49988_;
            return this.f_49980_ == $$1.f_49980_ && this.f_49981_ == $$1.f_49981_ && this.f_49982_ == $$1.f_49982_;
        }

        public int hashCode() {
            int $$0 = this.f_49980_.hashCode();
            $$0 = 31 * $$0 + this.f_49981_.hashCode();
            $$0 = 31 * $$0 + this.f_49982_.hashCode();
            return $$0;
        }
    }
}

