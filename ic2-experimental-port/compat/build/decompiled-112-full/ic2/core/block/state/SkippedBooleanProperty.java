/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.properties.PropertyBool
 */
package ic2.core.block.state;

import ic2.core.block.state.ISkippableProperty;
import net.minecraft.block.properties.PropertyBool;

public class SkippedBooleanProperty
extends PropertyBool
implements ISkippableProperty {
    public SkippedBooleanProperty(String name) {
        super(name);
    }
}

