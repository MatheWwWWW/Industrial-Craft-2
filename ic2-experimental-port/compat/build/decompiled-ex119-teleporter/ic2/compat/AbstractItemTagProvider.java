/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.tags.BlockTagsProvider
 *  net.minecraft.data.tags.ItemTagsProvider
 */
package ic2.compat;

import ic2.forge.FmlMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;

public abstract class AbstractItemTagProvider
extends ItemTagsProvider {
    public AbstractItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider) {
        super(dataGenerator, blockTagsProvider, "ic2", FmlMod.existingFileHelper);
    }
}

