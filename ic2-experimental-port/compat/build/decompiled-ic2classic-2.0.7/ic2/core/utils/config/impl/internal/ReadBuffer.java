/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 */
package ic2.core.utils.config.impl.internal;

import ic2.core.utils.config.api.buffer.IReadBuffer;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;

public class ReadBuffer
implements IReadBuffer {
    FriendlyByteBuf buf;

    public ReadBuffer(FriendlyByteBuf buf) {
        this.buf = buf;
    }

    @Override
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    @Override
    public byte readByte() {
        return this.buf.readByte();
    }

    @Override
    public short readShort() {
        return this.buf.readShort();
    }

    @Override
    public int readMedium() {
        return this.buf.readMedium();
    }

    @Override
    public int readInt() {
        return this.buf.readInt();
    }

    @Override
    public int readVarInt() {
        return this.buf.m_130242_();
    }

    @Override
    public float readFloat() {
        return this.buf.readFloat();
    }

    @Override
    public double readDouble() {
        return this.buf.readDouble();
    }

    @Override
    public long readLong() {
        return this.buf.readLong();
    }

    @Override
    public char readChar() {
        return this.buf.readChar();
    }

    @Override
    public <T extends Enum<T>> T readEnum(Class<T> clz) {
        return (T)this.buf.m_130066_(clz);
    }

    @Override
    public byte[] readBytes() {
        return this.buf.m_130052_();
    }

    @Override
    public String readString() {
        return this.buf.m_130136_(Short.MAX_VALUE);
    }

    @Override
    public UUID readUUID() {
        return this.buf.m_130259_();
    }
}

