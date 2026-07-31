/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.FrameType;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DisplayInfo {
    private final Component f_14958_;
    private final Component f_14959_;
    private final ItemStack f_14960_;
    @Nullable
    private final ResourceLocation f_14961_;
    private final FrameType f_14962_;
    private final boolean f_14963_;
    private final boolean f_14964_;
    private final boolean f_14965_;
    private float f_14966_;
    private float f_14967_;

    public DisplayInfo(ItemStack p_14969_, Component p_14970_, Component p_14971_, @Nullable ResourceLocation p_14972_, FrameType p_14973_, boolean p_14974_, boolean p_14975_, boolean p_14976_) {
        this.f_14958_ = p_14970_;
        this.f_14959_ = p_14971_;
        this.f_14960_ = p_14969_;
        this.f_14961_ = p_14972_;
        this.f_14962_ = p_14973_;
        this.f_14963_ = p_14974_;
        this.f_14964_ = p_14975_;
        this.f_14965_ = p_14976_;
    }

    public void m_14978_(float p_14979_, float p_14980_) {
        this.f_14966_ = p_14979_;
        this.f_14967_ = p_14980_;
    }

    public Component m_14977_() {
        return this.f_14958_;
    }

    public Component m_14985_() {
        return this.f_14959_;
    }

    public ItemStack m_14990_() {
        return this.f_14960_;
    }

    @Nullable
    public ResourceLocation m_14991_() {
        return this.f_14961_;
    }

    public FrameType m_14992_() {
        return this.f_14962_;
    }

    public float m_14993_() {
        return this.f_14966_;
    }

    public float m_14994_() {
        return this.f_14967_;
    }

    public boolean m_14995_() {
        return this.f_14963_;
    }

    public boolean m_14996_() {
        return this.f_14964_;
    }

    public boolean m_14997_() {
        return this.f_14965_;
    }

    public static DisplayInfo m_14981_(JsonObject p_14982_) {
        MutableComponent $$1 = Component.Serializer.m_130691_(p_14982_.get("title"));
        MutableComponent $$2 = Component.Serializer.m_130691_(p_14982_.get("description"));
        if ($$1 == null || $$2 == null) {
            throw new JsonSyntaxException("Both title and description must be set");
        }
        ItemStack $$3 = DisplayInfo.m_14986_(GsonHelper.m_13930_(p_14982_, "icon"));
        ResourceLocation $$4 = p_14982_.has("background") ? new ResourceLocation(GsonHelper.m_13906_(p_14982_, "background")) : null;
        FrameType $$5 = p_14982_.has("frame") ? FrameType.m_15549_(GsonHelper.m_13906_(p_14982_, "frame")) : FrameType.TASK;
        boolean $$6 = GsonHelper.m_13855_(p_14982_, "show_toast", true);
        boolean $$7 = GsonHelper.m_13855_(p_14982_, "announce_to_chat", true);
        boolean $$8 = GsonHelper.m_13855_(p_14982_, "hidden", false);
        return new DisplayInfo($$3, $$1, $$2, $$4, $$5, $$6, $$7, $$8);
    }

    private static ItemStack m_14986_(JsonObject p_14987_) {
        if (!p_14987_.has("item")) {
            throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
        }
        Item $$1 = GsonHelper.m_13909_(p_14987_, "item");
        if (p_14987_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }
        ItemStack $$2 = new ItemStack($$1);
        if (p_14987_.has("nbt")) {
            try {
                CompoundTag $$3 = TagParser.m_129359_(GsonHelper.m_13805_(p_14987_.get("nbt"), "nbt"));
                $$2.m_41751_($$3);
            }
            catch (CommandSyntaxException $$4) {
                throw new JsonSyntaxException("Invalid nbt tag: " + $$4.getMessage());
            }
        }
        return $$2;
    }

    public void m_14983_(FriendlyByteBuf p_14984_) {
        p_14984_.m_130083_(this.f_14958_);
        p_14984_.m_130083_(this.f_14959_);
        p_14984_.m_130055_(this.f_14960_);
        p_14984_.m_130068_(this.f_14962_);
        int $$1 = 0;
        if (this.f_14961_ != null) {
            $$1 |= 1;
        }
        if (this.f_14963_) {
            $$1 |= 2;
        }
        if (this.f_14965_) {
            $$1 |= 4;
        }
        p_14984_.writeInt($$1);
        if (this.f_14961_ != null) {
            p_14984_.m_130085_(this.f_14961_);
        }
        p_14984_.writeFloat(this.f_14966_);
        p_14984_.writeFloat(this.f_14967_);
    }

    public static DisplayInfo m_14988_(FriendlyByteBuf p_14989_) {
        Component $$1 = p_14989_.m_130238_();
        Component $$2 = p_14989_.m_130238_();
        ItemStack $$3 = p_14989_.m_130267_();
        FrameType $$4 = p_14989_.m_130066_(FrameType.class);
        int $$5 = p_14989_.readInt();
        ResourceLocation $$6 = ($$5 & 1) != 0 ? p_14989_.m_130281_() : null;
        boolean $$7 = ($$5 & 2) != 0;
        boolean $$8 = ($$5 & 4) != 0;
        DisplayInfo $$9 = new DisplayInfo($$3, $$1, $$2, $$6, $$4, $$7, false, $$8);
        $$9.m_14978_(p_14989_.readFloat(), p_14989_.readFloat());
        return $$9;
    }

    public JsonElement m_14998_() {
        JsonObject $$0 = new JsonObject();
        $$0.add("icon", (JsonElement)this.m_14999_());
        $$0.add("title", Component.Serializer.m_130716_(this.f_14958_));
        $$0.add("description", Component.Serializer.m_130716_(this.f_14959_));
        $$0.addProperty("frame", this.f_14962_.m_15548_());
        $$0.addProperty("show_toast", Boolean.valueOf(this.f_14963_));
        $$0.addProperty("announce_to_chat", Boolean.valueOf(this.f_14964_));
        $$0.addProperty("hidden", Boolean.valueOf(this.f_14965_));
        if (this.f_14961_ != null) {
            $$0.addProperty("background", this.f_14961_.toString());
        }
        return $$0;
    }

    private JsonObject m_14999_() {
        JsonObject $$0 = new JsonObject();
        $$0.addProperty("item", Registry.f_122827_.m_7981_(this.f_14960_.m_41720_()).toString());
        if (this.f_14960_.m_41782_()) {
            $$0.addProperty("nbt", this.f_14960_.m_41783_().toString());
        }
        return $$0;
    }
}

