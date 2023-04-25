import java.util.ArrayList;
import java.util.List;

public class LexAnalyzer {
    //Лексический анализатор
    public static List<Lexeme> lexAnalyze(String text) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int position = 0;
        //прохождение по строке
        while (position < text.length()) {
            char currentChar = text.charAt(position);//получение текущего символа
            switch (currentChar) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LeftBr, currentChar));
                    position++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RightBr, currentChar));
                    position++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.Plus, currentChar));
                    position++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.Minus, currentChar));
                    position++;
                    continue;
                default: //В этой ситуации оказываются числа, функции toDollars / toRubles, а также пробелы. Всё остальное - ошибка
                    boolean isDollar = false;
                    if (currentChar == '$') {//Проверка на доллар
                        isDollar = true;
                        position++;
                        currentChar = text.charAt(position);
                    }
                    //Получение числа
                    if (currentChar >= '0' && currentChar <= '9') {
                        StringBuilder sb = new StringBuilder();
                        int flag = 0;
                        do {
                            sb.append(currentChar);
                            position++;
                            if (position >= text.length()) {
                                break;
                            }
                            currentChar = text.charAt(position);
                            if (currentChar == '.') {
                                flag++;
                            }
                            if (flag > 1) { //Проверка на максимум одну точку в числе
                                throw new RuntimeException("2 ,,");
                            }
                        } while ((currentChar >= '0' && currentChar <= '9') || currentChar == '.');
                        //Определяем к какой валюте относится число
                        if (isDollar) {//Запись доллара
                            lexemes.add(new Lexeme(LexemeType.Doll, sb.toString()));
                            isDollar = false;
                            continue;
                        }
                        char a = text.charAt(position);
                        position++;
                        if (a == 'p' || a == 'р') {//Проверка на рубль
                            lexemes.add(new Lexeme(LexemeType.Rub, sb.toString()));
                        }
                    }//Ключевые слова
                    else if ("toDollars".contains(Character.toString(currentChar)) || "toRubles".contains(Character.toString(currentChar))) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(currentChar);
                        while ("toDollars".contains(sb.toString()) || "toRubles".contains(sb.toString())) {
                            position++;
                            if (position >= text.length()) {
                                break;
                            }
                            currentChar = text.charAt(position);
                            sb.append(currentChar);
                        }
                        if (sb.substring(0, sb.length() - 1).toString().equals("toDollars")) {
                            lexemes.add(new Lexeme(LexemeType.ToD, sb.substring(0, sb.length() - 1).toString()));
                        } else if (sb.substring(0, sb.length() - 1).toString().equals("toRubles")) {
                            lexemes.add(new Lexeme(LexemeType.ToR, sb.substring(0, sb.length() - 1).toString()));
                        } else {
                            throw new RuntimeException("Unexpected character: " + sb.toString());
                        }
                    } else {
                        if (currentChar != ' ') {//Если не пробел - ошибка
                            throw new RuntimeException("Unexpected character: " + currentChar);
                        }
                        position++;
                    }
            }
        }
        //Конец строки
        lexemes.add(new Lexeme(LexemeType.Eof, ""));
        return lexemes;
    }
}