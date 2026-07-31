/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Streams
 *  com.mojang.blocklist.BlockListSupplier
 */
package net.minecraft.client.multiplayer.resolver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.mojang.blocklist.BlockListSupplier;
import java.util.Objects;
import java.util.ServiceLoader;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public interface AddressCheck {
    public boolean m_142649_(ResolvedServerAddress var1);

    public boolean m_142408_(ServerAddress var1);

    public static AddressCheck m_171828_() {
        final ImmutableList $$0 = (ImmutableList)Streams.stream(ServiceLoader.load(BlockListSupplier.class)).map(BlockListSupplier::createBlockList).filter(Objects::nonNull).collect(ImmutableList.toImmutableList());
        return new AddressCheck(){

            @Override
            public boolean m_142649_(ResolvedServerAddress p_171835_) {
                String $$1 = p_171835_.m_142727_();
                String $$2 = p_171835_.m_142728_();
                return $$0.stream().noneMatch(p_171841_ -> p_171841_.test($$1) || p_171841_.test($$2));
            }

            @Override
            public boolean m_142408_(ServerAddress p_171837_) {
                String $$1 = p_171837_.m_171863_();
                return $$0.stream().noneMatch(p_171844_ -> p_171844_.test($$1));
            }
        };
    }
}

