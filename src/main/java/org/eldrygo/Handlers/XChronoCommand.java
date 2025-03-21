package org.eldrygo.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.eldrygo.Managers.ChronometerManager;
import org.eldrygo.Managers.SplitManager;
import org.eldrygo.Managers.TimerManager;
import org.eldrygo.Models.Chronometer;
import org.eldrygo.Models.Split;
import org.eldrygo.Models.Timer;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XChrono;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class XChronoCommand implements CommandExecutor {

    private final ChronometerManager chronometerManager;
    private final TimerManager timerManager;
    private final SplitManager splitManager;
    private final ChatUtils chatUtils;
    private final Plugin plugin;

    public XChronoCommand(XChrono xChrono, ChronometerManager chronometerManager, TimerManager timerManager, SplitManager splitManager, Plugin plugin) {
        this.chronometerManager = chronometerManager;
        this.timerManager = timerManager;
        this.splitManager = splitManager;
        this.chatUtils = xChrono.getChatUtils(); // Asegúrate de que ChatUtils esté en XChrono o pásalo como parámetro
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("error.unknown_command", (Player) sender));
            return false;
        }

        String subCommand = args[0];

        switch (subCommand.toLowerCase()) {
            case "chrono":
                if (!(sender.hasPermission("xchrono.chrono"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender));
                } else {
                    return handleChronoCommand(sender, command, Arrays.copyOfRange(args, 1, args.length));
                }
            case "split":
                if (!(sender.hasPermission("xchrono.split"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender));
                } else {
                    return handleSplitCommand(sender, command, Arrays.copyOfRange(args, 1, args.length));
                }
            case "timer":
                if (!(sender.hasPermission("xchrono.timer"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender));
                } else {
                    return handleTimerCommand(sender, command, Arrays.copyOfRange(args, 1, args.length));
                }
            default:
                sender.sendMessage(chatUtils.getMessage("error.unknown_command", (Player) sender));
                return false;
        }
    }

    // Subcomandos de /xchrono chrono
    private boolean handleChronoCommand(CommandSender sender, @NotNull Command command, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("command.chrono.not_subcommand", (Player) sender));
            return false;
        }

        String subCommand = args[0];
        String type = args.length > 1 ? args[1] : "global"; // Determinar si es global o player

        switch (subCommand.toLowerCase()) {
            case "create":
                if (!(sender.hasPermission("xchrono.chrono.create"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleCreateChrono(sender, args, type);
                }
            case "delete":
                if (!(sender.hasPermission("xchrono.chrono.delete"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                   return handleDeleteChrono(sender, args, type);
                }
            case "set":
                if (!(sender.hasPermission("xchrono.chrono.set"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                 return handleSetChrono(sender, args, type);
                }
            case "add":
                if (!(sender.hasPermission("xchrono.chrono.add"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleAddChrono(sender, args, type);
                }
            case "reduce":
                if (!(sender.hasPermission("xchrono.chrono.reduce"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleReduceChrono(sender, args, type);
                }
            case "play":
                if (!(sender.hasPermission("xchrono.chrono.play"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handlePlayChrono(sender, args, type);
                }
            case "pause":
                if (!(sender.hasPermission("xchrono.chrono.pause"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handlePauseChrono(sender, args, type);
                }
            case "reset":
                if (!(sender.hasPermission("xchrono.chrono.reset"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleResetChrono(sender, args, type);
                }
            case "list":
                if (!(sender.hasPermission("xchrono.chrono.list"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleListChrono(sender);
                }
            default:
                sender.sendMessage(chatUtils.getMessage("command.chrono.usage", (Player) sender));
                return false;
        }
    }
    // Subcomandos de /xchrono split
    private boolean handleSplitCommand(CommandSender sender, @NotNull Command command, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("command.split.not_subcommand", (Player) sender));
            return false;
        }

        String subCommand = args[0];
        String type = args.length > 1 ? args[1] : "global"; // Determinar si es global o player

        switch (subCommand.toLowerCase()) {
            case "create":
                if (!(sender.hasPermission("xchrono.split.create"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleCreateSplit(sender, args, type);
                }
            case "delete":
                if (!(sender.hasPermission("xchrono.split.delete"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleDeleteSplit(sender, args, type);
                }
            case "setdisplay":
                if (!(sender.hasPermission("xchrono.split.setdisplay"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleSetDisplaySplit(sender, args, type);
                }
            case "set":
                if (!(sender.hasPermission("xchrono.split.set"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleSetSplit(sender, args, type);
                }
            case "unset":
                if (!(sender.hasPermission("xchrono.split.unset"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleUnSetSplit(sender, args, type);
                }
            case "list":
                if (!(sender.hasPermission("xchrono.split.list"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleListSplit(sender);
                }
            default:
                sender.sendMessage(chatUtils.getMessage("command.split.usage", (Player) sender));
                return false;
        }
    }
    // Subcomandos de /xchrono timer
    private boolean handleTimerCommand(CommandSender sender, @NotNull Command command, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("command.timer.not_subcommand", (Player) sender));
            return false;
        }

        String subCommand = args[0];
        String type = args.length > 1 ? args[1] : "global"; // Determinar si es global o player

        switch (subCommand.toLowerCase()) {
            case "create":
                if (!(sender.hasPermission("xchrono.timer.create"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleCreateTimer(sender, args, type);
                }
            case "delete":
                if (!(sender.hasPermission("xchrono.timer.delete"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleDeleteTimer(sender, args, type);
                }
            case "set":
                if (!(sender.hasPermission("xchrono.timer.set"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleSetTimer(sender, args, type);
                }
            case "add":
                if (!(sender.hasPermission("xchrono.timer.add"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleAddTimer(sender, args, type);
                }
            case "reduce":
                if (!(sender.hasPermission("xchrono.timer.reduce"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleReduceTimer(sender, args, type);
                }
            case "play":
                if (!(sender.hasPermission("xchrono.timer.play"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handlePlayTimer(sender, args, type);
                }
            case "pause":
                if (!(sender.hasPermission("xchrono.timer.pause"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handlePauseTimer(sender, args, type);
                }
            case "reset":
                if (!(sender.hasPermission("xchrono.timer.reset"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleResetTimer(sender, args, type);
                }
            case "list":
                if (!(sender.hasPermission("xchrono.timer.list"))) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (Player) sender)
                            .replace("%command%", (CharSequence) command));
                } else {
                    return handleListTimer(sender);
                }
            default:
                sender.sendMessage(chatUtils.getMessage("command.timer.usage", (Player) sender));
                return false;
        }
    }

    // Cronómetro
    private boolean handleCreateChrono(CommandSender sender, String[] args, String type) {
        // Verificar si los argumentos son suficientes
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.create.not_args", (Player) sender));
            return false;
        }
        String chronoId = args[0]; // Primer argumento para el cronómetro
        chronometerManager.createChrono((Player) sender, chronoId, type); // No se pasa el Player
        return false;
    }

    private boolean handleDeleteChrono(CommandSender sender, String[] args, String type) {
        // Verificar si los argumentos son suficientes
        if (args.length < 1) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.delete.not_args", (Player) sender));
            return false;
        }
        String chronoId = args[0]; // Primer argumento para el cronómetro
        chronometerManager.deleteChrono((Player) sender, chronoId); // No se pasa el Player
        return false;
    }
    private boolean handleSetChrono(CommandSender sender, String[] args, String type) {
        if (args.length < 6) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.set.not_args", (Player) sender));
            return false;
        }
        // Extraer el ID del cronómetro y el tiempo
        String id = args[2]; // Se asume que el ID es el tercer argumento
        int hours = Integer.parseInt(args[3]); // Hora
        int minutes = Integer.parseInt(args[4]); // Minutos
        int seconds = Integer.parseInt(args[5]); // Segundos
        Player target = Bukkit.getPlayer(args[1]);
        // Llamada al método correspondiente en el ChronometerManager
        chronometerManager.setChronoTime((Player) sender, type, id, target, hours, minutes, seconds);
        return false;
    }

    private boolean handleAddChrono(CommandSender sender, String[] args, String type) {
        if (args.length < 6) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.add.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        int hours = Integer.parseInt(args[3]);
        int minutes = Integer.parseInt(args[4]);
        int seconds = Integer.parseInt(args[5]);
        Player target = Bukkit.getPlayer(args[1]);
        chronometerManager.addTimeToChrono((Player) sender, type, id, target, hours, minutes, seconds);
        return false;
    }

    private boolean handleReduceChrono(CommandSender sender, String[] args, String type) {
        if (args.length < 6) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.reduce.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        int hours = Integer.parseInt(args[3]);
        int minutes = Integer.parseInt(args[4]);
        int seconds = Integer.parseInt(args[5]);
        Player target = Bukkit.getPlayer(args[1]);
        chronometerManager.reduceTimeFromChrono((Player) sender, type, id, target, hours, minutes, seconds);
        return false;
    }

    private boolean handlePlayChrono(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.play.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        Player target = Bukkit.getPlayer(args[1]);
        chronometerManager.startChrono((Player) sender, type, id, target);
        return false;
    }

    private boolean handlePauseChrono(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.pause.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        Player target = Bukkit.getPlayer(args[1]);
        chronometerManager.pauseChrono((Player) sender, type, id, target);
        return false;
    }

    private boolean handleResetChrono(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.reset.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        chronometerManager.resetChrono((Player) sender, args);
        return false;
    }
    private boolean handleListChrono(CommandSender sender) {
        Map<String, Chronometer> activeChronos = chronometerManager.getActiveChronos(); // Método hipotético
        if (activeChronos.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.chrono.list.empty", (Player) sender));
            return false;
        }
        StringBuilder sb = new StringBuilder(chatUtils.getMessage("commands.chrono.list.header", (Player) sender));
        for (String id : activeChronos.keySet()) {
            sb.append(id).append(", ");
        }
        sender.sendMessage(sb.toString());
        return true;
    }

    // Temporizador
    private boolean handleCreateTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.create.not_args", (Player) sender));
            return false;
        }
        String timerId = args[0]; // Primer argumento para el temporizador
        long time = Long.parseLong(args[1]); // Convertir el segundo argumento a long para el tiempo
        timerManager.createTimer((Player) sender, timerId, type, time); // No pasamos el Player, sólo el tiempo
        return false;
    }

    private boolean handleDeleteTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 1) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.delete.not_args", (Player) sender));
            return false;
        }
        String timerId = args[0]; // Primer argumento para el temporizador
        timerManager.deleteTimer((Player) sender, timerId); // No se pasa el Player
        return false;
    }
    private boolean handleSetTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 6) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.set.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        int hours = Integer.parseInt(args[3]);
        int minutes = Integer.parseInt(args[4]);
        int seconds = Integer.parseInt(args[5]);
        timerManager.addTimeToTimer((Player) sender, type, id, (Player) sender, hours, minutes, seconds);
        return false;
    }

    private boolean handleAddTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 6) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.add.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        int hours = Integer.parseInt(args[3]);
        int minutes = Integer.parseInt(args[4]);
        int seconds = Integer.parseInt(args[5]);
        timerManager.addTimeToTimer((Player) sender, type, id, (Player) sender, hours, minutes, seconds);
        return false;
    }

    private boolean handleReduceTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 6) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.reduce.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        int hours = Integer.parseInt(args[3]);
        int minutes = Integer.parseInt(args[4]);
        int seconds = Integer.parseInt(args[5]);
        Player target = Bukkit.getPlayer(args[1]);
        timerManager.reduceTimeFromTimer((Player) sender, type, id, target, hours, minutes, seconds);
        return false;
    }

    private boolean handlePlayTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.play.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        Player target = Bukkit.getPlayer(args[1]);
        timerManager.startTimer((Player) sender, type, id, target);
        return false;
    }

    private boolean handlePauseTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.pause.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        Player target = Bukkit.getPlayer(args[1]);
        timerManager.pauseTimer((Player) sender, type, id, target);
        return false;
    }

    private boolean handleResetTimer(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.reset.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        timerManager.resetTimer((Player) sender, args);
        return false;
    }
    private boolean handleListTimer(CommandSender sender) {
        Map<String, Timer> activeTimers = timerManager.getActiveTimers(); // Método hipotético
        if (activeTimers.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.timer.list.empty", (Player) sender));
            return false;
        }
        StringBuilder sb = new StringBuilder(chatUtils.getMessage("commands.timer.list.header", (Player) sender));
        for (String id : activeTimers.keySet()) {
            sb.append(id).append(", ");
        }
        sender.sendMessage(sb.toString());
        return true;
    }

    // Split
    private boolean handleCreateSplit(CommandSender sender, String[] args, String type) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("commands.split.create.not_args", (Player) sender));
            return false;
        }
        String splitId = args[0]; // Primer argumento para el split
        String splitValue = args[1]; // Segundo argumento para el split
        splitManager.createSplit((Player) sender, splitId, type, splitValue, plugin); // Pasamos Player de forma correcta
        return false;
    }

    private boolean handleDeleteSplit(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.split.delete.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        splitManager.deleteSplit((Player) sender, id);
        return false;
    }

    private boolean handleSetDisplaySplit(CommandSender sender, String[] args, String type) {
        if (args.length < 4) {
            sender.sendMessage(chatUtils.getMessage("commands.split.setdisplay.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        String displayName = args[3];
        splitManager.setDisplayName((Player) sender, type, id, displayName);
        return false;
    }

    private boolean handleSetSplit(CommandSender sender, String[] args, String type) {
        if (args.length < 5) {
            sender.sendMessage(chatUtils.getMessage("commands.split.set.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        String timerType = args[3]; // Ejemplo: 'global' o 'player'
        String timerId = args[4];
        Player target = Bukkit.getPlayer(args[1]);
        splitManager.setSplitTimer((Player) sender, type, id, target, timerType, timerId);
        return false;
    }

    private boolean handleUnSetSplit(CommandSender sender, String[] args, String type) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("commands.split.unset.not_args", (Player) sender));
            return false;
        }
        String id = args[2];
        Player target = Bukkit.getPlayer(args[1]);
        splitManager.unsetSplitTimer((Player) sender, type, id, target);
        return false;
    }

    private boolean handleListSplit(CommandSender sender) {
        Map<String, Split> activeSplits = splitManager.getActiveSplits(); // Método hipotético
        if (activeSplits.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.split.list.empty", (Player) sender));
            return false;
        }
        StringBuilder sb = new StringBuilder(chatUtils.getMessage("commands.split.list.header", (Player) sender));
        for (String id : activeSplits.keySet()) {
            sb.append(id).append(", ");
        }
        sender.sendMessage(sb.toString());
        return true;
    }

}
