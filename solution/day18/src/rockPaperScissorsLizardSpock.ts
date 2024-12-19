export type Choice = "🪨" | "📄" | "✂️" | "🖖" | "🦎";
export type Winner = "Player 1" | "Player 2" | "Draw"
export type Result = {
    winner: Winner,
    reason: string
};

const whatBeatsWhat = new Map<string, string>([
    [keyFor("🪨", "✂️"), "rock crushes scissors"],
    [keyFor("📄", "🪨"), "paper covers rock"],
    [keyFor("✂️", "📄"), "scissors cuts paper"],
    [keyFor("🖖", "🪨"), "spock vaporizes rock"],
    [keyFor("🖖", "✂️"), "spock smashes scissors"],
    [keyFor("📄", "🖖"), "paper disproves spock"],
    [keyFor("🦎", "🖖"), "lizard poisons spock"],
    [keyFor("✂️", "🦎"), "scissors decapitates lizard"],
    [keyFor("🪨", "🦎"), "rock crushes lizard"],
    [keyFor("🦎", "📄"), "lizard eats paper"]
]);

function keyFor(choice1: Choice, choice2: Choice): string {
    return `${choice1}_${choice2}`;
}

export class RockPaperScissorsLizardSpock {
    static play(player1: Choice, player2: Choice): Result {
        if (player1 === player2) return {winner: "Draw", reason: "same choice"};
        else if (whatBeatsWhat.has(keyFor(player1, player2)))
            return {winner: "Player 1", reason: whatBeatsWhat.get(keyFor(player1, player2))};
        else return {winner: "Player 2", reason: whatBeatsWhat.get(keyFor(player2, player1))};
    }
}