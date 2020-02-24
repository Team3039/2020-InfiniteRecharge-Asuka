/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.TURRET_PPR_TO_DEGREES;
import static frc.robot.Constants.kP_TURRET;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * This device is responisble for the rotational control of the "Shooter" and the tracking of the 
 * retro-reflective target on the "Power Port"
 */
public class Turret extends SubsystemBase {
  
  public TalonSRX turret = new TalonSRX(RobotMap.turret);
  public DigitalInput turretSwitch = new DigitalInput(RobotMap.turretSwitch);
  
  public Turret() {
    turret.setSelectedSensorPosition(0);
    turret.configSelectedFeedbackCoefficient(TURRET_PPR_TO_DEGREES); //Convert to Degrees of Revolution
    turret.setSensorPhase(true);
    setLed(false);
    setPipeline(0);
    setCamMode(false);
  }

  public enum TurretControlMode {
    IDLE, 
    TRACKING,
    CONTROL, 
  }

  public TurretControlMode turretControlMode = TurretControlMode.IDLE;

  public synchronized TurretControlMode getControlMode() {
    return turretControlMode;
  }

  public synchronized void setControlMode(TurretControlMode controlMode) {
    this.turretControlMode = controlMode;
  }

  public void setLed(boolean isOn) {
    if(isOn) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3); //Force LED on
    }
    else {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1); //Force LED off
    }
  }

  public void setPipeline(int pipeline) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline); //Set the Pipeline #
  }

  public void setCamMode(boolean isTracking) {
    if(isTracking) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1); //Disable Vision Processing and Doubles Exposure
    }
    else {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0); //Enables Vision Processing and Lowers Exposure
    }
  }

  public void aim() {
    double error = getTargetX() * kP_TURRET;

    while (turretSwitch.get()) {
      if (turret.getSelectedSensorPosition() <= 0) {
        turret.set(ControlMode.PercentOutput, .15);
      }
      else if (turret.getSelectedSensorPosition() >= 0) {
        turret.set(ControlMode.Position, -.15);
      }
      else {
        turret.set(ControlMode.Position, error);
      }
    }
  }

  public void setTurretPosition(double degrees) {
    double modDegrees = degrees % 360;
    turret.set(ControlMode.Position, modDegrees);
  }

  public double getTargetX() {
    return Robot.targetX; 
  }

  public double getTargetY() {
    return Robot.targetY;
  }

  public double getTargetArea() {
    return Robot.targetArea;
  }

  public boolean onTarget() {
    if(Robot.targetValid == 1) {
      return true;
    }
    else {
      return false;
    }
  }

  public double getCurrentPosition() {
    return turret.getSelectedSensorPosition();        
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Turret Position", getCurrentPosition());
    // SmartDashboard.putNumber("target Y", getTargetY());

    synchronized (Turret.this) {
      switch (getControlMode()) {
        case IDLE:
          turret.set(ControlMode.Position, 0);
          break;
        case TRACKING:
          setLed(true);
          setCamMode(true);
          setPipeline(9);
          aim();
          break;
        case CONTROL:
          System.out.println("Set turret to specific angle");
          break;
        default:
          System.out.println("Unknown turret control mode");
          break;
      }
    }
  }
};
