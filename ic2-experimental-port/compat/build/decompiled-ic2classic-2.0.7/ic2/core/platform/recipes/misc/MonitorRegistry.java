/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.platform.recipes.misc;

import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IDisplayRegistry;
import ic2.api.tiles.display.impl.ItemDisplayInfo;
import ic2.api.tiles.display.impl.ProgressDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.core.item.tool.infos.components.InventoryDisplayInfo;
import ic2.core.item.tool.infos.components.TimeDisplayInfo;
import ic2.core.item.tool.infos.components.ViewerLogInfo;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class MonitorRegistry
implements IDisplayRegistry {
    public static final MonitorRegistry INSTANCE = new MonitorRegistry();
    Map<ResourceLocation, Function<FriendlyByteBuf, IDisplayInfo>> idToInstance = CollectionUtils.createMap();
    Map<Class<? extends IDisplayInfo>, ResourceLocation> clzToId = CollectionUtils.createMap();

    public void init() {
        ((IDisplayRegistry.DelegateRegistry)IDisplayInfo.REGISTRY).setRegistry(this);
        this.register(new ResourceLocation("ic2", "string"), StringDisplayInfo.class, StringDisplayInfo::new);
        this.register(new ResourceLocation("ic2", "progress"), ProgressDisplayInfo.class, ProgressDisplayInfo::new);
        this.register(new ResourceLocation("ic2", "time"), TimeDisplayInfo.class, TimeDisplayInfo::new);
        this.register(new ResourceLocation("ic2", "item"), ItemDisplayInfo.class, ItemDisplayInfo::new);
        this.register(new ResourceLocation("ic2", "view"), ViewerLogInfo.class, ViewerLogInfo::new);
        this.register(new ResourceLocation("ic2", "inv"), InventoryDisplayInfo.class, InventoryDisplayInfo::new);
    }

    @Override
    public void register(ResourceLocation id, Class<? extends IDisplayInfo> info, Function<FriendlyByteBuf, IDisplayInfo> creator) {
        this.idToInstance.put(id, creator);
        this.clzToId.put(info, id);
    }

    @Override
    public void serialize(IDisplayInfo info, FriendlyByteBuf buffer) {
        ResourceLocation id = this.clzToId.get(info.getClass());
        if (id == null) {
            return;
        }
        buffer.m_130085_(id);
        info.serialize(buffer);
    }

    @Override
    public IDisplayInfo deserialize(FriendlyByteBuf buffer) {
        return this.idToInstance.get(buffer.m_130281_()).apply(buffer);
    }
}

