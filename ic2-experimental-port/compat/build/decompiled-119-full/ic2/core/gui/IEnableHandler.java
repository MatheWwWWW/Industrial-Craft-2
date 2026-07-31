/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import java.util.Arrays;

public interface IEnableHandler {
    public boolean isEnabled();

    public static final class EnableHandlers {
        public static IEnableHandler and(IEnableHandler ... iEnableHandlerArray) {
            return () -> Arrays.stream(iEnableHandlerArray).allMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler nand(IEnableHandler ... iEnableHandlerArray) {
            return () -> !Arrays.stream(iEnableHandlerArray).allMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler or(IEnableHandler ... iEnableHandlerArray) {
            return () -> Arrays.stream(iEnableHandlerArray).anyMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler nor(IEnableHandler ... iEnableHandlerArray) {
            return () -> Arrays.stream(iEnableHandlerArray).noneMatch(IEnableHandler::isEnabled);
        }

        public static IEnableHandler not(IEnableHandler iEnableHandler) {
            return () -> !iEnableHandler.isEnabled();
        }
    }
}

