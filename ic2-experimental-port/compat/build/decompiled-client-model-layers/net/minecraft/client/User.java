/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.util.UUIDTypeAdapter
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class User {
    private final String f_92535_;
    private final String f_92536_;
    private final String f_92537_;
    private final Optional<String> f_193796_;
    private final Optional<String> f_193797_;
    private final Type f_92538_;

    public User(String p_193799_, String p_193800_, String p_193801_, Optional<String> p_193802_, Optional<String> p_193803_, Type p_193804_) {
        this.f_92535_ = p_193799_;
        this.f_92536_ = p_193800_;
        this.f_92537_ = p_193801_;
        this.f_193796_ = p_193802_;
        this.f_193797_ = p_193803_;
        this.f_92538_ = p_193804_;
    }

    public String m_92544_() {
        return "token:" + this.f_92537_ + ":" + this.f_92536_;
    }

    public String m_92545_() {
        return this.f_92536_;
    }

    public String m_92546_() {
        return this.f_92535_;
    }

    public String m_92547_() {
        return this.f_92537_;
    }

    public Optional<String> m_193805_() {
        return this.f_193797_;
    }

    public Optional<String> m_193806_() {
        return this.f_193796_;
    }

    @Nullable
    public UUID m_240411_() {
        try {
            return UUIDTypeAdapter.fromString((String)this.m_92545_());
        }
        catch (IllegalArgumentException $$0) {
            return null;
        }
    }

    public GameProfile m_92548_() {
        return new GameProfile(this.m_240411_(), this.m_92546_());
    }

    public Type m_168638_() {
        return this.f_92538_;
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type LEGACY = new Type("legacy");
        public static final /* enum */ Type MOJANG = new Type("mojang");
        public static final /* enum */ Type MSA = new Type("msa");
        private static final Map<String, Type> f_92551_;
        private final String f_92552_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_92564_) {
            return Enum.valueOf(Type.class, p_92564_);
        }

        private Type(String p_92558_) {
            this.f_92552_ = p_92558_;
        }

        @Nullable
        public static Type m_92561_(String p_92562_) {
            return f_92551_.get(p_92562_.toLowerCase(Locale.ROOT));
        }

        public String m_193808_() {
            return this.f_92552_;
        }

        private static /* synthetic */ Type[] m_168639_() {
            return new Type[]{LEGACY, MOJANG, MSA};
        }

        static {
            $VALUES = Type.m_168639_();
            f_92551_ = Arrays.stream(Type.values()).collect(Collectors.toMap(p_92560_ -> p_92560_.f_92552_, Function.identity()));
        }
    }
}

