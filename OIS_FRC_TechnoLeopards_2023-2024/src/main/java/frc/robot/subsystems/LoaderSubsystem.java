package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import frc.robot.Constants;

public class LoaderSubsystem {
    private static final double maxSpeed = 1.0;
    private static double currSpeed = maxSpeed;
    private final CANSparkMax lowerLoaderMotor;
    private final CANSparkMax upperLoaderMotor;

    public LoaderSubsystem(){
        lowerLoaderMotor = new CANSparkMax(Constants.lowerLoaderMotorDeviceId, CANSparkLowLevel.MotorType.kBrushless);
        upperLoaderMotor = new CANSparkMax(Constants.upperLoaderMotorDeviceId, CANSparkLowLevel.MotorType.kBrushless);
    }

    public static void updateSpeed(double speed){
        if(Math.abs(speed) <= maxSpeed){
            currSpeed = speed; 
        } else{
            if (speed > 0){
                currSpeed = maxSpeed;
            } else{
                currSpeed = -maxSpeed;
            }
        }
    }

    public void runLoader(){
        lowerLoaderMotor.set(currSpeed);
        upperLoaderMotor.set(currSpeed);
    }

    public void stopLoader(){
        lowerLoaderMotor.stopMotor();
        upperLoaderMotor.stopMotor();
    }
}
