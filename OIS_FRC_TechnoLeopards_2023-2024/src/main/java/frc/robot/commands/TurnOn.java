package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.subsystems.AmpShooterSubsystem;
import frc.robot.subsystems.LoaderSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class TurnOn extends Command{
    public TurnOn(LoaderSubsystem loaderSubsystem){
        System.out.println("on");
        loaderSubsystem.runLoader();
    }
    
    public TurnOn(ShooterSubsystem shooterSubsystem){
        System.out.println("on");
        shooterSubsystem.runShooter();
        shooterSubsystem.runAmpShooter();
    }



    @Override
    public boolean isFinished() {
        return true;
    }
}
