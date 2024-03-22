package frc.robot.subsystems;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

public class SwerveModule {

    private final CANSparkMax m_driveMotor;
    private final CANSparkMax m_turningMotor;
    private final Encoder m_turningEncoder;
    private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(2, 0, 0.1, new TrapezoidProfile.Constraints(DriveSubsystem.getMaxSpeed(), 2 * Math.PI));
    private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);
    private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);

    public SwerveModule(int turningDeviceId,
                        int driveDeviceId, 
                        int turningEncoderChannelA, 
                        int turningEncoderChannelB) {
        m_driveMotor = new CANSparkMax(driveDeviceId, CANSparkLowLevel.MotorType.kBrushless);
        m_turningMotor = new CANSparkMax(turningDeviceId, CANSparkLowLevel.MotorType.kBrushless);
        m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);
        m_turningEncoder.setDistancePerPulse(2 * Math.PI / Constants.encoderResolution);
        m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public int getEncoderResolution(){
        return Constants.encoderResolution;
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        var encoderRotation = new Rotation2d(m_turningEncoder.getDistance());
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, encoderRotation);
        state.speedMetersPerSecond *= state.angle.minus(encoderRotation).getCos();
        final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);
        final double turnOutput = m_turningPIDController.calculate(m_turningEncoder.getDistance(), state.angle.getRadians());
        final double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);
        m_driveMotor.setVoltage(driveFeedforward);
        m_turningMotor.setVoltage(turnOutput + turnFeedforward);
    }
}