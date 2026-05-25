import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Calculadora con 4 operaciones basicas.
 *
 * Patrones aplicados:
 *   1. Strategy  – cada operacion es una estrategia intercambiable (interfaz Operation)
 *   2. Factory   – OperationFactory crea la estrategia segun el operador
 *   3. Command   – Calculation encapsula un calculo y permite guardar historial
 *
 * Soporta enteros y reales (double).
 */
public class Calculator {

    // =========================================================
    // PATRON 1: STRATEGY
    // Interfaz comun para todas las operaciones matematicas.
    // =========================================================

    interface Operation {
        double execute(double a, double b);
        String symbol();
    }

    static class Add implements Operation {
        public double execute(double a, double b) { return a + b; }
        public String symbol() { return "+"; }
    }

    static class Subtract implements Operation {
        public double execute(double a, double b) { return a - b; }
        public String symbol() { return "-"; }
    }

    static class Multiply implements Operation {
        public double execute(double a, double b) { return a * b; }
        public String symbol() { return "*"; }
    }

    static class Divide implements Operation {
        public double execute(double a, double b) {
            if (b == 0) throw new ArithmeticException("Division por cero no permitida");
            return a / b;
        }
        public String symbol() { return "/"; }
    }

    // =========================================================
    // PATRON 2: FACTORY
    // Centraliza la creacion de estrategias.
    // El cliente solo conoce el simbolo; la fabrica decide
    // que clase concreta instanciar.
    // =========================================================

    static class OperationFactory {
        static Operation create(String op) {
            switch (op) {
                case "+": return new Add();
                case "-": return new Subtract();
                case "*": return new Multiply();
                case "/": return new Divide();
                default:  throw new IllegalArgumentException(
                              "Operador desconocido: '" + op + "'. Use: + - * /");
            }
        }
    }

    // =========================================================
    // PATRON 3: COMMAND
    // Encapsula operandos + estrategia + resultado como objeto.
    // Permite almacenarlo en un historial.
    // =========================================================

    static class Calculation {
        private final Operation operation;
        private final double a, b;
        private double result;

        Calculation(Operation op, double a, double b) {
            this.operation = op;
            this.a = a;
            this.b = b;
        }

        double execute() {
            result = operation.execute(a, b);
            return result;
        }

        @Override
        public String toString() {
            return fmt(a) + " " + operation.symbol() + " " + fmt(b) + " = " + fmt(result);
        }
    }

    // =========================================================
    // Orquestador: usa Factory para crear y Command para guardar
    // =========================================================

    private final List<Calculation> history = new ArrayList<>();

    double calculate(double a, String op, double b) {
        Operation operation = OperationFactory.create(op);   // Factory
        Calculation cmd = new Calculation(operation, a, b);  // Command
        double result = cmd.execute();
        history.add(cmd);
        return result;
    }

    void printHistory() {
        if (history.isEmpty()) {
            System.out.println("  (sin calculos aun)");
            return;
        }
        System.out.println("--- Historial ---");
        for (int i = 0; i < history.size(); i++)
            System.out.printf("  %d. %s%n", i + 1, history.get(i));
        System.out.println("-----------------");
    }

    // Muestra enteros sin decimales, reales con los decimales necesarios
    static String fmt(double v) {
        return (v == Math.floor(v) && !Double.isInfinite(v))
               ? String.valueOf((long) v)
               : String.valueOf(v);
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        Scanner sc = new Scanner(System.in);

        System.out.println("============================================");
        System.out.println("  Calculadora  |  Strategy + Factory + Command");
        System.out.println("============================================");
        System.out.println("  Uso     : <numero> <operador> <numero>");
        System.out.println("  Ejemplo : 3.5 + 2   |   10 / 4");
        System.out.println("  Comandos: history, exit");
        System.out.println();

        while (true) {
            System.out.print(">> ");
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();

            if (line.isEmpty()) continue;

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Hasta luego.");
                break;
            }

            if (line.equalsIgnoreCase("history")) {
                calc.printHistory();
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length != 3) {
                System.out.println("  Formato invalido. Use: <numero> <operador> <numero>\n");
                continue;
            }

            try {
                double a  = Double.parseDouble(parts[0]);
                String op = parts[1];
                double b  = Double.parseDouble(parts[2]);
                double result = calc.calculate(a, op, b);
                System.out.println("  = " + fmt(result) + "\n");
            } catch (NumberFormatException e) {
                System.out.println("  Error: numero invalido\n");
            } catch (ArithmeticException | IllegalArgumentException e) {
                System.out.println("  Error: " + e.getMessage() + "\n");
            }
        }

        sc.close();
    }
}
