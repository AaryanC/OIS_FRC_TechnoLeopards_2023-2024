package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class Drive extends Command {
    
    private DriveSubsystem driveTrain;
    private DoubleSupplier lsp;
    private DoubleSupplier rsp;
    private DoubleSupplier rot;
    private boolean fieldRelative;
    private DoubleSupplier periodSeconds;
    private double maxSpeed;
    
    public Drive(DriveSubsystem driveSubsystem, DoubleSupplier leftspeed, DoubleSupplier rightspeed, DoubleSupplier rot, boolean fieldRelative, DoubleSupplier periodSeconds){
        this.driveTrain = driveSubsystem;
        this.lsp = leftspeed;
        this.rsp = rightspeed;
        this.rot = rot;
        this.fieldRelative = fieldRelative;
        this.periodSeconds = periodSeconds;
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

    public void updateRot(DoubleSupplier rot){
        this.rot = rot;
    }

    public void updateFieldRelative(boolean fieldRelative){
        this.fieldRelative = fieldRelative;
    }

    public void updatePeriodSeconds(DoubleSupplier periodSeconds){
        this.periodSeconds = periodSeconds;
    }

    public void updateMaxSpeed(double maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
        driveTrain.updateMaxSpeed(maxSpeed);
        driveTrain.drive(lsp.getAsDouble(), rsp.getAsDouble(), rot.getAsDouble(), fieldRelative, periodSeconds.getAsDouble());
    }

    @Override
    public void end (boolean interrupted){
        driveTrain.updateMaxSpeed(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}