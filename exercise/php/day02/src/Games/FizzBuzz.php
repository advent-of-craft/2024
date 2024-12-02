<?php

declare(strict_types = 1);

namespace Games {
	class FizzBuzz
	{
		public const MIN = 1;

		public const MAX = 100;

		private static array $mapping = [
			15 => 'FizzBuzz',
			3 => 'Fizz',
			5 => 'Buzz',
		];

		public static function convert(int $input): ?string
		{
			if (self::isOutOfRange($input)) {
				return null;
			}

			return self::convertSafely($input);
		}

		private static function isOutOfRange(int $input): bool
		{
			return self::MIN > $input || self::MAX < $input;
		}

		private static function convertSafely(int $input): string
		{
			foreach (self::$mapping as $key => $value) {
				if (self::isDivisibleBy($input, $key)) {
					return $value;
				}
			}

			return (string) $input;
		}

		private static function isDivisibleBy(int $input, int $divisor): bool
		{
			return $input % $divisor === 0;
		}
	}
}
