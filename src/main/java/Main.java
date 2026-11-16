import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int opcion;

        do {
            opcion = mostrarMenu();

            switch (opcion) {
                case 1:
                    // TODO: opción 1
                    break;
                case 2:
                    // TODO: opción 2
                    break;
                case 3:
                    // TODO: opción 3
                    break;
                case 4:
                    // TODO: opción 4
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida",
                            "Error", JOptionPane.ERROR_MESSAGE);
            }

        } while (opcion != 0);
    }

    private static int mostrarMenu() {
        String entrada = JOptionPane.showInputDialog(null,
                "===== MENÚ PRINCIPAL =====\n"
                        + "1. Opción 1\n"
                        + "2. Opción 2\n"
                        + "3. Opción 3\n"
                        + "4. Opción 4\n"
                        + "0. Salir\n\n"
                        + "Elige una opción:",
                "Menú", JOptionPane.QUESTION_MESSAGE);


        if (entrada == null) {
            return 0;
        }

        try {
            return Integer.parseInt(entrada.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
