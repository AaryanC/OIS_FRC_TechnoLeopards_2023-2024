// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;

public class gripperSubsystem extends SubsystemBase {
  /** Creates a new TelescopicSubsystem. */
  public static final CANSparkMax shooter1 = new CANSparkMax(MotorConstants.gripper1, MotorType.kBrushless);
  public static final CANSparkMax shooter2 = new CANSparkMax(MotorConstants.gripper2, MotorType.kBrushless);

  public final static MotorControllerGroup shooterGroup = new MotorControllerGroup(shooter1, shooter2);

  public shooterSubsystem() {
    shooter1.restoreFactoryDefaults();
    shooter2.restoreFactoryDefaults();
    shooter2.setInverted(true);
  }

  public void movingShooter(double speed) {
    shooterGroup.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}