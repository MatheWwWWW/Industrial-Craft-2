/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ResourceLocationException
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.Material
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.TextureStitchEvent$Post
 *  net.minecraftforge.client.event.TextureStitchEvent$Pre
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.platform.rendering;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ic2.core.IC2;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(value=Dist.CLIENT)
public class IC2Textures {
    public static final IC2Textures INSTANCE = new IC2Textures();
    Map<ResourceLocation, Map<String, TextureAtlasSprite>> spriteMap = CollectionUtils.createMap();
    List<Material> materials = CollectionUtils.createList();
    List<ITextureEntry> toLoad = new ObjectArrayList();

    public void registerRenderMaterial(Material material) {
        this.materials.add(material);
    }

    public static Map<String, TextureAtlasSprite> getMappedEntries(ResourceLocation location) {
        Map<String, TextureAtlasSprite> m = IC2Textures.INSTANCE.spriteMap.get(location);
        if (m == null) {
            throw new ResourceLocationException(location.toString() + " is not registered as a texture location");
        }
        return m;
    }

    public static Map<String, TextureAtlasSprite> getMappedEntries(String mod, String id) {
        return IC2Textures.getMappedEntries(new ResourceLocation(mod, id));
    }

    public static Map<String, TextureAtlasSprite> getMappedEntriesItem(String mod, String id) {
        return IC2Textures.getMappedEntries(new ResourceLocation(mod, "item/" + id));
    }

    public static Map<String, TextureAtlasSprite> getMappedEntriesBlock(String mod, String id) {
        return IC2Textures.getMappedEntries(new ResourceLocation(mod, "block/" + id));
    }

    public static Map<String, TextureAtlasSprite> getMappedEntriesIC2(String id) {
        return IC2Textures.getMappedEntries(new ResourceLocation(IC2.MODID, id));
    }

    public static Map<String, TextureAtlasSprite> getMappedEntriesBlockIC2(String id) {
        return IC2Textures.getMappedEntries(new ResourceLocation(IC2.MODID, "block/" + id));
    }

    public static Map<String, TextureAtlasSprite> getMappedEntriesItemIC2(String id) {
        return IC2Textures.getMappedEntries(new ResourceLocation(IC2.MODID, "item/" + id));
    }

    private void loadTextures(Consumer<ResourceLocation> loader) {
        this.load();
        for (ITextureEntry load : this.toLoad) {
            load.load(loader);
        }
    }

    @SubscribeEvent
    public void preLoad(TextureStitchEvent.Pre event) {
        if (event.getAtlas().m_118330_().m_135815_().equals("textures/atlas/blocks.png")) {
            this.loadTextures(arg_0 -> ((TextureStitchEvent.Pre)event).addSprite(arg_0));
        }
    }

    @SubscribeEvent
    public void postLoad(TextureStitchEvent.Post event) {
        if (event.getAtlas().m_118330_().m_135815_().equals("textures/atlas/blocks.png")) {
            for (ITextureEntry load : this.toLoad) {
                this.spriteMap.put(load.genKey(), load.mapData(event.getAtlas()));
            }
        }
    }

    public void loadRenderMaterials(Consumer<Material> materials) {
        this.materials.forEach(materials);
    }

    private void load() {
        this.toLoad.clear();
        ResourceManager manager = Minecraft.m_91087_().m_91098_();
        Map resources = manager.m_214159_("textures", T -> T.m_135815_().endsWith("texture_package.json"));
        for (Map.Entry entry : resources.entrySet()) {
            String path = this.toPath((ResourceLocation)entry.getKey());
            String[] info = path.split("\\/", 2);
            boolean isBlock = info[0].equals("block");
            try {
                BufferedReader reader = ((Resource)entry.getValue()).m_215508_();
                try {
                    Object2ObjectMap<String, ResourceLocation> map = CollectionUtils.createMap();
                    JsonObject obj = JsonParser.parseReader((Reader)reader).getAsJsonObject();
                    for (Map.Entry subEntry : obj.entrySet()) {
                        ResourceLocation texture;
                        if (!((JsonElement)subEntry.getValue()).isJsonPrimitive() || (texture = ResourceLocation.m_135820_((String)((JsonElement)subEntry.getValue()).getAsString())) == null) continue;
                        if (isBlock) {
                            this.decodeSides((String)subEntry.getKey(), texture, (Map<String, ResourceLocation>)map);
                            continue;
                        }
                        map.put((String)subEntry.getKey(), texture);
                    }
                    this.toLoad.add(new MappedTextureEntry(((ResourceLocation)entry.getKey()).m_135827_(), info[0], info[1], (Map<String, ResourceLocation>)map));
                }
                finally {
                    if (reader == null) continue;
                    reader.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void decodeSides(String name, ResourceLocation texture, Map<String, ResourceLocation> map) {
        String subName;
        if (this.isSpecialDirection(name)) {
            for (String s : this.convertToVariants(name)) {
                map.put(s, texture);
            }
            return;
        }
        int lastUnderscore = name.lastIndexOf(95);
        if (lastUnderscore >= 0 && this.isSpecialDirection(subName = name.substring(lastUnderscore + 1))) {
            String pre = name.substring(0, lastUnderscore);
            for (String s : this.convertToVariants(subName)) {
                map.put(pre + "_" + s, texture);
            }
            return;
        }
        map.put(name, texture);
    }

    private Iterable<String> convertToVariants(String s) {
        switch (s) {
            case "vertical": {
                return CollectionUtils.asList("up", "down", s);
            }
            case "horizontal": {
                return CollectionUtils.asList("east", "west", "north", "south", s);
            }
            case "side": {
                return CollectionUtils.asList("east", "west", "north", "south", s);
            }
            case "xaxis": {
                return CollectionUtils.asList("east", "west", s);
            }
            case "yaxis": {
                return CollectionUtils.asList("up", "down", s);
            }
            case "zaxis": {
                return CollectionUtils.asList("north", "south", s);
            }
            case "back": {
                return CollectionUtils.asList("north", s);
            }
            case "front": {
                return CollectionUtils.asList("south", s);
            }
            case "left": {
                return CollectionUtils.asList("west", s);
            }
            case "right": {
                return CollectionUtils.asList("east", s);
            }
            case "top": {
                return CollectionUtils.asList("up", s);
            }
            case "bottom": {
                return CollectionUtils.asList("down", s);
            }
            case "all": {
                return CollectionUtils.asList("east", "west", "north", "south", "up", "down", s);
            }
        }
        return null;
    }

    private boolean isSpecialDirection(String s) {
        switch (s) {
            case "all": 
            case "vertical": 
            case "horizontal": 
            case "xaxis": 
            case "yaxis": 
            case "zaxis": 
            case "side": 
            case "top": 
            case "bottom": 
            case "left": 
            case "right": 
            case "front": 
            case "back": {
                return true;
            }
        }
        return false;
    }

    private String toPath(ResourceLocation id) {
        String s = id.m_135815_();
        return s.substring(s.indexOf("/") + 1, s.lastIndexOf("/"));
    }

    public static interface ITextureEntry {
        public ResourceLocation genKey();

        public Map<String, TextureAtlasSprite> mapData(TextureAtlas var1);

        public void load(Consumer<ResourceLocation> var1);
    }

    public static class MappedTextureEntry
    implements ITextureEntry {
        String mod;
        String target;
        String type;
        Map<String, ResourceLocation> sprites;

        public MappedTextureEntry(String mod, String target, String type, Map<String, ResourceLocation> sprites) {
            this.mod = mod;
            this.target = target;
            this.type = type;
            this.sprites = sprites;
        }

        @Override
        public ResourceLocation genKey() {
            return new ResourceLocation(this.mod, this.target + "/" + this.type);
        }

        @Override
        public Map<String, TextureAtlasSprite> mapData(TextureAtlas texture) {
            Object2ObjectSortedMap<String, TextureAtlasSprite> spriteMap = CollectionUtils.createLinkedMap();
            for (Map.Entry<String, ResourceLocation> entry : this.sprites.entrySet()) {
                spriteMap.put(entry.getKey(), texture.m_118316_(entry.getValue()));
            }
            return spriteMap;
        }

        @Override
        public void load(Consumer<ResourceLocation> location) {
            this.sprites.values().forEach(location);
        }
    }
}

