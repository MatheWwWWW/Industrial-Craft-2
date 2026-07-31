/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.locale.Language
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraftforge.server.LanguageHook
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package ic2.core.platform.corehacks.mixins.server;

import ic2.core.IC2;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.server.LanguageHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={LanguageHook.class})
public class ServerLangMixin {
    @Shadow(remap=false)
    private static Map<String, String> modTable;

    @Inject(method={"loadLanguage"}, at={@At(value="RETURN")}, locals=LocalCapture.CAPTURE_FAILHARD, remap=false)
    private static void hookLangLoad(String language, MinecraftServer server, CallbackInfo info) {
        IC2.PLATFORM.registerCustomLang(T -> {
            String s = String.format("lang/%1$s_%2$s.json", T.m_135815_(), language);
            try {
                ServerLangMixin.loadLang(server.m_177941_().m_213829_(new ResourceLocation(T.m_135827_(), s)), modTable);
            }
            catch (Exception e) {
                IC2.LOGGER.warn("Skipped language file: {}:{} ({})", (Object)T.m_135827_(), (Object)s, (Object)e.toString());
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

