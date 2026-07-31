/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.ClientLanguage
 *  net.minecraft.client.resources.language.LanguageInfo
 *  net.minecraft.locale.Language
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package ic2.core.platform.corehacks.mixins.client;

import ic2.core.IC2;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@OnlyIn(value=Dist.CLIENT)
@Mixin(value={ClientLanguage.class})
public class LangMixin {
    @Inject(method={"loadFrom"}, at={@At(value="RETURN", shift=At.Shift.BY, by=-7)}, locals=LocalCapture.CAPTURE_FAILHARD)
    private static void onResourceCallBack(ResourceManager manager, List<LanguageInfo> languages, CallbackInfoReturnable<ClientLanguage> info, Map<String, String> map) {
        IC2.PLATFORM.registerCustomLang(T -> {
            int m = languages.size();
            for (int i = 0; i < m; ++i) {
                String s = String.format("lang/%1$s_%2$s.json", T.m_135815_(), ((LanguageInfo)languages.get(i)).getCode());
                try {
                    LangMixin.loadLang(manager.m_213829_(new ResourceLocation(T.m_135827_(), s)), map);
                    continue;
                }
                catch (Exception e) {
                    IC2.LOGGER.warn("Skipped language file: {}:{} ({})", (Object)T.m_135827_(), (Object)s, (Object)e.toString());
                }
            }
        });
    }

    private static boolean loadLang(List<Resource> resources, Map<String, String> lang) {
        int old = lang.size();
        for (Resource iresource : resources) {
            try {
                InputStream inputstream = iresource.m_215507_();
                try {
                    Language.m_128108_((InputStream)inputstream, lang::put);
                }
                finally {
                    if (inputstream == null) continue;
                    inputstream.close();
                }
            }
            catch (IOException ioexception) {
                IC2.LOGGER.warn("Failed to load translations from {}", (Object)iresource, (Object)ioexception);
            }
        }
        return old != lang.size();
    }
}

