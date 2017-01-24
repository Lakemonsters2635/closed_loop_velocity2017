package org.usfirst.frc.team2635.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Example demonstrating the velocity closed-loop servo.
 * Tested with Logitech F350 USB Gamepad inserted into Driver Station]
 * 
 * Be sure to select the correct feedback sensor using SetFeedbackDevice() below.
 *
 * After deploying/debugging this to your RIO, first use the left Y-stick 
 * to throttle the Talon manually.  This will confirm your hardware setup.
 * Be sure to confirm that when the Talon is driving forward (green) the 
 * position sensor is moving in a positive direction.  If this is not the cause
 * flip the boolena input to the SetSensorDirection() call below.
 *
 * Once you've ensured your feedback device is in-phase with the motor,
 * use the button shortcuts to servo to target velocity.  
 *
 * Tweak the PID gains accordingly.
 */
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class Robot extends IterativeRobot {
  
	private static final int SHOOTER_TALON_R = 4;
	
	CANTalon L_talon = new CANTalon(SHOOTER_TALON_R);
	
private static final int SHOOTER_TALON_L = 6;
	
	CANTalon R_talon = new CANTalon(SHOOTER_TALON_L);
	
	//Joystick L_joy = new Joystick(0);
	//Joystick R_joy = new Joystick(0);
	Joystick _joy = new Joystick(0);
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	
	public void robotInit() {
        /* first choose the sensor */
        //_talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        L_talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        L_talon.reverseSensor(false);
        L_talon.configEncoderCodesPerRev(1000); // if using FeedbackDevice.QuadEncoder
        //_talon.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        L_talon.configNominalOutputVoltage(+0.0f, -0.0f);
        L_talon.configPeakOutputVoltage(+12.0f, -12.0f);
        /* set closed loop gains in slot0 */
        L_talon.setProfile(0);
        L_talon.setF(0.1097);
        L_talon.setP(0.22);
        L_talon.setI(0); 
        L_talon.setD(0);
	}
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	/* get gamepad axis */
    	//double leftYstick = L_joy.getAxis(AxisType.kY);
    	double leftYstick = -1.0 * _joy.getRawAxis(1);
    	double motorOutput_R = L_talon.getOutputVoltage() / L_talon.getBusVoltage();
    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput_R);
        _sb.append("\tspd:");
        _sb.append(L_talon.getSpeed() );
        
        if(_joy.getRawButton(1)){
        	/* Speed mode */
        	double LtargetSpeed = leftYstick * 1500.0; /* 1500 RPM in either direction */
        	L_talon.changeControlMode(TalonControlMode.Speed);
        	//_talon.set(targetSpeed); /* 1500 RPM in either direction */
        	L_talon.set(LtargetSpeed); /* 1500 RPM in either direction */
        	/* append more signals to print when in speed mode. */
            _sb.append("\terr:");
            _sb.append(L_talon.getClosedLoopError());
            _sb.append("\ttrg:");
            _sb.append(LtargetSpeed);
        } else {
        	/* Percent voltage mode */
        	L_talon.changeControlMode(TalonControlMode.PercentVbus);
        	L_talon.set(leftYstick);
        }

        if(++_loops >= 10) {
        	_loops = 0;
        	System.out.println(_sb.toString());
        }
        /*------------------------------------------------------------(other side)-------------*/
        
        /* first choose the sensor */
        //_talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        R_talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        R_talon.reverseSensor(false);
        R_talon.configEncoderCodesPerRev(1000); // if using FeedbackDevice.QuadEncoder
        //_talon.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        R_talon.configNominalOutputVoltage(+0.0f, -0.0f);
        R_talon.configPeakOutputVoltage(+12.0f, -12.0f);
        /* set closed loop gains in slot0 */
        R_talon.setProfile(0);
        R_talon.setF(0.1097);
        R_talon.setP(0.22);
        R_talon.setI(0); 
        R_talon.setD(0);
        
        /* get gamepad axis */
        //double RightYstick = R_joy.getAxis(AxisType.kY);
        double RightYstick = _joy.getRawAxis(5);
    	double motorOutput_L = R_talon.getOutputVoltage() / R_talon.getBusVoltage();
    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput_L);
        _sb.append("\tspd:");
        _sb.append(R_talon.getSpeed() );
        
        if(_joy.getRawButton(1)){
        	/* Speed mode */
        	double RtargetSpeed = RightYstick * 1500.0; /* 1500 RPM in either direction */
        	R_talon.changeControlMode(TalonControlMode.Speed);
        	//_talon.set(targetSpeed); /* 1500 RPM in either direction */
        	R_talon.set(RtargetSpeed); /* 1500 RPM in either direction */
        	/* append more signals to print when in speed mode. */
            _sb.append("\terr:");
            _sb.append(R_talon.getClosedLoopError());
            _sb.append("\ttrg:");
            _sb.append(RtargetSpeed);
        } else {
        	/* Percent voltage mode */
        	R_talon.changeControlMode(TalonControlMode.PercentVbus);
        	R_talon.set(RightYstick);
        }

        if(++_loops >= 10) {
        	_loops = 0;
        	System.out.println(_sb.toString());
        	
        _sb.setLength(0);
    }
}
}