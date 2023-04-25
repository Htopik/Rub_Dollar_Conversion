import java.util.List;

public class LexemeList {
    private int mPos;//Позиция
    private double mRate;//Курс доллара для подсчета
    private List<Lexeme> mLexemes;//Коллекция лексем

    //Конструктор
    public LexemeList(List<Lexeme> lexemes, double rate) {
        this.mLexemes = lexemes;
        this.mRate = rate;
    }

    public Lexeme next() { //Просмотр следующей лексемы
        return mLexemes.get(mPos++);
    }

    public void back() { //Вернуться на позицию назад
        mPos--;
    }

    public int getPos() { //Получить текущую позицию
        return mPos;
    }

    public double getRate() { //Получить текущую позицию
        return mRate;
    }
}
