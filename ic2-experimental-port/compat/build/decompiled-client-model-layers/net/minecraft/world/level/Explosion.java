/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Explosion {
    private static final ExplosionDamageCalculator f_46008_ = new ExplosionDamageCalculator();
    private static final int f_151469_ = 16;
    private final boolean f_46009_;
    private final BlockInteraction f_46010_;
    private final RandomSource f_46011_ = RandomSource.m_216327_();
    private final Level f_46012_;
    private final double f_46013_;
    private final double f_46014_;
    private final double f_46015_;
    @Nullable
    private final Entity f_46016_;
    private final float f_46017_;
    private final DamageSource f_46018_;
    private final ExplosionDamageCalculator f_46019_;
    private final ObjectArrayList<BlockPos> f_46020_ = new ObjectArrayList();
    private final Map<Player, Vec3> f_46021_ = Maps.newHashMap();

    public Explosion(Level p_151471_, @Nullable Entity p_151472_, double p_151473_, double p_151474_, double p_151475_, float p_151476_) {
        this(p_151471_, p_151472_, p_151473_, p_151474_, p_151475_, p_151476_, false, BlockInteraction.DESTROY);
    }

    public Explosion(Level p_46024_, @Nullable Entity p_46025_, double p_46026_, double p_46027_, double p_46028_, float p_46029_, List<BlockPos> p_46030_) {
        this(p_46024_, p_46025_, p_46026_, p_46027_, p_46028_, p_46029_, false, BlockInteraction.DESTROY, p_46030_);
    }

    public Explosion(Level p_46041_, @Nullable Entity p_46042_, double p_46043_, double p_46044_, double p_46045_, float p_46046_, boolean p_46047_, BlockInteraction p_46048_, List<BlockPos> p_46049_) {
        this(p_46041_, p_46042_, p_46043_, p_46044_, p_46045_, p_46046_, p_46047_, p_46048_);
        this.f_46020_.addAll(p_46049_);
    }

    public Explosion(Level p_46032_, @Nullable Entity p_46033_, double p_46034_, double p_46035_, double p_46036_, float p_46037_, boolean p_46038_, BlockInteraction p_46039_) {
        this(p_46032_, p_46033_, null, null, p_46034_, p_46035_, p_46036_, p_46037_, p_46038_, p_46039_);
    }

    public Explosion(Level p_46051_, @Nullable Entity p_46052_, @Nullable DamageSource p_46053_, @Nullable ExplosionDamageCalculator p_46054_, double p_46055_, double p_46056_, double p_46057_, float p_46058_, boolean p_46059_, BlockInteraction p_46060_) {
        this.f_46012_ = p_46051_;
        this.f_46016_ = p_46052_;
        this.f_46017_ = p_46058_;
        this.f_46013_ = p_46055_;
        this.f_46014_ = p_46056_;
        this.f_46015_ = p_46057_;
        this.f_46009_ = p_46059_;
        this.f_46010_ = p_46060_;
        this.f_46018_ = p_46053_ == null ? DamageSource.m_19358_(this) : p_46053_;
        this.f_46019_ = p_46054_ == null ? this.m_46062_(p_46052_) : p_46054_;
    }

    private ExplosionDamageCalculator m_46062_(@Nullable Entity p_46063_) {
        return p_46063_ == null ? f_46008_ : new EntityBasedExplosionDamageCalculator(p_46063_);
    }

    public static float m_46064_(Vec3 p_46065_, Entity p_46066_) {
        AABB $$2 = p_46066_.m_20191_();
        double $$3 = 1.0 / (($$2.f_82291_ - $$2.f_82288_) * 2.0 + 1.0);
        double $$4 = 1.0 / (($$2.f_82292_ - $$2.f_82289_) * 2.0 + 1.0);
        double $$5 = 1.0 / (($$2.f_82293_ - $$2.f_82290_) * 2.0 + 1.0);
        double $$6 = (1.0 - Math.floor(1.0 / $$3) * $$3) / 2.0;
        double $$7 = (1.0 - Math.floor(1.0 / $$5) * $$5) / 2.0;
        if ($$3 < 0.0 || $$4 < 0.0 || $$5 < 0.0) {
            return 0.0f;
        }
        int $$8 = 0;
        int $$9 = 0;
        for (double $$10 = 0.0; $$10 <= 1.0; $$10 += $$3) {
            for (double $$11 = 0.0; $$11 <= 1.0; $$11 += $$4) {
                for (double $$12 = 0.0; $$12 <= 1.0; $$12 += $$5) {
                    double $$15;
                    double $$14;
                    double $$13 = Mth.m_14139_($$10, $$2.f_82288_, $$2.f_82291_);
                    Vec3 $$16 = new Vec3($$13 + $$6, $$14 = Mth.m_14139_($$11, $$2.f_82289_, $$2.f_82292_), ($$15 = Mth.m_14139_($$12, $$2.f_82290_, $$2.f_82293_)) + $$7);
                    if (p_46066_.f_19853_.m_45547_(new ClipContext($$16, p_46065_, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_46066_)).m_6662_() == HitResult.Type.MISS) {
                        ++$$8;
                    }
                    ++$$9;
                }
            }
        }
        return (float)$$8 / (float)$$9;
    }

    public void m_46061_() {
        this.f_46012_.m_220400_(this.f_46016_, GameEvent.f_157812_, new Vec3(this.f_46013_, this.f_46014_, this.f_46015_));
        HashSet $$0 = Sets.newHashSet();
        int $$1 = 16;
        for (int $$2 = 0; $$2 < 16; ++$$2) {
            for (int $$3 = 0; $$3 < 16; ++$$3) {
                block2: for (int $$4 = 0; $$4 < 16; ++$$4) {
                    if ($$2 != 0 && $$2 != 15 && $$3 != 0 && $$3 != 15 && $$4 != 0 && $$4 != 15) continue;
                    double $$5 = (float)$$2 / 15.0f * 2.0f - 1.0f;
                    double $$6 = (float)$$3 / 15.0f * 2.0f - 1.0f;
                    double $$7 = (float)$$4 / 15.0f * 2.0f - 1.0f;
                    double $$8 = Math.sqrt($$5 * $$5 + $$6 * $$6 + $$7 * $$7);
                    $$5 /= $$8;
                    $$6 /= $$8;
                    $$7 /= $$8;
                    double $$10 = this.f_46013_;
                    double $$11 = this.f_46014_;
                    double $$12 = this.f_46015_;
                    float $$13 = 0.3f;
                    for (float $$9 = this.f_46017_ * (0.7f + this.f_46012_.f_46441_.m_188501_() * 0.6f); $$9 > 0.0f; $$9 -= 0.22500001f) {
                        BlockPos $$14 = new BlockPos($$10, $$11, $$12);
                        BlockState $$15 = this.f_46012_.m_8055_($$14);
                        FluidState $$16 = this.f_46012_.m_6425_($$14);
                        if (!this.f_46012_.m_46739_($$14)) continue block2;
                        Optional<Float> $$17 = this.f_46019_.m_6617_(this, this.f_46012_, $$14, $$15, $$16);
                        if ($$17.isPresent()) {
                            $$9 -= ($$17.get().floatValue() + 0.3f) * 0.3f;
                        }
                        if ($$9 > 0.0f && this.f_46019_.m_6714_(this, this.f_46012_, $$14, $$15, $$9)) {
                            $$0.add($$14);
                        }
                        $$10 += $$5 * (double)0.3f;
                        $$11 += $$6 * (double)0.3f;
                        $$12 += $$7 * (double)0.3f;
                    }
                }
            }
        }
        this.f_46020_.addAll((Collection)$$0);
        float $$18 = this.f_46017_ * 2.0f;
        int $$19 = Mth.m_14107_(this.f_46013_ - (double)$$18 - 1.0);
        int $$20 = Mth.m_14107_(this.f_46013_ + (double)$$18 + 1.0);
        int $$21 = Mth.m_14107_(this.f_46014_ - (double)$$18 - 1.0);
        int $$22 = Mth.m_14107_(this.f_46014_ + (double)$$18 + 1.0);
        int $$23 = Mth.m_14107_(this.f_46015_ - (double)$$18 - 1.0);
        int $$24 = Mth.m_14107_(this.f_46015_ + (double)$$18 + 1.0);
        List<Entity> $$25 = this.f_46012_.m_45933_(this.f_46016_, new AABB($$19, $$21, $$23, $$20, $$22, $$24));
        Vec3 $$26 = new Vec3(this.f_46013_, this.f_46014_, this.f_46015_);
        for (int $$27 = 0; $$27 < $$25.size(); ++$$27) {
            Player $$37;
            double $$32;
            double $$31;
            double $$30;
            double $$33;
            double $$29;
            Entity $$28 = $$25.get($$27);
            if ($$28.m_6128_() || !(($$29 = Math.sqrt($$28.m_20238_($$26)) / (double)$$18) <= 1.0) || ($$33 = Math.sqrt(($$30 = $$28.m_20185_() - this.f_46013_) * $$30 + ($$31 = ($$28 instanceof PrimedTnt ? $$28.m_20186_() : $$28.m_20188_()) - this.f_46014_) * $$31 + ($$32 = $$28.m_20189_() - this.f_46015_) * $$32)) == 0.0) continue;
            $$30 /= $$33;
            $$31 /= $$33;
            $$32 /= $$33;
            double $$34 = Explosion.m_46064_($$26, $$28);
            double $$35 = (1.0 - $$29) * $$34;
            $$28.m_6469_(this.m_46077_(), (int)(($$35 * $$35 + $$35) / 2.0 * 7.0 * (double)$$18 + 1.0));
            double $$36 = $$35;
            if ($$28 instanceof LivingEntity) {
                $$36 = ProtectionEnchantment.m_45135_((LivingEntity)$$28, $$35);
            }
            $$28.m_20256_($$28.m_20184_().m_82520_($$30 * $$36, $$31 * $$36, $$32 * $$36));
            if (!($$28 instanceof Player) || ($$37 = (Player)$$28).m_5833_() || $$37.m_7500_() && $$37.m_150110_().f_35935_) continue;
            this.f_46021_.put($$37, new Vec3($$30 * $$35, $$31 * $$35, $$32 * $$35));
        }
    }

    public void m_46075_(boolean p_46076_) {
        boolean $$1;
        if (this.f_46012_.f_46443_) {
            this.f_46012_.m_7785_(this.f_46013_, this.f_46014_, this.f_46015_, SoundEvents.f_11913_, SoundSource.BLOCKS, 4.0f, (1.0f + (this.f_46012_.f_46441_.m_188501_() - this.f_46012_.f_46441_.m_188501_()) * 0.2f) * 0.7f, false);
        }
        boolean bl = $$1 = this.f_46010_ != BlockInteraction.NONE;
        if (p_46076_) {
            if (this.f_46017_ < 2.0f || !$$1) {
                this.f_46012_.m_7106_(ParticleTypes.f_123813_, this.f_46013_, this.f_46014_, this.f_46015_, 1.0, 0.0, 0.0);
            } else {
                this.f_46012_.m_7106_(ParticleTypes.f_123812_, this.f_46013_, this.f_46014_, this.f_46015_, 1.0, 0.0, 0.0);
            }
        }
        if ($$1) {
            ObjectArrayList $$2 = new ObjectArrayList();
            boolean $$3 = this.m_46079_() instanceof Player;
            Util.m_214673_(this.f_46020_, this.f_46012_.f_46441_);
            for (BlockPos $$4 : this.f_46020_) {
                Level level;
                BlockState $$5 = this.f_46012_.m_8055_($$4);
                Block $$6 = $$5.m_60734_();
                if ($$5.m_60795_()) continue;
                BlockPos $$7 = $$4.m_7949_();
                this.f_46012_.m_46473_().m_6180_("explosion_blocks");
                if ($$6.m_6903_(this) && (level = this.f_46012_) instanceof ServerLevel) {
                    ServerLevel $$8 = (ServerLevel)level;
                    BlockEntity $$9 = $$5.m_155947_() ? this.f_46012_.m_7702_($$4) : null;
                    LootContext.Builder $$10 = new LootContext.Builder($$8).m_230911_(this.f_46012_.f_46441_).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_($$4)).m_78972_(LootContextParams.f_81463_, ItemStack.f_41583_).m_78984_(LootContextParams.f_81462_, $$9).m_78984_(LootContextParams.f_81455_, this.f_46016_);
                    if (this.f_46010_ == BlockInteraction.DESTROY) {
                        $$10.m_78972_(LootContextParams.f_81464_, Float.valueOf(this.f_46017_));
                    }
                    $$5.m_222967_($$8, $$4, ItemStack.f_41583_, $$3);
                    $$5.m_60724_($$10).forEach(p_46074_ -> Explosion.m_46067_((ObjectArrayList<Pair<ItemStack, BlockPos>>)$$2, p_46074_, $$7));
                }
                this.f_46012_.m_7731_($$4, Blocks.f_50016_.m_49966_(), 3);
                $$6.m_7592_(this.f_46012_, $$4, this);
                this.f_46012_.m_46473_().m_7238_();
            }
            for (Pair $$11 : $$2) {
                Block.m_49840_(this.f_46012_, (BlockPos)$$11.getSecond(), (ItemStack)$$11.getFirst());
            }
        }
        if (this.f_46009_) {
            for (BlockPos $$12 : this.f_46020_) {
                if (this.f_46011_.m_188503_(3) != 0 || !this.f_46012_.m_8055_($$12).m_60795_() || !this.f_46012_.m_8055_($$12.m_7495_()).m_60804_(this.f_46012_, $$12.m_7495_())) continue;
                this.f_46012_.m_46597_($$12, BaseFireBlock.m_49245_(this.f_46012_, $$12));
            }
        }
    }

    private static void m_46067_(ObjectArrayList<Pair<ItemStack, BlockPos>> p_46068_, ItemStack p_46069_, BlockPos p_46070_) {
        int $$3 = p_46068_.size();
        for (int $$4 = 0; $$4 < $$3; ++$$4) {
            Pair $$5 = (Pair)p_46068_.get($$4);
            ItemStack $$6 = (ItemStack)$$5.getFirst();
            if (!ItemEntity.m_32026_($$6, p_46069_)) continue;
            ItemStack $$7 = ItemEntity.m_32029_($$6, p_46069_, 16);
            p_46068_.set($$4, (Object)Pair.of((Object)$$7, (Object)((BlockPos)$$5.getSecond())));
            if (!p_46069_.m_41619_()) continue;
            return;
        }
        p_46068_.add((Object)Pair.of((Object)p_46069_, (Object)p_46070_));
    }

    public DamageSource m_46077_() {
        return this.f_46018_;
    }

    public Map<Player, Vec3> m_46078_() {
        return this.f_46021_;
    }

    @Nullable
    public LivingEntity m_46079_() {
        Entity $$0;
        if (this.f_46016_ == null) {
            return null;
        }
        if (this.f_46016_ instanceof PrimedTnt) {
            return ((PrimedTnt)this.f_46016_).m_32099_();
        }
        if (this.f_46016_ instanceof LivingEntity) {
            return (LivingEntity)this.f_46016_;
        }
        if (this.f_46016_ instanceof Projectile && ($$0 = ((Projectile)this.f_46016_).m_37282_()) instanceof LivingEntity) {
            return (LivingEntity)$$0;
        }
        return null;
    }

    public void m_46080_() {
        this.f_46020_.clear();
    }

    public List<BlockPos> m_46081_() {
        return this.f_46020_;
    }

    public static final class BlockInteraction
    extends Enum<BlockInteraction> {
        public static final /* enum */ BlockInteraction NONE = new BlockInteraction();
        public static final /* enum */ BlockInteraction BREAK = new BlockInteraction();
        public static final /* enum */ BlockInteraction DESTROY = new BlockInteraction();
        private static final /* synthetic */ BlockInteraction[] $VALUES;

        public static BlockInteraction[] values() {
            return (BlockInteraction[])$VALUES.clone();
        }

        public static BlockInteraction valueOf(String p_46091_) {
            return Enum.valueOf(BlockInteraction.class, p_46091_);
        }

        private static /* synthetic */ BlockInteraction[] m_151477_() {
            return new BlockInteraction[]{NONE, BREAK, DESTROY};
        }

        static {
            $VALUES = BlockInteraction.m_151477_();
        }
    }
}

