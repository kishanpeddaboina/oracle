package gov.laca.amp.pdcr.common.model;

import gov.laca.amp.ent.exception.AmpException;


public class AmpChangeRequestException extends AmpException {
    private static final long serialVersionUID = 1L;

    public AmpChangeRequestException() {
        super();
    }

    public AmpChangeRequestException(String string, String string1) {
        super(string, string1);
    }

    public AmpChangeRequestException(String string, String string1, Throwable throwable) {
        super(string, string1, throwable);
    }

    public AmpChangeRequestException(Throwable throwable) {
        super(throwable);
    }

}
