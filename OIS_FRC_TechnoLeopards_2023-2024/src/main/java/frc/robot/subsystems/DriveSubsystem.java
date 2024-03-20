package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase{
    private static final double kTrueMaxSpeed = 3.0; //3 m/s max speed, also set based on throttle
    private double kMaxSpeed = kTrueMaxSpeed;
    private static final double kMaxAngularSpeed = Math.PI; //180 degrees/s max speed

    private final double translationDistance = 0.381;//Enter translation distance here
    private final Translation2d m_frontLeftLocation = new Translation2d(translationDistance, translationDistance);
    private final Translation2d m_frontRightLocation = new Translation2d(translationDistance, -translationDistance);
    private final Translation2d m_backLeftLocation = new Translation2d(-translationDistance, translationDistance);
    private final Translation2d m_backRightLocation = new Translation2d(-translationDistance, -translationDistance);
    
    private final SwerveModule m_frontLeft = new SwerveModule(2, 1, CANSparkLowLevel.MotorType.kBrushless, CANSparkLowLevel.MotorType.kBrushless, 0, 0, 0, 0);
    private final SwerveModule m_frontRight = new SwerveModule(4, 3, CANSparkLowLevel.MotorType.kBrushless, CANSparkLowLevel.MotorType.kBrushless, 0, 0, 0, 0);
    private final SwerveModule m_backLeft = new SwerveModule(6, 5, CANSparkLowLevel.MotorType.kBrushless, CANSparkLowLevel.MotorType.kBrushless, 0, 0, 0, 0);
    private final SwerveModule m_backRight = new SwerveModule(8, 7, CANSparkLowLevel.MotorType.kBrushless, CANSparkLowLevel.MotorType.kBrushless, 0, 0, 0, 0);

    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
    
    public DriveSubsystem(){
        
    }

    public static double getMaxSpeed(){
        return kMaxAngularSpeed;
    }

    public void updateMaxSpeed(double maxSpeed){
        kMaxSpeed = maxSpeed;
    }

    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative, double periodSeconds){
        var swerveModuleStates =
        m_kinematics.toSwerveModuleStates(
            ChassisSpeeds.discretize(
                fieldRelative
                    ? ChassisSpeeds.fromFieldRelativeSpeeds(
                        xSpeed, ySpeed, rot, m_gyro.getRotation2d())
                    : new ChassisSpeeds(xSpeed, ySpeed, rot),
                periodSeconds));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, kMaxSpeed);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_backLeft.setDesiredState(swerveModuleStates[2]);
        m_backRight.setDesiredState(swerveModuleStates[3]);
    }
}
 