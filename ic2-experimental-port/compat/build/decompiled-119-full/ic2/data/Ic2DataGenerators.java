/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.common.hash.HashingOutputStream
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  net.minecraft.data.CachedOutput
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.DataProvider
 */
package ic2.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import ic2.data.recipe.BlastFurnaceRecipeProvider;
import ic2.data.recipe.CannerRecipeProvider;
import ic2.data.recipe.CentrifugeRecipeProvider;
import ic2.data.recipe.CompressorRecipeProvider;
import ic2.data.recipe.ExtractorRecipeProvider;
import ic2.data.recipe.MaceratorRecipeProvider;
import ic2.data.recipe.MetalFormerRecipeProvider;
import ic2.data.recipe.OreWasherRecipeProvider;
import ic2.data.recipe.ShapedRecipeProvider;
import ic2.data.recipe.ShapelessRecipeProvider;
import ic2.data.tag.Ic2BlockTagProvider;
import ic2.data.tag.Ic2ItemTagProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

public class Ic2DataGenerators {
    public static void setup(DataGenerator dataGenerator) {
        dataGenerator.m_236039_(true, (DataProvider)new Ic2ItemTagProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new Ic2BlockTagProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new BlastFurnaceRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new CannerRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new CentrifugeRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new CompressorRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new ExtractorRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new MaceratorRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new MetalFormerRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new OreWasherRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new ShapedRecipeProvider(dataGenerator));
        dataGenerator.m_236039_(true, (DataProvider)new ShapelessRecipeProvider(dataGenerator));
    }

    public static void saveJsonPreserveOrder(Gson gson, CachedOutput cachedOutput, JsonElement jsonElement, Path path) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), (OutputStream)byteArrayOutputStream);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)hashingOutputStream, StandardCharsets.UTF_8);
        outputStreamWriter.write(gson.toJson(jsonElement));
        ((Writer)outputStreamWriter).close();
        cachedOutput.m_213871_(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
    }
}

