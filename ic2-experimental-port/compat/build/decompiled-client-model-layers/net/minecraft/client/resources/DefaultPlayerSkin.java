/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources;

import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

public class DefaultPlayerSkin {
    private static final ResourceLocation f_118623_ = new ResourceLocation("textures/entity/steve.png");
    private static final ResourceLocation f_118624_ = new ResourceLocation("textures/entity/alex.png");
    private static final String f_174829_ = "default";
    private static final String f_174830_ = "slim";

    public static ResourceLocation m_118626_() {
        return f_118623_;
    }

    public static ResourceLocation m_118627_(UUID p_118628_) {
        if (DefaultPlayerSkin.m_118631_(p_118628_)) {
            return f_118624_;
        }
        return f_118623_;
    }

    public static String m_118629_(UUID p_118630_) {
        if (DefaultPlayerSkin.m_118631_(p_118630_)) {
            return f_174830_;
        }
        return f_174829_;
    }

    private static boolean m_118631_(UUID p_118632_) {
        return (p_118632_.hashCode() & 1) == 1;
    }
}

