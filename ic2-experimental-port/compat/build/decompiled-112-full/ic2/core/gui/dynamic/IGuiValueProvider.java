/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui.dynamic;

public interface IGuiValueProvider {
    public double getGuiValue(String var1);

    public static interface IActiveGuiValueProvider
    extends IGuiValueProvider {
        public boolean isGuiValueActive(String var1);
    }
}

