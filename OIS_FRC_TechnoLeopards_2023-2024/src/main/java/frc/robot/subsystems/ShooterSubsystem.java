package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class ShooterSubsystem extends PIDSubsystem {
    private final CANSparkMax m_shooterMotor;
    private final CANSparkMax m_feederMotor; // Optional, remove if not used
    private final Encoder m_shooterEncoder;
    private final SimpleMotorFeedforward m_shooterFeedforward;
    private final Joystick m_joystick; // Joystick instance
    private double shooterSpeed = 0.5;
    private double feederSpeed = 0.5;

    // Constants for the shooter subsystem
    private static final double kP = 0.1; // Proportional gain
    private static final double kI = 0.0; // Integral gain
    private static final double kD = 0.0; // Derivative gain
    private static final double kS = 0.123; // Static gain
    private static final double kV = 0.025; // Velocity gain
    private static final double kA = 0.01; // Acceleration gain
    private static final double kEncoderDistancePerPulse = 1.0 / 4096.0; // Adjust based on your encoder
    private static final double kShooterTargetRPS = 5000.0; // Target RPS for the shooter
    private static final double kShooterToleranceRPS = 100.0; // Tolerance for the shooter RPS

    public ShooterSubsystem(Joystick joystick) {
        super(new PIDController(kP, kI, kD));
        getController().setTolerance(kShooterToleranceRPS);

        m_shooterMotor = new CANSparkMax(0, CANSparkMax.MotorType.kBrushless);
        m_feederMotor = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless); // Remove if not used
        m_shooterEncoder = new Encoder(0, 1);
        m_shooterFeedforward = new SimpleMotorFeedforward(kS, kV, kA);

        m_shooterEncoder.setDistancePerPulse(kEncoderDistancePerPulse);
        setSetpoint(kShooterTargetRPS);

        this.m_joystick = joystick; // Initialize the joystick instance
    }

    @Override
    public void useOutput(double output, double setpoint) {
        m_shooterMotor.setVoltage(output + m_shooterFeedforward.calculate(setpoint));
    }

    @Override
    public double getMeasurement() {
        return m_shooterEncoder.getRate();
    }

    public boolean atSetpoint() {
        return getController().atSetpoint();
    }

    public void runFeeder() {
        m_feederMotor.set(feederSpeed); // Adjust the speed as needed
    }

    public void stopFeeder() {
        m_feederMotor.set(0);
    }

    public void checkJoystickAndShoot() {
        if (m_joystick.getRawButtonPressed(1)) { // Assuming button 1 is used for shooting
            shoot(shooterSpeed);
        }
    }

    private void shoot(double speed) {
        // Implement the logic to shoot here
        // For example, set the shooter motor to a certain speed
        m_shooterMotor.set(speed); // Adjust the speed as needed
    }

    public void setDefaultFeederSpeed(double feederSpeed){
        this.feederSpeed = feederSpeed;
    }

    public void setDefaultShooterSpeed(double shooterSpeed){
        this.shooterSpeed = shooterSpeed;
    }
}
