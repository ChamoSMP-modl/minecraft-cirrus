package dev.simplix.cirrus.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class CirrusChatElement {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private final Component component;
    private String cachedLegacyText;

    private CirrusChatElement(@NonNull Component component) {
        this.component = component;
    }

    public static CirrusChatElement of(@NonNull Component component) {
        return new CirrusChatElement(component);
    }

    public static CirrusChatElement ofMiniMessage(@Nullable String mmText) {
        if (mmText == null || mmText.isEmpty()) {
            return new CirrusChatElement(Component.empty());
        }

        Component component = MINI_MESSAGE.deserialize(mmText);
        CirrusChatElement element = new CirrusChatElement(component);
        element.cachedLegacyText = mmText;
        return element;
    }

    public static CirrusChatElement empty() {
        return new CirrusChatElement(Component.empty());
    }

    public Component asComponent() {
        return component;
    }

    public String asMiniMessageString() {
        if (cachedLegacyText == null) {
            cachedLegacyText = MINI_MESSAGE.serialize(component);
        }
        return cachedLegacyText;
    }

    public boolean isEmpty() {
        return component.equals(Component.empty()) ||
                (cachedLegacyText != null && cachedLegacyText.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CirrusChatElement that = (CirrusChatElement) o;
        return Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(component);
    }

    @Override
    public String toString() {
        return asMiniMessageString();
    }
}
