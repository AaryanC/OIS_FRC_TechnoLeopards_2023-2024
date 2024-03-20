package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

public class LoaderSubsystem {
    private static final double maxSpeed = 3.0;//CHECK SPEED
    private static double currSpeed = maxSpeed;
    private final CANSparkMax lowerLoaderMotor;
    private final CANSparkMax upperLoaderMotor;

    public LoaderSubsystem(){
        lowerLoaderMotor = new CANSparkMax(22, CANSparkLowLevel.MotorType.kBrushless);//NEED TO VERIFY
        upperLoaderMotor = new CANSparkMax(19, CANSparkLowLevel.MotorType.kBrushless);//NEED TO VERIFY
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

    public void runShooter(){
        lowerLoaderMotor.set(currSpeed);
        upperLoaderMotor.set(currSpeed);
    }

    public void stopShooter(){
        lowerLoaderMotor.stopMotor();
        upperLoaderMotor.stopMotor();
    }
}
