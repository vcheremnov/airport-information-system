package app.model.types;

public enum Sex {
    MALE,
    FEMALE;

    public static String toLocalizedString(Sex sex) {
        switch (sex) {
            case MALE:
                return "Муж.";
            case FEMALE:
                return "Жен.";
            default:
                return "";
        }
    }
}
