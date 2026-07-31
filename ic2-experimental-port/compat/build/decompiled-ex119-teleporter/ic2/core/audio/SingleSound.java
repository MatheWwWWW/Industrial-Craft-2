/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.audio;

public interface SingleSound {
    public void onFinish(Runnable var1);

    public void remove();

    public void cancel();

    public boolean isCancelled();

    public boolean isComplete();
}

