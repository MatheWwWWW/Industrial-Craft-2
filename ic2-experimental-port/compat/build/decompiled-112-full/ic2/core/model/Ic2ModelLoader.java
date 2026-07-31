/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.IResourceManager
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.ICustomModelLoader
 *  net.minecraftforge.client.model.IModel
 */
package ic2.core.model;

import ic2.core.model.IReloadableModel;
import ic2.core.model.ModelComparator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class Ic2ModelLoader
implements ICustomModelLoader {
    private static final Map<ResourceLocation, IReloadableModel> models = new HashMap<ResourceLocation, IReloadableModel>();

    public void register(String path, IReloadableModel model) {
        this.register(new ResourceLocation("ic2", path), model);
    }

    public void register(ResourceLocation location, IReloadableModel model) {
        models.put(location, model);
    }

    public void func_110549_a(IResourceManager resourceManager) {
        for (IReloadableModel model : models.values()) {
            model.onReload();
        }
        ModelComparator.onReload();
    }

    public boolean accepts(ResourceLocation modelLocation) {
        return models.containsKey(modelLocation);
    }

    public IModel loadModel(ResourceLocation modelLocation) throws IOException {
        return models.get(modelLocation);
    }
}

