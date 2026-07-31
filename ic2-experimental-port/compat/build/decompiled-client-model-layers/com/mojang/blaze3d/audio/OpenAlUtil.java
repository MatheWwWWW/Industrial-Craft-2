/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.lwjgl.openal.AL10
 *  org.lwjgl.openal.ALC10
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.audio;

import com.mojang.logging.LogUtils;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;
import org.slf4j.Logger;

public class OpenAlUtil {
    private static final Logger f_83780_ = LogUtils.getLogger();

    private static String m_83782_(int p_83783_) {
        switch (p_83783_) {
            case 40961: {
                return "Invalid name parameter.";
            }
            case 40962: {
                return "Invalid enumerated parameter value.";
            }
            case 40963: {
                return "Invalid parameter parameter value.";
            }
            case 40964: {
                return "Invalid operation.";
            }
            case 40965: {
                return "Unable to allocate memory.";
            }
        }
        return "An unrecognized error occurred.";
    }

    static boolean m_83787_(String p_83788_) {
        int $$1 = AL10.alGetError();
        if ($$1 != 0) {
            f_83780_.error("{}: {}", (Object)p_83788_, (Object)OpenAlUtil.m_83782_($$1));
            return true;
        }
        return false;
    }

    private static String m_83791_(int p_83792_) {
        switch (p_83792_) {
            case 40961: {
                return "Invalid device.";
            }
            case 40962: {
                return "Invalid context.";
            }
            case 40964: {
                return "Invalid value.";
            }
            case 40963: {
                return "Illegal enum.";
            }
            case 40965: {
                return "Unable to allocate memory.";
            }
        }
        return "An unrecognized error occurred.";
    }

    static boolean m_83784_(long p_83785_, String p_83786_) {
        int $$2 = ALC10.alcGetError((long)p_83785_);
        if ($$2 != 0) {
            f_83780_.error("{}{}: {}", new Object[]{p_83786_, p_83785_, OpenAlUtil.m_83791_($$2)});
            return true;
        }
        return false;
    }

    static int m_83789_(AudioFormat p_83790_) {
        AudioFormat.Encoding $$1 = p_83790_.getEncoding();
        int $$2 = p_83790_.getChannels();
        int $$3 = p_83790_.getSampleSizeInBits();
        if ($$1.equals(AudioFormat.Encoding.PCM_UNSIGNED) || $$1.equals(AudioFormat.Encoding.PCM_SIGNED)) {
            if ($$2 == 1) {
                if ($$3 == 8) {
                    return 4352;
                }
                if ($$3 == 16) {
                    return 4353;
                }
            } else if ($$2 == 2) {
                if ($$3 == 8) {
                    return 4354;
                }
                if ($$3 == 16) {
                    return 4355;
                }
            }
        }
        throw new IllegalArgumentException("Invalid audio format: " + p_83790_);
    }
}

