package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.AnalogGyro;

public class DriveSubsystem extends SubsystemBase{
    private double kMaxSpeed = Constants.kTrueMaxSpeed;
    private static final double kMaxAngularSpeed = Math.PI;

    private final double translationDistance = 0.381;//Enter translation distance here
    private final Translation2d m_frontLeftLocation = new Translation2d(translationDistance, translationDistance);
    private final Translation2d m_frontRightLocation = new Translation2d(translationDistance, -translationDistance);
    private final Translation2d m_backLeftLocation = new Translation2d(-translationDistance, translationDistance);
    private final Translation2d m_backRightLocation = new Translation2d(-translationDistance, -translationDistance);
    
    private final AnalogGyro m_gyro = new AnalogGyro(0);

    private final SwerveModule m_frontLeft = new SwerveModule(
        Constants.driveSubsystemFrontLeftTurnDeviceId, 
        Constants.driveSubsystemFrontLeftDriveDeviceId,
        Constants.driveSubsystemFrontLeftEncoderChannelA, 
        Constants.driveSubsystemFrontLeftEncoderChannelB);
    private final SwerveModule m_frontRight = new SwerveModule(
        Constants.driveSubsystemFrontRightTurnDeviceId, 
        Constants.driveSubsystemFrontRightDriveDeviceId, 
        Constants.driveSubsystemFrontRightEncoderChannelA,
        Constants.driveSubsystemFrontRightEncoderChannelB);
    private final SwerveModule m_backLeft = new SwerveModule(
        Constants.driveSubsystemBackLeftTurnDeviceId, 
        Constants.driveSubsystemBackLeftDriveDeviceId, 
        Constants.driveSubsystemBackLeftEncoderChannelA, 
        Constants.driveSubsystemBackLeftEncoderChannelB);
    private final SwerveModule m_backRight = new SwerveModule(
        Constants.driveSubsystemBackRightTurnDeviceId, 
        Constants.driveSubsystemBackRightDriveDeviceId, 
        Constants.driveSubsystemBackRightEncoderChannelA, 
        Constants.driveSubsystemBackRightEncoderChannelB);

    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
    
    public DriveSubsystem(){
        m_gyro.reset();
    }

    public static double getMaxSpeed(){
        return kMaxAngularSpeed;
    }

    public void updateMaxSpeed(double maxSpeed){
        if (maxSpeed <= Constants.kTrueMaxSpeed && maxSpeed >= 0){
            kMaxSpeed = maxSpeed;
        }
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