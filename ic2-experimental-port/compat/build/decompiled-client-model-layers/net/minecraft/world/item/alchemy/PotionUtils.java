/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class PotionUtils {
    public static final String f_151254_ = "CustomPotionEffects";
    public static final String f_151255_ = "CustomPotionColor";
    public static final String f_151256_ = "Potion";
    private static final int f_151257_ = 0xF800F8;
    private static final Component f_43545_ = Component.m_237115_("effect.none").m_130940_(ChatFormatting.GRAY);

    public static List<MobEffectInstance> m_43547_(ItemStack p_43548_) {
        return PotionUtils.m_43566_(p_43548_.m_41783_());
    }

    public static List<MobEffectInstance> m_43561_(Potion p_43562_, Collection<MobEffectInstance> p_43563_) {
        ArrayList $$2 = Lists.newArrayList();
        $$2.addAll(p_43562_.m_43488_());
        $$2.addAll(p_43563_);
        return $$2;
    }

    public static List<MobEffectInstance> m_43566_(@Nullable CompoundTag p_43567_) {
        ArrayList $$1 = Lists.newArrayList();
        $$1.addAll(PotionUtils.m_43577_(p_43567_).m_43488_());
        PotionUtils.m_43568_(p_43567_, $$1);
        return $$1;
    }

    public static List<MobEffectInstance> m_43571_(ItemStack p_43572_) {
        return PotionUtils.m_43573_(p_43572_.m_41783_());
    }

    public static List<MobEffectInstance> m_43573_(@Nullable CompoundTag p_43574_) {
        ArrayList $$1 = Lists.newArrayList();
        PotionUtils.m_43568_(p_43574_, $$1);
        return $$1;
    }

    public static void m_43568_(@Nullable CompoundTag p_43569_, List<MobEffectInstance> p_43570_) {
        if (p_43569_ != null && p_43569_.m_128425_(f_151254_, 9)) {
            ListTag $$2 = p_43569_.m_128437_(f_151254_, 10);
            for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
                CompoundTag $$4 = $$2.m_128728_($$3);
                MobEffectInstance $$5 = MobEffectInstance.m_19560_($$4);
                if ($$5 == null) continue;
                p_43570_.add($$5);
            }
        }
    }

    public static int m_43575_(ItemStack p_43576_) {
        CompoundTag $$1 = p_43576_.m_41783_();
        if ($$1 != null && $$1.m_128425_(f_151255_, 99)) {
            return $$1.m_128451_(f_151255_);
        }
        return PotionUtils.m_43579_(p_43576_) == Potions.f_43598_ ? 0xF800F8 : PotionUtils.m_43564_(PotionUtils.m_43547_(p_43576_));
    }

    public static int m_43559_(Potion p_43560_) {
        return p_43560_ == Potions.f_43598_ ? 0xF800F8 : PotionUtils.m_43564_(p_43560_.m_43488_());
    }

    public static int m_43564_(Collection<MobEffectInstance> p_43565_) {
        int $$1 = 3694022;
        if (p_43565_.isEmpty()) {
            return 3694022;
        }
        float $$2 = 0.0f;
        float $$3 = 0.0f;
        float $$4 = 0.0f;
        int $$5 = 0;
        for (MobEffectInstance $$6 : p_43565_) {
            if (!$$6.m_19572_()) continue;
            int $$7 = $$6.m_19544_().m_19484_();
            int $$8 = $$6.m_19564_() + 1;
            $$2 += (float)($$8 * ($$7 >> 16 & 0xFF)) / 255.0f;
            $$3 += (float)($$8 * ($$7 >> 8 & 0xFF)) / 255.0f;
            $$4 += (float)($$8 * ($$7 >> 0 & 0xFF)) / 255.0f;
            $$5 += $$8;
        }
        if ($$5 == 0) {
            return 0;
        }
        $$2 = $$2 / (float)$$5 * 255.0f;
        $$3 = $$3 / (float)$$5 * 255.0f;
        $$4 = $$4 / (float)$$5 * 255.0f;
        return (int)$$2 << 16 | (int)$$3 << 8 | (int)$$4;
    }

    public static Potion m_43579_(ItemStack p_43580_) {
        return PotionUtils.m_43577_(p_43580_.m_41783_());
    }

    public static Potion m_43577_(@Nullable CompoundTag p_43578_) {
        if (p_43578_ == null) {
            return Potions.f_43598_;
        }
        return Potion.m_43489_(p_43578_.m_128461_(f_151256_));
    }

    public static ItemStack m_43549_(ItemStack p_43550_, Potion p_43551_) {
        ResourceLocation $$2 = Registry.f_122828_.m_7981_(p_43551_);
        if (p_43551_ == Potions.f_43598_) {
            p_43550_.m_41749_(f_151256_);
        } else {
            p_43550_.m_41784_().m_128359_(f_151256_, $$2.toString());
        }
        return p_43550_;
    }

    public static ItemStack m_43552_(ItemStack p_43553_, Collection<MobEffectInstance> p_43554_) {
        if (p_43554_.isEmpty()) {
            return p_43553_;
        }
        CompoundTag $$2 = p_43553_.m_41784_();
        ListTag $$3 = $$2.m_128437_(f_151254_, 9);
        for (MobEffectInstance $$4 : p_43554_) {
            $$3.add($$4.m_19555_(new CompoundTag()));
        }
        $$2.m_128365_(f_151254_, $$3);
        return p_43553_;
    }

    public static void m_43555_(ItemStack p_43556_, List<Component> p_43557_, float p_43558_) {
        List<MobEffectInstance> $$3 = PotionUtils.m_43547_(p_43556_);
        ArrayList $$4 = Lists.newArrayList();
        if ($$3.isEmpty()) {
            p_43557_.add(f_43545_);
        } else {
            for (MobEffectInstance $$5 : $$3) {
                MutableComponent $$6 = Component.m_237115_($$5.m_19576_());
                MobEffect $$7 = $$5.m_19544_();
                Map<Attribute, AttributeModifier> $$8 = $$7.m_19485_();
                if (!$$8.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> $$9 : $$8.entrySet()) {
                        AttributeModifier $$10 = $$9.getValue();
                        AttributeModifier $$11 = new AttributeModifier($$10.m_22214_(), $$7.m_7048_($$5.m_19564_(), $$10), $$10.m_22217_());
                        $$4.add(new Pair((Object)$$9.getKey(), (Object)$$11));
                    }
                }
                if ($$5.m_19564_() > 0) {
                    $$6 = Component.m_237110_("potion.withAmplifier", $$6, Component.m_237115_("potion.potency." + $$5.m_19564_()));
                }
                if ($$5.m_19557_() > 20) {
                    $$6 = Component.m_237110_("potion.withDuration", $$6, MobEffectUtil.m_19581_($$5, p_43558_));
                }
                p_43557_.add($$6.m_130940_($$7.m_19483_().m_19497_()));
            }
        }
        if (!$$4.isEmpty()) {
            p_43557_.add(CommonComponents.f_237098_);
            p_43557_.add(Component.m_237115_("potion.whenDrank").m_130940_(ChatFormatting.DARK_PURPLE));
            for (Pair $$12 : $$4) {
                double $$16;
                AttributeModifier $$13 = (AttributeModifier)$$12.getSecond();
                double $$14 = $$13.m_22218_();
                if ($$13.m_22217_() == AttributeModifier.Operation.MULTIPLY_BASE || $$13.m_22217_() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    double $$15 = $$13.m_22218_() * 100.0;
                } else {
                    $$16 = $$13.m_22218_();
                }
                if ($$14 > 0.0) {
                    p_43557_.add(Component.m_237110_("attribute.modifier.plus." + $$13.m_22217_().m_22235_(), ItemStack.f_41584_.format($$16), Component.m_237115_(((Attribute)$$12.getFirst()).m_22087_())).m_130940_(ChatFormatting.BLUE));
                    continue;
                }
                if (!($$14 < 0.0)) continue;
                p_43557_.add(Component.m_237110_("attribute.modifier.take." + $$13.m_22217_().m_22235_(), ItemStack.f_41584_.format($$16 *= -1.0), Component.m_237115_(((Attribute)$$12.getFirst()).m_22087_())).m_130940_(ChatFormatting.RED));
            }
        }
    }
}

