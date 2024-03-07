package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;

public class ShooterSubsystem extends SubsystemBase {
   
    private final CANSparkMax shooterMotor; 
    private final CanSparkMAX shooterMotor2; 
    private final Encoder shooterEncoder;

    public ShooterSubsystem() {
        shooterMotor = new CANSparkMax(0, MotorType.kBrushless);
        shooterMotor2 = new CanSparkMax(0, MotorType.kBrushless);

        shooterPIDController = new PIDController(0, 0, 0); // SHOOTER CONSTANTS NEED TO BE FOUND
    }

    public void setShooterSpeeds(int speed) {
        shooterMotor.set(speed);
        shooterMotor2.set(speed);
    }

    public void stop() {
        shooterMotor.set(0);
        shooterMotor2.set(0);
    }

    
}