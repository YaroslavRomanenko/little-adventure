package com.yarikcompany.game.utils;
import com.badlogic.gdx.math.Vector2;

public class Interpolator {
    private static final float PI = (float) Math.PI;

    private float k1, k2, k3;

    private Vector2 y;
    private Vector2 y_speed;
    private Vector2 x;
    private Vector2 last_x;

    /**
     * Constructor for the Interpolator.
     *
     * @param initialValue Initial value for the interpolation.
     * @param f Frequency (in Hz).
     * @param zeta Damping ratio. Use 1.0f for critical damping.
     * @param r Initial response.
     */
    public Interpolator(Vector2 initialValue, float f, float zeta, float r) {
        if (f <= 0 || zeta < 0) {
            throw new IllegalArgumentException("Invalid interpolator parameters: f must be > 0, zeta must be >= 0.");
        }

        this.k1 = zeta / PI / f;
        this.k2 = 1.0f / (2 * PI * f) / (2 * PI * f);
        this.k3 = r * zeta / (2 * PI * f);

        this.x = new Vector2(initialValue);
        this.last_x = new Vector2(initialValue);
        this.y = new Vector2(initialValue);
        this.y_speed = new Vector2(0, 0);
    }

    public void setTarget(Vector2 newValue) {
        this.last_x.set(this.x);
        this.x.set(newValue);
    }

    public void step(float deltaTime) {
        y.add(y_speed.x * deltaTime, y_speed.y * deltaTime);

        Vector2 x_speed = new Vector2(x).sub(last_x).scl(1.0f / deltaTime);

        Vector2 term1 = new Vector2(x_speed).scl(k3);
        Vector2 term2 = new Vector2(y_speed).scl(k1);

        Vector2 y_accel = new Vector2(x).add(term1).sub(y).sub(term2).scl(1.0f / k2);

        y_speed.add(y_accel.x * deltaTime, y_accel.y * deltaTime);
    }

    public Vector2 getInterpolatedValue() {
        return new Vector2(y);
    }

    public Vector2 getTargetValue() {
        return new Vector2(x);
    }

    public void jumpTo(Vector2 value) {
        this.x.set(value);
        this.last_x.set(value);
        this.y.set(value);
        this.y_speed.set(0, 0);
    }
}
