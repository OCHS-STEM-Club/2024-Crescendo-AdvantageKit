// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import org.littletonrobotics.junction.Logger;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.autos.exampleAuto;
import frc.robot.commands.AlignToTagCmd;
import frc.robot.commands.ArmDownCommand;
import frc.robot.commands.ArmUpCommand;
import frc.robot.commands.DriveTeleopCmd;
import frc.robot.commands.IntakeInCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
  
public class RobotContainer {
  // Subsystems
  SwerveSubsystem m_swerveSubsystem = new SwerveSubsystem();
  ArmSubsystem m_armSubsystem = new ArmSubsystem();
  IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  
  // Controllers
  public CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  public CommandXboxController m_buttonBox = new CommandXboxController(Constants.OperatorConstants.kOperatorControllerPort);

  // Commands
  DriveTeleopCmd m_driveTeleopCmd = new DriveTeleopCmd(m_swerveSubsystem, m_driverController);
  ArmDownCommand m_armDownCommand = new ArmDownCommand(m_armSubsystem);
  ArmUpCommand m_armUpCommand = new ArmUpCommand(m_armSubsystem);
  IntakeInCommand m_intakeCommand = new IntakeInCommand(m_intakeSubsystem);
  ShooterCommand m_shooterCommand = new ShooterCommand(m_shooterSubsystem);
  AlignToTagCmd m_alignToTagCmd = new AlignToTagCmd(m_swerveSubsystem);

  // Autos
  exampleAuto m_exampleAuto = new exampleAuto(m_swerveSubsystem);

  private final SendableChooser<Command> autoChooser;
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_swerveSubsystem.setDefaultCommand(m_driveTeleopCmd);
    // autoChooser = AutoBuilder.buildAutoChooser("Autos");
   
    autoChooser = AutoBuilder.buildAutoChooser("New Auto");
    autoChooser.setDefaultOption("Drive straight", m_exampleAuto);
    SmartDashboard.putData("Autos", autoChooser);

    // SmartDashboard.putData("Auto Chooser", autoChooser);
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
    m_driverController.rightBumper().onTrue(
      new InstantCommand(m_swerveSubsystem::resetHeading, m_swerveSubsystem)
      );

    m_driverController.y().onTrue(
      new InstantCommand(m_swerveSubsystem::resetPose, m_swerveSubsystem)
      );

    m_buttonBox.pov(0).whileTrue(
      m_armUpCommand
      );
    m_buttonBox.pov(180).whileTrue(
      m_armDownCommand
      );

    m_driverController.leftTrigger().whileTrue(
      m_intakeCommand
      );

    m_driverController.a().whileTrue(
      m_shooterCommand
      );

    m_driverController.leftBumper().whileTrue(
      m_alignToTagCmd
      );

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    m_swerveSubsystem.resetPose();
    return autoChooser.getSelected();

    // TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
    //   AutoConstants.kMaxSpeedMetersPerSecond,
    //   AutoConstants.kMaxAccelerationMetersPerSecondSquared)
    //   .setKinematics(DriveConstants.kDriveKinematics);


    //   Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
    //     new Pose2d(0, 0, new Rotation2d(0)),
    //     List.of(
    //       new Translation2d(0.5, 0),
    //       new Translation2d(1, 0)),
    //     new Pose2d(1.5, 0, Rotation2d.fromDegrees(0)),
    //     trajectoryConfig);


    //   PIDController xController = new PIDController(AutoConstants.kPXController, 0, 0);
    //   PIDController yController = new PIDController(AutoConstants.kPYController, 0, 0);
    //   ProfiledPIDController thetaController = new ProfiledPIDController(
    //     AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
    //     thetaController.enableContinuousInput(-Math.PI, Math.PI);

    //     SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
    //       trajectory, 
    //       m_swerveSubsystem::getPose, 
    //       DriveConstants.kDriveKinematics, 
    //       xController, 
    //       yController, 
    //       thetaController,
    //       m_swerveSubsystem::setModuleStates,
    //       m_swerveSubsystem);


    //       return new SequentialCommandGroup(
    //         new InstantCommand(() -> m_swerveSubsystem.resetPose(trajectory.getInitialPose())),
    //         swerveControllerCommand,
    //         new InstantCommand(() -> m_swerveSubsystem.stopModules())); 

     }
  

  public void resetGyro() {
    m_swerveSubsystem.resetHeading();
  }

  public Command resetPose() {
    return new InstantCommand(m_swerveSubsystem::resetPose, m_swerveSubsystem);
  }

  public void armBrakeMode() {
    m_armSubsystem.armBrakeMode();
  }

  public void armCoastMode() {
    m_armSubsystem.armCoastMode();
  }
  

}