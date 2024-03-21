/*package frc.robot.subsystems;

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
    private static final double kWheelRadius = 0.0504;
    private int kEncoderResolutionDrive = 4096;
    private int kEncoderResolutionTurn = 4096;
    private static int kEncoderResolution = 4096;
    private static final double kModuleMaxAngularVelocity = DriveSubsystem.getMaxSpeed();
    private static final double kModuleMaxAngularAcceleration = 2 * Math.PI;

    private final CANSparkMax m_driveMotor;
    private final CANSparkMax m_turningMotor;
    private final Encoder m_driveEncoder;
    private final Encoder m_turningEncoder;
    private final PIDController m_drivePIDController = new PIDController(1, 0, 0);//PID COntroller BS needs to be fucking sorted
    private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));//PID COntroller BS needs to be fucking sorted
    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);

    public SwerveModule(int turningDeviceId,
                        int driveDeviceId,
                        int driveEncoderChannelA, 
                        int driveEncoderChannelB, 
                        int turningEncoderChannelA, 
                        int turningEncoderChannelB) {
        m_driveMotor = new CANSparkMax(driveDeviceId, CANSparkLowLevel.MotorType.kBrushless);
        m_turningMotor = new CANSparkMax(turningDeviceId, CANSparkLowLevel.MotorType.kBrushless);
        m_driveEncoder = new Encoder(driveEncoderChannelA, driveEncoderChannelB);
        m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);
        m_driveEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolutionDrive);
        m_turningEncoder.setDistancePerPulse(2 * Math.PI / kEncoderResolutionTurn);
        m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public static Encoder createSharedEncoderConfiguration(int channelA, int channelB) {
        Encoder encoder = new Encoder(channelA, channelB);
        encoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
        return encoder;
    }

    public int getDriveEncoderResolution(){
        return kEncoderResolutionDrive;
    }

    public int getTurnEncoderResolution(){
        return kEncoderResolutionTurn;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(m_driveEncoder.getRate(), new Rotation2d(m_turningEncoder.getDistance()));
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(m_driveEncoder.getDistance(), new Rotation2d(m_turningEncoder.getDistance()));
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        var encoderRotation = new Rotation2d(m_turningEncoder.getDistance());
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, encoderRotation);
        state.speedMetersPerSecond *= state.angle.minus(encoderRotation).getCos();
        final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getRate(), state.speedMetersPerSecond);
        final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);
        final double turnOutput = m_turningPIDController.calculate(m_turningEncoder.getDistance(), state.angle.getRadians());
        final double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);
        m_driveMotor.setVoltage(driveOutput + driveFeedforward);
        m_turningMotor.setVoltage(turnOutput + turnFeedforward);
    }
}
*/