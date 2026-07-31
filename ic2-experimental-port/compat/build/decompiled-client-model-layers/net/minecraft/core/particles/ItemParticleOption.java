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
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ItemParticleOption
implements ParticleOptions {
    public static final ParticleOptions.Deserializer<ItemParticleOption> f_123700_ = new ParticleOptions.Deserializer<ItemParticleOption>(){

        @Override
        public ItemParticleOption m_5739_(ParticleType<ItemParticleOption> p_123721_, StringReader p_123722_) throws CommandSyntaxException {
            p_123722_.expect(' ');
            ItemParser.ItemResult $$2 = ItemParser.m_235305_(HolderLookup.m_235701_(Registry.f_122827_), p_123722_);
            ItemStack $$3 = new ItemInput($$2.f_235328_(), $$2.f_235329_()).m_120980_(1, false);
            return new ItemParticleOption(p_123721_, $$3);
        }

        @Override
        public ItemParticleOption m_6507_(ParticleType<ItemParticleOption> p_123724_, FriendlyByteBuf p_123725_) {
            return new ItemParticleOption(p_123724_, p_123725_.m_130267_());
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
    private final ParticleType<ItemParticleOption> f_123701_;
    private final ItemStack f_123702_;

    public static Codec<ItemParticleOption> m_123710_(ParticleType<ItemParticleOption> p_123711_) {
        return ItemStack.f_41582_.xmap(p_123714_ -> new ItemParticleOption(p_123711_, (ItemStack)p_123714_), p_123709_ -> p_123709_.f_123702_);
    }

    public ItemParticleOption(ParticleType<ItemParticleOption> p_123705_, ItemStack p_123706_) {
        this.f_123701_ = p_123705_;
        this.f_123702_ = p_123706_;
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_123716_) {
        p_123716_.m_130055_(this.f_123702_);
    }

    @Override
    public String m_5942_() {
        return Registry.f_122829_.m_7981_(this.m_6012_()) + " " + new ItemInput(this.f_123702_.m_220173_(), this.f_123702_.m_41783_()).m_120988_();
    }

    public ParticleType<ItemParticleOption> m_6012_() {
        return this.f_123701_;
    }

    public ItemStack m_123718_() {
        return this.f_123702_;
    }
}

