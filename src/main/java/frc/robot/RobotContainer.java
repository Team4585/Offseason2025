// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.Autos;
import frc.robot.subsystems.*;
import swervelib.SwerveInputStream;

public class RobotContainer {
  
  ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  DriveSubsystem driveSubsystem = new DriveSubsystem();

  private final CommandJoystick m_driverController =
     new CommandJoystick(0);

  public RobotContainer() {
    configureBindings();
  }

 
  private void configureBindings() {
    SwerveInputStream driveSpeeds = new SwerveInputStream(driveSubsystem.getDrive(), () -> m_driverController.getX(), ()->m_driverController.getY(), ()->m_driverController.getZ());
    driveSubsystem.driveWithTheSpeeds(driveSpeeds);
  }

  
   public Command getAutonomousCommand() {
    return Autos.exampleAuto(m_exampleSubsystem);
   }
}
