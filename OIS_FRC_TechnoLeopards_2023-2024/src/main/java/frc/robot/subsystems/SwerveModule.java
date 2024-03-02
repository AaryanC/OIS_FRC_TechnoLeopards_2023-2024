/*
 * Source for most of the code(as a reference point): https://github.com/wpilibsuite/allwpilib/tree/main/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/swervebot
 * Anything marked with "//NEED TO FIND" requires customization to our robot
 * Anything marked with "//NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES" needs to be understood and improved upon
 */

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward; 
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

public class SwerveModule {
    private static final double kWheelRadius = 0.0508;//NEED TO FIND
    /*
    * kEncoderResolution like ADS, higher resolution = more precision but lower speed and lower resolution = less precision but higher speed
    */
    private int kEncoderResolutionDrive = 4096;//NEED TO FIND
    private int kEncoderResolutionTurn = 4096;//NEED TO FIND

    private static final double kModuleMaxAngularVelocity = DriveSubsystem.getMaxSpeed();
    private static final double kModuleMaxAngularAcceleration = 2 * Math.PI;

    private final CANSparkMax m_driveMotor;
    private final CANSparkMax m_turningMotor;

    private final Encoder m_driveEncoder;
    private final Encoder m_turningEncoder;

    private final PIDController m_drivePIDController = new PIDController(1, 0, 0);//NEED TO FIND

    private final ProfiledPIDController m_turningPIDController = 
    new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));//NEED TO FIND

    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);//NEED TO FIND
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);//NEED TO FIND

    public SwerveModule(int driveDeviceId,
    int turningDeviceId, 
    CANSparkLowLevel.MotorType driveMotorType,
    CANSparkLowLevel.MotorType turningMotorType,
    int driveEncoderChannelA, 
    int driveEncoderChannelB, 
    int turningEncoderChannelA, 
    int turningEncoderChannelB){
        //Creates the CANSparkMax objects with the device id for the motor and the motor type
        m_driveMotor = new CANSparkMax(driveDeviceId, driveMotorType);
        m_turningMotor = new CANSparkMax(turningDeviceId, turningMotorType);
    
        //Creates the encoders and sets the two channels
        m_driveEncoder = new Encoder(driveEncoderChannelA, driveEncoderChannelB);
        m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);

        //Sets the distance per pulse to the circumference of the wheel divided by the drive encoder resolution
        m_driveEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolutionDrive);

        //Sets the distance per pulse to 360 degrees divided by the drive encoder resolution
        m_turningEncoder.setDistancePerPulse(2 * Math.PI / kEncoderResolutionTurn);
    
        // Limit the PID Controller's input range between -pi and pi and set the input to be continuous.
        m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    //Allows for switch between prioritizing accuracy and prioritizing speed
    public void updateEncoderResolution(int newEncoderResolution){
        kEncoderResolutionDrive = newEncoderResolution;
        kEncoderResolutionTurn = newEncoderResolution;

        //Sets the distance per pulse to the circumference of the wheel divided by the drive encoder resolution
        m_driveEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolutionDrive);
    
        //Sets the distance per pulse to 360 degrees divided by the drive encoder resolution
        m_turningEncoder.setDistancePerPulse(2 * Math.PI / kEncoderResolutionTurn);
    }

    public void updateEncoderResolution(int newDriveEncoderResolution, int newTurnEncoderResolution){
        kEncoderResolutionDrive = newDriveEncoderResolution;
        kEncoderResolutionTurn = newTurnEncoderResolution;

        //Sets the distance per pulse to the circumference of the wheel divided by the drive encoder resolution
        m_driveEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolutionDrive);
    
        //Sets the distance per pulse to 360 degrees divided by the drive encoder resolution
        m_turningEncoder.setDistancePerPulse(2 * Math.PI / kEncoderResolutionTurn);
    }

    public int getDriveEncoderResolution(){
        return kEncoderResolutionDrive;
    }

    public int getTurnEncoderResolution(){
        return kEncoderResolutionTurn;
    }

    public SwerveModuleState getState() { //NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES
        return new SwerveModuleState(m_driveEncoder.getRate(), new Rotation2d(m_turningEncoder.getDistance()));
    }

    public SwerveModulePosition getPosition() { //NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES
        return new SwerveModulePosition(m_driveEncoder.getDistance(), new Rotation2d(m_turningEncoder.getDistance()));
    }

    public void setDesiredState(SwerveModuleState desiredState) { //NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES
        var encoderRotation = new Rotation2d(m_turningEncoder.getDistance());
    
        // Optimize the reference state to avoid spinning further than 90 degrees
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, encoderRotation);
    
        // Scale speed by cosine of angle error. This scales down movement perpendicular to the desired
        // direction of travel that can occur when modules change directions. This results in smoother
        // driving.
        state.speedMetersPerSecond *= state.angle.minus(encoderRotation).getCos();
    
        // Calculate the drive output from the drive PID controller.
        final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getRate(), state.speedMetersPerSecond);
    
        final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);
    
        // Calculate the turning motor output from the turning PID controller.
        final double turnOutput = m_turningPIDController.calculate(m_turningEncoder.getDistance(), state.angle.getRadians());
    
        final double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);
    
        m_driveMotor.setVoltage(driveOutput + driveFeedforward);
        m_turningMotor.setVoltage(turnOutput + turnFeedforward);
    }
}
