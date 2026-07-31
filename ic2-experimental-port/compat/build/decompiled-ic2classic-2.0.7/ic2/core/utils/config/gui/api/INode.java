/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.gui.api;

public interface INode {
    public boolean isDefault();

    public boolean isChanged();

    public void setDefault();

    public void setPrevious();

    public void createTemp();

    public void apply();
}

