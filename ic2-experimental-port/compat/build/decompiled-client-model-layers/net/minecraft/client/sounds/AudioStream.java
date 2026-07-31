/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.sounds;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;

public interface AudioStream
extends Closeable {
    public AudioFormat m_6206_();

    public ByteBuffer m_7118_(int var1) throws IOException;
}

