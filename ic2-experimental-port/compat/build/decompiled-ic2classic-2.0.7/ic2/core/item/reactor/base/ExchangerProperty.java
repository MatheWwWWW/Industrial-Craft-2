/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.reactor.base;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.List;

public class ExchangerProperty {
    public static final List<Vec2i> DEFAULT_OFFSETS = CollectionUtils.asList(new Vec2i(-1, 0), new Vec2i(1, 0), new Vec2i(0, -1), new Vec2i(0, 1));
    public int self;
    public int reactor;
    public int heatStorage;
    public String textureFolder;
    public String textureName;
    public List<Vec2i> offsets;
    public short id;

    public ExchangerProperty(int self, int reactor, int heatStorage, String textureFolder, String textureName, int id) {
        this(self, reactor, heatStorage, textureFolder, textureName, DEFAULT_OFFSETS, id);
    }

    public ExchangerProperty(int self, int reactor, int heatStorage, String textureFolder, String textureName, List<Vec2i> offsets, int id) {
        this.self = self;
        this.reactor = reactor;
        this.heatStorage = heatStorage;
        this.textureFolder = textureFolder;
        this.textureName = textureName;
        this.offsets = offsets;
        this.id = (short)id;
    }

    public int getSelf() {
        return this.self;
    }

    public int getReactor() {
        return this.reactor;
    }

    public int getHeatStorage() {
        return this.heatStorage;
    }

    public String getTextureFolder() {
        return this.textureFolder;
    }

    public String getTextureName() {
        return this.textureName;
    }

    public List<Vec2i> getOffsets() {
        return this.offsets;
    }

    public short getComponentID() {
        return this.id;
    }
}

