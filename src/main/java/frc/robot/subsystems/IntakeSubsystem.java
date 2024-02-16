// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */

    private CANSparkMax intakeMotor;
    private DigitalInput intakeSensor;

  public IntakeSubsystem() {
    intakeMotor = new CANSparkMax(Constants.IntakeConstants.kIntakeMotorID, MotorType.kBrushless);
    intakeSensor = new DigitalInput(0);
    }
  


  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    Logger.recordOutput("Digital Input sensor", intakeSensor.get());
  }

  public void intakeOn() {
    intakeMotor.set(0.25);
  }

  public void intakeOff() {
    intakeMotor.set(0);
  }

  public void intakeOut() {
    intakeMotor.set(-0.25);
  }

  public boolean beamBreakSensor() {
    return intakeSensor.get();
  }
}
