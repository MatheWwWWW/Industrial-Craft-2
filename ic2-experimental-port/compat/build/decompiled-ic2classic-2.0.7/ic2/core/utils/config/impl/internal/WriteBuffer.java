/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 */
package ic2.core.utils.config.impl.internal;

import ic2.core.utils.config.api.buffer.IWriteBuffer;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;

public class WriteBuffer
implements IWriteBuffer {
    FriendlyByteBuf buf;

    public WriteBuffer(FriendlyByteBuf buf) {
        this.buf = buf;
    }

    @Override
    public void writeBoolean(boolean value) {
        this.buf.writeBoolean(value);
    }

    @Override
    public void writeByte(byte value) {
        this.buf.writeByte((int)value);
    }

    @Override
    public void writeShort(short value) {
        this.buf.writeShort((int)value);
    }

    @Override
    public void writeMedium(int value) {
        this.buf.writeMedium(value);
    }

    @Override
    public void writeInt(int value) {
        this.buf.writeInt(value);
    }

    @Override
    public void writeVarInt(int value) {
        this.buf.m_130130_(value);
    }

    @Override
    public void writeFloat(float value) {
        this.buf.writeFloat(value);
    }

    @Override
    public void writeDouble(double value) {
        this.buf.writeDouble(value);
    }

    @Override
    public void writeLong(long value) {
        this.buf.writeLong(value);
    }

    @Override
    public void writeChar(char value) {
        this.buf.writeChar((int)value);
    }

    @Override
    public void writeEnum(Enum<?> value) {
        this.buf.m_130068_(value);
    }

    @Override
    public void writeString(String value) {
        this.buf.m_130072_(value, Short.MAX_VALUE);
    }

    @Override
    public void writeBytes(byte[] value) {
        this.buf.m_130087_(value);
    }

    @Override
    public void writeUUID(UUID value) {
        this.buf.m_130077_(value);
    }
}

