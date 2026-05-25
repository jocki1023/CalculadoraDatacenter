import unittest

from calculator import add, calculate, divide, multiply, subtract


class CalculatorOperationsTest(unittest.TestCase):
    def test_add(self):
        self.assertEqual(add(2, 3), 5)

    def test_subtract(self):
        self.assertEqual(subtract(10, 4), 6)

    def test_multiply(self):
        self.assertEqual(multiply(3, 7), 21)

    def test_divide(self):
        self.assertEqual(divide(20, 5), 4)

    def test_divide_by_zero_raises_error(self):
        with self.assertRaisesRegex(ValueError, "dividir entre cero"):
            divide(8, 0)

    def test_calculate_dispatches_by_operator(self):
        self.assertEqual(calculate(9, 3, "+"), 12)
        self.assertEqual(calculate(9, 3, "-"), 6)
        self.assertEqual(calculate(9, 3, "*"), 27)
        self.assertEqual(calculate(9, 3, "/"), 3)

    def test_calculate_rejects_unsupported_operator(self):
        with self.assertRaisesRegex(ValueError, "Operador no soportado"):
            calculate(1, 2, "%")


if __name__ == "__main__":
    unittest.main()
