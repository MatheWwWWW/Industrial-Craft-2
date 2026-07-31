/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.GameProfileRepository
 *  com.mojang.authlib.ProfileLookupCallback
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.UUIDUtil;
import org.slf4j.Logger;

public class GameProfileCache {
    private static final Logger f_10964_ = LogUtils.getLogger();
    private static final int f_143955_ = 1000;
    private static final int f_143956_ = 1;
    private static boolean f_10965_;
    private final Map<String, GameProfileInfo> f_10966_ = Maps.newConcurrentMap();
    private final Map<UUID, GameProfileInfo> f_10967_ = Maps.newConcurrentMap();
    private final Map<String, CompletableFuture<Optional<GameProfile>>> f_143957_ = Maps.newConcurrentMap();
    private final GameProfileRepository f_10968_;
    private final Gson f_10969_ = new GsonBuilder().create();
    private final File f_10970_;
    private final AtomicLong f_10971_ = new AtomicLong();
    @Nullable
    private Executor f_143958_;

    public GameProfileCache(GameProfileRepository p_10974_, File p_10975_) {
        this.f_10968_ = p_10974_;
        this.f_10970_ = p_10975_;
        Lists.reverse(this.m_10976_()).forEach(this::m_10979_);
    }

    private void m_10979_(GameProfileInfo p_10980_) {
        UUID $$3;
        GameProfile $$1 = p_10980_.m_11028_();
        p_10980_.m_11029_(this.m_11008_());
        String $$2 = $$1.getName();
        if ($$2 != null) {
            this.f_10966_.put($$2.toLowerCase(Locale.ROOT), p_10980_);
        }
        if (($$3 = $$1.getId()) != null) {
            this.f_10967_.put($$3, p_10980_);
        }
    }

    private static Optional<GameProfile> m_10993_(GameProfileRepository p_10994_, String p_10995_) {
        final AtomicReference $$2 = new AtomicReference();
        ProfileLookupCallback $$3 = new ProfileLookupCallback(){

            public void onProfileLookupSucceeded(GameProfile p_11017_) {
                $$2.set(p_11017_);
            }

            public void onProfileLookupFailed(GameProfile p_11014_, Exception p_11015_) {
                $$2.set(null);
            }
        };
        p_10994_.findProfilesByNames(new String[]{p_10995_}, Agent.MINECRAFT, $$3);
        GameProfile $$4 = (GameProfile)$$2.get();
        if (!GameProfileCache.m_11007_() && $$4 == null) {
            UUID $$5 = UUIDUtil.m_235875_(new GameProfile(null, p_10995_));
            return Optional.of(new GameProfile($$5, p_10995_));
        }
        return Optional.ofNullable($$4);
    }

    public static void m_11004_(boolean p_11005_) {
        f_10965_ = p_11005_;
    }

    private static boolean m_11007_() {
        return f_10965_;
    }

    public void m_10991_(GameProfile p_10992_) {
        Calendar $$1 = Calendar.getInstance();
        $$1.setTime(new Date());
        $$1.add(2, 1);
        Date $$2 = $$1.getTime();
        GameProfileInfo $$3 = new GameProfileInfo(p_10992_, $$2);
        this.m_10979_($$3);
        this.m_11006_();
    }

    private long m_11008_() {
        return this.f_10971_.incrementAndGet();
    }

    public Optional<GameProfile> m_10996_(String p_10997_) {
        Optional<GameProfile> $$5;
        String $$1 = p_10997_.toLowerCase(Locale.ROOT);
        GameProfileInfo $$2 = this.f_10966_.get($$1);
        boolean $$3 = false;
        if ($$2 != null && new Date().getTime() >= $$2.f_11019_.getTime()) {
            this.f_10967_.remove($$2.m_11028_().getId());
            this.f_10966_.remove($$2.m_11028_().getName().toLowerCase(Locale.ROOT));
            $$3 = true;
            $$2 = null;
        }
        if ($$2 != null) {
            $$2.m_11029_(this.m_11008_());
            Optional<GameProfile> $$4 = Optional.of($$2.m_11028_());
        } else {
            $$5 = GameProfileCache.m_10993_(this.f_10968_, $$1);
            if ($$5.isPresent()) {
                this.m_10991_($$5.get());
                $$3 = false;
            }
        }
        if ($$3) {
            this.m_11006_();
        }
        return $$5;
    }

    public void m_143967_(String p_143968_, Consumer<Optional<GameProfile>> p_143969_) {
        if (this.f_143958_ == null) {
            throw new IllegalStateException("No executor");
        }
        CompletableFuture<Optional<GameProfile>> $$2 = this.f_143957_.get(p_143968_);
        if ($$2 != null) {
            this.f_143957_.put(p_143968_, (CompletableFuture<Optional<GameProfile>>)$$2.whenCompleteAsync((p_143984_, p_143985_) -> p_143969_.accept((Optional<GameProfile>)p_143984_), this.f_143958_));
        } else {
            this.f_143957_.put(p_143968_, (CompletableFuture<Optional<GameProfile>>)((CompletableFuture)CompletableFuture.supplyAsync(() -> this.m_10996_(p_143968_), Util.m_183991_()).whenCompleteAsync((p_143965_, p_143966_) -> this.f_143957_.remove(p_143968_), this.f_143958_)).whenCompleteAsync((p_143978_, p_143979_) -> p_143969_.accept((Optional<GameProfile>)p_143978_), this.f_143958_));
        }
    }

    public Optional<GameProfile> m_11002_(UUID p_11003_) {
        GameProfileInfo $$1 = this.f_10967_.get(p_11003_);
        if ($$1 == null) {
            return Optional.empty();
        }
        $$1.m_11029_(this.m_11008_());
        return Optional.of($$1.m_11028_());
    }

    public void m_143974_(Executor p_143975_) {
        this.f_143958_ = p_143975_;
    }

    public void m_196559_() {
        this.f_143958_ = null;
    }

    private static DateFormat m_11009_() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<GameProfileInfo> m_10976_() {
        ArrayList $$0 = Lists.newArrayList();
        try (BufferedReader $$12222 = Files.newReader((File)this.f_10970_, (Charset)StandardCharsets.UTF_8);){
            JsonArray $$2 = (JsonArray)this.f_10969_.fromJson((Reader)$$12222, JsonArray.class);
            if ($$2 == null) {
                ArrayList arrayList = $$0;
                return arrayList;
            }
            DateFormat $$3 = GameProfileCache.m_11009_();
            $$2.forEach(p_143973_ -> GameProfileCache.m_10988_(p_143973_, $$3).ifPresent($$0::add));
            return $$0;
        }
        catch (FileNotFoundException $$12222) {
            return $$0;
        }
        catch (JsonParseException | IOException $$4) {
            f_10964_.warn("Failed to load profile cache {}", (Object)this.f_10970_, (Object)$$4);
        }
        return $$0;
    }

    public void m_11006_() {
        JsonArray $$0 = new JsonArray();
        DateFormat $$1 = GameProfileCache.m_11009_();
        this.m_10977_(1000).forEach(p_143962_ -> $$0.add(GameProfileCache.m_10981_(p_143962_, $$1)));
        String $$2 = this.f_10969_.toJson((JsonElement)$$0);
        try (BufferedWriter $$3 = Files.newWriter((File)this.f_10970_, (Charset)StandardCharsets.UTF_8);){
            $$3.write($$2);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private Stream<GameProfileInfo> m_10977_(int p_10978_) {
        return ImmutableList.copyOf(this.f_10967_.values()).stream().sorted(Comparator.comparing(GameProfileInfo::m_11034_).reversed()).limit(p_10978_);
    }

    private static JsonElement m_10981_(GameProfileInfo p_10982_, DateFormat p_10983_) {
        JsonObject $$2 = new JsonObject();
        $$2.addProperty("name", p_10982_.m_11028_().getName());
        UUID $$3 = p_10982_.m_11028_().getId();
        $$2.addProperty("uuid", $$3 == null ? "" : $$3.toString());
        $$2.addProperty("expiresOn", p_10983_.format(p_10982_.m_11033_()));
        return $$2;
    }

    /*
     * WARNING - void declaration
     */
    private static Optional<GameProfileInfo> m_10988_(JsonElement p_10989_, DateFormat p_10990_) {
        if (p_10989_.isJsonObject()) {
            void $$11;
            JsonObject $$2 = p_10989_.getAsJsonObject();
            JsonElement $$3 = $$2.get("name");
            JsonElement $$4 = $$2.get("uuid");
            JsonElement $$5 = $$2.get("expiresOn");
            if ($$3 == null || $$4 == null) {
                return Optional.empty();
            }
            String $$6 = $$4.getAsString();
            String $$7 = $$3.getAsString();
            Date $$8 = null;
            if ($$5 != null) {
                try {
                    $$8 = p_10990_.parse($$5.getAsString());
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
            if ($$7 == null || $$6 == null || $$8 == null) {
                return Optional.empty();
            }
            try {
                UUID $$9 = UUID.fromString($$6);
            }
            catch (Throwable $$10) {
                return Optional.empty();
            }
            return Optional.of(new GameProfileInfo(new GameProfile((UUID)$$11, $$7), $$8));
        }
        return Optional.empty();
    }

    static class GameProfileInfo {
        private final GameProfile f_11018_;
        final Date f_11019_;
        private volatile long f_11020_;

        GameProfileInfo(GameProfile p_11022_, Date p_11023_) {
            this.f_11018_ = p_11022_;
            this.f_11019_ = p_11023_;
        }

        public GameProfile m_11028_() {
            return this.f_11018_;
        }

        public Date m_11033_() {
            return this.f_11019_;
        }

        public void m_11029_(long p_11030_) {
            this.f_11020_ = p_11030_;
        }

        public long m_11034_() {
            return this.f_11020_;
        }
    }
}

