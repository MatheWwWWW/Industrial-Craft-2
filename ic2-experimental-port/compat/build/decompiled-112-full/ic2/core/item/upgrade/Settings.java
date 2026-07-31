/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.core.item.upgrade;

import ic2.core.item.upgrade.ComparisonSettings;
import ic2.core.item.upgrade.ComparisonType;
import net.minecraft.nbt.NBTTagCompound;

public class Settings {
    public final boolean active;
    public final ComparisonType comparison;
    public final String mainBox;
    public final String extraBox;
    public final ComparisonSettings main;
    public final ComparisonSettings extra;

    public Settings(NBTTagCompound nbt) {
        this.active = nbt.func_74767_n("active");
        if (!this.active) {
            this.comparison = ComparisonType.IGNORED;
            this.extraBox = "";
            this.mainBox = "";
            this.main = this.extra = ComparisonSettings.DEFAULT;
        } else {
            this.comparison = !nbt.func_150297_b("type", 1) ? ComparisonType.DIRECT : ComparisonType.getFromNBT(nbt.func_74771_c("type"));
            switch (this.comparison) {
                case DIRECT: {
                    this.extraBox = "";
                    this.mainBox = "";
                    this.main = this.extra = ComparisonSettings.DEFAULT;
                    break;
                }
                case COMPARISON: {
                    this.mainBox = nbt.func_74779_i("normal");
                    this.extraBox = "";
                    this.main = ComparisonSettings.getFromNBT(nbt.func_74771_c("normalComp"));
                    this.extra = ComparisonSettings.DEFAULT;
                    break;
                }
                case RANGE: {
                    this.mainBox = nbt.func_74779_i("normal");
                    this.extraBox = nbt.func_74779_i("extra");
                    this.main = ComparisonSettings.getFromNBT(nbt.func_74771_c("normalComp"));
                    this.extra = ComparisonSettings.getFromNBT(nbt.func_74771_c("extraComp"));
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected comparison type " + (Object)((Object)this.comparison));
                }
            }
        }
    }

    public boolean doComparison(int value) {
        switch (this.comparison) {
            case COMPARISON: {
                return this.main.compare(Integer.parseInt(this.mainBox), value);
            }
            case RANGE: {
                return this.main.compare(Integer.parseInt(this.mainBox), value) && this.extra.compare(value, Integer.parseInt(this.extraBox));
            }
        }
        throw new IllegalStateException("Unexpected comparison type " + (Object)((Object)this.comparison));
    }
}

