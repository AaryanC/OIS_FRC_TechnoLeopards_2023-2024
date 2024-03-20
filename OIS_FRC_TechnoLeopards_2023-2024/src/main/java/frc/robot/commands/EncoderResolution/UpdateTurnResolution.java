package frc.robot.commands.EncoderResolution;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class UpdateTurnResolution extends Command{
    private final DriveSubsystem driveSubsystem;
    private final int resolution;

    public UpdateTurnResolution(DriveSubsystem driveSubsystem, int resolution){
        this.driveSubsystem = driveSubsystem;
        this.resolution = resolution;
    }

    @Override
    public void initialize() {
        driveSubsystem.updateEncoderResolutionTurn(resolution);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
