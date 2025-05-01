package com.bedrockcore.wordscrambler;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.utils.Config;
import net.lldv.llamaeconomy.LlamaEconomy;

import java.io.File;
import java.util.*;

public class WordScramblerPlugin extends PluginBase implements Listener {

    private final Random random = new Random();
    private List<String> words;
    private String currentWord;
    private String scrambledWord;
    private Config config;

    private final String prefix =
         "§l§f§7[ §eWordScrambler Chat Games §7]\n" +
         "§f⤅ §7Unscramble words for prize money\n" +
         "§f⤅ §7Test your minecraft intelligence with our fun games\n" +
         "§l§f⤅ ---------------\n" +
         "§l§e\n";
         
    @Override
    public void onEnable() {
        saveResource("config.yml");
        this.config = new Config(new File(getDataFolder(), "config.yml"), Config.YAML);

        Object rawWords = config.get("words");
        if (rawWords instanceof List) {
            this.words = (List<String>) rawWords;
        } else {
            this.words = Arrays.asList("acktar", "bedrockcore");
        }

        getServer().getPluginManager().registerEvents(this, this);
        scheduleNextScramble(0);
        startRepeatingScramble();
    }

    private void scheduleNextScramble(int delaySeconds) {
        getServer().getScheduler().scheduleDelayedTask(this, () -> {
            generateScramble();
            getServer().broadcastMessage(prefix);
            getServer().broadcastMessage("§l§dUnscramble this: §f" + scrambledWord);
            getServer().broadcastMessage("§l§a\n");
            getServer().broadcastMessage("§l§f⤅ ---------------");
        }, delaySeconds * 20);
    }

    private void generateScramble() {
        this.currentWord = words.get(random.nextInt(words.size()));
        List<Character> chars = new ArrayList<>();
        for (char c : currentWord.toCharArray()) chars.add(c);
        Collections.shuffle(chars);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        this.scrambledWord = sb.toString();
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        if (currentWord == null) return;

        String message = event.getMessage().trim();
        Player player = event.getPlayer();

        if (message.equalsIgnoreCase(currentWord)) {
            int prizeMin = config.getInt("prize_min", 1000);
            int prizeMax = config.getInt("prize_max", 10000);
            int prize = prizeMin + random.nextInt(prizeMax - prizeMin + 1);

            LlamaEconomy.getAPI().addMoney(player, prize);

            String completionMsg = config.getString("scramble_completion_message",
                    "§e{player} §fanswers §a\"{answer}\" §fand gets a prize of §6{money} §fMoney")
                    .replace("{player}", player.getName())
                    .replace("{answer}", currentWord)
                    .replace("{money}", String.valueOf(prize));
            getServer().broadcastMessage(completionMsg);

            currentWord = null;
            scrambledWord = null;
            event.setCancelled(true);

            scheduleNextScramble(config.getInt("scramble_delay_solved", 5));
        }
    }

    private void startRepeatingScramble() {
        int interval = config.getInt("scramble_interval", 60);
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, () -> {
            if (currentWord == null) {
                generateScramble();
                getServer().broadcastMessage(prefix);
                getServer().broadcastMessage("§l§dUnscramble this: §f" + scrambledWord);
                getServer().broadcastMessage("§l§a\n");
                getServer().broadcastMessage("§l§f⤅ ---------------");
            } else {
                getServer().broadcastMessage(config.getString("no_answer_message",
                        "§c§l⤅ No one answered the WordScramble! Moving on to the next one..."));
                currentWord = null;
                scrambledWord = null;
            }
        }, 0, interval * 20);
    }
}
