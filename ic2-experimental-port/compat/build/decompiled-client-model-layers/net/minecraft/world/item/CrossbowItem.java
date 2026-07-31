/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import com.google.common.collect.Lists;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CrossbowItem
extends ProjectileWeaponItem
implements Vanishable {
    private static final String f_150790_ = "Charged";
    private static final String f_150791_ = "ChargedProjectiles";
    private static final int f_150792_ = 25;
    public static final int f_150789_ = 8;
    private boolean f_40847_ = false;
    private boolean f_40848_ = false;
    private static final float f_150793_ = 0.2f;
    private static final float f_150794_ = 0.5f;
    private static final float f_150795_ = 3.15f;
    private static final float f_150796_ = 1.6f;

    public CrossbowItem(Item.Properties p_40850_) {
        super(p_40850_);
    }

    @Override
    public Predicate<ItemStack> m_6442_() {
        return f_43006_;
    }

    @Override
    public Predicate<ItemStack> m_6437_() {
        return f_43005_;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_40920_, Player p_40921_, InteractionHand p_40922_) {
        ItemStack $$3 = p_40921_.m_21120_(p_40922_);
        if (CrossbowItem.m_40932_($$3)) {
            CrossbowItem.m_40887_(p_40920_, p_40921_, p_40922_, $$3, CrossbowItem.m_40945_($$3), 1.0f);
            CrossbowItem.m_40884_($$3, false);
            return InteractionResultHolder.m_19096_($$3);
        }
        if (!p_40921_.m_6298_($$3).m_41619_()) {
            if (!CrossbowItem.m_40932_($$3)) {
                this.f_40847_ = false;
                this.f_40848_ = false;
                p_40921_.m_6672_(p_40922_);
            }
            return InteractionResultHolder.m_19096_($$3);
        }
        return InteractionResultHolder.m_19100_($$3);
    }

    private static float m_40945_(ItemStack p_40946_) {
        if (CrossbowItem.m_40871_(p_40946_, Items.f_42688_)) {
            return 1.6f;
        }
        return 3.15f;
    }

    @Override
    public void m_5551_(ItemStack p_40875_, Level p_40876_, LivingEntity p_40877_, int p_40878_) {
        int $$4 = this.m_8105_(p_40875_) - p_40878_;
        float $$5 = CrossbowItem.m_40853_($$4, p_40875_);
        if ($$5 >= 1.0f && !CrossbowItem.m_40932_(p_40875_) && CrossbowItem.m_40859_(p_40877_, p_40875_)) {
            CrossbowItem.m_40884_(p_40875_, true);
            SoundSource $$6 = p_40877_ instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            p_40876_.m_6263_(null, p_40877_.m_20185_(), p_40877_.m_20186_(), p_40877_.m_20189_(), SoundEvents.f_11841_, $$6, 1.0f, 1.0f / (p_40876_.m_213780_().m_188501_() * 0.5f + 1.0f) + 0.2f);
        }
    }

    private static boolean m_40859_(LivingEntity p_40860_, ItemStack p_40861_) {
        int $$2 = EnchantmentHelper.m_44843_(Enchantments.f_44959_, p_40861_);
        int $$3 = $$2 == 0 ? 1 : 3;
        boolean $$4 = p_40860_ instanceof Player && ((Player)p_40860_).m_150110_().f_35937_;
        ItemStack $$5 = p_40860_.m_6298_(p_40861_);
        ItemStack $$6 = $$5.m_41777_();
        for (int $$7 = 0; $$7 < $$3; ++$$7) {
            if ($$7 > 0) {
                $$5 = $$6.m_41777_();
            }
            if ($$5.m_41619_() && $$4) {
                $$5 = new ItemStack(Items.f_42412_);
                $$6 = $$5.m_41777_();
            }
            if (CrossbowItem.m_40862_(p_40860_, p_40861_, $$5, $$7 > 0, $$4)) continue;
            return false;
        }
        return true;
    }

    private static boolean m_40862_(LivingEntity p_40863_, ItemStack p_40864_, ItemStack p_40865_, boolean p_40866_, boolean p_40867_) {
        ItemStack $$7;
        boolean $$5;
        if (p_40865_.m_41619_()) {
            return false;
        }
        boolean bl = $$5 = p_40867_ && p_40865_.m_41720_() instanceof ArrowItem;
        if (!($$5 || p_40867_ || p_40866_)) {
            ItemStack $$6 = p_40865_.m_41620_(1);
            if (p_40865_.m_41619_() && p_40863_ instanceof Player) {
                ((Player)p_40863_).m_150109_().m_36057_(p_40865_);
            }
        } else {
            $$7 = p_40865_.m_41777_();
        }
        CrossbowItem.m_40928_(p_40864_, $$7);
        return true;
    }

    public static boolean m_40932_(ItemStack p_40933_) {
        CompoundTag $$1 = p_40933_.m_41783_();
        return $$1 != null && $$1.m_128471_(f_150790_);
    }

    public static void m_40884_(ItemStack p_40885_, boolean p_40886_) {
        CompoundTag $$2 = p_40885_.m_41784_();
        $$2.m_128379_(f_150790_, p_40886_);
    }

    private static void m_40928_(ItemStack p_40929_, ItemStack p_40930_) {
        ListTag $$4;
        CompoundTag $$2 = p_40929_.m_41784_();
        if ($$2.m_128425_(f_150791_, 9)) {
            ListTag $$3 = $$2.m_128437_(f_150791_, 10);
        } else {
            $$4 = new ListTag();
        }
        CompoundTag $$5 = new CompoundTag();
        p_40930_.m_41739_($$5);
        $$4.add($$5);
        $$2.m_128365_(f_150791_, $$4);
    }

    private static List<ItemStack> m_40941_(ItemStack p_40942_) {
        ListTag $$3;
        ArrayList $$1 = Lists.newArrayList();
        CompoundTag $$2 = p_40942_.m_41783_();
        if ($$2 != null && $$2.m_128425_(f_150791_, 9) && ($$3 = $$2.m_128437_(f_150791_, 10)) != null) {
            for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
                CompoundTag $$5 = $$3.m_128728_($$4);
                $$1.add(ItemStack.m_41712_($$5));
            }
        }
        return $$1;
    }

    private static void m_40943_(ItemStack p_40944_) {
        CompoundTag $$1 = p_40944_.m_41783_();
        if ($$1 != null) {
            ListTag $$2 = $$1.m_128437_(f_150791_, 9);
            $$2.clear();
            $$1.m_128365_(f_150791_, $$2);
        }
    }

    public static boolean m_40871_(ItemStack p_40872_, Item p_40873_) {
        return CrossbowItem.m_40941_(p_40872_).stream().anyMatch(p_40870_ -> p_40870_.m_150930_(p_40873_));
    }

    private static void m_40894_(Level p_40895_, LivingEntity p_40896_, InteractionHand p_40897_, ItemStack p_40898_, ItemStack p_40899_, float p_40900_, boolean p_40901_, float p_40902_, float p_40903_, float p_40904_) {
        AbstractArrow $$12;
        if (p_40895_.f_46443_) {
            return;
        }
        boolean $$10 = p_40899_.m_150930_(Items.f_42688_);
        if ($$10) {
            FireworkRocketEntity $$11 = new FireworkRocketEntity(p_40895_, p_40899_, p_40896_, p_40896_.m_20185_(), p_40896_.m_20188_() - (double)0.15f, p_40896_.m_20189_(), true);
        } else {
            $$12 = CrossbowItem.m_40914_(p_40895_, p_40896_, p_40898_, p_40899_);
            if (p_40901_ || p_40904_ != 0.0f) {
                $$12.f_36705_ = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
        }
        if (p_40896_ instanceof CrossbowAttackMob) {
            CrossbowAttackMob $$13 = (CrossbowAttackMob)((Object)p_40896_);
            $$13.m_5811_($$13.m_5448_(), p_40898_, $$12, p_40904_);
        } else {
            Vec3 $$14 = p_40896_.m_20289_(1.0f);
            Quaternion $$15 = new Quaternion(new Vector3f($$14), p_40904_, true);
            Vec3 $$16 = p_40896_.m_20252_(1.0f);
            Vector3f $$17 = new Vector3f($$16);
            $$17.m_122251_($$15);
            ((Projectile)$$12).m_6686_($$17.m_122239_(), $$17.m_122260_(), $$17.m_122269_(), p_40902_, p_40903_);
        }
        p_40898_.m_41622_($$10 ? 3 : 1, p_40896_, p_40858_ -> p_40858_.m_21190_(p_40897_));
        p_40895_.m_7967_($$12);
        p_40895_.m_6263_(null, p_40896_.m_20185_(), p_40896_.m_20186_(), p_40896_.m_20189_(), SoundEvents.f_11847_, SoundSource.PLAYERS, 1.0f, p_40900_);
    }

    private static AbstractArrow m_40914_(Level p_40915_, LivingEntity p_40916_, ItemStack p_40917_, ItemStack p_40918_) {
        ArrowItem $$4 = (ArrowItem)(p_40918_.m_41720_() instanceof ArrowItem ? p_40918_.m_41720_() : Items.f_42412_);
        AbstractArrow $$5 = $$4.m_6394_(p_40915_, p_40918_, p_40916_);
        if (p_40916_ instanceof Player) {
            $$5.m_36762_(true);
        }
        $$5.m_36740_(SoundEvents.f_11840_);
        $$5.m_36793_(true);
        int $$6 = EnchantmentHelper.m_44843_(Enchantments.f_44961_, p_40917_);
        if ($$6 > 0) {
            $$5.m_36767_((byte)$$6);
        }
        return $$5;
    }

    public static void m_40887_(Level p_40888_, LivingEntity p_40889_, InteractionHand p_40890_, ItemStack p_40891_, float p_40892_, float p_40893_) {
        List<ItemStack> $$6 = CrossbowItem.m_40941_(p_40891_);
        float[] $$7 = CrossbowItem.m_220023_(p_40889_.m_217043_());
        for (int $$8 = 0; $$8 < $$6.size(); ++$$8) {
            boolean $$10;
            ItemStack $$9 = $$6.get($$8);
            boolean bl = $$10 = p_40889_ instanceof Player && ((Player)p_40889_).m_150110_().f_35937_;
            if ($$9.m_41619_()) continue;
            if ($$8 == 0) {
                CrossbowItem.m_40894_(p_40888_, p_40889_, p_40890_, p_40891_, $$9, $$7[$$8], $$10, p_40892_, p_40893_, 0.0f);
                continue;
            }
            if ($$8 == 1) {
                CrossbowItem.m_40894_(p_40888_, p_40889_, p_40890_, p_40891_, $$9, $$7[$$8], $$10, p_40892_, p_40893_, -10.0f);
                continue;
            }
            if ($$8 != 2) continue;
            CrossbowItem.m_40894_(p_40888_, p_40889_, p_40890_, p_40891_, $$9, $$7[$$8], $$10, p_40892_, p_40893_, 10.0f);
        }
        CrossbowItem.m_40905_(p_40888_, p_40889_, p_40891_);
    }

    private static float[] m_220023_(RandomSource p_220024_) {
        boolean $$1 = p_220024_.m_188499_();
        return new float[]{1.0f, CrossbowItem.m_220025_($$1, p_220024_), CrossbowItem.m_220025_(!$$1, p_220024_)};
    }

    private static float m_220025_(boolean p_220026_, RandomSource p_220027_) {
        float $$2 = p_220026_ ? 0.63f : 0.43f;
        return 1.0f / (p_220027_.m_188501_() * 0.5f + 1.8f) + $$2;
    }

    private static void m_40905_(Level p_40906_, LivingEntity p_40907_, ItemStack p_40908_) {
        if (p_40907_ instanceof ServerPlayer) {
            ServerPlayer $$3 = (ServerPlayer)p_40907_;
            if (!p_40906_.f_46443_) {
                CriteriaTriggers.f_10555_.m_65462_($$3, p_40908_);
            }
            $$3.m_36246_(Stats.f_12982_.m_12902_(p_40908_.m_41720_()));
        }
        CrossbowItem.m_40943_(p_40908_);
    }

    @Override
    public void m_5929_(Level p_40910_, LivingEntity p_40911_, ItemStack p_40912_, int p_40913_) {
        if (!p_40910_.f_46443_) {
            int $$4 = EnchantmentHelper.m_44843_(Enchantments.f_44960_, p_40912_);
            SoundEvent $$5 = this.m_40851_($$4);
            SoundEvent $$6 = $$4 == 0 ? SoundEvents.f_11842_ : null;
            float $$7 = (float)(p_40912_.m_41779_() - p_40913_) / (float)CrossbowItem.m_40939_(p_40912_);
            if ($$7 < 0.2f) {
                this.f_40847_ = false;
                this.f_40848_ = false;
            }
            if ($$7 >= 0.2f && !this.f_40847_) {
                this.f_40847_ = true;
                p_40910_.m_6263_(null, p_40911_.m_20185_(), p_40911_.m_20186_(), p_40911_.m_20189_(), $$5, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
            if ($$7 >= 0.5f && $$6 != null && !this.f_40848_) {
                this.f_40848_ = true;
                p_40910_.m_6263_(null, p_40911_.m_20185_(), p_40911_.m_20186_(), p_40911_.m_20189_(), $$6, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
        }
    }

    @Override
    public int m_8105_(ItemStack p_40938_) {
        return CrossbowItem.m_40939_(p_40938_) + 3;
    }

    public static int m_40939_(ItemStack p_40940_) {
        int $$1 = EnchantmentHelper.m_44843_(Enchantments.f_44960_, p_40940_);
        return $$1 == 0 ? 25 : 25 - 5 * $$1;
    }

    @Override
    public UseAnim m_6164_(ItemStack p_40935_) {
        return UseAnim.CROSSBOW;
    }

    private SoundEvent m_40851_(int p_40852_) {
        switch (p_40852_) {
            case 1: {
                return SoundEvents.f_11844_;
            }
            case 2: {
                return SoundEvents.f_11845_;
            }
            case 3: {
                return SoundEvents.f_11846_;
            }
        }
        return SoundEvents.f_11843_;
    }

    private static float m_40853_(int p_40854_, ItemStack p_40855_) {
        float $$2 = (float)p_40854_ / (float)CrossbowItem.m_40939_(p_40855_);
        if ($$2 > 1.0f) {
            $$2 = 1.0f;
        }
        return $$2;
    }

    @Override
    public void m_7373_(ItemStack p_40880_, @Nullable Level p_40881_, List<Component> p_40882_, TooltipFlag p_40883_) {
        List<ItemStack> $$4 = CrossbowItem.m_40941_(p_40880_);
        if (!CrossbowItem.m_40932_(p_40880_) || $$4.isEmpty()) {
            return;
        }
        ItemStack $$5 = $$4.get(0);
        p_40882_.add(Component.m_237115_("item.minecraft.crossbow.projectile").m_130946_(" ").m_7220_($$5.m_41611_()));
        if (p_40883_.m_7050_() && $$5.m_150930_(Items.f_42688_)) {
            ArrayList $$6 = Lists.newArrayList();
            Items.f_42688_.m_7373_($$5, p_40881_, $$6, p_40883_);
            if (!$$6.isEmpty()) {
                for (int $$7 = 0; $$7 < $$6.size(); ++$$7) {
                    $$6.set($$7, Component.m_237113_("  ").m_7220_((Component)$$6.get($$7)).m_130940_(ChatFormatting.GRAY));
                }
                p_40882_.addAll($$6);
            }
        }
    }

    @Override
    public boolean m_41463_(ItemStack p_150801_) {
        return p_150801_.m_150930_(this);
    }

    @Override
    public int m_6615_() {
        return 8;
    }
}

