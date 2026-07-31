/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.utils.Helpers;
import net.minecraft.resources.ResourceLocation;

public class BackgroundTexture {
    public static final BackgroundTexture DEFAULT = BackgroundTexture.of().build();
    ResourceLocation backgroundTexture;
    ResourceLocation foregroundTexture;
    int backgroundBrightness = 32;
    int foregroundBrightness = 64;
    boolean disableBackgroundInLevel = true;

    private BackgroundTexture() {
    }

    public static Builder of() {
        return new Builder().withTexture("minecraft:textures/block/oak_planks.png").withBrightness(192);
    }

    public static Builder of(String id) {
        return new Builder().withTexture(id);
    }

    public static Builder of(String namespace, String path) {
        return new Builder().withTexture(namespace, path);
    }

    public static Builder of(ResourceLocation location) {
        return new Builder().withTexture(location);
    }

    public ResourceLocation getBackgroundTexture() {
        return this.backgroundTexture;
    }

    public ResourceLocation getForegroundTexture() {
        return this.foregroundTexture;
    }

    public boolean shouldDisableInLevel() {
        return this.disableBackgroundInLevel;
    }

    public int getBackgroundBrightness() {
        return this.backgroundBrightness;
    }

    public int getForegroundBrightness() {
        return this.foregroundBrightness;
    }

    public static class Builder {
        BackgroundTexture texture = new BackgroundTexture();

        public Builder withBackground(String id) {
            return this.withBackground(new ResourceLocation(id));
        }

        public Builder withBackground(String namespace, String path) {
            return this.withBackground(new ResourceLocation(namespace, path));
        }

        public Builder withBackground(ResourceLocation texture) {
            this.texture.backgroundTexture = texture;
            return this;
        }

        public Builder withForeground(String id) {
            return this.withForeground(new ResourceLocation(id));
        }

        public Builder withForeground(String namespace, String path) {
            return this.withForeground(new ResourceLocation(namespace, path));
        }

        public Builder withForeground(ResourceLocation texture) {
            this.texture.foregroundTexture = texture;
            return this;
        }

        public Builder withTexture(String id) {
            return this.withTexture(new ResourceLocation(id));
        }

        public Builder withTexture(String namespace, String path) {
            return this.withTexture(new ResourceLocation(namespace, path));
        }

        public Builder withTexture(ResourceLocation texture) {
            this.texture.backgroundTexture = texture;
            this.texture.foregroundTexture = texture;
            return this;
        }

        public Builder withBrightness(int brightness) {
            int sanitized = Helpers.clamp(brightness, 0, 255);
            return this.withBrightness(sanitized, sanitized / 2);
        }

        public Builder withBrightness(int foreground, int background) {
            this.texture.backgroundBrightness = Helpers.clamp(background, 0, 255);
            this.texture.foregroundBrightness = Helpers.clamp(foreground, 0, 255);
            return this;
        }

        public Builder withBackground(int brightness) {
            this.texture.backgroundBrightness = Helpers.clamp(brightness, 0, 255);
            return this;
        }

        public Builder withForeground(int brightness) {
            this.texture.foregroundBrightness = Helpers.clamp(brightness, 0, 255);
            return this;
        }

        public Builder withBackgroundPresentIngame(boolean value) {
            this.texture.disableBackgroundInLevel = !value;
            return this;
        }

        public BackgroundTexture build() {
            BackgroundTexture result = this.texture;
            this.texture = null;
            return result;
        }
    }
}

