package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class Drive extends Command {
    
    private DriveSubsystem driveTrain;
    private DoubleSupplier lsp;
    private DoubleSupplier rsp;
    
    public Drive(DriveSubsystem driveSubsystem, DoubleSupplier leftspeed, DoubleSupplier rightspeed){
        this.driveTrain = driveSubsystem;
        this.lsp = leftspeed;
        this.rsp = rightspeed;
        addRequirements(driveSubsystem);
    }

    public void updateSpeed(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed){
        this.lsp = leftSpeed;
        this.rsp = rightSpeed;
    }

    public void updateSpeedLeft(DoubleSupplier leftspeed){
        this.lsp = leftspeed;
    }

    public void updateSpeedRight(DoubleSupplier rightspeed){
        this.rsp = rightspeed;
    }

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
        driveTrain.drive(lsp.getAsDouble(),rsp.getAsDouble());
    }

    @Override
    public void end (boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}