/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableList
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.internal.Streams
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonWriter
 *  com.mojang.authlib.GameProfile
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.network;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.TextFilter;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.thread.ProcessorMailbox;
import org.slf4j.Logger;

public class TextFilterClient
implements AutoCloseable {
    private static final Logger f_10098_ = LogUtils.getLogger();
    private static final AtomicInteger f_10099_ = new AtomicInteger(1);
    private static final ThreadFactory f_10100_ = p_10148_ -> {
        Thread $$1 = new Thread(p_10148_);
        $$1.setName("Chat-Filter-Worker-" + f_10099_.getAndIncrement());
        return $$1;
    };
    private static final String f_215270_ = "v1/chat";
    private final URL f_10101_;
    private final MessageEncoder f_215271_;
    final URL f_10102_;
    final JoinOrLeaveEncoder f_215272_;
    final URL f_10103_;
    final JoinOrLeaveEncoder f_215273_;
    private final String f_10104_;
    final IgnoreStrategy f_10107_;
    final ExecutorService f_10108_;

    private TextFilterClient(URL p_215275_, MessageEncoder p_215276_, URL p_215277_, JoinOrLeaveEncoder p_215278_, URL p_215279_, JoinOrLeaveEncoder p_215280_, String p_215281_, IgnoreStrategy p_215282_, int p_215283_) {
        this.f_10104_ = p_215281_;
        this.f_10107_ = p_215282_;
        this.f_10101_ = p_215275_;
        this.f_215271_ = p_215276_;
        this.f_10102_ = p_215277_;
        this.f_215272_ = p_215278_;
        this.f_10103_ = p_215279_;
        this.f_215273_ = p_215280_;
        this.f_10108_ = Executors.newFixedThreadPool(p_215283_, f_10100_);
    }

    private static URL m_212245_(URI p_212246_, @Nullable JsonObject p_212247_, String p_212248_, String p_212249_) throws MalformedURLException {
        String $$4 = TextFilterClient.m_215294_(p_212247_, p_212248_, p_212249_);
        return p_212246_.resolve("/" + $$4).toURL();
    }

    private static String m_215294_(@Nullable JsonObject p_215295_, String p_215296_, String p_215297_) {
        return p_215295_ != null ? GsonHelper.m_13851_(p_215295_, p_215296_, p_215297_) : p_215297_;
    }

    @Nullable
    public static TextFilterClient m_143736_(String p_143737_) {
        if (Strings.isNullOrEmpty((String)p_143737_)) {
            return null;
        }
        try {
            MessageEncoder $$18;
            JsonObject $$1 = GsonHelper.m_13864_(p_143737_);
            URI $$2 = new URI(GsonHelper.m_13906_($$1, "apiServer"));
            String $$3 = GsonHelper.m_13906_($$1, "apiKey");
            if ($$3.isEmpty()) {
                throw new IllegalArgumentException("Missing API key");
            }
            int $$4 = GsonHelper.m_13824_($$1, "ruleId", 1);
            String $$5 = GsonHelper.m_13851_($$1, "serverId", "");
            String $$6 = GsonHelper.m_13851_($$1, "roomId", "Java:Chat");
            int $$7 = GsonHelper.m_13824_($$1, "hashesToDrop", -1);
            int $$8 = GsonHelper.m_13824_($$1, "maxConcurrentRequests", 7);
            JsonObject $$9 = GsonHelper.m_13841_($$1, "endpoints", null);
            String $$10 = TextFilterClient.m_215294_($$9, "chat", f_215270_);
            boolean $$11 = $$10.equals(f_215270_);
            URL $$12 = $$2.resolve("/" + $$10).toURL();
            URL $$13 = TextFilterClient.m_212245_($$2, $$9, "join", "v1/join");
            URL $$14 = TextFilterClient.m_212245_($$2, $$9, "leave", "v1/leave");
            JoinOrLeaveEncoder $$15 = p_215310_ -> {
                JsonObject $$3 = new JsonObject();
                $$3.addProperty("server", $$5);
                $$3.addProperty("room", $$6);
                $$3.addProperty("user_id", p_215310_.getId().toString());
                $$3.addProperty("user_display_name", p_215310_.getName());
                return $$3;
            };
            if ($$11) {
                MessageEncoder $$16 = (p_238214_, p_238215_) -> {
                    JsonObject $$5 = new JsonObject();
                    $$5.addProperty("rule", (Number)$$4);
                    $$5.addProperty("server", $$5);
                    $$5.addProperty("room", $$6);
                    $$5.addProperty("player", p_238214_.getId().toString());
                    $$5.addProperty("player_display_name", p_238214_.getName());
                    $$5.addProperty("text", p_238215_);
                    $$5.addProperty("language", "*");
                    return $$5;
                };
            } else {
                String $$17 = String.valueOf($$4);
                $$18 = (p_238220_, p_238221_) -> {
                    JsonObject $$5 = new JsonObject();
                    $$5.addProperty("rule_id", $$17);
                    $$5.addProperty("category", $$5);
                    $$5.addProperty("subcategory", $$6);
                    $$5.addProperty("user_id", p_238220_.getId().toString());
                    $$5.addProperty("user_display_name", p_238220_.getName());
                    $$5.addProperty("text", p_238221_);
                    $$5.addProperty("language", "*");
                    return $$5;
                };
            }
            IgnoreStrategy $$19 = IgnoreStrategy.m_143744_($$7);
            String $$20 = Base64.getEncoder().encodeToString($$3.getBytes(StandardCharsets.US_ASCII));
            return new TextFilterClient($$12, $$18, $$13, $$15, $$14, $$15, $$20, $$19, $$8);
        }
        catch (Exception $$21) {
            f_10098_.warn("Failed to parse chat filter config {}", (Object)p_143737_, (Object)$$21);
            return null;
        }
    }

    void m_215302_(GameProfile p_215303_, URL p_215304_, JoinOrLeaveEncoder p_215305_, Executor p_215306_) {
        p_215306_.execute(() -> {
            JsonObject $$3 = p_215305_.m_215317_(p_215303_);
            try {
                this.m_10151_($$3, p_215304_);
            }
            catch (Exception $$4) {
                f_10098_.warn("Failed to send join/leave packet to {} for player {}", new Object[]{p_215304_, p_215303_, $$4});
            }
        });
    }

    CompletableFuture<FilteredText> m_10136_(GameProfile p_10137_, String p_10138_, IgnoreStrategy p_10139_, Executor p_10140_) {
        if (p_10138_.isEmpty()) {
            return CompletableFuture.completedFuture(FilteredText.f_243020_);
        }
        return CompletableFuture.supplyAsync(() -> {
            JsonObject $$3 = this.f_215271_.m_215319_(p_10137_, p_10138_);
            try {
                JsonObject $$4 = this.m_10127_($$3, this.f_10101_);
                boolean $$5 = GsonHelper.m_13855_($$4, "response", false);
                if ($$5) {
                    return FilteredText.m_243054_(p_10138_);
                }
                String $$6 = GsonHelper.m_13851_($$4, "hashed", null);
                if ($$6 == null) {
                    return FilteredText.m_243131_(p_10138_);
                }
                JsonArray $$7 = GsonHelper.m_13933_($$4, "hashes");
                FilterMask $$8 = this.m_243083_(p_10138_, $$7, p_10139_);
                return new FilteredText(p_10138_, $$8);
            }
            catch (Exception $$9) {
                f_10098_.warn("Failed to validate message '{}'", (Object)p_10138_, (Object)$$9);
                return FilteredText.m_243131_(p_10138_);
            }
        }, p_10140_);
    }

    private FilterMask m_243083_(String p_243283_, JsonArray p_243222_, IgnoreStrategy p_243237_) {
        if (p_243222_.isEmpty()) {
            return FilterMask.f_242999_;
        }
        if (p_243237_.m_10171_(p_243283_, p_243222_.size())) {
            return FilterMask.f_243007_;
        }
        FilterMask $$3 = new FilterMask(p_243283_.length());
        for (int $$4 = 0; $$4 < p_243222_.size(); ++$$4) {
            $$3.m_243123_(p_243222_.get($$4).getAsInt());
        }
        return $$3;
    }

    @Override
    public void close() {
        this.f_10108_.shutdownNow();
    }

    private void m_10145_(InputStream p_10146_) throws IOException {
        byte[] $$1 = new byte[1024];
        while (p_10146_.read($$1) != -1) {
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JsonObject m_10127_(JsonObject p_10128_, URL p_10129_) throws IOException {
        HttpURLConnection $$2 = this.m_10156_(p_10128_, p_10129_);
        try (InputStream $$3 = $$2.getInputStream();){
            JsonObject jsonObject;
            if ($$2.getResponseCode() == 204) {
                JsonObject jsonObject2 = new JsonObject();
                return jsonObject2;
            }
            try {
                jsonObject = Streams.parse((JsonReader)new JsonReader((Reader)new InputStreamReader($$3, StandardCharsets.UTF_8))).getAsJsonObject();
            }
            catch (Throwable throwable) {
                this.m_10145_($$3);
                throw throwable;
            }
            this.m_10145_($$3);
            return jsonObject;
        }
    }

    private void m_10151_(JsonObject p_10152_, URL p_10153_) throws IOException {
        HttpURLConnection $$2 = this.m_10156_(p_10152_, p_10153_);
        try (InputStream $$3 = $$2.getInputStream();){
            this.m_10145_($$3);
        }
    }

    private HttpURLConnection m_10156_(JsonObject p_10157_, URL p_10158_) throws IOException {
        HttpURLConnection $$2 = (HttpURLConnection)p_10158_.openConnection();
        $$2.setConnectTimeout(15000);
        $$2.setReadTimeout(2000);
        $$2.setUseCaches(false);
        $$2.setDoOutput(true);
        $$2.setDoInput(true);
        $$2.setRequestMethod("POST");
        $$2.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        $$2.setRequestProperty("Accept", "application/json");
        $$2.setRequestProperty("Authorization", "Basic " + this.f_10104_);
        $$2.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.m_183709_().getName());
        try (OutputStreamWriter $$3 = new OutputStreamWriter($$2.getOutputStream(), StandardCharsets.UTF_8);
             JsonWriter $$4 = new JsonWriter((Writer)$$3);){
            Streams.write((JsonElement)p_10157_, (JsonWriter)$$4);
        }
        int $$5 = $$2.getResponseCode();
        if ($$5 < 200 || $$5 >= 300) {
            throw new RequestFailedException($$5 + " " + $$2.getResponseMessage());
        }
        return $$2;
    }

    public TextFilter m_10134_(GameProfile p_10135_) {
        return new PlayerContext(p_10135_);
    }

    @FunctionalInterface
    public static interface IgnoreStrategy {
        public static final IgnoreStrategy f_10162_ = (p_10169_, p_10170_) -> false;
        public static final IgnoreStrategy f_10163_ = (p_10166_, p_10167_) -> p_10166_.length() == p_10167_;

        public static IgnoreStrategy m_143738_(int p_143739_) {
            return (p_143742_, p_143743_) -> p_143743_ >= p_143739_;
        }

        public static IgnoreStrategy m_143744_(int p_143745_) {
            return switch (p_143745_) {
                case -1 -> f_10162_;
                case 0 -> f_10163_;
                default -> IgnoreStrategy.m_143738_(p_143745_);
            };
        }

        public boolean m_10171_(String var1, int var2);
    }

    @FunctionalInterface
    static interface MessageEncoder {
        public JsonObject m_215319_(GameProfile var1, String var2);
    }

    @FunctionalInterface
    static interface JoinOrLeaveEncoder {
        public JsonObject m_215317_(GameProfile var1);
    }

    public static class RequestFailedException
    extends RuntimeException {
        RequestFailedException(String p_10199_) {
            super(p_10199_);
        }
    }

    class PlayerContext
    implements TextFilter {
        private final GameProfile f_10175_;
        private final Executor f_10176_;

        PlayerContext(GameProfile p_10179_) {
            this.f_10175_ = p_10179_;
            ProcessorMailbox<Runnable> $$1 = ProcessorMailbox.m_18751_(TextFilterClient.this.f_10108_, "chat stream for " + p_10179_.getName());
            this.f_10176_ = $$1::m_6937_;
        }

        @Override
        public void m_7674_() {
            TextFilterClient.this.m_215302_(this.f_10175_, TextFilterClient.this.f_10102_, TextFilterClient.this.f_215272_, this.f_10176_);
        }

        @Override
        public void m_7670_() {
            TextFilterClient.this.m_215302_(this.f_10175_, TextFilterClient.this.f_10103_, TextFilterClient.this.f_215273_, this.f_10176_);
        }

        @Override
        public CompletableFuture<List<FilteredText>> m_5925_(List<String> p_10190_) {
            List $$1 = (List)p_10190_.stream().map(p_10195_ -> TextFilterClient.this.m_10136_(this.f_10175_, (String)p_10195_, TextFilterClient.this.f_10107_, this.f_10176_)).collect(ImmutableList.toImmutableList());
            return Util.m_143840_($$1).exceptionally(p_143747_ -> ImmutableList.of());
        }

        @Override
        public CompletableFuture<FilteredText> m_6770_(String p_10186_) {
            return TextFilterClient.this.m_10136_(this.f_10175_, p_10186_, TextFilterClient.this.f_10107_, this.f_10176_);
        }
    }
}

