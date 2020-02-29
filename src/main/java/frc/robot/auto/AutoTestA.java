/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveLinear;
import frc.robot.commands.ResetPose;
import frc.robot.commands.Rotate;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoTestA extends SequentialCommandGroup {
  /**
   * Creates a new AutoTest.
   */
  public AutoTestA() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new Rotate(45),
      new WaitCommand(.3),
      new DriveLinear(103, 45)
      // new WaitCommand(.2),
      // new Rotate(37)
      // new WaitCommand(.25),
      // new DriveLinear(120),
      // new DriveLinear(-90)
    );
  }
}
