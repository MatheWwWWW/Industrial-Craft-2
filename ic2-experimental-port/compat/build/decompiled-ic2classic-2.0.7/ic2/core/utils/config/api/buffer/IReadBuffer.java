/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.api.buffer;

import java.util.UUID;

public interface IReadBuffer {
    public boolean readBoolean();

    public byte readByte();

    public short readShort();

    public int readMedium();

    public int readInt();

    public int readVarInt();

    public float readFloat();

    public double readDouble();

    public long readLong();

    public char readChar();

    public <T extends Enum<T>> T readEnum(Class<T> var1);

    public byte[] readBytes();

    public String readString();

    public UUID readUUID();
}

