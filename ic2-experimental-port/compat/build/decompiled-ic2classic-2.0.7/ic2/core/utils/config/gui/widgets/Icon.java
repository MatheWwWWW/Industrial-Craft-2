/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.utils.config.gui.widgets;

import ic2.core.utils.config.api.ConfigType;
import java.util.EnumMap;
import net.minecraft.resources.ResourceLocation;

public class Icon {
    private static final ResourceLocation ICONS = new ResourceLocation("ic2:textures/gui_sprites/icons.png");
    public static final Icon DELETE = new Icon(ICONS, 0, 16, 64, 64);
    public static final Icon REVERT = new Icon(ICONS, 0, 0, 64, 64);
    public static final Icon SET_DEFAULT = new Icon(ICONS, 16, 0, 64, 64);
    public static final Icon RELOAD = new Icon(ICONS, 16, 16, 64, 64);
    public static final Icon RESTART = new Icon(ICONS, 32, 16, 64, 64);
    public static final Icon SEARCH = new Icon(ICONS, 48, 16, 64, 64);
    public static final Icon SEARCH_SELECTED = new Icon(ICONS, 48, 32, 64, 64);
    public static final Icon NOT_DEFAULT = new Icon(ICONS, 32, 0, 64, 64);
    public static final Icon NOT_DEFAULT_SELECTED = new Icon(ICONS, 48, 0, 64, 64);
    public static final EnumMap<ConfigType, Icon> TYPE_ICON = Icon.create(new Icon(ICONS, 0, 32, 64, 64), new Icon(ICONS, 16, 32, 64, 64), new Icon(ICONS, 32, 32, 64, 64));
    public static final EnumMap<ConfigType, Icon> MULTITYPE_ICON = Icon.create(new Icon(ICONS, 0, 48, 64, 64), new Icon(ICONS, 16, 48, 64, 64), new Icon(ICONS, 32, 48, 64, 64));
    ResourceLocation texture;
    int x;
    int y;
    int sheetWidth;
    int sheetHeight;

    public Icon(ResourceLocation texture, int x, int y, int sheetWidth, int sheetHeight) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.sheetWidth = sheetWidth;
        this.sheetHeight = sheetHeight;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getSheetWidth() {
        return this.sheetWidth;
    }

    public float getSheetHeight() {
        return this.sheetHeight;
    }

    private static EnumMap<ConfigType, Icon> create(Icon first, Icon second, Icon third) {
        EnumMap<ConfigType, Icon> icons = new EnumMap<ConfigType, Icon>(ConfigType.class);
        icons.put(ConfigType.CLIENT, first);
        icons.put(ConfigType.SHARED, second);
        icons.put(ConfigType.SERVER, third);
        return icons;
    }
}

