package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
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
    
    private final SwerveModule m_frontLeft = new SwerveModule(1, 2, 0, 0, 0, 0);
    private final SwerveModule m_frontRight = new SwerveModule(3, 4, 0, 0, 0, 0);
    private final SwerveModule m_backLeft = new SwerveModule(5, 6, 0, 0, 0, 0);
    private final SwerveModule m_backRight = new SwerveModule(7, 8, 0, 0, 0, 0);

    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
    
    public DriveSubsystem(){
        
    }

    public static double getMaxSpeed(){
        return kMaxAngularSpeed;
    }

    public void updateMaxSpeed(double maxSpeed){
        kMaxSpeed = maxSpeed;
    }

    public void drive(double xSpeed, double ySpeed, double rot){
        SwerveModuleState[] swerveModuleStates = m_kinematics.toSwerveModuleStates(new ChassisSpeeds(xSpeed, ySpeed, rot));
        
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, kMaxSpeed);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_backLeft.setDesiredState(swerveModuleStates[2]);
        m_backRight.setDesiredState(swerveModuleStates[3]);
    }

    public void updateEncoderResolutionDrive(int newDriveEncoderResolution){
        m_frontLeft.updateEncoderResolution(newDriveEncoderResolution, m_frontLeft.getTurnEncoderResolution());
        m_frontRight.updateEncoderResolution(newDriveEncoderResolution, m_frontRight.getTurnEncoderResolution());
        m_backLeft.updateEncoderResolution(newDriveEncoderResolution, m_backLeft.getTurnEncoderResolution());
        m_backRight.updateEncoderResolution(newDriveEncoderResolution, m_backRight.getTurnEncoderResolution());
    }

    public void updateEncoderResolutionTurn(int newTrueEncoderResolution){
        m_frontLeft.updateEncoderResolution(m_frontLeft.getDriveEncoderResolution(), newTrueEncoderResolution);
        m_frontRight.updateEncoderResolution(m_frontRight.getDriveEncoderResolution(), newTrueEncoderResolution);
        m_backLeft.updateEncoderResolution(m_backLeft.getDriveEncoderResolution(), newTrueEncoderResolution);
        m_backRight.updateEncoderResolution(m_backRight.getDriveEncoderResolution(), newTrueEncoderResolution);
    }
}
 