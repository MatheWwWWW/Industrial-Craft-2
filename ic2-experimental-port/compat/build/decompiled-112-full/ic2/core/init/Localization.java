/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.resources.IReloadableResourceManager
 *  net.minecraft.client.resources.IResource
 *  net.minecraft.client.resources.IResourceManager
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.client.resources.Locale
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.text.translation.I18n
 *  net.minecraft.util.text.translation.LanguageMap
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.init;

import com.google.common.base.Charsets;
import ic2.core.IC2;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.Locale;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Localization {
    private static final String defaultLang = "en_us";
    private static final String ic2LangKey = "ic2.";

    public static void preInit(File modSourceFile) {
        if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            Map<String, String> map = Localization.getLanguageMapMap();
            Localization.loadServerLangFile(modSourceFile, map);
        } else {
            Localization.registerResourceReloadHook();
        }
    }

    private static void loadServerLangFile(File modSourceFile, Map<String, String> out) {
        String path = "/assets/ic2/" + Localization.getLangPath(defaultLang);
        InputStream is = Localization.class.getResourceAsStream(path);
        try {
            Localization.loadLocalization(is, out);
            IC2.log.trace(LogCategory.Resource, "Successfully loaded server localization.");
        }
        catch (IOException e) {
            IC2.log.warn(LogCategory.Resource, "Failed to load server localization.");
            e.printStackTrace();
        }
    }

    private static String getLangPath(String language) {
        return "lang_ic2/" + language + ".properties";
    }

    @SideOnly(value=Side.CLIENT)
    private static void registerResourceReloadHook() {
        IResourceManager resManager = Minecraft.func_71410_x().func_110442_L();
        if (resManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager)resManager).func_110542_a(new IResourceManagerReloadListener(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void func_110549_a(IResourceManager manager) {
                    HashMap tmpMap = new HashMap();
                    Map<String, String> lmMap = Localization.getLanguageMapMap();
                    Map<String, String> localeMap = Localization.getLocaleMap();
                    LinkedHashSet<String> languages = new LinkedHashSet<String>();
                    languages.add(Localization.defaultLang);
                    languages.add(Minecraft.func_71410_x().field_71474_y.field_74363_ab);
                    for (String lang : languages) {
                        try {
                            for (IResource res : manager.func_135056_b(new ResourceLocation("ic2", Localization.getLangPath(lang)))) {
                                try {
                                    tmpMap.clear();
                                    Localization.loadLocalization(res.func_110527_b(), tmpMap);
                                    lmMap.putAll(tmpMap);
                                    localeMap.putAll(tmpMap);
                                    IC2.log.debug(LogCategory.Resource, "Loaded translation keys from %s.", res.func_177241_a());
                                }
                                finally {
                                    try {
                                        res.close();
                                    }
                                    catch (IOException iOException) {}
                                }
                            }
                        }
                        catch (FileNotFoundException e) {
                            IC2.log.debug(LogCategory.Resource, "No translation file for language %s.", lang);
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

    private static void loadLocalization(InputStream inputStream, Map<String, String> out) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(inputStream, Charsets.UTF_8));
        for (Map.Entry<Object, Object> entries : properties.entrySet()) {
            Object key = entries.getKey();
            Object value = entries.getValue();
            if (!(key instanceof String) || !(value instanceof String)) continue;
            String newKey = (String)key;
            if (!(newKey.startsWith("achievement.") || newKey.startsWith("itemGroup.") || newKey.startsWith("death."))) {
                newKey = ic2LangKey + newKey;
            }
            out.put(newKey, (String)value);
        }
    }

    protected static Map<String, String> getLanguageMapMap() {
        for (Method method : LanguageMap.class.getDeclaredMethods()) {
            if (method.getReturnType() != LanguageMap.class) continue;
            method.setAccessible(true);
            Field mapField = ReflectionUtil.getField(LanguageMap.class, Map.class);
            try {
                return (Map)mapField.get(method.invoke(null, new Object[0]));
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    protected static Map<String, String> getLocaleMap() {
        Field localeField = ReflectionUtil.getField(net.minecraft.client.resources.I18n.class, Locale.class);
        Field mapField = ReflectionUtil.getField(Locale.class, Map.class);
        try {
            return (Map)mapField.get(localeField.get(null));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String translate(String key) {
        return I18n.func_74838_a((String)key);
    }

    public static String translate(String key, Object ... args) {
        return I18n.func_74837_a((String)key, (Object[])args);
    }
}

