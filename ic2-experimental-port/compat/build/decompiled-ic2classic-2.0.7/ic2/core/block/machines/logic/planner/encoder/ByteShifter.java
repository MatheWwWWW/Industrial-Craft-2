/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.machines.logic.planner.encoder;

import java.math.BigInteger;

public class ByteShifter {
    private BigInteger dataStack = null;

    public ByteShifter() {
        this.dataStack = new BigInteger("0");
    }

    public ByteShifter(int value) {
        this.dataStack = BigInteger.valueOf(value);
    }

    public ByteShifter(String dataCode) {
        if (dataCode != null) {
            try {
                this.dataStack = new BigInteger(dataCode, 36);
            }
            catch (Exception e) {
                this.dataStack = new BigInteger("0");
            }
        } else {
            this.dataStack = new BigInteger("0");
        }
    }

    public String readString(int bits) {
        StringBuilder build = new StringBuilder();
        int length = this.readInt(bits);
        for (int i = 0; i < length; ++i) {
            build.append((char)this.readChar());
        }
        return build.reverse().toString();
    }

    public int readChar() {
        return this.readBigInteger(8).intValue();
    }

    public boolean readBoolean() {
        return this.readBigInteger(1).intValue() != 0;
    }

    public int readInt(int bits) {
        return this.readBigInteger(bits).intValue();
    }

    public void shiftLeft(int bits) {
        this.dataStack = this.dataStack.shiftLeft(bits);
    }

    public BigInteger readBigInteger(int bits) {
        StringBuilder bitString = new StringBuilder();
        for (int i = 0; i < bits; ++i) {
            bitString.append('1');
        }
        BigInteger data = this.dataStack.and(new BigInteger(bitString.toString(), 2));
        this.dataStack = this.dataStack.shiftRight(bits);
        return data;
    }

    public void writeString(String data, int bits) {
        char[] array = data.toCharArray();
        for (int i = 0; i < array.length; ++i) {
            this.writeInteger(array[i], 8);
        }
        this.writeInteger(array.length, bits);
    }

    public void writeCharacter(char data) {
        this.writeInteger(data, 8);
    }

    public void writeBigInteger(BigInteger data, int bits) {
        this.dataStack = this.dataStack.shiftLeft(bits);
        this.dataStack = this.dataStack.add(data);
    }

    public void writeInteger(int data, int bits) {
        this.dataStack = this.dataStack.shiftLeft(bits);
        this.dataStack = this.dataStack.add(BigInteger.valueOf(data & this.getBits(bits)));
    }

    public void writeBoolean(boolean data) {
        this.dataStack = this.dataStack.shiftLeft(1);
        this.dataStack = this.dataStack.add(BigInteger.valueOf(data ? 1L : 0L));
    }

    private int getBits(int bit) {
        return (1 << bit + 1) - 1;
    }

    public String getDataCode() {
        return this.dataStack.toString(36);
    }

    public int getEncodedData() {
        return this.dataStack.intValue();
    }
}

