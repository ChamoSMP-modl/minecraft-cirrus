package dev.simplix.cirrus.spigot.menubuilder;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import dev.simplix.cirrus.common.service.AbstractPacketMenuBuildService;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.Player;

import java.util.UUID;

@Slf4j
public class SpigotMenuBuildService extends AbstractPacketMenuBuildService {
    @Override
    protected User getUser(CirrusPlayerWrapper playerWrapper) {
        return PacketEvents.getAPI().getPlayerManager().getUser(playerWrapper.handle());
    }

    @Override
    protected UUID getPlayerUuid(CirrusPlayerWrapper playerWrapper) {
        return ((Player) playerWrapper.handle()).getUniqueId();
    }
}