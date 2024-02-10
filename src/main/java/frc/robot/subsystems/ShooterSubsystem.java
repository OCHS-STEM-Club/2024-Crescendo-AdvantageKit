// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;


import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
private TalonFX shooterMotorLeft;
private TalonFX shooterMotorRight;

final VelocityVoltage m_velocity = new VelocityVoltage(0);



  public ShooterSubsystem() {
    shooterMotorLeft = new TalonFX(ShooterConstants.kShooterMotorLeftID);
    shooterMotorRight = new TalonFX(ShooterConstants.kShooterMotorRightID);

    shooterMotorLeft.setInverted(false);
    shooterMotorRight.setInverted(false);

    shooterMotorLeft.setNeutralMode(NeutralModeValue.Coast);
    shooterMotorRight.setNeutralMode(NeutralModeValue.Coast);

    
    // in init function, set slot 0 gains
    var slot0ConfigsLeft = new Slot0Configs();
    slot0ConfigsLeft.kP = 1; // An error of 0.5 rotations results in 12 V output
    slot0ConfigsLeft.kI = 0; // no output for integrated error
    slot0ConfigsLeft.kD = 0; // A velocity of 1 rps results in 0.1 V output

    shooterMotorLeft.getConfigurator().apply(slot0ConfigsLeft, 0.05);


    // in init function, set slot 0 gains
    var slot0ConfigsRight = new Slot0Configs();
    slot0ConfigsRight.kP = 1; // An error of 0.5 rotations results in 12 V output
    slot0ConfigsRight.kI = 0; // no output for integrated error
    slot0ConfigsRight.kD = 0; // A velocity of 1 rps results in 0.1 V output

    shooterMotorRight.getConfigurator().apply(slot0ConfigsRight, 0.05);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("leftShooterVelocity", leftShooterVelocity().getValueAsDouble());
    SmartDashboard.putNumber("rightShooterVelocity", rightShooterVelocity().getValueAsDouble());

    Logger.recordOutput("Left Shooter Velocity", leftShooterVelocity().getValueAsDouble());
    Logger.recordOutput("Right Shooter Velocity", rightShooterVelocity().getValueAsDouble());

    m_velocity.Slot = 0;

  }

  public void shooterOn() {
    shooterMotorLeft.setControl(m_velocity.withVelocity(50));
    shooterMotorRight.setControl(m_velocity.withVelocity(50));
  }

  public void shooterOff() {
    shooterMotorLeft.setControl(m_velocity.withVelocity(0));
    shooterMotorRight.setControl(m_velocity.withVelocity(0));
  }

  public StatusSignal<Double> leftShooterVelocity() {
    return shooterMotorLeft.getVelocity();
  }

  public StatusSignal<Double> rightShooterVelocity() {
    return shooterMotorRight.getVelocity();     
  }
}
