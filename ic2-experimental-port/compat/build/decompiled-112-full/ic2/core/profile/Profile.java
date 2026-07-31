/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.profile;

import ic2.core.profile.ProfileManager;
import ic2.core.profile.RecipeChange;
import ic2.core.profile.TextureStyle;
import ic2.core.profile.Version;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Profile {
    public final String name;
    public final Set<TextureStyle> textures;
    public final Version style;
    public final Map<String, List<RecipeChange>> recipeConfigs;
    public final Map<String, List<Object>> recipeRemovals;

    public Profile(String name, Set<TextureStyle> textures, Version style, RecipeChange ... changes) {
        this.name = name;
        this.textures = textures;
        this.style = style;
        if (changes.length == 0) {
            this.recipeConfigs = Collections.emptyMap();
            this.recipeRemovals = Collections.emptyMap();
        } else {
            HashMap<String, List> recipeConfigs = new HashMap<String, List>();
            HashMap<String, List> recipeRemovals = new HashMap<String, List>();
            for (RecipeChange change : changes) {
                if (change.type != RecipeChange.ChangeType.REMOVAL) {
                    recipeConfigs.computeIfAbsent(change.name, k -> new ArrayList()).add(change);
                    continue;
                }
                recipeRemovals.computeIfAbsent(change.name, k -> new ArrayList()).add(change);
            }
            this.recipeConfigs = !recipeConfigs.isEmpty() ? recipeConfigs : Collections.emptyMap();
            this.recipeRemovals = !recipeRemovals.isEmpty() ? recipeRemovals : Collections.emptyMap();
        }
    }

    public List<RecipeChange> processRecipeConfigs(String name) {
        List<RecipeChange> configs = this.recipeConfigs.get(name);
        if (configs == null) {
            return Collections.emptyList();
        }
        ArrayList<RecipeChange> ret = new ArrayList<RecipeChange>();
        block4: for (RecipeChange change : configs) {
            switch (change.type) {
                case EXTENSION: {
                    ret.addAll(ProfileManager.getOrError(((RecipeChange.RecipeExtension)change).profile).processRecipeConfigs(name));
                    continue block4;
                }
                case ADDITION: 
                case REPLACEMENT: {
                    ret.add(change);
                    continue block4;
                }
            }
            throw new IllegalStateException("Unexpected recipe change " + change + " for " + name);
        }
        return ret;
    }

    public String toString() {
        return "Profile: " + this.name;
    }
}

