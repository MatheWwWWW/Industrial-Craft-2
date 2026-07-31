/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Transformation
 *  net.minecraft.client.resources.model.ModelState
 */
package ic2.core.platform.rendering.misc;

import com.mojang.math.Transformation;
import net.minecraft.client.resources.model.ModelState;

public class ModelTransform
implements ModelState {
    ModelState transform;
    boolean uvLock;

    public ModelTransform(ModelState transform, boolean uvLock) {
        this.transform = transform;
        this.uvLock = uvLock;
    }

    public Transformation m_6189_() {
        return this.transform.m_6189_();
    }

    public boolean m_7538_() {
        return this.uvLock;
    }
}

