package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.subsystems.AmpShooterSubsystem;
import frc.robot.subsystems.LoaderSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class TurnOff extends Command{
    public TurnOff(LoaderSubsystem loaderSubsystem){
        loaderSubsystem.stopLoader();
    }
    
    public TurnOff(ShooterSubsystem shooterSubsystem){
        shooterSubsystem.stopShooter();
        shooterSubsystem.stopAmpShooter();
    }

   
    @Override
    public boolean isFinished() {
        return true;
    }
}
