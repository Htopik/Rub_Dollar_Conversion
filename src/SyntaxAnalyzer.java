public class SyntaxAnalyzer {//Синтаксический анализатор

    public static double syntaxAnalyzer(LexemeList lexemes) {
        Lexeme lex = lexemes.next();
        switch (lex.getType()) {
            case Rub:
            case ToR:
                lexemes.back();
                return sumRub(lexemes); //Вызов метода работы над числами в формате рублей
            case Doll:
            case ToD:
                lexemes.back();
                return sumDoll(lexemes);//Вызов метода работы над числами в формате долларов
            default:
                throw new RuntimeException("Incorrect Syntax");
        }
    }

    //Метод работы над числами в формате рублей
    public static double sumRub(LexemeList lexemes) {
        double val = rubConverter(lexemes);
        while (true) {
            Lexeme lex = lexemes.next();
            switch (lex.getType()) {
                case Minus:
                    val -= rubConverter(lexemes);
                    break;
                case Plus:
                    val += rubConverter(lexemes);
                    break;
                case Eof:
                    lexemes.back();
                    return val;
                case RightBr://Правая скобка означает, что сложение рублей происходило внутри функции toDollars()
                    lexemes.back();
                    return val / lexemes.getRate();//Перевод рубли в доллары по заданному курсу
                default:
                    throw new RuntimeException("Incorrect Syntax");
            }
        }
    }

    //Метод работы над числами в формате долларов
    public static double sumDoll(LexemeList lexemes) {
        double val = dollConverter(lexemes);
        while (true) {
            Lexeme lex = lexemes.next();
            switch (lex.getType()) {
                case Minus:
                    val -= dollConverter(lexemes);
                    break;
                case Plus:
                    val += dollConverter(lexemes);
                    break;
                case Eof:
                    lexemes.back();
                    return val;
                case RightBr://Правая скобка означает, что сложение долларов происходило внутри функции toRubles()
                    lexemes.back();
                    return val * lexemes.getRate();//Перевод долларов в рубли по заданному курсу
                default:
                    throw new RuntimeException("Incorrect Syntax");
            }
        }
    }

    //Метод преобразования строки, содержащей рубли или операции над ними, в число
    public static double rubConverter(LexemeList lexemes) {
        Lexeme lex = lexemes.next();
        switch (lex.getType()) {
            case Rub:
                return Double.parseDouble(lex.getVal());
            case ToR:
                lex = lexemes.next();
                if (lex.getType() == LexemeType.LeftBr) {
                    double val = sumDoll(lexemes);
                    lex = lexemes.next();
                    if (lex.getType() != LexemeType.RightBr) {
                        throw new RuntimeException("Incorrect Syntax");
                    }
                    return val;
                } else {
                    throw new RuntimeException("Incorrect Syntax");
                }
            default:
                throw new RuntimeException("Incorrect Syntax");
        }
    }

    //Метод преобразования строки, содержащей доллары или операции над ними, в число
    public static double dollConverter(LexemeList lexemes) {
        Lexeme lex = lexemes.next();
        switch (lex.getType()) {
            case Doll:
                return Double.parseDouble(lex.getVal());
            case ToD:
                lex = lexemes.next();
                if (lex.getType() == LexemeType.LeftBr) {
                    double val = sumRub(lexemes);
                    lex = lexemes.next();
                    if (lex.getType() != LexemeType.RightBr) {
                        throw new RuntimeException("Incorrect Syntax");
                    }
                    return val;
                }
            default:
                throw new RuntimeException("Incorrect Syntax");
        }
    }
}