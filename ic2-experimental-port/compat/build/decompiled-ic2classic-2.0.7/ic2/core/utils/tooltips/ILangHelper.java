/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ObjectArrays
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.utils.tooltips;

import com.google.common.collect.ObjectArrays;
import ic2.core.IC2;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.SanityHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ILangHelper {
    default public MutableComponent string() {
        return Component.m_237119_();
    }

    default public MutableComponent string(String text) {
        return Component.m_237113_((String)text);
    }

    default public MutableComponent string(String text, ChatFormatting ... formats) {
        return Component.m_237113_((String)text).m_130944_(formats);
    }

    default public MutableComponent translate(String text) {
        return Component.m_237115_((String)text);
    }

    default public MutableComponent translate(String text, ChatFormatting ... formats) {
        return Component.m_237115_((String)text).m_130944_(formats);
    }

    default public MutableComponent translate(String text, Object ... args) {
        return Component.m_237110_((String)text, (Object[])args);
    }

    default public MutableComponent translate(String text, ChatFormatting format, Object ... args) {
        return Component.m_237110_((String)text, (Object[])args).m_130940_(format);
    }

    default public MutableComponent error() {
        return Component.m_237115_((String)"tooltip.item.ic2.error");
    }

    default public MutableComponent pos(BlockPos pos) {
        return Component.m_237110_((String)"tooltip.item.ic2.pos", (Object[])new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()});
    }

    default public MutableComponent pos(Level world, BlockPos pos) {
        return this.pos((ResourceKey<Level>)world.m_46472_(), pos);
    }

    default public MutableComponent pos(ResourceKey<Level> dimension, BlockPos pos) {
        return Component.m_237110_((String)"tooltip.item.ic2.dim_pos", (Object[])new Object[]{SanityHelper.toPascalCase(dimension.m_135782_().m_135815_()), pos.m_123341_(), pos.m_123342_(), pos.m_123343_()});
    }

    default public MutableComponent bool(boolean value) {
        return Component.m_237115_((String)(value ? "misc.ic2.yes" : "misc.ic2.no"));
    }

    default public MutableComponent eu(int amount) {
        return Component.m_237110_((String)"misc.ic2.eu", (Object[])new Object[]{Formatters.EU_FORMAT.format(amount)});
    }

    default public MutableComponent buildKeyDescription(KeyHelper key, Object ... args) {
        return this.buildKeyDescription(ObjectArrays.concat((Object)IC2.KEYBOARD.getKeyName(key).m_130940_(ChatFormatting.GOLD), (Object[])args));
    }

    default public MutableComponent buildKeyDescription(KeyHelper key, String translationKey, Object ... args) {
        return this.buildKeyDescription(IC2.KEYBOARD.getKeyName(key).m_130940_(ChatFormatting.GOLD), this.translate(translationKey, args).m_130940_(ChatFormatting.UNDERLINE));
    }

    default public MutableComponent buildKeyDescription(KeyHelper key1, KeyHelper key2, Object ... args) {
        return this.buildKeyDescription(ObjectArrays.concat((Object)ILangHelper.combineKeys(key1, key2), (Object[])args));
    }

    default public MutableComponent buildKeyDescription(KeyHelper key1, KeyHelper key2, String translationKey, Object ... args) {
        return this.buildKeyDescription(ILangHelper.combineKeys(key1, key2), this.translate(translationKey, args).m_130940_(ChatFormatting.UNDERLINE));
    }

    default public MutableComponent buildKeyDescription(KeyHelper[] keys, Object ... args) {
        return this.buildKeyDescription(ObjectArrays.concat((Object)ILangHelper.combineKeys(keys), (Object[])args));
    }

    default public MutableComponent buildKeyDescription(KeyHelper[] keys, String translationKey, Object ... args) {
        return this.buildKeyDescription(ILangHelper.combineKeys(keys), this.translate(translationKey, args).m_130940_(ChatFormatting.UNDERLINE));
    }

    default public MutableComponent buildKeyDescription(Object ... args) {
        return Component.m_237110_((String)"tooltip.ic2.press_key_description", (Object[])args);
    }

    @OnlyIn(value=Dist.CLIENT)
    default public MutableComponent combineKeys(KeyMapping ... keys) {
        MutableComponent text = Component.m_237119_();
        int m = keys.length;
        for (int i = 0; i < m; ++i) {
            if (i != 0) {
                text.m_130946_(" & ");
            }
            text.m_7220_((Component)keys[i].getKey().m_84875_().m_6881_().m_130940_(ChatFormatting.GOLD));
        }
        return text;
    }

    public static MutableComponent combineKeys(KeyHelper ... keys) {
        MutableComponent text = Component.m_237119_();
        int m = keys.length;
        for (int i = 0; i < m; ++i) {
            if (i != 0) {
                text.m_130946_(" & ");
            }
            text.m_7220_((Component)IC2.KEYBOARD.getKeyName(keys[i]).m_130940_(ChatFormatting.GOLD));
        }
        return text;
    }
}

