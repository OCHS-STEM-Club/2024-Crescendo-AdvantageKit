// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
private TalonFX shooterMotorLeft;
private TalonFX shooterMotorRight;


  public ShooterSubsystem() {
    shooterMotorLeft = new TalonFX(ShooterConstants.kShooterMotorLeftID);
    shooterMotorRight = new TalonFX(ShooterConstants.kShooterMotorRightID);

    shooterMotorLeft.setInverted(false);
    shooterMotorRight.setInverted(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void shooterOn() {
    shooterMotorLeft.set(0.4);
    shooterMotorRight.set(0.4);
  }

  public void shooterOff() {
    shooterMotorLeft.set(0);
    shooterMotorRight.set(0);
  }

}
