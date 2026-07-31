/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import java.util.Arrays;

public interface IEnableHandler {
    public boolean isEnabled();

    public static final class EnableHandlers {
        public static IEnableHandler and(IEnableHandler ... handlers) {
            return () -> Arrays.stream(handlers).allMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler nand(IEnableHandler ... handlers) {
            return () -> !Arrays.stream(handlers).allMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler or(IEnableHandler ... handlers) {
            return () -> Arrays.stream(handlers).anyMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler nor(IEnableHandler ... handlers) {
            return () -> Arrays.stream(handlers).noneMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler not(IEnableHandler handler) {
            return () -> !handler.isEnabled();
        }
    }
}

