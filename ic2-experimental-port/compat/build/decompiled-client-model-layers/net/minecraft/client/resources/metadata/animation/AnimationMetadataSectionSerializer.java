/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.client.resources.metadata.animation;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.metadata.animation.AnimationFrame;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer
implements MetadataSectionSerializer<AnimationMetadataSection> {
    @Override
    public AnimationMetadataSection m_6322_(JsonObject p_119064_) {
        ImmutableList.Builder $$1 = ImmutableList.builder();
        int $$2 = GsonHelper.m_13824_(p_119064_, "frametime", 1);
        if ($$2 != 1) {
            Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)$$2, (String)"Invalid default frame time");
        }
        if (p_119064_.has("frames")) {
            try {
                JsonArray $$3 = GsonHelper.m_13933_(p_119064_, "frames");
                for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
                    JsonElement $$5 = $$3.get($$4);
                    AnimationFrame $$6 = this.m_119058_($$4, $$5);
                    if ($$6 == null) continue;
                    $$1.add((Object)$$6);
                }
            }
            catch (ClassCastException $$7) {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + p_119064_.get("frames"), (Throwable)$$7);
            }
        }
        int $$8 = GsonHelper.m_13824_(p_119064_, "width", -1);
        int $$9 = GsonHelper.m_13824_(p_119064_, "height", -1);
        if ($$8 != -1) {
            Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)$$8, (String)"Invalid width");
        }
        if ($$9 != -1) {
            Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)$$9, (String)"Invalid height");
        }
        boolean $$10 = GsonHelper.m_13855_(p_119064_, "interpolate", false);
        return new AnimationMetadataSection((List<AnimationFrame>)$$1.build(), $$8, $$9, $$2, $$10);
    }

    @Nullable
    private AnimationFrame m_119058_(int p_119059_, JsonElement p_119060_) {
        if (p_119060_.isJsonPrimitive()) {
            return new AnimationFrame(GsonHelper.m_13897_(p_119060_, "frames[" + p_119059_ + "]"));
        }
        if (p_119060_.isJsonObject()) {
            JsonObject $$2 = GsonHelper.m_13918_(p_119060_, "frames[" + p_119059_ + "]");
            int $$3 = GsonHelper.m_13824_($$2, "time", -1);
            if ($$2.has("time")) {
                Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)$$3, (String)"Invalid frame time");
            }
            int $$4 = GsonHelper.m_13927_($$2, "index");
            Validate.inclusiveBetween((long)0L, (long)Integer.MAX_VALUE, (long)$$4, (String)"Invalid frame index");
            return new AnimationFrame($$4, $$3);
        }
        return null;
    }

    @Override
    public String m_7991_() {
        return "animation";
    }

    @Override
    public /* synthetic */ Object m_6322_(JsonObject jsonObject) {
        return this.m_6322_(jsonObject);
    }
}

