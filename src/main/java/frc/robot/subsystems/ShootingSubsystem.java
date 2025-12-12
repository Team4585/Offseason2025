package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootingSubsystem extends SubsystemBase {
  public SparkMax intakeMotor; 
  public SparkMax shootMotor;
  
  public ShootingSubsystem() {
    shootMotor = new SparkMax(Constants.MotorConstants.kShootMotorID, MotorType.kBrushless);
  }

  public void shoot(){
    shootMotor.set(1);
  }

  public void stop(){
    shootMotor.set(0);
 }

 public void backwards(){
  shootMotor.set(-1);
 }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
  
    return false;
  }

  @Override
  public void periodic() {
    
  }

  @Override
  public void simulationPeriodic() {
    
  }
}