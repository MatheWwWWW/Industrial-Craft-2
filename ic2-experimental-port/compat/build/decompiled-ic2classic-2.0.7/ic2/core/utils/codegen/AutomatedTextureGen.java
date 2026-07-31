/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.internal.Streams
 *  com.google.gson.stream.JsonWriter
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectIterators
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.utils.codegen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import net.minecraft.resources.ResourceLocation;

public class AutomatedTextureGen {
    public static void generateTextures(String basePath) {
        try {
            AutomatedTextureGen.generateTextures("ic2", basePath, true, "block", "item", "misc");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletePackages(String mod, String basePath) throws IOException {
        Files.walk(Paths.get(basePath + "/resources/assets/" + mod + "/textures", new String[0]), new FileVisitOption[0]).filter(AutomatedTextureGen::isPackage).forEach(AutomatedTextureGen::remove);
    }

    public static void generateTextures(String mod, String basePath, boolean filterDuplicates, String ... validFolders) throws IOException {
        AutomatedTextureGen.deletePackages(mod, basePath);
        TextureCache cache = new TextureCache(filterDuplicates);
        Path path = Paths.get(basePath + "/resources/assets/" + mod + "/textures", new String[0]);
        System.out.println("Finding Texture Folders");
        ObjectList files = ObjectIterators.pour(Arrays.stream(validFolders).map(T -> path.resolve((String)T)).flatMap(AutomatedTextureGen::walk).iterator());
        System.out.println("Filtering Textures: [" + files.size() + "] Potential Textures Found");
        files.removeIf(x$0 -> Files.isDirectory(x$0, new LinkOption[0]));
        files.removeIf(AutomatedTextureGen::isInvalidFile);
        files.sort(PathComparator.INSTANCE);
        System.out.println("Generating Texture Cache: [" + files.size() + "] Textures Found");
        long time = System.nanoTime();
        files.forEach(T -> cache.addTexture((Path)T, AutomatedTextureGen.getTexture(mod, T, path)));
        time = System.nanoTime() - time;
        System.out.println("Textures Processed: " + cache.processed + ", Textures Left: " + cache.newEntries + ", Texture Exact Duplicates: " + (cache.processed - cache.newEntries) + MathUtils.toLagString(" Process Time", time));
        for (String s : validFolders) {
            AutomatedTextureGen.generateTextureFiles(path, mod, s, cache);
        }
        System.out.println("Updated packages");
    }

    public static void generateTextureFiles(Path basePath, String mod, String folder, TextureCache cache) throws IOException {
        for (Path path : IterableWrapper.wrap(Files.walk(basePath.resolve(folder), new FileVisitOption[0]).filter(x$0 -> Files.isDirectory(x$0, new LinkOption[0])).iterator())) {
            ObjectList files = ObjectIterators.pour(Files.walk(path, 1, new FileVisitOption[0]).iterator());
            files.removeIf(AutomatedTextureGen::isInvalidFile);
            files.sort(PathComparator.INSTANCE);
            if (files.isEmpty()) continue;
            JsonObject obj = new JsonObject();
            for (Path file : files) {
                obj.addProperty(AutomatedTextureGen.getFileName(file), cache.getTexturePath(AutomatedTextureGen.getTexture(mod, file, basePath)).toString());
            }
            try (JsonWriter writer = new JsonWriter((Writer)Files.newBufferedWriter(path.resolve("texture_package.json"), new OpenOption[0]));){
                writer.setIndent("\t");
                Streams.write((JsonElement)obj, (JsonWriter)writer);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Stream<Path> walk(Path path) {
        try {
            return Files.walk(path, new FileVisitOption[0]);
        }
        catch (Exception e) {
            return Stream.empty();
        }
    }

    private static ResourceLocation getTexture(String mod, Path texture, Path basePath) {
        String s = basePath.relativize(texture).toString().replace("\\", "/");
        return new ResourceLocation(mod, s.substring(0, s.lastIndexOf(".")));
    }

    private static String getFileName(Path path) {
        String s = path.getFileName().toString();
        return s.substring(0, s.lastIndexOf("."));
    }

    private static boolean isPackage(Path path) {
        return path.getFileName().toString().equals("texture_package.json");
    }

    private static boolean isInvalidFile(Path path) {
        return !path.getFileName().toString().endsWith("png");
    }

    private static void remove(Path path) {
        try {
            Files.deleteIfExists(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class TextureCache {
        Object2ObjectMap<ResourceLocation, int[]> images = CollectionUtils.createLinkedMap();
        Object2ObjectMap<ResourceLocation, ResourceLocation> paths = CollectionUtils.createLinkedMap();
        int processed = 0;
        int newEntries = 0;
        boolean filterDuplicates;

        public TextureCache(boolean filterDuplicates) {
            this.filterDuplicates = filterDuplicates;
        }

        public void addTexture(Path path, ResourceLocation location) {
            ++this.processed;
            if (this.images.containsKey((Object)location)) {
                return;
            }
            try {
                BufferedImage image = ImageIO.read(Files.newInputStream(path, new OpenOption[0]));
                if (image == null) {
                    System.out.println("No Image found: " + path);
                    return;
                }
                int[] imageData = this.toPixels(image);
                if (imageData == null) {
                    System.out.println("Image is not Byte Pixels?: " + path + ": " + image.getRaster().getDataBuffer().getClass());
                    return;
                }
                ResourceLocation match = this.findMatchingImage(imageData);
                if (match == null) {
                    this.images.put((Object)location, (Object)imageData);
                    this.paths.put((Object)location, (Object)location);
                    ++this.newEntries;
                    return;
                }
                this.images.put((Object)location, (Object)((int[])this.images.get((Object)match)));
                this.paths.put((Object)location, (Object)match);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ResourceLocation getTexturePath(ResourceLocation mainPath) {
            return (ResourceLocation)this.paths.get((Object)mainPath);
        }

        private ResourceLocation findMatchingImage(int[] image) {
            if (!this.filterDuplicates) {
                return null;
            }
            for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.images)) {
                if (!Arrays.equals((int[])entry.getValue(), image)) continue;
                return (ResourceLocation)entry.getKey();
            }
            return null;
        }

        private int[] toPixels(BufferedImage image) {
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            return pixels;
        }
    }

    private static class PathComparator
    implements Comparator<Path> {
        public static Comparator<Path> INSTANCE = new PathComparator();

        private PathComparator() {
        }

        @Override
        public int compare(Path o1, Path o2) {
            return Collator.getInstance(Locale.GERMANY).compare(o1.toString(), o2.toString());
        }
    }
}

