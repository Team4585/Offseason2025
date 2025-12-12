// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.driveandturn;
import frc.robot.subsystems.*;
import swervelib.SwerveInputStream;

public class RobotContainer {
  private static final String kNoAuto = "None";
  private static final String kLeftAuto = "Left";
  private static final String kRightAuto = "Right";
  private static final String kCenterAuto = "Center";
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Command AutoCommand;
  
  ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  public DriveSubsystem driveSubsystem = new DriveSubsystem();
  ShootingSubsystem shootingSubsystem = new ShootingSubsystem();

  private final CommandJoystick m_driverController =
     new CommandJoystick(0);

  public RobotContainer() {
    configureBindings();
    m_chooser.setDefaultOption("No Auto", kNoAuto);
    m_chooser.addOption("Left", kLeftAuto);
    m_chooser.addOption("Right", kRightAuto);
    m_chooser.addOption("Center", kCenterAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

 
  private void configureBindings() {
    SwerveInputStream driveSpeeds = new SwerveInputStream(driveSubsystem.getDrive(), () -> m_driverController.getX(), ()->m_driverController.getY(), ()->m_driverController.getZ());
    driveSubsystem.setDefaultCommand(driveSubsystem.driveWithTheSpeeds(driveSpeeds));

    m_driverController.trigger().whileTrue(new RunCommand(()->{shootingSubsystem.shoot();}));
    m_driverController.button(6).whileTrue(new RunCommand(()->{shootingSubsystem.backwards();}));
    shootingSubsystem.setDefaultCommand(new RunCommand(()->{shootingSubsystem.stop();}));

  }

  
   public Command getAutonomousCommand() {
    if(m_chooser.getSelected() == kNoAuto){
      AutoCommand = new SequentialCommandGroup(new driveandturn(0, 0, 0, driveSubsystem));
      
    } 


    if(m_chooser.getSelected() == kCenterAuto){
      AutoCommand = new SequentialCommandGroup(new driveandturn(2, 0, 0, driveSubsystem));
      
    } 

    if(m_chooser.getSelected() == kLeftAuto){
      AutoCommand = new SequentialCommandGroup(new driveandturn(0, 0, 2, driveSubsystem));
      
    } 
    if(m_chooser.getSelected() == kCenterAuto){
      AutoCommand = new SequentialCommandGroup(new driveandturn(0, 0, -2, driveSubsystem));
      
    } 
    else{
      AutoCommand = new SequentialCommandGroup(new driveandturn(0, 0, 0, driveSubsystem));
    }
    return AutoCommand;
   }
}
