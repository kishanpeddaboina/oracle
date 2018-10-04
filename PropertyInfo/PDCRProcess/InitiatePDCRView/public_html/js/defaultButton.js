//function called by the client listener
function onFieldEnterKey(inputEvent) {
    if (inputEvent.getKeyCode() == AdfKeyStroke.ENTER_KEY) {
        //get the input text component from the event            
        var inputTextField = inputEvent.getSource();
        var uiComponent = "retrieveAIN";
        //the button is relative to the input text field so
        //relative search will do with no worrying about naming
        //containers
        var defaultButton = inputTextField.findComponent(uiComponent);
        //perform a partial submot
        var partialSubmit = true;
        AdfActionEvent.queue(defaultButton, partialSubmit);
        //Enter key does not need to go to server as we
        //queued a new event
        inputEvent.cancel();
    }
}