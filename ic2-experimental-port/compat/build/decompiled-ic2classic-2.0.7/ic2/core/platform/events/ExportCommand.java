/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.internal.Streams
 *  com.google.gson.stream.JsonWriter
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.platform.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import ic2.core.IC2;
import java.io.Writer;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ExportCommand {
    public static void export(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder source = Commands.m_82127_((String)"ic2c_export");
        source.executes(ExportCommand::execute);
        dispatcher.register(source);
    }

    public static int execute(CommandContext<CommandSourceStack> context) {
        Path path = IC2.PLATFORM.getConfigFolder().resolve("ic2c").resolve("Datapack.zip");
        try {
            Files.deleteIfExists(path);
            if (Files.notExists(path, new LinkOption[0])) {
                try {
                    ZipOutputStream stream = new ZipOutputStream(Files.newOutputStream(path, new OpenOption[0]));
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try (FileSystem system = FileSystems.newFileSystem(path);){
                ExportCommand.exportRecipes(system, IC2.RECIPES.get(true).createRecipeFiles());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ((CommandSourceStack)context.getSource()).m_81354_((Component)Component.m_237113_((String)"successfully exported datapack"), false);
        return 0;
    }

    private static void exportRecipes(FileSystem system, Map<ResourceLocation, JsonObject> map) {
        map.forEach((K, V) -> ExportCommand.exportRecipe(system, K, V));
    }

    private static void exportRecipe(FileSystem system, ResourceLocation location, JsonObject obj) {
        try {
            Path path = system.getPath("data", location.m_135827_(), location.m_135815_() + ".json");
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            try (JsonWriter writer = new JsonWriter((Writer)Files.newBufferedWriter(path, new OpenOption[0]));){
                writer.setIndent("\t");
                Streams.write((JsonElement)obj, (JsonWriter)writer);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

