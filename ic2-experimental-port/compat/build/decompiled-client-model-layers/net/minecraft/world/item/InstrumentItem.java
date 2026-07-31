/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class InstrumentItem
extends Item {
    private static final String f_220096_ = "instrument";
    private TagKey<Instrument> f_220097_;

    public InstrumentItem(Item.Properties p_220099_, TagKey<Instrument> p_220100_) {
        super(p_220099_);
        this.f_220097_ = p_220100_;
    }

    @Override
    public void m_7373_(ItemStack p_220115_, @Nullable Level p_220116_, List<Component> p_220117_, TooltipFlag p_220118_) {
        super.m_7373_(p_220115_, p_220116_, p_220117_, p_220118_);
        Optional $$4 = this.m_220134_(p_220115_).flatMap(Holder::m_203543_);
        if ($$4.isPresent()) {
            MutableComponent $$5 = Component.m_237115_(Util.m_137492_(f_220096_, ((ResourceKey)$$4.get()).m_135782_()));
            p_220117_.add($$5.m_130940_(ChatFormatting.GRAY));
        }
    }

    public static ItemStack m_220107_(Item p_220108_, Holder<Instrument> p_220109_) {
        ItemStack $$2 = new ItemStack(p_220108_);
        InstrumentItem.m_220119_($$2, p_220109_);
        return $$2;
    }

    public static void m_220110_(ItemStack p_220111_, TagKey<Instrument> p_220112_, RandomSource p_220113_) {
        Optional $$3 = Registry.f_235738_.m_203431_(p_220112_).flatMap(p_220103_ -> p_220103_.m_213653_(p_220113_));
        if ($$3.isPresent()) {
            InstrumentItem.m_220119_(p_220111_, (Holder)$$3.get());
        }
    }

    private static void m_220119_(ItemStack p_220120_, Holder<Instrument> p_220121_) {
        CompoundTag $$2 = p_220120_.m_41784_();
        $$2.m_128359_(f_220096_, p_220121_.m_203543_().orElseThrow(() -> new IllegalStateException("Invalid instrument")).m_135782_().toString());
    }

    @Override
    public void m_6787_(CreativeModeTab p_220105_, NonNullList<ItemStack> p_220106_) {
        if (this.m_220152_(p_220105_)) {
            for (Holder<Instrument> $$2 : Registry.f_235738_.m_206058_(this.f_220097_)) {
                p_220106_.add(InstrumentItem.m_220107_(Items.f_220219_, $$2));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_220123_, Player p_220124_, InteractionHand p_220125_) {
        ItemStack $$3 = p_220124_.m_21120_(p_220125_);
        Optional<Holder<Instrument>> $$4 = this.m_220134_($$3);
        if ($$4.isPresent()) {
            Instrument $$5 = $$4.get().m_203334_();
            p_220124_.m_6672_(p_220125_);
            InstrumentItem.m_220126_(p_220123_, p_220124_, $$5);
            p_220124_.m_36335_().m_41524_(this, $$5.f_220080_());
            return InteractionResultHolder.m_19096_($$3);
        }
        return InteractionResultHolder.m_19100_($$3);
    }

    @Override
    public int m_8105_(ItemStack p_220131_) {
        Optional<Holder<Instrument>> $$1 = this.m_220134_(p_220131_);
        if ($$1.isPresent()) {
            return $$1.get().m_203334_().f_220080_();
        }
        return 0;
    }

    private Optional<Holder<Instrument>> m_220134_(ItemStack p_220135_) {
        ResourceLocation $$2;
        CompoundTag $$1 = p_220135_.m_41783_();
        if ($$1 != null && ($$2 = ResourceLocation.m_135820_($$1.m_128461_(f_220096_))) != null) {
            return Registry.f_235738_.m_203636_(ResourceKey.m_135785_(Registry.f_235737_, $$2));
        }
        Iterator<Holder<Instrument>> $$3 = Registry.f_235738_.m_206058_(this.f_220097_).iterator();
        if ($$3.hasNext()) {
            return Optional.of($$3.next());
        }
        return Optional.empty();
    }

    @Override
    public UseAnim m_6164_(ItemStack p_220133_) {
        return UseAnim.TOOT_HORN;
    }

    private static void m_220126_(Level p_220127_, Player p_220128_, Instrument p_220129_) {
        SoundEvent $$3 = p_220129_.f_220079_();
        float $$4 = p_220129_.f_220081_() / 16.0f;
        p_220127_.m_6269_(p_220128_, p_220128_, $$3, SoundSource.RECORDS, $$4, 1.0f);
        p_220127_.m_214171_(GameEvent.f_223696_, p_220128_.m_20182_(), GameEvent.Context.m_223717_(p_220128_));
    }
}

