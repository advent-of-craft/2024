class Building:
    @staticmethod
    def which_floor(instructions: str) -> int:
        val_list = []
        result = 0

        for i in range(len(instructions)):
            c = instructions[i]

            if "🧝" in instructions:
                j = 3 if c == ')' else -2
                val_list.append((c, j))
            elif "🧝" not in instructions:
                val_list.append((c, 1 if c == '(' else -1))
            else:
                val_list.append((c, 42 if c == '(' else -2))

        for kp in val_list:
            result += kp[1]

        return result
