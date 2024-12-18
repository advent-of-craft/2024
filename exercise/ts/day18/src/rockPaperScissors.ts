export type Choice = "🪨" | "📄" | "✂️";
export type Winner = "Player 1" | "Player 2" | "Draw"
export type Result = {
    winner: Winner,
    reason: string
};

export class RockPaperScissors {
    static play(player1: Choice, player2: Choice): Result {
        if (player1 === player2) return {winner: "Draw", reason: "same choice"};
        else if (player1 === "🪨" && player2 === "✂️")
            return {winner: "Player 1", reason: "rock crushes scissors"};
        else if (player1 === "📄" && player2 === "🪨")
            return {winner: "Player 1", reason: "paper covers rock"};
        else if (player1 === "✂️" && player2 === "📄")
            return {winner: "Player 1", reason: "scissors cuts paper"};
        else if (player2 === "🪨" && player1 === "✂️")
            return {winner: "Player 2", reason: "rock crushes scissors"};
        else if (player2 === "📄" && player1 === "🪨")
            return {winner: "Player 2", reason: "paper covers rock"};
        else return {winner: "Player 2", reason: "scissors cuts paper"};
    }
}