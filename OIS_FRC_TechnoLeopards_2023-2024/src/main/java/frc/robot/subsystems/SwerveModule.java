/*
 * Source for most of the code(as a reference point): https://github.com/wpilibsuite/allwpilib/tree/main/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/swervebot
 * Anything marked with "//NEED TO FIND" requires customization to our robot
 * Anything marked with "//NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES" needs to be understood and improved upon
 */

//CODE TO POTENTIALLY IMPLIMENT

//FOR DEGREE CHANGE

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class SwerveModule {
    public static double degreeChangeClosest(double targetDegree, double currentAngle){
        //Possitive is clockwise negative is anti-clockwise
        double degreeDifference = targetDegree - currentAngle;

        if (degreeDifference == 0){
            return 0;
        }

        if (degreeDifference <= 180 && degreeDifference >= -180){
            return degreeDifference;
        } else {
            if (degreeDifference > 180){
                return (degreeDifference - 180) * -1;
            } else{
                return (degreeDifference + 180) * -1;
            }
        }
    }

    public static int degreeChangeClosest(int targetDegree, int currentAngle){
        //Possitive is clockwise negative is anti-clockwise
        int degreeDifference = targetDegree - currentAngle;

        if (degreeDifference == 0){
            return 0;
        }

        if (degreeDifference <= 180 && degreeDifference >= -180){
            return degreeDifference;
        } else {
            if (degreeDifference > 180){
                return (degreeDifference - 180) * -1;
            } else{
                return (degreeDifference + 180) * -1;
            }
        }
    }

    private static final double kWheelRadius = 0.0508;//NEED TO FIND
    private static final int kEncoderResolution = 4096;//NEED TO FIND

    private static final double kModuleMaxAngularVelocity = Math.PI;
    private static final double kModuleMaxAngularAcceleration = 2 * Math.PI;

    private final PWMSparkMax m_driveMotor;
    private final PWMSparkMax m_turningMotor;

    private final Encoder m_driveEncoder;
    private final Encoder m_turningEncoder;

    private final PIDController m_drivePIDController = new PIDController(1, 0, 0);//NEED TO FIND

    private final ProfiledPIDController m_turningPIDController = 
    new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));//NEED TO FIND

    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);//NEED TO FIND
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);//NEED TO FIND

    public SwerveModule(int driveMotorChannel, //NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES
    int turningMotorChannel, 
    int driveEncoderChannelA, 
    int driveEncoderChannelB, 
    int turningEncoderChannelA, 
    int turningEncoderChannelB){
    m_driveMotor = new PWMSparkMax(driveMotorChannel);
    m_turningMotor = new PWMSparkMax(turningMotorChannel);
    
    m_driveEncoder = new Encoder(driveEncoderChannelA, driveEncoderChannelB);
    m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);
    
        // Set the distance per pulse for the drive encoder. We can simply use the
        // distance traveled for one rotation of the wheel divided by the encoder
        // resolution.
    m_driveEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
    
        // Set the distance (in this case, angle) in radians per pulse for the turning encoder.
        // This is the the angle through an entire rotation (2 * pi) divided by the
        // encoder resolution.
    m_turningEncoder.setDistancePerPulse(2 * Math.PI / kEncoderResolution);
    
        // Limit the PID Controller's input range between -pi and pi and set the input
        // to be continuous.
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public SwerveModuleState getState() { //NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES
        return new SwerveModuleState(
            m_driveEncoder.getRate(), new Rotation2d(m_turningEncoder.getDistance()));
    }

    public SwerveModulePosition getPosition() { //NEED TO LEARN WHAT THIS ENTIRE FUNCTION DOES
        return new SwerveModulePosition(
            m_driveEncoder.getDistance(), new Rotation2d(m_turningEncoder.getDistance()));
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
        final double driveOutput =
            m_drivePIDController.calculate(m_driveEncoder.getRate(), state.speedMetersPerSecond);
    
        final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);
    
        // Calculate the turning motor output from the turning PID controller.
        final double turnOutput =
            m_turningPIDController.calculate(m_turningEncoder.getDistance(), state.angle.getRadians());
    
        final double turnFeedforward =
            m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);
    
        m_driveMotor.setVoltage(driveOutput + driveFeedforward);
        m_turningMotor.setVoltage(turnOutput + turnFeedforward);
      }
}
