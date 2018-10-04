function openPopup(popupId, panelWindowId) {
    return function (event) {
        var agent = AdfAgent.AGENT;
        var windowWidth = agent.getWindowWidth();
        var windowHeight = agent.getWindowHeight();
        var popup = AdfPage.PAGE.findComponentByAbsoluteId(popupId);
        if (popup != null) {
            var panelWindow = popup.findComponent(panelWindowId);
            panelWindow.setContentWidth(Math.max(100, windowWidth - 60));
            panelWindow.setContentHeight(Math.max(100, windowHeight - 80));
            popup.show();
        }
    }
}