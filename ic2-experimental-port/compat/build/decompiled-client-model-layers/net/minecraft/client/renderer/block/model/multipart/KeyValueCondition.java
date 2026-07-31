/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.base.Splitter
 */
package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.block.model.multipart.Condition;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class KeyValueCondition
implements Condition {
    private static final Splitter f_111934_ = Splitter.on((char)'|').omitEmptyStrings();
    private final String f_111935_;
    private final String f_111936_;

    public KeyValueCondition(String p_111939_, String p_111940_) {
        this.f_111935_ = p_111939_;
        this.f_111936_ = p_111940_;
    }

    @Override
    public Predicate<BlockState> m_7289_(StateDefinition<Block, BlockState> p_111960_) {
        Predicate<BlockState> $$7;
        List $$4;
        boolean $$3;
        Property<?> $$1 = p_111960_.m_61081_(this.f_111935_);
        if ($$1 == null) {
            throw new RuntimeException(String.format(Locale.ROOT, "Unknown property '%s' on '%s'", this.f_111935_, p_111960_.m_61091_()));
        }
        String $$2 = this.f_111936_;
        boolean bl = $$3 = !$$2.isEmpty() && $$2.charAt(0) == '!';
        if ($$3) {
            $$2 = $$2.substring(1);
        }
        if (($$4 = f_111934_.splitToList((CharSequence)$$2)).isEmpty()) {
            throw new RuntimeException(String.format(Locale.ROOT, "Empty value '%s' for property '%s' on '%s'", this.f_111936_, this.f_111935_, p_111960_.m_61091_()));
        }
        if ($$4.size() == 1) {
            Predicate<BlockState> $$5 = this.m_111944_(p_111960_, $$1, $$2);
        } else {
            List $$6 = $$4.stream().map(p_111958_ -> this.m_111944_(p_111960_, $$1, (String)p_111958_)).collect(Collectors.toList());
            $$7 = p_111954_ -> $$6.stream().anyMatch(p_173509_ -> p_173509_.test(p_111954_));
        }
        return $$3 ? $$7.negate() : $$7;
    }

    private Predicate<BlockState> m_111944_(StateDefinition<Block, BlockState> p_111945_, Property<?> p_111946_, String p_111947_) {
        Optional<?> $$3 = p_111946_.m_6215_(p_111947_);
        if (!$$3.isPresent()) {
            throw new RuntimeException(String.format(Locale.ROOT, "Unknown value '%s' for property '%s' on '%s' in '%s'", p_111947_, this.f_111935_, p_111945_.m_61091_(), this.f_111936_));
        }
        return p_111951_ -> p_111951_.m_61143_(p_111946_).equals($$3.get());
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("key", (Object)this.f_111935_).add("value", (Object)this.f_111936_).toString();
    }
}

