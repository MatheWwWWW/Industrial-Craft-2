/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.item.upgrade;

import ic2.core.item.upgrade.ComparisonSettings;
import ic2.core.item.upgrade.ComparisonType;
import net.minecraft.nbt.CompoundTag;

public class UpgradeSettings {
    public final boolean active;
    public final ComparisonType comparison;
    public final String mainBox;
    public final String extraBox;
    public final ComparisonSettings main;
    public final ComparisonSettings extra;

    public UpgradeSettings(CompoundTag compoundTag) {
        this.active = compoundTag.m_128471_("active");
        if (!this.active) {
            this.comparison = ComparisonType.IGNORED;
            this.extraBox = "";
            this.mainBox = "";
            this.main = this.extra = ComparisonSettings.DEFAULT;
        } else {
            this.comparison = !compoundTag.m_128425_("type", 1) ? ComparisonType.DIRECT : ComparisonType.getFromNBT(compoundTag.m_128445_("type"));
            switch (this.comparison) {
                case DIRECT: {
                    this.extraBox = "";
                    this.mainBox = "";
                    this.main = this.extra = ComparisonSettings.DEFAULT;
                    break;
                }
                case COMPARISON: {
                    this.mainBox = compoundTag.m_128461_("normal");
                    this.extraBox = "";
                    this.main = ComparisonSettings.getFromNBT(compoundTag.m_128445_("normalComp"));
                    this.extra = ComparisonSettings.DEFAULT;
                    break;
                }
                case RANGE: {
                    this.mainBox = compoundTag.m_128461_("normal");
                    this.extraBox = compoundTag.m_128461_("extra");
                    this.main = ComparisonSettings.getFromNBT(compoundTag.m_128445_("normalComp"));
                    this.extra = ComparisonSettings.getFromNBT(compoundTag.m_128445_("extraComp"));
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected comparison type " + this.comparison);
                }
            }
        }
    }

    public boolean doComparison(int n) {
        switch (this.comparison) {
            case COMPARISON: {
                return this.main.compare(Integer.parseInt(this.mainBox), n);
            }
            case RANGE: {
                return this.main.compare(Integer.parseInt(this.mainBox), n) && this.extra.compare(n, Integer.parseInt(this.extraBox));
            }
        }
        throw new IllegalStateException("Unexpected comparison type " + this.comparison);
    }
}

