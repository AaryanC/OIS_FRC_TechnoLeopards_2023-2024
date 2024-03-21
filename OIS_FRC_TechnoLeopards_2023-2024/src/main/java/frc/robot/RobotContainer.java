// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.TurnOff;
import frc.robot.commands.TurnOn;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LoaderSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  final LoaderSubsystem m_LoaderSubsystem = new LoaderSubsystem();
  final ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();
  final Joystick joystick = new Joystick(OperatorConstants.kDriverControllerPort);

  JoystickButton button1 = new JoystickButton(joystick, 1);
  JoystickButton button2 = new JoystickButton(joystick, 2);
  JoystickButton button3 = new JoystickButton(joystick, 3);
  JoystickButton button4 = new JoystickButton(joystick, 4);
  JoystickButton button5 = new JoystickButton(joystick, 5);
  JoystickButton button6 = new JoystickButton(joystick, 6);
  JoystickButton button7 = new JoystickButton(joystick, 7);
  JoystickButton button8 = new JoystickButton(joystick, 8);
  JoystickButton button9 = new JoystickButton(joystick, 0);
  JoystickButton button10 = new JoystickButton(joystick, 10);
  JoystickButton button11 = new JoystickButton(joystick, 11);
  JoystickButton button12 = new JoystickButton(joystick, 12);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    button1.onTrue(new TurnOn(m_ShooterSubsystem));
    button1.onFalse(new TurnOff(m_ShooterSubsystem));

    button2.onTrue(new TurnOn(m_LoaderSubsystem));
    button2.onFalse(new TurnOff(m_LoaderSubsystem));
  }
}
