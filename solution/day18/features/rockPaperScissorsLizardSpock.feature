Feature: Rock, Paper, Scissors, Lizard, Spock Game

  Scenario Outline: Rock, Paper, Scissors, Lizard, Spock Winners
    Given Player 1 chooses <player1>
    And Player 2 chooses <player2>
    When they play
    Then the result should be <result> because <reason>

    Examples:
      | player1 | player2 | result   | reason                      |
      | 🪨      | ✂️      | Player 1 | rock crushes scissors       |
      | 📄      | 🪨      | Player 1 | paper covers rock           |
      | 📄      | ✂️      | Player 2 | scissors cuts paper         |
      | ✂️      | 🪨      | Player 2 | rock crushes scissors       |
      | 🪨      | 🪨      | Draw     | same choice                 |
      | ✂️      | ✂️      | Draw     | same choice                 |
      | 📄      | 📄      | Draw     | same choice                 |
      | 🖖      | 🪨      | Player 1 | spock vaporizes rock        |
      | ✂️      | 🖖      | Player 2 | spock smashes scissors      |
      | 📄      | 🖖      | Player 1 | paper disproves spock       |
      | 🖖      | 🦎      | Player 2 | lizard poisons spock        |
      | ✂️      | 🦎      | Player 1 | scissors decapitates lizard |
      | 🦎      | 🪨      | Player 2 | rock crushes lizard         |
      | 🦎      | 📄      | Player 1 | lizard eats paper           |
      | 🦎      | 🦎      | Draw     | same choice                 |
      | 🖖      | 🖖      | Draw     | same choice                 |

