/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.FireworkStarItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireworkRocketItem
extends Item {
    public static final String f_150831_ = "Fireworks";
    public static final String f_150832_ = "Explosion";
    public static final String f_150833_ = "Explosions";
    public static final String f_150834_ = "Flight";
    public static final String f_150835_ = "Type";
    public static final String f_150836_ = "Trail";
    public static final String f_150837_ = "Flicker";
    public static final String f_150838_ = "Colors";
    public static final String f_150839_ = "FadeColors";
    public static final double f_150840_ = 0.15;

    public FireworkRocketItem(Item.Properties p_41209_) {
        super(p_41209_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_41216_) {
        Level $$1 = p_41216_.m_43725_();
        if (!$$1.f_46443_) {
            ItemStack $$2 = p_41216_.m_43722_();
            Vec3 $$3 = p_41216_.m_43720_();
            Direction $$4 = p_41216_.m_43719_();
            FireworkRocketEntity $$5 = new FireworkRocketEntity($$1, p_41216_.m_43723_(), $$3.f_82479_ + (double)$$4.m_122429_() * 0.15, $$3.f_82480_ + (double)$$4.m_122430_() * 0.15, $$3.f_82481_ + (double)$$4.m_122431_() * 0.15, $$2);
            $$1.m_7967_($$5);
            $$2.m_41774_(1);
        }
        return InteractionResult.m_19078_($$1.f_46443_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41218_, Player p_41219_, InteractionHand p_41220_) {
        if (p_41219_.m_21255_()) {
            ItemStack $$3 = p_41219_.m_21120_(p_41220_);
            if (!p_41218_.f_46443_) {
                FireworkRocketEntity $$4 = new FireworkRocketEntity(p_41218_, $$3, p_41219_);
                p_41218_.m_7967_($$4);
                if (!p_41219_.m_150110_().f_35937_) {
                    $$3.m_41774_(1);
                }
                p_41219_.m_36246_(Stats.f_12982_.m_12902_(this));
            }
            return InteractionResultHolder.m_19092_(p_41219_.m_21120_(p_41220_), p_41218_.m_5776_());
        }
        return InteractionResultHolder.m_19098_(p_41219_.m_21120_(p_41220_));
    }

    @Override
    public void m_7373_(ItemStack p_41211_, @Nullable Level p_41212_, List<Component> p_41213_, TooltipFlag p_41214_) {
        ListTag $$5;
        CompoundTag $$4 = p_41211_.m_41737_(f_150831_);
        if ($$4 == null) {
            return;
        }
        if ($$4.m_128425_(f_150834_, 99)) {
            p_41213_.add(Component.m_237115_("item.minecraft.firework_rocket.flight").m_130946_(" ").m_130946_(String.valueOf($$4.m_128445_(f_150834_))).m_130940_(ChatFormatting.GRAY));
        }
        if (!($$5 = $$4.m_128437_(f_150833_, 10)).isEmpty()) {
            for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
                CompoundTag $$7 = $$5.m_128728_($$6);
                ArrayList $$8 = Lists.newArrayList();
                FireworkStarItem.m_41256_($$7, $$8);
                if ($$8.isEmpty()) continue;
                for (int $$9 = 1; $$9 < $$8.size(); ++$$9) {
                    $$8.set($$9, Component.m_237113_("  ").m_7220_((Component)$$8.get($$9)).m_130940_(ChatFormatting.GRAY));
                }
                p_41213_.addAll($$8);
            }
        }
    }

    @Override
    public ItemStack m_7968_() {
        ItemStack $$0 = new ItemStack(this);
        $$0.m_41784_().m_128344_(f_150834_, (byte)1);
        return $$0;
    }

    public static final class Shape
    extends Enum<Shape> {
        public static final /* enum */ Shape SMALL_BALL = new Shape(0, "small_ball");
        public static final /* enum */ Shape LARGE_BALL = new Shape(1, "large_ball");
        public static final /* enum */ Shape STAR = new Shape(2, "star");
        public static final /* enum */ Shape CREEPER = new Shape(3, "creeper");
        public static final /* enum */ Shape BURST = new Shape(4, "burst");
        private static final Shape[] f_41226_;
        private final int f_41227_;
        private final String f_41228_;
        private static final /* synthetic */ Shape[] $VALUES;

        public static Shape[] values() {
            return (Shape[])$VALUES.clone();
        }

        public static Shape valueOf(String p_41245_) {
            return Enum.valueOf(Shape.class, p_41245_);
        }

        private Shape(int p_41234_, String p_41235_) {
            this.f_41227_ = p_41234_;
            this.f_41228_ = p_41235_;
        }

        public int m_41236_() {
            return this.f_41227_;
        }

        public String m_41241_() {
            return this.f_41228_;
        }

        public static Shape m_41237_(int p_41238_) {
            if (p_41238_ < 0 || p_41238_ >= f_41226_.length) {
                return SMALL_BALL;
            }
            return f_41226_[p_41238_];
        }

        private static /* synthetic */ Shape[] m_150842_() {
            return new Shape[]{SMALL_BALL, LARGE_BALL, STAR, CREEPER, BURST};
        }

        static {
            $VALUES = Shape.m_150842_();
            f_41226_ = (Shape[])Arrays.stream(Shape.values()).sorted(Comparator.comparingInt(p_41240_ -> p_41240_.f_41227_)).toArray(Shape[]::new);
        }
    }
}

