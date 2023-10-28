package org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.StartPositions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.AutoBlueAlliance;
import org.firstinspires.ftc.teamcode.Competition.CenterStage.Robots.BlueBot;

@Autonomous(name="Blue:Audience:Start_2")
public class BlueAudienceStart_Truss2 extends AutoBlueAlliance {

    BlueBot Bot = new BlueBot();

    @Override
    public void runOpMode() throws InterruptedException{
        Bot.initRobot(hardwareMap);
        Bot.setLinearOp(this);

        telemetry.addLine("Awaiting Start");
        telemetry.update();


        waitForStart();

        while(opModeIsActive()){

            // INSERT AUTO CODE BELOW


            Bot.driveForward(.25, 1.5);
            sleep(100);


            Bot.rotateLeftNew(0.25, 2.6);
            sleep(100);


            Bot.driveForward(.5,20);
            sleep(100);

            // INSERT AUTO CODE ABOVE

            requestOpModeStop();

        }

        idle();
    }
}
