package gov.laca.amp.bvm;

import gov.laca.amp.ent.exception.AmpException;

public class AmpBaseYearEventException extends AmpException {
    private static final long serialVersionUID = 1L;

    public AmpBaseYearEventException() {
        super();
    }

    public AmpBaseYearEventException(String string, String string1) {
        super(string, string1);
    }

    public AmpBaseYearEventException(String string, String string1,
                           Throwable throwable) {
        super(string, string1, throwable);
    }

    public AmpBaseYearEventException(Throwable throwable) {
        super(throwable);
    }
}
