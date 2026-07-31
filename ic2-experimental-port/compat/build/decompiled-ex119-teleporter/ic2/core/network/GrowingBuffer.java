/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 */
package ic2.core.network;

import ic2.api.network.IGrowingBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.nio.BufferUnderflowException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class GrowingBuffer
extends OutputStream
implements IGrowingBuffer {
    private static byte[] emptyBuffer = new byte[0];
    private static final Charset utf8 = Charset.forName("UTF-8");
    private byte[] buffer;
    private int pos;
    private int altPos;
    private int mark = -1;

    public GrowingBuffer() {
        this(4096);
    }

    public GrowingBuffer(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("invalid initial size: " + n);
        }
        this.buffer = n == 0 ? emptyBuffer : new byte[n];
    }

    public static GrowingBuffer wrap(byte[] byArray) {
        GrowingBuffer growingBuffer = new GrowingBuffer(0);
        growingBuffer.buffer = byArray;
        growingBuffer.altPos = byArray.length;
        return growingBuffer;
    }

    public static GrowingBuffer wrap(ByteBuf byteBuf) {
        GrowingBuffer growingBuffer;
        int n = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            growingBuffer = new GrowingBuffer(0);
            growingBuffer.buffer = byteBuf.array();
            growingBuffer.pos = byteBuf.arrayOffset() + byteBuf.readerIndex();
            growingBuffer.altPos = growingBuffer.pos + n;
        } else {
            growingBuffer = new GrowingBuffer(n);
            growingBuffer.altPos = n;
            byteBuf.getBytes(byteBuf.readerIndex(), growingBuffer.buffer);
        }
        return growingBuffer;
    }

    public void clear() {
        this.pos = 0;
        this.altPos = 0;
        this.mark = -1;
    }

    public void mark() {
        this.mark = this.pos;
    }

    public void reset() {
        if (this.mark == -1) {
            throw new IllegalStateException("not marked");
        }
        this.pos = this.mark;
    }

    public void flip() {
        int n = this.pos;
        this.pos = this.altPos;
        this.altPos = n;
    }

    public void rewind() {
        assert (this.pos <= this.altPos);
        this.pos = 0;
    }

    public boolean hasAvailable() {
        return this.pos < this.altPos;
    }

    public int available() {
        return Math.max(0, this.altPos - this.pos);
    }

    public void writeTo(GrowingBuffer growingBuffer) {
        int n = this.altPos - this.pos;
        if (n <= 0) {
            return;
        }
        growingBuffer.ensureCapacity(n);
        System.arraycopy(this.buffer, this.pos, growingBuffer.buffer, growingBuffer.pos, n);
        growingBuffer.pos += n;
        this.pos += n;
    }

    public void writeTo(ByteBuf byteBuf) {
        int n = this.altPos - this.pos;
        if (n <= 0) {
            return;
        }
        byteBuf.writeBytes(this.buffer, this.pos, n);
        this.pos += n;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        int n = this.altPos - this.pos;
        if (n <= 0) {
            return;
        }
        outputStream.write(this.buffer, this.pos, n);
        this.pos += n;
    }

    public ByteBuf toByteBuf(boolean bl) {
        int n = this.altPos - this.pos;
        if (n <= 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBuf byteBuf = Unpooled.wrappedBuffer((byte[])this.buffer, (int)this.pos, (int)n);
        if (bl) {
            this.pos += n;
        }
        return byteBuf;
    }

    public GrowingBuffer copy(int n) {
        int n2 = Math.max(0, Math.min(n, this.altPos - this.pos));
        GrowingBuffer growingBuffer = new GrowingBuffer(n2);
        if (n2 > 0) {
            System.arraycopy(this.buffer, this.pos, growingBuffer.buffer, 0, n2);
            growingBuffer.altPos = n2;
            this.pos += n2;
        }
        return growingBuffer;
    }

    @Override
    public void write(int n) {
        this.ensureCapacity(1);
        this.buffer[this.pos] = (byte)n;
        ++this.pos;
    }

    @Override
    public void write(byte[] byArray) {
        this.ensureCapacity(byArray.length);
        System.arraycopy(byArray, 0, this.buffer, this.pos, byArray.length);
        this.pos += byArray.length;
    }

    @Override
    public void write(byte[] byArray, int n, int n2) {
        this.ensureCapacity(n2);
        System.arraycopy(byArray, n, this.buffer, this.pos, n2);
        this.pos += n2;
    }

    @Override
    public void writeBoolean(boolean bl) {
        this.write(bl ? 1 : 0);
    }

    @Override
    public void writeByte(int n) {
        this.write(n);
    }

    @Override
    public void writeShort(int n) {
        this.ensureCapacity(2);
        this.buffer[this.pos] = (byte)(n >> 8);
        this.buffer[this.pos + 1] = (byte)n;
        this.pos += 2;
    }

    @Override
    public void writeChar(int n) {
        this.writeShort(n);
    }

    @Override
    public void writeInt(int n) {
        this.ensureCapacity(4);
        this.buffer[this.pos] = (byte)(n >> 24);
        this.buffer[this.pos + 1] = (byte)(n >> 16);
        this.buffer[this.pos + 2] = (byte)(n >> 8);
        this.buffer[this.pos + 3] = (byte)n;
        this.pos += 4;
    }

    @Override
    public void writeLong(long l) {
        this.ensureCapacity(8);
        this.buffer[this.pos] = (byte)(l >> 56);
        this.buffer[this.pos + 1] = (byte)(l >> 48);
        this.buffer[this.pos + 2] = (byte)(l >> 40);
        this.buffer[this.pos + 3] = (byte)(l >> 32);
        this.buffer[this.pos + 4] = (byte)(l >> 24);
        this.buffer[this.pos + 5] = (byte)(l >> 16);
        this.buffer[this.pos + 6] = (byte)(l >> 8);
        this.buffer[this.pos + 7] = (byte)l;
        this.pos += 8;
    }

    @Override
    public void writeFloat(float f) {
        this.writeInt(Float.floatToRawIntBits(f));
    }

    @Override
    public void writeDouble(double d) {
        this.writeLong(Double.doubleToRawLongBits(d));
    }

    @Override
    public void writeVarInt(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("only positive numbers are supported");
        }
        do {
            int n2 = n & 0x7F;
            if ((n >>>= 7) != 0) {
                n2 |= 0x80;
            }
            this.writeByte(n2);
        } while (n != 0);
    }

    @Override
    public void writeString(String string) {
        byte[] byArray = string.getBytes(utf8);
        this.writeVarInt(byArray.length);
        this.write(byArray);
    }

    @Override
    public void writeBytes(String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeChars(String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeUTF(String string) {
        char c;
        int n;
        int n2 = 0;
        for (n = 0; n < string.length(); ++n) {
            c = string.charAt(n);
            if (c > '\u0000' && c < '\u0080') {
                ++n2;
                continue;
            }
            if (c >= '\u0800') {
                n2 += 3;
                continue;
            }
            n2 += 2;
        }
        if (n2 > 65535) {
            throw new IllegalArgumentException("string length limit exceeded");
        }
        this.writeShort(n2);
        for (n = 0; n < string.length(); ++n) {
            c = string.charAt(n);
            if (c > '\u0000' && c < '\u0080') {
                this.write(c);
                continue;
            }
            if (c >= '\u0800') {
                this.write(0xE0 | c >> 12 & 0xF);
                this.write(0x80 | c >> 6 & 0x3F);
                this.write(0x80 | c & 0x3F);
                continue;
            }
            this.write(0xC0 | c >> 6 & 0x1F);
            this.write(0x80 | c & 0x3F);
        }
    }

    private void ensureCapacity(int n) {
        if (this.pos + n > this.buffer.length) {
            this.buffer = Arrays.copyOf(this.buffer, Math.max(this.buffer.length * 2, this.pos + n));
        }
    }

    @Override
    public void readFully(byte[] byArray) {
        if (this.pos + byArray.length > this.altPos) {
            throw new BufferUnderflowException();
        }
        System.arraycopy(this.buffer, this.pos, byArray, 0, byArray.length);
        this.pos += byArray.length;
    }

    @Override
    public void readFully(byte[] byArray, int n, int n2) {
        if (this.pos + n2 > this.altPos) {
            throw new BufferUnderflowException();
        }
        System.arraycopy(this.buffer, this.pos, byArray, n, n2);
        this.pos += n2;
    }

    @Override
    public int skipBytes(int n) {
        int n2 = Math.max(-this.pos, Math.min(n, Math.max(0, this.altPos - this.pos)));
        this.pos += n2;
        return n2;
    }

    @Override
    public boolean readBoolean() {
        return this.readByte() != 0;
    }

    @Override
    public byte readByte() {
        if (this.pos + 1 > this.altPos) {
            throw new BufferUnderflowException();
        }
        return this.buffer[this.pos++];
    }

    @Override
    public int readUnsignedByte() {
        return this.readByte() & 0xFF;
    }

    @Override
    public short readShort() {
        if (this.pos + 2 > this.altPos) {
            throw new BufferUnderflowException();
        }
        short s = (short)(this.buffer[this.pos] << 8 | this.buffer[this.pos + 1] & 0xFF);
        this.pos += 2;
        return s;
    }

    @Override
    public int readUnsignedShort() {
        if (this.pos + 2 > this.altPos) {
            throw new BufferUnderflowException();
        }
        int n = (this.buffer[this.pos] & 0xFF) << 8 | this.buffer[this.pos + 1] & 0xFF;
        this.pos += 2;
        return n;
    }

    @Override
    public char readChar() {
        return (char)this.readShort();
    }

    @Override
    public int readInt() {
        if (this.pos + 4 > this.altPos) {
            throw new BufferUnderflowException();
        }
        int n = (this.buffer[this.pos] & 0xFF) << 24 | (this.buffer[this.pos + 1] & 0xFF) << 16 | (this.buffer[this.pos + 2] & 0xFF) << 8 | this.buffer[this.pos + 3] & 0xFF;
        this.pos += 4;
        return n;
    }

    @Override
    public long readLong() {
        if (this.pos + 8 > this.altPos) {
            throw new BufferUnderflowException();
        }
        long l = ((long)this.buffer[this.pos] & 0xFFL) << 56 | ((long)this.buffer[this.pos + 1] & 0xFFL) << 48 | ((long)this.buffer[this.pos + 2] & 0xFFL) << 40 | ((long)this.buffer[this.pos + 3] & 0xFFL) << 32 | ((long)this.buffer[this.pos + 4] & 0xFFL) << 24 | ((long)this.buffer[this.pos + 5] & 0xFFL) << 16 | ((long)this.buffer[this.pos + 6] & 0xFFL) << 8 | (long)this.buffer[this.pos + 7] & 0xFFL;
        this.pos += 8;
        return l;
    }

    @Override
    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public double readDouble() {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public int readVarInt() {
        int n = 0;
        int n2 = 0;
        while (true) {
            byte by = this.readByte();
            n |= (by & 0x7F) << n2;
            if ((by & 0x80) == 0) break;
            n2 += 7;
        }
        return n;
    }

    @Override
    public String readString() {
        int n = this.readVarInt();
        byte[] byArray = new byte[n];
        this.readFully(byArray);
        return new String(byArray, utf8);
    }

    @Override
    public String readLine() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String readUTF() throws IOException {
        int n = this.readUnsignedShort();
        if (n == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(Math.min(n, 10 + (n + 2) / 3));
        int n2 = 0;
        while (n2 < n) {
            byte by;
            byte by2 = this.readByte();
            if ((by2 & 0x80) == 0) {
                stringBuilder.append((char)by2);
                ++n2;
                continue;
            }
            if ((by2 & 0xE0) == 192) {
                if (n - n2 < 2) {
                    throw new UTFDataFormatException();
                }
                by = this.readByte();
                if ((by & 0xC0) != 128) {
                    throw new UTFDataFormatException();
                }
                stringBuilder.append((char)((by2 & 0x1F) << 6 | by & 0xEF));
                n2 += 2;
                continue;
            }
            if ((by2 & 0xF0) == 224) {
                if (n - n2 < 3) {
                    throw new UTFDataFormatException();
                }
                by = this.readByte();
                if ((by & 0xC0) != 128) {
                    throw new UTFDataFormatException();
                }
                byte by3 = this.readByte();
                if ((by3 & 0xC0) != 128) {
                    throw new UTFDataFormatException();
                }
                stringBuilder.append((char)((by2 & 0xF) << 12 | (by & 0xEF) << 6 | by3 & 0xEF));
                n2 += 3;
                continue;
            }
            throw new UTFDataFormatException();
        }
        return stringBuilder.toString();
    }
}

