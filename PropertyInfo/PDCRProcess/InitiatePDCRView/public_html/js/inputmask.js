// JavaScript function that applies a specific format 
// to numeric input. The pattern argument defines the 
// input mask, e.g. xxx-xx-xxxx. The delimiter defines 
// the delimiter character to add to the user input based 
// on the pattern
function handleNumberFormatConversion(pattern, delimiter) {
    return function (evt) {
        var inputField = evt.getCurrentTarget();
        var keyPressed = evt.getKeyCode();
        var oldValue = inputField.getSubmittedValue();
        //keycode 48-57 are keys 0-9
        //keycode 96-105 are numbpad keys 0-9
        var validKeys = new Array(48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, AdfKeyStroke.ARROWRIGHT_KEY, AdfKeyStroke.ARROWLEFT_KEY, AdfKeyStroke.BACKSPACE_KEY, AdfKeyStroke.DELETE_KEY, AdfKeyStroke.END_KEY, AdfKeyStroke.ESC_KEY, AdfKeyStroke.TAB_KEY);
        var numberKeys = new Array(48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105);
        var isValidKey = false;

        for (var i = 0;i < validKeys.length;++i) {
            if (validKeys[i] == keyPressed) {
                isValidKey = true;
                break;
            }
        }
        if (isValidKey) {
            //key is valid, ensure formatting is correct
            var isNumberKey = false;
            for (var n = 0;n < numberKeys.length;++n) {
                if (numberKeys[n] == keyPressed) {
                    isNumberKey = true;
                    break;
                }
            }
            if (isNumberKey) {
                //if the user provided enough data, cancel the input
                var formatLength = pattern.length;
                if (formatLength == oldValue.length) {
                    inputField.setValue(oldValue);
                    evt.cancel();
                }
                //more values allowed. Check if delimiter/s needs to be set 
                else {
                    //if the data format has a delimiter as the next character, add it
                    if (pattern.charAt(oldValue.length) == delimiter) {
                        oldValue = oldValue + delimiter;
                        inputField.setValue(oldValue);
                    }
                }
            }
        }
        else {
            //key is not valid, so undo entry
            inputField.setValue(oldValue);
            evt.cancel();
        }
    }
}

function handlePhoneNumberFormatConversion(pattern) {
    return function (evt) {
        var inputField = evt.getCurrentTarget();
        var keyPressed = evt.getKeyCode();
        var oldValue = inputField.getSubmittedValue();
        var delimiter1 = "-";
        var delimiter2 = "(";
        var delimiter3 = ")";
        var delimiter4 = " ";

        //keycode 48-57 are keys 0-9
        //keycode 96-105 are numbpad keys 0-9
        var validKeys = new Array(48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, AdfKeyStroke.ARROWRIGHT_KEY, AdfKeyStroke.ARROWLEFT_KEY, AdfKeyStroke.BACKSPACE_KEY, AdfKeyStroke.DELETE_KEY, AdfKeyStroke.END_KEY, AdfKeyStroke.ESC_KEY, AdfKeyStroke.TAB_KEY);

        var numberKeys = new Array(48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105);

        var isValidKey = false;
        for (var i = 0;i < validKeys.length;++i) {
            if (validKeys[i] == keyPressed) {
                isValidKey = true;
                break;
            }
        }
        if (isValidKey) {
            //key is valid, ensure formatting is correct
            var isNumberKey = false;
            for (var n = 0;n < numberKeys.length;++n) {
                if (numberKeys[n] == keyPressed) {
                    isNumberKey = true;
                    break;
                }
            }
            if (isNumberKey) {
                //if the user provided enough data, cancel the input
                var formatLength = pattern.length;
                if (formatLength == oldValue.length) {
                    inputField.setValue(oldValue);
                    evt.cancel();
                }
                //more values allowed. Check if delimiter/s needs to be set 
                else {
                    //if the data format has a delimiter as the next character, add it
                    if (pattern.charAt(oldValue.length) == delimiter1) {
                        oldValue = oldValue + delimiter;
                        inputField.setValue(oldValue);
                    }
                    else if (pattern.charAt(oldValue.length) == delimiter2) {
                        oldValue = oldValue + delimiter;
                        inputField.setValue(oldValue);
                    }
                    else if (pattern.charAt(oldValue.length) == delimiter3) {
                        oldValue = oldValue + delimiter;
                        inputField.setValue(oldValue);
                    }
                    else if (pattern.charAt(oldValue.length) == delimiter4) {
                        oldValue = oldValue + delimiter;
                        inputField.setValue(oldValue);
                    }
                }
            }
        }
        else {
            //key is not valid, so undo entry
            inputField.setValue(oldValue);
            evt.cancel();
        }
    }
}