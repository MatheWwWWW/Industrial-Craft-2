/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.platform.rendering.events;

import java.util.function.Consumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

@OnlyIn(value=Dist.CLIENT)
public class TilesRenderedEvent
extends Event {
    Consumer<RenderType> listener;
    boolean translucent;

    public TilesRenderedEvent(Consumer<RenderType> listener, boolean translucent) {
        this.listener = listener;
        this.translucent = translucent;
    }

    public void finishType(RenderType type) {
        this.listener.accept(type);
    }

    public boolean isTranslucent() {
        return this.translucent;
    }
}

