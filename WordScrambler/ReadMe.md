# WordScrambler Plugin

A PowerNukkitX plugin that broadcasts scrambled words at regular intervals. Players race to unscramble the word and win in-game money via [LlamaEconomy](https://github.com/LlamaDevelopment/LlamaEconomy).

## Features

- Reward system using LlamaEconomy
- Fully configurable via `config.yml`
- Anti-spam: only one scramble active at a time
- Custom messages for no-answer rounds

## Screenshots

![Scramble Example](https://example.com/scramble1.png)
*Broadcast with ASCII, divider, and scramble*

![Winning Example](https://example.com/scramble2.png)
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
