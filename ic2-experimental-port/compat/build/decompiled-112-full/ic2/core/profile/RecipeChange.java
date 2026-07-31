/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.profile;

import ic2.core.profile.ProfileTarget;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public abstract class RecipeChange {
    public final String name;
    public final ChangeType type;

    public RecipeChange(String name, ChangeType type) {
        this.name = name;
        this.type = type;
    }

    public abstract InputStream getStream();

    static InputStream asStream(ProfileTarget target) {
        try {
            return target.asStream();
        }
        catch (IOException e) {
            throw new RuntimeException("Error getting replacement stream for " + target, e);
        }
    }

    public static class RecipeAddition
    extends RecipeChange {
        protected final ProfileTarget[] targets;

        public RecipeAddition(String name, ProfileTarget ... targets) {
            super(name, ChangeType.ADDITION);
            this.targets = targets;
        }

        @Override
        public InputStream getStream() {
            switch (this.targets.length) {
                case 0: {
                    return null;
                }
                case 1: {
                    return RecipeAddition.asStream(this.targets[0]);
                }
            }
            return new SequenceInputStream(Collections.enumeration(Arrays.stream(this.targets).map(RecipeChange::asStream).collect(Collectors.toList())));
        }
    }

    public static class RecipeReplacement
    extends RecipeChange {
        protected final ProfileTarget[] targets;

        public RecipeReplacement(String name, ProfileTarget ... targets) {
            super(name, ChangeType.REPLACEMENT);
            this.targets = targets;
        }

        @Override
        public InputStream getStream() {
            switch (this.targets.length) {
                case 0: {
                    return new ByteArrayInputStream(new byte[0]);
                }
                case 1: {
                    return RecipeReplacement.asStream(this.targets[0]);
                }
            }
            return new SequenceInputStream(Collections.enumeration(Arrays.stream(this.targets).map(RecipeChange::asStream).collect(Collectors.toList())));
        }
    }

    public static class RecipeExtension
    extends RecipeChange {
        public final String profile;

        public RecipeExtension(String name, String profile) {
            super(name, ChangeType.EXTENSION);
            this.profile = profile;
        }

        @Override
        public InputStream getStream() {
            throw new UnsupportedOperationException();
        }
    }

    public static enum ChangeType {
        EXTENSION,
        ADDITION,
        REMOVAL,
        REPLACEMENT;

    }
}

