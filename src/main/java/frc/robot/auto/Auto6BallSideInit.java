// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.auto;

// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.autoPaths.InitLeftToTrench;
// import frc.robot.autoPaths.AutoShoot;
// import frc.robot.autoPaths.TrenchToLeftInit;
// import frc.robot.commands.Track;

// // NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// // information, see:
// // https://docs.wpilib.org/en/laDriveLinear/docs/software/commandbased/convenience-features.html
// public class Auto6BallSideInit extends SequentialCommandGroup {
//   /**
//    * Creates a new AutoCommand.
//    */
//   public Auto6BallSideInit() {
//     // Add your commands in the super() call, e.g.
//     // super(new FooCommand(), new BarCommand());
//     super(
//       new Track(), new AutoShoot(2.5), new InitLeftToTrench(), new TrenchToLeftInit(), new Track(), new AutoShoot(4)
//       );
//   }
// }