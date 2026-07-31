/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.network;

import java.io.DataInput;
import java.io.DataOutput;

public interface IGrowingBuffer
extends DataInput,
DataOutput {
    public void writeVarInt(int var1);

    public void writeString(String var1);

    public int readVarInt();

    public String readString();
}

