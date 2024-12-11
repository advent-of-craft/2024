<?php

namespace Christmas;

class Preparation {
    public static function prepareGifts(int $numberOfGifts): string {
        return match (true) {
            $numberOfGifts <= 0 => 'No gifts to prepare.',
            $numberOfGifts < 50 => 'Elves will prepare the gifts.',
            default => 'Santa will prepare the gifts.'
        };
    }

    public static function categorizeGift(int $age): string {
        return match (true) {
            $age <= 2 => 'Baby',
            $age <= 5 => 'Toddler',
            $age <= 12 => 'Child',
            default => 'Teen'
        };
    }

    public static function ensureToyBalance(ToyType $toyType, int $toysCount, int $totalToys): bool {
        $typePercentage = $toysCount / $totalToys;

        return match ($toyType) {
            ToyType::EDUCATIONAL => $typePercentage >= 0.25,
            ToyType::FUN => $typePercentage >= 0.30,
            ToyType::CREATIVE => $typePercentage >= 0.20,
        };
    }
}
