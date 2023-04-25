import java.util.List;
import java.util.Scanner;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        FileInputStream input;
        Properties property = new Properties();
        Scanner in = new Scanner(System.in);
        try {
            input = new FileInputStream("src/config.properties");
            property.load(input);
            double rate = Double.parseDouble(property.getProperty("rate"));
            System.out.print("Enter expression: ");
            String text = in.nextLine().replace(",", ".");//Получение выражения
            //Все числа с дробными значениями должны быть отделены "." для успешной работы Double.parse()
            List<Lexeme> lexemes = LexAnalyzer.lexAnalyze(text);//Запуск лексического анализатора
            Lexeme finalCurr = lexemes.get(0);//Получение информации о формате итоговой валюты
            LexemeList lexBuf = new LexemeList(lexemes, rate);//Создание буфера лексем
            double result = SyntaxAnalyzer.syntaxAnalyzer(lexBuf);//Запуск синтаксического анализатора
            String res = String.format("%.2f", result);//Вывод результата с двумя знаками после запятой
            if (finalCurr.getType() == LexemeType.ToR || finalCurr.getType() == LexemeType.Rub)
                System.out.print(res + "p");
            else if (finalCurr.getType() == LexemeType.ToD || finalCurr.getType() == LexemeType.Doll)
                System.out.print("$" + res);
        }//Обработка исключений
        catch (IOException ex) {
            System.err.println("Configuration file is not found!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid dollar rate");
        }
    }
}