/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.collection;

public class FlagHolder {
    int flags;

    public FlagHolder() {
    }

    public FlagHolder(int initFlags) {
        this.flags = initFlags;
    }

    public void setFlag(int flag) {
        this.flags |= flag;
    }

    public boolean setFlag(int flag, boolean value) {
        if (this.isFlagSet(flag) == value) {
            return false;
        }
        this.flags = value ? this.flags | flag : this.flags & ~flag;
        return true;
    }

    public int getFlags() {
        return this.flags;
    }

    public void flipFlag(int flag) {
        this.flags ^= flag;
    }

    public void clearFlag(int flag) {
        this.flags &= ~flag;
    }

    public boolean isFlagSet(int flag) {
        return (this.flags & flag) == flag;
    }

    public boolean isAnyFlagSet(int flag) {
        return (this.flags & flag) != 0;
    }

    public boolean isFlagNotSet(int flag) {
        return (this.flags & flag) == 0;
    }
}

