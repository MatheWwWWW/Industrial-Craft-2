/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.serialization.Codec
 */
package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockParticleOption
implements ParticleOptions {
    public static final ParticleOptions.Deserializer<BlockParticleOption> f_123624_ = new ParticleOptions.Deserializer<BlockParticleOption>(){

        @Override
        public BlockParticleOption m_5739_(ParticleType<BlockParticleOption> p_123645_, StringReader p_123646_) throws CommandSyntaxException {
            p_123646_.expect(' ');
            return new BlockParticleOption(p_123645_, BlockStateParser.m_234700_(Registry.f_122824_, p_123646_, false).f_234748_());
        }

        @Override
        public BlockParticleOption m_6507_(ParticleType<BlockParticleOption> p_123648_, FriendlyByteBuf p_123649_) {
            return new BlockParticleOption(p_123648_, p_123649_.m_236816_(Block.f_49791_));
        }

        @Override
        public /* synthetic */ ParticleOptions m_6507_(ParticleType particleType, FriendlyByteBuf friendlyByteBuf) {
            return this.m_6507_(particleType, friendlyByteBuf);
        }

        @Override
        public /* synthetic */ ParticleOptions m_5739_(ParticleType particleType, StringReader stringReader) throws CommandSyntaxException {
            return this.m_5739_(particleType, stringReader);
        }
    };
    private final ParticleType<BlockParticleOption> f_123625_;
    private final BlockState f_123626_;

    public static Codec<BlockParticleOption> m_123634_(ParticleType<BlockParticleOption> p_123635_) {
        return BlockState.f_61039_.xmap(p_123638_ -> new BlockParticleOption(p_123635_, (BlockState)p_123638_), p_123633_ -> p_123633_.f_123626_);
    }

    public BlockParticleOption(ParticleType<BlockParticleOption> p_123629_, BlockState p_123630_) {
        this.f_123625_ = p_123629_;
        this.f_123626_ = p_123630_;
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_123640_) {
        p_123640_.m_236818_(Block.f_49791_, this.f_123626_);
    }

    @Override
    public String m_5942_() {
        return Registry.f_122829_.m_7981_(this.m_6012_()) + " " + BlockStateParser.m_116769_(this.f_123626_);
    }

    public ParticleType<BlockParticleOption> m_6012_() {
        return this.f_123625_;
    }

    public BlockState m_123642_() {
        return this.f_123626_;
    }
}

