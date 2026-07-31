/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

public class ServerStatus {
    public static final int f_179835_ = 64;
    public static final int f_179836_ = 64;
    @Nullable
    private Component f_134900_;
    @Nullable
    private Players f_134901_;
    @Nullable
    private Version f_134902_;
    @Nullable
    private String f_134903_;
    private boolean f_238077_;
    private boolean f_242955_;

    @Nullable
    public Component m_134905_() {
        return this.f_134900_;
    }

    public void m_134908_(Component p_134909_) {
        this.f_134900_ = p_134909_;
    }

    @Nullable
    public Players m_134914_() {
        return this.f_134901_;
    }

    public void m_134910_(Players p_134911_) {
        this.f_134901_ = p_134911_;
    }

    @Nullable
    public Version m_134915_() {
        return this.f_134902_;
    }

    public void m_134912_(Version p_134913_) {
        this.f_134902_ = p_134913_;
    }

    public void m_134906_(String p_134907_) {
        this.f_134903_ = p_134907_;
    }

    @Nullable
    public String m_134916_() {
        return this.f_134903_;
    }

    public void m_238078_(boolean p_238079_) {
        this.f_238077_ = p_238079_;
    }

    public boolean m_238080_() {
        return this.f_238077_;
    }

    public void m_242958_(boolean p_242968_) {
        this.f_242955_ = p_242968_;
    }

    public boolean m_242963_() {
        return this.f_242955_;
    }

    public static class Players {
        private final int f_134917_;
        private final int f_134918_;
        @Nullable
        private GameProfile[] f_134919_;

        public Players(int p_134921_, int p_134922_) {
            this.f_134917_ = p_134921_;
            this.f_134918_ = p_134922_;
        }

        public int m_134923_() {
            return this.f_134917_;
        }

        public int m_134926_() {
            return this.f_134918_;
        }

        @Nullable
        public GameProfile[] m_134927_() {
            return this.f_134919_;
        }

        public void m_134924_(GameProfile[] p_134925_) {
            this.f_134919_ = p_134925_;
        }

        public static class Serializer
        implements JsonDeserializer<Players>,
        JsonSerializer<Players> {
            public Players deserialize(JsonElement p_134930_, Type p_134931_, JsonDeserializationContext p_134932_) throws JsonParseException {
                JsonArray $$5;
                JsonObject $$3 = GsonHelper.m_13918_(p_134930_, "players");
                Players $$4 = new Players(GsonHelper.m_13927_($$3, "max"), GsonHelper.m_13927_($$3, "online"));
                if (GsonHelper.m_13885_($$3, "sample") && ($$5 = GsonHelper.m_13933_($$3, "sample")).size() > 0) {
                    GameProfile[] $$6 = new GameProfile[$$5.size()];
                    for (int $$7 = 0; $$7 < $$6.length; ++$$7) {
                        JsonObject $$8 = GsonHelper.m_13918_($$5.get($$7), "player[" + $$7 + "]");
                        String $$9 = GsonHelper.m_13906_($$8, "id");
                        $$6[$$7] = new GameProfile(UUID.fromString($$9), GsonHelper.m_13906_($$8, "name"));
                    }
                    $$4.m_134924_($$6);
                }
                return $$4;
            }

            public JsonElement serialize(Players p_134934_, Type p_134935_, JsonSerializationContext p_134936_) {
                JsonObject $$3 = new JsonObject();
                $$3.addProperty("max", (Number)p_134934_.m_134923_());
                $$3.addProperty("online", (Number)p_134934_.m_134926_());
                GameProfile[] $$4 = p_134934_.m_134927_();
                if ($$4 != null && $$4.length > 0) {
                    JsonArray $$5 = new JsonArray();
                    for (int $$6 = 0; $$6 < $$4.length; ++$$6) {
                        JsonObject $$7 = new JsonObject();
                        UUID $$8 = $$4[$$6].getId();
                        $$7.addProperty("id", $$8 == null ? "" : $$8.toString());
                        $$7.addProperty("name", $$4[$$6].getName());
                        $$5.add((JsonElement)$$7);
                    }
                    $$3.add("sample", (JsonElement)$$5);
                }
                return $$3;
            }

            public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
                return this.serialize((Players)object, type, jsonSerializationContext);
            }

            public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }
        }
    }

    public static class Version {
        private final String f_134962_;
        private final int f_134963_;

        public Version(String p_134965_, int p_134966_) {
            this.f_134962_ = p_134965_;
            this.f_134963_ = p_134966_;
        }

        public String m_134967_() {
            return this.f_134962_;
        }

        public int m_134968_() {
            return this.f_134963_;
        }

        public static class Serializer
        implements JsonDeserializer<Version>,
        JsonSerializer<Version> {
            public Version deserialize(JsonElement p_134971_, Type p_134972_, JsonDeserializationContext p_134973_) throws JsonParseException {
                JsonObject $$3 = GsonHelper.m_13918_(p_134971_, "version");
                return new Version(GsonHelper.m_13906_($$3, "name"), GsonHelper.m_13927_($$3, "protocol"));
            }

            public JsonElement serialize(Version p_134975_, Type p_134976_, JsonSerializationContext p_134977_) {
                JsonObject $$3 = new JsonObject();
                $$3.addProperty("name", p_134975_.m_134967_());
                $$3.addProperty("protocol", (Number)p_134975_.m_134968_());
                return $$3;
            }

            public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
                return this.serialize((Version)object, type, jsonSerializationContext);
            }

            public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }
        }
    }

    public static class Serializer
    implements JsonDeserializer<ServerStatus>,
    JsonSerializer<ServerStatus> {
        public ServerStatus deserialize(JsonElement p_134947_, Type p_134948_, JsonDeserializationContext p_134949_) throws JsonParseException {
            JsonObject $$3 = GsonHelper.m_13918_(p_134947_, "status");
            ServerStatus $$4 = new ServerStatus();
            if ($$3.has("description")) {
                $$4.m_134908_((Component)p_134949_.deserialize($$3.get("description"), Component.class));
            }
            if ($$3.has("players")) {
                $$4.m_134910_((Players)p_134949_.deserialize($$3.get("players"), Players.class));
            }
            if ($$3.has("version")) {
                $$4.m_134912_((Version)p_134949_.deserialize($$3.get("version"), Version.class));
            }
            if ($$3.has("favicon")) {
                $$4.m_134906_(GsonHelper.m_13906_($$3, "favicon"));
            }
            if ($$3.has("previewsChat")) {
                $$4.m_238078_(GsonHelper.m_13912_($$3, "previewsChat"));
            }
            if ($$3.has("enforcesSecureChat")) {
                $$4.m_242958_(GsonHelper.m_13912_($$3, "enforcesSecureChat"));
            }
            return $$4;
        }

        public JsonElement serialize(ServerStatus p_134951_, Type p_134952_, JsonSerializationContext p_134953_) {
            JsonObject $$3 = new JsonObject();
            $$3.addProperty("previewsChat", Boolean.valueOf(p_134951_.m_238080_()));
            $$3.addProperty("enforcesSecureChat", Boolean.valueOf(p_134951_.m_242963_()));
            if (p_134951_.m_134905_() != null) {
                $$3.add("description", p_134953_.serialize((Object)p_134951_.m_134905_()));
            }
            if (p_134951_.m_134914_() != null) {
                $$3.add("players", p_134953_.serialize((Object)p_134951_.m_134914_()));
            }
            if (p_134951_.m_134915_() != null) {
                $$3.add("version", p_134953_.serialize((Object)p_134951_.m_134915_()));
            }
            if (p_134951_.m_134916_() != null) {
                $$3.addProperty("favicon", p_134951_.m_134916_());
            }
            return $$3;
        }

        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ServerStatus)object, type, jsonSerializationContext);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

