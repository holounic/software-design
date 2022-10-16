package ru.akirakozov.sd.refactoring.servlet;

public final class Utils {
    public static final String COMMAND_PARAM = "command";
    public static final String MIN_COMMAND = "min";
    public static final String MAX_COMMAND = "max";
    public static final String SUM_COMMAND = "sum";

    public static final String COUNT_COMMAND = "count";
    static final String NAME_COLUMN = "name";
    static final String PRICE_COLUMN = "price";

    static final String CONNECTION_URL = "jdbc:sqlite:test.db";

    static final String RESPONSE_CONTENT_TYPE = "text/html";
    static final String OPEN_TAGS = "<html><body>";
    static final String CLOSE_TAGS = "</body></html>";

    private Utils() {}
}
