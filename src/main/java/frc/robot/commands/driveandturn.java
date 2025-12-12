package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;



public class driveandturn extends Command{
    protected Pose2d pose;
    private DriveSubsystem drivesub;
    protected int correctCount=0;
    protected double runtime=0;
    protected static StructPublisher<Pose2d> goalPosePublisher = NetworkTableInstance.getDefault().getStructTopic("goalPose", Pose2d.struct).publish();
    protected PIDController pid = new PIDController(0.01, 0, 0);

   
    public driveandturn(int distance, int angle,int strafe,DriveSubsystem driver){
        drivesub = driver;
        this.pose=drivesub.getPose().plus(new Transform2d(new Translation2d(strafe, distance), new Rotation2d(angle))); ;
        pid.setSetpoint(0);
        pid.setTolerance(0.1);
        
        addRequirements(drivesub);
    }
    public static double pythagorean(double x, double y){
        return Math.sqrt(Math.pow(x, 2)+ Math.pow(y, 2));
    }
    public static double pythagorean(double x1, double x2, double y1, double y2){
        return pythagorean(x1-x2, y1-y2);
    }

    @Override
    public void initialize(){
        runtime=0;
        pid.calculate(pythagorean(pose.getX(), drivesub.getPose().getX(), pose.getY(), drivesub.getPose().getY()));
        if (isFinished()){
            cancel();
        }
    }

     /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        
        Rotation2d angleRad = new Rotation2d(-(pose.getX()-drivesub.getPose().getX()), pose.getY()-drivesub.getPose().getY());
        
        double speed = -pid.calculate(pythagorean(pose.getX(), drivesub.getPose().getX(), pose.getY(), drivesub.getPose().getY()));
        drivesub.drive(speed, angleRad, pose.getRotation());

        SmartDashboard.putNumber("smallDriveSpeed", speed);
        SmartDashboard.putNumber("smallDriveError", pythagorean(pose.getX(), drivesub.getPose().getX(), pose.getY(), drivesub.getPose().getY()));
        SmartDashboard.putBoolean("smallDriveRunning", true);
        runtime+=0.02;
        SmartDashboard.putNumber("runtime", runtime);

        
        goalPosePublisher.set(pose); 
    }


    /**
     * @return true once the robot has been within tolerance for three frames straight
     */
    @Override
    public boolean isFinished(){
        return pid.atSetpoint()&& drivesub.getRobotVelocity().vxMetersPerSecond<0.01&& drivesub.getRobotVelocity().vyMetersPerSecond<0.01;
        
    }


    /**
     * command called when the command finishes
     * @param wasInterrupted wether or not the command was canceled
    */
    @Override
    public void end(boolean wasCanceled){
        SmartDashboard.putBoolean("smallDriveRunning", false);
    }
}
