package net.tape.TapeSON;

public class jsonParseException extends RuntimeException {
    static final long serialVersionUID = -694201337L;

    /**
     * Creates exception with the specified message. If you are wrapping another exception, consider
     * using {@link #jsonParseException(String, Throwable)} instead.
     *
     * @param msg error message describing a possible cause of this exception.
     */
    public jsonParseException(String msg) {
            super(msg);
        }
        /**
         * Creates exception with the specified message and cause.
         *
         * @param msg error message describing what happened.
         * @param cause root exception that caused this exception to be thrown.
         */
    public jsonParseException(String msg, Throwable cause) {
            super(msg, cause);
        }

        /**
         * Creates exception with the specified cause. Consider using
         * {@link #jsonParseException(String, Throwable)} instead if you can describe what happened.
         *
         * @param cause root exception that caused this exception to be thrown.
         */
    public jsonParseException(Throwable cause) {
            super(cause);
        }
    }

