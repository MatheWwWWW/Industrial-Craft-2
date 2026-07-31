/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class DebugEntityNameGenerator {
    private static final String[] f_133662_ = new String[]{"Slim", "Far", "River", "Silly", "Fat", "Thin", "Fish", "Bat", "Dark", "Oak", "Sly", "Bush", "Zen", "Bark", "Cry", "Slack", "Soup", "Grim", "Hook", "Dirt", "Mud", "Sad", "Hard", "Crook", "Sneak", "Stink", "Weird", "Fire", "Soot", "Soft", "Rough", "Cling", "Scar"};
    private static final String[] f_133663_ = new String[]{"Fox", "Tail", "Jaw", "Whisper", "Twig", "Root", "Finder", "Nose", "Brow", "Blade", "Fry", "Seek", "Wart", "Tooth", "Foot", "Leaf", "Stone", "Fall", "Face", "Tongue", "Voice", "Lip", "Mouth", "Snail", "Toe", "Ear", "Hair", "Beard", "Shirt", "Fist"};

    public static String m_179486_(Entity p_179487_) {
        if (p_179487_ instanceof Player) {
            return p_179487_.m_7755_().getString();
        }
        Component $$1 = p_179487_.m_7770_();
        if ($$1 != null) {
            return $$1.getString();
        }
        return DebugEntityNameGenerator.m_133668_(p_179487_.m_20148_());
    }

    public static String m_133668_(UUID p_133669_) {
        RandomSource $$1 = DebugEntityNameGenerator.m_237883_(p_133669_);
        return DebugEntityNameGenerator.m_237880_($$1, f_133662_) + DebugEntityNameGenerator.m_237880_($$1, f_133663_);
    }

    private static String m_237880_(RandomSource p_237881_, String[] p_237882_) {
        return Util.m_214670_(p_237882_, p_237881_);
    }

    private static RandomSource m_237883_(UUID p_237884_) {
        return RandomSource.m_216335_(p_237884_.hashCode() >> 2);
    }
}

