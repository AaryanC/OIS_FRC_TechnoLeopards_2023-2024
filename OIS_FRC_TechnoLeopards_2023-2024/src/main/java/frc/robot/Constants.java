// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int kDriverControllerPort = 0;//Not in use?

  //ShooterSubsystem
  public static final int lowerShooterMotorDeviceId = 10;
  public static final int upperShooterMotorDeviceId = 9;

  //LoaderSubsystem
  public static final int lowerLoaderMotorDeviceId = 20;
  public static final int upperLoaderMotorDeviceId = 23;

  //DriveSubsystem
  public static final int driveSubsystemFrontLeftTurnDeviceId = 1;
  public static final int driveSubsystemFrontLeftDriveDeviceId = 2;
  public static final int driveSubsystemFrontRightTurnDeviceId = 3;
  public static final int driveSubsystemFrontRightDriveDeviceId = 4;
  public static final int driveSubsystemBackLeftTurnDeviceId = 5;
  public static final int driveSubsystemBackLeftDriveDeviceId = 6;
  public static final int driveSubsystemBackRightTurnDeviceId = 7;
  public static final int driveSubsystemBackRightDriveDeviceId = 8;

  public static final int driveSubsystemFrontLeftEncoderChannelA = 0;
  public static final int driveSubsystemFrontLeftEncoderChannelB = 0;
  public static final int driveSubsystemFrontRightEncoderChannelA = 0;
  public static final int driveSubsystemFrontRightEncoderChannelB = 0;
  public static final int driveSubsystemBackLeftEncoderChannelA = 0;
  public static final int driveSubsystemBackLeftEncoderChannelB = 0;
  public static final int driveSubsystemBackRightEncoderChannelA = 0;
  public static final int driveSubsystemBackRightEncoderChannelB = 0;

  //Robot
  public static final double robotDeadZone = 0.1;
}
