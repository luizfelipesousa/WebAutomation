package automation.data.models;

import java.util.HashMap;

public class Device {

	public Device(String platformName, String platformVersion, String deviceName, String udid) {
		super();
		this.platformName = platformName;
		this.platformVersion = platformVersion;
		this.deviceName = deviceName;
		this.udid = udid;
	}

	public Device() {
		super();
	}

	private String platformName;
	private String platformVersion;
	private String deviceName;
	private String udid;
	private String deviceType;
	private String deviceHostingType;
	private String nickName;
	private String connectName;
	private boolean connected;
	private HashMap<String, String> currentReservation;

	@Override
	public String toString() {
		return "Device [platformName=" + platformName + ", platformVersion=" + platformVersion + ", deviceName="
				+ deviceName + ", udid=" + udid + ", deviceType=" + deviceType + ", deviceHostingType="
				+ deviceHostingType + ", nickName=" + nickName + ", connectName=" + connectName + ", connected="
				+ connected + ", currentReservation=" + currentReservation + "]";
	}

	public String getPlatformName() {
		return platformName;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getUdid() {
		return udid;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public String getDeviceHostingType() {
		return deviceHostingType;
	}

	public String getNickName() {
		return nickName;
	}

	public String getConnectName() {
		return connectName;
	}

	public boolean isConnected() {
		return connected;
	}

	public HashMap<String, String> getCurrentReservation() {
		return currentReservation;
	}

}
