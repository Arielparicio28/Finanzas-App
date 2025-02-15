package com.example.backend.backend.util;

import java.util.Random;

public class CardNumberGenerator {

    private static final Random random = new Random();

    /**
     * Genera un número de tarjeta de 16 dígitos que cumple el algoritmo de Luhn.
     */
    public static String generateCardNumber() {
        // Uso un prefijo, por ejemplo "4000" (puede ser cualquiera)
        StringBuilder sb = new StringBuilder("4000");

        // Generamos 11 dígitos aleatorios (ya que el último dígito será el dígito de control)
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }

        // Obtenemos el dígito de control usando el algoritmo de Luhn
        String numberWithoutCheckDigit = sb.toString();
        int checkDigit = calculateLuhnCheckDigit(numberWithoutCheckDigit);
        sb.append(checkDigit);

        return sb.toString();
    }

    /**
     * Calcula el dígito de control usando el algoritmo de Luhn para el número proporcionado.
     */
    private static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = true;
        // Se recorre el número de derecha a izquierda
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = n - 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit;
    }
}
