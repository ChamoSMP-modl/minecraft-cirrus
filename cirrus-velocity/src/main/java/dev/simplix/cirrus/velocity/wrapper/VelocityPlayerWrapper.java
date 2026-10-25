package dev.simplix.cirrus.velocity.wrapper;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.sound.SoundCategory;
import com.github.retrooper.packetevents.protocol.sound.Sounds;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSoundEffect;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.cirrus.model.CirrusSoundCategory;
import dev.simplix.cirrus.model.SimpleSound;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.UUID;

@RequiredArgsConstructor
public class VelocityPlayerWrapper implements CirrusPlayerWrapper {

    private final Player player;

    @Override
    public UUID uuid() {
        return player.getUniqueId();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T handle() {
        return (T) player;
    }

    @Override
    public int protocolVersion() {
        try {
            User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
            if (user != null) {
                return user.getClientVersion().getProtocolVersion();
            }
        } catch (Throwable _) {
        }
        return ClientVersion.getLatest().getProtocolVersion();
    }

    @Override
    public void play(SimpleSound sound) {
        User user = PacketEvents.getAPI().getPlayerManager().getUser(player);

        try {
            if (user == null) throw new Exception("");

            var peSound = Sounds.getByName(sound.sound().identifier());
            if (peSound == null) {
                return;
            }

            SoundCategory category = mapSoundCategory(sound.soundCategory());

            WrapperPlayServerSoundEffect packet = new WrapperPlayServerSoundEffect(
                    peSound,
                    category,
                    new Vector3d(0, 64, 0),
                    sound.volume(),
                    sound.pitch()
            );

            user.sendPacket(packet);
        } catch (Exception _) {
            // Silently fail if sound sending fails
        }
    }

    private SoundCategory mapSoundCategory(CirrusSoundCategory category) {
        if (category == null) {
            return SoundCategory.MASTER;
        }
        return switch (category) {
            case MASTER -> SoundCategory.MASTER;
            case MUSIC -> SoundCategory.MUSIC;
            case RECORDS -> SoundCategory.RECORD;
            case WEATHER -> SoundCategory.WEATHER;
            case BLOCKS -> SoundCategory.BLOCK;
            case HOSTILE -> SoundCategory.HOSTILE;
            case NEUTRAL -> SoundCategory.NEUTRAL;
            case PLAYERS -> SoundCategory.PLAYER;
            case AMBIENT -> SoundCategory.AMBIENT;
            case VOICE -> SoundCategory.VOICE;
        };
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
}
