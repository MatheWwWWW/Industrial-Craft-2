/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;

public class SelectorContents
implements ComponentContents {
    private static final Logger f_237459_ = LogUtils.getLogger();
    private final String f_237460_;
    @Nullable
    private final EntitySelector f_237461_;
    protected final Optional<Component> f_237458_;

    public SelectorContents(String p_237464_, Optional<Component> p_237465_) {
        this.f_237460_ = p_237464_;
        this.f_237458_ = p_237465_;
        this.f_237461_ = SelectorContents.m_237471_(p_237464_);
    }

    @Nullable
    private static EntitySelector m_237471_(String p_237472_) {
        EntitySelector $$1 = null;
        try {
            EntitySelectorParser $$2 = new EntitySelectorParser(new StringReader(p_237472_));
            $$1 = $$2.m_121377_();
        }
        catch (CommandSyntaxException $$3) {
            f_237459_.warn("Invalid selector component: {}: {}", (Object)p_237472_, (Object)$$3.getMessage());
        }
        return $$1;
    }

    public String m_237466_() {
        return this.f_237460_;
    }

    @Nullable
    public EntitySelector m_237478_() {
        return this.f_237461_;
    }

    public Optional<Component> m_237479_() {
        return this.f_237458_;
    }

    @Override
    public MutableComponent m_213698_(@Nullable CommandSourceStack p_237468_, @Nullable Entity p_237469_, int p_237470_) throws CommandSyntaxException {
        if (p_237468_ == null || this.f_237461_ == null) {
            return Component.m_237119_();
        }
        Optional<MutableComponent> $$3 = ComponentUtils.m_178424_(p_237468_, this.f_237458_, p_237469_, p_237470_);
        return ComponentUtils.m_178429_(this.f_237461_.m_121160_(p_237468_), $$3, Entity::m_5446_);
    }

    @Override
    public <T> Optional<T> m_213724_(FormattedText.StyledContentConsumer<T> p_237476_, Style p_237477_) {
        return p_237476_.m_7164_(p_237477_, this.f_237460_);
    }

    @Override
    public <T> Optional<T> m_213874_(FormattedText.ContentConsumer<T> p_237474_) {
        return p_237474_.m_130809_(this.f_237460_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object p_237481_) {
        if (this == p_237481_) {
            return true;
        }
        if (!(p_237481_ instanceof SelectorContents)) return false;
        SelectorContents $$1 = (SelectorContents)p_237481_;
        if (!this.f_237460_.equals($$1.f_237460_)) return false;
        if (!this.f_237458_.equals($$1.f_237458_)) return false;
        return true;
    }

    public int hashCode() {
        int $$0 = this.f_237460_.hashCode();
        $$0 = 31 * $$0 + this.f_237458_.hashCode();
        return $$0;
    }

    public String toString() {
        return "pattern{" + this.f_237460_ + "}";
    }
}

