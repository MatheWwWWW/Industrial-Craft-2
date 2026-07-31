/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.gson.JsonParser
 *  com.mojang.authlib.exceptions.MinecraftClientException
 *  com.mojang.authlib.minecraft.InsecurePublicKeyException$MissingException
 *  com.mojang.authlib.minecraft.UserApiService
 *  com.mojang.authlib.yggdrasil.response.KeyPairResponse
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.base.Strings;
import com.google.gson.JsonParser;
import com.mojang.authlib.exceptions.MinecraftClientException;
import com.mojang.authlib.minecraft.InsecurePublicKeyException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.response.KeyPairResponse;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.PublicKey;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.Signer;
import net.minecraft.world.entity.player.ProfileKeyPair;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.slf4j.Logger;

public class ProfileKeyPairManager {
    private static final Logger f_233765_ = LogUtils.getLogger();
    private static final Path f_233766_ = Path.of("profilekeys", new String[0]);
    private final UserApiService f_243348_;
    private final Path f_233767_;
    private CompletableFuture<Optional<Result>> f_243342_;

    public ProfileKeyPairManager(UserApiService p_233772_, UUID p_233773_, Path p_233774_) {
        this.f_243348_ = p_233772_;
        this.f_233767_ = p_233774_.resolve(f_233766_).resolve(p_233773_ + ".json");
        this.f_243342_ = CompletableFuture.supplyAsync(() -> this.m_233798_().filter(p_233788_ -> !p_233788_.f_219763_().f_219781_().m_219810_()), Util.m_183991_()).thenCompose(this::m_243398_);
    }

    public CompletableFuture<Optional<ProfilePublicKey.Data>> m_243369_() {
        this.f_243342_ = this.f_243342_.thenCompose(p_243400_ -> {
            Optional<ProfileKeyPair> $$1 = p_243400_.map(Result::f_243349_);
            return this.m_243398_($$1);
        });
        return this.f_243342_.thenApply(p_233792_ -> p_233792_.map(p_243371_ -> p_243371_.f_243349_().f_219763_().f_219781_()));
    }

    private CompletableFuture<Optional<Result>> m_243398_(Optional<ProfileKeyPair> p_243401_) {
        return CompletableFuture.supplyAsync(() -> {
            if (p_243401_.isPresent() && !((ProfileKeyPair)p_243401_.get()).m_219770_()) {
                if (!SharedConstants.f_136183_) {
                    this.m_233776_(null);
                } else {
                    return p_243401_;
                }
            }
            try {
                ProfileKeyPair $$1 = this.m_233789_(this.f_243348_);
                this.m_233776_($$1);
                return Optional.of($$1);
            }
            catch (MinecraftClientException | IOException | CryptException $$2) {
                f_233765_.error("Failed to retrieve profile key pair", $$2);
                this.m_233776_(null);
                return p_243401_;
            }
        }, Util.m_183991_()).thenApply(p_233785_ -> p_233785_.map(Result::new));
    }

    private Optional<ProfileKeyPair> m_233798_() {
        Optional optional;
        block9: {
            if (Files.notExists(this.f_233767_, new LinkOption[0])) {
                return Optional.empty();
            }
            BufferedReader $$0 = Files.newBufferedReader(this.f_233767_);
            try {
                optional = ProfileKeyPair.f_219761_.parse((DynamicOps)JsonOps.INSTANCE, (Object)JsonParser.parseReader((Reader)$$0)).result();
                if ($$0 == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if ($$0 != null) {
                        try {
                            $$0.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception $$1) {
                    f_233765_.error("Failed to read profile key pair file {}", (Object)this.f_233767_, (Object)$$1);
                    return Optional.empty();
                }
            }
            $$0.close();
        }
        return optional;
    }

    private void m_233776_(@Nullable ProfileKeyPair p_233777_) {
        try {
            Files.deleteIfExists(this.f_233767_);
        }
        catch (IOException $$1) {
            f_233765_.error("Failed to delete profile key pair file {}", (Object)this.f_233767_, (Object)$$1);
        }
        if (p_233777_ == null) {
            return;
        }
        if (!SharedConstants.f_136183_) {
            return;
        }
        ProfileKeyPair.f_219761_.encodeStart((DynamicOps)JsonOps.INSTANCE, (Object)p_233777_).result().ifPresent(p_233779_ -> {
            try {
                Files.createDirectories(this.f_233767_.getParent(), new FileAttribute[0]);
                Files.writeString(this.f_233767_, (CharSequence)p_233779_.toString(), new OpenOption[0]);
            }
            catch (Exception $$1) {
                f_233765_.error("Failed to write profile key pair file {}", (Object)this.f_233767_, (Object)$$1);
            }
        });
    }

    private ProfileKeyPair m_233789_(UserApiService p_233790_) throws CryptException, IOException {
        KeyPairResponse $$1 = p_233790_.getKeyPair();
        if ($$1 != null) {
            ProfilePublicKey.Data $$2 = ProfileKeyPairManager.m_233782_($$1);
            return new ProfileKeyPair(Crypt.m_216069_($$1.getPrivateKey()), new ProfilePublicKey($$2), Instant.parse($$1.getRefreshedAfter()));
        }
        throw new IOException("Could not retrieve profile key pair");
    }

    private static ProfilePublicKey.Data m_233782_(KeyPairResponse p_233783_) throws CryptException {
        if (Strings.isNullOrEmpty((String)p_233783_.getPublicKey()) || p_233783_.getPublicKeySignature() == null || p_233783_.getPublicKeySignature().array().length == 0) {
            throw new CryptException((Throwable)new InsecurePublicKeyException.MissingException());
        }
        try {
            Instant $$1 = Instant.parse(p_233783_.getExpiresAt());
            PublicKey $$2 = Crypt.m_216080_(p_233783_.getPublicKey());
            ByteBuffer $$3 = p_233783_.getPublicKeySignature();
            return new ProfilePublicKey.Data($$1, $$2, $$3.array());
        }
        catch (IllegalArgumentException | DateTimeException $$4) {
            throw new CryptException($$4);
        }
    }

    @Nullable
    public Signer m_233775_() {
        return this.f_243342_.join().map(Result::f_243351_).orElse(null);
    }

    public Optional<ProfilePublicKey> m_233786_() {
        return this.f_243342_.join().map(p_243372_ -> p_243372_.f_243349_().f_219763_());
    }

    record Result(ProfileKeyPair f_243349_, Signer f_243351_) {
        public Result(ProfileKeyPair p_243382_) {
            this(p_243382_, Signer.m_216387_(p_243382_.f_219762_(), "SHA256withRSA"));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Result.class, "keyPair;signer", "f_243349_", "f_243351_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Result.class, "keyPair;signer", "f_243349_", "f_243351_"}, this);
        }

        @Override
        public final boolean equals(Object p_243377_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Result.class, "keyPair;signer", "f_243349_", "f_243351_"}, this, p_243377_);
        }
    }
}

