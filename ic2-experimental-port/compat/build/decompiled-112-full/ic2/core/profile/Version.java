/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.profile;

import ic2.core.IC2;
import ic2.core.profile.Both;
import ic2.core.profile.NotClassic;
import ic2.core.profile.NotExperimental;
import java.lang.reflect.AnnotatedElement;

public enum Version {
    NEW,
    BOTH,
    OLD;


    public boolean isExperimental() {
        return this == NEW;
    }

    public boolean isClassic() {
        return this == OLD;
    }

    public static boolean shouldEnable(AnnotatedElement e) {
        return Version.shouldEnable(e, true);
    }

    public static boolean shouldEnable(AnnotatedElement e, boolean defaultState) {
        if (e.isAnnotationPresent(NotExperimental.class)) {
            return !IC2.version.isExperimental();
        }
        if (e.isAnnotationPresent(NotClassic.class)) {
            return !IC2.version.isClassic();
        }
        return e.isAnnotationPresent(Both.class) || defaultState;
    }
}

