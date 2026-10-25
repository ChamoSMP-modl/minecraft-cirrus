package dev.simplix.cirrus.spigot;

import com.github.retrooper.packetevents.PacketEvents;
import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.menu.MenuUpdateTask;
import dev.simplix.cirrus.service.ItemService;
import dev.simplix.cirrus.service.MenuBuildService;
import dev.simplix.cirrus.service.RunSyncService;
import dev.simplix.cirrus.spigot.listener.SpigotInventoryPacketListener;
import dev.simplix.cirrus.spigot.listener.SpigotQuitListener;
import dev.simplix.cirrus.spigot.menubuilder.SpigotMenuBuildService;
import dev.simplix.cirrus.spigot.services.SpigotItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class CirrusSpigot {

    private final JavaPlugin plugin;

    public void init() {
        Cirrus.init();
        Cirrus.canDisplayAsync(true);
        Cirrus.isSpigot(true);

        SpigotMenuBuildService menuBuildService = new SpigotMenuBuildService();

        Cirrus.registerService(ItemService.class, new SpigotItemService());
        Cirrus.registerService(MenuBuildService.class, menuBuildService);

        Cirrus.registerService(RunSyncService.class,
                runnable -> Bukkit.getGlobalRegionScheduler().run(plugin, _ -> runnable.run())
        );

        PacketEvents.getAPI().getEventManager().registerListener(
                new SpigotInventoryPacketListener(menuBuildService.getInventoryTracker(), menuBuildService)
        );

        Bukkit.getPluginManager().registerEvents(
                new SpigotQuitListener(menuBuildService.getInventoryTracker()),
                plugin
        );

        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, _ -> new MenuUpdateTask(), 50, 50, TimeUnit.MILLISECONDS);
    }
}
