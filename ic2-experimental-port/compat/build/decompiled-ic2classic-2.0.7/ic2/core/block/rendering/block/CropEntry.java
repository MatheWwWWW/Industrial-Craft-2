/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.rendering.block;

import ic2.api.crops.ICrop;
import java.util.Objects;

public class CropEntry {
    ICrop crop;
    int stage;

    public CropEntry(ICrop crop, int stage) {
        this.crop = crop;
        this.stage = stage;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CropEntry) {
            CropEntry entry = (CropEntry)obj;
            return entry.crop == this.crop && entry.stage == this.stage;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.crop, this.stage);
    }
}

