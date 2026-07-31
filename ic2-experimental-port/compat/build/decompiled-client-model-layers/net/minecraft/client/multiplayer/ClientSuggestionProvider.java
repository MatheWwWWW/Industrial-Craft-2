/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ClientSuggestionProvider
implements SharedSuggestionProvider {
    private final ClientPacketListener f_105160_;
    private final Minecraft f_105161_;
    private int f_105162_ = -1;
    @Nullable
    private CompletableFuture<Suggestions> f_105163_;
    private final Set<String> f_240667_ = new HashSet<String>();

    public ClientSuggestionProvider(ClientPacketListener p_105165_, Minecraft p_105166_) {
        this.f_105160_ = p_105165_;
        this.f_105161_ = p_105166_;
    }

    @Override
    public Collection<String> m_5982_() {
        ArrayList $$0 = Lists.newArrayList();
        for (PlayerInfo $$1 : this.f_105160_.m_105142_()) {
            $$0.add($$1.m_105312_().getName());
        }
        return $$0;
    }

    @Override
    public Collection<String> m_240700_() {
        if (this.f_240667_.isEmpty()) {
            return this.m_5982_();
        }
        HashSet<String> $$0 = new HashSet<String>(this.m_5982_());
        $$0.addAll(this.f_240667_);
        return $$0;
    }

    @Override
    public Collection<String> m_6264_() {
        if (this.f_105161_.f_91077_ != null && this.f_105161_.f_91077_.m_6662_() == HitResult.Type.ENTITY) {
            return Collections.singleton(((EntityHitResult)this.f_105161_.f_91077_).m_82443_().m_20149_());
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<String> m_5983_() {
        return this.f_105160_.m_105147_().m_6188_().m_83488_();
    }

    @Override
    public Collection<ResourceLocation> m_5984_() {
        return this.f_105161_.m_91106_().m_120354_();
    }

    @Override
    public Stream<ResourceLocation> m_6860_() {
        return this.f_105160_.m_105141_().m_44073_();
    }

    @Override
    public boolean m_6761_(int p_105178_) {
        LocalPlayer $$1 = this.f_105161_.f_91074_;
        return $$1 != null ? $$1.m_20310_(p_105178_) : p_105178_ == 0;
    }

    @Override
    public CompletableFuture<Suggestions> m_212095_(ResourceKey<? extends Registry<?>> p_212429_, SharedSuggestionProvider.ElementSuggestionType p_212430_, SuggestionsBuilder p_212431_, CommandContext<?> p_212432_) {
        return this.m_5894_().m_6632_(p_212429_).map(p_212427_ -> {
            this.m_212335_((Registry<?>)p_212427_, p_212430_, p_212431_);
            return p_212431_.buildFuture();
        }).orElseGet(() -> this.m_212155_(p_212432_));
    }

    @Override
    public CompletableFuture<Suggestions> m_212155_(CommandContext<?> p_212423_) {
        if (this.f_105163_ != null) {
            this.f_105163_.cancel(false);
        }
        this.f_105163_ = new CompletableFuture();
        int $$1 = ++this.f_105162_;
        this.f_105160_.m_104955_(new ServerboundCommandSuggestionPacket($$1, p_212423_.getInput()));
        return this.f_105163_;
    }

    private static String m_105167_(double p_105168_) {
        return String.format(Locale.ROOT, "%.2f", p_105168_);
    }

    private static String m_105169_(int p_105170_) {
        return Integer.toString(p_105170_);
    }

    @Override
    public Collection<SharedSuggestionProvider.TextCoordinates> m_6265_() {
        HitResult $$0 = this.f_105161_.f_91077_;
        if ($$0 == null || $$0.m_6662_() != HitResult.Type.BLOCK) {
            return SharedSuggestionProvider.super.m_6265_();
        }
        BlockPos $$1 = ((BlockHitResult)$$0).m_82425_();
        return Collections.singleton(new SharedSuggestionProvider.TextCoordinates(ClientSuggestionProvider.m_105169_($$1.m_123341_()), ClientSuggestionProvider.m_105169_($$1.m_123342_()), ClientSuggestionProvider.m_105169_($$1.m_123343_())));
    }

    @Override
    public Collection<SharedSuggestionProvider.TextCoordinates> m_6284_() {
        HitResult $$0 = this.f_105161_.f_91077_;
        if ($$0 == null || $$0.m_6662_() != HitResult.Type.BLOCK) {
            return SharedSuggestionProvider.super.m_6284_();
        }
        Vec3 $$1 = $$0.m_82450_();
        return Collections.singleton(new SharedSuggestionProvider.TextCoordinates(ClientSuggestionProvider.m_105167_($$1.f_82479_), ClientSuggestionProvider.m_105167_($$1.f_82480_), ClientSuggestionProvider.m_105167_($$1.f_82481_)));
    }

    @Override
    public Set<ResourceKey<Level>> m_6553_() {
        return this.f_105160_.m_105151_();
    }

    @Override
    public RegistryAccess m_5894_() {
        return this.f_105160_.m_105152_();
    }

    public void m_105171_(int p_105172_, Suggestions p_105173_) {
        if (p_105172_ == this.f_105162_) {
            this.f_105163_.complete(p_105173_);
            this.f_105163_ = null;
            this.f_105162_ = -1;
        }
    }

    public void m_240713_(ClientboundCustomChatCompletionsPacket.Action p_240810_, List<String> p_240765_) {
        switch (p_240810_) {
            case ADD: {
                this.f_240667_.addAll(p_240765_);
                break;
            }
            case REMOVE: {
                p_240765_.forEach(this.f_240667_::remove);
                break;
            }
            case SET: {
                this.f_240667_.clear();
                this.f_240667_.addAll(p_240765_);
            }
        }
    }
}

