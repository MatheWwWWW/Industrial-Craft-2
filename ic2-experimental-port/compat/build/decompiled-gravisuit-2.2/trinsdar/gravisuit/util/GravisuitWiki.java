/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.platform.events.impl.WikiEvent
 *  ic2.core.platform.events.impl.WikiEvent$AddonPageEvent
 *  ic2.core.platform.events.impl.WikiEvent$AddonSetupEvent
 *  ic2.core.platform.events.impl.WikiEvent$WikiSetupEvent
 *  ic2.core.wiki.base.IChapterBuilder
 *  ic2.core.wiki.components.builders.CategoryObj
 *  ic2.core.wiki.components.builders.CategoryObj$Link
 *  ic2.core.wiki.components.builders.CraftObj
 *  ic2.core.wiki.components.builders.DoublePageEndObj
 *  ic2.core.wiki.components.builders.HeaderObj
 *  ic2.core.wiki.components.builders.IWikiObj
 *  ic2.core.wiki.components.builders.LinkObj
 *  ic2.core.wiki.components.builders.PreviewObj
 *  ic2.core.wiki.components.builders.TextObj
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.registries.ForgeRegistries
 */
package trinsdar.gravisuit.util;

import ic2.core.platform.events.impl.WikiEvent;
import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.components.builders.CategoryObj;
import ic2.core.wiki.components.builders.CraftObj;
import ic2.core.wiki.components.builders.DoublePageEndObj;
import ic2.core.wiki.components.builders.HeaderObj;
import ic2.core.wiki.components.builders.IWikiObj;
import ic2.core.wiki.components.builders.LinkObj;
import ic2.core.wiki.components.builders.PreviewObj;
import ic2.core.wiki.components.builders.TextObj;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import trinsdar.gravisuit.util.Registry;

public class GravisuitWiki {
    @SubscribeEvent
    public static void onWikiEvent(WikiEvent event) {
        WikiEvent.WikiSetupEvent setupEvent;
        if (event instanceof WikiEvent.WikiSetupEvent) {
            setupEvent = (WikiEvent.WikiSetupEvent)event;
            setupEvent.builder.addChapterBuilder(GravisuitWiki::createWiki);
        }
        if (event instanceof WikiEvent.AddonSetupEvent) {
            setupEvent = (WikiEvent.AddonSetupEvent)event;
            setupEvent.enableAddons();
        }
        if (event instanceof WikiEvent.AddonPageEvent) {
            setupEvent = (WikiEvent.AddonPageEvent)event;
            setupEvent.registerChapters(new IWikiObj[]{new CategoryObj("wiki.ic2.category.general", new CategoryObj.Link[]{new CategoryObj.Link((ItemLike)Registry.GRAVITOOL, "gravisuit:items").with("wiki.gravisuit.category.main")})});
        }
    }

    public static void createWiki(IChapterBuilder builder) {
        builder.startBuildChapter("gravisuit", "items");
        builder.addSimplePage(new IWikiObj[]{new HeaderObj("wiki.gravisuit.header.main"), new TextObj("wiki.gravisuit.preview.gravisuit_main.desc")});
        builder.addSubPages(new ItemLike[]{Registry.GRAVITOOL});
        builder.addSimplePage(new IWikiObj[]{new HeaderObj(Registry.RELOCATOR.m_5524_()), new CraftObj(new ItemLike[]{Registry.RELOCATOR}), new PreviewObj(new ItemLike[]{Registry.RELOCATOR}), new TextObj("wiki.gravisuit.preview.relocator.desc"), new DoublePageEndObj()});
        builder.addSubPages(new ItemLike[]{Registry.VAJRA});
        builder.addSimplePage(GravisuitWiki.createItemList(new ItemLike[]{Registry.ADVANCED_LAPPACK, Registry.ULTIMATE_LAPPACK}));
        builder.addSimplePage(GravisuitWiki.createItemList(new ItemLike[]{Registry.ADVANCED_ELECTRIC_JETPACK, Registry.ADVANCED_NUCLEAR_JETPACK}));
        builder.addSimplePage(GravisuitWiki.createItemList(new ItemLike[]{Registry.GRAVITATION_JETPACK, Registry.NUCLEAR_GRAVITATION_JETPACK}));
        builder.finishBuildChapter(true);
    }

    private static List<IWikiObj> createItemList(ItemLike ... items) {
        ArrayList<IWikiObj> objs = new ArrayList<IWikiObj>();
        objs.add((IWikiObj)new PreviewObj(items));
        objs.add((IWikiObj)new HeaderObj(items[0].m_5456_().m_5524_()));
        objs.add((IWikiObj)new CraftObj(items));
        ResourceLocation location = ForgeRegistries.ITEMS.getKey((Object)items[0].m_5456_());
        objs.add((IWikiObj)new TextObj("wiki." + location.m_135827_() + ".preview." + location.m_135815_() + ".desc").setCutOff());
        objs.add((IWikiObj)new LinkObj(location.m_135827_(), "subpage." + location.m_135815_()));
        return objs;
    }
}

