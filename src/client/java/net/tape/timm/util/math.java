package net.tape.timm.util;

public class math {
    public static long lerp(long min, long max, double t) {
        if ((t > 1.0) || (t < 0.0)) {
            throw new ArithmeticException("lerp value out of bounds");
        } else {
            return Math.round(max*t + min*(1-t));
        }
    }

    public static double invLerp(long min, long max, long value) {
        if ((value < min) || (value > max)) {
            throw new ArithmeticException("value out of bounds");
        } else {
            return (double) (value - min) / (double) (max - min);
        }
    }
}
