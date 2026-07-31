/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.DataFixUtils
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.Entity;

public class ComponentUtils {
    public static final String f_178419_ = ", ";
    public static final Component f_178420_ = Component.m_237113_(", ").m_130940_(ChatFormatting.GRAY);
    public static final Component f_178421_ = Component.m_237113_(", ");

    public static MutableComponent m_130750_(MutableComponent p_130751_, Style p_130752_) {
        if (p_130752_.m_131179_()) {
            return p_130751_;
        }
        Style $$2 = p_130751_.m_7383_();
        if ($$2.m_131179_()) {
            return p_130751_.m_6270_(p_130752_);
        }
        if ($$2.equals(p_130752_)) {
            return p_130751_;
        }
        return p_130751_.m_6270_($$2.m_131146_(p_130752_));
    }

    public static Optional<MutableComponent> m_178424_(@Nullable CommandSourceStack p_178425_, Optional<Component> p_178426_, @Nullable Entity p_178427_, int p_178428_) throws CommandSyntaxException {
        return p_178426_.isPresent() ? Optional.of(ComponentUtils.m_130731_(p_178425_, p_178426_.get(), p_178427_, p_178428_)) : Optional.empty();
    }

    public static MutableComponent m_130731_(@Nullable CommandSourceStack p_130732_, Component p_130733_, @Nullable Entity p_130734_, int p_130735_) throws CommandSyntaxException {
        if (p_130735_ > 100) {
            return p_130733_.m_6881_();
        }
        MutableComponent $$4 = p_130733_.m_214077_().m_213698_(p_130732_, p_130734_, p_130735_ + 1);
        for (Component $$5 : p_130733_.m_7360_()) {
            $$4.m_7220_(ComponentUtils.m_130731_(p_130732_, $$5, p_130734_, p_130735_ + 1));
        }
        return $$4.m_130948_(ComponentUtils.m_130736_(p_130732_, p_130733_.m_7383_(), p_130734_, p_130735_));
    }

    private static Style m_130736_(@Nullable CommandSourceStack p_130737_, Style p_130738_, @Nullable Entity p_130739_, int p_130740_) throws CommandSyntaxException {
        Component $$5;
        HoverEvent $$4 = p_130738_.m_131186_();
        if ($$4 != null && ($$5 = $$4.m_130823_(HoverEvent.Action.f_130831_)) != null) {
            HoverEvent $$6 = new HoverEvent(HoverEvent.Action.f_130831_, ComponentUtils.m_130731_(p_130737_, $$5, p_130739_, p_130740_ + 1));
            return p_130738_.m_131144_($$6);
        }
        return p_130738_;
    }

    public static Component m_130727_(GameProfile p_130728_) {
        if (p_130728_.getName() != null) {
            return Component.m_237113_(p_130728_.getName());
        }
        if (p_130728_.getId() != null) {
            return Component.m_237113_(p_130728_.getId().toString());
        }
        return Component.m_237113_("(unknown)");
    }

    public static Component m_130743_(Collection<String> p_130744_) {
        return ComponentUtils.m_130745_(p_130744_, p_130742_ -> Component.m_237113_(p_130742_).m_130940_(ChatFormatting.GREEN));
    }

    public static <T extends Comparable<T>> Component m_130745_(Collection<T> p_130746_, Function<T, Component> p_130747_) {
        if (p_130746_.isEmpty()) {
            return CommonComponents.f_237098_;
        }
        if (p_130746_.size() == 1) {
            return p_130747_.apply((Comparable)p_130746_.iterator().next());
        }
        ArrayList $$2 = Lists.newArrayList(p_130746_);
        $$2.sort(Comparable::compareTo);
        return ComponentUtils.m_178440_($$2, p_130747_);
    }

    public static <T> Component m_178440_(Collection<? extends T> p_178441_, Function<T, Component> p_178442_) {
        return ComponentUtils.m_178436_(p_178441_, f_178420_, p_178442_);
    }

    public static <T> MutableComponent m_178429_(Collection<? extends T> p_178430_, Optional<? extends Component> p_178431_, Function<T, Component> p_178432_) {
        return ComponentUtils.m_178436_(p_178430_, (Component)DataFixUtils.orElse(p_178431_, (Object)f_178420_), p_178432_);
    }

    public static Component m_178433_(Collection<? extends Component> p_178434_, Component p_178435_) {
        return ComponentUtils.m_178436_(p_178434_, p_178435_, Function.identity());
    }

    public static <T> MutableComponent m_178436_(Collection<? extends T> p_178437_, Component p_178438_, Function<T, Component> p_178439_) {
        if (p_178437_.isEmpty()) {
            return Component.m_237119_();
        }
        if (p_178437_.size() == 1) {
            return p_178439_.apply(p_178437_.iterator().next()).m_6881_();
        }
        MutableComponent $$3 = Component.m_237119_();
        boolean $$4 = true;
        for (T $$5 : p_178437_) {
            if (!$$4) {
                $$3.m_7220_(p_178438_);
            }
            $$3.m_7220_(p_178439_.apply($$5));
            $$4 = false;
        }
        return $$3;
    }

    public static MutableComponent m_130748_(Component p_130749_) {
        return Component.m_237110_("chat.square_brackets", p_130749_);
    }

    public static Component m_130729_(Message p_130730_) {
        if (p_130730_ instanceof Component) {
            Component $$1 = (Component)p_130730_;
            return $$1;
        }
        return Component.m_237113_(p_130730_.getString());
    }

    public static boolean m_237134_(@Nullable Component p_237135_) {
        ComponentContents componentContents;
        if (p_237135_ != null && (componentContents = p_237135_.m_214077_()) instanceof TranslatableContents) {
            TranslatableContents $$1 = (TranslatableContents)componentContents;
            String $$2 = $$1.m_237508_();
            return Language.m_128107_().m_6722_($$2);
        }
        return true;
    }
}

