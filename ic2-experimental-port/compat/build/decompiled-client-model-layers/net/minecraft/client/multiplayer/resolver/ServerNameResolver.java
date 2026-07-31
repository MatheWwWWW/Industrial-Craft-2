/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.client.multiplayer.resolver;

import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;
import net.minecraft.client.multiplayer.resolver.AddressCheck;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddressResolver;
import net.minecraft.client.multiplayer.resolver.ServerRedirectHandler;

public class ServerNameResolver {
    public static final ServerNameResolver f_171881_ = new ServerNameResolver(ServerAddressResolver.f_171875_, ServerRedirectHandler.m_171895_(), AddressCheck.m_171828_());
    private final ServerAddressResolver f_171882_;
    private final ServerRedirectHandler f_171883_;
    private final AddressCheck f_171884_;

    @VisibleForTesting
    ServerNameResolver(ServerAddressResolver p_171887_, ServerRedirectHandler p_171888_, AddressCheck p_171889_) {
        this.f_171882_ = p_171887_;
        this.f_171883_ = p_171888_;
        this.f_171884_ = p_171889_;
    }

    public Optional<ResolvedServerAddress> m_171890_(ServerAddress p_171891_) {
        Optional<ResolvedServerAddress> $$1 = this.f_171882_.m_171879_(p_171891_);
        if ($$1.isPresent() && !this.f_171884_.m_142649_($$1.get()) || !this.f_171884_.m_142408_(p_171891_)) {
            return Optional.empty();
        }
        Optional<ServerAddress> $$2 = this.f_171883_.m_171901_(p_171891_);
        if ($$2.isPresent()) {
            $$1 = this.f_171882_.m_171879_($$2.get()).filter(this.f_171884_::m_142649_);
        }
        return $$1;
    }
}

