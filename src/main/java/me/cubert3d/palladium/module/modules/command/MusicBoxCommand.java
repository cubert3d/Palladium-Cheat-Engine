package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.event.mixin.accessors.MusicTrackerAccessor;
import me.cubert3d.palladium.input.CommandError;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        authors = "cubert3d",
        date = "7/16/2021",
        type = ClassType.MODULE
)

public final class MusicBoxCommand extends CommandModule {

    public MusicBoxCommand() {
        super("MusicBox", "Allows control over the background music.");
    }

    @Override
    protected final void execute(String @NotNull [] args) {
        if (args.length == 0) {
            printToChatHud("§bNow playing: " + getSongName());
        }
        else if (args.length == 1) {
            switch (args[0]) {
                case "play":
                    MusicSound musicSound = MinecraftClient.getInstance().getMusicType();
                    MinecraftClient.getInstance().getMusicTracker().stop();
                    MinecraftClient.getInstance().getMusicTracker().play(musicSound);
                    printToChatHud("§bPlaying " + getSongName());
                    break;

                case "stop":
                    MinecraftClient.getInstance().getMusicTracker().stop();
                    printToChatHud("§bStopping song!");
                    break;

                default:
                    CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                    break;
            }
        }
        else {
            CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
        }
    }

    private String getSongName() {
        SoundInstance currentSoundInstance = ((MusicTrackerAccessor) MinecraftClient.getInstance().getMusicTracker()).getCurrent();
        if (currentSoundInstance != null) {

            Identifier soundID = currentSoundInstance.getSound().getIdentifier();
            String[] pathTerms = soundID.getPath().split("/");

            if (pathTerms.length > 0) {
                String songName = pathTerms[pathTerms.length - 1];
                // Add the composer and the proper name of the track, if possible.
                switch (songName) {
                    // Game music
                    case "calm1": songName = "C418 - " + songName.concat(" (\"Minecraft\")"); break;
                    case "calm2": songName = "C418 - " + songName.concat(" (\"Clark\")"); break;
                    case "calm3": songName = "C418 - " + songName.concat(" (\"Sweden\")"); break;
                    case "hal1": songName = "C418 - " + songName.concat(" (\"Subwoofer Lullaby\")"); break;
                    case "hal2": songName = "C418 - " + songName.concat(" (\"Living Mice\")"); break;
                    case "hal3": songName = "C418 - " + songName.concat(" (\"Haggstrom\")"); break;
                    case "hal4": songName = "C418 - " + songName.concat(" (\"Danny\")"); break;
                    case "nuance1": songName = "C418 - " + songName.concat(" (\"Key\")"); break;
                    case "nuance2": songName = "C418 - " + songName.concat(" (\"Oxygène\")"); break;
                    case "piano1": songName = "C418 - " + songName.concat(" (\"Dry Hands\")"); break;
                    case "piano2": songName = "C418 - " + songName.concat(" (\"Wet Hands\")"); break;
                    case "piano3": songName = "C418 - " + songName.concat(" (\"Mice on Venus\")"); break;
                    // Creative mode music
                    case "creative1": songName = "§eC418 - " + songName.concat(" (\"Biome Fest\")"); break;
                    case "creative2": songName = "§eC418 - " + songName.concat(" (\"Blind Spots\")"); break;
                    case "creative3": songName = "§eC418 - " + songName.concat(" (\"Haunt Muskie\")"); break;
                    case "creative4": songName = "§eC418 - " + songName.concat(" (\"Aria Math\")"); break;
                    case "creative5": songName = "§eC418 - " + songName.concat(" (\"Dreiton\")"); break;
                    case "creative6": songName = "§eC418 - " +    songName.concat(" (\"Taswell\")"); break;
                    // Underwater music
                    case "axolotl": songName = "§3C418 - " + songName.concat(" (\"Axolotl\")"); break;
                    case "dragon_fish": songName = "§3C418 - " + songName.concat(" (\"Dragon Fish\")"); break;
                    case "shuniji": songName = "§3C418 - " + songName.concat(" (\"Shuniji\")"); break;
                    // Nether music
                    case "nether1": songName = "§cC418 - " + songName.concat(" (\"Concrete Halls\")"); break;
                    case "nether2": songName = "§cC418 - " + songName.concat(" (\"Dead Voxel\")"); break;
                    case "nether3": songName = "§cC418 - " + songName.concat(" (\"Warmth\")"); break;
                    case "nether4": songName = "§cC418 - " + songName.concat(" (\"Ballad of the Cats\")"); break;
                    case "rubedo": songName = "§cLena Raine - " + songName.concat(" (\"Rubedo\")"); break;
                    case "chrysopoeia": songName = "§cLena Raine - " + songName.concat(" (\"Chrysopoeia\")"); break;
                    case "so_below": songName = "§cLena Raine - " + songName.concat(" (\"So Below\")"); break;
                    // End music
                    case "boss": songName = "§dLena Raine - " + songName.concat(" (\"Boss\")"); break;
                    case "end": songName = "§dC418 - " +  songName.concat(" (\"The End\")"); break;
                }
                return songName;
            }
        }
        return "None";
    }
}
