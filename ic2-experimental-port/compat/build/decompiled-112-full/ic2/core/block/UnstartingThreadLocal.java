/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block;

class UnstartingThreadLocal<T>
extends ThreadLocal<T> {
    UnstartingThreadLocal() {
    }

    @Override
    protected T initialValue() {
        throw new UnsupportedOperationException();
    }
}

