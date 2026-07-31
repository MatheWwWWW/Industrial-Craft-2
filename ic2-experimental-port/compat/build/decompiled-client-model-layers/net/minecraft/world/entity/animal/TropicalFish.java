/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class TropicalFish
extends AbstractSchoolingFish {
    public static final String f_149057_ = "BucketVariantTag";
    private static final EntityDataAccessor<Integer> f_30011_ = SynchedEntityData.m_135353_(TropicalFish.class, EntityDataSerializers.f_135028_);
    public static final int f_149061_ = 0;
    public static final int f_149062_ = 1;
    private static final int f_149058_ = 2;
    private static final ResourceLocation[] f_30012_ = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_a.png"), new ResourceLocation("textures/entity/fish/tropical_b.png")};
    private static final ResourceLocation[] f_30008_ = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_a_pattern_1.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_2.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_3.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_4.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_5.png"), new ResourceLocation("textures/entity/fish/tropical_a_pattern_6.png")};
    private static final ResourceLocation[] f_30009_ = new ResourceLocation[]{new ResourceLocation("textures/entity/fish/tropical_b_pattern_1.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_2.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_3.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_4.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_5.png"), new ResourceLocation("textures/entity/fish/tropical_b_pattern_6.png")};
    private static final int f_149059_ = 6;
    private static final int f_149060_ = 15;
    public static final int[] f_30007_ = new int[]{TropicalFish.m_30018_(Pattern.STRIPEY, DyeColor.ORANGE, DyeColor.GRAY), TropicalFish.m_30018_(Pattern.FLOPPER, DyeColor.GRAY, DyeColor.GRAY), TropicalFish.m_30018_(Pattern.FLOPPER, DyeColor.GRAY, DyeColor.BLUE), TropicalFish.m_30018_(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.GRAY), TropicalFish.m_30018_(Pattern.SUNSTREAK, DyeColor.BLUE, DyeColor.GRAY), TropicalFish.m_30018_(Pattern.KOB, DyeColor.ORANGE, DyeColor.WHITE), TropicalFish.m_30018_(Pattern.SPOTTY, DyeColor.PINK, DyeColor.LIGHT_BLUE), TropicalFish.m_30018_(Pattern.BLOCKFISH, DyeColor.PURPLE, DyeColor.YELLOW), TropicalFish.m_30018_(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.RED), TropicalFish.m_30018_(Pattern.SPOTTY, DyeColor.WHITE, DyeColor.YELLOW), TropicalFish.m_30018_(Pattern.GLITTER, DyeColor.WHITE, DyeColor.GRAY), TropicalFish.m_30018_(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.ORANGE), TropicalFish.m_30018_(Pattern.DASHER, DyeColor.CYAN, DyeColor.PINK), TropicalFish.m_30018_(Pattern.BRINELY, DyeColor.LIME, DyeColor.LIGHT_BLUE), TropicalFish.m_30018_(Pattern.BETTY, DyeColor.RED, DyeColor.WHITE), TropicalFish.m_30018_(Pattern.SNOOPER, DyeColor.GRAY, DyeColor.RED), TropicalFish.m_30018_(Pattern.BLOCKFISH, DyeColor.RED, DyeColor.WHITE), TropicalFish.m_30018_(Pattern.FLOPPER, DyeColor.WHITE, DyeColor.YELLOW), TropicalFish.m_30018_(Pattern.KOB, DyeColor.RED, DyeColor.WHITE), TropicalFish.m_30018_(Pattern.SUNSTREAK, DyeColor.GRAY, DyeColor.WHITE), TropicalFish.m_30018_(Pattern.DASHER, DyeColor.CYAN, DyeColor.YELLOW), TropicalFish.m_30018_(Pattern.FLOPPER, DyeColor.YELLOW, DyeColor.YELLOW)};
    private boolean f_30010_ = true;

    private static int m_30018_(Pattern p_30019_, DyeColor p_30020_, DyeColor p_30021_) {
        return p_30019_.m_30088_() & 0xFF | (p_30019_.m_30092_() & 0xFF) << 8 | (p_30020_.m_41060_() & 0xFF) << 16 | (p_30021_.m_41060_() & 0xFF) << 24;
    }

    public TropicalFish(EntityType<? extends TropicalFish> p_30015_, Level p_30016_) {
        super((EntityType<? extends AbstractSchoolingFish>)p_30015_, p_30016_);
    }

    public static String m_30030_(int p_30031_) {
        return "entity.minecraft.tropical_fish.predefined." + p_30031_;
    }

    public static DyeColor m_30050_(int p_30051_) {
        return DyeColor.m_41053_(TropicalFish.m_30060_(p_30051_));
    }

    public static DyeColor m_30052_(int p_30053_) {
        return DyeColor.m_41053_(TropicalFish.m_30062_(p_30053_));
    }

    public static String m_30054_(int p_30055_) {
        int $$1 = TropicalFish.m_30058_(p_30055_);
        int $$2 = TropicalFish.m_30064_(p_30055_);
        return "entity.minecraft.tropical_fish.type." + Pattern.m_30089_($$1, $$2);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_30011_, 0);
    }

    @Override
    public void m_7380_(CompoundTag p_30033_) {
        super.m_7380_(p_30033_);
        p_30033_.m_128405_("Variant", this.m_30042_());
    }

    @Override
    public void m_7378_(CompoundTag p_30029_) {
        super.m_7378_(p_30029_);
        this.m_30056_(p_30029_.m_128451_("Variant"));
    }

    public void m_30056_(int p_30057_) {
        this.f_19804_.m_135381_(f_30011_, p_30057_);
    }

    @Override
    public boolean m_7296_(int p_30035_) {
        return !this.f_30010_;
    }

    public int m_30042_() {
        return this.f_19804_.m_135370_(f_30011_);
    }

    @Override
    public void m_6872_(ItemStack p_30049_) {
        super.m_6872_(p_30049_);
        CompoundTag $$1 = p_30049_.m_41784_();
        $$1.m_128405_(f_149057_, this.m_30042_());
    }

    @Override
    public ItemStack m_28282_() {
        return new ItemStack(Items.f_42459_);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12526_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12527_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_30039_) {
        return SoundEvents.f_12529_;
    }

    @Override
    protected SoundEvent m_5699_() {
        return SoundEvents.f_12528_;
    }

    private static int m_30060_(int p_30061_) {
        return (p_30061_ & 0xFF0000) >> 16;
    }

    public float[] m_30043_() {
        return DyeColor.m_41053_(TropicalFish.m_30060_(this.m_30042_())).m_41068_();
    }

    private static int m_30062_(int p_30063_) {
        return (p_30063_ & 0xFF000000) >> 24;
    }

    public float[] m_30044_() {
        return DyeColor.m_41053_(TropicalFish.m_30062_(this.m_30042_())).m_41068_();
    }

    public static int m_30058_(int p_30059_) {
        return Math.min(p_30059_ & 0xFF, 1);
    }

    public int m_30045_() {
        return TropicalFish.m_30058_(this.m_30042_());
    }

    private static int m_30064_(int p_30065_) {
        return Math.min((p_30065_ & 0xFF00) >> 8, 5);
    }

    public ResourceLocation m_30046_() {
        if (TropicalFish.m_30058_(this.m_30042_()) == 0) {
            return f_30008_[TropicalFish.m_30064_(this.m_30042_())];
        }
        return f_30009_[TropicalFish.m_30064_(this.m_30042_())];
    }

    public ResourceLocation m_30047_() {
        return f_30012_[TropicalFish.m_30058_(this.m_30042_())];
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_30023_, DifficultyInstance p_30024_, MobSpawnType p_30025_, @Nullable SpawnGroupData p_30026_, @Nullable CompoundTag p_30027_) {
        int $$19;
        int $$18;
        int $$17;
        int $$16;
        p_30026_ = super.m_6518_(p_30023_, p_30024_, p_30025_, p_30026_, p_30027_);
        if (p_30025_ == MobSpawnType.BUCKET && p_30027_ != null && p_30027_.m_128425_(f_149057_, 3)) {
            this.m_30056_(p_30027_.m_128451_(f_149057_));
            return p_30026_;
        }
        RandomSource $$5 = p_30023_.m_213780_();
        if (p_30026_ instanceof TropicalFishGroupData) {
            TropicalFishGroupData $$6 = (TropicalFishGroupData)p_30026_;
            int $$7 = $$6.f_30097_;
            int $$8 = $$6.f_30098_;
            int $$9 = $$6.f_30099_;
            int $$10 = $$6.f_30100_;
        } else if ((double)$$5.m_188501_() < 0.9) {
            int $$11 = Util.m_214667_(f_30007_, $$5);
            int $$12 = $$11 & 0xFF;
            int $$13 = ($$11 & 0xFF00) >> 8;
            int $$14 = ($$11 & 0xFF0000) >> 16;
            int $$15 = ($$11 & 0xFF000000) >> 24;
            p_30026_ = new TropicalFishGroupData(this, $$12, $$13, $$14, $$15);
        } else {
            this.f_30010_ = false;
            $$16 = $$5.m_188503_(2);
            $$17 = $$5.m_188503_(6);
            $$18 = $$5.m_188503_(15);
            $$19 = $$5.m_188503_(15);
        }
        this.m_30056_($$16 | $$17 << 8 | $$18 << 16 | $$19 << 24);
        return p_30026_;
    }

    public static boolean m_218266_(EntityType<TropicalFish> p_218267_, LevelAccessor p_218268_, MobSpawnType p_218269_, BlockPos p_218270_, RandomSource p_218271_) {
        return p_218268_.m_6425_(p_218270_.m_7495_()).m_205070_(FluidTags.f_13131_) && p_218268_.m_8055_(p_218270_.m_7494_()).m_60713_(Blocks.f_49990_) && (p_218268_.m_204166_(p_218270_).m_203656_(BiomeTags.f_215812_) || WaterAnimal.m_218282_(p_218267_, p_218268_, p_218269_, p_218270_, p_218271_));
    }

    static final class Pattern
    extends Enum<Pattern> {
        public static final /* enum */ Pattern KOB = new Pattern(0, 0);
        public static final /* enum */ Pattern SUNSTREAK = new Pattern(0, 1);
        public static final /* enum */ Pattern SNOOPER = new Pattern(0, 2);
        public static final /* enum */ Pattern DASHER = new Pattern(0, 3);
        public static final /* enum */ Pattern BRINELY = new Pattern(0, 4);
        public static final /* enum */ Pattern SPOTTY = new Pattern(0, 5);
        public static final /* enum */ Pattern FLOPPER = new Pattern(1, 0);
        public static final /* enum */ Pattern STRIPEY = new Pattern(1, 1);
        public static final /* enum */ Pattern GLITTER = new Pattern(1, 2);
        public static final /* enum */ Pattern BLOCKFISH = new Pattern(1, 3);
        public static final /* enum */ Pattern BETTY = new Pattern(1, 4);
        public static final /* enum */ Pattern CLAYFISH = new Pattern(1, 5);
        private final int f_30078_;
        private final int f_30079_;
        private static final Pattern[] f_30080_;
        private static final /* synthetic */ Pattern[] $VALUES;

        public static Pattern[] values() {
            return (Pattern[])$VALUES.clone();
        }

        public static Pattern valueOf(String p_30095_) {
            return Enum.valueOf(Pattern.class, p_30095_);
        }

        private Pattern(int p_30086_, int p_30087_) {
            this.f_30078_ = p_30086_;
            this.f_30079_ = p_30087_;
        }

        public int m_30088_() {
            return this.f_30078_;
        }

        public int m_30092_() {
            return this.f_30079_;
        }

        public static String m_30089_(int p_30090_, int p_30091_) {
            return f_30080_[p_30091_ + 6 * p_30090_].m_30093_();
        }

        public String m_30093_() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        private static /* synthetic */ Pattern[] m_149065_() {
            return new Pattern[]{KOB, SUNSTREAK, SNOOPER, DASHER, BRINELY, SPOTTY, FLOPPER, STRIPEY, GLITTER, BLOCKFISH, BETTY, CLAYFISH};
        }

        static {
            $VALUES = Pattern.m_149065_();
            f_30080_ = Pattern.values();
        }
    }

    static class TropicalFishGroupData
    extends AbstractSchoolingFish.SchoolSpawnGroupData {
        final int f_30097_;
        final int f_30098_;
        final int f_30099_;
        final int f_30100_;

        TropicalFishGroupData(TropicalFish p_30102_, int p_30103_, int p_30104_, int p_30105_, int p_30106_) {
            super(p_30102_);
            this.f_30097_ = p_30103_;
            this.f_30098_ = p_30104_;
            this.f_30099_ = p_30105_;
            this.f_30100_ = p_30106_;
        }
    }
}

