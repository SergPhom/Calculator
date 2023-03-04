import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                Введите пример для решения.
                Тербуется два операнда (арабские или римские цифры от 1 до 10)\s
                и один оператор (+, -, /, *) введенные через пробел:""");
        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine();
        sc.close();
        System.out.println(calc(string));
    }
    // калькулятор принимающий ввод
    public static String calc(String input){
        String[] parts = input.trim().split(" ");
        parts = checkAndParse(parts);
        var result = "";
        byte operand1 = Byte.parseByte(parts[0]);
        byte operand2 = Byte.parseByte(parts[2]);
        result = switch (parts[1]) {
            case "+" ->  String.valueOf(operand1 + operand2);
            case "-" ->  String.valueOf(operand1 - operand2);
            case "*" ->  String.valueOf(operand1 * operand2);
            case "/" ->  String.valueOf(operand1 / operand2);
            default -> "Пример не решен!";
        };
        if (parts[3].equals(NumericSystem.ROMAN.name())) {
            result = arabicToRoman(result);
        }
        return result;
    }
    // Основные проверки и разбор ввода
    private static String[] checkAndParse(String[] input){
        // Проверка количества элементов ввода
        if(input.length != 3){
            throw new RuntimeException("Формат математической операции не удовлетворяет заданию " +
                    "- два операнда и один оператор (+, -, /, *)");
        }
        // Проверка наличия оператора
        if(!input[1].equals("+") && !input[1].equals("-")
                && !input[1].equals("*") && !input[1].equals("/") ) {
            throw new RuntimeException("Корректный оператор (+, -, * или /) не найден!");
        }
        // Проверка идентичности систем счисления операндов
        if(isRoman(input[0]) != isRoman(input[2])){
            throw new RuntimeException("Используются одновременно разные системы счисления!");
        }
        // Объявление элементов результата
        byte num1, num2;
        var langFlag = "";
        // Парсинг операндов
        if(isRoman(input[0])){
            var temp = romanToByteV2(new String[]{input[0], input[2]});
            num1 = temp[0];
            num2 = temp[1];
            langFlag = NumericSystem.ROMAN.name();

        } else {
            try{
                num1 = Byte.parseByte(input[0]);
                num2 = Byte.parseByte(input[2]);
            }catch (Exception e){
                throw new RuntimeException("Введены некорретрные арабские числа!");
            }
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 >10){
                throw new RuntimeException("Введенные числа должны быть от 1 до 10!");
            }
            langFlag = NumericSystem.ARABIC.name();
        }

        return new String[]{String.valueOf(num1), input[1], String.valueOf(num2), langFlag};
    }
    // Проверка на признак римского числа
    private static boolean isRoman(String s){
        return s.toUpperCase().contains("I")
                || s.toUpperCase().contains("V")
                || s.toUpperCase().contains("X");
    }
    // Конвертеры для римских чисел
    private static byte[] romanToByte(String[] args){
        byte[] result = new byte[args.length];
        try{
            for (int i = 0; i < args.length; i++) {
                result[i] = RomanNumbs.valueOf(args[i].toUpperCase()).arabic;
            }
        } catch (Exception e){
            throw new RuntimeException("Введены некорректные римские числа!");
        }
        return result;
    }
    private static byte[] romanToByteV2(String[] args){
        byte[] result = new byte[args.length];
        try{
            for (int i = 0; i < args.length; i++) {
                byte sum = 0;
                byte prev = 0;
                for(int j = args[i].length() - 1; j >= 0; j--){
                    byte curr = RomanNumbs.valueOf(
                            Character.valueOf(args[i].toUpperCase().charAt(j)).toString()
                    ).arabic;
                    sum = (byte) ((curr < prev) ?  (sum - curr) : (sum + curr));
                    prev = curr;
                }
                result[i] = sum;
            }
        } catch (Exception e){
            throw new RuntimeException("Введены некорректные римские числа!");
        }
        return result;
    }

    private static String arabicToRoman(String arab) {
        byte input = Byte.parseByte(arab);
        StringBuilder result = new StringBuilder();
        for (RomanNumbs numbs: RomanNumbs.values()) {
            while (input >= numbs.arabic){
                result.append(numbs.name());
                input -= numbs.arabic;
            }
        }
        return result.toString();
    }
    private enum RomanNumbs {
        C((byte) 100), XC((byte) 90), L((byte) 50), XL((byte) 40),
        X((byte) 10), IX((byte) 9), VIII((byte) 8), VII((byte) 7), VI((byte) 6),
        V((byte) 5), IV((byte) 4), III((byte) 3), II((byte) 2), I((byte) 1);
        private final byte arabic;
        RomanNumbs(byte arabic) {
            this.arabic = arabic;
        }
    }
}