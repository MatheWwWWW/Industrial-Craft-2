/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.item;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightBlock;

public class ItemProperties {
    private static final Map<ResourceLocation, ItemPropertyFunction> f_117820_ = Maps.newHashMap();
    private static final String f_174568_ = "CustomModelData";
    private static final ResourceLocation f_117821_ = new ResourceLocation("damaged");
    private static final ResourceLocation f_117822_ = new ResourceLocation("damage");
    private static final ClampedItemPropertyFunction f_117823_ = (p_174660_, p_174661_, p_174662_, p_174663_) -> p_174660_.m_41768_() ? 1.0f : 0.0f;
    private static final ClampedItemPropertyFunction f_117824_ = (p_174655_, p_174656_, p_174657_, p_174658_) -> Mth.m_14036_((float)p_174655_.m_41773_() / (float)p_174655_.m_41776_(), 0.0f, 1.0f);
    private static final Map<Item, Map<ResourceLocation, ItemPropertyFunction>> f_117825_ = Maps.newHashMap();

    private static ClampedItemPropertyFunction m_174581_(ResourceLocation p_174582_, ClampedItemPropertyFunction p_174583_) {
        f_117820_.put(p_174582_, p_174583_);
        return p_174583_;
    }

    private static void m_174579_(ItemPropertyFunction p_174580_) {
        f_117820_.put(new ResourceLocation("custom_model_data"), p_174580_);
    }

    private static void m_174570_(Item p_174571_, ResourceLocation p_174572_, ClampedItemPropertyFunction p_174573_) {
        f_117825_.computeIfAbsent(p_174571_, p_117828_ -> Maps.newHashMap()).put(p_174572_, p_174573_);
    }

    @Nullable
    public static ItemPropertyFunction m_117829_(Item p_117830_, ResourceLocation p_117831_) {
        ItemPropertyFunction $$2;
        if (p_117830_.m_41462_() > 0) {
            if (f_117822_.equals(p_117831_)) {
                return f_117824_;
            }
            if (f_117821_.equals(p_117831_)) {
                return f_117823_;
            }
        }
        if (($$2 = f_117820_.get(p_117831_)) != null) {
            return $$2;
        }
        Map<ResourceLocation, ItemPropertyFunction> $$3 = f_117825_.get(p_117830_);
        if ($$3 == null) {
            return null;
        }
        return $$3.get(p_117831_);
    }

    static {
        ItemProperties.m_174581_(new ResourceLocation("lefthanded"), (p_174650_, p_174651_, p_174652_, p_174653_) -> p_174652_ == null || p_174652_.m_5737_() == HumanoidArm.RIGHT ? 0.0f : 1.0f);
        ItemProperties.m_174581_(new ResourceLocation("cooldown"), (p_174645_, p_174646_, p_174647_, p_174648_) -> p_174647_ instanceof Player ? ((Player)p_174647_).m_36335_().m_41521_(p_174645_.m_41720_(), 0.0f) : 0.0f);
        ItemProperties.m_174579_((p_174640_, p_174641_, p_174642_, p_174643_) -> p_174640_.m_41782_() ? (float)p_174640_.m_41783_().m_128451_(f_174568_) : 0.0f);
        ItemProperties.m_174570_(Items.f_42411_, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0f;
            }
            if (p_174637_.m_21211_() != p_174635_) {
                return 0.0f;
            }
            return (float)(p_174635_.m_41779_() - p_174637_.m_21212_()) / 20.0f;
        });
        ItemProperties.m_174570_(Items.f_42411_, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.m_6117_() && p_174632_.m_21211_() == p_174630_ ? 1.0f : 0.0f);
        ItemProperties.m_174570_(Items.f_151058_, new ResourceLocation("filled"), (p_174625_, p_174626_, p_174627_, p_174628_) -> BundleItem.m_150766_(p_174625_));
        ItemProperties.m_174570_(Items.f_42524_, new ResourceLocation("time"), new ClampedItemPropertyFunction(){
            private double f_117899_;
            private double f_117900_;
            private long f_117901_;

            @Override
            public float m_142187_(ItemStack p_174665_, @Nullable ClientLevel p_174666_, @Nullable LivingEntity p_174667_, int p_174668_) {
                double $$6;
                Entity $$4;
                Entity entity = $$4 = p_174667_ != null ? p_174667_ : p_174665_.m_41609_();
                if ($$4 == null) {
                    return 0.0f;
                }
                if (p_174666_ == null && $$4.f_19853_ instanceof ClientLevel) {
                    p_174666_ = (ClientLevel)$$4.f_19853_;
                }
                if (p_174666_ == null) {
                    return 0.0f;
                }
                if (p_174666_.m_6042_().f_63858_()) {
                    double $$5 = p_174666_.m_46942_(1.0f);
                } else {
                    $$6 = Math.random();
                }
                $$6 = this.m_117903_(p_174666_, $$6);
                return (float)$$6;
            }

            private double m_117903_(Level p_117904_, double p_117905_) {
                if (p_117904_.m_46467_() != this.f_117901_) {
                    this.f_117901_ = p_117904_.m_46467_();
                    double $$2 = p_117905_ - this.f_117899_;
                    $$2 = Mth.m_14109_($$2 + 0.5, 1.0) - 0.5;
                    this.f_117900_ += $$2 * 0.1;
                    this.f_117900_ *= 0.9;
                    this.f_117899_ = Mth.m_14109_(this.f_117899_ + this.f_117900_, 1.0);
                }
                return this.f_117899_;
            }
        });
        ItemProperties.m_174570_(Items.f_42522_, new ResourceLocation("angle"), new CompassItemPropertyFunction((p_234992_, p_234993_, p_234994_) -> {
            if (CompassItem.m_40736_(p_234993_)) {
                return CompassItem.m_220021_(p_234993_.m_41784_());
            }
            return CompassItem.m_220019_(p_234992_);
        }));
        ItemProperties.m_174570_(Items.f_220211_, new ResourceLocation("angle"), new CompassItemPropertyFunction((p_234983_, p_234984_, p_234985_) -> {
            if (p_234985_ instanceof Player) {
                Player $$3 = (Player)p_234985_;
                return $$3.m_219759_().orElse(null);
            }
            return null;
        }));
        ItemProperties.m_174570_(Items.f_42717_, new ResourceLocation("pull"), (p_174610_, p_174611_, p_174612_, p_174613_) -> {
            if (p_174612_ == null) {
                return 0.0f;
            }
            if (CrossbowItem.m_40932_(p_174610_)) {
                return 0.0f;
            }
            return (float)(p_174610_.m_41779_() - p_174612_.m_21212_()) / (float)CrossbowItem.m_40939_(p_174610_);
        });
        ItemProperties.m_174570_(Items.f_42717_, new ResourceLocation("pulling"), (p_174605_, p_174606_, p_174607_, p_174608_) -> p_174607_ != null && p_174607_.m_6117_() && p_174607_.m_21211_() == p_174605_ && !CrossbowItem.m_40932_(p_174605_) ? 1.0f : 0.0f);
        ItemProperties.m_174570_(Items.f_42717_, new ResourceLocation("charged"), (p_174600_, p_174601_, p_174602_, p_174603_) -> p_174602_ != null && CrossbowItem.m_40932_(p_174600_) ? 1.0f : 0.0f);
        ItemProperties.m_174570_(Items.f_42717_, new ResourceLocation("firework"), (p_174595_, p_174596_, p_174597_, p_174598_) -> p_174597_ != null && CrossbowItem.m_40932_(p_174595_) && CrossbowItem.m_40871_(p_174595_, Items.f_42688_) ? 1.0f : 0.0f);
        ItemProperties.m_174570_(Items.f_42741_, new ResourceLocation("broken"), (p_174590_, p_174591_, p_174592_, p_174593_) -> ElytraItem.m_41140_(p_174590_) ? 0.0f : 1.0f);
        ItemProperties.m_174570_(Items.f_42523_, new ResourceLocation("cast"), (p_174585_, p_174586_, p_174587_, p_174588_) -> {
            boolean $$5;
            if (p_174587_ == null) {
                return 0.0f;
            }
            boolean $$4 = p_174587_.m_21205_() == p_174585_;
            boolean bl = $$5 = p_174587_.m_21206_() == p_174585_;
            if (p_174587_.m_21205_().m_41720_() instanceof FishingRodItem) {
                $$5 = false;
            }
            return ($$4 || $$5) && p_174587_ instanceof Player && ((Player)p_174587_).f_36083_ != null ? 1.0f : 0.0f;
        });
        ItemProperties.m_174570_(Items.f_42740_, new ResourceLocation("blocking"), (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.m_6117_() && p_174577_.m_21211_() == p_174575_ ? 1.0f : 0.0f);
        ItemProperties.m_174570_(Items.f_42713_, new ResourceLocation("throwing"), (p_234996_, p_234997_, p_234998_, p_234999_) -> p_234998_ != null && p_234998_.m_6117_() && p_234998_.m_21211_() == p_234996_ ? 1.0f : 0.0f);
        ItemProperties.m_174570_(Items.f_151033_, new ResourceLocation("level"), (p_234987_, p_234988_, p_234989_, p_234990_) -> {
            CompoundTag $$4 = p_234987_.m_41737_("BlockStateTag");
            try {
                Tag $$5;
                if ($$4 != null && ($$5 = $$4.m_128423_(LightBlock.f_153657_.m_61708_())) != null) {
                    return (float)Integer.parseInt($$5.m_7916_()) / 16.0f;
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            return 1.0f;
        });
        ItemProperties.m_174570_(Items.f_220219_, new ResourceLocation("tooting"), (p_234978_, p_234979_, p_234980_, p_234981_) -> p_234980_ != null && p_234980_.m_6117_() && p_234980_.m_21211_() == p_234978_ ? 1.0f : 0.0f);
    }
}

