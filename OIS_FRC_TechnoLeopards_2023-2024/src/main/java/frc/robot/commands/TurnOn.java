package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LoaderSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class TurnOn extends Command{
    public TurnOn(LoaderSubsystem loaderSubsystem){
        loaderSubsystem.runLoader();
    }
    
    public TurnOn(ShooterSubsystem shooterSubsystem){
        shooterSubsystem.runShooter();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
