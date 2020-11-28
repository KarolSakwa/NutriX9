package Classes;

import javafx.scene.control.TextField;

public class TextFieldSanitizer {

    public static String sanitizeStringTextField(TextField textField)
    {
        String string = new String();
        string = textField.getText().replace(" ", "");
        string = string.replaceAll("\\<[^>]*>","");
        if (string.length() > 10)
            return null;

        return string;
    }

    public static Integer sanitizeIntegerTextField(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
