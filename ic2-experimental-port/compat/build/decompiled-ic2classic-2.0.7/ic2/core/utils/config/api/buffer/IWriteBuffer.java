/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.api.buffer;

import java.util.UUID;

public interface IWriteBuffer {
    public void writeBoolean(boolean var1);

    public void writeByte(byte var1);

    public void writeShort(short var1);

    public void writeMedium(int var1);

    public void writeInt(int var1);

    public void writeVarInt(int var1);

    public void writeFloat(float var1);

    public void writeDouble(double var1);

    public void writeLong(long var1);

    public void writeChar(char var1);

    public void writeEnum(Enum<?> var1);

    public void writeString(String var1);

    public void writeBytes(byte[] var1);

    public void writeUUID(UUID var1);
}

