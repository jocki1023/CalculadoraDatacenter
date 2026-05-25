"""Core calculator operations for the technical test project."""


def add(a, b):
    """Return the sum of two numeric values."""
    return a + b


def subtract(a, b):
    """Return the subtraction result of ``a - b``."""
    return a - b


def multiply(a, b):
    """Return the multiplication result of ``a * b``."""
    return a * b


def divide(a, b):
    """Return the division result of ``a / b``.

    Raises:
        ValueError: If ``b`` is zero.
    """
    if b == 0:
        raise ValueError("No se puede dividir entre cero")
    return a / b


def calculate(a, b, operator):
    """Execute the operation defined by ``operator`` over ``a`` and ``b``.

    Supported operators are ``+``, ``-``, ``*`` and ``/``.

    Raises:
        ValueError: If ``operator`` is not supported.
    """
    operations = {
        "+": add,
        "-": subtract,
        "*": multiply,
        "/": divide,
    }

    if operator not in operations:
        raise ValueError(f"Operador no soportado: {operator}")

    return operations[operator](a, b)
