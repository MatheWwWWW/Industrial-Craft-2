/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.DataSource;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;

public class NbtContents
implements ComponentContents {
    private static final Logger f_237382_ = LogUtils.getLogger();
    private final boolean f_237383_;
    private final Optional<Component> f_237384_;
    private final String f_237385_;
    private final DataSource f_237386_;
    @Nullable
    protected final NbtPathArgument.NbtPath f_237381_;

    public NbtContents(String p_237395_, boolean p_237396_, Optional<Component> p_237397_, DataSource p_237398_) {
        this(p_237395_, NbtContents.m_237409_(p_237395_), p_237396_, p_237397_, p_237398_);
    }

    private NbtContents(String p_237389_, @Nullable NbtPathArgument.NbtPath p_237390_, boolean p_237391_, Optional<Component> p_237392_, DataSource p_237393_) {
        this.f_237385_ = p_237389_;
        this.f_237381_ = p_237390_;
        this.f_237383_ = p_237391_;
        this.f_237384_ = p_237392_;
        this.f_237386_ = p_237393_;
    }

    @Nullable
    private static NbtPathArgument.NbtPath m_237409_(String p_237410_) {
        try {
            return new NbtPathArgument().parse(new StringReader(p_237410_));
        }
        catch (CommandSyntaxException $$1) {
            return null;
        }
    }

    public String m_237399_() {
        return this.f_237385_;
    }

    public boolean m_237426_() {
        return this.f_237383_;
    }

    public Optional<Component> m_237427_() {
        return this.f_237384_;
    }

    public DataSource m_237428_() {
        return this.f_237386_;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object p_237430_) {
        if (this == p_237430_) {
            return true;
        }
        if (!(p_237430_ instanceof NbtContents)) return false;
        NbtContents $$1 = (NbtContents)p_237430_;
        if (!this.f_237386_.equals($$1.f_237386_)) return false;
        if (!this.f_237384_.equals($$1.f_237384_)) return false;
        if (this.f_237383_ != $$1.f_237383_) return false;
        if (!this.f_237385_.equals($$1.f_237385_)) return false;
        return true;
    }

    public int hashCode() {
        int $$0 = this.f_237383_ ? 1 : 0;
        $$0 = 31 * $$0 + this.f_237384_.hashCode();
        $$0 = 31 * $$0 + this.f_237385_.hashCode();
        $$0 = 31 * $$0 + this.f_237386_.hashCode();
        return $$0;
    }

    public String toString() {
        return "nbt{" + this.f_237386_ + ", interpreting=" + this.f_237383_ + ", separator=" + this.f_237384_ + "}";
    }

    @Override
    public MutableComponent m_213698_(@Nullable CommandSourceStack p_237401_, @Nullable Entity p_237402_, int p_237403_) throws CommandSyntaxException {
        if (p_237401_ == null || this.f_237381_ == null) {
            return Component.m_237119_();
        }
        Stream<String> $$3 = this.f_237386_.m_213601_(p_237401_).flatMap(p_237417_ -> {
            try {
                return this.f_237381_.m_99638_((Tag)p_237417_).stream();
            }
            catch (CommandSyntaxException $$1) {
                return Stream.empty();
            }
        }).map(Tag::m_7916_);
        if (this.f_237383_) {
            Component $$4 = (Component)DataFixUtils.orElse(ComponentUtils.m_178424_(p_237401_, this.f_237384_, p_237402_, p_237403_), (Object)ComponentUtils.f_178421_);
            return $$3.flatMap(p_237408_ -> {
                try {
                    MutableComponent $$4 = Component.Serializer.m_130701_(p_237408_);
                    return Stream.of(ComponentUtils.m_130731_(p_237401_, $$4, p_237402_, p_237403_));
                }
                catch (Exception $$5) {
                    f_237382_.warn("Failed to parse component: {}", p_237408_, (Object)$$5);
                    return Stream.of(new MutableComponent[0]);
                }
            }).reduce((p_237420_, p_237421_) -> p_237420_.m_7220_($$4).m_7220_((Component)p_237421_)).orElseGet(Component::m_237119_);
        }
        return ComponentUtils.m_178424_(p_237401_, this.f_237384_, p_237402_, p_237403_).map(p_237415_ -> $$3.map(Component::m_237113_).reduce((p_237424_, p_237425_) -> p_237424_.m_7220_((Component)p_237415_).m_7220_((Component)p_237425_)).orElseGet(Component::m_237119_)).orElseGet(() -> Component.m_237113_($$3.collect(Collectors.joining(", "))));
    }
}

