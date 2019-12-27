package org.futurerobotics.bluejay.original.detectors;

import android.graphics.Bitmap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;
import org.futurerobotics.bluejay.original.detectors.foundation.Foundation;
import org.futurerobotics.bluejay.original.detectors.foundation.Pipeline;
import org.futurerobotics.bluejay.original.detectors.foundation.SkyStone;
import org.futurerobotics.bluejay.original.detectors.foundation.Stone;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public class OpenCvDetector {
	
	//Originally in RobotControllerActivity, but caused the camera shutter to make weird noises, so now it lives here
	static {
		OpenCVLoader.initDebug();
		RobotLog.e(Core.NATIVE_LIBRARY_NAME);
//		System.loadLibrary("opencv_java3");
	}
	public final Pipeline foundationPipeline = new Pipeline();
	public List<Foundation> foundations = new ArrayList<>(); //detected foundations
	public List<Stone> stones = new ArrayList<>();
	volatile boolean activated = false;
	private ImageDetector vuforia;
	
	public OpenCvDetector(OpMode opMode) {
		
		this.vuforia = new ImageDetector(opMode);
	}
	
	public OpenCvDetector(ImageDetector vuforia) {
		this.vuforia = vuforia;
	}
	
	//for future interface
	public void start() {
//		vuforia.start();
		activated = true;
	}
	
	public void stop() {
//		vuforia.stop();
		activated = false;
	}
	
	/**
	 * hold the phone as you would use it to browse reddit
	 * x: 0 at the top, increases as you go down
	 * y: 0 at the right, increases as you go left
	 */
	public List<SkyStone> getSkystones() {
		//get raw image
		//raw image for camera
		Bitmap image = vuforia.getImage();
		
		//raw to Mat
		//image converted to OpenCV Mat
		Mat matImage = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC1);
		Utils.bitmapToMat(image, matImage);
		
		//Opencv pipeline
		foundationPipeline.process(matImage);
		return foundationPipeline.skyStones;
//		foundations.clear();
//		foundations.addAll(foundationPipeline.foundations);
//		stones.clear();
//		stones.addAll(foundationPipeline.stones);
	}
	
	public List<Foundation> getObjects() {
		if (!activated) throw new IllegalStateException("Not activated");
		
		return foundations;
	}
	
	public List<Stone> getObjectsStones() {
		if (!activated) throw new IllegalStateException("Not activated");
		
		return stones;
	}
}
