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
    private static final double kWheelRadius = 0.0508;//NEED TO FIND
    private static final int kEncoderResolution = 4096;//NEED TO FIND

    private static final double kModuleMaxAngularVelocity = Math.PI;
    private static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared

    private final PWMSparkMax m_driveMotor;
    private final PWMSparkMax m_turningMotor;

    private final Encoder m_driveEncoder;
    private final Encoder m_turningEncoder;

    private final PIDController m_drivePIDController = new PIDController(1, 0, 0);//NEED TO FIND

    private final ProfiledPIDController m_turningPIDController = 
    new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));//NEED TO FIND

    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);//NEED TO FIND
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);//NEED TO FIND

    public SwerveModule(int driveMotorChannel, 
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
}
