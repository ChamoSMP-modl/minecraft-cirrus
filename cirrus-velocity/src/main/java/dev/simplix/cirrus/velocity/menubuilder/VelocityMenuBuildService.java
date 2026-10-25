package dev.simplix.cirrus.velocity.menubuilder;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.cirrus.common.service.AbstractPacketMenuBuildService;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class VelocityMenuBuildService extends AbstractPacketMenuBuildService {

    @Override
    protected User getUser(CirrusPlayerWrapper playerWrapper) {
        return PacketEvents.getAPI().getPlayerManager().getUser(playerWrapper.handle());
    }

    @Override
    protected UUID getPlayerUuid(CirrusPlayerWrapper playerWrapper) {
        return ((Player) playerWrapper.handle()).getUniqueId();
    }
}