/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  net.minecraft.advancements.Advancement$Builder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.PackResources
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.metadata.MetadataSectionSerializer
 *  net.minecraft.server.packs.metadata.pack.PackMetadataSection
 *  net.minecraft.server.packs.repository.Pack
 *  net.minecraft.server.packs.repository.Pack$PackConstructor
 *  net.minecraft.server.packs.repository.Pack$Position
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.repository.PackSource
 *  net.minecraft.server.packs.repository.RepositorySource
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.forgespi.language.IModInfo
 *  org.apache.commons.io.IOUtils
 */
package ic2.core.platform.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.IC2;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.commons.io.IOUtils;

public class PackHack {
    public static void loadHack(PackRepository list) {
        list.addPackFinder((RepositorySource)new PackFinder());
    }

    public static class PackFinder
    implements RepositorySource {
        public void m_7686_(Consumer<Pack> acceptor, Pack.PackConstructor factory) {
            acceptor.accept(factory.create("mod:ic2c", (Component)Component.m_237113_((String)"IC2Classic Resources"), true, IC2RecipeHacker::new, new PackMetadataSection((Component)Component.m_237113_((String)"IC2Classic Resources"), 6), Pack.Position.BOTTOM, PackSource.f_10528_, false));
        }
    }

    public static class IC2RecipeHacker
    implements PackResources {
        Object2IntMap<ResourceLocation> recipesToSerialize = new Object2IntOpenHashMap();
        String[] loaders = new String[]{"{\"type\": \"ic2:crafting_ic2_automated\",\"enabled\": true}", "{\"type\": \"ic2:cooking_ic2_serializer\",\"enabled\": true}", "{\"type\": \"ic2:single_item_ic2_serializer\",\"enabled\": true}", "{\"type\": \"ic2:smithing_ic2_serializer\",\"enabled\": true}"};
        Set<String> searched = ObjectSets.synchronize(CollectionUtils.createSet());

        public void close() {
        }

        public boolean isHidden() {
            return true;
        }

        public InputStream m_5542_(String fileName) throws IOException {
            throw new FileNotFoundException(fileName);
        }

        public InputStream m_8031_(PackType type, ResourceLocation location) throws IOException {
            if (type == PackType.SERVER_DATA) {
                if (location.m_135815_().startsWith("tags")) {
                    location = new ResourceLocation(location.m_135827_(), location.m_135815_().substring(0, location.m_135815_().length() - 5));
                    JsonArray array = new JsonArray();
                    for (String tag : IC2Tags.TAGS_TO_LOAD.getValue(location)) {
                        array.add(tag);
                    }
                    if (array.isEmpty()) {
                        return null;
                    }
                    JsonObject obj = new JsonObject();
                    obj.addProperty("replace", Boolean.valueOf(false));
                    obj.add("values", (JsonElement)array);
                    return IOUtils.toInputStream((String)obj.toString(), (String)"UTF-8");
                }
                if (location.m_135815_().startsWith("recipes")) {
                    int id = this.recipesToSerialize.getInt((Object)location);
                    if (id == 0) {
                        return null;
                    }
                    return IOUtils.toInputStream((String)this.loaders[id - 1], (String)"UTF-8");
                }
                if (location.m_135815_().startsWith("advancements")) {
                    Advancement.Builder builder = IC2Advancements.ADVANCEMENTS.getValue(new ResourceLocation(location.m_135827_(), location.m_135815_().substring("advancements/".length(), location.m_135815_().length() - 5)));
                    return builder == null ? null : IOUtils.toInputStream((String)builder.m_138400_().toString(), (String)"UTF-8");
                }
            }
            return null;
        }

        public Collection<ResourceLocation> m_214146_(PackType type, String namespaceIn, String pathIn, Predicate<ResourceLocation> filterIn) {
            if (type == PackType.SERVER_DATA) {
                if (this.searched.add(pathIn)) {
                    String s = pathIn.split("/")[0];
                    if (s.equals("recipes")) {
                        try {
                            IC2.RECIPES.get(true).reloadRecipes();
                        }
                        catch (Exception e) {
                            IC2.LOGGER.info("Reloading Recipes Crash", (Throwable)e);
                        }
                    } else if (s.startsWith("advancements")) {
                        try {
                            IC2Advancements.reload();
                        }
                        catch (Exception e) {
                            IC2.LOGGER.info("Reloading Advancements Crash", (Throwable)e);
                        }
                    }
                }
                if (pathIn.startsWith("recipes")) {
                    ObjectList recipes = CollectionUtils.createList();
                    for (ResourceLocation location : AdvRecipeRegistry.INSTANCE.getRecipes().getResourceForMod(namespaceIn)) {
                        location = new ResourceLocation(location.m_135827_(), "recipes/" + location.m_135815_() + ".json");
                        this.recipesToSerialize.put((Object)location, 1);
                        if (!filterIn.test(location)) continue;
                        recipes.add((ResourceLocation)location);
                    }
                    for (ResourceLocation location : AdvRecipeRegistry.INSTANCE.getCooking().getResourceForMod(namespaceIn)) {
                        location = new ResourceLocation(location.m_135827_(), "recipes/" + location.m_135815_() + ".json");
                        this.recipesToSerialize.put((Object)location, 2);
                        if (!filterIn.test(location)) continue;
                        recipes.add((ResourceLocation)location);
                    }
                    for (ResourceLocation location : AdvRecipeRegistry.INSTANCE.getSingleRecipes().getResourceForMod(namespaceIn)) {
                        location = new ResourceLocation(location.m_135827_(), "recipes/" + location.m_135815_() + ".json");
                        this.recipesToSerialize.put((Object)location, 3);
                        if (!filterIn.test(location)) continue;
                        recipes.add((ResourceLocation)location);
                    }
                    for (ResourceLocation location : AdvRecipeRegistry.INSTANCE.getSmithingRecipes().getResourceForMod(namespaceIn)) {
                        location = new ResourceLocation(location.m_135827_(), "recipes/" + location.m_135815_() + ".json");
                        this.recipesToSerialize.put((Object)location, 4);
                        if (!filterIn.test(location)) continue;
                        recipes.add((ResourceLocation)location);
                    }
                    return recipes;
                }
                if (pathIn.startsWith("tags")) {
                    return this.getLocationsForIdentifier(namespaceIn, pathIn, filterIn);
                }
                if (pathIn.startsWith("advancements")) {
                    return this.convert(IC2Advancements.ADVANCEMENTS.getResourceForMod(namespaceIn), "advancements", filterIn);
                }
            }
            return Collections.emptyList();
        }

        public boolean m_7211_(PackType type, ResourceLocation location) {
            if (type == PackType.SERVER_DATA) {
                String path = location.m_135815_();
                if (path.startsWith("tags")) {
                    return IC2Tags.TAGS_TO_LOAD.contains(new ResourceLocation(location.m_135827_(), path.substring(0, path.length() - 5)));
                }
                if (path.startsWith("recipes") && path.endsWith(".json")) {
                    return AdvRecipeRegistry.INSTANCE.contains(new ResourceLocation(location.m_135827_(), path.substring("recipes/".length(), path.length() - 5)));
                }
                if (path.startsWith("advancements")) {
                    return IC2Advancements.ADVANCEMENTS.contains(new ResourceLocation(location.m_135827_(), path.substring("advancements/".length(), path.length() - 5)));
                }
            }
            return false;
        }

        public Set<String> m_5698_(PackType type) {
            if (type == PackType.CLIENT_RESOURCES) {
                return ObjectSets.emptySet();
            }
            ObjectSet set = CollectionUtils.createSet();
            for (IModInfo info : ModList.get().getMods()) {
                set.add((String)info.getModId());
            }
            set.add((String)"ic2");
            set.add((String)"minecraft");
            return set;
        }

        public <T> T m_5550_(MetadataSectionSerializer<T> deserializer) throws IOException {
            if (deserializer.m_7991_().equalsIgnoreCase("pack")) {
                return (T)new PackMetadataSection((Component)Component.m_237113_((String)"IC2ServerPack"), 9);
            }
            return null;
        }

        public String m_8017_() {
            return "";
        }

        public List<ResourceLocation> convert(Collection<ResourceLocation> collection, String prefix, Predicate<ResourceLocation> filterIn) {
            ObjectList entries = CollectionUtils.createList();
            for (ResourceLocation location : collection) {
                ResourceLocation result = new ResourceLocation(location.m_135827_(), prefix + "/" + location.m_135815_() + ".json");
                if (!filterIn.test(result)) continue;
                entries.add((ResourceLocation)result);
            }
            return entries;
        }

        public List<ResourceLocation> getLocationsForIdentifier(String namespace, String id, Predicate<ResourceLocation> filterIn) {
            ObjectList locations = CollectionUtils.createList();
            for (ResourceLocation location : IC2Tags.TAGS_TO_LOAD.getResourceForMod(namespace)) {
                ResourceLocation result = new ResourceLocation(location.m_135827_(), location.m_135815_() + ".json");
                if (!location.m_135815_().startsWith(id) || !filterIn.test(result)) continue;
                locations.add((ResourceLocation)result);
            }
            return locations;
        }
    }
}

