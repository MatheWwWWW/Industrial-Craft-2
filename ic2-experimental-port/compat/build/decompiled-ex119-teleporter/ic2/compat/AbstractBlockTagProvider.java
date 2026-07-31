/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.tags.BlockTagsProvider
 */
package ic2.compat;

import ic2.forge.FmlMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;

public abstract class AbstractBlockTagProvider
extends BlockTagsProvider {
    public AbstractBlockTagProvider(DataGenerator dataGenerator) {
        super(dataGenerator, "ic2", FmlMod.existingFileHelper);
    }
}

