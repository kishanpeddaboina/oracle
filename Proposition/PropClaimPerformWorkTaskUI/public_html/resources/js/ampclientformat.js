function handleSSNTaxIDFormatConversion(actionEvent) {
    var inputComponent=actionEvent.getSource();
    var legalEntityNameComponent=inputComponent.findComponent("it15");
    
    if (legalEntityNameComponent.getValue()==null || legalEntityNameComponent.getValue() == "" ){
        return updateSSNTaxIDFormat('xxx-xx-xxxx','-', actionEvent)
    }
    else {  
        return updateSSNTaxIDFormat('xx-xxxxxxx','-', actionEvent)
    }

}

function handleUpdateSSNTaxIDFormatOnLegalEntityNameOutFocus(actionEvent) {

    var inputComponent=actionEvent.getSource();
    var legalEntityNameComponent=inputComponent.findComponent("it15");
    var ssnInputComponent=inputComponent.findComponent("it16");
    var oldValue = ssnInputComponent.getValue();
    var formattedValue = oldValue;
    
    if (!formattedValue || formattedValue.length <= 2){
        return
    }
    
    formattedValue = formattedValue.split('-').join('');
        
    if (legalEntityNameComponent.getValue()==null || legalEntityNameComponent.getValue() == "" ){
        if (formattedValue.length >= 5) {
            formattedValue = formattedValue.substr(0,3) + '-' + formattedValue.substr(3,2) + '-' + formattedValue.substr(5,4);
        }
        else{
            formattedValue = formattedValue.substr(0,3) + '-' + formattedValue.substr(3,2)
        }
    }
    else {
        formattedValue = formattedValue.substr(0,2) + '-' + formattedValue.substr(2,8);
    }
    
     ssnInputComponent.setValue(formattedValue);
     actionEvent.cancel();
}


function updateSSNTaxIDFormat(pattern, delimiter, evt)  {

    
        var inputField = evt.getCurrentTarget();
        var keyPressed = evt.getNativeEvent().keyCode;
        var oldValue = inputField.getSubmittedValue();

        var dComp = document.getElementById(inputField.getClientId() + "::content");
        
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

            var isNumberKey = false;
            for (var n = 0;n < numberKeys.length;++n) {
                if (numberKeys[n] == keyPressed) {
                    isNumberKey = true;
                    break;
                }
            }
            if (isNumberKey) {

                var formatLength = pattern.length;
                if (formatLength == oldValue.length) {
//                    inputField.setValue(oldValue);
                    dComp.value = oldValue;
                    evt.cancel();
                }

                else {

                    if (pattern.charAt(oldValue.length) == delimiter) {
                        oldValue = oldValue + delimiter;
//                        inputField.setValue(oldValue);
                        dComp.value = oldValue;
                    }
                }
            }
        }
        else {
//            inputField.setValue(oldValue);
            dComp.value = oldValue;
            evt.cancel();
        }
}


function handleNumberFormatConversion(pattern, delimiter) {
    return function (evt) {
        var inputField = evt.getCurrentTarget();
        var keyPressed = evt.getNativeEvent().keyCode;
        var oldValue = inputField.getSubmittedValue();

        var dComp = document.getElementById(inputField.getClientId() + "::content");

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

            var isNumberKey = false;
            for (var n = 0;n < numberKeys.length;++n) {
                if (numberKeys[n] == keyPressed) {
                    isNumberKey = true;
                    break;
                }
            }
            if (isNumberKey) {

                var formatLength = pattern.length;
                if (formatLength == oldValue.length) {
                    //inputField.setValue(oldValue);
                    dComp.value = oldValue;
                    evt.cancel();
                }

                else {

                    if (pattern.charAt(oldValue.length) == delimiter) {
                        oldValue = oldValue + delimiter;
                        //inputField.setValue(oldValue);
                        dComp.value = oldValue;
                    }
                }
            }
        }
        else {

            //inputField.setValue(oldValue);
            dComp.value = oldValue;
            evt.cancel();
        }

    }

}

function handlePhNumberFormatConversion(pattern, delimiter) {
    return function (evt) {
        var inputField = evt.getCurrentTarget();
        var keyPressed = evt.getNativeEvent().keyCode;
        var oldValue = inputField.getSubmittedValue();

        var dComp = document.getElementById(inputField.getClientId() + "::content");
        
        var oldLength = oldValue.length;
        var position = dComp.selectionEnd;

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

            var isNumberKey = false;
            for (var n = 0;n < numberKeys.length;++n) {
                if (numberKeys[n] == keyPressed) {
                    isNumberKey = true;
                    break;
                }
            }
            if (isNumberKey) {

                var formatLength = pattern.length;
                if (formatLength == oldValue.length) {
                    //inputField.setValue(oldValue);
                    dComp.value = oldValue;
                    evt.cancel();
                }

                else {

                    //if(pattern.charAt(oldValue.length)== delimiter){
                    //oldValue = oldValue+delimiter;
                    //inputField.setValue(oldValue);
                    //}
                    
                    var keyVal = String.fromCharCode(keyPressed);
                     if (keyPressed >= 96 && keyPressed <= 105) {
                             keyPressed -= 48;
                             keyVal = String.fromCharCode(keyPressed);
                             
                     }
                    
                    if(position == oldLength) {
                       oldValue = oldValue + keyVal;
                    } else {
                      oldValue = oldValue.substr(0, position) + keyVal + oldValue.substr(position,oldLength );
                    }
                    
                    oldValue = pho(oldValue);
                    //alert(oldValue);
                    //console.log(oldValue);
                    //inputField.setValue(oldValue);
                    dComp.value = oldValue;
                    if(position == 0 || position == 9 ) {
                        position  = position + 2;
                    } else if(position == 4 ) {
                        position  = position + 3;
                    } else {
                        position  = position + 1;
                    }
                    dComp.selectionEnd = position;//+oldValue.length - oldLength; 
                    evt.cancel();
                }
            }
        }
        else {

            //inputField.setValue(oldValue);
            dComp.value = oldValue;
            evt.cancel();
        }
    }

}

function pho(input) {
    input = input.replace(/\D/g, '');

    // Trim the remaining input to ten characters, to preserve phone number format
    input = input.substring(0, 10);

    // Based upon the length of the string, we add formatting as necessary
    var size = input.length;
    if (size == 0) {
        input = input;
    }
    else if (size < 4) {
        input = '(' + input;
    }
    else if (size < 7) {
        input = '(' + input.substring(0, 3) + ') ' + input.substring(3, 6);
    }
    else {
        input = '(' + input.substring(0, 3) + ') ' + input.substring(3, 6) + '-' + input.substring(6, 10);
    }
    return input;
}