public class Lexeme {
    private LexemeType mType;//Тип лексемы
    private String mVal;//Содержание лексемы

    //Геттеры
    public LexemeType getType() {
        return mType;
    }

    public String getVal() {
        return mVal;
    }

    public Lexeme(LexemeType type, String val) {
        this.mType = type;
        this.mVal = val;
    }

    public Lexeme(LexemeType type, Character val) {
        this.mType = type;
        this.mVal = val.toString();
    }
}
