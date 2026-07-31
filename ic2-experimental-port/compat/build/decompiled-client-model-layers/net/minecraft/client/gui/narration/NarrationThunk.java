/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.narration;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;

public class NarrationThunk<T> {
    private final T f_169154_;
    private final BiConsumer<Consumer<String>, T> f_169155_;
    public static final NarrationThunk<?> f_169153_ = new NarrationThunk<Unit>(Unit.INSTANCE, (p_169171_, p_169172_) -> {});

    private NarrationThunk(T p_169158_, BiConsumer<Consumer<String>, T> p_169159_) {
        this.f_169154_ = p_169158_;
        this.f_169155_ = p_169159_;
    }

    public static NarrationThunk<?> m_169160_(String p_169161_) {
        return new NarrationThunk<String>(p_169161_, Consumer::accept);
    }

    public static NarrationThunk<?> m_169176_(Component p_169177_) {
        return new NarrationThunk<Component>(p_169177_, (p_169174_, p_169175_) -> p_169174_.accept(p_169175_.getString()));
    }

    public static NarrationThunk<?> m_169162_(List<Component> p_169163_) {
        return new NarrationThunk<List>(p_169163_, (p_169166_, p_169167_) -> p_169163_.stream().map(Component::getString).forEach((Consumer<String>)p_169166_));
    }

    public void m_169168_(Consumer<String> p_169169_) {
        this.f_169155_.accept(p_169169_, (Consumer<String>)this.f_169154_);
    }

    public boolean equals(Object p_169179_) {
        if (this == p_169179_) {
            return true;
        }
        if (p_169179_ instanceof NarrationThunk) {
            NarrationThunk $$1 = (NarrationThunk)p_169179_;
            return $$1.f_169155_ == this.f_169155_ && $$1.f_169154_.equals(this.f_169154_);
        }
        return false;
    }

    public int hashCode() {
        int $$0 = this.f_169154_.hashCode();
        $$0 = 31 * $$0 + this.f_169155_.hashCode();
        return $$0;
    }
}

