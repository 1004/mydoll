package com.qike.telecast.presentation.model.dto.updata;

public class UpdateDto {
	public static final int FORCE_UPDATA = 1;
	public static final int UN_FORCE_UPDATA = 0;

	private String downlink;
	private int is_force;
	private String update_notes;
	private String version;
	private int version_code;
	private long size;

	public UpdateDto() {
		super();
	}

	public String getDownlink() {
		return downlink;
	}

	public void setDownlink(String downlink) {
		this.downlink = downlink;
	}

	public int getIs_force() {
		return is_force;
	}

	public void setIs_force(int is_force) {
		this.is_force = is_force;
	}

	public String getUpdate_notes() {
		return update_notes;
	}

	public void setUpdate_notes(String update_notes) {
		this.update_notes = update_notes;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getVersion_code() {
		return version_code;
	}

	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
