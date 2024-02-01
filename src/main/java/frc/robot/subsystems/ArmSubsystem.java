// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.GroupMotorControllers;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */
  private CANSparkMax armMotorLeft;
  private CANSparkMax armMotorRight;
  private RelativeEncoder armEncoderLeft;
  private RelativeEncoder armEncoderRight;

  public ArmSubsystem() {
    armMotorLeft = new CANSparkMax(Constants.ArmConstants.kArmMotorLeftID, MotorType.kBrushless);
    armMotorRight = new CANSparkMax(Constants.ArmConstants.kArmMotorRightID, MotorType.kBrushless);
    armEncoderLeft = armMotorLeft.getEncoder();
    armEncoderRight = armMotorRight.getEncoder();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public void armMotorUp() {
    armMotorLeft.set(0.5);
    armMotorRight.set(0.5);
  }

  public void armMotorDown() {
    armMotorLeft.set(-0.5);
    armMotorRight.set(-0.5);
  }

  public void armOff() {
    armMotorLeft.set(0);
    armMotorRight.set(0);
  }
}
