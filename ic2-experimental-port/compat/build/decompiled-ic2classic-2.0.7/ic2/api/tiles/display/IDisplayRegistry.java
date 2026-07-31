/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.api.tiles.display;

import ic2.api.tiles.display.IDisplayInfo;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IDisplayRegistry {
    public void register(ResourceLocation var1, Class<? extends IDisplayInfo> var2, Function<FriendlyByteBuf, IDisplayInfo> var3);

    public void serialize(IDisplayInfo var1, FriendlyByteBuf var2);

    public IDisplayInfo deserialize(FriendlyByteBuf var1);

    public static class DelegateRegistry
    implements IDisplayRegistry {
        IDisplayRegistry registry;

        public void setRegistry(IDisplayRegistry registry) {
            this.registry = registry;
        }

        @Override
        public void register(ResourceLocation id, Class<? extends IDisplayInfo> info, Function<FriendlyByteBuf, IDisplayInfo> creator) {
            if (this.registry == null) {
                throw new UnsupportedOperationException();
            }
            this.registry.register(id, info, creator);
        }

        @Override
        public void serialize(IDisplayInfo info, FriendlyByteBuf buffer) {
            if (this.registry == null) {
                throw new UnsupportedOperationException();
            }
            this.registry.serialize(info, buffer);
        }

        @Override
        public IDisplayInfo deserialize(FriendlyByteBuf buffer) {
            if (this.registry == null) {
                throw new UnsupportedOperationException();
            }
            return this.registry.deserialize(buffer);
        }
    }
}

