package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;

public class ShooterSubsystem {
    private final CANSparkMax lowerShooterMotor;
    private final CANSparkMax upperShooterMotor;

    public ShooterSubsystem(){
        lowerShooterMotor = new CANSparkMax(Constants.lowerShooterMotorDeviceId, CANSparkLowLevel.MotorType.kBrushless);
        upperShooterMotor = new CANSparkMax(Constants.upperShooterMotorDeviceId, CANSparkLowLevel.MotorType.kBrushless);
    }

    public void runShooter(){
        lowerShooterMotor.set(-Constants.shooterMaxSpeed);
        upperShooterMotor.set(-Constants.shooterMaxSpeed);
    }

    public void stopShooter(){
        lowerShooterMotor.stopMotor();
        upperShooterMotor.stopMotor();
    }
}