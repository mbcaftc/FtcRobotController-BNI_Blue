package org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.StartPositions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.AutoBlueAlliance;
import org.firstinspires.ftc.teamcode.Competition.CenterStage.Robots.BlueBot;

@Disabled
@Autonomous(name="Blue:Audience:Start_1")
public class BlueAudienceStart_Truss1 extends AutoBlueAlliance {


    @Override
    public void runOpMode() throws InterruptedException{
        Bot.initRobot(hardwareMap, true);
        Bot.setLinearOp(this);

        telemetry.addLine("Awaiting Start");
        telemetry.update();


        waitForStart();

        while(opModeIsActive()){

            // INSERT AUTO CODE BELOW

            Bot.rotatePixelArmDown(.5);
            sleep(1000);

            Bot.driveForward(.25, .28);
            sleep(100);


            Bot.rotateLeftNew(0.25, 3);
            sleep(100);


            Bot.driveForward(.5,9);
            sleep(100);

            // INSERT AUTO CODE ABOVE

            requestOpModeStop();

        }

        idle();
    }
}
