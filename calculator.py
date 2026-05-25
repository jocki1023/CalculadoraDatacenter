"""Core calculator operations for the technical test project."""


def add(a, b):
    return a + b


def subtract(a, b):
    return a - b


def multiply(a, b):
    return a * b


def divide(a, b):
    if b == 0:
        raise ValueError("No se puede dividir entre cero")
    return a / b


def calculate(a, b, operator):
    operations = {
        "+": add,
        "-": subtract,
        "*": multiply,
        "/": divide,
    }

    if operator not in operations:
        raise ValueError(f"Operador no soportado: {operator}")

    return operations[operator](a, b)
