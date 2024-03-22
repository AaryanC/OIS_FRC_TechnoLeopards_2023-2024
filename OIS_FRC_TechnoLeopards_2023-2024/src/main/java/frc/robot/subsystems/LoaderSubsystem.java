package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import frc.robot.Constants;

public class LoaderSubsystem {
    private final CANSparkMax lowerLoaderMotor;
    private final CANSparkMax upperLoaderMotor;

    public LoaderSubsystem(){
        lowerLoaderMotor = new CANSparkMax(Constants.lowerLoaderMotorDeviceId, CANSparkLowLevel.MotorType.kBrushless);
        upperLoaderMotor = new CANSparkMax(Constants.upperLoaderMotorDeviceId, CANSparkLowLevel.MotorType.kBrushless);
    }

    public void runLoader(){
        lowerLoaderMotor.set(Constants.loaderMaxSpeed);
        upperLoaderMotor.set(Constants.loaderMaxSpeed);
    }

    public void stopLoader(){
        lowerLoaderMotor.stopMotor();
        upperLoaderMotor.stopMotor();
    }
}
