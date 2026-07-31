/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.tooltips.helper;

import ic2.core.IC2;
import java.text.NumberFormat;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;

public abstract class ConfigTranslationSupplier
implements Supplier<Component> {
    protected Component comp;
    protected String key;
    protected NumberFormat formatter;

    public ConfigTranslationSupplier(String key, NumberFormat formatter) {
        this.key = key;
        this.formatter = formatter;
        IC2.CONFIG.addLoadedListener(() -> {
            this.comp = null;
        });
    }

    protected abstract Component buildTextComponent();

    @Override
    public Component get() {
        if (this.comp == null) {
            this.comp = this.buildTextComponent();
        }
        return this.comp;
    }
}

