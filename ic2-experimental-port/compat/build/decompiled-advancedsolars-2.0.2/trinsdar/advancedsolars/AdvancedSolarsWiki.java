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
package trinsdar.advancedsolars;

import ic2.core.platform.events.impl.WikiEvent;
import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.components.builders.CategoryObj;
import ic2.core.wiki.components.builders.CraftObj;
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
import trinsdar.advancedsolars.util.Registry;

public class AdvancedSolarsWiki {
    @SubscribeEvent
    public static void onWikiEvent(WikiEvent event) {
        WikiEvent.WikiSetupEvent setupEvent;
        if (event instanceof WikiEvent.WikiSetupEvent) {
            setupEvent = (WikiEvent.WikiSetupEvent)event;
            setupEvent.builder.addChapterBuilder(AdvancedSolarsWiki::createWiki);
        }
        if (event instanceof WikiEvent.AddonSetupEvent) {
            setupEvent = (WikiEvent.AddonSetupEvent)event;
            setupEvent.enableAddons();
        }
        if (event instanceof WikiEvent.AddonPageEvent) {
            setupEvent = (WikiEvent.AddonPageEvent)event;
            setupEvent.registerChapters(new IWikiObj[]{new CategoryObj("wiki.ic2.category.general", new CategoryObj.Link[]{new CategoryObj.Link((ItemLike)Registry.ADVANCED_SOLAR_PANEL, "advancedsolars:items").with("wiki.advanced_solars.category")})});
        }
    }

    public static void createWiki(IChapterBuilder builder) {
        builder.startBuildChapter("advancedsolars", "items");
        builder.addSimplePage(new IWikiObj[]{new HeaderObj("wiki.advanced_solars.header.main"), new TextObj("wiki.advanced_solars.preview.main.desc")});
        builder.addSimplePage(AdvancedSolarsWiki.createItemList(new ItemLike[]{Registry.ADVANCED_SOLAR_HELMET, Registry.HYBRID_SOLAR_HELMET, Registry.ULTIMATE_HYBRID_SOLAR_HELMET}));
        builder.finishBuildChapter(true);
        builder.startBuildChapter("advancedsolars", "blocks");
        builder.addSimplePage(AdvancedSolarsWiki.createItemList(new ItemLike[]{Registry.ADVANCED_SOLAR_PANEL, Registry.HYBRID_SOLAR_PANEL, Registry.ULTIMATE_HYBRID_SOLAR_PANEL}));
        builder.addSubPages(new ItemLike[]{Registry.MOLECULAR_TRANSFORMER});
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

