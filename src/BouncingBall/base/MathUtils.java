package BouncingBall.base;

public class MathUtils {
    public static double root(double a, double b, double c) {
        return (-b - Math.sqrt(b*b-4*a*c)) / (2*a);
    }
}
