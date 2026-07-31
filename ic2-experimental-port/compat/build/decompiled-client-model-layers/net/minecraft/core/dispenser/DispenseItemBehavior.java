/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.core.dispenser;

import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public interface DispenseItemBehavior {
    public static final Logger f_181892_ = LogUtils.getLogger();
    public static final DispenseItemBehavior f_123393_ = (p_123400_, p_123401_) -> p_123401_;

    public ItemStack m_6115_(BlockSource var1, ItemStack var2);

    public static void m_123402_() {
        DispenserBlock.m_52672_(Items.f_42412_, new AbstractProjectileDispenseBehavior(){

            @Override
            protected Projectile m_6895_(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
                Arrow $$3 = new Arrow(p_123407_, p_123408_.m_7096_(), p_123408_.m_7098_(), p_123408_.m_7094_());
                $$3.f_36705_ = AbstractArrow.Pickup.ALLOWED;
                return $$3;
            }
        });
        DispenserBlock.m_52672_(Items.f_42738_, new AbstractProjectileDispenseBehavior(){

            @Override
            protected Projectile m_6895_(Level p_123420_, Position p_123421_, ItemStack p_123422_) {
                Arrow $$3 = new Arrow(p_123420_, p_123421_.m_7096_(), p_123421_.m_7098_(), p_123421_.m_7094_());
                $$3.m_36878_(p_123422_);
                $$3.f_36705_ = AbstractArrow.Pickup.ALLOWED;
                return $$3;
            }
        });
        DispenserBlock.m_52672_(Items.f_42737_, new AbstractProjectileDispenseBehavior(){

            @Override
            protected Projectile m_6895_(Level p_123456_, Position p_123457_, ItemStack p_123458_) {
                SpectralArrow $$3 = new SpectralArrow(p_123456_, p_123457_.m_7096_(), p_123457_.m_7098_(), p_123457_.m_7094_());
                $$3.f_36705_ = AbstractArrow.Pickup.ALLOWED;
                return $$3;
            }
        });
        DispenserBlock.m_52672_(Items.f_42521_, new AbstractProjectileDispenseBehavior(){

            @Override
            protected Projectile m_6895_(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.m_137469_(new ThrownEgg(p_123468_, p_123469_.m_7096_(), p_123469_.m_7098_(), p_123469_.m_7094_()), p_123466_ -> p_123466_.m_37446_(p_123470_));
            }
        });
        DispenserBlock.m_52672_(Items.f_42452_, new AbstractProjectileDispenseBehavior(){

            @Override
            protected Projectile m_6895_(Level p_123476_, Position p_123477_, ItemStack p_123478_) {
                return Util.m_137469_(new Snowball(p_123476_, p_123477_.m_7096_(), p_123477_.m_7098_(), p_123477_.m_7094_()), p_123474_ -> p_123474_.m_37446_(p_123478_));
            }
        });
        DispenserBlock.m_52672_(Items.f_42612_, new AbstractProjectileDispenseBehavior(){

            @Override
            protected Projectile m_6895_(Level p_123485_, Position p_123486_, ItemStack p_123487_) {
                return Util.m_137469_(new ThrownExperienceBottle(p_123485_, p_123486_.m_7096_(), p_123486_.m_7098_(), p_123486_.m_7094_()), p_123483_ -> p_123483_.m_37446_(p_123487_));
            }

            @Override
            protected float m_7101_() {
                return super.m_7101_() * 0.5f;
            }

            @Override
            protected float m_7104_() {
                return super.m_7104_() * 1.25f;
            }
        });
        DispenserBlock.m_52672_(Items.f_42736_, new DispenseItemBehavior(){

            @Override
            public ItemStack m_6115_(BlockSource p_123491_, ItemStack p_123492_) {
                return new AbstractProjectileDispenseBehavior(){

                    @Override
                    protected Projectile m_6895_(Level p_123501_, Position p_123502_, ItemStack p_123503_) {
                        return Util.m_137469_(new ThrownPotion(p_123501_, p_123502_.m_7096_(), p_123502_.m_7098_(), p_123502_.m_7094_()), p_123499_ -> p_123499_.m_37446_(p_123503_));
                    }

                    @Override
                    protected float m_7101_() {
                        return super.m_7101_() * 0.5f;
                    }

                    @Override
                    protected float m_7104_() {
                        return super.m_7104_() * 1.25f;
                    }
                }.m_6115_(p_123491_, p_123492_);
            }
        });
        DispenserBlock.m_52672_(Items.f_42739_, new DispenseItemBehavior(){

            @Override
            public ItemStack m_6115_(BlockSource p_123507_, ItemStack p_123508_) {
                return new AbstractProjectileDispenseBehavior(){

                    @Override
                    protected Projectile m_6895_(Level p_123517_, Position p_123518_, ItemStack p_123519_) {
                        return Util.m_137469_(new ThrownPotion(p_123517_, p_123518_.m_7096_(), p_123518_.m_7098_(), p_123518_.m_7094_()), p_123515_ -> p_123515_.m_37446_(p_123519_));
                    }

                    @Override
                    protected float m_7101_() {
                        return super.m_7101_() * 0.5f;
                    }

                    @Override
                    protected float m_7104_() {
                        return super.m_7104_() * 1.25f;
                    }
                }.m_6115_(p_123507_, p_123508_);
            }
        });
        DefaultDispenseItemBehavior $$0 = new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_123523_, ItemStack p_123524_) {
                Direction $$2 = p_123523_.m_6414_().m_61143_(DispenserBlock.f_52659_);
                EntityType<?> $$3 = ((SpawnEggItem)p_123524_.m_41720_()).m_43228_(p_123524_.m_41783_());
                try {
                    $$3.m_20592_(p_123523_.m_7727_(), p_123524_, null, p_123523_.m_7961_().m_121945_($$2), MobSpawnType.DISPENSER, $$2 != Direction.UP, false);
                }
                catch (Exception $$4) {
                    f_181892_.error("Error while dispensing spawn egg from dispenser at {}", (Object)p_123523_.m_7961_(), (Object)$$4);
                    return ItemStack.f_41583_;
                }
                p_123524_.m_41774_(1);
                p_123523_.m_7727_().m_142346_(null, GameEvent.f_157810_, p_123523_.m_7961_());
                return p_123524_;
            }
        };
        for (SpawnEggItem $$1 : SpawnEggItem.m_43233_()) {
            DispenserBlock.m_52672_($$1, $$0);
        }
        DispenserBlock.m_52672_(Items.f_42650_, new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_123461_, ItemStack p_123462_) {
                Direction $$2 = p_123461_.m_6414_().m_61143_(DispenserBlock.f_52659_);
                BlockPos $$3 = p_123461_.m_7961_().m_121945_($$2);
                ServerLevel $$4 = p_123461_.m_7727_();
                ArmorStand $$5 = new ArmorStand($$4, (double)$$3.m_123341_() + 0.5, $$3.m_123342_(), (double)$$3.m_123343_() + 0.5);
                EntityType.m_20620_($$4, null, $$5, p_123462_.m_41783_());
                $$5.m_146922_($$2.m_122435_());
                $$4.m_7967_($$5);
                p_123462_.m_41774_(1);
                return p_123462_;
            }
        });
        DispenserBlock.m_52672_(Items.f_42450_, new OptionalDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_123529_, ItemStack p_123530_) {
                BlockPos $$2 = p_123529_.m_7961_().m_121945_(p_123529_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                List<LivingEntity> $$3 = p_123529_.m_7727_().m_6443_(LivingEntity.class, new AABB($$2), p_123527_ -> {
                    if (p_123527_ instanceof Saddleable) {
                        Saddleable $$1 = (Saddleable)((Object)p_123527_);
                        return !$$1.m_6254_() && $$1.m_6741_();
                    }
                    return false;
                });
                if (!$$3.isEmpty()) {
                    ((Saddleable)((Object)$$3.get(0))).m_5853_(SoundSource.BLOCKS);
                    p_123530_.m_41774_(1);
                    this.m_123573_(true);
                    return p_123530_;
                }
                return super.m_7498_(p_123529_, p_123530_);
            }
        });
        OptionalDispenseItemBehavior $$2 = new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack m_7498_(BlockSource p_123535_, ItemStack p_123536_) {
                BlockPos $$2 = p_123535_.m_7961_().m_121945_(p_123535_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                List<AbstractHorse> $$3 = p_123535_.m_7727_().m_6443_(AbstractHorse.class, new AABB($$2), p_123533_ -> p_123533_.m_6084_() && p_123533_.m_7482_());
                for (AbstractHorse $$4 : $$3) {
                    if (!$$4.m_6010_(p_123536_) || $$4.m_7481_() || !$$4.m_30614_()) continue;
                    $$4.m_141942_(401).m_142104_(p_123536_.m_41620_(1));
                    this.m_123573_(true);
                    return p_123536_;
                }
                return super.m_7498_(p_123535_, p_123536_);
            }
        };
        DispenserBlock.m_52672_(Items.f_42654_, $$2);
        DispenserBlock.m_52672_(Items.f_42651_, $$2);
        DispenserBlock.m_52672_(Items.f_42652_, $$2);
        DispenserBlock.m_52672_(Items.f_42653_, $$2);
        DispenserBlock.m_52672_(Items.f_42130_, $$2);
        DispenserBlock.m_52672_(Items.f_42131_, $$2);
        DispenserBlock.m_52672_(Items.f_42139_, $$2);
        DispenserBlock.m_52672_(Items.f_42141_, $$2);
        DispenserBlock.m_52672_(Items.f_42142_, $$2);
        DispenserBlock.m_52672_(Items.f_42198_, $$2);
        DispenserBlock.m_52672_(Items.f_42137_, $$2);
        DispenserBlock.m_52672_(Items.f_42143_, $$2);
        DispenserBlock.m_52672_(Items.f_42133_, $$2);
        DispenserBlock.m_52672_(Items.f_42138_, $$2);
        DispenserBlock.m_52672_(Items.f_42135_, $$2);
        DispenserBlock.m_52672_(Items.f_42132_, $$2);
        DispenserBlock.m_52672_(Items.f_42136_, $$2);
        DispenserBlock.m_52672_(Items.f_42140_, $$2);
        DispenserBlock.m_52672_(Items.f_42197_, $$2);
        DispenserBlock.m_52672_(Items.f_42134_, $$2);
        DispenserBlock.m_52672_(Items.f_42009_, new OptionalDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_123541_, ItemStack p_123542_) {
                BlockPos $$2 = p_123541_.m_7961_().m_121945_(p_123541_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                List<AbstractChestedHorse> $$3 = p_123541_.m_7727_().m_6443_(AbstractChestedHorse.class, new AABB($$2), p_123539_ -> p_123539_.m_6084_() && !p_123539_.m_30502_());
                for (AbstractChestedHorse $$4 : $$3) {
                    if (!$$4.m_30614_() || !$$4.m_141942_(499).m_142104_(p_123542_)) continue;
                    p_123542_.m_41774_(1);
                    this.m_123573_(true);
                    return p_123542_;
                }
                return super.m_7498_(p_123541_, p_123542_);
            }
        });
        DispenserBlock.m_52672_(Items.f_42688_, new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_123547_, ItemStack p_123548_) {
                Direction $$2 = p_123547_.m_6414_().m_61143_(DispenserBlock.f_52659_);
                FireworkRocketEntity $$3 = new FireworkRocketEntity((Level)p_123547_.m_7727_(), p_123548_, p_123547_.m_7096_(), p_123547_.m_7098_(), p_123547_.m_7096_(), true);
                DispenseItemBehavior.m_123395_(p_123547_, $$3, $$2);
                $$3.m_6686_($$2.m_122429_(), $$2.m_122430_(), $$2.m_122431_(), 0.5f, 1.0f);
                p_123547_.m_7727_().m_7967_($$3);
                p_123548_.m_41774_(1);
                return p_123548_;
            }

            @Override
            protected void m_6823_(BlockSource p_123545_) {
                p_123545_.m_7727_().m_46796_(1004, p_123545_.m_7961_(), 0);
            }
        });
        DispenserBlock.m_52672_(Items.f_42613_, new DefaultDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_123556_, ItemStack p_123557_) {
                Direction $$2 = p_123556_.m_6414_().m_61143_(DispenserBlock.f_52659_);
                Position $$3 = DispenserBlock.m_52720_(p_123556_);
                double $$4 = $$3.m_7096_() + (double)((float)$$2.m_122429_() * 0.3f);
                double $$5 = $$3.m_7098_() + (double)((float)$$2.m_122430_() * 0.3f);
                double $$6 = $$3.m_7094_() + (double)((float)$$2.m_122431_() * 0.3f);
                ServerLevel $$7 = p_123556_.m_7727_();
                RandomSource $$8 = $$7.f_46441_;
                double $$9 = $$8.m_216328_($$2.m_122429_(), 0.11485000000000001);
                double $$10 = $$8.m_216328_($$2.m_122430_(), 0.11485000000000001);
                double $$11 = $$8.m_216328_($$2.m_122431_(), 0.11485000000000001);
                SmallFireball $$12 = new SmallFireball($$7, $$4, $$5, $$6, $$9, $$10, $$11);
                $$7.m_7967_(Util.m_137469_($$12, p_123552_ -> p_123552_.m_37010_(p_123557_)));
                p_123557_.m_41774_(1);
                return p_123557_;
            }

            @Override
            protected void m_6823_(BlockSource p_123554_) {
                p_123554_.m_7727_().m_46796_(1018, p_123554_.m_7961_(), 0);
            }
        });
        DispenserBlock.m_52672_(Items.f_42453_, new BoatDispenseItemBehavior(Boat.Type.OAK));
        DispenserBlock.m_52672_(Items.f_42742_, new BoatDispenseItemBehavior(Boat.Type.SPRUCE));
        DispenserBlock.m_52672_(Items.f_42743_, new BoatDispenseItemBehavior(Boat.Type.BIRCH));
        DispenserBlock.m_52672_(Items.f_42744_, new BoatDispenseItemBehavior(Boat.Type.JUNGLE));
        DispenserBlock.m_52672_(Items.f_42746_, new BoatDispenseItemBehavior(Boat.Type.DARK_OAK));
        DispenserBlock.m_52672_(Items.f_42745_, new BoatDispenseItemBehavior(Boat.Type.ACACIA));
        DispenserBlock.m_52672_(Items.f_220204_, new BoatDispenseItemBehavior(Boat.Type.MANGROVE));
        DispenserBlock.m_52672_(Items.f_220207_, new BoatDispenseItemBehavior(Boat.Type.OAK, true));
        DispenserBlock.m_52672_(Items.f_220208_, new BoatDispenseItemBehavior(Boat.Type.SPRUCE, true));
        DispenserBlock.m_52672_(Items.f_220200_, new BoatDispenseItemBehavior(Boat.Type.BIRCH, true));
        DispenserBlock.m_52672_(Items.f_220201_, new BoatDispenseItemBehavior(Boat.Type.JUNGLE, true));
        DispenserBlock.m_52672_(Items.f_220203_, new BoatDispenseItemBehavior(Boat.Type.DARK_OAK, true));
        DispenserBlock.m_52672_(Items.f_220202_, new BoatDispenseItemBehavior(Boat.Type.ACACIA, true));
        DispenserBlock.m_52672_(Items.f_220205_, new BoatDispenseItemBehavior(Boat.Type.MANGROVE, true));
        DefaultDispenseItemBehavior $$3 = new DefaultDispenseItemBehavior(){
            private final DefaultDispenseItemBehavior f_123558_ = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack m_7498_(BlockSource p_123561_, ItemStack p_123562_) {
                DispensibleContainerItem $$2 = (DispensibleContainerItem)((Object)p_123562_.m_41720_());
                BlockPos $$3 = p_123561_.m_7961_().m_121945_(p_123561_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                ServerLevel $$4 = p_123561_.m_7727_();
                if ($$2.m_142073_(null, $$4, $$3, null)) {
                    $$2.m_142131_(null, $$4, p_123562_, $$3);
                    return new ItemStack(Items.f_42446_);
                }
                return this.f_123558_.m_6115_(p_123561_, p_123562_);
            }
        };
        DispenserBlock.m_52672_(Items.f_42448_, $$3);
        DispenserBlock.m_52672_(Items.f_42447_, $$3);
        DispenserBlock.m_52672_(Items.f_151055_, $$3);
        DispenserBlock.m_52672_(Items.f_42457_, $$3);
        DispenserBlock.m_52672_(Items.f_42458_, $$3);
        DispenserBlock.m_52672_(Items.f_42456_, $$3);
        DispenserBlock.m_52672_(Items.f_42459_, $$3);
        DispenserBlock.m_52672_(Items.f_151057_, $$3);
        DispenserBlock.m_52672_(Items.f_220210_, $$3);
        DispenserBlock.m_52672_(Items.f_42446_, new DefaultDispenseItemBehavior(){
            private final DefaultDispenseItemBehavior f_123563_ = new DefaultDispenseItemBehavior();

            /*
             * WARNING - void declaration
             */
            @Override
            public ItemStack m_7498_(BlockSource p_123566_, ItemStack p_123567_) {
                void $$8;
                ItemStack $$6;
                BlockPos $$3;
                ServerLevel $$2 = p_123566_.m_7727_();
                BlockState $$4 = $$2.m_8055_($$3 = p_123566_.m_7961_().m_121945_(p_123566_.m_6414_().m_61143_(DispenserBlock.f_52659_)));
                Block $$5 = $$4.m_60734_();
                if ($$5 instanceof BucketPickup) {
                    $$6 = ((BucketPickup)((Object)$$5)).m_142598_($$2, $$3, $$4);
                    if ($$6.m_41619_()) {
                        return super.m_7498_(p_123566_, p_123567_);
                    }
                } else {
                    return super.m_7498_(p_123566_, p_123567_);
                }
                $$2.m_142346_(null, GameEvent.f_157816_, $$3);
                Item $$7 = $$6.m_41720_();
                p_123567_.m_41774_(1);
                if (p_123567_.m_41619_()) {
                    return new ItemStack((ItemLike)$$8);
                }
                if (((DispenserBlockEntity)p_123566_.m_8118_()).m_59237_(new ItemStack((ItemLike)$$8)) < 0) {
                    this.f_123563_.m_6115_(p_123566_, new ItemStack((ItemLike)$$8));
                }
                return p_123567_;
            }
        });
        DispenserBlock.m_52672_(Items.f_42409_, new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack m_7498_(BlockSource p_123412_, ItemStack p_123413_) {
                ServerLevel $$2 = p_123412_.m_7727_();
                this.m_123573_(true);
                Direction $$3 = p_123412_.m_6414_().m_61143_(DispenserBlock.f_52659_);
                BlockPos $$4 = p_123412_.m_7961_().m_121945_($$3);
                BlockState $$5 = $$2.m_8055_($$4);
                if (BaseFireBlock.m_49255_($$2, $$4, $$3)) {
                    $$2.m_46597_($$4, BaseFireBlock.m_49245_($$2, $$4));
                    $$2.m_142346_(null, GameEvent.f_157797_, $$4);
                } else if (CampfireBlock.m_51321_($$5) || CandleBlock.m_152845_($$5) || CandleCakeBlock.m_152910_($$5)) {
                    $$2.m_46597_($$4, (BlockState)$$5.m_61124_(BlockStateProperties.f_61443_, true));
                    $$2.m_142346_(null, GameEvent.f_157792_, $$4);
                } else if ($$5.m_60734_() instanceof TntBlock) {
                    TntBlock.m_57433_($$2, $$4);
                    $$2.m_7471_($$4, false);
                } else {
                    this.m_123573_(false);
                }
                if (this.m_123570_() && p_123413_.m_220157_(1, $$2.f_46441_, null)) {
                    p_123413_.m_41764_(0);
                }
                return p_123413_;
            }
        });
        DispenserBlock.m_52672_(Items.f_42499_, new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack m_7498_(BlockSource p_123416_, ItemStack p_123417_) {
                this.m_123573_(true);
                ServerLevel $$2 = p_123416_.m_7727_();
                BlockPos $$3 = p_123416_.m_7961_().m_121945_(p_123416_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                if (BoneMealItem.m_40627_(p_123417_, $$2, $$3) || BoneMealItem.m_40631_(p_123417_, $$2, $$3, null)) {
                    if (!$$2.f_46443_) {
                        $$2.m_46796_(1505, $$3, 0);
                    }
                } else {
                    this.m_123573_(false);
                }
                return p_123417_;
            }
        });
        DispenserBlock.m_52672_(Blocks.f_50077_, new DefaultDispenseItemBehavior(){

            @Override
            protected ItemStack m_7498_(BlockSource p_123425_, ItemStack p_123426_) {
                ServerLevel $$2 = p_123425_.m_7727_();
                BlockPos $$3 = p_123425_.m_7961_().m_121945_(p_123425_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                PrimedTnt $$4 = new PrimedTnt($$2, (double)$$3.m_123341_() + 0.5, $$3.m_123342_(), (double)$$3.m_123343_() + 0.5, null);
                $$2.m_7967_($$4);
                $$2.m_6263_(null, $$4.m_20185_(), $$4.m_20186_(), $$4.m_20189_(), SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0f, 1.0f);
                $$2.m_142346_(null, GameEvent.f_157810_, $$3);
                p_123426_.m_41774_(1);
                return p_123426_;
            }
        });
        OptionalDispenseItemBehavior $$4 = new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack m_7498_(BlockSource p_123429_, ItemStack p_123430_) {
                this.m_123573_(ArmorItem.m_40398_(p_123429_, p_123430_));
                return p_123430_;
            }
        };
        DispenserBlock.m_52672_(Items.f_42682_, $$4);
        DispenserBlock.m_52672_(Items.f_42681_, $$4);
        DispenserBlock.m_52672_(Items.f_42683_, $$4);
        DispenserBlock.m_52672_(Items.f_42678_, $$4);
        DispenserBlock.m_52672_(Items.f_42680_, $$4);
        DispenserBlock.m_52672_(Items.f_42679_, new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack m_7498_(BlockSource p_123433_, ItemStack p_123434_) {
                ServerLevel $$2 = p_123433_.m_7727_();
                Direction $$3 = p_123433_.m_6414_().m_61143_(DispenserBlock.f_52659_);
                BlockPos $$4 = p_123433_.m_7961_().m_121945_($$3);
                if ($$2.m_46859_($$4) && WitherSkullBlock.m_58267_($$2, $$4, p_123434_)) {
                    $$2.m_7731_($$4, (BlockState)Blocks.f_50312_.m_49966_().m_61124_(SkullBlock.f_56314_, $$3.m_122434_() == Direction.Axis.Y ? 0 : $$3.m_122424_().m_122416_() * 4), 3);
                    $$2.m_142346_(null, GameEvent.f_157797_, $$4);
                    BlockEntity $$5 = $$2.m_7702_($$4);
                    if ($$5 instanceof SkullBlockEntity) {
                        WitherSkullBlock.m_58255_($$2, $$4, (SkullBlockEntity)$$5);
                    }
                    p_123434_.m_41774_(1);
                    this.m_123573_(true);
                } else {
                    this.m_123573_(ArmorItem.m_40398_(p_123433_, p_123434_));
                }
                return p_123434_;
            }
        });
        DispenserBlock.m_52672_(Blocks.f_50143_, new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack m_7498_(BlockSource p_123437_, ItemStack p_123438_) {
                ServerLevel $$2 = p_123437_.m_7727_();
                BlockPos $$3 = p_123437_.m_7961_().m_121945_(p_123437_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                CarvedPumpkinBlock $$4 = (CarvedPumpkinBlock)Blocks.f_50143_;
                if ($$2.m_46859_($$3) && $$4.m_51381_($$2, $$3)) {
                    if (!$$2.f_46443_) {
                        $$2.m_7731_($$3, $$4.m_49966_(), 3);
                        $$2.m_142346_(null, GameEvent.f_157797_, $$3);
                    }
                    p_123438_.m_41774_(1);
                    this.m_123573_(true);
                } else {
                    this.m_123573_(ArmorItem.m_40398_(p_123437_, p_123438_));
                }
                return p_123438_;
            }
        });
        DispenserBlock.m_52672_(Blocks.f_50456_.m_5456_(), new ShulkerBoxDispenseBehavior());
        for (DyeColor $$5 : DyeColor.values()) {
            DispenserBlock.m_52672_(ShulkerBoxBlock.m_56190_($$5).m_5456_(), new ShulkerBoxDispenseBehavior());
        }
        DispenserBlock.m_52672_(Items.f_42590_.m_5456_(), new OptionalDispenseItemBehavior(){
            private final DefaultDispenseItemBehavior f_123439_ = new DefaultDispenseItemBehavior();

            private ItemStack m_123446_(BlockSource p_123447_, ItemStack p_123448_, ItemStack p_123449_) {
                p_123448_.m_41774_(1);
                if (p_123448_.m_41619_()) {
                    p_123447_.m_7727_().m_142346_(null, GameEvent.f_157816_, p_123447_.m_7961_());
                    return p_123449_.m_41777_();
                }
                if (((DispenserBlockEntity)p_123447_.m_8118_()).m_59237_(p_123449_.m_41777_()) < 0) {
                    this.f_123439_.m_6115_(p_123447_, p_123449_.m_41777_());
                }
                return p_123448_;
            }

            @Override
            public ItemStack m_7498_(BlockSource p_123444_, ItemStack p_123445_) {
                this.m_123573_(false);
                ServerLevel $$2 = p_123444_.m_7727_();
                BlockPos $$3 = p_123444_.m_7961_().m_121945_(p_123444_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                BlockState $$4 = $$2.m_8055_($$3);
                if ($$4.m_204338_(BlockTags.f_13072_, p_123442_ -> p_123442_.m_61138_(BeehiveBlock.f_49564_) && p_123442_.m_60734_() instanceof BeehiveBlock) && $$4.m_61143_(BeehiveBlock.f_49564_) >= 5) {
                    ((BeehiveBlock)$$4.m_60734_()).m_49594_($$2, $$4, $$3, null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
                    this.m_123573_(true);
                    return this.m_123446_(p_123444_, p_123445_, new ItemStack(Items.f_42787_));
                }
                if ($$2.m_6425_($$3).m_205070_(FluidTags.f_13131_)) {
                    this.m_123573_(true);
                    return this.m_123446_(p_123444_, p_123445_, PotionUtils.m_43549_(new ItemStack(Items.f_42589_), Potions.f_43599_));
                }
                return super.m_7498_(p_123444_, p_123445_);
            }
        });
        DispenserBlock.m_52672_(Items.f_42054_, new OptionalDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_123452_, ItemStack p_123453_) {
                Direction $$2 = p_123452_.m_6414_().m_61143_(DispenserBlock.f_52659_);
                BlockPos $$3 = p_123452_.m_7961_().m_121945_($$2);
                ServerLevel $$4 = p_123452_.m_7727_();
                BlockState $$5 = $$4.m_8055_($$3);
                this.m_123573_(true);
                if ($$5.m_60713_(Blocks.f_50724_)) {
                    if ($$5.m_61143_(RespawnAnchorBlock.f_55833_) != 4) {
                        RespawnAnchorBlock.m_55855_($$4, $$3, $$5);
                        p_123453_.m_41774_(1);
                    } else {
                        this.m_123573_(false);
                    }
                    return p_123453_;
                }
                return super.m_7498_(p_123452_, p_123453_);
            }
        });
        DispenserBlock.m_52672_(Items.f_42574_.m_5456_(), new ShearsDispenseItemBehavior());
        DispenserBlock.m_52672_(Items.f_42784_, new OptionalDispenseItemBehavior(){

            @Override
            public ItemStack m_7498_(BlockSource p_175747_, ItemStack p_175748_) {
                BlockPos $$2 = p_175747_.m_7961_().m_121945_(p_175747_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                ServerLevel $$3 = p_175747_.m_7727_();
                BlockState $$4 = $$3.m_8055_($$2);
                Optional<BlockState> $$5 = HoneycombItem.m_150878_($$4);
                if ($$5.isPresent()) {
                    $$3.m_46597_($$2, $$5.get());
                    $$3.m_46796_(3003, $$2, 0);
                    p_175748_.m_41774_(1);
                    this.m_123573_(true);
                    return p_175748_;
                }
                return super.m_7498_(p_175747_, p_175748_);
            }
        });
        DispenserBlock.m_52672_(Items.f_42589_, new DefaultDispenseItemBehavior(){
            private final DefaultDispenseItemBehavior f_235893_ = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack m_7498_(BlockSource p_235896_, ItemStack p_235897_) {
                if (PotionUtils.m_43579_(p_235897_) != Potions.f_43599_) {
                    return this.f_235893_.m_6115_(p_235896_, p_235897_);
                }
                ServerLevel $$2 = p_235896_.m_7727_();
                BlockPos $$3 = p_235896_.m_7961_();
                BlockPos $$4 = p_235896_.m_7961_().m_121945_(p_235896_.m_6414_().m_61143_(DispenserBlock.f_52659_));
                if ($$2.m_8055_($$4).m_204336_(BlockTags.f_215828_)) {
                    if (!$$2.f_46443_) {
                        for (int $$5 = 0; $$5 < 5; ++$$5) {
                            $$2.m_8767_(ParticleTypes.f_123769_, (double)$$3.m_123341_() + $$2.f_46441_.m_188500_(), $$3.m_123342_() + 1, (double)$$3.m_123343_() + $$2.f_46441_.m_188500_(), 1, 0.0, 0.0, 0.0, 1.0);
                        }
                    }
                    $$2.m_5594_(null, $$3, SoundEvents.f_11769_, SoundSource.BLOCKS, 1.0f, 1.0f);
                    $$2.m_142346_(null, GameEvent.f_157769_, $$3);
                    $$2.m_46597_($$4, Blocks.f_220864_.m_49966_());
                    return new ItemStack(Items.f_42590_);
                }
                return this.f_235893_.m_6115_(p_235896_, p_235897_);
            }
        });
    }

    public static void m_123395_(BlockSource p_123396_, Entity p_123397_, Direction p_123398_) {
        p_123397_.m_6034_(p_123396_.m_7096_() + (double)p_123398_.m_122429_() * (0.5000099999997474 - (double)p_123397_.m_20205_() / 2.0), p_123396_.m_7098_() + (double)p_123398_.m_122430_() * (0.5000099999997474 - (double)p_123397_.m_20206_() / 2.0) - (double)p_123397_.m_20206_() / 2.0, p_123396_.m_7094_() + (double)p_123398_.m_122431_() * (0.5000099999997474 - (double)p_123397_.m_20205_() / 2.0));
    }
}

