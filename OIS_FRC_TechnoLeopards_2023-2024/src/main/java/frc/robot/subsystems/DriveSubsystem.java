/*package frc.robot.subsystems;

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
    
    private final SwerveModule m_frontLeft = new SwerveModule(1, 2, 1, 5, 9, 13);
    private final SwerveModule m_frontRight = new SwerveModule(3, 4, 2, 6, 10, 14);
    private final SwerveModule m_backLeft = new SwerveModule(5, 6, 3, 7, 11, 15);
    private final SwerveModule m_backRight = new SwerveModule(7, 8, 4, 8, 12, 16);

    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
    
    public DriveSubsystem(){
        
    }

    public static double getMaxSpeed(){
        return kMaxAngularSpeed;
    }

    public void updateMaxSpeed(double maxSpeed){
        if (maxSpeed <= kTrueMaxSpeed && maxSpeed >= 0){
            kMaxSpeed = maxSpeed;
        }
    }

    public void drive(double xSpeed, double ySpeed, double rot){
        SwerveModuleState[] swerveModuleStates = m_kinematics.toSwerveModuleStates(new ChassisSpeeds(xSpeed, ySpeed, rot));
        
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, kMaxSpeed);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_backLeft.setDesiredState(swerveModuleStates[2]);
        m_backRight.setDesiredState(swerveModuleStates[3]);
    }
}
 */