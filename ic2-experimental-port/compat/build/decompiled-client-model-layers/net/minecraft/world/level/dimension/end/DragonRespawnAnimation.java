/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public abstract class DragonRespawnAnimation
extends Enum<DragonRespawnAnimation> {
    public static final /* enum */ DragonRespawnAnimation START = new DragonRespawnAnimation(){

        @Override
        public void m_6363_(ServerLevel p_64017_, EndDragonFight p_64018_, List<EndCrystal> p_64019_, int p_64020_, BlockPos p_64021_) {
            BlockPos $$5 = new BlockPos(0, 128, 0);
            for (EndCrystal $$6 : p_64019_) {
                $$6.m_31052_($$5);
            }
            p_64018_.m_64087_(PREPARING_TO_SUMMON_PILLARS);
        }
    };
    public static final /* enum */ DragonRespawnAnimation PREPARING_TO_SUMMON_PILLARS = new DragonRespawnAnimation(){

        @Override
        public void m_6363_(ServerLevel p_64026_, EndDragonFight p_64027_, List<EndCrystal> p_64028_, int p_64029_, BlockPos p_64030_) {
            if (p_64029_ < 100) {
                if (p_64029_ == 0 || p_64029_ == 50 || p_64029_ == 51 || p_64029_ == 52 || p_64029_ >= 95) {
                    p_64026_.m_46796_(3001, new BlockPos(0, 128, 0), 0);
                }
            } else {
                p_64027_.m_64087_(SUMMONING_PILLARS);
            }
        }
    };
    public static final /* enum */ DragonRespawnAnimation SUMMONING_PILLARS = new DragonRespawnAnimation(){

        @Override
        public void m_6363_(ServerLevel p_64035_, EndDragonFight p_64036_, List<EndCrystal> p_64037_, int p_64038_, BlockPos p_64039_) {
            boolean $$7;
            int $$5 = 40;
            boolean $$6 = p_64038_ % 40 == 0;
            boolean bl = $$7 = p_64038_ % 40 == 39;
            if ($$6 || $$7) {
                int $$9 = p_64038_ / 40;
                List<SpikeFeature.EndSpike> $$8 = SpikeFeature.m_66858_(p_64035_);
                if ($$9 < $$8.size()) {
                    SpikeFeature.EndSpike $$10 = $$8.get($$9);
                    if ($$6) {
                        for (EndCrystal $$11 : p_64037_) {
                            $$11.m_31052_(new BlockPos($$10.m_66886_(), $$10.m_66899_() + 1, $$10.m_66893_()));
                        }
                    } else {
                        int $$12 = 10;
                        for (BlockPos $$13 : BlockPos.m_121940_(new BlockPos($$10.m_66886_() - 10, $$10.m_66899_() - 10, $$10.m_66893_() - 10), new BlockPos($$10.m_66886_() + 10, $$10.m_66899_() + 10, $$10.m_66893_() + 10))) {
                            p_64035_.m_7471_($$13, false);
                        }
                        p_64035_.m_46511_(null, (float)$$10.m_66886_() + 0.5f, $$10.m_66899_(), (float)$$10.m_66893_() + 0.5f, 5.0f, Explosion.BlockInteraction.DESTROY);
                        SpikeConfiguration $$14 = new SpikeConfiguration(true, (List<SpikeFeature.EndSpike>)ImmutableList.of((Object)$$10), new BlockPos(0, 128, 0));
                        Feature.f_65732_.m_225028_($$14, p_64035_, p_64035_.m_7726_().m_8481_(), RandomSource.m_216327_(), new BlockPos($$10.m_66886_(), 45, $$10.m_66893_()));
                    }
                } else if ($$6) {
                    p_64036_.m_64087_(SUMMONING_DRAGON);
                }
            }
        }
    };
    public static final /* enum */ DragonRespawnAnimation SUMMONING_DRAGON = new DragonRespawnAnimation(){

        @Override
        public void m_6363_(ServerLevel p_64044_, EndDragonFight p_64045_, List<EndCrystal> p_64046_, int p_64047_, BlockPos p_64048_) {
            if (p_64047_ >= 100) {
                p_64045_.m_64087_(END);
                p_64045_.m_64101_();
                for (EndCrystal $$5 : p_64046_) {
                    $$5.m_31052_(null);
                    p_64044_.m_46511_($$5, $$5.m_20185_(), $$5.m_20186_(), $$5.m_20189_(), 6.0f, Explosion.BlockInteraction.NONE);
                    $$5.m_146870_();
                }
            } else if (p_64047_ >= 80) {
                p_64044_.m_46796_(3001, new BlockPos(0, 128, 0), 0);
            } else if (p_64047_ == 0) {
                for (EndCrystal $$6 : p_64046_) {
                    $$6.m_31052_(new BlockPos(0, 128, 0));
                }
            } else if (p_64047_ < 5) {
                p_64044_.m_46796_(3001, new BlockPos(0, 128, 0), 0);
            }
        }
    };
    public static final /* enum */ DragonRespawnAnimation END = new DragonRespawnAnimation(){

        @Override
        public void m_6363_(ServerLevel p_64053_, EndDragonFight p_64054_, List<EndCrystal> p_64055_, int p_64056_, BlockPos p_64057_) {
        }
    };
    private static final /* synthetic */ DragonRespawnAnimation[] $VALUES;

    public static DragonRespawnAnimation[] values() {
        return (DragonRespawnAnimation[])$VALUES.clone();
    }

    public static DragonRespawnAnimation valueOf(String p_64011_) {
        return Enum.valueOf(DragonRespawnAnimation.class, p_64011_);
    }

    public abstract void m_6363_(ServerLevel var1, EndDragonFight var2, List<EndCrystal> var3, int var4, BlockPos var5);

    private static /* synthetic */ DragonRespawnAnimation[] m_156734_() {
        return new DragonRespawnAnimation[]{START, PREPARING_TO_SUMMON_PILLARS, SUMMONING_PILLARS, SUMMONING_DRAGON, END};
    }

    static {
        $VALUES = DragonRespawnAnimation.m_156734_();
    }
}

