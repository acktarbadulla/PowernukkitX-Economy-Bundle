# WordScrambler Plugin

A PowerNukkitX plugin that broadcasts scrambled words at regular intervals. Players race to unscramble the word and win in-game money via [LlamaEconomy](https://github.com/LlamaDevelopment/LlamaEconomy).

## Features

- Reward system using LlamaEconomy
- Fully configurable via `config.yml`
- Anti-spam: only one scramble active at a time
- Custom messages for no-answer rounds

## Screenshots

![Scramble Example](https://github.com/acktarbadulla/PowernukkitX-Economy-Bundle/blob/main/WordScrambler/IMG_20250501_162902.jpg)
*Broadcast with divider, and scramble*

![Winning Example](https://github.com/acktarbadulla/PowernukkitX-Economy-Bundle/blob/main/WordScrambler/Screenshot_2025-05-01-16-04-59-24_70482d039613b80a94c774c51a1e8f86.jpg)
*Player answers correctly and wins money*

## Configuration

```yaml
scramble_interval: 60         # Seconds between each scramble
scramble_delay_solved: 5      # Delay before next scramble if solved
prize_min: 1000
prize_max: 10000
scramble_completion_message: "{player} answers \"{answer}\" and gets {money} Money"
no_answer_message: "No one answered the WordScramble! Moving on to the next one..."
words:
  - plugin
  - minecraft
  - economy
  - scramble
  - llama
```
